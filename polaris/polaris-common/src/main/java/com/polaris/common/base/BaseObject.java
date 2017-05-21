package com.polaris.common.base;

import java.io.Serializable;

import com.polaris.common.utils.ToStringUtil;

public class BaseObject implements Serializable {

	private static final long serialVersionUID = 5836536587070114007L;

	@Override
	public String toString() {
		return ToStringUtil.toJSON(this);
	}

}
