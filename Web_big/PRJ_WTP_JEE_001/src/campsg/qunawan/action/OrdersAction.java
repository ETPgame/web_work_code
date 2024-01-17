package campsg.qunawan.action;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import campsg.qunawan.dao.ContactDao;
import campsg.qunawan.dao.OrdersDao;
import campsg.qunawan.dao.SequenceDao;
import campsg.qunawan.dao.TripDao;
import campsg.qunawan.entity.Contact;
import campsg.qunawan.entity.Orders;
import campsg.qunawan.entity.Sequence;
import campsg.qunawan.entity.Trip;
import campsg.qunawan.entity.User;
import campsg.qunawan.form.OrderDetailForm;
import campsg.qunawan.form.OrderForm;
import campsg.qunawan.form.PutOrderForm;
import campsg.qunawan.globle.Constants;
import campsg.qunawan.service.OrdersService;
import campsg.qunawan.utils.Utils;

@Component
public class OrdersAction extends HttpServlet {

	private static final long serialVersionUID = 7438301614329084360L;

	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private ContactDao contactDao;
	@Autowired
	private SequenceDao sequenceDao;
	@Autowired
	private OrdersDao ordersDao;
	@Autowired
	private TripDao tripDao;

	private HttpServletRequest request;
	private HttpServletResponse response;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;
		String type = request.getParameter("type");
		User user = (User) request.getSession().getAttribute(Constants.USER_KEY);

		if ("initOrderDetail".equals(type)) {
			String id = request.getParameter("order_id");
			if (id == null)
				return;
			Integer order_id = Integer.parseInt(id);

			OrderDetailForm orderDetailForm = new OrderDetailForm();

			orderDetailForm.setContact_many(ordersService.getContactsByOrderId(order_id));
			orderDetailForm.setContact_one(ordersService.getContactByOrderId(order_id));
			orderDetailForm.setOrder(ordersDao.getOrderById(order_id));

			request.getSession().setAttribute("orderDetailForm", orderDetailForm);
			request.getRequestDispatcher("/WEB-INF/pages/order_detail.jsp").forward(request, response);
			return;
		}
		// 订单初始化&订单分页查询
		if ("init".equals(type)) {
			List<OrderForm> orderForms = getOrders(user.getId());
			request.setAttribute("orderFormList", orderForms);
			request.setAttribute("pageCount", orderForms .size() != 0 ? orderForms.get(0).getPageCount() : 1);
			request.setAttribute("cur", Utils.getPageNum(request.getParameter("page")));
			request.getRequestDispatcher("/WEB-INF/pages/personal/personal_myOrder.jsp").forward(request, response);
		}
		// 订单确认的初始数据
		if ("confirmOrder".equals(type)) {
			confirmOrder(user.getId());
		}
		// 下单
		if ("putOrder".equals(type)) {
			putOrder(user);
		}
		// 点击确认付款时，修改当前订单状态
		if ("payForOrder".equals(type)) {
			payForOrder();
		}

		// 跳转到订单付款界面
		if ("showPayFor".equals(type)) {
			String ordId = request.getParameter("id");
			int id = Integer.parseInt(ordId);
			Orders order = ordersDao.getOrderById(id);
			PutOrderForm pof = new PutOrderForm(order);
			request.setAttribute("vo", pof);
			request.getRequestDispatcher("/WEB-INF/pages/order_pay.jsp").forward(request, response);
		}
	}

	/**
	 * 订单付款
	 */
	private void payForOrder() throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = Integer.parseInt(idStr);
		Orders order = ordersDao.getOrderById(id);
		Sequence sq = sequenceDao.getSeqByKeyAndType(Constants.WAIT_COMMENT_ORDER_STATE, Constants.ORDER_TYPE);
		order.setState(sq);
		
		ordersDao.updateOrderState(sq, id);
		
		// 对页面输出信息
		PutOrderForm pof = new PutOrderForm(order);
		request.setAttribute("vo", pof);
		request.getRequestDispatcher("/WEB-INF/pages/order_success.jsp").forward(request, response);
	}

	/**
	 * 下订单
	 * @param userId 用户Id
	 */
	private void putOrder(User user) throws ServletException, IOException {
		// 获取紧急联系人信息
		String em_id = request.getParameter("em_id");
		String em_name = request.getParameter("em_name");
		String em_phone = request.getParameter("em_phone");
		// 获取订单的详细信息
		Trip trip = (Trip) request.getSession().getAttribute("put_order_trip");
		float price = (float) request.getSession().getAttribute("put_order_price");
		int num = (int) request.getSession().getAttribute("put_order_num");
		Date time = (Date) request.getSession().getAttribute("put_order_time");
		// 遍历获取游玩人的信息，保存在数组之中
		String[] id = request.getParameterValues("w_id");
		String[] name = request.getParameterValues("w_name");
		String[] phone = request.getParameterValues("w_phone");
		String[] cardno = request.getParameterValues("w_cardno");
		// 获取选择上车的地点信息
		String place = request.getParameter("place");
		
		// 封装订单信息
		Orders order = ordersService.packOrder(num, time, price, place, trip, user);
		// 生成紧急联系人
		Contact em_contact = ordersService.packContact(em_id, em_name, em_phone, user);
		// 生成游客
		List<Contact> contacts = new ArrayList<Contact>();
		for (int i = 0; i < num; i++) {
			String cid = "";
			if(id != null && i<id.length)
				cid = id[i];
			Contact contact = ordersService.packContact(cid, name[i], phone[i], cardno[i], user);
			contacts.add(contact);
		}

		ordersService.putOrder(order, em_contact, contacts);
			
		// 数据封装并响应
		PutOrderForm pof = new PutOrderForm(order);
		request.setAttribute("vo", pof);
		request.getRequestDispatcher("/WEB-INF/pages/order_pay.jsp").forward(request, response);
	}

	/**
	 * 订单确认
	 * @param userId 用户id
	 * @throws ServletException 调度转发时Servlet出现错误
	 * @throws IOException 页面调度转发时出现错误
	 */
	private void confirmOrder(Integer userId) throws ServletException, IOException {
		// 获取提交的参数
		String putOrder_trip_id = request.getParameter("trip_id");
		String putOrder_numStr = request.getParameter("num");
		String putOrder_go_time = request.getParameter("date");
		// 参数验证
		if (putOrder_trip_id == null || putOrder_numStr == null || putOrder_go_time == null
				|| putOrder_trip_id.equals("") || putOrder_numStr.equals("") || putOrder_go_time.equals("")) {
			System.out.println("订单确认参数不合法！");
			return;
		}
		// 获取行程对象
		int tid = Integer.parseInt(putOrder_trip_id);
		Trip trip = tripDao.getTripById(tid);
		// 人数参数类型转换
		int num = Integer.parseInt(putOrder_numStr);
		// 日期参数类型转换
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date time = formatter.parse(putOrder_go_time, pos);
		java.sql.Date date = new java.sql.Date(time.getTime());
		
		// 通过出行日期和人数以及产品，查询总价格
		float price = ordersService.getTripPrice(date, num, trip);
		List<Contact> contacts = contactDao.getAllContactByUser(userId);

		// 订单保存进行确认处理
		request.getSession().setAttribute("put_order_trip", trip);
		request.getSession().setAttribute("put_order_num", num);
		request.getSession().setAttribute("put_order_price", price);
		request.getSession().setAttribute("put_order_time", time);
		request.getSession().setAttribute("put_order_contactlist", contacts);
		request.getRequestDispatcher("/WEB-INF/pages/order.jsp").forward(request, response);
	}

	/**
	 * 获取用户的所有订单
	 * @param userId 用户的id
	 */
	private List<OrderForm> getOrders(Integer userId) {
		// 接收要查询的订单页码
		String pageStr = request.getParameter("page");
		// 类型转换
		int page = Utils.getPageNum(pageStr);
		// 获取订单的总数目
		int orderCount = ordersDao.getOrdersbyUser(userId);
		// 计算列表的总页码
		int pageCount = (orderCount % Constants.ORDER_MAX == 0) ? (orderCount / Constants.ORDER_MAX)
				: (orderCount / Constants.ORDER_MAX + 1);
		// 获取查询页的订单集合
		List<Orders> orders = ordersDao.getOrders(userId, page, Constants.ORDER_MAX);
		// 将查询的订单集合进行重新封装处理
		List<OrderForm> orderForms = new ArrayList<OrderForm>();
		for (Orders order : orders) {
			OrderForm orderForm = new OrderForm();
			orderForm.setOrderid(order.getId());
			orderForm.setContent(order.getTrip().getTitle());
			orderForm.setCreate_time(order.getCreate_time().toString());
			orderForm.setOrderno(order.getOrderNo());
			orderForm.setPerson_num(order.getNum());
			orderForm.setPrice(order.getTotal_price());
			orderForm.setStart_time(order.getStart_time().toString());
			orderForm.setState(order.getState());
			orderForm.setPageCount(pageCount);
			orderForm.setTotalDays(order.getTrip().getTime());
			orderForms.add(orderForm);
		}
		return orderForms;
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
