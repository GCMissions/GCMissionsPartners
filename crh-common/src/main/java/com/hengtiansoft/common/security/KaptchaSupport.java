package com.hengtiansoft.common.security;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.servlet.KaptchaExtend;
import com.google.code.kaptcha.util.Config;
import com.hengtiansoft.common.security.service.KaptchaService;

/**
 * Class Name: KaptchaSupport 
 * Description: 
 * 
 * @author taochen
 *
 */
public class KaptchaSupport extends KaptchaExtend {

    private static final Logger LOG = LoggerFactory.getLogger(KaptchaSupport.class);

    private Producer kaptchaProducer = null;

    @Autowired
    private KaptchaService kaptchaService;

    public KaptchaSupport() {
        // Switch off disk based caching.
        ImageIO.setUseCache(false);
        initialize(new Config(createDefaultConfigProps()));
    }

    public KaptchaSupport(Properties configProps) {
        this();
        Properties props = createDefaultConfigProps();
        props.putAll(configProps);
        Config config = new Config(props);
        initialize(config);
    }

    private void initialize(Config config) {
        this.kaptchaProducer = config.getProducerImpl();
    }

    private Properties createDefaultConfigProps() {
        Properties props = new Properties();
        props.put("kaptcha.border", "no");
        props.put("kaptcha.textproducer.font.color", "black");
        props.put("kaptcha.textproducer.char.space", "5");
        return props;
    }

    /**
     * map it to the /url/captcha.jpg
     * 
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void captcha(String key, HttpServletResponse resp) throws ServletException, IOException {
        // Set standard HTTP/1.1 no-cache headers.
        resp.setHeader("Cache-Control", "no-store, no-cache");

        // return a jpeg
        resp.setContentType("image/jpeg");

        // create the text for the image
        String capText = this.kaptchaProducer.createText();

        // create the image with the text
        BufferedImage bi = this.kaptchaProducer.createImage(capText);

        ServletOutputStream out = resp.getOutputStream();

        // write the data out
        ImageIO.write(bi, "jpg", out);

        LOG.debug("captcha \"{}\" written to response", capText);

        kaptchaService.saveKaptchToMemcache(capText, key);
        LOG.debug("captcha \"{}\" written to memcached, key {}", capText, key);
    }

    public boolean validateCaptcha(String captcha, String key) {
        boolean valid = false;
        if (captcha != null) {
            valid = captcha.equalsIgnoreCase(kaptchaService.saveKaptchToMemcache("", key));
            if (valid) {
                LOG.debug("captcha \"{}\" removed", captcha);
                kaptchaService.cleanKaptchCache(key);
            }
        }
        return valid;
    }
}
