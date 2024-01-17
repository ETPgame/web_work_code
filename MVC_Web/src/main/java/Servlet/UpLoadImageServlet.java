package Servlet;

import Service.UserService;
import Service.UserServiceImplements;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@WebServlet(urlPatterns = "/uploadImageServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 2,    // 2 MB
        maxRequestSize = 1024 * 1024 * 3   // 3 MB
)
public class UpLoadImageServlet extends HttpServlet {
    private UserService userService = new UserServiceImplements();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        System.out.println("执行uploadImageServlet方法");


        String username = req.getParameter("username");
        String name = req.getParameter("name");
        int age = Integer.parseInt(req.getParameter("age"));
        String sex = req.getParameter("sex");

        System.out.println("username:"+username);
        System.out.println("name:"+name);
        System.out.println("age:"+age);
        System.out.println("sex:"+sex);

        // 获取上传的文件 Part
        Part filePart = req.getPart("picture");

        // 从 Part 中获取输入流
        InputStream pictureStream = filePart.getInputStream();

        // 将输入流中的数据读取为字节数组
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = pictureStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();

        // 获取字节数组
        byte[] picture = buffer.toByteArray();

        // 使用 Base64 编码将 byte 数组转换为字符串
        String base64String = Base64.getEncoder().encodeToString(picture);

        System.out.println(base64String);

        // 创建 User 对象
        User user = new User(username, name, age, sex, picture);

        // 调用 UserService 更新用户信息
        userService.updateInformation(user);

        System.out.println("uploadImageServlet:"+user);
        // 跳转到信息页面
        req.getRequestDispatcher("info.jsp").forward(req, resp);
    }

}
