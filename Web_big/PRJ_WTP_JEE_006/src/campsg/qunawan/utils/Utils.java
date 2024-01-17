package campsg.qunawan.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;

public class Utils {

	/**
	 * 图像切割(按指定起点坐标和宽高切割)
	 * @param bi 需要裁剪的BufferedImage图
	 * @param x1 起点坐标X
	 * @param y1 起点坐标Y
	 * @param width 目标宽度
	 * @param height 目标高度
	 */
	public static BufferedImage cut(BufferedImage bi, int x1, int y1, int width, int height) {
		int srcWidth = bi.getWidth();
		int srcHeight = bi.getHeight();
		if (srcWidth <= 0 || srcHeight <= 0)
			return null;

		// 获取传入的Image资源图
		Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
		ImageFilter cropFilter = new CropImageFilter(x1, y1, width, height);
		// Image进行裁剪
		Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);

		// 创建画布，并绘制Image
		Graphics g = result.getGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		g.dispose();

		return result;
	}
}
