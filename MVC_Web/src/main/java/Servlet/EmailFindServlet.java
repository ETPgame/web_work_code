package Servlet;

import Service.EmailService;
import Service.EmailServiceImpl;
import Service.UserService;
import Service.UserServiceImplements;
import entity.Email;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/findEmailServlet")
public class EmailFindServlet extends HttpServlet {

    UserService userService=new UserServiceImplements();
    EmailService emailService=new EmailServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("执行findEmailServlet方法");
        // 获取参数值
        String username = req.getParameter("username");
        String id= String.valueOf(userService.findIdByUsername(username));
        System.out.println("username:"+username);
        System.out.println("id:"+id);

        List<Email> emails=emailService.findEmailByRecipient(id);

        System.out.println(emails);

        req.setAttribute("emails",emails);
        req.getRequestDispatcher("email.jsp").forward(req,resp);

    }
}
