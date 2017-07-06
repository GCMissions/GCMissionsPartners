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
 * Class Name: Im4javaUtil Description: Cut the picture
 * 
 * @author taochen
 *
 */
public final class Im4javaUtil {

    /** GraphicsMagick program path */
    private static String graphicsMagickPath;

    /** background color */
    private static final Color BACKGROUND_COLOR = Color.white;

    /** Target image quality (in the range 0 - 100) */
    private static final int DEST_QUALITY = 88;
    private static final Logger log = LoggerFactory.getLogger(Im4javaUtil.class);

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
     * Can not be instantiated
     */
    private Im4javaUtil() {
    }

    /**
     * The same proportion of image scaling
     * 
     * @param srcFile
     * 
     * @param destFile
     * 
     * @param destWidth
     * 
     * @param destHeight
     * 
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
            log.error("msg", e);
        } catch (InterruptedException e) {
            log.error("msg", e);
        } catch (IM4JavaException e) {
            log.error("msg", e);
        }
    }

    /**
     * initialization
     */
    public static void initialize() {
    }

    /**
     * The conversion color is hexadecimal
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
}
