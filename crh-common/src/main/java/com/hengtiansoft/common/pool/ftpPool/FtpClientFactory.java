package com.hengtiansoft.common.pool.ftpPool;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* Class Name: FtpClientFactory
* Description: Ftp Connection pool factory
* @author taochen
*
*/
public class FtpClientFactory extends BasePooledObjectFactory<FTPClient> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpClientFactory.class);

    private FtpInfo             ftpInfo;

    public void setFtpInfo(FtpInfo ftpInfo) {
        this.ftpInfo = ftpInfo;
    }

    @Override
    public FTPClient create() {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpInfo.getFtpHost(), ftpInfo.getFtpPort());// Connect to the FTP server
            ftpClient.login(ftpInfo.getFtpUserName(), ftpInfo.getFtpPassword());// Log in to the FTP server
            LOGGER.debug("IP:" + ftpInfo.getFtpHost() + ", Port：" + ftpInfo.getFtpPort() + "，UserName：" + ftpInfo.getFtpUserName() + "，Password" + ftpInfo.getFtpPassword());
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                LOGGER.info("FTP connection is successful");
            } else {
                ftpClient.disconnect();
                LOGGER.error("Not connected to FTP, username or password is incorrect");
            }
        } catch (SocketException e) {
            LOGGER.error("IP address of the FTP may be wrong, please correct configuration", e);
        } catch (IOException e) {
            LOGGER.error("msg",e);
        }
        return ftpClient;
    }

    @Override
    public PooledObject<FTPClient> wrap(FTPClient obj) {
        return new DefaultPooledObject<FTPClient>(obj);
    }

    @Override
    public void destroyObject(PooledObject<FTPClient> p) {
        try {
            p.getObject().disconnect();
            LOGGER.debug("FTP connection is off");
        } catch (IOException e) {
            LOGGER.error("FTP Close connection error", e);
        }
    }

    @Override
    public boolean validateObject(PooledObject<FTPClient> p) {
        FTPClient ftpClient = p.getObject();
        if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            return true;
        }
        return false;
    }

}
