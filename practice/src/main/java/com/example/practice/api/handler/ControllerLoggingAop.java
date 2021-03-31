
package com.example.practice.api.handler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.server.ServerRequest;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@Aspect
public class ControllerLoggingAop {
	Logger logger = LoggerFactory.getLogger(ControllerLoggingAop.class);
	
//	@Pointcut("execution(* com.example.practice.api..*.*(..))")
//	public void ControllerLoggingPointcut() {};
	
	//@Around("execution(* com.example.practice.api.handler.*.*(..))")
	//@Around("@annotation(com.example.practice.api.annotation.LogTargetAnno)")
	public Object log(ProceedingJoinPoint pjp) throws Throwable {
		
		
		Object o = pjp.getArgs();
		StopWatch stopWatch = new StopWatch();
		
		logger.info("로오오오깅" + o.toString());

        stopWatch.start();
        
        logger.info("로오오오깅" + stopWatch.prettyPrint());
        // @LogExecutionTime 애노테이션이 붙어있는 타겟 메소드를 실행
        Object proceed = pjp.proceed();
        
        stopWatch.stop();
        
        return proceed;
	}
}
