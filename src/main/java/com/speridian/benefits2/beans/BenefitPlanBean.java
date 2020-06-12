package com.speridian.benefits2.beans;


/**
 * 
 * <pre>
 * Backing Bean for BenefitPlan CRUD
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 05-Feb-2017
 *
 */
public class BenefitPlanBean {
	
	private String planName;
	private String planDesc;
	
	private String effFrom;
	private String effTill;
	
	private String yearlyDeduction;
	private String yearlyClaim;
	
	private Boolean active;
	
	private String claimType;
	private String claimFrequency;
	
	private Boolean claimDocsRequired; 
	
	private String mode;
	private String step;
	private String status;
	

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private Integer contactLength;
	private Integer categoryLength;
	private Integer categoryMiscLength;
	private Integer documentLength;
	
	
	
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getPlanDesc() {
		return planDesc;
	}
	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
	}
	public String getEffFrom() {
		return effFrom;
	}
	public void setEffFrom(String effFrom) {
		this.effFrom = effFrom;
	}
	public String getEffTill() {
		return effTill;
	}
	public void setEffTill(String effTill) {
		this.effTill = effTill;
	}
	public String getYearlyDeduction() {
		return yearlyDeduction;
	}
	public void setYearlyDeduction(String yearlyDeduction) {
		this.yearlyDeduction = yearlyDeduction;
	}
	public String getYearlyClaim() {
		return yearlyClaim;
	}
	public void setYearlyClaim(String yearlyClaim) {
		this.yearlyClaim = yearlyClaim;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public String getClaimFrequency() {
		return claimFrequency;
	}
	public void setClaimFrequency(String claimFrequency) {
		this.claimFrequency = claimFrequency;
	}
	public Boolean getClaimDocsRequired() {
		return claimDocsRequired;
	}
	public void setClaimDocsRequired(Boolean claimDocsRequired) {
		this.claimDocsRequired = claimDocsRequired;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	
	
	
	public Integer getContactLength() {
		return contactLength;
	}
	public void setContactLength(Integer contactLength) {
		this.contactLength = contactLength;
	}
	public Integer getCategoryLength() {
		return categoryLength;
	}
	public void setCategoryLength(Integer categoryLength) {
		this.categoryLength = categoryLength;
	}
	public Integer getCategoryMiscLength() {
		return categoryMiscLength;
	}
	public void setCategoryMiscLength(Integer categoryMiscLength) {
		this.categoryMiscLength = categoryMiscLength;
	}
	public Integer getDocumentLength() {
		return documentLength;
	}
	public void setDocumentLength(Integer documentLength) {
		this.documentLength = documentLength;
	}
	@Override
	public String toString() {
		return "BenefitPlanBean [planName=" + planName + ", planDesc="
				+ planDesc + ", effFrom=" + effFrom + ", effTill=" + effTill
				+ ", yearlyDeduction=" + yearlyDeduction + ", yearlyClaim="
				+ yearlyClaim + ", active=" + active + ", claimType="
				+ claimType + ", claimFrequency=" + claimFrequency
				+ ", claimDocsRequired=" + claimDocsRequired + ", mode=" + mode
				+ ", step=" + step + "]";
	}
	
	
}


