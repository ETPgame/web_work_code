package Servlet;

import Service.UserService;
import Service.UserServiceImplements;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

@WebServlet(value = "/insertServlet")
public class InsertServlet extends HttpServlet {
    private UserService userService=new UserServiceImplements();
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("turn to insertServlet ok!");

        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String email=req.getParameter("email");

        System.out.println(username);//null
        System.out.println(password);
        System.out.println(email);

        if (email == null && username==null&&password==null) {

            // 从请求的 Reader 流中读取 JSON 数据
            BufferedReader reader = req.getReader();
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

//            System.out.println(jsonContent);


            // 解析 JSON 数据
            User user = objectMapper.readValue(jsonContent.toString(), User.class);

            // 获取参数
            username = user.getUsername();
            password = user.getPassword();
            email = user.getEmail();

            System.out.println(username);
            System.out.println(password);
            System.out.println(email);

        }

        User user= new User(username,password,email);
        try {
            userService.register(user);
            String targetUrl="welcome.jsp?defaultSection=section2";
            resp.sendRedirect(req.getContextPath()+targetUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
