package campsg.qunawan.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import campsg.qunawan.dao.PlaceDao;
import campsg.qunawan.dao.SequenceDao;
import campsg.qunawan.dao.ThemeDao;
import campsg.qunawan.dao.TripDao;
import campsg.qunawan.dao.jdbc.PlaceDaoImpl;
import campsg.qunawan.dao.jdbc.SequenceDaoImpl;
import campsg.qunawan.dao.jdbc.ThemeDaoImpl;
import campsg.qunawan.dao.jdbc.TripDaoImpl;
import campsg.qunawan.entity.Trip;
import campsg.qunawan.globle.Constants;

public class IndexAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// 行程类型字符串数组，依次为 {自驾游、国内游和出境游}
	private String[] tripTypes = { "国内游","出境游","自驾游" };

	private SequenceDao sequenceDao = new SequenceDaoImpl();

	private TripDao tripDao = new TripDaoImpl();

	private PlaceDao placeDao = new PlaceDaoImpl();

	private ThemeDao themeDao = new ThemeDaoImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 显示的行程列表
		List<Trip> trips = new ArrayList<Trip>();
		// 页面显示的景点列表
		List<String> places = new ArrayList<String>();
		// 页面显示的主题列表
		List<String> themes = new ArrayList<String>();

		/**
		 * 这里只获取国内游的数据
		 */
		int typeId = sequenceDao.getSeqByValue(tripTypes[0]).getId();
		trips = tripDao.getPageTripsByType(typeId, 0, 6);
		places = placeDao.getPagePlacesByType(typeId, 0, 10);
		themes = themeDao.getPageThemesByType(typeId, 0, 10);

		request.setAttribute(Constants.INDEX_TRIP, trips);
		request.setAttribute(Constants.INDEX_PLACE, places);
		request.setAttribute(Constants.INDEX_THEME, themes);


		request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		doGet(request, response);
	}

}
