package com.speridian.benefits2.beans;

import java.math.BigDecimal;
import java.util.List;

import com.speridian.benefits2.model.pojo.BenefitPlanEmployee;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployeeClaimPeriod;

public class ClaimPeriodBean {
	private BenefitPlanEmployee planEmployee;
	private List<BenefitPlanEmployeeClaimPeriod> claimPeriods;
	private BigDecimal taxableAmt;
	public BenefitPlanEmployee getPlanEmployee() {
		return planEmployee;
	}
	public void setPlanEmployee(BenefitPlanEmployee planEmployee) {
		this.planEmployee = planEmployee;
	}
	public List<BenefitPlanEmployeeClaimPeriod> getClaimPeriods() {
		return claimPeriods;
	}
	public void setClaimPeriods(List<BenefitPlanEmployeeClaimPeriod> claimPeriods) {
		this.claimPeriods = claimPeriods;
	}
	
	
	public BigDecimal getTaxableAmt() {
		return taxableAmt;
	}
	public void setTaxableAmt(BigDecimal taxableAmt) {
		this.taxableAmt = taxableAmt;
	}
	@Override
	public String toString() {
		return "ClaimPeriodBean [planEmployee=" + planEmployee
				+ ", claimPeriods=" + claimPeriods + "]";
	}
	
	
	
}
