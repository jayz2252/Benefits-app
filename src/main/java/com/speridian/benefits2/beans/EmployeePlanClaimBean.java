package com.speridian.benefits2.beans;

import java.util.List;

import com.speridian.benefits2.model.pojo.BenefitPlanEmployee;
import com.speridian.benefits2.model.pojo.Employee;

/*
 * @author: swathy.raghu
 */

public class EmployeePlanClaimBean {
	Employee employee;
	List<BenefitPlanEmployee> benefitPlanEmployee;
	
	Integer noOfPlans;
	
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public List<BenefitPlanEmployee> getBenefitPlanEmployee() {
		return benefitPlanEmployee;
	}
	public void setBenefitPlanEmployee(List<BenefitPlanEmployee> benefitPlanEmployee) {
		this.benefitPlanEmployee = benefitPlanEmployee;
	}
	public Integer getNoOfPlans() {
		return noOfPlans;
	}
	public void setNoOfPlans(Integer noOfPlans) {
		this.noOfPlans = noOfPlans;
	}
	
	
	public EmployeePlanClaimBean(Employee employee,
			List<BenefitPlanEmployee> benefitPlanEmployee,
			 Integer noOfPlans) {
		super();
		this.employee = employee;
		this.benefitPlanEmployee = benefitPlanEmployee;
		this.noOfPlans = noOfPlans;
	}
	public EmployeePlanClaimBean() {
		super();
	}
	
	
	
	

}
