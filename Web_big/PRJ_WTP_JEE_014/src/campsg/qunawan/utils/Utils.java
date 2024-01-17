package campsg.qunawan.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 工具类，提供一些静态的功能函数
 */
public class Utils {
	
	/**
	 * 将字符串进行MD5加密， 主要用于密码部分
	 */
	public static String toMD5(String data) {
		if (data == null)
			return null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			// 加密转换
			md.update(data.getBytes());
			String result = new BigInteger(1, md.digest()).toString(16);

			return result;

		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

}
