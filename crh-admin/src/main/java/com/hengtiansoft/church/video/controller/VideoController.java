package com.hengtiansoft.church.video.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hengtiansoft.church.common.dto.FtpImageUploadDto;
import com.hengtiansoft.church.video.service.VideoService;
import com.hengtiansoft.common.dto.ResultDto;

@Controller
@RequestMapping(value = "video")
public class VideoController {
    
    @Autowired
    private VideoService videoService;

    
    /**
     * 
    * Description: 上传本地服务器视频
    *
    * @param 
    * @return
     */
    @RequestMapping(value = "/uploadVideo/{source}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<FtpImageUploadDto> uploadVideo(@RequestParam(value="videoFile") MultipartFile multipartFiles,@PathVariable("source") String source) {
        return videoService.uploadVideo(multipartFiles);
    }
}
