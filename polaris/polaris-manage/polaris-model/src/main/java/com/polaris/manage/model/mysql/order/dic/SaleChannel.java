package com.polaris.manage.model.mysql.order.dic;

public enum SaleChannel {
	Amazon("Amazon", "亚马逊"), 
	Taobao("TaoBao", "淘宝"), 
	Tianmao("TianMao", "天猫"), 
	JingDong("JD", "京东"), 
	Ebay("Ebay", "Ebay"), 
	SMT("SMT", "速卖通"), 
	Wish("Wish", "Wish"), 
	Linio("Linio", "Linio"), 
	Lazada("Lazada", "Lazada");

	private String code;

	private String desc;

	private SaleChannel(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return this.code;
	}

	public String getDesc() {
		return this.desc;
	}

	public SaleChannel getSaleChannelByCode(String code) {
		for (SaleChannel saleChannel : SaleChannel.values()) {
			if (saleChannel.getCode().equalsIgnoreCase(code)) {
				return saleChannel;
			}
		}
		return null;
	}

	public SaleChannel getSaleChannelByDesc(String desc) {
		for (SaleChannel saleChannel : SaleChannel.values()) {
			if (saleChannel.getDesc().equalsIgnoreCase(desc)) {
				return saleChannel;
			}
		}
		return null;
	}

}
