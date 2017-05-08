package com.polaris.manage.web.advice;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestAttributes;
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

	private static final ThreadLocal<AccessAdviceInfo> THREAD_LOCAL = new ThreadLocal<AccessAdviceInfo>();

	@Pointcut(CONTROLLER_EXECUTION)
	private void pointcutInControllerLayer() {
		// do nothing
	}

	/**
	 * 记录方法执行前的请求路径,请求人信息,并使用日志打印出来
	 */
	@Around(value = "pointcutInControllerLayer()")
	public void doAroundInControllerLayer(JoinPoint joinPoint) {
		// 取得当前的 request 对象
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        // TODO
	}

	@Before(value = "pointcutInControllerLayer()")
	public void doBeforeInControllerLayer(JoinPoint joinPoint) {

	}

	@After(value = "pointcutInControllerLayer()")
	public void doAfterInControllerLayer(JoinPoint joinPoint) {
		// TODO
	}

}
