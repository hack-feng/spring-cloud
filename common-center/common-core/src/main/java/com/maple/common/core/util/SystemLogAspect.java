package com.maple.common.core.util;

import com.alibaba.fastjson.JSONObject;
import com.maple.common.core.bean.LogSystemLog;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * @Aspect 配置切面类，@Component 注解把切面类放入Ioc容器中
 */
@Aspect
@Component
@Slf4j
public class SystemLogAspect {

    @Value("${spring.application.name}")
    private String appName;

    // 定义切点，拦截com.maple.demo.controller下的所有方法
//    @Pointcut("execution(public * com.maple.*.controller..*.*(..))")
//    public void systemLog (){}

    @Pointcut(value = "@annotation(LogHelper)")
    public void systemLog(){}

    @Around(value = "systemLog()&&@annotation(LogHelper)")
    public Object doAround(ProceedingJoinPoint joinPoint){

        /**
         * 定义执行开始时间
         */
        Long startTime;

        /**
         * 定义执行结束时间
         */
        Long endTime;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        LogSystemLog systemLog = new LogSystemLog();
        startTime = new Date().getTime();
        Object obj;
        try {
            obj = joinPoint.proceed();

            endTime = new Date().getTime();
            systemLog.setRespTime((endTime - startTime) + "");
            systemLog.setSuccess("SUCCESS");
            systemLog.setResults(obj.toString());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            obj = throwable;

            endTime = new Date().getTime();
            systemLog.setRespTime((endTime - startTime) + "");
            systemLog.setSuccess("FALSE");
            systemLog.setErrorMsg(throwable.toString());
        }

        systemLog.setAppName(appName);
        systemLog.setAllMethodName(String.valueOf(joinPoint.getSignature()));
        systemLog.setMethodName(joinPoint.getSignature().getName());
        systemLog.setCreateDate(new Date());
        systemLog.setRequestIp(request.getRemoteAddr());
        Map<String, String[]> paramMap = request.getParameterMap();

        //保存参数
        String param = "";
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            String [] flag = entry.getValue();
            String valueFlag = "";
            for (String value: flag) {
                valueFlag = valueFlag + value;
            }
            if(!StringUtils.isEmpty(param)){
                param = param + "&";
            }
            param = String.format("%s%s=%s", param, entry.getKey(), valueFlag);
        }

        MethodSignature signature=(MethodSignature) joinPoint.getSignature();
        Method method=signature.getMethod();
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        systemLog.setLogDesc(apiOperation.value() + "");
        systemLog.setParams(param);

        LogHelper logHelper = method.getAnnotation(LogHelper.class);
        systemLog.setLogType(logHelper.logType() + "");

        log.info(systemLog.toString());
        return obj;
    }
}
