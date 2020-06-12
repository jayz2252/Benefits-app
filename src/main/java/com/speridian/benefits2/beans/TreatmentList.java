package com.speridian.benefits2.beans;

import java.math.BigDecimal;
import java.util.List;

import com.speridian.benefits2.model.pojo.Treatment;

public class TreatmentList {
	
	public List<Treatment> treatments;
	
	public List<BigDecimal> coveredAmounts;

	public List<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}

	public List<BigDecimal> getCoveredAmounts() {
		return coveredAmounts;
	}

	public void setCoveredAmounts(List<BigDecimal> coveredAmounts) {
		this.coveredAmounts = coveredAmounts;
	}

	

	

	
	

	
	
	
	

}
