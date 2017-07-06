package com.hengtiansoft.church.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
/**
 * 
* Class Name: FtpUtil
* Description: 
* @author taochen
*
 */
public class FtpUtil {
    
    private static FTPClient ftpClient = null;
    private static String ip;
    private static int port;
    private static String userName;
    private static String userPassword;
    private static String prefix;
    
    static{
        Map<String, Object> pro = PropertiesParse.properties2Map("/env/env-var.properties");
        ip = (String)pro.get("ftp.server.ip");
        port = Integer.parseInt((String)pro.get("ftp.server.port"));
        userName = (String)pro.get("ftp.server.userName");
        userPassword = (String)pro.get("ftp.server.userPwd");
        prefix = (String)pro.get("ftp.prefix");
    }
    
    /**
     * connect to the server
     * @return  true:success， false:failure
     */
    public static boolean open() {
        if (ftpClient != null && ftpClient.isConnected()) {
            return true;
        }
        try {
            ftpClient = new FTPClient();
            // connect
            ftpClient.connect(ip, port);
            ftpClient.login(userName, userPassword);
            // Check whether the connection was successful
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                close();
                System.exit(1);
            }
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // Set the upload mode.binally  or ascii
            return true;
        } catch (Exception ex) {
            close();
            return false;
        }
    }
    
    /**
     * Upload the file to the FTP server
     * @param localDirectoryAndFileName
     * @param ftpFileName   Uploaded to the server's file name
     * @param ftpDirectory  FTP directory such as: / path1 / pathb2 /, if the directory does not exist will automatically create the directory
     * @return
     */
    public static boolean upload(InputStream fis, String ftpFileName, String ftpDirectory) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        boolean flag = false;
        if (ftpClient != null) {
            try {
                //Create a directory (created when it does not exist)
                mkDir(ftpDirectory);
                ftpClient.setBufferSize(100000);
                ftpClient.setControlEncoding("UTF-8");
                // Set the type of file (binary)
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                // upload
                flag = ftpClient.storeFile(new String(ftpFileName.getBytes(), "iso-8859-1"), fis);
            } catch (Exception e) {
                close();
                return false;
            } finally {
                try {
                    fis.close();
                    close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
    
    /**
     * Loop to create the directory, complete the creation of the directory,
     *  set the working directory for the current directory created
     * @param ftpPath  Need to create the directory
     * @return
     */
    public static boolean mkDir(String ftpPath) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            char[] chars = ftpPath.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {
                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpPath = sbStr.toString();
            if (ftpPath.indexOf('/') == -1) {
                // There is only one directory
                ftpClient.makeDirectory(new String(ftpPath.getBytes(), "iso-8859-1"));
                ftpClient.changeWorkingDirectory(new String(ftpPath.getBytes(), "iso-8859-1"));
            } else {
                // Loop to create a multi-level directory
                String[] paths = ftpPath.split("/");
                for (int i = 0; i < paths.length; i++) {
                    ftpClient.makeDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
                    ftpClient.changeWorkingDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * get url
    * Description: 
    *
    * @param key
    * @return
     */
    public static String getUrl(String key){
        return prefix + key;
    }
    
    
    /**
     * Close the connection
     */
    public static void close() {
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
