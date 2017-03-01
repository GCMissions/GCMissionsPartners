/*
 * Project Name: zc-collect-web-user
 * File Name: ValidateInterceptor.java
 * Class Name: ValidateInterceptor
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * 
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
 * @author SC
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
    // 循环验证,并抓取错误(只验证本类下的validation注解的属性和包含的类型为Dto的属性,继承属性不验证, 只有onValid配置isDeepCheck为true时才循环验证)
    // isParentCheck为true时验证从父类继承的属性
    private void executeValidate(final Object validatedObj, final Class<?>[] groups,
            final Set<ConstraintViolation<?>> result, boolean isDeepCheck, boolean isParentCheck) throws IllegalArgumentException,
            IllegalAccessException {
        result.addAll(validator.validate(validatedObj, groups));
        Class<?> clazz = validatedObj.getClass();
        if (isDeepCheck) {
            List<Field> fields = Arrays.asList(clazz.getDeclaredFields());//获取本类声明的属性
            fieldsValidate(fields, validatedObj, groups, result, isDeepCheck, isParentCheck);
        }
        if (isParentCheck) {    //获取包括父类的继承属性(只是父类,不包含超类)
            Class<?> parentClazz = clazz.getSuperclass();
            List<Field> superFields = Arrays.asList(parentClazz.getDeclaredFields());//获取本类声明的属性
            fieldsValidate(superFields, validatedObj, groups, result, isDeepCheck, isParentCheck);
        }
    }
    
    private void fieldsValidate(List<Field> fields, final Object validatedObj, final Class<?>[] groups,
            final Set<ConstraintViolation<?>> result, boolean isDeepCheck, boolean isParentCheck) throws IllegalArgumentException, IllegalAccessException{
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(validatedObj);
            // 单实体dto
            if (value != null && value.getClass().toString().endsWith("Dto")) {
                executeValidate(value, groups, result, isDeepCheck, isParentCheck);
            }
            // 数组array
            if (value != null && value.getClass().isArray()) {
                int arrayLength = Array.getLength(value);
                for (int i = 0; i < arrayLength; i++) {
                    Object tempValue = Array.get(value, i);
                    if (tempValue != null && tempValue.getClass().toString().endsWith("Dto")) {
                        executeValidate(tempValue, groups, result, isDeepCheck, isParentCheck);
                    }
                }
            }
            // 集合collection
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
