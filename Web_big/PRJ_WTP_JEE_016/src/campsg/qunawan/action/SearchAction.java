package campsg.qunawan.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import campsg.qunawan.dao.TripDao;
import campsg.qunawan.dao.jdbc.TripDaoImpl;
import campsg.qunawan.entity.Trip;

public class SearchAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private TripDao tripDao = new TripDaoImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pageStr = request.getParameter("page"); // 当前页码
		String key = (String) request.getParameter("key"); // 搜索关键字
		if(key == null)
			key = "";
		
		Integer page;
		try {
			page = Integer.parseInt(pageStr);
		} catch (Exception e) {
			page = 1;
		}

		System.out.println("你输入的关键字是："+key);
		
		// 获取搜索结果数
		Integer count = tripDao.getTripNum(key);
		// 获取搜索页面显示的行程列表
		List<Trip> trips = tripDao.getTripByCondition(key, page);
		request.setAttribute("key", key);
		request.setAttribute("pageCurrent", page);
		request.setAttribute("count", count);
		request.setAttribute("trips", trips);
		request.setAttribute("pageTotal", (int)Math.ceil(count/10.0));
		
		request.getRequestDispatcher("/WEB-INF/pages/search.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
