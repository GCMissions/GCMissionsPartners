package com.hengtiansoft.common.exception;

/*
 * Project Name: zc-collect-web-user
 * File Name: ExceptionHandler.java
 * Class Name: ExceptionHandler
 * Copyright 2014 Hengtian Software Inc
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hengtiansoft.common.constant.ApplicationConstant;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.web.WebUtil;

/**
 * Class Name: ExceptionResolver
 * <p>
 * Description: the <code>ValidateException</code> handler<br>
 * the validation from service will be wrapped into <code>ValidateException</code>, then the handler will catch the
 * exception and return the errors into view
 * 
 * @author SC
 */
public class ExceptionResolver implements HandlerExceptionResolver {

    private static final Logger   LOGGER = LoggerFactory.getLogger(ExceptionResolver.class);

    @Autowired
    BindingResultExceptionHandler bindingResultExceptionHandler;

    @Autowired
    BeanValidatorExceptionHandler beanValidatorExceptionHandler;

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public ModelAndView resolveException(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
            final Exception ex) {
        ModelAndView mav = new ModelAndView();
        if (WebUtil.isAjaxRequest(request)) {
            if (ex instanceof AuthorizationException) {
                LOGGER.debug("AuthorizationException handled (non-ajax style):", ex);
                setResult(response, HttpServletResponse.SC_UNAUTHORIZED, ResultDtoFactory.toUnauthorized("Please login again!" + ex.getMessage()));
            } else if (ex instanceof MaxUploadSizeExceededException) {
                LOGGER.debug("MaxUploadSizeExceededException handled (non-ajax style):", ex);
                setResult(response, HttpServletResponse.SC_OK, ResultDtoFactory.toNack("文件大小必须小于2M，请重新上传"));
            } else if (ex instanceof BizServiceException) {
                LOGGER.debug("BizServiceException handled (non-ajax style):", ex);
                setResult(response, HttpServletResponse.SC_OK, ResultDtoFactory.toBusinessError(ex.getMessage()));
            } else if (ex instanceof WRWException) {
                LOGGER.debug("WLYException handled (non-ajax style):", ex);
                setResult(response, HttpServletResponse.SC_OK, ResultDtoFactory.toNack(ex.getMessage()));
            } else {
                LOGGER.error("Exception handled (non-ajax style):", ex);
                setResult(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ResultDtoFactory.toNack("服务器异常"));
            }
        } else {
            if (ex instanceof AuthorizationException) {
                mav.setViewName("error/401");
            } else {
                LOGGER.error("Exception handled :", ex);
                mav.setViewName("error/404");
            }
        }
        return mav;
    }

    private void setResult(HttpServletResponse response, int status, ResultDto<?> error) {
        try (OutputStream os = response.getOutputStream();) {
            response.setStatus(status);
            response.setContentType("application/json;charset=UTF-8");
            ObjectMapper mapper = WebUtil.getObjectMapper();
            String json = mapper.writeValueAsString(error);
            os.write(json.getBytes(ApplicationConstant.ENCODING));
            os.flush();
        } catch (final IOException e) {
            LOGGER.warn("response writer open fail", e);
        }
    }

}
