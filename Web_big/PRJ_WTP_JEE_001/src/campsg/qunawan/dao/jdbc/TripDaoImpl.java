package campsg.qunawan.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import campsg.qunawan.dao.CityDao;
import campsg.qunawan.dao.PlaceDao;
import campsg.qunawan.dao.PriceDao;
import campsg.qunawan.dao.SequenceDao;
import campsg.qunawan.dao.TripDao;
import campsg.qunawan.entity.City;
import campsg.qunawan.entity.Dates;
import campsg.qunawan.entity.Place;
import campsg.qunawan.entity.PlaceOnTrip;
import campsg.qunawan.entity.Price;
import campsg.qunawan.entity.Schedule;
import campsg.qunawan.entity.Sequence;
import campsg.qunawan.entity.Theme;
import campsg.qunawan.entity.ThemeOnTrip;
import campsg.qunawan.entity.Trip;
import campsg.qunawan.entity.TripPicture;
import campsg.qunawan.form.SearchForm;
import campsg.qunawan.utils.Packager;

@Repository("tripDao")
public class TripDaoImpl extends JDBCBase implements TripDao{
	
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private SequenceDao sequenceDao;

	@Autowired
	private PriceDao priceDao;
	
	@Autowired
	private PlaceDao placeDao;

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

	@Override
	public List<Trip> getAllTripByCondition(SearchForm vo) {
		return getPageTripByCondition(vo, null, null);
	}

	@Override
	public List<Trip> getPageTripByCondition(SearchForm vo, Integer start, Integer maxCount) {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet trip_rs = null;
		ResultSet themelist_rs = null;
		ResultSet theme_rs = null;
		ResultSet placelist_rs = null;
		ResultSet place_rs = null;
		ResultSet picture_rs = null;
		
		List<Trip> trips = new ArrayList<>();
		try {
			String sql = getBaseSql(vo);
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			trip_rs = query(ps, start, maxCount);
			Trip trip = null;
			while(trip_rs.next()){
				trip = new Trip();
				trip.setId(trip_rs.getInt("id"));
				trip.setTitle(trip_rs.getString("title"));
				trip.setTime(trip_rs.getInt("time"));
				trip.setTraffic(trip_rs.getString("traffic"));
				trip.setHotel(trip_rs.getString("hotel"));
				trip.setGood_rate(trip_rs.getFloat("good_rate"));
				
				/* 封装并保存出发地对象 */
				int cityid = trip_rs.getInt("start_place");
				trip.setStart(cityDao.getCityById(cityid));
				
				/* 封装并保存行程类型对象 */
				int typeid = trip_rs.getInt("type");
				trip.setType(sequenceDao.getSequenceById(typeid));
				
				/* 封装并保存主题行程关系对象集合 */
				String themelist_sql = "SELECT * FROM ThemeOnTrip tt WHERE tt.trip = " + trip.getId();
				ps = con.prepareStatement(themelist_sql);
				themelist_rs = query(ps);
				List<ThemeOnTrip> ttlist = new ArrayList<>();
				while(themelist_rs.next()){
					ThemeOnTrip tt = new ThemeOnTrip();
					int tid = themelist_rs.getInt("theme");
					String theme_sql = "SELECT * FROM Theme WHERE id = " + tid;
					ps = con.prepareStatement(theme_sql);
					theme_rs = query(ps);
					Theme t = null;
					if(theme_rs.next()){
						t = new Theme();
						t.setName(theme_rs.getString("name"));
					}
					tt.setTheme(t);
					ttlist.add(tt);
				}
				trip.setThemeontrip_list(ttlist);
				
				/* 封装并保存景点行程关系对象集合 */
				String placelist_sql = "SELECT * FROM PlaceOnTrip pt WHERE pt.trip = " + trip.getId();
				ps = con.prepareStatement(placelist_sql);
				placelist_rs = query(ps);
				List<PlaceOnTrip> ptlist = new ArrayList<>();
				while(placelist_rs.next()){
					PlaceOnTrip pt = new PlaceOnTrip();
					int pid = placelist_rs.getInt("place");
					String place_sql = "SELECT * FROM Place WHERE id = " + pid;
					ps = con.prepareStatement(place_sql);
					place_rs = query(ps);
					Place p = null;
					if(place_rs.next()){
						p = new Place();
						p.setName(place_rs.getString("name"));
					}
					pt.setPlace(p);
					ptlist.add(pt);
				}
				trip.setPlaceontrip_list(ptlist);
				
				/* 封装并保存图片列表 */
				String pic_sql = "SELECT * FROM TripPicture tp WHERE tp.trip = "+trip.getId()+" AND tp.type = 0";
				ps = con.prepareStatement(pic_sql);
				picture_rs = query(ps);
				Set<TripPicture> tplist = new HashSet<>();
				if(picture_rs.next()){
					TripPicture tp = Packager.packTripPicture(picture_rs);
					tplist.add(tp);
				}
				trip.setPic_list(tplist);
				
				/* 封装并保存价格列表 */
				trip.setPrice_list(priceDao.getPricesByTripId(trip.getId()));
				
				// 把封装好的Trip对象存放到集合中
				trips.add(trip);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.close(picture_rs, place_rs, placelist_rs, theme_rs, themelist_rs);
			JDBCUtil.close(trip_rs, ps, con);
		}
		return trips;
	}

	@Override
	public Trip getTripById(int id) {
		String sql = "SELECT * FROM Trip WHERE id = " + id;
		
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet schedule_rs = null;
		ResultSet dates_rs = null;
		ResultSet pic_rs = null;
		ResultSet price_rs = null;
		ResultSet placelist_rs = null;
		ResultSet position_rs = null;
		ResultSet detail_rs = null;
		ResultSet rs = null;
		
		Trip trip = null;
		try {
			ps = con.prepareStatement(sql);
			rs = query(ps);
			
			if(rs.next()){
				trip = Packager.packTrip(rs);
				
				/* 封装并保存出发地对象 */
				City city = cityDao.getCityById(rs.getInt("start_place"));
				if(city != null)
					trip.setStart(city);
				
				/* 封装并保存行程类型对象 */
				Sequence seq = sequenceDao.getSequenceById(rs.getInt("type"));
				if(seq != null)
					trip.setType(seq);
				
				/* 封装行程的计划安排信息 */
				String schedule_sql = "SELECT s.*, t.go_point, t.go_time, t.return_point, t.return_time FROM Schedule s "
						+ "LEFT JOIN Traffic t on s.traffic = t.id "
						+ "WHERE s.trip = " + trip.getId();
				ps = con.prepareStatement(schedule_sql);
				schedule_rs = query(ps);
				if(schedule_rs.next()){
					Schedule schedule = new Schedule();
					schedule.setId(schedule_rs.getInt("id"));
					
					// 封装Traffic对象
					schedule.setTraffic(Packager.packTraffic(schedule_rs));
					
					// 封装日程集合
					String dates_sql = "SELECT * FROM Dates WHERE schedule = " + schedule.getId();
					ps = con.prepareStatement(dates_sql);
					dates_rs = query(ps);
					List<Dates> dlist = new ArrayList<>();
					while(dates_rs.next()){
						Dates d = Packager.packDate(dates_rs);
						dlist.add(d);
					}
					schedule.setDates(dlist);
					
					trip.setSchedule(schedule);
				}
				
				/* 封装并保存图片列表 */
				String pic_sql = "SELECT * FROM TripPicture tp WHERE tp.trip = "+id;
				ps = con.prepareStatement(pic_sql);
				pic_rs = query(ps);
				Set<TripPicture> tplist = new HashSet<>();
				while(pic_rs.next()){
					tplist.add(Packager.packTripPicture(pic_rs));
				}
				trip.setPic_list(tplist);
				
				/* 封装并保存价格列表 */
				String pri_sql = "SELECT * FROM Price p WHERE p.trip = "+trip.getId();
				ps = con.prepareStatement(pri_sql);
				price_rs = query(ps);
				List<Price> plist = new ArrayList<>();
				while(price_rs.next()){
					plist.add(Packager.packPrice(price_rs));
				}
				trip.setPrice_list(plist);
				
				/* 封装并保存景点行程关系对象集合 */
				String placelist_sql = "SELECT * FROM PlaceOnTrip pt WHERE pt.trip = " + trip.getId();
				ps = con.prepareStatement(placelist_sql);
				placelist_rs = query(ps);
				List<PlaceOnTrip> ptlist = new ArrayList<>();
				while(placelist_rs.next()){
					PlaceOnTrip pt = new PlaceOnTrip();
					int pid = placelist_rs.getInt("place");
					pt.setPlace(placeDao.getPlaceById(pid));
					ptlist.add(pt);
				}
				trip.setPlaceontrip_list(ptlist);
				
				/* 封装并保存坐标对象 */
				int position_id = rs.getInt("position");
				String position_sql = "SELECT * FROM Position WHERE id = " + position_id;
				ps = con.prepareStatement(position_sql);
				position_rs = query(ps);
				if(position_rs.next()){
					trip.setPosition(Packager.packPosition(position_rs));
				}
				
				/* 封装并保存详情细节对象 */
				String detail_sql = "SELECT * FROM Detail WHERE trip = " + trip.getId();
				ps = con.prepareStatement(detail_sql);
				detail_rs = query(ps);
				if(detail_rs.next()){
					trip.setDetail(Packager.packDetail(detail_rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.close(detail_rs, position_rs, placelist_rs, price_rs, pic_rs, dates_rs, schedule_rs);
			JDBCUtil.close(rs, ps, con);
		}
		
		return trip;
	}
	
	/********************** 提交评论事务的数据库操作【start】 *************************/
	@Override
	public void updateScore(Trip trip, Connection con) {
		String sql = "update trip set good_rate="+trip.getGood_rate()
				+ " and place_score="+trip.getPlace_score()
				+ " and hotel_score="+trip.getHotel_score()
				+ " and service_score="+trip.getService_score()
				+ " and traffic_score="+trip.getTraffic_score()
				+ " where id="+trip.getId();
		PreparedStatement stmt = null;
		try {
			try {
				stmt = con.prepareStatement(sql);
				stmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			JDBCUtil.close(stmt);
		}
	}
	/********************** 提交评论事务的数据库操作【end】 *************************/
	
	/**************************** 私有函数区域【start】 ****************************/
	/**
	 * 根据筛选表单内容来拼接sql查询语句
	 * @param vo 筛选表单
	 * @return 拼接好的sql语句
	 */
	private String getBaseSql(SearchForm vo) {
		
		String sql = "SELECT distinct t.*, (SELECT min(price) FROM Price WHERE trip=t.id) AS price_sort FROM Trip t,Price p WHERE t.id=p.trip AND t.is_ok=1";

		// 如果产品类型不为空，添加查询条件
		if (vo.getType_id() != null) {
			sql += " AND t.type=" + vo.getType_id();
		}

		// 如果出发地不为空，添加查询条件
		List<Integer> startList = vo.getStart_place_id_list();
		if (startList != null && !startList.isEmpty()) {
			sql += " AND (t.start_place=" + startList.get(0);
			for (int i = 1; i < startList.size(); i++) {
				sql += " OR t.start_place=" + startList.get(i);
			}
			sql += ")";
		}

		// 如果交通工具不为空，添加查询条件
		if (vo.getTraffic() != null) {
			sql += " AND t.traffic LIKE '%" + vo.getTraffic() + "%'";
		}

		// 如果出行天数不为空，添加查询条件
		if (vo.getTime() != null) {
			sql += " AND t.time=" + vo.getTime();
		}
		
		// 如果搜索关键字不为空，添加查询条件
		if (vo.getSearch_key() != null) {
			sql += " AND (t.id IN (" + "SELECT tot.trip FROM ThemeOnTrip tot WHERE tot.theme IN ("
					+ "SELECT th.id FROM Theme th WHERE th.name LIKE '%" + vo.getSearch_key() + "%'))" + "OR t.id IN ("
					+ "SELECT pot.trip FROM PlaceOnTrip pot WHERE pot.place IN ("
					+ "SELECT p.id FROM Place p WHERE p.name LIKE '%" + vo.getSearch_key() + "%'))" + "OR t.title LIKE '%"
					+ vo.getSearch_key() + "%' " + "OR t.s_title LIKE '%" + vo.getSearch_key() + "%')";
		}
		
		// 如果景点地区不为空，添加查询条件
		List<Integer> placeList = vo.getPlace_id_list();
		if (placeList != null && !placeList.isEmpty()) {
			sql += " AND t.id IN (SELECT pt.trip FROM PlaceOnTrip pt WHERE (pt.place=" + placeList.get(0);
			for (int i = 1; i < placeList.size(); i++) {
				sql += " OR pt.place=" + placeList.get(i);
			}
			sql += "))";
		}

		// 如果主题不为空，添加查询条件
		List<Integer> themeList = vo.getTheme_id_list();
		if (themeList != null && !themeList.isEmpty()) {
			sql += " AND t.id IN (SELECT tt.trip FROM ThemeOnTrip tt WHERE (tt.theme=" + themeList.get(0);
			for (int i = 1; i < themeList.size(); i++) {
				sql += " OR tt.theme=" + themeList.get(i);
			}
			sql += "))";
		}

		// 如果最低价格不为空，添加查询条件
		if (vo.getMin_price() != null) {
			sql += " AND t.id IN (SELECT p.trip FROM Price p where p.price>=" + vo.getMin_price() + ")";
		}

		// 如果最高价格不为空，添加查询条件
		if (vo.getMax_price() != null) {
			sql += " AND t.id IN (SELECT p.trip FROM Price p where p.price<=" + vo.getMax_price() + ")";
		}
		
		// 如果好评&价格排序都为空，采用默认排序规则
		if (vo.getGood_rate_sort() == null && vo.getPrice_sort() == null)
			sql += " ORDER BY t.good_rate desc,price_sort asc";

		// 当前以价格为主排序规则进行排序
		if (vo.getCur_sort_str() != null && "price".equals(vo.getCur_sort_str())) {
			if (vo.getGood_rate_sort() != null)
				sql += " ORDER BY price_sort " + vo.getPrice_sort() + ",t.good_rate " + vo.getGood_rate_sort();
			else
				sql += " ORDER BY price_sort " + vo.getPrice_sort() + ",t.good_rate desc";
		}

		// 当前以好评率为主排序规则进行排序
		if (vo.getCur_sort_str() != null && "comment".equals(vo.getCur_sort_str())) {
			if (vo.getPrice_sort() != null)
				sql += " ORDER BY t.good_rate " + vo.getGood_rate_sort() + ",price_sort " + vo.getPrice_sort();
			else
				sql += " ORDER BY t.good_rate " + vo.getGood_rate_sort() + ",price_sort asc";
		}

		return sql;
	}
	/**************************** 私有函数区域【end】 ****************************/
}
