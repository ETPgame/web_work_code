package Servlet;

import Service.UserService;
import Service.UserServiceImplements;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserFindAllServlet",value = "/findAllUsers")
public class UserFindAllServlet extends HttpServlet {
    private UserService userService=new UserServiceImplements();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("turn to UserFindAllServlet ok!");
        List<User> users=userService.findAllUsers();
        req.setAttribute("users",users);
        req.getRequestDispatcher("users.jsp").forward(req,resp);
    }
}
