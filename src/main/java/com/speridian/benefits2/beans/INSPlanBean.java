package com.speridian.benefits2.beans;

public class INSPlanBean {
	
	private String insPlanId;
	private String planName;
	private String planDesc;
	
	private String planType;
	
	private Boolean loyaltyLevelIncluded;
	private String totalLoyaltyLevels;
	private String totalApprovalLevels;
	private Boolean eaicIncluded;
	private String eaicYearlyDeduction;
	private String eaicYearlyCoverage;
	private String yearlyCoverage;
	private Boolean active;
	private Boolean othTreatmentsAppicable;

	private Boolean deleted;
	
	private String effFrom;
	private String effTill;
	
	public String getInsPlanId() {
		return insPlanId;
	}
	public void setInsPlanId(String insPlanId) {
		this.insPlanId = insPlanId;
	}
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
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public Boolean getLoyaltyLevelIncluded() {
		return loyaltyLevelIncluded;
	}
	public void setLoyaltyLevelIncluded(Boolean loyaltyLevelIncluded) {
		this.loyaltyLevelIncluded = loyaltyLevelIncluded;
	}
	public String getTotalLoyaltyLevels() {
		return totalLoyaltyLevels;
	}
	public void setTotalLoyaltyLevels(String totalLoyaltyLevels) {
		this.totalLoyaltyLevels = totalLoyaltyLevels;
	}
	public String getTotalApprovalLevels() {
		return totalApprovalLevels;
	}
	public void setTotalApprovalLevels(String totalApprovalLevels) {
		this.totalApprovalLevels = totalApprovalLevels;
	}
	public Boolean getEaicIncluded() {
		return eaicIncluded;
	}
	public void setEaicIncluded(Boolean eaicIncluded) {
		this.eaicIncluded = eaicIncluded;
	}
	public String getEaicYearlyDeduction() {
		return eaicYearlyDeduction;
	}
	public void setEaicYearlyDeduction(String eaicYearlyDeduction) {
		this.eaicYearlyDeduction = eaicYearlyDeduction;
	}
	public String getEaicYearlyCoverage() {
		return eaicYearlyCoverage;
	}
	public void setEaicYearlyCoverage(String eaicYearlyCoverage) {
		this.eaicYearlyCoverage = eaicYearlyCoverage;
	}
	public String getYearlyCoverage() {
		return yearlyCoverage;
	}
	public void setYearlyCoverage(String yearlyCoverage) {
		this.yearlyCoverage = yearlyCoverage;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Boolean getOthTreatmentsAppicable() {
		return othTreatmentsAppicable;
	}
	public void setOthTreatmentsAppicable(Boolean othTreatmentsAppicable) {
		this.othTreatmentsAppicable = othTreatmentsAppicable;
	}
	
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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
	
	
}
