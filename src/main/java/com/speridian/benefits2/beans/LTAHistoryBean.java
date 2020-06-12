package com.speridian.benefits2.beans;

import java.util.List;

import com.speridian.benefits2.model.pojo.LTAEmployee;
import com.speridian.benefits2.model.pojo.LTAEmployeeDependent;

public class LTAHistoryBean {
	LTAEmployee ltaEmployee;
	List<LTAEmployeeDependent> dependents;
	public LTAEmployee getLtaEmployee() {
		return ltaEmployee;
	}
	public void setLTAEmployee(LTAEmployee employee) {
		this.ltaEmployee = employee;
	}
	public List<LTAEmployeeDependent> getDependents() {
		return dependents;
	}
	public void setDependents(List<LTAEmployeeDependent> dependents) {
		this.dependents = dependents;
	}
	
	

}
