package campsg.qunawan.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CropAction extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("执行CropAction方法");

        InputStream is= req.getPart("image").getInputStream();

        String filePath = req.getServletContext().getRealPath("/user_img/1.jpg");

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] buf = new byte[1024];
            int i;

            while ((i = is.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        }

        resp.sendRedirect("/personal_information.html");



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
