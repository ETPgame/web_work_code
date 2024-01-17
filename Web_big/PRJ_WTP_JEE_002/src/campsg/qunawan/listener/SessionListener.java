package campsg.qunawan.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 监听session的创建及销毁，每次一个用户登陆，会创建一个session函数
 * 当1分钟内不进行操作，session会销毁，此时调用sessionDestroyed函数，实际情况会在1分钟到2分钟之间
 * @author huang
 *
 */
/*
 * ******************实训场景028：会话过期-SessionListener监听器【start】*******************
 */

public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println(getTime() + "  创建的session编号：" + arg0.getSession().getId());

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println(getTime() + "  销毁的session编号：" + arg0.getSession().getId());
	}

	/**
	 * 获取系统时间
	 * @return
	 */
	private String getTime() {
		long l = System.currentTimeMillis();
		// new日期对象
		Date date = new Date(l);
		// 转换提日期输出格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

}
/* ******************实训场景028：会话过期-SessionListener监听器【end】******************* */
