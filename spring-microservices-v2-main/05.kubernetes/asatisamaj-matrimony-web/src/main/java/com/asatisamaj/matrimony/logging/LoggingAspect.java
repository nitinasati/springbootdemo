package com.asatisamaj.matrimony.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.asatisamaj.matrimony.utils.AppUtil;

@Aspect
@Component
public class LoggingAspect {
	private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

	@Around("execution(* com.asatisamaj.matrimony..*(..)))")
	public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		final StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Object result = proceedingJoinPoint.proceed();
		stopWatch.stop();

		if (methodName.equalsIgnoreCase("getMemberDetails") && (null == authentication || authentication.getPrincipal().equals("anonymousUser"))) {
				result = AppUtil.maskResult(result);
		}

		// Log method execution time
		LOGGER.info("Execution time of {} . {} :: {} ms  ", className, methodName, stopWatch.getTotalTimeMillis());

		return result;
	}
}
