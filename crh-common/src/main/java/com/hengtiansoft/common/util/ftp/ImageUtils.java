package com.hengtiansoft.common.util.ftp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

import org.apache.commons.io.FilenameUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Utils -Image processing (support JDK, GraphicsMagick, ImageMagick)
 * 
 * @author Hengtiansoft Team
 * @version 1.0_beta
 */
public final class ImageUtils {

	private final static Logger	LOGGER		= LoggerFactory.getLogger(ImageUtils.class);

	/**
	 * Processing type
	 */
	private enum Type {

		/** automatic */
		auto,

		/** jdk */
		jdk,

		/**
		 * GraphicsMagick
		 */
		graphicsMagick,

		/**
		 * ImageMagick
		 */
		imageMagick
	}

	/** Processing type */
	private static Type type = Type.auto;

	/** GraphicsMagick program path */
	private static String graphicsMagickPath;

	/** ImageMagick program path */
	private static String imageMagickPath;

	/** background color */
	private static final Color BACKGROUND_COLOR = Color.white;

	/** Target image quality (in the range 0 - 100) */
	private static final int DEST_QUALITY = 88;

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
							break;
						}
					}
				}
			}
		}

		if (imageMagickPath == null) {
			String osName = System.getProperty("os.name").toLowerCase();
			if (osName.indexOf("windows") >= 0) {
				String pathVariable = System.getenv("Path");
				if (pathVariable != null) {
					String[] paths = pathVariable.split(";");
					for (String path : paths) {
						File convertFile = new File(path.trim() + "/convert.exe");
						File compositeFile = new File(path.trim() + "/composite.exe");
						if (convertFile.exists() && compositeFile.exists()) {
							imageMagickPath = path.trim();
							break;
						}
					}
				}
			}
		}

		if (type == Type.auto) {
			try {
				IMOperation operation = new IMOperation();
				operation.version();
				IdentifyCmd identifyCmd = new IdentifyCmd(true);
				if (graphicsMagickPath != null) {
					identifyCmd.setSearchPath(graphicsMagickPath);
				}
				identifyCmd.run(operation);
				type = Type.graphicsMagick;
			} catch (Exception  e1) {
				try {
					IMOperation operation = new IMOperation();
					operation.version();
					IdentifyCmd identifyCmd = new IdentifyCmd(false);
					identifyCmd.run(operation);
					if (imageMagickPath != null) {
						identifyCmd.setSearchPath(imageMagickPath);
					}
					type = Type.imageMagick;
				} catch (Exception e2) {
					type = Type.jdk;
				}
			}
		}
	}

	/**
	 * Can not be instantiated
	 */
	private ImageUtils() {
	}

	/**
	 *
	 * 
	 * @param srcFile
	 *            Source File
	 * @param destFile
	 *            destination File
	 * @param destWidth
	 *            destination Width
	 * @param destHeight
	 *            destination Height
	 */
	public static void zoom(File srcFile, File destFile, int destWidth, int destHeight) {
		Assert.notNull(srcFile);
		Assert.notNull(destFile);
		Assert.state(destWidth > 0);
		Assert.state(destHeight > 0);
		if (type == Type.jdk) {
			Graphics2D graphics2D = null;
			ImageOutputStream imageOutputStream = null;
			ImageWriter imageWriter = null;
			try {
				// BufferedImage srcBufferedImage = ImageIO.read (srcFile); 
			    //this approach will lead to ICC read information is incomplete picture becomes red or black
				
				// New way: Get images to redraw and convert to BufferedImage
				Image srcImg = Toolkit.getDefaultToolkit().getImage(srcFile.getPath());
				BufferedImage srcBufferedImage = toBufferedImage(srcImg);
				
				int srcWidth = srcBufferedImage.getWidth();
				int srcHeight = srcBufferedImage.getHeight();
				int width = destWidth;
				int height = destHeight;
				if (srcHeight >= srcWidth) {
					width = (int) Math.round(((destHeight * 1.0 / srcHeight) * srcWidth));
				} else {
					height = (int) Math.round(((destWidth * 1.0 / srcWidth) * srcHeight));
				}
				BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
				graphics2D = destBufferedImage.createGraphics();
				graphics2D.setBackground(BACKGROUND_COLOR);
				graphics2D.clearRect(0, 0, destWidth, destHeight);
				graphics2D.drawImage(srcBufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), (destWidth / 2) - (width / 2), (destHeight / 2) - (height / 2), null);

				imageOutputStream = ImageIO.createImageOutputStream(destFile);
				imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName())).next();
				imageWriter.setOutput(imageOutputStream);
				ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
				imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				imageWriteParam.setCompressionQuality((float) (DEST_QUALITY / 100.0));
				imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
				imageOutputStream.flush();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			} finally {
				if (graphics2D != null) {
					graphics2D.dispose();
				}
				if (imageWriter != null) {
					imageWriter.dispose();
				}
				if (imageOutputStream != null) {
					try {
						imageOutputStream.close();
					} catch (IOException e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
			}
		} else {
			IMOperation operation = new IMOperation();
			operation.thumbnail(destWidth, destHeight);
			operation.gravity("center");
			operation.background(toHexEncoding(BACKGROUND_COLOR));
			operation.extent(destWidth, destHeight);
			operation.quality((double) DEST_QUALITY);
			operation.addImage(srcFile.getPath());
			operation.addImage(destFile.getPath());
			if (type == Type.graphicsMagick) {
				ConvertCmd convertCmd = new ConvertCmd(true);
				if (graphicsMagickPath != null) {
					convertCmd.setSearchPath(graphicsMagickPath);
				}
				try {
					convertCmd.run(operation);
				} catch (IOException | InterruptedException | IM4JavaException e) {
					LOGGER.error(e.getMessage(), e);
				}
			} else {
				ConvertCmd convertCmd = new ConvertCmd(false);
				if (imageMagickPath != null) {
					convertCmd.setSearchPath(imageMagickPath);
				}
				try {
					convertCmd.run(operation);
				} catch (IOException | InterruptedException | IM4JavaException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * initialization
	 */
	public static void initialize() {
	}

	/**
	 * Convert color to hex code
	 * 
	 * @param color
	 *            
	 * @return Hexadecimal code
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
	
	/**
	 * Image object is converted to bufferedImage
	 * @param image
	 * @return
	 */
	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();

		// Determine if the image has transparent pixels; for this method's
		// implementation, see e661 Determining If an Image Has Transparent Pixels
		// boolean hasAlpha = hasAlpha(image);

		// Create a buffered image with a format that's compatible with the screen
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			/*
			 * if (hasAlpha) { transparency = Transparency.BITMASK; }
			 */

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			// int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
			/*
			 * if (hasAlpha) { type = BufferedImage.TYPE_INT_ARGB; }
			 */
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}

}
