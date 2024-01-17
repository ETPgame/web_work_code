package campsg.qunawan.listener;

import campsg.qunawan.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.util.Map;

public class UserListener implements HttpSessionListener, HttpSessionAttributeListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        System.out.println("UserListener被创建，当前SESSION ID:"+session.getId());
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
    }
    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        HttpSession session = httpSessionBindingEvent.getSession();
        String id = session.getId();
        ServletContext servletContext = session.getServletContext();
        if (!httpSessionBindingEvent.getName().equals("user")){
            return;
        }
        User user = (User) httpSessionBindingEvent.getValue();
        String phone = user.getPhone();
        Map<String,String> map = (Map<String, String>) servletContext.getAttribute("online_user");
        map.put(phone,id);
        servletContext.setAttribute("online_user",map);
        System.out.println("客户端"+id+",使用"+user.getPhone()+"登录《去哪玩》成功！");
        debugUsers(servletContext);

    }
    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        HttpSession session = httpSessionBindingEvent.getSession();
        String id = session.getId();
        ServletContext servletContext = session.getServletContext();
        Map<String,String> map = (Map<String, String>) servletContext.getAttribute("online_user");
        map.remove(id);
        servletContext.setAttribute("online_user",map);
        debugUsers(servletContext);
        System.out.println("客户端"+id+",执行注销操作");
    }
    private void debugUsers(ServletContext servletContext) {
        Map<String,String> map = (Map<String, String>) servletContext.getAttribute("online_user");
        System.out.println("Context容器(Application)中的Online_Users数据∶");
        System.out.println(map);
    }
    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
    }
}
