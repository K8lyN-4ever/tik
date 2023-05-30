package edu.td.zy.tik_user.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author K8lyN
 * @version v1.0
 * @date 2023/3/8 10:00
 */
@Slf4j
@Aspect
@Component
public class AutoHandlerAspect {

//    @Pointcut("execution(* edu.td.zy.tik_user.mapper.UserMapper.*(..))")
//    public void servicePointcut() {
//
//    }
//
//    @Before("servicePointcut()")
//    public void before(JoinPoint joinPoint) {
//        log.info("Method " );
//        System.out.println("Method " + joinPoint.getSignature().getName() + " is executing...");
//    }
//
//    @AfterReturning(value = "servicePointcut()", returning = "result")
//    public void afterReturning(JoinPoint joinPoint, Object result) {
//        log.error();
//        System.out.println("Method " + joinPoint.getSignature().getName() + " executed successfully. Result is " + result);
//    }

}
