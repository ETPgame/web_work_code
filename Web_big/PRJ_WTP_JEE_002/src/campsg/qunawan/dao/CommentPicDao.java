package campsg.qunawan.dao;

import java.sql.Connection;
import java.util.Set;

import campsg.qunawan.entity.CommentPicture;

public interface CommentPicDao {

	/**
	 * 存储评论图片 
	 */
	Integer save(CommentPicture cp, Connection con);
	
	/**
	 * 根据评论id获取评论图集
	 */
	Set<CommentPicture> getCommentPics(Integer commentId);
}
