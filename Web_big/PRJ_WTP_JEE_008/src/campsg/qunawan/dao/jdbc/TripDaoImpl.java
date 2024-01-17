package campsg.qunawan.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import campsg.qunawan.dao.PriceDao;
import campsg.qunawan.dao.TripDao;
import campsg.qunawan.entity.Trip;
import campsg.qunawan.entity.TripPicture;
import campsg.qunawan.utils.Packager;

public class TripDaoImpl extends JDBCBase implements TripDao{
	
	private PriceDao priceDao = new PriceDaoImpl();

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
