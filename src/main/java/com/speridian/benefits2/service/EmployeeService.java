package com.speridian.benefits2.service;

import java.util.List;

import com.speridian.benefits2.beans.EmployeePlanBean;
import com.speridian.benefits2.model.pojo.BenefitPlan;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployee;
import com.speridian.benefits2.model.pojo.Dependent;
import com.speridian.benefits2.model.pojo.EmpBenPlansYrlyOpts;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.PlanEmployeeDenies;

/**
 * 
 * <pre>
 * Service interface for Employee related processes
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 05-Feb-2017
 *
 */
public interface EmployeeService {
	
	public List<Employee> listAll();
	public List<String> listAllDesignations();
	public List<String> listAllDesignations(String deptName);
	public List<Employee> listAll(String empcode,String desg,String dept);
	public List<BenefitPlanEmployee> listPlanEmployee(Integer empid,String fiscalYear,Boolean active);
	public List<String> listAllRelationships();
	public List<Dependent> listAllDependents(Integer employeeId);
	public List<Dependent> listAllDependents(Integer employeeId, String relationship, Short ageFrom, Short ageTo);
	public List<Dependent> listAllDependents(Integer employeeId, List<String> relationships);
	
	public Employee get(Integer employeeId);
	public Dependent getDependent(Integer dependentId);
	public Dependent getDependent(Integer employeeId, String relationship);
	
	
	public BenefitPlanEmployee getPlanEmployee(Integer planId, Integer employeeId, String fiscalYear);
	public PlanEmployeeDenies getPlanEmployeeDeny (Integer planId, Integer employeeId, String fiscalYear);
	
	public EmpBenPlansYrlyOpts getPlanYrlyOpts(Integer employeeId, String fiscalYear);
	public void insert(EmpBenPlansYrlyOpts empBenPlansYrlyOpts);

	public void insert(EmployeePlanBean employeePlanBean,EmpBenPlansYrlyOpts benPlansYrlyOpts);
	
	public EmpBenPlansYrlyOpts getPlanYrlyOpts (Integer yealryOptId);
	public Boolean editYearlyOpt (EmpBenPlansYrlyOpts opt);
	
	public Boolean addNewEmployee(Employee employee);
	public Boolean editEmployee(Employee employee);
	public Employee getEmployeeByCode(String employeeCode);
}
