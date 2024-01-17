package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter",urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("LoginFilter init()");
    }

    @Override
    public void destroy() {
        System.out.println("LoginFilter destroy()");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI().substring(request.getContextPath().length());

        // Exclude checkCodeServlet and login-related requests from filtering
        if (path.endsWith("/checkCodeServlet") || path.endsWith("login.jsp") ||
                path.endsWith("login")||path.endsWith("loginServlet")||
                path.endsWith("register.jsp") || path.endsWith("registerSuccess.jsp")||
                path.endsWith("registerServlet")||path.endsWith("userExistServlet")||
                path.endsWith("image/1.jpg")||path.endsWith("JS/city.js")||
                path.endsWith("css/login.css")||path.endsWith("JS/register.js")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession();
        String username=(String) session.getAttribute("username");

        if(username==null||"".equals(username)){
            System.out.println("拦截网页...");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }else {
            filterChain.doFilter(request,response);
        }
    }
}
