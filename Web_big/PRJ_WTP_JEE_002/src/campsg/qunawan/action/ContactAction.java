package campsg.qunawan.action;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import campsg.qunawan.dao.ContactDao;
import campsg.qunawan.dao.OrderContactDao;
import campsg.qunawan.entity.Contact;
import campsg.qunawan.entity.User;
import campsg.qunawan.form.ContactForm;
import campsg.qunawan.globle.Constants;
import campsg.qunawan.utils.ControllerUtil;
import campsg.qunawan.utils.Utils;

@Component
public class ContactAction extends HttpServlet {

	private static final long serialVersionUID = -5747037685165941130L;

	@Autowired
	private OrderContactDao orderContactDao;	
	
	@Autowired
	private ContactDao contactDao;	

	private HttpServletRequest request = null;
	private HttpServletResponse response = null;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute(Constants.USER_KEY);
		this.request = request;
		this.response = response;

		String type = request.getParameter("type");

		// 获取常用联系人列表信息
			if ("getContacts".equals(type)) {			
				String pageStr = request.getParameter("page");
				int page = Utils.getPageNum(pageStr);
		
				int contactNum = contactDao.getAllContactByUser(user.getId()).size();
				int pageCount = (int) Math.ceil((float) contactNum / Constants.CONTACT_MAX);
				if (pageCount == 0)
					pageCount = 1;
		
				List<Contact> contacts = contactDao.getAllContactPerPage(user.getId(), page, Constants.CONTACT_MAX);
				List<ContactForm> formList = new ArrayList<ContactForm>();
				for (Contact contact : contacts) {
					ContactForm contactForm = new ContactForm(contact, pageCount);
					formList.add(contactForm);
				}
				if (formList.size() != 0)
					request.setAttribute("pageCount", formList.get(0).getPageCount());
				request.setAttribute("formList", formList);
				request.setAttribute("cur", Utils.getPageNum(request.getParameter("page")));
				request.getRequestDispatcher("/WEB-INF/pages/personal/personal_myContact.jsp").forward(request, response);}

		// 增加常用联系人信息
		if ("saveContact".equals(type)) {
			Contact contact = getContactFromRequest(new Contact());
			contactDao.saveContact(contact, user.getId());
			ControllerUtil.out(response, Constants.OP_SUCCESS);
		}

		// 修改常用联系人信息
		if ("editContact".equals(type)) {
			String contactId = request.getParameter("c_id");
			if (contactId == null || "".equals(contactId))
				return;
			int id = Integer.parseInt(contactId);
			Contact contact = contactDao.getContactById(id);

			getContactFromRequest(contact);

			try {
				contactDao.updateContact(contact);
				ControllerUtil.out(response, "更新成功！");
			} catch (Exception e) {
				ControllerUtil.out(response, "更新失败！");
				e.printStackTrace();
			}
		}

		// 删除常用联系人
		if ("deleteContact".equals(type)) {
			String c_contactid = request.getParameter("c_id");
			if (c_contactid == null)
				return;
			int id = Integer.parseInt(c_contactid);

			// 直接删除，由于外键关联会报错
			int count = orderContactDao.getOrderContactsCount(id);
			if (count == 0) {
				contactDao.delete(id);
				ControllerUtil.out(response, Constants.OP_SUCCESS);
			} else
				ControllerUtil.out(response, Constants.OP_FAIL);
		}
	}

	private Contact getContactFromRequest(Contact contact) throws ServletException, IOException {

		String c_name = request.getParameter("c_name");
		String c_sexStr = request.getParameter("c_sex");
		String c_phone = request.getParameter("c_mobile");
		String c_email = request.getParameter("c_email");
		String c_year = request.getParameter("c_sel_year");
		String c_month = request.getParameter("c_sel_month");
		String c_day = request.getParameter("c_sel_day");
		String c_cardno = request.getParameter("c_cardno");
		boolean c_sex = "1".equals(c_sexStr) ? true : false;

		Date birthday;
		if (c_year == null || "".equals(c_year) || "0".equals(c_year)) {
			birthday = null;
		} else {
			if (Integer.parseInt(c_month) < 10)
				c_month = "0" + c_month;

			if (Integer.parseInt(c_day) < 10)
				c_day = "0" + c_day;
			birthday = Utils.stringToDate(c_year + c_month + c_day);
		}
		if (!validateData(c_name, c_phone, c_cardno)) {
			request.setAttribute(Constants.ERROR, "信息不完整,更新失败");
			request.getRequestDispatcher("/WEB-INF/pages/personal.jsp").forward(request, response);
		}

		contact.setBirthday(birthday);
		contact.setName(c_name);
		contact.setPhone(c_phone);
		contact.setSex(c_sex);
		contact.setCardno(c_cardno);
		contact.setEmail(c_email);

		return contact;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	/**
	 * 对提交的空字符进行验证
	 */
	private boolean validateData(String name, String phone, String real_name) {
		if ("".equals(name) || "".equals(phone) || "".equals(real_name))
			return false;
		if (name == null || phone == null || real_name == null)
			return false;

		return true;
	}
}
