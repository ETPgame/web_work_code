package Servlet;

import Service.UserService;
import Service.UserServiceImplements;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "loginServlet", urlPatterns = "/loginServlet")
public class LoginServlet extends HttpServlet {

    private UserService userService=new UserServiceImplements();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String inputCode= req.getParameter("inputCode");

        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        String checkCode= (String) req.getSession().getAttribute("check_code");
        req.getSession().removeAttribute("check_code");

        if (inputCode.equalsIgnoreCase(checkCode)){
            if (userService.login(user)) {
                HttpSession session = req.getSession();

                session.setAttribute("username", username);
                session.setAttribute("loggedInUser", user);
                session.setAttribute("isAuthenticated", true);

                Cookie uCookie = new Cookie("username", username);
                Cookie pCookie = new Cookie("password", password);
                uCookie.setMaxAge(60 * 60 * 24 * 30);
                pCookie.setMaxAge(60 * 60 * 24 * 30);
                resp.addCookie(uCookie);
                resp.addCookie(pCookie);

                req.setAttribute("username", username);

                resp.sendRedirect("welcome.jsp");
            } else {
                req.setAttribute("loginError", "用户名密码错误！");
                req.getRequestDispatcher("/login.jsp").forward(req,resp);
//                resp.sendRedirect("login.jsp");
            }
        }else {
            req.setAttribute("checkCodeError", "验证码错误！");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }
}