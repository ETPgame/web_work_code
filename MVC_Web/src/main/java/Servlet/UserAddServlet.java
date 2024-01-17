package Servlet;

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


@WebServlet(urlPatterns = "/UserAddServlet")
public class UserAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        Connection conn= JDBCUtils.getConnection();
        String sql="insert into user(username,password,email) values(?,?,?)";
        try {
            PreparedStatement pstmt= conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            pstmt.setString(3,email);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
