package campsg.qunawan.action;

import campsg.qunawan.dao.TripDao;
import campsg.qunawan.dao.jdbc.TripDaoImpl;
import campsg.qunawan.entity.Trip;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	TripDao tripDao=new TripDaoImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/** PRJ_WTP_JEE_010：检索页信息显示 - 检索的请求与响应 【Start】 **/
		request.setCharacterEncoding("utf-8");
		String key=request.getParameter("key");
		String pageStr=request.getParameter("page");
		int page=1;

		try {
			page=Integer.parseInt(pageStr);
		}catch (NumberFormatException e){
			e.printStackTrace();
		}

		// 添加打印测试语句
		System.out.println("检索关键字: " + key);
		System.out.println("当前页数: " + page);

		int	count=tripDao.getTripNum(key);
		System.out.println("总记录数: " + count);

		// 调用 TripDaolmpl 的 getTripByCondition 方法，获取旅游产品集合
		List<Trip> trips = tripDao.getTripByCondition(key, page);

		// 将产品的记录数、旅游产品集合和翻页页码存入 Request
		request.setAttribute("count", count);
		request.setAttribute("trips", trips);
		request.setAttribute("page", page);

		for (Trip trip : trips) {
			System.out.println(trip);
		}

		request.getRequestDispatcher("/WEB-INF/pages/search.jsp").forward(request,response);







		/** PRJ_WTP_JEE_010：检索页信息显示 - 检索的请求与响应 【End】 **/
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
