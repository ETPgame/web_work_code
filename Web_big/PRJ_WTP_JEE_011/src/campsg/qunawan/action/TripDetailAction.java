package campsg.qunawan.action;

import campsg.qunawan.dao.jdbc.TripDaoImpl;
import campsg.qunawan.entity.Trip;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 旅游产品详情的相关业务
 */
public class TripDetailAction extends HttpServlet {

	private static final long serialVersionUID = -5269537219643513765L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 1. 从首页传递的产品ID
		String productId = request.getParameter("id");
		System.out.println("从首页传递的产品ID: " + productId);

		try {
			// 2. 处理产品ID的非法情况
			int productIdInt = Integer.parseInt(productId);

			// 3. 按获取的产品ID查询旅游产品数据
			TripDaoImpl tripDao = new TripDaoImpl();
			Trip trip = tripDao.getTripById(productIdInt);

			// 4. 将查询所得的旅游产品对象存入Request中，key设置为：trip
			request.setAttribute("trip", trip);

			// 6. 在控制台中输出获取的产品ID和查询所得的旅游产品数据
			System.out.println("查询所得的旅游产品数据：" + trip);

			// 5. 跳转并显示产品详情页：trip_detail.jsp
			request.getRequestDispatcher("/WEB-INF/pages/trip_detail.jsp").forward(request, response);

		} catch (NumberFormatException e) {
			// 处理非法产品ID，客户端重定向到首页的Servlet
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
