package com.cabr.pkpm.config;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cabr.pkpm.entity.user.CurrentUserInfo;

/**
 * 自定义解析器实现参数绑定
 * 增加方法注入，将含有 @CurrentUser 注解的方法参数注入当前登录用户
 * @author zhangshuai
 * @since 2018/01/28
 */
/*@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(CurrentUserInfo.class)
                && parameter.hasMethodAnnotation(CurrentUserInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        CurrentUserInfo currentUserInfo = (CurrentUserInfo) webRequest.getAttribute(Const.CurrentUserInfo,
                RequestAttributes.SCOPE_REQUEST);
        return currentUserInfo;
    }
    
}*/
