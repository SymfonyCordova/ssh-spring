package com.zler.tools;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 日志类
 */
@Component
@Aspect
public class Logger {

    @Pointcut("execution(* com.zler.service.impl.*.*(..))")
    private void pt1(){}

    //@Before("pt1()")
    public void beforeLog(){
        System.out.println("beforeLog开始记录日志了");
    }

    //@AfterReturning("pt1()")
    public void afterLog(){ System.out.println("afterLog开始记录日志了"); }

    //@AfterThrowing("pt1()")
    public void throwLog(){ System.out.println("throwLog开始记录日志了"); }

    //@After("pt1()")
    public void finallyLog(){ System.out.println("finallyLog开始记录日志了"); }

    @Around("pt1()")
    public Object aroundLog(ProceedingJoinPoint pjp) {
        Object rtValue = null;
        try {
            System.out.println("aroundLog开始记录日志了 前置");
            rtValue = pjp.proceed();
            System.out.println("aroundLog开始记录日志了 后置");
        } catch (Throwable throwable) {
            System.out.println("aroundLog开始记录日志了 异常");
            throwable.printStackTrace();
        }finally {
            System.out.println("aroundLog开始记录日志了 最终");
        }

        return rtValue;
    }

}
