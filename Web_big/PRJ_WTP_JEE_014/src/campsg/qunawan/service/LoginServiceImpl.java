package campsg.qunawan.service;

import javax.servlet.http.HttpServletRequest;

import campsg.qunawan.constant.Globle;
import campsg.qunawan.dao.jdbc.UserDaoImpl;
import campsg.qunawan.entity.User;
import campsg.qunawan.utils.Utils;

public class LoginServiceImpl implements LoginService {

	@Override
	public boolean isNormalCode(HttpServletRequest request) {
		String code = request.getParameter("code");
		
		if(code == null)
			return false;
		
		if(Globle.getCode() == null)
			return false;
		
		if(!Globle.getCode().equalsIgnoreCase(code)){
			return false;
		}
		return true;
	}

	@Override
	public boolean isNormalLogin(String uid, String pwd) {
		UserDaoImpl userDaoImpl = new UserDaoImpl();
		
		User user = userDaoImpl.getUserByCondition(uid);
		
		if (user == null || !user.getPassword().equals(Utils.toMD5(pwd))) {
			return false;
		}
		
		return true;
	}

}
