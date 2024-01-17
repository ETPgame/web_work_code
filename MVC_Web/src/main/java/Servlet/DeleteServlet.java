package Servlet;

import Service.UserService;
import Service.UserServiceImplements;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/delete")
public class DeleteServlet extends HttpServlet {
    private UserService userService=new UserServiceImplements();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("turn to DeleteServlet ok!");

        int id=Integer.parseInt(req.getParameter("id"));
        userService.deleteUserById(id);
        String targetUrl="welcome.jsp?defaultSection=section2";
        resp.sendRedirect(req.getContextPath()+targetUrl);

    }
}
