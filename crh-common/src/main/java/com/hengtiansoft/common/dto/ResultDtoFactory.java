package com.hengtiansoft.common.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.validation.BindingResult;

import com.hengtiansoft.common.constant.ResultCode;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.BaseException;
import com.hengtiansoft.common.exception.DisplayableError;
import com.hengtiansoft.common.util.AppConfigUtil;
import com.hengtiansoft.common.util.MessageUtil;

/**
 * Class Name: ResultDtoFactory Description:
 * 
 * @author taochen
 */
public final class ResultDtoFactory {

    private ResultDtoFactory() {
    };

    public static <T> ResultDto<T> toAck(String msg) {
        return toAck(msg, null);
    }

    public static <T> ResultDto<T> toAck(String msg, T data) {
        ResultDto<T> dto = new ResultDto<T>();
        dto.setCode(ResultCode.ACK);
        dto.setMessage(msg);
        dto.setData(data);
        return dto;
    }

    public static <T> ResultDto<T> toAck(DisplayableError error) {
        ResultDto<T> dto = new ResultDto<T>();
        dto.setCode(ResultCode.ACK);
        dto.setMessage(error.getDisplayMsg());
        return dto;
    }

    public static ResultDto<String> toRedirect(String url) {
        ResultDto<String> dto = new ResultDto<String>();
        dto.setCode(ResultCode.REDIRECT);
        dto.setData(url);
        return dto;
    }

    /**
     * Description: In the controller layer directly return error message, to avoid using the method in the controller
     * catch exception to do processing
     * 
     * @param msg
     * @return
     */
    public static <T> ResultDto<T> toNack(String msg) {
        return toNack(msg, null);
    }

    /**
     * Description: In the controller layer directly return error message, to avoid using the method in the controller
     * catch exception to do processing
     * 
     * @param error
     * @return
     */
    public static <T> ResultDto<T> toNack(DisplayableError error) {
        String msg = "";
        if (error != null && StringUtils.isNotBlank(error.getErrorCode())) {
            msg = MessageUtil.getMessage(error.getDisplayMsg(), error.getArgs());
        }
        return toNack(msg, null);
    }

    /**
     * Description: In the controller layer directly return error message, to avoid using the method in the controller
     * catch exception to do processing
     * 
     * @param msg
     * @param data
     * @return
     */
    public static <T> ResultDto<T> toNack(String msg, T data) {
        ResultDto<T> dto = new ResultDto<T>();
        dto.setCode(ResultCode.NACK);
        dto.setMessage(msg);
        dto.setData(data);
        return dto;
    }

    public static <T> ResultDto<T> toUnauthorized(String msg) {
        ResultDto<T> dto = new ResultDto<T>();
        dto.setCode(ResultCode.UNAUTHORIZED);
        dto.setMessage(msg);
        dto.setData(null);
        return dto;
    }

    public static <T> ResultDto<T> toBusinessError(String msg) {
        ResultDto<T> dto = new ResultDto<T>();
        dto.setCode(ResultCode.BUSINESS_ERROR);
        dto.setMessage(msg);
        dto.setData(null);
        return dto;
    }

    public static ResultDto<BindingResult> toValidationError(String msg, BindingResult br) {
        ResultDto<BindingResult> dto = new ResultDto<BindingResult>();
        dto.setCode(ResultCode.VALIDATION_ERROR);
        dto.setMessage(msg);
        dto.setData(br);
        return dto;
    }

    private static ResultDto<String> toCommonError(String code, String msg, String details) {
        ResultDto<String> dto = new ResultDto<String>();
        dto.setCode(ResultCode.COMMON_ERROR);
        StringBuilder text = new StringBuilder();
        if (StringUtils.isBlank(msg)) {
            text.append(MessageUtil.getMessage(EErrorCode.COMM_INTERNAL_ERROR.getDisplayMsg())).append("[")
                    .append("time：").append((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()))
                    .append("]");
        } else {
            text.append(msg);
        }
        if (StringUtils.isNotBlank(code)) {
            text.append("(").append(code).append(")");
        }
        dto.setMessage(text.toString());
        if (!AppConfigUtil.isProdEnv()) {
            dto.setData(details);
        }
        return dto;
    }

    /**
     * Description: Exceptional stacktrace and message will be displayed in a non-production environment
     * 
     * @param e
     * @return
     */
    public static ResultDto<String> toCommonError(BaseException e) {
        String msg = MessageUtil.getMessage(e.getError().getDisplayMsg());
        return toCommonError(e.getError().getErrorCode(), msg, ExceptionUtils.getStackTrace(e));
    }

    /**
     * Description: Exceptional stacktrace and message will be displayed in a non-production environment
     * 
     * @param e
     * @return
     */
    public static ResultDto<String> toCommonError(Exception e) {
        return toCommonError(null, null, ExceptionUtils.getStackTrace(e));
    }

    /**
     * Description:The incoming msg will be displayed
     * 
     * @param msg
     * @return
     */
    public static ResultDto<String> toCommonError(String msg) {
        return toCommonError(null, msg, null);
    }

    public static ResultDto<String> toBizError(String msg, Exception e) {
        ResultDto<String> dto = new ResultDto<String>();
        dto.setCode(ResultCode.BUSINESS_ERROR);
        dto.setMessage(msg == null ? e.getMessage() : msg);
        if (!AppConfigUtil.isProdEnv()) {
            dto.setData(ExceptionUtils.getStackTrace(e));
        }
        return dto;
    }
}
