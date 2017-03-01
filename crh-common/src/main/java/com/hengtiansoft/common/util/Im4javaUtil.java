package com.hengtiansoft.common.util;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
* Class Name: Im4javaUtil
* Description: 裁剪图片
* @author quanjunlin
*
*/
public final class Im4javaUtil {

	/** GraphicsMagick程序路径 */
	private static String graphicsMagickPath;

	/** 背景颜色 */
	private static final Color BACKGROUND_COLOR = Color.white;

	/** 目标图片品质(取值范围: 0 - 100) */
	private static final int DEST_QUALITY = 88;
	private static final Logger log= LoggerFactory.getLogger(Im4javaUtil.class);

	static {
		if (graphicsMagickPath == null) {
			String osName = System.getProperty("os.name").toLowerCase();
			if (osName.indexOf("windows") >= 0) {
				String pathVariable = System.getenv("Path");
				if (pathVariable != null) {
					String[] paths = pathVariable.split(";");
					for (String path : paths) {
						File gmFile = new File(path.trim() + "/gm.exe");
						File gmdisplayFile = new File(path.trim() + "/gmdisplay.exe");
						if (gmFile.exists() && gmdisplayFile.exists()) {
							graphicsMagickPath = path.trim();
							System.out.println("GraphicsMagick install success!");
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * 不可实例化
	 */
	private Im4javaUtil() {
	}

	/**
	 * 等比例图片缩放
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 * @param destWidth
	 *            目标宽度
	 * @param destHeight
	 *            目标高度
	 */
	public static void zoom(File srcFile, File destFile, int destWidth, int destHeight) {
		Assert.notNull(srcFile);
		Assert.notNull(destFile);
		Assert.state(destWidth > 0);
		Assert.state(destHeight > 0);
		IMOperation operation = new IMOperation();
		operation.thumbnail(destWidth, destHeight);
		operation.gravity("center");
		operation.background(toHexEncoding(BACKGROUND_COLOR));
		operation.extent(destWidth, destHeight);
		operation.quality((double) DEST_QUALITY);
		operation.addImage(srcFile.getPath());
		operation.addImage(destFile.getPath());
		ConvertCmd convertCmd = new ConvertCmd(true);
		if (graphicsMagickPath != null) {
			convertCmd.setSearchPath(graphicsMagickPath);
		}
		try {
			convertCmd.run(operation);
		} catch (IOException e) {
		    log.error("msg",e);
		} catch (InterruptedException e) {
		    log.error("msg",e);
		} catch (IM4JavaException e) {
		    log.error("msg",e);
		}
	}

	/**
	 * 初始化
	 */
	public static void initialize() {
	}

	/**
	 * 转换颜色为十六进制代码
	 * 
	 * @param color
	 *            颜色
	 * @return 十六进制代码
	 */
	private static String toHexEncoding(Color color) {
		String R, G, B;
		StringBuffer stringBuffer = new StringBuffer();
		R = Integer.toHexString(color.getRed());
		G = Integer.toHexString(color.getGreen());
		B = Integer.toHexString(color.getBlue());
		R = R.length() == 1 ? "0" + R : R;
		G = G.length() == 1 ? "0" + G : G;
		B = B.length() == 1 ? "0" + B : B;
		stringBuffer.append("#");
		stringBuffer.append(R);
		stringBuffer.append(G);
		stringBuffer.append(B);
		return stringBuffer.toString();
	}
}
