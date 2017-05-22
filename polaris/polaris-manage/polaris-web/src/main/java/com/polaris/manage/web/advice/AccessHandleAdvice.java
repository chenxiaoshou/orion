package com.polaris.manage.web.advice;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.PolarisException;
import com.polaris.common.utils.DateUtil;
import com.polaris.common.utils.ReflectionUtils;
import com.polaris.common.utils.ToStringUtil;
import com.polaris.manage.web.advice.advicebean.AccessAdviceInfo;
import com.polaris.manage.web.advice.advicebean.AdviceException;

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

	private static final String EXCEPTION_ADVICE_EXECUTION = "execution(public * com.polaris.manage.web.advice.ExceptionHandleAdvice.*(..))";

	@Autowired
	private MessageSource messageSource;

	/**
	 * 因为controller和exceptionadvice类之间关系过于密切， 所以放在一个切面中同时处理controller和异常处理类中的信息
	 */
	@Pointcut(CONTROLLER_EXECUTION + " || " + EXCEPTION_ADVICE_EXECUTION)
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
		MethodSignature methodSignature = getMethodSignature(joinPoint);
		Method method = methodSignature.getMethod();
		boolean isAccessible = method.isAccessible();
		method.setAccessible(true);
		String[] paramNames = methodSignature.getParameterNames();
		String returnType = methodSignature.getReturnType().getSimpleName();
		AccessAdviceInfo accessAdviceInfo = new AccessAdviceInfo();
		// visitor TODO
		// visitorIp TODO
		// requestURL
		accessAdviceInfo.setRequestURL(request.getRequestURL().toString());
		// className
		accessAdviceInfo.setClassName(joinPoint.getTarget().getClass().getName());
		// methodName
		accessAdviceInfo.setMethodName(joinPoint.getSignature().getName());
		// inputParamMap
		Object[] args = joinPoint.getArgs();
		if (!ArrayUtils.isEmpty(args)) {
			putInputParams(paramNames, accessAdviceInfo, args);
		}
		// hasResponse
		accessAdviceInfo.setReturned(!"void".equalsIgnoreCase(returnType));
		long start = System.currentTimeMillis();
		long tookMillSeconds = 0;
		Object object = null;
		boolean successed = true;
		Date exceptionTime = null;
		String errMsg = "";
		AdviceException adviceException = null;
		try {
			object = joinPoint.proceed();
			tookMillSeconds = System.currentTimeMillis() - start;
		} catch (Throwable e) {
			successed = false;
			exceptionTime = DateUtil.now();
			tookMillSeconds = System.currentTimeMillis() - start;
			errMsg = getErrMsg(e);
			adviceException = new AdviceException(errMsg, e);
			LOGGER.error(e.getMessage(), e);
			if (PolarisException.class.isAssignableFrom(e.getClass())) { // 如果是polarisException的子类，直接抛出子类
				throw e;
			} else { // 否则，包装成polarisException再抛出
				throw new PolarisException(e.getMessage(), e);
			}
		} finally {
			// response
			if (accessAdviceInfo.isReturned() && object != null) {
				accessAdviceInfo.setResponse(ToStringUtil.toJSON(object));
			}
			// tookMillSeconds
			accessAdviceInfo.setTookMillSeconds(tookMillSeconds);
			// successed
			accessAdviceInfo.setSuccessed(successed);
			if (!accessAdviceInfo.isSuccessed() && exceptionTime != null) {
				accessAdviceInfo.setExceptionTime(DateUtil.date2str(exceptionTime));
			}
			// errMsg
			accessAdviceInfo.setErrMsg(errMsg);
			// adviceException
			accessAdviceInfo.setAdviceException(adviceException);
			method.setAccessible(isAccessible);
			LOGGER.info(accessAdviceInfo);
		}
		return object;
	}

	private void putInputParams(String[] paramNames, AccessAdviceInfo accessAdviceInfo, Object[] args) {
		for (int i = 0; i < args.length; i++) {
			Class<?> clz = args[i].getClass();
			boolean isPolarisClass = clz.getName().startsWith("com.polaris");
			boolean isSimpleClass = ReflectionUtils.isBaseClassOrString(clz) || ReflectionUtils.isCollection(clz)
					|| ReflectionUtils.isMap(clz);
			// 异常处理切面中方法的参数太过于复杂，这里没有必要作为日志打印详细信息，故排除掉
			boolean isNotExceptionClass = !clz.getName().endsWith("Exception");
			if ((isPolarisClass || isSimpleClass) && isNotExceptionClass) {
				accessAdviceInfo.putInputParam(paramNames[i], ToStringUtil.toJSON(args[i]));
			} else {
				accessAdviceInfo.putInputParam(paramNames[i], args[i].toString());
			}
		}
	}

	private String getErrMsg(Throwable e) {
		String errMsg;
		if (e instanceof ApiException) {
			ApiException apiException = (ApiException) e;
			errMsg = messageSource.getMessage(apiException.getErrorKey(), apiException.getArgs(), e.getMessage(), null);
		} else if (e instanceof NullPointerException) {
			errMsg = "空指针异常";
		} else {
			errMsg = e.getMessage();
		}
		return errMsg;
	}

	private HttpServletRequest getHttpServletRequest() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return sra.getRequest();
	}

	private MethodSignature getMethodSignature(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		return (MethodSignature) signature;
	}

}
