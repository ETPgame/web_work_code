package campsg.qunawan.dao;

import campsg.qunawan.entity.User;

public interface UserDao {
	/**
	 * 通过邮箱或者电话号码,用户名查询User
	 * 
	 * @param condition
	 *            查询关键字
	 * @return 返回User对象
	 */
	User getUserByCondition(String condition);
	
}