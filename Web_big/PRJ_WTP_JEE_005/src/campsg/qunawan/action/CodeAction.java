package campsg.qunawan.action;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import campsg.qunawan.action.Constant.Constants;
import campsg.qunawan.action.Constant.Globle;

/**
 * 输出：随机验证码图片
 */
public class CodeAction extends HttpServlet {

	private static final long serialVersionUID = 1736370958785036518L;
	
	protected static String codeNumbers = "";
	private Globle Global;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 创建一个图像,验证码显示的图片大小
		BufferedImage image = new BufferedImage(Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);

		// 获取图形绘制对象
		Graphics g = image.getGraphics();

		// 绘制图片背景颜色
		g.setColor(createRandomColor(200, 250));

		// 绘制背景图片
		g.fillRect(0, 0, Constants.IMAGE_WIDTH, Constants.IMAGE_HEIGHT);

		// 循环产生4位随机数
		for (int i = 0; i < 4; i++)
			drawString(g, i);
		
		/** PRJ-WTP-JEE-005：校验验证码 保存验证码 【Start】 **/
		System.out.println(codeNumbers);
		Globle.setCode(codeNumbers);
		/** PRJ-WTP-JEE-005：校验验证码 保存验证码 【End】 **/
		
		// 重设字符串
		codeNumbers = "";
		// 利用ImageIO类的write方法对图像进行编码
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(image, "GIF", sos);
		sos.close();
	}

	/**
	 * 产生随即号
	 * 
	 * @param graphics
	 *            绘图对象
	 * @param i
	 *            随机数序号
	 */
	private void drawString(Graphics graphics, int i) {
		Random random = new Random();
		// 产生随即切割序号
		/* 0-61.9999 */
		Integer j = random.nextInt((Constants.IMAGE_CHAR.length()));
		// 切割随机数
		String number = Constants.IMAGE_CHAR.substring(j, j + 1);
		graphics.setFont(Constants.codeFont[i]);
		graphics.setColor(Constants.color[i]);
		// 绘制验证码到图片X、Y（每个字体x每次步进13的倍数，y不变，大小6*6）
		graphics.drawString(number, 20 + i * 20, 30);

		codeNumbers += number;
	}


	/**
	 * 产生随即背景色
	 * 
	 * @param fc
	 *            背景色色调边界
	 * @param bc
	 *            背景色色调边界
	 * @return 背景色
	 */
	private Color createRandomColor(int fc, int bc) {// 给定范围获得随机颜色
		// 随机对象
		Random random = new Random();
		// 随机初始数值不得大于255
		if (fc > 255)
			fc = 255;
		// 随机初始数值不得大于255
		if (bc > 255)
			bc = 255;
		// 产生随机红蓝绿色调
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
