package com.speridian.benefits2.beans;

import java.math.BigDecimal;

public class CategoryBean {
	private Integer categoryId;
	private String categoryName;
	private BigDecimal reqAmt;
	private BigDecimal apprAmt;
	private BigDecimal rejAmt;
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public BigDecimal getReqAmt() {
		return reqAmt;
	}
	public void setReqAmt(BigDecimal reqAmt) {
		this.reqAmt = reqAmt;
	}
	public BigDecimal getApprAmt() {
		return apprAmt;
	}
	public void setApprAmt(BigDecimal apprAmt) {
		this.apprAmt = apprAmt;
	}
	public BigDecimal getRejAmt() {
		return rejAmt;
	}
	public void setRejAmt(BigDecimal rejAmt) {
		this.rejAmt = rejAmt;
	}
	
	
}
