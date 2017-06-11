package com.orion.manage.model.tools.dic.order;

public enum SaleChannelEnum {
	Amazon("亚马逊"), Taobao("淘宝"), Tianmao("天猫"), JD("京东"), Ebay("Ebay"), SMT("速卖通"), Wish("Wish"), Linio(
			"Linio"), Lazada("Lazada");

	private String desc;

	private SaleChannelEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}

	public static SaleChannelEnum getSaleChannelByDesc(String desc) {
		for (SaleChannelEnum saleChannel : SaleChannelEnum.values()) {
			if (saleChannel.getDesc().equalsIgnoreCase(desc)) {
				return saleChannel;
			}
		}
		return null;
	}

}
