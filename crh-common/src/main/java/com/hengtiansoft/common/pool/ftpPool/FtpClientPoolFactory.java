package com.hengtiansoft.common.pool.ftpPool;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Name: FtpClientFactory Description: Ftp Connection pool factory
 * 
 * @author taochen
 *
 */
public class FtpClientPoolFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpClientPoolFactory.class);

    private GenericObjectPool<FTPClient> pool;

    public FtpClientPoolFactory(FtpClientFactory factory, GenericObjectPoolConfig config) {
        pool = new GenericObjectPool<FTPClient>(factory, config);
    }

    public synchronized FTPClient getConnection() {
        FTPClient ftpClient = null;
        try {
            ftpClient = pool.borrowObject();
            if (ftpClient == null) {
                LOGGER.error("Failed to get FTP connection");
            } else {
                LOGGER.info("Get FTP connection successful");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to get FTP connection");
        }
        return ftpClient;
    }

    public void releaseConnection(FTPClient ftClient) {
        pool.returnObject(ftClient);
    }

}
