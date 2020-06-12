package com.speridian.benefits2.beans;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * <pre>
 * View Object for Eligible Dependents, merges dependent and amount informations
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 17-May-2017
 *
 */
public class INSEligibleDependentVO {
	
	private Integer depId;
	
	private String depName;
	
	private String depRelationship;
	
	private String depGender;
	
	private Date depDateOfBirth;
	
	private BigDecimal employeeContribution;
	
	private BigDecimal companyContribution;
	
	private BigDecimal eaicYearlyDeduction;
	
	private Boolean isSelf;
	private Boolean isINSEnroled;
	private Boolean isEAICEnroled;
	
	

	



	private Integer flag;
	
	
	public Boolean getIsINSEnroled() {
		return isINSEnroled;
	}

	public void setIsINSEnroled(Boolean isINSEnroled) {
		this.isINSEnroled = isINSEnroled;
	}
	
	

	public Boolean getIsEAICEnroled() {
		return isEAICEnroled;
	}

	public void setIsEAICEnroled(Boolean isEAICEnroled) {
		this.isEAICEnroled = isEAICEnroled;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public INSEligibleDependentVO() {
		super();
	}

	public INSEligibleDependentVO(Integer depId, String depName, String depRelationship,
			String depGender) {
		super();
		this.depId = depId;
		this.depName = depName;
		this.depRelationship = depRelationship;
		this.depGender = depGender;
	}
	
	

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDepRelationship() {
		return depRelationship;
	}

	public void setDepRelationship(String depRelationship) {
		this.depRelationship = depRelationship;
	}

	public String getDepGender() {
		return depGender;
	}

	public void setDepGender(String depGender) {
		this.depGender = depGender;
	}

	public Date getDepDateOfBirth() {
		return depDateOfBirth;
	}

	public void setDepDateOfBirth(Date depDateOfBirth) {
		this.depDateOfBirth = depDateOfBirth;
	}

	public BigDecimal getEmployeeContribution() {
		return employeeContribution;
	}

	public void setEmployeeContribution(BigDecimal employeeContribution) {
		this.employeeContribution = employeeContribution;
	}

	public BigDecimal getCompanyContribution() {
		return companyContribution;
	}

	public void setCompanyContribution(BigDecimal companyContribution) {
		this.companyContribution = companyContribution;
	}

	public BigDecimal getEaicYearlyDeduction() {
		return eaicYearlyDeduction;
	}

	public void setEaicYearlyDeduction(BigDecimal eaicYearlyDeduction) {
		this.eaicYearlyDeduction = eaicYearlyDeduction;
	}

	public Boolean getIsSelf() {
		return isSelf;
	}

	public void setIsSelf(Boolean isSelf) {
		this.isSelf = isSelf;
	}
}
