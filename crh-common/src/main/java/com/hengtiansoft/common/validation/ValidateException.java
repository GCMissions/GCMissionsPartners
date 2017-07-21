package com.hengtiansoft.common.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;

/**
 * Class Name: ValidateException
 * <p>
 * Description: the validate exception, <br>
 * the exception will wrap the <code>Erros</code> list which thrown from service or any facade
 * 
 * @author taochen
 * 
 */

public class ValidateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final List<BindingResult> bindingResults;

    public List<BindingResult> getBindingResults() {
        return bindingResults;
    }

    public ValidateException(final List<BindingResult> bindingResults) {
        this.bindingResults = bindingResults;
    }

    public ValidateException(final BindingResult bindingResult) {
        bindingResults = new ArrayList<BindingResult>();
        bindingResults.add(bindingResult);
    }

    @Override
    public String getMessage() {
        final StringBuilder sb = new StringBuilder("bindingResults string:");
        for (BindingResult bindingResult : bindingResults) {
            sb.append(bindingResult.toString()).append(",");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return this.bindingResults.hashCode();
    }

    /**
     * 
     * Description:
     * 
     * @param bindingResult
     * @return
     */
    public boolean hasErrors(final BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }

    /**
     * 
     * Description:
     * 
     * @return
     */
    public boolean hasErrors() {
        if (bindingResults != null && bindingResults.size() > 0) {
            for (BindingResult bindingResult : bindingResults) {
                if (bindingResult.hasErrors()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * Description:
     * 
     * @param bindingResult
     * @return
     */
    public BindingResult getError(final BindingResult bindingResult) {
        return bindingResult;
    }

    /**
     * 
     * Description:
     * 
     * @return
     */
    public List<BindingResult> getErrors() {
        List<BindingResult> errors = new ArrayList<BindingResult>();
        if (bindingResults != null && bindingResults.size() > 0) {
            for (BindingResult bindingResult : bindingResults) {
                if (bindingResult.hasErrors()) {
                    errors.add(bindingResult);
                }
            }
        }
        return errors;
    }

    /**
     * 
     * Description:
     * 
     * @param bindingResult
     * @return
     */
    public int getErrorCount(final BindingResult bindingResult) {
        return bindingResult.getErrorCount();
    }

    /**
     * 
     * Description:
     * 
     * @return
     */
    public int getErrorCount() {
        int errorCount = 0;
        if (bindingResults != null && bindingResults.size() > 0) {
            for (BindingResult bindingResult : bindingResults) {
                if (bindingResult.hasErrors()) {
                    errorCount += bindingResult.getErrorCount();
                }
            }
        }
        return errorCount;
    }
}
