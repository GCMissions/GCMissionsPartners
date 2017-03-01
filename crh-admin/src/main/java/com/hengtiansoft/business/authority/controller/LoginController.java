package com.hengtiansoft.business.authority.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.authority.dto.LoginDto;
import com.hengtiansoft.business.pay.service.WechatRefundService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.security.KaptchaSupport;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * Class Name: LoginController Description: 登录控制器
 * 
 * @author zhisongliu
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    private KaptchaSupport kaptchaSupport;

    @Autowired
    private SUserDao       userDao;
    
    @Autowired
    private WechatRefundService wechatRefundService;

    /**
     * Descption:跳转至登录页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/login")
    public String toLogin(Model model) {
        return "login/login";
    }

    /**
     * Descption:登录验证
     * 
     * @param loginDto
     * @param response
     * @return
     */
    @RequestMapping(value = "/authc")
    @ResponseBody
    public Object login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

        SUserEntity userInfo = userDao.findByLoginId(loginDto.getLoginId());

        if (userInfo == null || StatusEnum.REMOVED.getCode().equals(userInfo.getStatus())) {
            return ResultDtoFactory.toNack("用户名不存在", null);
        }else if(!StatusEnum.NORMAL.getCode().equals(userInfo.getStatus())){
            return ResultDtoFactory.toNack("用户被锁定", null);
        }
        
        Integer pwdErrorTimes = userInfo.getPwdErrorTimes() == null ? 0 : userInfo.getPwdErrorTimes();
        if (pwdErrorTimes > 0) {
            if (StringUtils.isEmpty(loginDto.getCaptcha())) {
                return ResultDtoFactory.toNack("需要输入验证码", pwdErrorTimes);
            }
            if (!kaptchaSupport.validateCaptcha(loginDto.getCaptcha(), loginDto.getKey())) {
                return ResultDtoFactory.toNack("验证码错误", "0");
            }
        }
        try {
            AuthorityContext.login(loginDto.getLoginId(), loginDto.getPassword());

        } catch (UnknownAccountException e) {
            userInfo.setPwdErrorTimes(++pwdErrorTimes);
            userDao.save(userInfo);
            return ResultDtoFactory.toNack("用户名不存在", 1);
        } catch (CredentialsException e) {
            userInfo.setPwdErrorTimes(++pwdErrorTimes);
            if(pwdErrorTimes >= 5){
                userInfo.setStatus(StatusEnum.UNENABLED.getCode());
            }
            userDao.save(userInfo);
            return ResultDtoFactory.toNack("用户名或密码错误", 1);
        }
        AuthorityContext.setTokenCookie(response, null);
        if (pwdErrorTimes > 0) {
            SUserEntity userInfoNew = userDao.findByLoginIdAndStatus(loginDto.getLoginId(), StatusEnum.NORMAL.getCode());
            userInfoNew.setPwdErrorTimes(0);
            userDao.save(userInfoNew);
        }
        return ResultDtoFactory.toAck("登陆成功", loginDto);
    }

    /**
     * Description: render captcha
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/captcha")
    public void captcha(@RequestParam String key, HttpServletResponse response) throws ServletException, IOException {
        kaptchaSupport.captcha(key, response);
    }

    /**
     * Description: 登出
     * 
     * @param response
     * @param cookieDomain
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> logout(HttpServletResponse response) {
        AuthorityContext.logout(response, null);
        return ResultDtoFactory.toAck("登出成功");
    }

}
