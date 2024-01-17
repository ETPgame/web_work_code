package campsg.qunawan.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import campsg.qunawan.entity.TripPicture;

/**
 * 工具类，提供一些静态的功能函数
 */
public class Utils {
	

	public static void initTripPicture(Set<TripPicture> pictures, String basePath) {
		for (TripPicture tp : pictures) {
			String path = basePath + "image_cache\\" + tp.getName();
			if (!new File(path).exists()) {
				Utils.getFile(tp.getData(), path);
			}
		}
	}

	/**
	 * 根据byte数组，生成文件
	 */
	public static void getFile(byte[] bfile, String filePath) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	
}
