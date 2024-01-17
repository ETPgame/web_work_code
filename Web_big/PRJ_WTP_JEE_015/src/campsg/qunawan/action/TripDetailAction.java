package campsg.qunawan.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import campsg.qunawan.dao.TripDao;
import campsg.qunawan.dao.jdbc.TripDaoImpl;
import campsg.qunawan.entity.Trip;

/**
 * 旅游产品详情的相关业务
 */
public class TripDetailAction extends HttpServlet {

	private static final long serialVersionUID = -5269537219643513765L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tmp_id = request.getParameter("id");
		Integer id = -1;
		try {
			id = Integer.parseInt(tmp_id);
		} catch (NumberFormatException e) {
			// 如果参数不合法，则跳转到首页
			response.sendRedirect("index.jhtml");
			return;
		}
		TripDao tripDao = new TripDaoImpl();
		// 通过产品id获得该产品对象
		Trip trip = tripDao.getTripById(id);
		// 把产品对象存到session中
		request.setAttribute("trip", trip);

		System.out.println("从首页传递的产品ID:" + id);
		System.out.println(trip);
		request.getRequestDispatcher("/WEB-INF/pages/trip_detail.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
