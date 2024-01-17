package campsg.qunawan.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter implements Filter {
    String encoding="";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("EncodingFilter被创建");

        encoding = filterConfig.getInitParameter("encoding");
        System.out.println("encoding:"+encoding);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 设置请求和响应的字符编码为配置的编码集
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        System.out.println("EncodingFilter获得请求："+request.getRequestURI());


        // 继续执行过滤链的下一个组件
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
