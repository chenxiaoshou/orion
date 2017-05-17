package com.polaris.common.base;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import net.sf.json.JSONObject;

public class BaseObject implements Serializable {

	private static final long serialVersionUID = 5836536587070114007L;
	
	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate(this.getClass().getSimpleName(),
				ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE));
		return jsonObject.toString();
	}

}
