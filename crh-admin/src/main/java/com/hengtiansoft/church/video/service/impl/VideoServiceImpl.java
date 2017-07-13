package com.hengtiansoft.church.video.service.impl;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hengtiansoft.church.common.dto.FtpImageUploadDto;
import com.hengtiansoft.church.dao.ResourceDao;
import com.hengtiansoft.church.resource.service.ResourceAdminService;
import com.hengtiansoft.church.util.FtpUtil;
import com.hengtiansoft.church.video.service.VideoService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;

@Service
public class VideoServiceImpl implements VideoService{
    
    @Autowired
    private ResourceDao resourceDao;
    
    @Autowired
    private ResourceAdminService resourceAdminService;
    
    @Value("${ftp.server.base}")
    private String filePath;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoServiceImpl.class); 
    
    @Override
    public ResultDto<FtpImageUploadDto> uploadVideo(MultipartFile file) {
        String fileName = "";
        try {
            String suffix = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            fileName = UUID.randomUUID().toString() + "." + suffix;
            if (FtpUtil.open()) {
                FtpUtil.upload(file.getInputStream(), fileName, filePath);
            } else {
                return ResultDtoFactory.toNack("get connection fail!");
            }
        } catch (Exception e) {
            LOGGER.error("upload failed!{}", e);
        }
        String key = filePath + "/" + fileName;
        FtpImageUploadDto dto = new FtpImageUploadDto();
        if (StringUtils.isNotBlank(key)) {
            dto.setKey(key);
            dto.setUrl(FtpUtil.getUrl(key));
            return ResultDtoFactory.toAck("", dto);
        }
        return ResultDtoFactory.toNack("upload failed!");
    }
}
