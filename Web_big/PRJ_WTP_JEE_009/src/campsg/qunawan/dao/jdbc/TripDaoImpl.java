package campsg.qunawan.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import campsg.qunawan.dao.TripDao;
import campsg.qunawan.entity.Trip;

public class TripDaoImpl implements TripDao {


	@Override
	public Integer getTripNum(String key) {
		System.out.println("getTripNum key :"+key);
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String sql="select count(*) from Trip where 1=1";

		try {
			connection=JDBCManager.getConnection();
			if (key != null && !"".equals(key)) {
				sql+=" and title like ?";
			}

			preparedStatement=connection.prepareStatement(sql);
			if (key != null && !"".equals(key)) {
				preparedStatement.setString(1,"%"+key+"%");
			}

			resultSet=preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getInt(1);
			}


		} catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
			JDBCManager.close(connection);
			JDBCManager.close(resultSet);
			JDBCManager.close(preparedStatement);
		}

        return 0;
	}

	@Override
	public List<Trip> getTripByCondition(String key, Integer page) {
		String sql = "SELECT * FROM Trip WHERE 1=1";
		if (key != null && !key.equals(""))
			sql += " and title LIKE '%" + key + "%'";

		int max = 10;
		int start = (page - 1) * max;

		Connection con = JDBCManager.getConnection();
		List<Trip> trips = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// 在 SQL 查询中包含 id 字段
			sql += " ORDER BY id LIMIT ?, ?";

			ps = con.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, max);

			rs = ps.executeQuery();
			while (rs.next()) {
				Trip trip = new Trip();
				trip.setId(rs.getInt("id"));
				trip.setTitle(rs.getString("title"));
				trip.setTime(rs.getInt("time"));
				trip.setTraffic(rs.getString("traffic"));
				trip.setHotel(rs.getString("hotel"));
				trip.setGood_rate(rs.getFloat("good_rate"));

				// 把封装好的Trip对象存放到集合中
				trips.add(trip);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCManager.close(rs);
			JDBCManager.close(ps);
			JDBCManager.close(con);
		}
		return trips;

	}
}
