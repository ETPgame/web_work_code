package campsg.qunawan.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import campsg.qunawan.action.Constant.Globle;

public class LoginAction extends HttpServlet {

	private static final long serialVersionUID = 2149515480031549266L;

	// 登录失败跳转的url
	private static String ERROR_URL;
	// 登陆成功跳转的url
	private static String OK_URL;

	@Override
	public void init() throws ServletException {
		ERROR_URL = super.getServletConfig().getInitParameter("error_url");
		OK_URL = super.getServletContext().getInitParameter("index_url");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// 用户提交的手机或邮箱
		String condition = req.getParameter("n");
		// 用户的个人密码
		String password = req.getParameter("p");
		
		/** PRJ-WTP-JEE-005：校验验证码 获取并校验验证码 【Start】 **/

		String userInputtedCaptcha = req.getParameter("code");

		String serverGeneratedCaptcha = Globle.getCode();

		if (userInputtedCaptcha != null && userInputtedCaptcha.equals(serverGeneratedCaptcha)) {
			// 3-4.1 验证码正确，进入到用户名和密码的校验流程
			if ("18701721202".equals(condition) && "123456".equals(password)) {
				resp.sendRedirect(OK_URL);
			} else {
				resp.sendRedirect(ERROR_URL);
			}
		} else {
			// 3-4.1 验证码错误，重定向到登录页面
			resp.sendRedirect(ERROR_URL);
		}
		/** PRJ-WTP-JEE-005：校验验证码 获取并校验验证码 【End】 **/

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
