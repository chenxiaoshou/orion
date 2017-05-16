package com.polaris.manage.web.advice;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 记录请求日志信息的切面
 * 
 * @author dong
 *
 */
@Order(1)
@Aspect
public class AccessHandleAdvice {

	private static final Logger LOGGER = LogManager.getLogger(AccessHandleAdvice.class);

	private static final String CONTROLLER_EXECUTION = "execution(public * com.polaris.manage.web.controller..*.*(..))";

	@Pointcut(CONTROLLER_EXECUTION)
	private void pointcutInControllerLayer() {
		// do nothing
	}

	/**
	 * 记录方法执行前的请求路径,请求人信息,并使用日志打印出来
	 * 
	 * @throws Throwable
	 */
	@Around(value = "pointcutInControllerLayer()")
	public Object doAroundInControllerLayer(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = getHttpServletRequest();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("request url [" + request.getRequestURL() + "]");
			LOGGER.info("target class name [" + request.getRequestURL() + "]");
			LOGGER.info("target method name [" + request.getRequestURL() + "]");
			LOGGER.info("input params [" + request.getRequestURL() + "]");
		}
		long start = System.currentTimeMillis();
		Object object = null;
		try {
			object = joinPoint.proceed();
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("[" + joinPoint + "] took millseconds : " + (System.currentTimeMillis() - start)
						+ "ms successfully. execute result [" + object + "]");
			}
		} catch (Throwable e) {
			long end = System.currentTimeMillis();
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("[" + joinPoint + "] took millseconds : " + (end - start) + "ms with exception ["
						+ e.getMessage() + "]", e);
			}
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("response [" + request.getRequestURL() + "]");
		}
		return object;
	}

	private HttpServletRequest getHttpServletRequest() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return sra.getRequest();
	}

}
