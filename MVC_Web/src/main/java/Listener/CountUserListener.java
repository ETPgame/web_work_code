package Listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebListener
public class CountUserListener implements HttpSessionAttributeListener, ServletContextListener {
    private final Set<HttpSession> loggedInUsers= Collections.synchronizedSet(new HashSet<>());

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        sce.getServletContext().setAttribute("loggedInUsers",loggedInUsers);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        String attributeName = event.getName();
        Object attributeValue = event.getValue();

        //sout
        System.out.println("Added...");
        System.out.println("attributeName:" + attributeName);
        System.out.println("attributeValue:"+ attributeValue);

        if ("isAuthenticated".equals(attributeName) && Boolean.TRUE.equals(attributeValue)) {
            HttpSession session = event.getSession();
            loggedInUsers.add(session);
        }
        event.getSession().getServletContext().setAttribute("count",loggedInUsers.size());

        System.out.println("count:"+loggedInUsers.size());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        String attributeName = event.getName();
        Object newAttributeValue = event.getSession().getAttribute(attributeName);

        //sout
        System.out.println("Replaced...");
        System.out.println("attributeName:" + attributeName);
        System.out.println("attributeValue:"+ event.getValue());

        if ("isAuthenticated".equals(attributeName) && Boolean.TRUE.equals(newAttributeValue)) {
            HttpSession session = event.getSession();
            loggedInUsers.add(session);
        } else if ("isAuthenticated".equals(attributeName) && Boolean.FALSE.equals(newAttributeValue)) {
            HttpSession session = event.getSession();
            loggedInUsers.remove(session);
        }
        event.getSession().getServletContext().setAttribute("count",loggedInUsers.size());

        System.out.println("count:"+loggedInUsers.size());
    }
}
