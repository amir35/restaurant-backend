package com.amir35.logutil;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimingLogAspect {

    /**
     * This method uses Around advice which ensures that an advice can run before
     * and after the method execution, to and log the execution time of the method
     * This advice will be applied to all the method which are annotate with the
     * annotation @LogExecutionTime @see com.amir35.logutil.TimingLogs
     *
     * Any method where execution times need to be measure and log, annotate the method with @TimingLogs
     * example
     * @LogExecutionTime
     * public void m1();
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */

    @Around("@annotation(com.amir35.logutil.TimingLog)")
    public Object methodTimeLogger(ProceedingJoinPoint joinPoint) throws Throwable
    {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // Get intercepted method details
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object arguments = joinPoint.getArgs();

        //Method start time
        final long initTime = System.currentTimeMillis();
        log.info("Start {}-{} with request: {}", className, methodName, arguments);

        //Method execution
        Object result = joinPoint.proceed();

        //Measure method execution time
        log.info("Stop {}-{} in {}ms with response: {}", className, methodName, System.currentTimeMillis() - initTime, result);

        return result;
    }
}
