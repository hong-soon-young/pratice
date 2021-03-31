
package com.example.practice.api.handler;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.practice.api.dto.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class UserSettingAop {
	Logger logger = LoggerFactory.getLogger(LoggingAop.class);

	// @Around("*execution(*com.example.practice.api..*.*(..))")
	//@Around("@annotation(com.example.practice.api.annotation.UserSettingTargetAnno)")
	//@Around("execution(* *(.., @UserSettingTargetAnno (*), ..))")
	public Object log(ProceedingJoinPoint pjp) throws Throwable {
		User user = new User();
		user.setUserId("simplegod815");
		user.setUserNm("hsy");


		Object[] args = Arrays.stream(pjp.getArgs()).map(data -> {
			if (data instanceof User) {
				data = user;
			}
			return data;
		}).toArray();

		return pjp.proceed(args);

	}
}
