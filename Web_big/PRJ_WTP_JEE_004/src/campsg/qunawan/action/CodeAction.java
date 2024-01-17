package campsg.qunawan.action;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class CodeAction extends HttpServlet {

    private String codeNumbers = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("执行CodeAction方法");

        BufferedImage image = new BufferedImage(Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        drawRandomBackground(g);

        drawCode(g);

        System.out.println("验证码字符串: " + codeNumbers);

        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(image, "GIF", sos);
        sos.close();

        codeNumbers="";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    // 3-1.组建验证码字符串
    private void drawCode(Graphics g) {
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            char codeChar = Constants.IMAGE_CHAR.charAt(random.nextInt(Constants.IMAGE_CHAR.length()));

            codeNumbers += codeChar;

            g.setFont(Constants.codeFont[i]);
            g.setColor(Constants.color[i]);

            g.drawString(String.valueOf(codeChar), 20 + i * 20, 30);
        }
    }

    private void drawRandomBackground(Graphics g) {
        g.setColor(getRandColor());

        g.fillRect(0, 0, Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT);
    }

    private Color getRandColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        return new Color(red, green, blue);
    }
}
