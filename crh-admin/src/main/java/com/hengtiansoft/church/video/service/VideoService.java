package com.hengtiansoft.church.video.service;

import org.springframework.web.multipart.MultipartFile;

import com.hengtiansoft.church.common.dto.FtpImageUploadDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface VideoService {
    
    ResultDto<FtpImageUploadDto> uploadVideo(MultipartFile multipartFiles);
}
