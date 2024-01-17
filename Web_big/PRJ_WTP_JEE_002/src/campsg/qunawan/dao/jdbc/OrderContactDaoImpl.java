package campsg.qunawan.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import campsg.qunawan.dao.OrderContactDao;
import campsg.qunawan.entity.OrderContact;
import campsg.qunawan.utils.Packager;

@Repository("orderContactDao")
public class OrderContactDaoImpl extends JDBCBase implements OrderContactDao {

	@Override
	public List<OrderContact> getOrderContacts(int orderId, int type) {
		String sql = "SELECT * from Contact c,ordercontact oc "
				+ "where "+orderId+"=oc.orders and oc.type ="+type+" and oc.contact=c.id;";
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<OrderContact> orderContacts = new ArrayList<>();
		try {
			/**
			 * 	1.TYPE_FORWORD_ONLY,只可向前滚动；
			 * 	2.TYPE_SCROLL_INSENSITIVE,双向滚动，但不及时更新，就是如果数据库里的数据修改过，并不在ResultSet中反应出来。
			 *	3.TYPE_SCROLL_SENSITIVE，双向滚动，并及时跟踪数据库的更新,以便更改ResultSet中的数据。 
			 */
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = query(ps);
			while(rs.next()){
				orderContacts.add(Packager.packOrderContact(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JDBCUtil.close(rs, ps, con);
		}
		return orderContacts;
	}

	@Override
	public int getOrderContactsCount(int contactId) {
		String sql = "select count(*) from ordercontact where contact="+contactId;
		return getCount(sql);
	}

	/********************** 下单事务的数据库操作【start】 *************************/
	@Override
	public void saveOrderContact(Connection con, OrderContact oc) {
		String sql = "INSERT INTO OrderContact(orders,contact,type) "
				+ "VALUES(?,?,?)";
		Object[] param = {
				oc.getOrder().getId(),
				oc.getContact().getId(),
				oc.getType()};
		save(sql, param, con);
	}
	/********************** 下单事务的数据库操作【end】 *************************/
}
