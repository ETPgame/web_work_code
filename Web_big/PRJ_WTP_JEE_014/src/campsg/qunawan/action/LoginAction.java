package campsg.qunawan.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import campsg.qunawan.service.LoginService;
import campsg.qunawan.service.LoginServiceImpl;

public class LoginAction extends HttpServlet {

	private static final long serialVersionUID = 2149515480031549266L;
	
	private LoginService service = new LoginServiceImpl();

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 用户提交的手机或邮箱
		String uid = request.getParameter("name");
		// 用户的个人密码
		String pwd = request.getParameter("password");

		
		if(!service.isNormalCode(request)){
			request.setAttribute("message", "验证码不正确！");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		

		if (!service.isNormalLogin(uid,pwd)) {
			request.setAttribute("message", "用户名或密码错误！");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}else{

			Cookie qunawan_uid=new Cookie(uid,pwd);
			qunawan_uid.setMaxAge(86400);
			response.addCookie(qunawan_uid);

			response.sendRedirect("index.html");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
