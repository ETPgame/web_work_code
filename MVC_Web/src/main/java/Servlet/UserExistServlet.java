package Servlet;

import Service.UserService;
import Service.UserServiceImplements;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/userExistServlet")
public class UserExistServlet extends HttpServlet {
    private UserService userService=new UserServiceImplements();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("turn to userExistServlet ok!");

        String username=req.getParameter("username");
        boolean flag=userService.findUserByName(username);
        if(flag){
            resp.getWriter().write("true");
        }
        else{
            resp.getWriter().write("false");
        }
    }
}
