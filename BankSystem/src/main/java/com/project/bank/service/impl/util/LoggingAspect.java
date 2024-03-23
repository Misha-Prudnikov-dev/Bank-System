package com.project.bank.service.impl.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 The LoggingAspect class is an aspect that logs method calls and their results.
 */
@Aspect
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    /**
     * The method logs all methods at the service level.
     */
    @AfterReturning(pointcut = "execution(* com.project.bank.service.impl.*.*(..))", returning = "result")
    public void logMethodCall(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

            if (methodSignature.getReturnType() != void.class) {
                logger.info("Method: "+ methodName + " with arguments: " + Arrays.toString(args) + " returned result: " + result);
            } else {
                logger.info("Method: "+ methodName + " with arguments: " + Arrays.toString(args) + " without returned result");
            }
    }
}
