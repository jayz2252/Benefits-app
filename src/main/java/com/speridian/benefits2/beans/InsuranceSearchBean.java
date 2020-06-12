package com.speridian.benefits2.beans;

import java.util.Date;



public class InsuranceSearchBean {
	
	private String claimRefNo;
	private String empCode;
	private String claimType;
	private String fromDate;
	private String toDate;
	private String fiscalYear;
	
	
	
	public String getClaimRefNo() {
		return claimRefNo;
	}
	public void setClaimRefNo(String claimRefNo) {
		this.claimRefNo = claimRefNo;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	
	

}
