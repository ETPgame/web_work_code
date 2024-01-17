package campsg.qunawan.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SetCharacterEncodingFilter implements Filter {
	protected String encoding = null;
	protected FilterConfig filterConfig = null;

	@Override
	public void destroy() {
		// 过滤器销毁
		this.encoding = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/* *****实训场景024：防乱码过滤器【start】****** */
		// 判断当前请求是否已经独立设置了编码格式
		if (request.getCharacterEncoding() == null) {
			String encoding = this.encoding;
			if (encoding != null) {
				// 设置request和reponse编码格式为utf-8
				request.setCharacterEncoding(encoding);
				response.setCharacterEncoding(encoding);
			}
		}
		// 过滤器执行结束，转发至下一个过滤器
		chain.doFilter(request, response);
		/* *****实训场景024：防乱码过滤器 【end】****** */
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		/* *****实训场景024：防乱码过滤器 【start】****** */
		// 获取初始化参数
		this.encoding = filterConfig.getInitParameter("encoding");
		/* *****实训场景024：防乱码过滤器 【end】****** */
	}
}
