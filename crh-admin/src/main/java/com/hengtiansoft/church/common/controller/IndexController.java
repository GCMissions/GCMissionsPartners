package com.hengtiansoft.church.common.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.church.authority.dto.SUserSaveAndUpdateDto;
import com.hengtiansoft.church.authority.service.SUserService;
import com.hengtiansoft.common.authority.AuthorityContext;

@Controller
@RequestMapping("main")
public class IndexController implements ServletContextAware {

    @Autowired
    private SUserService        sUserService;

    /** servletContext */
    private ServletContext      servletContext;

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
}
