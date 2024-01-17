package campsg.qunawan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import campsg.qunawan.dao.UserDao;
import campsg.qunawan.entity.User;
import campsg.qunawan.service.UserService;
import campsg.qunawan.utils.Utils;

/**
 * 用户表的Service类
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public void registUser(String condition) {
		User user = new User();
		// 如果用户输入的是邮箱号码
		if (Utils.isEmail(condition))
			user.setEmail(condition);
		// 否则用户输入的就是手机号码
		else
			user.setPhone(condition);
		// 设置用户默认密码为123456
		user.setPassword(Utils.toMD5("123456"));
		userDao.save(user);
	}

	/** PRJ-WTP-HIB-007：用户数据层优化 更新Service方法 【2/2 end】**/
	
	/**
	 * 检查验证码信息
	 */
	@Override
	public boolean checkCode(String right_code, String code) {
		if (right_code == null || "".equals(right_code))
			return false;
		right_code = right_code.toUpperCase();
		code = code.toUpperCase();

		if (right_code.equals(code))
			return true;
		else
			return false;

	}

	/**
	 * 验证手机和邮箱的唯一性
	 * 
	 */
	@Override
	public boolean checkPhoneAndEmail(String phone, String email, User user) {
		if (!phone.equals(user.getPhone()))
			if (userDao.getUserByCondition(phone)!=null)
				return false;

		if (!email.equals(user.getEmail()))
			if (userDao.getUserByCondition(email)!=null)
				return false;

		return true;
	}

}
