package campsg.qunawan.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import campsg.qunawan.dao.OrdersDao;
import campsg.qunawan.dao.SequenceDao;
import campsg.qunawan.dao.TripDao;
import campsg.qunawan.dao.UserDao;
import campsg.qunawan.entity.Orders;
import campsg.qunawan.entity.Sequence;
import campsg.qunawan.entity.Trip;
import campsg.qunawan.entity.User;
import campsg.qunawan.utils.Packager;

@Component
@Repository("ordersDao")
public class OrdersDaoImpl extends JDBCBase implements OrdersDao {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SequenceDao sequenceDao;
	
	@Autowired
	private TripDao tripDao;
	
	@Override
	public List<Orders> getOrders(int userId, Sequence sq, int page, int max) {
		String sql = "SELECT * FROM Orders o "
				+ "LEFT JOIN User u ON o.user = u.id "
				+ "LEFT JOIN Trip t ON o.trip = t.id "
				+ "WHERE o.user = " + userId + " AND o.state = " + sq.getId();
		int firstResult = (page - 1) * max;
		return queryOrders(firstResult, sql, max);
	}
	
	@Override
	public List<Orders> getOrders(int userId, int page, int max) {
		String sql = "SELECT * FROM Orders o"
				+ " WHERE o.user = " + userId
				+ " ORDER BY o.create_time desc";
		int firstResult = (page - 1) * max;
		return queryOrders(firstResult, sql, max);
	}

	@Override
	public int getOrdersbyUser(Integer userId) {
		String sql = "SELECT count(*) FROM Orders WHERE user = " + userId;
		return getCount(sql);
	}

	@Override
	public int getOrdersbyUser(Integer userId, Sequence sq) {
		String sql = "SELECT count(*) FROM Orders WHERE user = " + userId + " AND state = " + sq.getId();
		return getCount(sql);
	}

	@Override
	public Orders getOrderById(int id) {
		String sql = "SELECT * FROM Orders WHERE id = " + id;
		
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Orders order = null;
		try {
			ps = con.prepareStatement(sql);
			rs = query(ps);
			
			if(rs.next()){
				order = Packager.packOrder(rs);
				
				// 封装订单中的行程对象
				int tid = rs.getInt("trip");
				Trip trip = tripDao.getTripById(tid);
				order.setTrip(trip);
				
				// 封装订单的购买人对象
				int uid = rs.getInt("user");
				User user = userDao.getUserById(uid);
				order.setUser(user);
				
				// 封装订单的状态对象
				int sid = rs.getInt("state");
				Sequence seq = sequenceDao.getSequenceById(sid);
				order.setState(seq);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.close(rs, ps, con);
		}
		return order;
	}
	
	@Override
	public void updateOrderState(Sequence state, Integer id) {
		String sql = "update orders set "
				+ "state = ? "
				+ "WHERE id = ?";
		Object[] param = {state.getId(), id};
		save(sql, param);
	}
	
	/********************** 下单事务的数据库操作【start】 *************************/
	@Override
	public void updateOrderState(Sequence state, Integer id, Connection con) {
		String sql = "update orders set "
				+ "state = ? "
				+ "WHERE id = ?";
		Object[] param = {state.getId(), id};
		save(sql, param,con);
	}
	
	@Override
	public int savaOrder(Connection con, Orders order) {
		String sql = "INSERT INTO Orders(orderNo,trip,user,num,start_time,create_time,go_point,go_time,total_price,state) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
		Object[] param = {
				order.getOrderNo(), 
				order.getTrip().getId(),
				order.getUser().getId(),
				order.getNum(),
				order.getStart_time(),
				order.getCreate_time(),
				order.getGo_point(),
				order.getGo_time(),
				order.getTotal_price(),
				order.getState().getId()};
		return save(sql, param, con);
	}
	/********************** 下单事务的数据库操作【end】 *************************/
	

	/**************************** 私有函数区域【start】 ****************************/
	private List<Orders> queryOrders(int firstResult, String sql, int max) {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Orders> orders = new ArrayList<>();
		try {
			ps = con.prepareStatement(sql);
			rs = query(ps, firstResult, max);
			
			while(rs.next()){
				Orders order = Packager.packOrder(rs);
				
				// 封装订单中的行程对象
				int tid = rs.getInt("trip");
				Trip trip = tripDao.getTripById(tid);
				order.setTrip(trip);
				
				// 封装订单的购买人对象
				int uid = rs.getInt("user");
				User user = userDao.getUserById(uid);
				order.setUser(user);
				
				// 封装订单的状态对象
				int sid = rs.getInt("state");
				Sequence seq = sequenceDao.getSequenceById(sid);
				order.setState(seq);
				
				orders.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.close(rs, ps, con);
		}
		return orders;
	}
	/**************************** 私有函数区域【end】 ****************************/

}
