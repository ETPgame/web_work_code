package campsg.qunawan.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import campsg.qunawan.dao.CityDao;
import campsg.qunawan.dao.PriceDao;
import campsg.qunawan.dao.SequenceDao;
import campsg.qunawan.dao.ThemeOnTripDao;
import campsg.qunawan.dao.TripDao;
import campsg.qunawan.dao.TripPicDao;
import campsg.qunawan.entity.Trip;
import campsg.qunawan.entity.TripPicture;
import campsg.qunawan.utils.Packager;

public class TripDaoImpl extends JDBCBase implements TripDao {

	private CityDao cityDao = new CityDaoImpl();

	private SequenceDao sequenceDao = new SequenceDaoImpl();

	private PriceDao priceDao  = new PriceDaoImpl();

	private ThemeOnTripDao themeOnTripDao  = new ThemeOnTripDaoImpl();
	
	private TripPicDao tripPicDao  = new TripPicDaoImpl();

	@Override
	public Integer getTripNum(String key) {
		String sql = "SELECT count(*) FROM Trip WHERE is_ok=1";
		if (key != "")
			sql += " and title LIKE '%" + key + "%'";

		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		int count = 0;
		try {
			/**
			 * 	1.TYPE_FORWORD_ONLY,只可向前滚动；
			 * 	2.TYPE_SCROLL_INSENSITIVE,双向滚动，但不及时更新，就是如果数据库里的数据修改过，并不在ResultSet中反应出来。
			 *	3.TYPE_SCROLL_SENSITIVE，双向滚动，并及时跟踪数据库的更新,以便更改ResultSet中的数据。 
			 */
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			try {
				rs = ps.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, ps, con);
		}
		return count;
	}

	@Override
	public List<Trip> getTripByCondition(String key, Integer page) {
		String sql = "SELECT * FROM Trip WHERE is_ok=1";
		if (key != "")
			sql += " and title LIKE '%" + key + "%'";

		int max = 10;
		int start = (page - 1) * max;

		Connection con = JDBCUtil.getConnection();
		List<Trip> trips = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setMaxRows(start + max);
			rs = ps.executeQuery();
			rs.first();
			rs.relative(start - 1);
			while (rs.next()) {
				Trip trip = new Trip();
				trip.setId(rs.getInt("id"));
				trip.setTitle(rs.getString("title"));
				trip.setTime(rs.getInt("time"));
				trip.setTraffic(rs.getString("traffic"));
				trip.setHotel(rs.getString("hotel"));
				trip.setGood_rate(rs.getFloat("good_rate"));

				/* 封装并保存出发地对象 */
				int cityid = rs.getInt("start_place");
				trip.setStart(cityDao.getCityById(cityid));

				/* 封装并保存行程类型对象 */
				int typeid = rs.getInt("type");
				trip.setType(sequenceDao.getSequenceById(typeid));

				/* 封装并保存主题行程关系对象集合 */
				trip.setThemeontrip_list(themeOnTripDao.getThemesByTripId(trip.getId()));

				/* 封装并保存图片列表 */
				trip.setPic_list(tripPicDao.getTripPics(trip.getId()));

				/* 封装并保存价格列表 */
				trip.setPrice_list(priceDao.getPricesByTripId(trip.getId()));

				// 把封装好的Trip对象存放到集合中
				trips.add(trip);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, ps, con);
		}
		return trips;
	}
	
	
	
	@Override
	public List<Trip> getPageTripsByType(int id, int start, int maxCount){
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet t_rs = null;
		ResultSet pic_rs = null;
		
		List<Trip> trips = new ArrayList<>();
		try {
			String sql = "SELECT * FROM Trip t WHERE t.type = "+ id;
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			t_rs = query(ps, start, maxCount);
			while(t_rs.next()){
				Trip trip = new Trip();
				/*
				 * 因为首页只用到短标题、图片和价格所以只查出了这几个属性
				 */
				int tid = t_rs.getInt("id");
				// 保存id值
				trip.setId(tid);
				// 保存短标题
				trip.setS_title(t_rs.getString("s_title"));
				
				/** 封装并保存图片列表 */
				String pic_sql = "SELECT * FROM TripPicture tp WHERE tp.trip = "+tid+" AND tp.type = 0";
				ps = con.prepareStatement(pic_sql);
				pic_rs = query(ps);
				Set<TripPicture> tplist = new HashSet<>();
				if(pic_rs.next()){
					tplist.add(Packager.packTripPicture(pic_rs));
				}
				trip.setPic_list(tplist);
				
				/** 封装并保存价格列表 */
				trip.setPrice_list(priceDao.getPricesByTripId(tid));
				
				// 把封装好的Trip对象存放到集合中
				trips.add(trip);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.close(pic_rs);
			JDBCUtil.close(t_rs, ps, con);
		}
		
		return trips;
	}
	
	
	
}
