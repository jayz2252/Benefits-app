package com.speridian.benefits2.beans;
/*
 * @author : swathy.raghu , minnu.john
 */



public class SearchBean {
	
	private Integer benefitPlanId;
	
	private String status;
	private String claimRefNo;
	private String desg;
	
	private String dept;
	private String employeeCode;
	private String fiscalYear;

	
	
	
	
	

	//getters and setters
	
	
	public Integer getBenefitPlanId() {
		return benefitPlanId;
	}


	public String getClaimRefNo() {
		return claimRefNo;
	}


	public void setClaimRefNo(String claimRefNo) {
		this.claimRefNo = claimRefNo;
	}


	public void setBenefitPlanId(Integer benefitPlanId) {
		this.benefitPlanId = benefitPlanId;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}




	public String getDesg() {
		return desg;
	}


	public void setDesg(String desg) {
		this.desg = desg;
	}


	public String getDept() {
		return dept;
	}


	public void setDept(String dept) {
		this.dept = dept;
	}


	public String getFiscalYear() {
		return fiscalYear;
	}


	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	

	
	
}
