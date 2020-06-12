package com.speridian.benefits2.beans;

public class LTASearchBean {
	private String ltaEmployeeId;
	private String block;

	public LTASearchBean(String ltaEmployeeId, String block) {
		super();
		this.ltaEmployeeId = ltaEmployeeId;
		this.block = block;
	}

	public LTASearchBean() {
		super();
	}

	public String getLtaEmployeeId() {
		return ltaEmployeeId;
	}

	public void setLtaEmployeeId(String ltaEmployeeId) {
		this.ltaEmployeeId = ltaEmployeeId;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

}
