package com.hengtiansoft.common.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.hengtiansoft.common.dto.annotation.OnValid;
import com.hengtiansoft.common.util.ApplicationContextUtil;

/**
 * Class Name: ValidateInterceptor
 * <p>
 * Description: the Interceptor for service validation
 * 
 * @author taochen
 * 
 */
@Component
@Aspect
public class ValidateInterceptor {

    @Autowired
    private LocalValidatorFactoryBean validator;

    public ValidateInterceptor() {
        if (validator == null) {
            validator = ApplicationContextUtil.getBean(LocalValidatorFactoryBean.class);
        }
    }

    private static final String EXECUTION = "execution(* com.fxpt..*.*(..,@com.hengtiansoft.common.dto.annotation.OnValid (*),..))";

    /**
     * defination pointcut of service method stick <code>@Validated</code> in args.
     */
    @Pointcut(EXECUTION)
    public void findValidateAnnotation() {
    }

    /**
     * validate the business logic before executing target method.
     * 
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @Before("findValidateAnnotation()")
    public void validate(final JoinPoint jp) throws ConstraintViolationException, IllegalArgumentException,
            IllegalAccessException {
        final Signature signature = jp.getSignature();
        final Object[] args = jp.getArgs();
        final MethodSignature methodSignature = (MethodSignature) signature;
        final Method targetMethod = methodSignature.getMethod();
        Set<ConstraintViolation<?>> result = new HashSet<ConstraintViolation<?>>();
        final Annotation[][] paraAnnotations = targetMethod.getParameterAnnotations();// get the parameters annotations
        if (paraAnnotations != null && paraAnnotations.length > 0) {
            for (int i = 0; i < paraAnnotations.length; i++) {
                int paraAnnotationLength = paraAnnotations[i].length;// current parameter annotation length
                if (paraAnnotationLength == 0) { // no annotation on current parameter
                    continue;
                }
                for (int j = 0; j < paraAnnotationLength; j++) {
                    if (paraAnnotations[i][j] instanceof OnValid) {
                        OnValid validated = (OnValid) (paraAnnotations[i][j]);
                        Class<?>[] groups = (validated.value());
                        boolean isDeepCheck = (validated.isDeepCheck());
                        boolean isParentCheck = (validated.isParentCheck());
                        Object validatedObj = args[i];
                        executeValidate(validatedObj, groups, result, isDeepCheck, isParentCheck);
                        break;
                    }
                }
            }
        }
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
    }

    /**
     * 
     * Description: execute the validate and set into result set.
     * 
     * @param validatedObj
     * @param groups
     * @param result
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    // Loop validation and catch errors
    //(Only validate the attributes of the "@validation" annotation in this class and the attributes that contain the type Dto, the inherited attribute is not validated, 
    //and only onValid is configured to check if the isDeepCheck is true)
    // When the isParentCheck is true, the attributes inherited from the parent class are validated
    private void executeValidate(final Object validatedObj, final Class<?>[] groups,
            final Set<ConstraintViolation<?>> result, boolean isDeepCheck, boolean isParentCheck) throws IllegalArgumentException,
            IllegalAccessException {
        result.addAll(validator.validate(validatedObj, groups));
        Class<?> clazz = validatedObj.getClass();
        if (isDeepCheck) {
            List<Field> fields = Arrays.asList(clazz.getDeclaredFields());//Get the attributes of this type of declaration
            fieldsValidate(fields, validatedObj, groups, result, isDeepCheck, isParentCheck);
        }
        if (isParentCheck) {    //Gets the inheritance attribute that includes the parent class (only the parent class, not the superclass)
            Class<?> parentClazz = clazz.getSuperclass();
            List<Field> superFields = Arrays.asList(parentClazz.getDeclaredFields());//Get attributes of this class declaration
            fieldsValidate(superFields, validatedObj, groups, result, isDeepCheck, isParentCheck);
        }
    }
    
    private void fieldsValidate(List<Field> fields, final Object validatedObj, final Class<?>[] groups,
            final Set<ConstraintViolation<?>> result, boolean isDeepCheck, boolean isParentCheck) throws IllegalArgumentException, IllegalAccessException{
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(validatedObj);
            //dto
            if (value != null && value.getClass().toString().endsWith("Dto")) {
                executeValidate(value, groups, result, isDeepCheck, isParentCheck);
            }
            //array
            if (value != null && value.getClass().isArray()) {
                int arrayLength = Array.getLength(value);
                for (int i = 0; i < arrayLength; i++) {
                    Object tempValue = Array.get(value, i);
                    if (tempValue != null && tempValue.getClass().toString().endsWith("Dto")) {
                        executeValidate(tempValue, groups, result, isDeepCheck, isParentCheck);
                    }
                }
            }
            if (value != null && Collection.class.isAssignableFrom(value.getClass())) {
                Collection<?> collection = (Collection<?>) value;
                for (Object inner : collection) {
                    if (inner.getClass().toString().endsWith("Dto")) {
                        executeValidate(inner, groups, result, isDeepCheck, isParentCheck);
                    }
                }
            }
        }
    }

}
