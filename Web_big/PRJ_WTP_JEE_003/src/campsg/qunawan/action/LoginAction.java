package campsg.qunawan.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAction extends HttpServlet {

    private String successUrl;
    private String errorUrl;

    @Override
    public void init() throws ServletException {
        super.init();

        // 通过ServletConfig对象获取正确跳转连接和错误跳转连接
        successUrl = getServletConfig().getInitParameter("index_url");
        errorUrl = getServletConfig().getInitParameter("error_url");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("执行LoginAction方法");

        // 获取表单中的name和password值
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        System.out.println("name:"+name);
        System.out.println("password:"+password);

        if ("18701721202".equals(name)&&"123456".equals(password)){
            resp.sendRedirect(successUrl);
        }else {
            resp.sendRedirect(errorUrl);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
