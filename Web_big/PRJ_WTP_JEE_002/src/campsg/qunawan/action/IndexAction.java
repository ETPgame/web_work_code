package campsg.qunawan.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import campsg.qunawan.dao.PlaceDao;
import campsg.qunawan.dao.SequenceDao;
import campsg.qunawan.dao.ThemeDao;
import campsg.qunawan.dao.TripDao;
import campsg.qunawan.entity.Trip;
import campsg.qunawan.entity.User;
import campsg.qunawan.globle.Constants;
import campsg.qunawan.globle.Globle;
import campsg.qunawan.service.TripService;

@Component
public class IndexAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// 行程类型字符串数组，依次为 {自驾游、国内游和出境游}
	private String[] tripTypes = Constants.TRIP_TYPE;

	@Autowired
	private TripService tripService;

	@Autowired
	private SequenceDao sequenceDao;
	
	@Autowired
	private TripDao tripDao;
	
	@Autowired
	private PlaceDao placeDao;
	
	@Autowired
	private ThemeDao themeDao;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 显示的行程列表
		Map<String, List<Trip>> tripMap = new HashMap<>();
		// 页面显示的景点列表
		Map<String, List<String>> placeMap = new HashMap<>();
		// 页面显示的主题列表
		Map<String, List<String>> themeMap = new HashMap<>();

		// 使用'自驾游'、'国内游'和'出境游'为键，存放各自要显示的列表集合
		for (int i = 0; i < 3; i++) {
			int typeId = sequenceDao.getSeqByValue(tripTypes[i]).getId();
			List<Trip> items = tripDao.getPageTripsByType(typeId, 0, 6);
			tripMap.put(tripTypes[i], items);
			for (Trip trip : items){
				tripService.initTripPicture(trip.getPic_list(),
						this.getServletConfig().getServletContext().getRealPath("/"));
			}

			placeMap.put(tripTypes[i], placeDao.getPagePlacesByType(typeId, 0, 10));
			themeMap.put(tripTypes[i], themeDao.getPageThemesByType(typeId, 0, 10));
		}

		request.setAttribute(Constants.INDEX_TRIP_MAP, tripMap);
		request.setAttribute(Constants.INDEX_PLACE_MAP, placeMap);
		request.setAttribute(Constants.INDEX_THEME_MAP, themeMap);

		// 如果当前用户已登录，则记录访问首页的时间和次数
		if (request.getSession().getAttribute(Constants.USER_KEY) != null) {
			// 获取用户id
			Integer uid = ((User) request.getSession().getAttribute(Constants.USER_KEY)).getId();
			// 添加首页访问记录
			Globle.addAccessRecord(uid, Constants.INDEX_PAGE);
		}

		request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
