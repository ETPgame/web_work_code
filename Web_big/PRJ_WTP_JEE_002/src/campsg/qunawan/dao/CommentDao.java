package campsg.qunawan.dao;

import java.sql.Connection;
import java.util.List;

import campsg.qunawan.entity.Comment;

public interface CommentDao {

	/**
	 * 获取某用户已点评的评论集
	 * @param userId 用户id
	 * @param sq_id 订单状态序列id
	 * @param firstResult 本页起始位置
	 * @return 评论集合
	 */
	List<Comment> getCommentsPerPage(Integer userId, Integer sq_id, Integer page, Integer max);
	
	/**
	 * 获取产品集合的长度
	 * @param tripId 产品id
	 * @return 集合长度
	 */
	Integer getCommentCount(Integer tripId);
	
	/**
	 * 更新用户点赞信息
	 * @param commentId 评论id
	 * @param isUseful 是否点赞
	 * @return 更新赞/踩的个数
	 */
	Integer updateUseful(Integer commentId, Boolean isUseful);

	/**
	 * 行程单项平均分
	 * type：评论评分的属性名称 
	 */
	Float getAvg_Score(String type, Integer tripId, Connection con) ;

	/**
	 * 按照【平均分】-【评论个数】的方式查询评论数据
	 */
	List<Float[]> getCountByAvg(Integer tripId, Connection con) ;
	
	/**
	 * 根据tripid分页查询Comment【根据详情页需求，属性部分封装，详情见Packager】
	 */
	List<Comment> getCommentsByTripId(Integer tripId, Integer page, Integer max);
	
	/**
	 * 获取用户的评论数
	 */
	Integer getCommentedCountByUser(Integer userId);
	
	/**
	 * 获取行程的评论数
	 */
	Integer getCommentedCountByTrip(Integer tripId);
	
	/**
	 * 存储评论对象，返回主键
	 */
	Integer save(Comment com, Connection con);
}
