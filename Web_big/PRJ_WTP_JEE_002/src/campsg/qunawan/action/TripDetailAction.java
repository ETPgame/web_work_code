package campsg.qunawan.action;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import campsg.qunawan.dao.CommentDao;
import campsg.qunawan.dao.TripDao;
import campsg.qunawan.entity.Comment;
import campsg.qunawan.entity.CommentPicture;
import campsg.qunawan.entity.Trip;
import campsg.qunawan.entity.User;
import campsg.qunawan.globle.Constants;
import campsg.qunawan.globle.Globle;
import campsg.qunawan.service.CommentService;
import campsg.qunawan.service.TripService;
import campsg.qunawan.utils.ControllerUtil;
import campsg.qunawan.utils.Utils;

/**
 * 旅游产品详情的相关业务
 */
@Component
public class TripDetailAction extends HttpServlet {

	private static final long serialVersionUID = -5269537219643513765L;

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private TripDao tripDao;

	@Autowired
	private CommentService commentService;

	@Autowired
	private TripService tripService;

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
			tripService.initTripPicture(trip.getPic_list(),
					this.getServletConfig().getServletContext().getRealPath("/"));

			HttpSession session = (HttpSession) request.getSession();
			// 把产品对象存到session中
			session.setAttribute("detail", trip);
			// 把产品评论总数存到session中
			session.setAttribute("num", commentDao.getCommentCount(id));

			// 如果当前用户已登录，则记录访问旅游详情页的时间和次数
			if (request.getSession().getAttribute(Constants.USER_KEY) != null) {
				// 获取用户id
				User user = (User) request.getSession().getAttribute(Constants.USER_KEY);
				// 添加详情页访问记录
				Globle.addAccessRecord(user.getId(), Constants.TRIP_DETAIL_PAGE);
			}

			request.getRequestDispatcher("/WEB-INF/pages/trip_detail.jsp").forward(request, response);
		}

		// 产品-评论分页查询
		if (type.equals("comment")) {
			// 获取要显示的页码数
			String pageStr = request.getParameter("page");
			Integer page = Utils.getPageNum(pageStr);
			
			// 获取指定页的评论集合
			List<Comment> comments = commentDao.getCommentsByTripId(id, page, Constants.COMMENT_DETAIL_MAX);
			// 初始化评论图片
			for (Comment c : comments) {
				Set<CommentPicture> pictures = c.getPictures();
				commentService.initCommentPicture(pictures, this.getServletConfig().getServletContext()
						.getRealPath("/"));
			}
			ControllerUtil.out(response, comments);
		}

		// 更新评论的赞或踩数量
		if (type.equals("goodOrBad")) {
				String isUsefull_tmp = request.getParameter("isUsefull");
				boolean isUsefull = Boolean.parseBoolean(isUsefull_tmp);
				ControllerUtil.out(response, "" + commentDao.updateUseful(id, isUsefull));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	

}
