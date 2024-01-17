package Servlet;

import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/quitServlet")
public class QuitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("turn to QuitServlet ok!");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            session.removeAttribute("loggedInUser");

            // 在用户退出时将 isAuthenticated 设置为 false
            session.setAttribute("isAuthenticated", false);

            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}
