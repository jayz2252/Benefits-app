package com.speridian.benefits2.beans;

import com.speridian.benefits2.model.pojo.BenefitPlan;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.PlanEmployeeDenies;

/**
 * 
 * <pre>
 * Purpose of class
 * </pre>
 *
 * @author swathy.raghu, jithin.kuriakose
 * @since 10-Feb-2017
 */
public class EmployeePlanBean {

	private BenefitPlan plan;
	private Employee employee;
	
	private Integer planEmployeeId;
	
	
	private String planStatus;
	private String denyReason;
	
	
	

	public EmployeePlanBean() {
		super();
	}


	public EmployeePlanBean(BenefitPlan plan, Employee employee) {
		super();
		this.plan = plan;
		this.employee = employee;
	}


	public BenefitPlan getPlan() {
		return plan;
	}


	public void setPlan(BenefitPlan plan) {
		this.plan = plan;
	}

	public String getPlanStatus() {
		return planStatus;
	}


	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}


	public Employee getEmployee() {
		return employee;
	}


	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	public Integer getPlanEmployeeId() {
		return planEmployeeId;
	}


	public void setPlanEmployeeId(Integer planEmployeeId) {
		this.planEmployeeId = planEmployeeId;
	}


	public String getDenyReason() {
		return denyReason;
	}


	public void setDenyReason(String denyReason) {
		this.denyReason = denyReason;
	}
	
	

}
