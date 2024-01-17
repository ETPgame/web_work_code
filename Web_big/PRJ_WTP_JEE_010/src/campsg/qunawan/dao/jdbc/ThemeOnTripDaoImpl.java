package campsg.qunawan.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import campsg.qunawan.dao.ThemeOnTripDao;
import campsg.qunawan.entity.Theme;
import campsg.qunawan.entity.ThemeOnTrip;


public class ThemeOnTripDaoImpl extends JDBCBase implements ThemeOnTripDao {

	@Override
	public List<ThemeOnTrip> getThemesByTripId(Integer tripId) {
		Connection con = JDBCUtil.getConnection();
		List<ThemeOnTrip> tot_list = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet themelist_rs = null;
		ResultSet theme_rs = null;
		String tot_sql = "SELECT * FROM ThemeOnTrip tt WHERE tt.trip = " + tripId;
		try {
			ps = con.prepareStatement(tot_sql);
			themelist_rs = ps.executeQuery();
			while (themelist_rs.next()) {
				ThemeOnTrip tot = new ThemeOnTrip();
				int tid = themelist_rs.getInt("theme");
				String theme_sql = "SELECT * FROM Theme WHERE id = " + tid;
				ps = con.prepareStatement(theme_sql);
				try {
					theme_rs = ps.executeQuery();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				Theme theme = null;
				if (theme_rs.next()) {
					theme = new Theme();
					theme.setName(theme_rs.getString("name"));
				}
				tot.setTheme(theme);
				tot_list.add(tot);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.close(theme_rs, ps, con);
			JDBCUtil.close(themelist_rs);
		}
		return tot_list;
	}
	
}
