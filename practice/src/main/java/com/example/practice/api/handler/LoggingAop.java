
package com.example.practice.api.handler;

import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bazaarvoice.jolt.JsonUtils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingAop {
	private static final String LOG_TYPE = "[PRATICE_LOGGING]";
	Logger logger = LoggerFactory.getLogger(LoggingAop.class);
	
	@Pointcut("@annotation(com.example.practice.api.annotation.LogTargetAnno)")
	public void LoggingAopPointCut() {};
	
	//@Around("*execution(*com.example.practice.api..*.*(..))")
	@Around("LoggingAopPointCut()")
	public Object log(ProceedingJoinPoint pjp) throws Throwable {
		Signature signature = pjp.getSignature();
		
		loggingIn(signature);
		try {		
			loggingArgs(signature, pjp.getArgs());
			
			return pjp.proceed();
		} finally {
			loggingOut(signature);
		}
	}
	
	private void loggingIn(Signature signature) {
		log.debug("{}: IN {}.{}", LOG_TYPE, signature.getDeclaringType().getSimpleName(), signature.getName());
	}

	private void loggingOut(Signature signature) {
		log.debug("{}: OUT {}.{}", LOG_TYPE, signature.getDeclaringType().getSimpleName(), signature.getName());
	}
	
	private void loggingArgs(Signature signature, Object[] args) {
		if (ObjectUtils.isNotEmpty(args) && args.length > 0 ) {
			try {
				log.debug("PRATICE_LOGGING: REQUEST ARGS {}.{} ==> {}", signature.getDeclaringType().getSimpleName(), signature.getName(), JsonUtils.toJsonString(args));
			} catch (Exception e) {
				/* ignore */
			}
		}
	}
}
