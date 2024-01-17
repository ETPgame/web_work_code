package campsg.qunawan.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import campsg.qunawan.entity.User;
import campsg.qunawan.globle.Constants;
import campsg.qunawan.globle.Globle;

public class LogoutAction extends HttpServlet {

	private static final long serialVersionUID = 4619547987616222442L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获得session对象
		HttpSession session = request.getSession();
		// 获得当前登录用户id
		User user = (User)session.getAttribute(Constants.USER_KEY);
		if (user == null){
			response.sendRedirect("/Qunawan/login.html");
			return;
		}

		// 保存用户访问记录到session
		saveRecordList(session, user.getId());
		// 移除session中保存的用户id
		session.removeAttribute(Constants.USER_KEY);

		// 注销后跳转到登录页面（场景012的跳转）
		response.sendRedirect("message.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 保存用户访问记录到session中，同时移除Globle中存放该用户的记录器
	 * 
	 * @param session
	 *            HttpSession对象
	 * @param recorder
	 *            访问记录器
	 * @param uid
	 *            用户id
	 */
	private void saveRecordList(HttpSession session, Integer uid) {
		// 获取按时间排列后的访问信息列表
		List<Map.Entry<String, String>> accessList = Globle.getAccessList(uid);
		// 把该列表存到session中
		session.setAttribute(Constants.ACCESS_RECORD_IN_SESSION, accessList);
		// 清除当前用户的访问记录器
		Globle.clearAccessRecorder(uid);
	}
}
