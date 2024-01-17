package campsg.qunawan.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import campsg.qunawan.dao.TripDao;
import campsg.qunawan.dao.jdbc.TripDaoImpl;
import campsg.qunawan.entity.Trip;
import campsg.qunawan.utils.Utils;

/**
 * 旅游产品详情的相关业务
 */

public class TripDetailAction extends HttpServlet {

	private static final long serialVersionUID = -5269537219643513765L;

	private TripDao tripDao = new TripDaoImpl();


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String type = request.getParameter("type");
		String tmp_id = request.getParameter("id");

		// 如果参数不合法，则跳转到首页
		if (tmp_id == null || type == null)
			request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);

		Integer id = Integer.parseInt(tmp_id);

		// 产品详情页初始化
		if (type.equals("init")) {
			// 通过产品id获得该产品对象
			Trip trip = tripDao.getTripById(id);
			// 初始化产品画廊的图片并加载到缓存
			Utils.initTripPicture(trip.getPic_list(),
					this.getServletConfig().getServletContext().getRealPath("/"));

			// 把产品对象存到session中
			request.setAttribute("trip", trip);

			request.getRequestDispatcher("/WEB-INF/pages/trip_detail.jsp").forward(request, response);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
