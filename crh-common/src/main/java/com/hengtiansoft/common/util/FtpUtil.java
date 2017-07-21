package com.hengtiansoft.common.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtiansoft.common.pool.ftpPool.FtpClientPoolFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Class Name: FtpUtil Description: ftp tool class
 * 
 * @author taochen
 */
public class FtpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpUtil.class);

    /**
     * Description: upload files
     *
     * @param fileName
     * @param input
     * @param image
     * @param imagePath
     * @throws IOException
     * @throws Exception
     */
    public static void upLoad(String fileName, InputStream input, String image, String imagePath) throws IOException {
        FtpClientPoolFactory pool = ApplicationContextUtil.getBean(FtpClientPoolFactory.class);
        FTPClient ftpClient = pool.getConnection();
        try {
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setRemoteVerificationEnabled(false);
            changeWorkingDirectory(ftpClient, image + imagePath);
            ftpClient.storeFile(fileName, input);
        } finally {
            pool.releaseConnection(ftpClient);
        }
    }

    /**
     * Description: upload files
     *
     * @param fileName
     * @param input
     * @param image
     * @param imagePath
     */
    public static String upLoadImage(String fileName, InputStream input, String image, String imagePath, String FTPIP) {
        FtpClientPoolFactory pool = ApplicationContextUtil.getBean(FtpClientPoolFactory.class);
        FTPClient ftpClient = pool.getConnection();
        SimpleDateFormat sm = new SimpleDateFormat("yyyyMM");
        String date = sm.format(new Date());
        try {
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setRemoteVerificationEnabled(false);
            changeWorkingDirectory(ftpClient, image + imagePath);
            FTPFile[] files = ftpClient.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    // If the folder does not have the current month, it is created
                    String name = files[i].getName();
                    if (!name.equals(date)) {
                        ftpClient.makeDirectory(date);
                        changeWorkingDirectory(ftpClient, image + imagePath + "/" + date);
                        ftpClient.storeFile(fileName, input);
                        break;
                    } else {
                        changeWorkingDirectory(ftpClient, image + imagePath + "/" + date);
                        ftpClient.storeFile(fileName, input);
                        break;
                    }

                } else {
                    ftpClient.makeDirectory(date);
                    changeWorkingDirectory(ftpClient, image + imagePath + "/" + date);
                    ftpClient.storeFile(fileName, input);
                    break;
                }
            }

        } catch (Exception e) {
            LOGGER.error("File upload failed", e);
        } finally {
            pool.releaseConnection(ftpClient);
        }
        return date;
    }

    public static void upload(String path, Map<String, Object> model, Template template) {
        FtpClientPoolFactory pool = ApplicationContextUtil.getBean(FtpClientPoolFactory.class);
        FTPClient ftpClient = pool.getConnection();

        String directory = StringUtils.substringBeforeLast(path, "/");
        String[] paths = StringUtils.split(directory, "/");
        String filePath = path.substring(path.lastIndexOf('/') + 1);
        StringBuilder workingDirectory = new StringBuilder("/");
        String oldDir;
        for (String pathName : paths) {
            oldDir = workingDirectory.toString();
            workingDirectory.append(pathName);
            workingDirectory.append("/");
            changeWorkingDirectory(ftpClient, workingDirectory.toString());
            /*
             * sb.append(pathName) .append("/"); workingDirectory=sb.toString();
             */
        }

        changeWorkingDirectory(ftpClient, workingDirectory.toString());
        putStream(pool, ftpClient, filePath, true, model, template);

    }

    public static void putStream(FtpClientPoolFactory pool, FTPClient ftpClient, String remoteAbsoluteFile,
            boolean autoClose, Map<String, Object> model, Template template) {
        Writer writer = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            // Transmission processing
            outputStreamWriter = new OutputStreamWriter(ftpClient.storeFileStream(remoteAbsoluteFile), "UTF-8");
            writer = new BufferedWriter(outputStreamWriter);
            template.process(model, writer);
            writer.flush();
        } catch (IOException | TemplateException e) {
            LoggerFactory.getLogger(FtpUtil.class).error("Could not put file to server.", e);
        } finally {
            IOUtils.closeQuietly(writer);
            IOUtils.closeQuietly(outputStreamWriter);
            if (autoClose) {
                // Disconnect
                pool.releaseConnection(ftpClient);
            }
        }
    }

    /**
     * Description: delete file
     *
     * @param fileName
     * @param image
     * @param imagePath
     */
    public static void delete(String fileName, String image, String imagePath) {
        FtpClientPoolFactory pool = ApplicationContextUtil.getBean(FtpClientPoolFactory.class);
        FTPClient ftpClient = pool.getConnection();
        try {
            changeWorkingDirectory(ftpClient, image + imagePath);
            ftpClient.deleteFile(fileName);
        } catch (Exception e) {
            LOGGER.error("File deletion failed", e);
        } finally {
            pool.releaseConnection(ftpClient);
        }
    }

    /**
     * Description: Directory switch
     *
     * @param ftpClient
     * @param targetPath
     */
    private static void changeWorkingDirectory(FTPClient ftpClient, String targetPath) {
        ftpClient.enterLocalPassiveMode();
        try {
            boolean result = ftpClient.changeWorkingDirectory(targetPath);
            if (!result) {
                ftpClient.changeWorkingDirectory("/");
                String[] paths = targetPath.split("/");
                for (int i = 0; i < paths.length; i++) {
                    ftpClient.makeDirectory(paths[i]);
                    ftpClient.changeWorkingDirectory(paths[i]);
                }
                if (!ftpClient.changeWorkingDirectory(targetPath)) {
                    LOGGER.error("Change job target directory failed");
                    return;
                }
            }
        } catch (IOException e) {
            LOGGER.error("Change job target directory failed", e);
        }
        LOGGER.debug("Change the target directory of the job");
    }
}
