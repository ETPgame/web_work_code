package Servlet;

import Service.UserService;
import Service.UserServiceImplements;
import entity.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@WebServlet(urlPatterns = "/ShowInfoServlet")
public class ShowInfoServlet extends HttpServlet {
    private UserService userService=new UserServiceImplements();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("执行showinfoservlet方法");

        String username= req.getParameter("username");
        int id=userService.findIdByUsername(username);
        User user=userService.findUserById2(id);

        if (user.getPicture()==null){
            // 获取 ServletContext
            ServletContext context = req.getServletContext();

            // 通过 ServletContext 获取图片的输入流
            InputStream defaultImageStream = context.getResourceAsStream("/image/icon.jpg");

            // 将二进制数据写入 ByteArrayOutputStream
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = defaultImageStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();

            // 设置默认图片的二进制数据到 user 对象
            user.setPicture(buffer.toByteArray());
        }

//        System.out.println("showinfoservlet:user:"+user.getUsername()+","+
//                user.getName()+","+user.getSex()+","+user.getAge());

        req.setAttribute("user",user);
        req.getRequestDispatcher("info.jsp").forward(req,resp);
    }
}
