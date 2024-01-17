package campsg.qunawan.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

import campsg.qunawan.dao.CommentPicDao;
import campsg.qunawan.entity.CommentPicture;
import campsg.qunawan.utils.Packager;

@Repository("commentPicDao")
public class CommentPicDaoImpl extends JDBCBase implements CommentPicDao {
	
	@Override
	public Integer save(CommentPicture cp, Connection con) {
		String sql = "insert into commentpicture(comment,data,name) "
				+ "values(?,?,?)";
		Object[] params = {cp.getComment().getId(),cp.getData(),cp.getName()};
		return save(sql, params, con);
	}

	/**
	 * 根据评论id获取评论图集
	 */
	@Override
	public Set<CommentPicture> getCommentPics(Integer commentId) {
		String sql = "select * from commentpicture where comment="+commentId;
		
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Set<CommentPicture> cp = new HashSet<>();
		try {
			/**
			 * 	1.TYPE_FORWORD_ONLY,只可向前滚动；
			 * 	2.TYPE_SCROLL_INSENSITIVE,双向滚动，但不及时更新，就是如果数据库里的数据修改过，并不在ResultSet中反应出来。
			 *	3.TYPE_SCROLL_SENSITIVE，双向滚动，并及时跟踪数据库的更新,以便更改ResultSet中的数据。 
			 */
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = query(ps);
			while(rs.next()){
				cp.add(Packager.packCommentPicture(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.close(rs, ps, con);
		}
		return cp;
	}

}
