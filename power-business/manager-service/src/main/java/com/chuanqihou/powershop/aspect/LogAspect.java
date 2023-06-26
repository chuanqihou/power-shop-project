package com.chuanqihou.powershop.aspect;

import com.chuanqihou.powershop.annotation.LogMethod;
import com.chuanqihou.powershop.domain.SysLog;
import com.chuanqihou.powershop.mapper.SysLogMapper;
import com.chuanqihou.powershop.util.AuthUtil;
import com.chuanqihou.powershop.util.WebScopeUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * @author 传奇后
 * @date 2023/6/26 19:06
 * @description 日志切面 （方法级别） 用于记录方法的执行时间、方法的参数、方法的返回值、方法的执行结果
 */
@Component
@Aspect
public class LogAspect {

    /**
     * 日志mapper
     */
    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * 环绕通知 用于记录方法的执行时间、方法的参数、方法的返回值、方法的执行结果
     * @param joinPoint 连接点
     * @return 方法的返回值
     * @throws Throwable 异常
     */
    @Around("@annotation(com.chuanqihou.powershop.annotation.LogMethod)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Long beginTime = System.currentTimeMillis();
        // 记录方法的参数
        Object[] args = joinPoint.getArgs();
        // 执行目标方法
        Object returnValue = joinPoint.proceed(args);
        Long endTime = System.currentTimeMillis();
        // 计算方法的执行时间
        Long executionTime = endTime - beginTime;

        // 获取请求的ip地址
        HttpServletRequest request = WebScopeUtil.getRequest();
        String ip = null;
        if (request != null) {
            ip = request.getRemoteAddr();
        }
        // 获取方法上的日志注解 @LogMethod 的值
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LogMethod logMethodAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogMethod.class);
        String operation = logMethodAnnotation.value();

        // 获取方法的全限定名
        String methodName = joinPoint.getTarget().getClass().getTypeName() + "." + joinPoint.getSignature().getName();

        // 封装日志对象
        SysLog sysLog = new SysLog()
                .setTime(executionTime)
                .setIp(ip)
                .setParams(Arrays.toString(args))
                .setMethod(methodName)
                .setOperation(operation)
                .setCreateDate(new Date())
                .setUserId(AuthUtil.getLoginUserId());
        // 保存日志
        sysLogMapper.insert(sysLog);
        // 返回方法的返回值
        return returnValue;
    }

}
