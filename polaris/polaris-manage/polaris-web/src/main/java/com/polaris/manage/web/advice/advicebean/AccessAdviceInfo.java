package com.polaris.manage.web.advice.advicebean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import net.sf.json.JSONObject;

/**
 * 用来记录访问日志的切面bean
 * 
 */
public class AccessAdviceInfo implements Serializable {

	private static final long serialVersionUID = -5877789774624894127L;

	private String visitor; // 请求者身份标识

	private String visitorIp; // 访问者Ip

	private String requestURL; // 请求的完整路径

	private String className; // 类名

	private String methodName; // 方法名

	private Map<Object, Object> inputParamMap = new LinkedHashMap<>(); // 入参

	private String hasResponse; // 方法是否有返回值

	private String response; // 返回值的序列化结果

	private long tookMillSeconds; // 方法运行总耗时（毫秒）

	private boolean isSuccess; // 方法是否成功结束运行

	private String exceptionTime; // 异常发生的时间

	private AdviceException adviceException; // 异常信息对象

	public String getVisitor() {
		return visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}

	public String getVisitorIp() {
		return visitorIp;
	}

	public void setVisitorIp(String visitorIp) {
		this.visitorIp = visitorIp;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Map<Object, Object> getInputParamMap() {
		return inputParamMap;
	}

	public void setInputParamMap(Map<Object, Object> inputParamMap) {
		this.inputParamMap = inputParamMap;
	}

	public long getTookMillSeconds() {
		return tookMillSeconds;
	}

	public void setTookMillSeconds(long tookMillSeconds) {
		this.tookMillSeconds = tookMillSeconds;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getExceptionTime() {
		return exceptionTime;
	}

	public void setExceptionTime(String exceptionTime) {
		this.exceptionTime = exceptionTime;
	}

	public AdviceException getAdviceException() {
		return adviceException;
	}

	public void setAdviceException(AdviceException adviceException) {
		this.adviceException = adviceException;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getHasResponse() {
		return hasResponse;
	}

	public void setHasResponse(String hasResponse) {
		this.hasResponse = hasResponse;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}
	
	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate(this.getClass().getSimpleName(),
				ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE));
		return jsonObject.toString();
	}

}
