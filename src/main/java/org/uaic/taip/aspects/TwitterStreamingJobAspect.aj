package org.uaic.taip.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public aspect TwitterStreamingJobAspect {

    @Before("execution(* org.uaic.taip.spark.SparkTwitterStreamingJobImpl.run(..))")
    public void logBeforeJobExecution(JoinPoint joinPoint) {
        System.out.println("Twitter Streaming job started running");
        if(joinPoint.getArgs().length > 4)
            System.out.println("Running with strategy: " + joinPoint.getArgs()[4]);
        System.out.println("----");
    }
}
