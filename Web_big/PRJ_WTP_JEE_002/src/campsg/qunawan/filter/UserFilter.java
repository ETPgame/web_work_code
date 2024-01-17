package campsg.qunawan.filter;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import campsg.qunawan.entity.User;
import campsg.qunawan.globle.Constants;

/**
 * 用户登陆状态维护的过滤器
 */
public class UserFilter implements Filter {

	// 可以免验证的URL链接群
	private String pass = null;
	// 过滤器验证不通过时跳转的URL
	private String error_url = null;
	// 存储用户状态的字符串-关键字
	private String userConstants = null;
	/**
	 * Filter初始化时调用，主要用于配置文件中的param参数获取
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		pass = config.getInitParameter("pass");
		error_url = config.getInitParameter("error_url");
		userConstants = config.getInitParameter("userConstants");
	}

	/*
	 * 当前过滤器的验证业务
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 转换HttpServletRequest的类型
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		// 如果URL属于免验证，则通过验证
		if (isCheckAble(req)) {
			chain.doFilter(req, res);
			return;
		}
		
		// 如果用户已登录，则通过验证
		if (checkUser(req) && isAvailable(req)) {
			chain.doFilter(req, res);
			return;
		}

		// 如果错误跳转链接为空，则免验证
		if (error_url == null) {
			chain.doFilter(req, res);
			return;
		}
		res.sendRedirect(req.getContextPath()+error_url);
	}

	/**
	 * 验证URL是否属于免验证范围，如果是返回true
	 */
	private boolean isCheckAble(HttpServletRequest request) {
		if (pass == null)
			return false;

		String url = request.getRequestURI();
		String[] permitParams = pass.split("[;]");

		for (String permit : permitParams) {
			if (isURLPassable(request.getContextPath() + permit, url, request))
				return true;
		}
		return false;
	}

	/**
	 * 判断链接是否可以通过过滤验证
	 * 
	 * @param permit
	 *            （单个）可通过验证的链接
	 * @param url
	 *            需要验证的URL地址
	 * @return 是否通过验证
	 */
	private boolean isURLPassable(String permit, String url, HttpServletRequest request) {
		try {
			/*
			 * 当浏览器禁用cookie时url后需要带上"sessionid的参数"
			 * 所以在不是"/Qunawan/"的premit后加上".*"的任意字符串说明来对此类url进行匹配
			 */
			String reg = "";
			if (permit.equals(request.getContextPath() + "/"))
				reg = "^" + permit + "$";
			else
				reg = "^" + permit + ".*$";
			Pattern p = Pattern.compile(reg);
			return p.matcher(url).matches();
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isAvailable(HttpServletRequest req) {
		ServletContext context = req.getServletContext();
		@SuppressWarnings("unchecked")
		Map<String, String> userMap = (Map<String, String>) context.getAttribute(Constants.ONLINE_USERS);
		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(Constants.USER_KEY);
		String userid = user.getId()+"";
		
		if(userMap.get(userid) != null && userMap.get(userid) != session.getId()){
			session.setAttribute(Constants.ERROR, "您的账号在其他地方登录，被迫下线！");
			session.removeAttribute(Constants.USER_KEY);
			return false;
		}
		return true;
	}
	
	
	/**
	 * 验证当前用户是否登陆
	 * 
	 * @param request
	 *            请求对象
	 * @return 是否登陆
	 */
	private boolean checkUser(HttpServletRequest request) {
		Object o = request.getSession().getAttribute(userConstants);
		return o == null ? false : true;
	}

	
	@Override
	public void destroy() {
	}
}
