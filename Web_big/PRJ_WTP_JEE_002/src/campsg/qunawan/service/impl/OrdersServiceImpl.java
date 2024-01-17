package campsg.qunawan.service.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import campsg.qunawan.dao.ContactDao;
import campsg.qunawan.dao.OrderContactDao;
import campsg.qunawan.dao.OrdersDao;
import campsg.qunawan.dao.PriceDao;
import campsg.qunawan.dao.SequenceDao;
import campsg.qunawan.dao.jdbc.JDBCUtil;
import campsg.qunawan.entity.Contact;
import campsg.qunawan.entity.OrderContact;
import campsg.qunawan.entity.Orders;
import campsg.qunawan.entity.Price;
import campsg.qunawan.entity.Sequence;
import campsg.qunawan.entity.Trip;
import campsg.qunawan.entity.User;
import campsg.qunawan.globle.Constants;
import campsg.qunawan.service.OrdersService;
import campsg.qunawan.utils.Utils;

@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrderContactDao orderContactDao;
	
	@Autowired
	private PriceDao priceDao;
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private ContactDao contactDao;
	
	@Autowired
	private SequenceDao sequenceDao;

	@Override
	public List<Contact> getContactsByOrderId(int orderId) {
		List<Contact> ocs = new ArrayList<Contact>();

		List<OrderContact> orderContacts = orderContactDao.getOrderContacts(orderId, Constants.CONTACT_FOR_PLAY);
		if (orderContacts == null || orderContacts.size() < 1)
			return null;

		for (OrderContact oc : orderContacts) {
			ocs.add(oc.getContact());
		}

		return ocs;
	}

	@Override
	public Contact getContactByOrderId(int id) {
		List<OrderContact> orderContacts = orderContactDao.getOrderContacts(id, Constants.CONTACT_FOR_URGENT);
		return orderContacts.get(0).getContact();
	}
	
	/**
	 * 通过出行日期和人数以及trip获取价格
	 */
	@Override
	public Float getTripPrice(Date date, int num, Trip trip) {
		Price price = priceDao.getOneDayPrice(trip.getId(), date);
		Float p = price.getPrice();
		return p * num;
	}

	@Override
	public void putOrder(Orders order, Contact em_contact, List<Contact> clist) {
		Connection con = JDBCUtil.getConnection();
		try {
			// 设置为手动提交
			con.setAutoCommit(false);
			
			/**
			 *  1、保存订单
			 */
			int oid = ordersDao.savaOrder(con, order);
			order.setId(oid);

			/**
			 *  2、保存紧急联系人
			 */
			int em_id = em_contact.getId();
			if(em_id == 0){
				em_id = contactDao.saveContact(con, em_contact);
			}
			em_contact.setId(em_id);
			OrderContact oc_em = new OrderContact(order, em_contact, Constants.EM_CONTACT);
			orderContactDao.saveOrderContact(con, oc_em);
			
			/**
			 *  3、保存游玩人
			 */
			for(Contact contact : clist){
				int cid = contact.getId();
				if(cid == 0){
					cid = contactDao.saveContact(con, contact);
				}
				contact.setId(cid);
				OrderContact oc_pl = new OrderContact(order, contact, Constants.PL_CONTACT);
				orderContactDao.saveOrderContact(con, oc_pl);
			}
			
			// 事务提交
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			JDBCUtil.close(con);
		}
	}


	@Override
	public Contact packContact(String id, String name, String phone, User user) {
		return packContact(id, name, phone, null, user);
	}

	@Override
	public Contact packContact(String id, String name, String phone, String cardno, User user) {
		Contact em_contact = null;
		if (id !=null && "-1".equals(id)) {
			if(cardno != null)
				em_contact = new Contact(user, name, phone, cardno);
			else
				em_contact = new Contact(user, name, phone);
		} else {
			em_contact = contactDao.getContactById(Integer.parseInt(id));
		}
		return em_contact;
	}

	@Override
	public Orders packOrder(int num, java.util.Date time, float total_price,
			String place, Trip trip, User user) {
		// 封装订单信息
		Orders order = new Orders();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestr = df.format(new Date(System.currentTimeMillis()));
		order.setCreate_time(timestr);
		
		order.setGo_point(place);
		order.setGo_time(trip.getSchedule().getTraffic().getGo_time());
		order.setNum(num);
		order.setStart_time(new java.sql.Date(time.getTime()));
		order.setTrip(trip);
		order.setUser(user);
		order.setTotal_price(total_price);
		
		// 订单状态生成
		Sequence sq = sequenceDao.getSeqByKeyAndType(Constants.WAIT_PAY_STATE, Constants.ORDER_TYPE);
		order.setState(sq);
		
		// 订单编号生成
		String no = Utils.createName();
		order.setOrderNo(no);
		return order;
	}

}
