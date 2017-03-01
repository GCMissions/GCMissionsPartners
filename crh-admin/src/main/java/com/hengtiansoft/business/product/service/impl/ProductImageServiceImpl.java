/*
 * Project Name: ecom
 * File Name: ProductImageServiceImpl.java
 * Class Name: ProductImageServiceImpl
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.product.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.hengtiansoft.business.product.service.ProductImageService;
import com.hengtiansoft.common.constant.ImageListConstant;
import com.hengtiansoft.common.dto.ImageDto;
import com.hengtiansoft.common.enumeration.ImageEnum;
import com.hengtiansoft.common.enumeration.ImagePathEnum;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.FtpUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.common.util.ftp.ImageUtils;

@Service("productImageServiceImpl")
public class ProductImageServiceImpl implements ServletContextAware, ProductImageService {

    private static final Logger LOGGER         = LoggerFactory.getLogger(ProductImageServiceImpl.class);

    /** 目标扩展名 */
    private static final String DEST_EXTENSION = "jpg";

    /** servletContext */
    private ServletContext      servletContext;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public String uploadImage(MultipartFile multipartFile, String source) {

        String url = "";
        String value = ImagePathEnum.getValue(source);
        if (WRWUtil.isEmpty(value)) {
            throw new WRWException("上传失败,找不到对应的属性!");
        }
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                String uploadPath = DateTimeUtil.parseDateToString(new Date(), "YYYYMMDD");
                String uuid = UUID.randomUUID().toString();
                String temPath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getContextPath() + "/" + uploadPath + uuid;
                String sourcePath = "/" + uploadPath + uuid + "-source." + DEST_EXTENSION;
                List<ImageDto> list = ImageListConstant.getList(source);
                for (ImageDto dto : list) {
                    dto.setSourcePath(temPath + dto.getSourcePath() + DEST_EXTENSION);
                }
                File tempFile = new File(System.getProperty("java.io.tmpdir") + sourcePath);
                if (!tempFile.getParentFile().exists()) {
                    tempFile.getParentFile().mkdirs();
                }
                multipartFile.transferTo(tempFile);
                List<File> listFile = new ArrayList<File>();
                String tempPath = System.getProperty("java.io.tmpdir");
                try {

                    for (ImageDto dto : list) {
                        File flie = new File(tempPath + "/" + dto.getSourcePath().substring(dto.getSourcePath().lastIndexOf('/') + 1));
                        listFile.add(flie);
                        ImageUtils.zoom(tempFile, flie, dto.getWeiht(), dto.getHeight());
                        FtpUtil.upLoad(
                                tempFile.getName().substring(0, tempFile.getName().indexOf(".jpg")) + "_" + dto.getWeiht() + "*" + dto.getHeight()
                                        + "." + DEST_EXTENSION, new FileInputStream(flie), ImageEnum.PLATFORM.getValue(),
                                "/" + ImagePathEnum.getValue(source));
                    }
                    FtpUtil.upLoad(tempFile.getName(), new FileInputStream(tempFile), ImageEnum.PLATFORM.getValue(),
                            "/" + ImagePathEnum.getValue(source));
                } finally {
                    FileUtils.deleteQuietly(tempFile);
                    for (File file1 : listFile) {
                        FileUtils.deleteQuietly(file1);
                    }
                }
                url = "/image" + ImagePathEnum.getValue(source) + sourcePath;
            } catch (Exception e) {
                LOGGER.error("msg", e);
            }
        }
        return url;
    }

    @Override
    public String addImage(MultipartFile file, String source) {
        int index = file.getOriginalFilename().lastIndexOf(".");
        String fileName = file.getOriginalFilename().substring(index + 1).toLowerCase();
        if (!"ogg".equals(fileName) && !"mpeg4".equals(fileName) && !"webm".equals(fileName) && !"mp4".equals(fileName) && !"wmv".equals(fileName)) {
            throw new WRWException("视频上传格式不对,请选择视频后缀名为.ogg/.mpeg4/.webm/.mp4的视频文件");
        }
        DateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
        String srcFileName = date.format(new Date()) + "." + fileName;
        DateFormat dateImg = new SimpleDateFormat("yyyyMM");
        String imgPath = ImagePathEnum.getValue(source) + "/" + dateImg.format(new Date());
        InputStream input = null;
        try {
            input = file.getInputStream();
            FtpUtil.upLoad(srcFileName, input, ImageEnum.PLATFORM.getValue(), imgPath);
        } catch (IOException e) {
            throw new WRWException("图片上传失败");
        }
        return "/image" + imgPath + "/" + srcFileName;
    }

}
