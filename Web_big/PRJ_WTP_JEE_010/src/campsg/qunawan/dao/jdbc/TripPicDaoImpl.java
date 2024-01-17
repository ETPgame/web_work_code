package campsg.qunawan.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import campsg.qunawan.dao.TripPicDao;
import campsg.qunawan.entity.TripPicture;

public class TripPicDaoImpl extends JDBCBase implements TripPicDao {

	@Override
	public Set<TripPicture> getTripPicsByTripId(Integer tripId) {
		Connection con = JDBCUtil.getConnection();
		Set<TripPicture> tplist = new HashSet<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String pic_sql = "SELECT * FROM TripPicture tp WHERE tp.trip = " + tripId;
		try {
			ps = con.prepareStatement(pic_sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				TripPicture tp = new TripPicture();
				tp.setName(rs.getString("name"));
				tplist.add(tp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.close(rs, ps, con);
		}
		return tplist;
	}
}
