package campsg.qunawan.action;

import campsg.qunawan.dao.TripDao;
import campsg.qunawan.dao.jdbc.JDBCManager;
import campsg.qunawan.dao.jdbc.TripDaoImpl;
import campsg.qunawan.entity.Trip;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		request.setCharacterEncoding("GBK");
//		response.setCharacterEncoding("GBK");

		System.out.println("执行SearchAction方法");
		// 获取数据库连接对象
		Connection connection = JDBCManager.getConnection();

		// 输出连接对象到控制台
		System.out.println("Database Connection: " + connection);

		TripDao tripDao = new TripDaoImpl();

		// 获取全部记录数
		int totalNum = tripDao.getTripNum(null);
		System.out.println("《去哪玩》旅游网共包含: " + totalNum + "个旅游产品(行程)");

		// 获取含有关键字的记录数
		int numWithKeyword = tripDao.getTripNum("上海");
		System.out.println("《去哪玩》旅游网共包含: " + numWithKeyword + "个含[上海]关键字的旅游产品(行程)");

		System.out.println("显示含[上海] 关键字的，第1页旅游产品");
		List<Trip> trips = tripDao.getTripByCondition("上海", 1);
		for (Trip trip : trips) {
			System.out.println(trip);
		}


		try {
			// 关闭数据库连接对象
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
