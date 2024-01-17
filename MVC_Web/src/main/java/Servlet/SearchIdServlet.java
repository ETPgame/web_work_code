package Servlet;

import Service.UserService;
import Service.UserServiceImplements;
import com.google.gson.Gson;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/SearchId")
public class SearchIdServlet extends HttpServlet {
    private UserService userService=new UserServiceImplements();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("turn to SearchIdServlet ok!");

        int id = Integer.parseInt(req.getParameter("id"));
        User user = userService.findUserById(id);
        PrintWriter writer = resp.getWriter();
        writer.write("<input type=\"hidden\" id=\"idd\" value=\"" + user.getId() + "\"><br>\n");
        writer.write("用户名：<input type=\"text\" id=\"username2\" value=\"" + user.getUsername() + "\"><br>\n");
        writer.write("密码：<input type=\"password\" id=\"password2\" value=\"" + user.getPassword() + "\"><br>\n");
        writer.write("邮箱：<input type=\"text\" id=\"email2\" value=\"" + user.getEmail() + "\"><br>");



    }
}
