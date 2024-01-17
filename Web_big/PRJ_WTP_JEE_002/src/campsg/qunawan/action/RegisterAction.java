package campsg.qunawan.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import campsg.qunawan.dao.UserDao;
import campsg.qunawan.entity.User;
import campsg.qunawan.globle.Constants;
import campsg.qunawan.service.UserService;

/**
 * 注册新用户的Action
 */
@Component
public class RegisterAction extends HttpServlet {

	private static final long serialVersionUID = -2194865395691253568L;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取用户的注册名（手机或邮箱）
		String condition = request.getParameter("name");
		// 获取操作类型字符串（初始化页面或提交注册信息）
		String type = request.getParameter("type");

		/*
		 * 如果是初始化页面
		 */
		if ("init".equals(type))
			// 设置跳转回注册页面的标识（在登录页面通过该标识进行登录和注册的切换）
			request.setAttribute("init", "reg");
		/*
		 * 否则就是注册操作
		 */
		else {
			/*
			 * 验证当前注册账号是否存在
			 */
			if (isUserExisted(condition)) {
				// 设置错误信息
				request.setAttribute(Constants.ERROR, "当前注册帐号已存在");
				request.setAttribute("init", "reg");
			}
			/*
			 * 保存用户
			 */
			else {
				// 保存用户数据
				userService.registUser(condition);
			}
		}

		// 跳转到登录页面
		forward(request, response);
	}

	/**
	 * 判断当前尝试注册的用户是否存在
	 */
	private boolean isUserExisted(String condition) throws ServletException, IOException {
		User user = userDao.getUserByCondition(condition);

		// 对注册帐号的唯一性进行验证
		if (user != null)
			return true;

		return false;
	}

	/**
	 * 执行页面跳转
	 * 
	 * @param request
	 * @param response
	 */
	private void forward(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
