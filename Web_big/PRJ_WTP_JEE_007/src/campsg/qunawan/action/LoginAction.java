package campsg.qunawan.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import campsg.qunawan.constant.Constants;
import campsg.qunawan.constant.Globle;
import campsg.qunawan.dao.jdbc.UserDaoImpl;
import campsg.qunawan.entity.User;
import campsg.qunawan.utils.Utils;

public class LoginAction extends HttpServlet {

	private static final long serialVersionUID = 2149515480031549266L;

	// 登录失败跳转的url
	private static String ERROR_URL = "/login.jsp";
	// 登陆成功跳转的url
	private static String OK_URL = "/index.html";

	@Override
	public void init() throws ServletException {
		ERROR_URL = super.getServletConfig().getInitParameter("error_url");
		OK_URL = super.getServletContext().getInitParameter("index_url");
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 用户提交的手机或邮箱
		String condition = request.getParameter("name");
		// 用户的个人密码
		String password = request.getParameter("password");

		System.out.println(condition);
		System.out.println(password);
		/**
		 * 获取并验证验证码
		 */
		String code = request.getParameter("code");
		if(!Globle.getCode().equalsIgnoreCase(code)){
			request.setAttribute(Constants.GLOBAL_ERROR_KEY, "验证码不正确！");
			request.getRequestDispatcher(ERROR_URL).forward(request, response);
			return;
		}

		/** 实训场景007：登陆错误提示 - 验证用户信息 【Start】 **/
		// 创建UserDaoImpl实例
		UserDaoImpl userDao = new UserDaoImpl();

		// 调用getUserByCondition获取User对象
		User user = userDao.getUserByCondition(condition);

		// 判断是否存在匹配记录
		if (user != null) {
			// 对用户输入的密码进行MD5加密
			String encryptedPassword = Utils.toMD5(password);

			// 判断密码是否一致
			if (encryptedPassword.equals(user.getPassword())) {

				System.out.println("用户名和密码输入正确");
				// 登录信息有效，重定向到首页
				response.sendRedirect(OK_URL);

			} else {
				System.out.println("用户名或密码错误");
				// 登录信息无效，保存错误信息到Request作用域，然后跳转回登录页面
				request.setAttribute("error_msg", "用户名或密码错误");
				request.getRequestDispatcher(ERROR_URL).forward(request, response);


			}
		} else {
			System.out.println("用户名或密码错误");
			request.setAttribute("error_msg", "用户名或密码错误");
			request.getRequestDispatcher(ERROR_URL).forward(request, response);


		}
		

		/** 实训场景007：登陆错误提示 - 验证用户信息 【End】 **/
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
