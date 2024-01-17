package campsg.qunawan.service;

import java.util.List;
import java.util.Set;

import campsg.qunawan.entity.Comment;
import campsg.qunawan.entity.CommentPicture;
import campsg.qunawan.entity.Sequence;

/**
 * 评论服务接口类
 */
public interface CommentService {

	/**
	 * 添加新评论及评论图片
	 */
	void saveComment(Comment comment, List<byte[]> byteArray, Sequence sq_commented);

	/**
	 * 检查是否存在评论的缓存图片，没如果不存在则创建图片
	 * @param pictures
	 * @param basePath
	 */
	void initCommentPicture(Set<CommentPicture> pictures, String basePath);

}
