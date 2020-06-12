package com.speridian.benefits2.beans;

/**
 * 
 * <pre>
 * View Wrapper for Dependent - PlanDependentCategory
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 05-Apr-2017
 *
 */
public class DependentCategoryBean {
	
	private String depId;
	private String depName;
	private String depDob;
	private String relationship;
	private String yearlyClaim;
	private String yearlyDeduction;
	
	public DependentCategoryBean() {
		super();
	}

	public DependentCategoryBean(String depId, String depName) {
		super();
		this.depId = depId;
		this.depName = depName;
	}

	public DependentCategoryBean(String depId, String depName, String relationship,
			String yearlyClaim, String yearlyDeduction) {
		super();
		this.depId = depId;
		this.depName = depName;
		this.relationship = relationship;
		this.yearlyClaim = yearlyClaim;
		this.yearlyDeduction = yearlyDeduction;
	}
	
	public String getDepId() {
		return depId;
	}
	
	public void setDepId(String depId) {
		this.depId = depId;
	}
	
	public String getDepName() {
		return depName;
	}
	
	public void setDepName(String depName) {
		this.depName = depName;
	}
	
	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getDepDob() {
		return depDob;
	}
	
	public void setDepDob(String depDob) {
		this.depDob = depDob;
	}
	
	public String getYearlyClaim() {
		return yearlyClaim;
	}
	
	public void setYearlyClaim(String yearlyClaim) {
		this.yearlyClaim = yearlyClaim;
	}
	
	public String getYearlyDeduction() {
		return yearlyDeduction;
	}
	
	public void setYearlyDeduction(String yearlyDeduction) {
		this.yearlyDeduction = yearlyDeduction;
	}
}