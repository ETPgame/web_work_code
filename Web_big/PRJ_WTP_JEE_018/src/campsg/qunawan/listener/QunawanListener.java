package campsg.qunawan.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;

public class QunawanListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        HashMap<String, String> map = new HashMap<>();
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("online_user",map);
        System.out.println("QunawanContextListener被创建");
        System.out.println("创建用户列表成功");
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
