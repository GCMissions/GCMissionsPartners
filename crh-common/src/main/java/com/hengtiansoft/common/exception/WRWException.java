package com.hengtiansoft.common.exception;

import com.hengtiansoft.common.enumeration.EErrorCode;

public class WRWException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected final EErrorCode code;

    protected final String[] params;

    public WRWException() {
        super();
        code = EErrorCode.COMM_INTERNAL_ERROR;
        params = null;
    }

    public WRWException(EErrorCode code) {
        super(code.getDisplayMsg());
        this.code = code;
        params = null;
    }

    public WRWException(String message) {
        super(message);
        code = EErrorCode.COMM_INTERNAL_ERROR;
        params = null;
    }

    public WRWException(String message, EErrorCode code) {
        super(message);
        this.code = code;
        params = null;
    }

    public WRWException(String message, EErrorCode code, String... params) {
        super(message);
        this.code = code;
        this.params = params;
    }

    public WRWException(String message, Throwable cause, EErrorCode code) {
        super(message, cause);
        this.code = code;
        params = null;
    }

    public WRWException(Throwable cause, EErrorCode code) {
        super(cause);
        this.code = code;
        params = null;
    }

    public EErrorCode getCode() {
        return code;
    }

    /**
     * @return the params
     */
    public String[] getParams() {
        return params;
    }

    public WRWException(Throwable cause) {
        super(cause);
        code = EErrorCode.COMM_INTERNAL_ERROR;
        params = null;
    }

}
