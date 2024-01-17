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
import campsg.qunawan.utils.Utils;

/**
 * 更新用户密码的Action
 */
@Component
public class UpdatePwdAction extends HttpServlet {

	private static final long serialVersionUID = 5676793869496436439L;

	@Autowired
	private UserDao userDao;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		if (type == null)
			return;

		User user = (User) request.getSession().getAttribute(Constants.USER_KEY);

		// 修改密码的字段参数
		String oldPassword = request.getParameter("oldPassWord");
		String newPassword = request.getParameter("newPassWord");

		if ("init".equals(type))
			request.getRequestDispatcher("/WEB-INF/pages/personal/personal_passwordChange.jsp").forward(request,
					response);

		// 修改密码
		if ("modifyPwd".equals(type)) {
			if (!checkPwd(oldPassword, user)) {
				request.setAttribute(Constants.ERROR, "密码不正确");
				request.getRequestDispatcher("/WEB-INF/pages/personal/personal_passwordChange.jsp").forward(request,
						response);
				return;
			}

			user.setPassword(Utils.toMD5(newPassword));
			userDao.update(user);

			request.setAttribute(Constants.ERROR, "密码更新成功");
			request.getSession().removeAttribute(Constants.USER_KEY);
			response.sendRedirect("login.jsp");
		}
	}

	/**
	 * 验证密码是否正确
	 */
	private boolean checkPwd(String oldPassword, User user) {
		if (Utils.toMD5(oldPassword).equals(user.getPassword()))
			return true;
		else
			return false;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
