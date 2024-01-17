package campsg.qunawan.action;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import campsg.qunawan.dao.CityDao;
import campsg.qunawan.dao.UserDao;
import campsg.qunawan.entity.City;
import campsg.qunawan.entity.User;
import campsg.qunawan.globle.Constants;
import campsg.qunawan.service.UserService;
import campsg.qunawan.utils.ControllerUtil;
import campsg.qunawan.utils.Utils;

/**
 * 个人信息编辑的Action
 */
@Component
public class PersonalInfoAction extends HttpServlet {

	private static final long serialVersionUID = -4483154013667377726L;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String type = request.getParameter("type");

		// 页面初始化
		if (type == null || "init".equals(type)) {
			List<City> provinces = cityDao.getAllProvinces();
			request.getSession().setAttribute("provinces", provinces);
			request.getRequestDispatcher("/WEB-INF/pages/personal/personal_information.jsp").forward(request, response);
			return;
		}

		// 通过省份来获取相关的城市列表
		if ("getCity".equals(type)) {
			String idStr = request.getParameter("province");
			if (idStr == null || "".equals(idStr))
				return;
			int id = Integer.parseInt(idStr.trim());
			List<City> cityList = cityDao.getAllCityByParentId(id);
			ControllerUtil.out(response, cityList);
		}

		// 修改个人基本资料
		if ("modifyInfo".equals(type)) {
			User user = (User) request.getSession().getAttribute(Constants.USER_KEY);
			updateUserInfo(request, response, user);
		}
	}

	private void updateUserInfo(HttpServletRequest request, HttpServletResponse response, User user)
			throws ServletException, IOException {
		// 接收个人资料的字段参数
		String name = request.getParameter("name");
		String sexStr = request.getParameter("sex");
		String phone = request.getParameter("mobile");
		String email = request.getParameter("email");
		String real_name = request.getParameter("realname");
		String year = request.getParameter("sel_year");
		String month = request.getParameter("sel_month");
		String day = request.getParameter("sel_day");
		String cityStr = request.getParameter("city");
		// 对提交的信息进行空字符验证
		if (!validateData(email, phone, sexStr)) {
			// 设置错误信息
			request.setAttribute(Constants.ERROR, "基本信息不完整,更新失败");
			request.getRequestDispatcher("/WEB-INF/pages/personal/personal_information.jsp").forward(request, response);
			return;
		}
		// 对提交的信息进行手机和邮箱唯一性验证
		if (!userService.checkPhoneAndEmail(phone, email, user)) {
			request.setAttribute(Constants.ERROR, "邮箱或者手机已存在");
			request.getRequestDispatcher("/WEB-INF/pages/personal/personal_information.jsp").forward(request, response);
			return;
		}
		// 对表单提交的数据进行封装处理
		boolean sex = "0".equals(sexStr) ? false : true;
		Date birthday;
		City city;
		if (year == null || "".equals(year) || "0".equals(year)) {
			birthday = null;
		} else {
			if (Integer.parseInt(month) < 10)
				month = "0" + month;

			if (Integer.parseInt(day) < 10)
				day = "0" + day;
			birthday = Utils.stringToDate(year + month + day);
		}
		if (cityStr == null || "".equals(cityStr)) {
			city = null;
		} else {
			city = cityDao.getCityById(Integer.parseInt(cityStr));
		}
		// 将数据封装到user对象
		user.setName(name);
		user.setEmail(email);
		user.setPhone(phone);
		user.setReal_name(real_name);
		user.setSex(sex);
		user.setCity(city);
		user.setBirthday(birthday);

		userDao.update(user);
		// 更新后的数据保存进session
		request.getSession().setAttribute(Constants.USER_KEY, user);

		// 提示信息更新成功
		request.setAttribute(Constants.ERROR, "更新成功");
		request.getRequestDispatcher("/WEB-INF/pages/personal/personal_information.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 对提交的空字符进行验证
	 */
	private boolean validateData(String email, String phone, String sex) {
		if ("".equals(email) || "".equals(phone) || "".equals(sex))
			return false;

		if (email == null || phone == null || sex == null)
			return false;

		return true;
	}

}
