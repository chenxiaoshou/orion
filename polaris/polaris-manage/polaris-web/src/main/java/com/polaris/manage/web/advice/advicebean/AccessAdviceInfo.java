package com.polaris.manage.web.advice.advicebean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.polaris.common.base.BaseObject;

/**
 * 用来记录访问日志的切面bean
 * 
 */
public class AccessAdviceInfo extends BaseObject implements Serializable {

	private static final long serialVersionUID = -5877789774624894127L;

	private String visitor; // 请求者身份标识

	private String visitorIp; // 访问者Ip

	private String requestURL; // 请求的完整路径

	private String className; // 类名

	private String methodName; // 方法名

	private Map<String, String> inputParamMap = new LinkedHashMap<>(); // 入参

	private boolean returned; // 方法是否有返回值

	private String response; // 返回值的序列化结果

	private long tookMillSeconds; // 方法运行总耗时（毫秒）

	private boolean successed; // 方法是否成功结束运行

	private String exceptionTime; // 异常发生的时间

	private String errMsg; // 异常信息

	@JsonIgnore
	private AdviceException adviceException; // 异常对象

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

	public Map<String, String> getInputParamMap() {
		return inputParamMap;
	}

	public void setInputParamMap(Map<String, String> inputParamMap) {
		this.inputParamMap = inputParamMap;
	}

	public void putInputParam(String key, String value) {
		if (!inputParamMap.containsKey(key)) {
			inputParamMap.put(key, value);
		}
	}
	
	public void removeInputParam(String key) {
		if (inputParamMap.containsKey(key)) {
			inputParamMap.remove(key);
		}
	}
	
	public long getTookMillSeconds() {
		return tookMillSeconds;
	}

	public void setTookMillSeconds(long tookMillSeconds) {
		this.tookMillSeconds = tookMillSeconds;
	}

	public boolean isSuccessed() {
		return successed;
	}

	public void setSuccessed(boolean successed) {
		this.successed = successed;
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

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

}
