package org.uaic.taip.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public aspect StreamingJobStrategyAspect {
    @AfterReturning(
           value = "execution(* org.uaic.taip.spark.strategy.StreamingJobStrategy.getDimeansion())",
            returning = "dimension")
    public void runAfterDimensionProvisioning(JoinPoint joinPoint, Object dimension) {
        System.out.println(
                String.format("Provided Strategy gliding window dimension, with a value of %s", dimension)
        );
        System.out.println("----");
    }

    @Before("execution(* org.uaic.taip.spark.strategy.StreamingJobStrategy.apply(..))")
    public void logStrategyBeforeApplying(JoinPoint joinPoint) {
        System.out.println("Applying strategy: " + joinPoint.getThis());
        System.out.println("----");
    }


    @After("execution(* org.uaic.taip.spark.strategy.StreamingJobStrategy.getDimeansion())")
    public void logStrategyBindingCompletion(JoinPoint joinPoint) {
        System.out.println("Finished applying strategy");
        System.out.println("----");
    }
}
