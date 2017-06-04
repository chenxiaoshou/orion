package com.polaris.manage.model.mysql.auth;

import com.polaris.manage.model.mysql.BaseMysqlObject;

public class MapPrivilegeElement extends BaseMysqlObject {

	private static final long serialVersionUID = -7208177982189783654L;

	private String privilegeId;

	private String elementId;

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

}
