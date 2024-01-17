package campsg.qunawan.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import campsg.qunawan.dao.PlaceDao;
import campsg.qunawan.dao.ThemeDao;
import campsg.qunawan.dao.TripDao;
import campsg.qunawan.dao.jdbc.PlaceDaoImpl;
import campsg.qunawan.dao.jdbc.ThemeDaoImpl;
import campsg.qunawan.dao.jdbc.TripDaoImpl;
import campsg.qunawan.entity.PlaceOnTrip;
import campsg.qunawan.entity.Trip;

public class IndexAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private TripDao tripDao = new TripDaoImpl();

	private PlaceDao placeDao = new PlaceDaoImpl();

	private ThemeDao themeDao = new ThemeDaoImpl();
	
	private static int TYPE_CHINESE = 2;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/** PRJ-WTP-JEE-008：首页信息显示 - 自驾游数据获取 【Start】 **/
		List<Trip> trips = tripDao.getPageTripsByType(TYPE_CHINESE, 0, 6);

		List<String> places = placeDao.getPagePlacesByType(TYPE_CHINESE, 0, 10);

		List<String> themes = themeDao.getPageThemesByType(TYPE_CHINESE, 0, 10);

		request.setAttribute("trips", trips);
		request.setAttribute("places", places);
		request.setAttribute("themes", themes);

		System.out.println("行程个数: " + trips.size());
		System.out.println("景点名称个数: " + places.size());
		System.out.println("主题名称个数: " + themes.size());

		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);


		/** PRJ-WTP-JEE-008：首页信息显示 - 自驾游数据获取 【End】 **/
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		doGet(request, response);
	}

}
