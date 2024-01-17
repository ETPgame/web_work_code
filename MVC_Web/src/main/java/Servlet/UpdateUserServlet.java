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
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(urlPatterns = "/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    private UserService userService=new UserServiceImplements();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("turn to the updateUserServlet ok!");

        req.setCharacterEncoding("UTF-8");
        int id=Integer.parseInt(req.getParameter("id"));
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String email=req.getParameter("email");
        User user=new User(id,username,password,email);
        userService.updateUser(user);

    }
}
