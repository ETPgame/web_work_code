package campsg.qunawan.service.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import campsg.qunawan.dao.CommentDao;
import campsg.qunawan.dao.CommentPicDao;
import campsg.qunawan.dao.OrdersDao;
import campsg.qunawan.dao.TripDao;
import campsg.qunawan.dao.jdbc.JDBCUtil;
import campsg.qunawan.entity.Comment;
import campsg.qunawan.entity.CommentPicture;
import campsg.qunawan.entity.Sequence;
import campsg.qunawan.service.CommentService;
import campsg.qunawan.utils.Utils;

/**
 * 评论信息的服务类
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private CommentPicDao commentPicDao;
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private TripDao tripDao;
	
	@Override
	public void initCommentPicture(Set<CommentPicture> pictures, String basePath) {
		if (pictures == null || pictures.size() == 0) {
			return;
		}
		for (CommentPicture cp : pictures) {
			String path = basePath + "image_cache\\" + cp.getName();
			if (!new File(path).exists()) {
				Utils.getFile(cp.getData(), path);
			}
		}
	}

	@Override
	public void saveComment(Comment comment, List<byte[]> byteArray, Sequence sq_commented) {
		Connection con = JDBCUtil.getConnection();// 获取连接对象
		
		try {
			// 设置事务的提交方式为非自动提交：
			con.setAutoCommit(false);
			/**
			 * 1、存储评论对象，获取对应id
			 */
			int com_id = commentDao.save(comment, con);
			
			/**
			 * 2、存储评论图片集
			 */
			if(byteArray != null){
				for (byte[] bs : byteArray) {
					CommentPicture comPic = new CommentPicture();
					comPic.setComment(new Comment(com_id));
					comPic.setData(bs);
					comPic.setName(createName() + ".jpg");
					commentPicDao.save(comPic, con);
				}
			}
			
			/**
			 * 3、更新行程评分
			 */
			//评论对应行程id
			int trip_id = comment.getOrders().getTrip().getId();
			//五星评论数（好评数）
			float good_count=0;
			//获取该产品的各星级对应的评论数列表
			List<Float[]> data = commentDao.getCountByAvg(trip_id, con);
			//总评论数，如果没有则设置为1，防止分母为0的异常
			float total_count=data.size()==0?1:data.size();
			for(Float[] avg_score : data){
				//取星级近似值
				int star = Math.round(avg_score[0]);
				if(star == 5)
					good_count += avg_score[1];
			}
			//计算好评率
			float good_rate = (float)good_count/total_count*100;
			
			float place_score = commentDao.getAvg_Score("place", trip_id, con);
			float hotel_score = commentDao.getAvg_Score("hotel", trip_id, con);
			float service_score = commentDao.getAvg_Score("service", trip_id, con);
			float traffic_score = commentDao.getAvg_Score("traffic", trip_id, con);
			
			comment.getTrip().setGood_rate(good_rate);
			comment.getTrip().setPlace_score(place_score);
			comment.getTrip().setService_score(service_score);
			comment.getTrip().setHotel_score(hotel_score);
			comment.getTrip().setTraffic_score(traffic_score);					
			tripDao.updateScore(comment.getTrip(), con);
			
			/**
			 * 4、更新订单状态
			 */
			ordersDao.updateOrderState(sq_commented, comment.getOrders().getId(),con);			
			// 在try块内添加事务的提交操作，表示操作无异常，提交事务。
			con.commit();
		} catch (SQLException e) {
			try {
				// .在catch块内添加回滚事务，表示操作出现异常，撤销事务：
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				// 设置事务提交方式为自动提交：
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JDBCUtil.close(con);
		}
	}

	private String createName() {
		return (int) (Math.random() * 10241314) + "" + (int) (Math.random() * 10241314);
	}
}
