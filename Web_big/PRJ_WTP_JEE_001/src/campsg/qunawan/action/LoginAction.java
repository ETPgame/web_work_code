package campsg.qunawan.action;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import campsg.qunawan.bean.LoginInfo;
import campsg.qunawan.dao.UserDao;
import campsg.qunawan.entity.User;
import campsg.qunawan.globle.Constants;
import campsg.qunawan.service.UserService;
import campsg.qunawan.utils.Utils;

@Component
public class LoginAction extends HttpServlet {

	private static final long serialVersionUID = 2149515480031549266L;

	// 登录失败跳转的url
	private static String ERROR_URL;
	// 登陆成功跳转的url
	private static String OK_URL;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		super.init();
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
		// 用户输入的验证码
		String code = request.getParameter("code");

		// 如果用户名、密码或者验证码为空则跳转回登录页面
		if (condition == null || password == null || code == null || code.equals("")) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}

		// session域中拿到当前正确的验证码
		String right_code = (String) request.getSession().getAttribute(Constants.CHECK_NUMBER_NAME);

		// 对验证码的正确性进行验证
		if (!userService.checkCode(right_code, code)) {
			request.setAttribute(Constants.ERROR, "验证码不正确");
			forword(request, response, true, null);
			return;
		}

		// 通过用户名查询用户
		User user = userDao.getUserByCondition(condition);
		// 验证用户名和密码是否正确
		if (user == null || !user.getPassword().equals(Utils.toMD5(password))) {
			request.setAttribute(Constants.ERROR, "用户名或密码错误！");
			forword(request, response, true, user);
			return;
		}

		// 登陆成功，进行跳转
		forword(request, response, false, user);
		
	}

	/**
	 * 根据登陆是否成功，跳转到目标链接
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @param isError
	 *            登陆是否成功
	 * @param user
	 *            登录的用户对象
	 */
	private void forword(HttpServletRequest request, HttpServletResponse response, boolean isError, User user)
			throws ServletException, IOException {

		// 获取session中的正确跳转后的URL 如果跳转前有存储页面链接，则跳转到登陆前的页面 如果没有，则跳转到首页
		String okUrl = getUrl(Constants.OK_URL, request);
		// 暂未实现错误链接跳转 后续可以作为不同错误跳转页面的伏笔
		String errUrl = getUrl(Constants.ERR_URL, request);

		if (okUrl == null)
			okUrl = "/" + OK_URL;

		if (errUrl == null)
			errUrl = ERROR_URL;

		if (user != null) {
			// 保存用户的相关信息（用户信息、访问记录、登录信息）
			saveUserInfo(request, user);
		}

		if (isError)
			request.getRequestDispatcher(errUrl).forward(request, response);
		else
			/**
			 * 跳转的首页需要通过action获取数据后才能跳转，本来需要用到服务端跳转
			 * 这里只学到重定向，故需要手动添加项目前缀
			 */
			response.sendRedirect(this.getServletContext().getContextPath() + okUrl);
	}

	private void saveUserInfo(HttpServletRequest request, User user) {
		// 重新开启session，方便计算用户登录时间
		request.getSession().invalidate();
		HttpSession session = request.getSession();

		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setIp(request.getRemoteAddr());
		loginInfo.setLoginName(user.getName());
		loginInfo.setLoginTime(new Date());
		session.setAttribute("loginInfo", loginInfo);

		// 把用户状态存入session中
		session.setAttribute(Constants.USER_KEY, user);
	}

	/**
	 * 尝试从请求中获取对应的URL值
	 * 
	 * @param key
	 *            存储URL的键值
	 * @param request
	 *            请求对象
	 * @return 获取到的URL地址
	 */
	private String getUrl(String key, HttpServletRequest request) {

		String url = request.getParameter(key);

		if (url != null && !"".equals(url.trim())) {
			request.setAttribute(key, url);
			return url;
		}

		url = (String) request.getAttribute(key);
		if (url != null && !"".equals(url.trim()))
			return url;

		url = (String) request.getSession().getAttribute(key);
		if (url != null && !"".equals(url.trim())) {
			request.getSession().removeAttribute(key);
			return url;
		}

		return url;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
