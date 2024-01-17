package Servlet;

import Service.EmailService;
import Service.EmailServiceImpl;
import Service.UserService;
import Service.UserServiceImplements;
import utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/sendEmailServlet")
public class EmailSendServlet extends HttpServlet {
    UserService userService=new UserServiceImplements();
    EmailService emailService=new EmailServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sender=req.getParameter("sender");
        sender= String.valueOf(userService.findIdByUsername(sender));
        String recipient = req.getParameter("recipient");
        if(userService.findUserByName(recipient)){
            recipient= String.valueOf(userService.findIdByUsername(recipient));
            String subject = req.getParameter("subject");
            String content = req.getParameter("content");
            Connection conn= JDBCUtils.getConnection();
            String sql="insert into email(sender,recipient,subject,content) values(?,?,?,?)";
            try {
                PreparedStatement pstmt= conn.prepareStatement(sql);
                pstmt.setString(1,sender);
                pstmt.setString(2,recipient);
                pstmt.setString(3,subject);
                pstmt.setString(4,content);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
