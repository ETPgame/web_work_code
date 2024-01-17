package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter",urlPatterns = "/*")
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("EncodingFilter init()");
    }

    @Override
    public void destroy() {
        System.out.println("EncodingFilter destroy()");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        request.setCharacterEncoding("utf-8");
        response.setHeader("content-type","text/html;charset=utf-8");
        filterChain.doFilter(request,response);

    }

}
