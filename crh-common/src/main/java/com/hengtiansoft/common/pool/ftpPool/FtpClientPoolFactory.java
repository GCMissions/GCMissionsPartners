/*
 * Project Name: zc-collect-common
 * File Name: FtpClientPool.java
 * Class Name: FtpClientPool
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.common.pool.ftpPool;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* Class Name: FtpClientFactory
* Description: Ftp连接池工厂
* @author jialiangli
*
*/
public class FtpClientPoolFactory {

    private static final Logger          LOGGER = LoggerFactory.getLogger(FtpClientPoolFactory.class);

    private GenericObjectPool<FTPClient> pool;

    public FtpClientPoolFactory(FtpClientFactory factory, GenericObjectPoolConfig config) {
        pool = new GenericObjectPool<FTPClient>(factory, config);
    }

    public synchronized FTPClient getConnection() {
        FTPClient ftpClient = null;
        try {
            ftpClient = pool.borrowObject();
            if (ftpClient == null) {
                LOGGER.error("获得FTP连接失败");
            } else {
                LOGGER.info("获得FTP连接成功");
            }
        } catch (Exception e) {
            LOGGER.error("获得FTP连接失败");
        }
        return ftpClient;
    }

    public void releaseConnection(FTPClient ftClient) {
        pool.returnObject(ftClient);
    }

}
