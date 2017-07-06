package com.hengtiansoft.common.controller;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.hengtiansoft.common.constant.ApplicationConstant;

/**
 * Class Name: BaseController
 * Description: all controller should extend this base class
 * 
 * @author taochen
 * 
 */

public abstract class AbstractController {

    /**
     * Description: get all static options from a enum object for display.
     * 
     * @param enumClass
     * @return
     */

    @Autowired
    private LocalValidatorFactoryBean validator;

    protected void validate(final Object validatedObj, final Class<?>[] groups) {
        final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(validatedObj, groups);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(ApplicationConstant.MANUAL_VALIDATE, constraintViolations);
        }
    }

}
