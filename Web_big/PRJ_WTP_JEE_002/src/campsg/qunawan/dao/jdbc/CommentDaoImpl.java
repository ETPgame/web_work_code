package campsg.qunawan.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import campsg.qunawan.dao.CommentDao;
import campsg.qunawan.dao.CommentPicDao;
import campsg.qunawan.entity.Comment;
import campsg.qunawan.entity.Orders;
import campsg.qunawan.entity.Trip;
import campsg.qunawan.entity.User;
import campsg.qunawan.utils.Packager;

@Repository("commentDao")
public class CommentDaoImpl extends JDBCBase implements CommentDao {
	
	@Autowired
	private CommentPicDao commentPicDao;
	
	@Override
	public Integer updateUseful(Integer commentId, Boolean isUseful) {
		/**
		 *  获取需要更新的数字
		 */
		int num = getUsefullNum(commentId, isUseful) + 1;
		
		// 根据用户点击的内容拼接更新语句
		String sql = "update comment ";
		if (isUseful) {
			sql += " set usefull = " + num;
		} else {
			sql += " set useless = " + num;
		}
		sql += " where id = " + commentId;

		saveOrUpdateOrDelete(sql, null);
		return num;
	}

	@Override
	public Integer getCommentCount(Integer tripId) {
		String sql = "select count(*) from comment " + "where trip="+tripId;
		return getCount(sql);
	}

	@Override
	public List<Comment> getCommentsPerPage(Integer userId, Integer sq_id, Integer page, Integer max) {
		String sql = "select com.id,o.id as orders,o.orderNo,com.content,com.time,com.hotel,com.place,"
				+ "com.service,com.traffic,com.usefull,com.useless,t.title "
				+ "from comment com left outer join orders o on com.orders=o.id "
				+ "left outer join trip t on com.trip=t.id "
				+ "where o.user="+userId+" and o.state="+sq_id+" order by com.time desc";		
		int firstResult = (page - 1) * max;
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Comment> comments = new ArrayList<>();
		try {
			/**
			 * 	1.TYPE_FORWORD_ONLY,只可向前滚动；
			 * 	2.TYPE_SCROLL_INSENSITIVE,双向滚动，但不及时更新，就是如果数据库里的数据修改过，并不在ResultSet中反应出来。
			 *	3.TYPE_SCROLL_SENSITIVE，双向滚动，并及时跟踪数据库的更新,以便更改ResultSet中的数据。 
			 */
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = query(ps, firstResult, max);
			while(rs.next()){
				Comment comment = Packager.packComment(rs);
				
				Orders order = new Orders(rs.getInt("orders"));
				order.setOrderNo(rs.getString("orderNo"));
				comment.setOrders(order);
				
				Trip trip = new Trip();
				trip.setTitle(rs.getString("title"));
				comment.setTrip(trip);

				comments.add(comment);
			}
			for(Comment c : comments){
				c.setPictures(commentPicDao.getCommentPics(c.getId()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.close(rs, ps, con);
		}
		return comments;
	}

	@Override
	public List<Comment> getCommentsByTripId(Integer tripId, Integer page, Integer max) {
		String sql = "select c.id,c.trip,c.place,c.hotel,c.service,c.traffic,c.content,c.time,c.usefull,c.useless,u.name "
				+ "from comment c,user u  where c.user=u.id and c.trip ="+tripId+" order by c.time desc";
		int firstResult = (page - 1) * max;
		
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Comment> comments = new ArrayList<>();
		try {
			/**
			 * 	1.TYPE_FORWORD_ONLY,只可向前滚动；
			 * 	2.TYPE_SCROLL_INSENSITIVE,双向滚动，但不及时更新，就是如果数据库里的数据修改过，并不在ResultSet中反应出来。
			 *	3.TYPE_SCROLL_SENSITIVE，双向滚动，并及时跟踪数据库的更新,以便更改ResultSet中的数据。 
			 */
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = query(ps, firstResult, max);
			while(rs.next()){
				Comment comment = Packager.packComment(rs);
				
				User user = new User();
				user.setName(rs.getString("name"));
				comment.setUser(user);
				
				comments.add(comment);
				for(Comment c : comments){
					c.setPictures(commentPicDao.getCommentPics(c.getId()));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.close(rs, ps, con);
		}
		return comments;
	}

	@Override
	public Integer getCommentedCountByUser(Integer userId) {
		String sql = "select count(*) from comment where user="+userId;
		return getCount(sql);
	}

	@Override
	public Integer getCommentedCountByTrip(Integer tripId) {
		String sql = "select count(*) from comment where trip="+tripId;
		return getCount(sql);
	}

	/**
	 * 获取对应评论的点赞或点踩数 
	 */
	private Integer getUsefullNum(Integer commentId, Boolean isUseful) {
		String type = null;
		if(isUseful)
			type = "usefull";
		else
			type= "useless";
		String sql = "select "+type+" from comment where id=" + commentId;
		return getCount(sql);
	}
	
	/********************** 提交评论事务的数据库操作【start】 *************************/
	@Override
	public Integer save(Comment com, Connection con) {
		String sql = "insert into comment(user,trip,orders,place,hotel,service,traffic,content,time) "
				+ "values(?,?,?,?,?,?,?,?,?)";
		Object[] params = {com.getUser().getId(),com.getTrip().getId(),com.getOrders().getId(),
				com.getPlace(),com.getHotel(),com.getService(),com.getTraffic(),com.getContent(),
				com.getTime()};
		return save(sql, params, con);
	}
	
	@Override
	public Float getAvg_Score(String type, Integer tripId, Connection con) {
		String sql = "select avg(c." + type + ") from comment c where c.trip=" + tripId;
		PreparedStatement ps = null;
		ResultSet rs = null;

		float count = 0;
		try {
			/**
			 * 	1.TYPE_FORWORD_ONLY,只可向前滚动；
			 * 	2.TYPE_SCROLL_INSENSITIVE,双向滚动，但不及时更新，就是如果数据库里的数据修改过，并不在ResultSet中反应出来。
			 *	3.TYPE_SCROLL_SENSITIVE，双向滚动，并及时跟踪数据库的更新,以便更改ResultSet中的数据。 
			 */
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = query(ps);
			if (rs.next()) {
				count = rs.getFloat(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.close(ps);
			JDBCUtil.close(rs);
		}
		return count;
	}

	@Override
	public List<Float[]> getCountByAvg(Integer tripId, Connection con) {
		String sql = "select (c.place+c.hotel+c.service+c.traffic)/4, COUNT(*) " + "from Comment c "
				+ "where c.trip="+ tripId + " group by (c.place+c.hotel+c.service+c.traffic)/4";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Float[]> results = new ArrayList<>();
		try {
			/**
			 * 	1.TYPE_FORWORD_ONLY,只可向前滚动；
			 * 	2.TYPE_SCROLL_INSENSITIVE,双向滚动，但不及时更新，就是如果数据库里的数据修改过，并不在ResultSet中反应出来。
			 *	3.TYPE_SCROLL_SENSITIVE，双向滚动，并及时跟踪数据库的更新,以便更改ResultSet中的数据。 
			 */
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = query(ps);
			while(rs.next()){
				results.add(new Float[]{rs.getFloat(1),rs.getFloat(2)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.close(ps);
			JDBCUtil.close(rs);
		}
		return results;
	}
	/********************** 提交评论事务的数据库操作【end】 *************************/
}
