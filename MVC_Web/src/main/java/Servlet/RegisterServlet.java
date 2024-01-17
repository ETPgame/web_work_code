package Servlet;

import Service.UserService;
import Service.UserServiceImplements;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "registerServlet", value = "/registerServlet")
public class RegisterServlet extends HttpServlet {

    private UserService userService=new UserServiceImplements();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("turn to registerServlet ok!");

        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String email=req.getParameter("email");

        User user=new User(username,password,email);
        try {
            userService.register(user);
//            HttpSession session= req.getSession();
//            session.setAttribute("username",user.getUsername());
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
