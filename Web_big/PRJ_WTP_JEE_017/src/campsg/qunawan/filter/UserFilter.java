package campsg.qunawan.filter;

import campsg.qunawan.entity.User;
import com.sun.deploy.util.SessionState;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

public class UserFilter implements Filter {

    String[] checkedlist;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("UserFilter被创建");
        String checked=filterConfig.getInitParameter("checked");
        checkedlist=checked.split(";");

        ServletContext servletContext = filterConfig.getServletContext();

        checkedlist = Arrays.stream(checkedlist)
                .map(uri -> servletContext.getContextPath() + uri)
                .toArray(String[]::new);

        System.out.println(Arrays.toString(checkedlist));

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;



        String uri= request.getRequestURI();
        System.out.println("UserFilter获得请求："+uri);

        if (Arrays.asList(checkedlist).contains(uri)){
            HttpSession session=request.getSession();
            User user= (User) session.getAttribute("user");
            System.out.println(user);
            if (user == null){
//                response.sendRedirect("/login.jsp");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }else {
                filterChain.doFilter(request,response);
            }
        }

        filterChain.doFilter(request,response);


    }

    @Override
    public void destroy() {

    }
}
