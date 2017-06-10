package com.polaris.manage.model.tools.dic.order;

public enum GenderEnum {

	Male("男性"), Female("女性"), Others("其他");

	private String desc;

	private GenderEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}

	public GenderEnum getGenderByDesc(String desc) {
		for (GenderEnum genderEnum : GenderEnum.values()) {
			if (genderEnum.getDesc().equalsIgnoreCase(desc)) {
				return genderEnum;
			}
		}
		return null;
	}

}
