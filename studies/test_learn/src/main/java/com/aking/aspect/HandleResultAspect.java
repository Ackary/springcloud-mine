package com.aking.aspect;

import com.aking.annotation.HandleResult;
import com.aking.handle.ExceptionHandle;
import com.aking.handle.Result;
import com.aking.handle.ResultUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * HandleResultAspect
 *
 * @author ak
 * @version 1.0
 * @date 2021/6/8
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class HandleResultAspect {

    @Resource
    private ExceptionHandle exceptionHandle;

    /**
     * 定义切点
     *
     * @author ak
     * @date 2021/6/8 18:11
     */
    @Pointcut("@annotation(com.aking.annotation.HandleResult)")
    public void HandleResult() {
    }

    /**
     * 打印接口名、类名、方法名及参数名
     *
     * @author ak
     * @date 2021/6/8 18:12
     */
    @Before(value = "@annotation(t)", argNames = "joinPoint,t")
    public void doBefore(JoinPoint joinPoint, HandleResult t) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request;
        if (requestAttributes != null) {
            request = requestAttributes.getRequest();
            log.info(request.getAuthType());
            log.info(request.getContextPath());
            log.info(request.getMethod());
        }

        // 类名
        String className = joinPoint.getTarget().getClass().getName();
        // 方法名
        String methodName = joinPoint.getSignature().getName();
        // 参数名
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                sb.append(arg).append(", ");
            }
        }
        log.info("接口 {} 开始被调用, 类名: {}, 方法名: {}, 参数名为: {} .",
                t.desc(), className, methodName, sb.toString());
    }

    /**
     * 定义@Around环绕，用于何时执行切点
     *
     * @author ak
     * @date 2021/6/8 18:13
     */
    @Around("HandleResult()")
    public Result doAround(ProceedingJoinPoint point) {
        long startTime = System.currentTimeMillis();
        log.info("---HandleResultAspect--Around的前半部分---");
        Object result;
        try {
            // 执行切点。point.proceed 为方法返回值
            result = point.proceed();
            // 打印出参
            log.info("接口原输出内容: {}", new Gson().toJson(result));
            // 执行耗时
            log.info("执行耗时：{} ms", System.currentTimeMillis() - startTime);
            return ResultUtil.success(result);
        } catch (Throwable throwable) {
            return exceptionHandle.exceptionGet(throwable);
        }
    }

    /**
     * 程序无论正常还是异常，均执行的方法
     *
     * @author ak
     * @date 2021/6/8 18:14
     */
    //@After("HandleResult()")
    @After(value = "@annotation(t)")
    public void doAfter(HandleResult t) {
        log.info("doAfter...");
    }

    /**
     * 当程序运行正常，所执行的方法
     *
     * @author ak
     * @date 2021/6/8 18:14
     */
    @AfterReturning(pointcut = "@annotation(t)", returning = "res")
    public void afterReturn(HandleResult t, Object res) {
        log.info("接口 {} 被调用已结束, 接口最终返回结果为: {} .",
                t.desc(), new Gson().toJson(res));
    }

    /**
     * 当程序运行异常，所执行的方法
     *
     * @author ak
     * @date 2021/6/8 18:15
     */
    //@AfterThrowing(throwing = "throwable", pointcut = "HandleResult()")
    public void afterThrowing(Throwable throwable) {
        log.info("After throwing...", throwable);
    }

}
