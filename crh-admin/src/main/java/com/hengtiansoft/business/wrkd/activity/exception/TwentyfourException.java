package com.hengtiansoft.business.wrkd.activity.exception;

public class TwentyfourException extends RuntimeException{

    
    private static final long serialVersionUID = 1L;

    
    public TwentyfourException() {
        super();
    }

    public TwentyfourException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TwentyfourException(String message, Throwable cause) {
        super(message, cause);
    }

    public TwentyfourException(String message) {
        super(message);
    }

    public TwentyfourException(Throwable cause) {
        super(cause);
    }

}
