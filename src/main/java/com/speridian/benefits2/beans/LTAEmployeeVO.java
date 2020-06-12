package com.speridian.benefits2.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.LTAEmployeeDependent;

public class LTAEmployeeVO {
	
	private Integer ltaEmployeeId;

	
	private Employee employee;

	
	private String block;
	
	private String fiscalYear;

	
	private Integer noOfLeave;
	
	
	private Date periodFrom;

	
	private Date periodTill;

	
	private String origin;

	
	private String destination1;

	private String routeDescription;
	
	private BigDecimal actualFare;


	private BigDecimal shortestFare;
	
	
	private BigDecimal approvedAmt;
	
	
	private String status;

	
	private String hrApprovedBy;
	
	
	private String finApprovedBy;
	
	
	private List<LTAEmployeeDependent> dependents;
	
	
	/*
	 * Audit fields
	 */

	
	private String updatedBy;

	
	private Date updatedDate;

	
	private String createdBy;

	
	private Date createdDate;
	
	private String declineReason;
	
	

	

	public String getDeclineReason() {
		return declineReason;
	}

	public void setDeclineReason(String declineReason) {
		this.declineReason = declineReason;
	}

	public Integer getLtaEmployeeId() {
		return ltaEmployeeId;
	}

	public void setLtaEmployeeId(Integer ltaEmployeeId) {
		this.ltaEmployeeId = ltaEmployeeId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public Integer getNoOfLeave() {
		return noOfLeave;
	}

	public void setNoOfLeave(Integer noOfLeave) {
		this.noOfLeave = noOfLeave;
	}

	public Date getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(Date periodFrom) {
		this.periodFrom = periodFrom;
	}

	public Date getPeriodTill() {
		return periodTill;
	}

	public void setPeriodTill(Date periodTill) {
		this.periodTill = periodTill;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination1() {
		return destination1;
	}

	public void setDestination1(String destination1) {
		this.destination1 = destination1;
	}

	public String getRouteDescription() {
		return routeDescription;
	}

	public void setRouteDescription(String routeDescription) {
		this.routeDescription = routeDescription;
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

	public BigDecimal getActualFare() {
		return actualFare;
	}

	public void setActualFare(BigDecimal actualFare) {
		this.actualFare = actualFare;
	}

	public BigDecimal getShortestFare() {
		return shortestFare;
	}

	public void setShortestFare(BigDecimal shortestFare) {
		this.shortestFare = shortestFare;
	}

	public BigDecimal getApprovedAmt() {
		return approvedAmt;
	}

	public void setApprovedAmt(BigDecimal approvedAmt) {
		this.approvedAmt = approvedAmt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
	public List<LTAEmployeeDependent> getDependents() {
		return dependents;
	}

	public void setDependents(List<LTAEmployeeDependent> dependents) {
		this.dependents = dependents;
	}

	
	public String getHrApprovedBy() {
		return hrApprovedBy;
	}

	public void setHrApprovedBy(String hrApprovedBy) {
		this.hrApprovedBy = hrApprovedBy;
	}

	public String getFinApprovedBy() {
		return finApprovedBy;
	}

	public void setFinApprovedBy(String finApprovedBy) {
		this.finApprovedBy = finApprovedBy;
	}
	

}
