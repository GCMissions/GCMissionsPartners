package com.hengtiansoft.business.common.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.authority.dto.SUserSaveAndUpdateDto;
import com.hengtiansoft.business.authority.service.SUserService;
import com.hengtiansoft.business.common.dto.ImageDetailDto;
import com.hengtiansoft.business.common.dto.OssImageUploadDto;
import com.hengtiansoft.business.common.enums.UploadSourceEnum;
import com.hengtiansoft.business.message.service.SMessageService;
import com.hengtiansoft.business.product.service.ProductImageService;
import com.hengtiansoft.business.setting.service.MessageModelService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.ImagePathEnum;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.standard.FileOperator;
import com.hengtiansoft.wrw.dao.SBasicParaDao;

@Controller
@RequestMapping("main")
public class IndexController implements ServletContextAware {
    private static Logger LOGGER  = LoggerFactory.getLogger(IndexController.class);
    
    @Autowired
    private SMessageService     sMessageService;

    @Autowired
    private ProductImageService productImageService;

    @Value("${common.file.server}")
    private String              imgPath;

    @Autowired
    private SUserService        sUserService;

    @Autowired
    private MessageModelService messageModelService;

    @Autowired
    private FileOperator fileOperator;
    
    private final String        IMAGE_SUCCESS = "SUCCESS";

    private final String        IMAGE_FAIL    = "FAIL";
    
    // 图片大小限制
    private static final String IMAGE_MAXSIZE_TYPE = "图片大小限制";

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
        model.addAttribute("userName", dto.getUserName());
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

    // test for my qrcode
    @RequestMapping(value = "/qrcode", method = RequestMethod.GET)
    public ModelAndView qrcode() throws Exception {
        ModelAndView mav = new ModelAndView("index_goods");

        return mav;
    }

    @RequestMapping(value = "/message/getUnredMessageNum", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Integer> getUnredMessageNum() {
        return ResultDtoFactory.toAck("未读消息", sMessageService.getUnredMessageNum());
    }

    /**
     * Description: upload a image
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/addImage/{source}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> addImage(@RequestBody MultipartFile file, @PathVariable("source") String source) {
        String url = productImageService.uploadImage(file, source);
        return ResultDtoFactory.toAck("", url);
    }

    @RequestMapping(value = "/uploadImage/{source}", method = RequestMethod.POST)
    @ResponseBody
    public ImageDetailDto uploadImage(@RequestBody MultipartFile upfile, @PathVariable("source") String source, HttpServletRequest request) {
        String url = "";
        String action = request.getParameter("action");
        if ("uploadvideo".equals(action)) {// 视频
            url = productImageService.addImage(upfile, ImagePathEnum.ADMIN_MEDIA.getKey());
        } else {
            url = productImageService.uploadImage(upfile, source);
        }
        ImageDetailDto dto = new ImageDetailDto();
        if (!WRWUtil.isEmpty(url)) {
            dto.setUrl(imgPath + url);
            dto.setState(IMAGE_SUCCESS);
        } else {
            dto.setUrl("");
            dto.setState(IMAGE_FAIL);
        }
        dto.setTitle("");
        return dto;
    }

    @RequestMapping(value = "/uploadMedia/{source}", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestBody MultipartFile upfile, @PathVariable("source") String source) {

        return productImageService.addImage(upfile, source);
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
    public ResultDto<OssImageUploadDto> ossAddImage(@RequestBody MultipartFile file, @PathVariable("source") String source) {
        //　校验文件大小
        String paraName = UploadSourceEnum.getFlag(source);
        if (StringUtils.isNotBlank(paraName)) {
            String imgSize = basicParaDao.findParaValue1ByTypeName(IMAGE_MAXSIZE_TYPE, paraName);
            if (StringUtils.isNotBlank(imgSize) && (file.getSize() / 1024) > Long.valueOf(imgSize)) {
                return ResultDtoFactory.toNack("图片过大，图片的大小不可以超过" + imgSize + "K ！");
            }
        }
        
        String url = "";
        String key = "";
        try {
            String suffix = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            key = fileOperator.uploadFile(file.getInputStream(), UUID.randomUUID().toString()+"."+suffix, null);
        } catch (Exception e) {
            LOGGER.error("图片上传OSS失败！{}", e);
        } 
        
        OssImageUploadDto dto = new OssImageUploadDto();
        if (StringUtils.isNotBlank(key)) {
            dto.setKey(key);
            url = fileOperator.getUrl(key);
            dto.setUrl("http://" + checkCdn(url));
            return ResultDtoFactory.toAck("", dto);
        } 
        return ResultDtoFactory.toNack("上传失败！");
    }
    
    /**
     * 
    * Description: 生产环境启用CDN,替换头部链接地址
    *
    * @param originUrl
    * @return
    * @author chengchaoyin
     */
    private String checkCdn(String originUrl){
        if(originUrl.indexOf("pccp-prod") != -1){
            originUrl = originUrl.replaceAll("pccp-prod.oss-cn-hangzhou.aliyuncs.com", "image.5260wawa.com");
        }
        return originUrl;
    }
    
    /**
     * 
    * Description: 多个图片oss上传
    *
    * @param request
    * @param source
    * @return
    * @author chengchaoyin
     */
    @RequestMapping(value = "/ossAddImageList/{source}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<List<OssImageUploadDto>> ossAddImageList(MultipartHttpServletRequest request, @PathVariable("source") String source) {
        List<MultipartFile> files = request.getFiles("file");
        List<OssImageUploadDto> dtos = new ArrayList<OssImageUploadDto>();
        if (files.size() <= 5) {
            String paraName = UploadSourceEnum.getFlag(source);
            String imgSize = basicParaDao.findParaValue1ByTypeName(IMAGE_MAXSIZE_TYPE, paraName);
            String overString = "";
            for(MultipartFile file : files) {
                if (StringUtils.isNotBlank(imgSize) && (file.getSize() / 1024) > Long.valueOf(imgSize)) {
                    overString += file.getOriginalFilename() + ",";
                } else {
                    String key = "";
                    try {
                        String suffix = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
                        key = fileOperator.uploadFile(file.getInputStream(), UUID.randomUUID().toString()+"."+suffix, null);
                    } catch (Exception e) {
                        LOGGER.error("图片上传OSS失败！{}", e);
                    } 
                    String url = fileOperator.getUrl(key);
                    OssImageUploadDto dto = new OssImageUploadDto();
                    dto.setKey(key);
                	dto.setUrl("http://"+checkCdn(url));
                    dtos.add(dto);
                }
            }
            if (!"".equals(overString)) {
                return ResultDtoFactory.toNack("文件 " + overString.substring(0, overString.length() - 1) + " 超过" + imgSize + "K限制！请重新上传！");
            }
            return ResultDtoFactory.toAck("", dtos);
        } else {
            return ResultDtoFactory.toNack("本地上传图片单次建议在5张以内");
        }
    }
    
    /**
     * 
    * Description: 单个图片上传-富文本-oss
    *
    * @param upfile
    * @param source
    * @param request
    * @return
    * @author chengchaoyin
     */
    @RequestMapping(value = "/ossUploadImage/{source}", method = RequestMethod.POST)
    @ResponseBody
    public ImageDetailDto ossUploadImage(@RequestBody MultipartFile upfile, @PathVariable("source") String source, HttpServletRequest request) {
        String url = "";
        String key = "";
        try {
            String suffix = StringUtils.substringAfterLast(upfile.getOriginalFilename(), ".");
            key = fileOperator.uploadFile(upfile.getInputStream(), UUID.randomUUID().toString() +"."+ suffix, null);
            url = fileOperator.getUrl(key);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        ImageDetailDto dto = new ImageDetailDto();
        if (!WRWUtil.isEmpty(url)) {
            dto.setUrl("http://" + checkCdn(url));
            dto.setState(IMAGE_SUCCESS);
        } else {
            dto.setUrl("");
            dto.setState(IMAGE_FAIL);
        }
        dto.setTitle("");
        return dto;
    }
    
    @RequestMapping(value = "/imageDelete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> imageDelete(@RequestParam(value="key") String key) {
        try {
            fileOperator.deleteFile(key);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return ResultDtoFactory.toAck("");
    }
}
