package com.hengtiansoft.church.common.controller;

import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.church.authority.dto.SUserSaveAndUpdateDto;
import com.hengtiansoft.church.authority.service.SUserService;
import com.hengtiansoft.church.common.dto.FtpImageUploadDto;
import com.hengtiansoft.church.dao.SBasicParaDao;
import com.hengtiansoft.church.util.FtpUtil;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.standard.FileOperator;

@Controller
@RequestMapping("main")
public class IndexController implements ServletContextAware {
    private static Logger LOGGER  = LoggerFactory.getLogger(IndexController.class);

    @Value("${ftp.server.base}")
    private String              imgPath;

    @Autowired
    private SUserService        sUserService;

    @Autowired
    private FileOperator fileOperator;
    
    /** servletContext */
    private ServletContext      servletContext;
    
    @Autowired
    private SBasicParaDao       basicParaDao;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    // 后台页面 框架
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main(Model model) throws Exception {
        SUserSaveAndUpdateDto dto = sUserService.findById(AuthorityContext.getCurrentUser().getUserId());
        model.addAttribute("userName", dto.getLoginId());
        model.addAttribute("userDto", dto);
        return new ModelAndView("main");
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() throws Exception {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("javaVersion", System.getProperty("java.version"));
        mav.addObject("javaHome", System.getProperty("java.home"));
        mav.addObject("osName", System.getProperty("os.name"));
        mav.addObject("osArch", System.getProperty("os.arch"));
        mav.addObject("serverInfo", servletContext.getServerInfo());
        mav.addObject("servletVersion", servletContext.getMajorVersion() + "." + servletContext.getMinorVersion());
        return mav;
    }

    /**
     * 
    * Description: 单个图片oss上传
    *
    * @param file
    * @param source
    * @return
    * @author chengchaoyin
     */
    @RequestMapping(value = "/ossAddImage/{source}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<FtpImageUploadDto> ossAddImage(@RequestBody MultipartFile file, @PathVariable("source") String source) {
        String fileName = "";
        try {
            String suffix = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            fileName = UUID.randomUUID().toString()+"."+suffix;
            if(FtpUtil.open()){
                FtpUtil.upload(file.getInputStream(), fileName, imgPath);
            }else{
                return ResultDtoFactory.toNack("get connection fail!");   
            }
        } catch (Exception e) {
            LOGGER.error("upload failed!{}", e);
        } 
        String key = imgPath+"/"+fileName;
        FtpImageUploadDto dto = new FtpImageUploadDto();
        if (StringUtils.isNotBlank(key)) {
            dto.setKey(key);
            dto.setUrl(FtpUtil.getUrl(key));
            return ResultDtoFactory.toAck("", dto);
        } 
        return ResultDtoFactory.toNack("upload failed!");
    }
}
