package campsg.qunawan.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import campsg.qunawan.dao.JDBCManager;
import campsg.qunawan.dao.UserDao;
import campsg.qunawan.entity.User;

public class UserDaoImpl implements UserDao {
	
	@Override
	public User getUserByCondition(String condition) {
		Connection con = JDBCManager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		
		String sql = "SELECT * FROM User u WHERE u.phone = ? or u.email = ?";
		try {
			ps = con.prepareStatement(sql);
			ps.setObject(1, condition);
			ps.setObject(2, condition);
			rs = ps.executeQuery();
			if(rs.next()){
				user = new User();
				user.setId(rs.getInt("id"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setName(rs.getString("name"));
				user.setSex(rs.getBoolean("sex"));
				user.setImg_path(rs.getString("img_path"));
				user.setEmail(rs.getString("email"));
				user.setReal_name(rs.getString("real_name"));
				user.setBirthday(rs.getDate("birthday"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCManager.close(rs, ps, con);
		}
		return user;
	}

}