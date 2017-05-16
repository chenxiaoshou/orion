package com.polaris.manage.web.advice;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.polaris.manage.web.advice.advicebean.AccessAdviceInfo;

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
	private static final ThreadLocal<AccessAdviceInfo> THREAD_LOCAL = new ThreadLocal<>();

	@Pointcut(CONTROLLER_EXECUTION)
	private void pointcutInControllerLayer() {
		// do nothing
	}

	/**
	 * 记录方法执行前的请求路径,请求人信息,并使用日志打印出来
	 * @throws Throwable 
	 */
	@Around(value = "pointcutInControllerLayer()")
	public void doAroundInControllerLayer(ProceedingJoinPoint joinPoint) throws Throwable {
		LOGGER.info("doAround ... ");
		joinPoint.proceed(); // Around切面中这一步必不可少，不然程序不会执行
	}

	@Before(value = "pointcutInControllerLayer()")
	public void doBeforeInControllerLayer(JoinPoint joinPoint) {
		LOGGER.info("doBefore ... ");
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		LOGGER.info("request url [" + request.getRequestURI() + "]");
		// TODO
	}

	@After(value = "pointcutInControllerLayer()")
	public void doAfterInControllerLayer(JoinPoint joinPoint) {
		LOGGER.info("doAfter ... ");
		// TODO
	}

	private Method getControllerMethod(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		return signature.getMethod();
	}

}
