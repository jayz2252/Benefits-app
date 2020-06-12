package com.speridian.benefits2.beans;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.speridian.benefits2.model.pojo.BenefitPlanEmployee;

/**
 * 
 * <pre>
 * Bean for Direct claim upload
 * </pre>
 *
 * @author aswin.jose
 * @since 06-Mar-2017
 *
 */
public class BenefitPlanDirectClaimBean {
	
	private BenefitPlanEmployee planEmployee;
	
	private String directClaimId;
	private String fiscalYear;
	private String claimRefNo;
	private String extRefNo;
	private String amount;
	private String periodFrom;
	private String periodTo;
	private String issuedBy;
	private String issuedDate;
	private String status;
	private String updatedBy;
	private String updatedDate;
	private String createdBy;
	private String createdDate;
	

	public String getDirectClaimId() {
		return directClaimId;
	}
	public void setDirectClaimId(String directClaimId) {
		this.directClaimId = directClaimId;
	}
	public BenefitPlanEmployee getPlanEmployee() {
		return planEmployee;
	}
	public void setPlanEmployee(BenefitPlanEmployee planEmployee) {
		this.planEmployee = planEmployee;
	}
	public String getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public String getClaimRefNo() {
		return claimRefNo;
	}
	public void setClaimRefNo(String claimRefNo) {
		this.claimRefNo = claimRefNo;
	}
	public String getExtRefNo() {
		return extRefNo;
	}
	public void setExtRefNo(String extRefNo) {
		this.extRefNo = extRefNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPeriodFrom() {
		return periodFrom;
	}
	public void setPeriodFrom(String periodFrom) {
		this.periodFrom = periodFrom;
	}
	public String getPeriodTo() {
		return periodTo;
	}
	public void setPeriodTo(String periodTo) {
		this.periodTo = periodTo;
	}
	public String getIssuedBy() {
		return issuedBy;
	}
	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}
	public String getIssuedDate() {
		return issuedDate;
	}
	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	
}
