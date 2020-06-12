package com.speridian.benefits2.beans;

import java.util.Date;

public class TaxRegimeBean {
	private Integer empTaxRegimeID;
	private Integer employeeId;
	private String employeeCode;
	private Integer taxRegime;
	private String fiscalYear;
	private String updatedBy;
	private Date updatedDate;
	private String createdBy;
	private Date createdDate;
	private String taxRegimeName;
	
	
	public Integer getEmpTaxRegimeID() {
		return empTaxRegimeID;
	}
	public void setEmpTaxRegimeID(Integer empTaxRegimeID) {
		this.empTaxRegimeID = empTaxRegimeID;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public Integer getTaxRegime() {
		return taxRegime;
	}
	public void setTaxRegime(Integer taxRegime) {
		this.taxRegime = taxRegime;
	}
	public String getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getTaxRegimeName() {
		return taxRegimeName;
	}
	public void setTaxRegimeName(String taxRegimeName) {
		this.taxRegimeName = taxRegimeName;
	}

}
