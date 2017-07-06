package com.hengtiansoft.common.exception;

import com.hengtiansoft.common.enumeration.EErrorCode;

/**
 * Base exception class for services
 * 
 * @author taochen
 *
 */
public class BizServiceException extends BaseException {
    private static final long serialVersionUID = 1L;

    /**
    * BizServiceException Constructor
    *
    */
    public BizServiceException() {
        super();
    }

    public BizServiceException(String message) {
        super(EErrorCode.COMM_INTERNAL_ERROR, message);
    }

    /**
    * BizServiceException Constructor
    *
    * @param error
    */
    public BizServiceException(DisplayableError error) {
        super(error);
    }

    /**
    * BizServiceException Constructor
    *
    * @param error
    * @param message
    */
    public BizServiceException(DisplayableError error, String message) {
        super(error, message);
    }

    /**
    * BizServiceException Constructor
    *
    * @param error
    * @param message
    * @param cause
    */
    public BizServiceException(DisplayableError error, String message, Throwable cause) {
        super(error, message, cause);
    }
    
}
