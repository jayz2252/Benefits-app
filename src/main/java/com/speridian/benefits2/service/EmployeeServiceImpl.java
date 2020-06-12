package com.speridian.benefits2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.beans.EmployeePlanBean;
import com.speridian.benefits2.model.dao.BenefitsPlanEmployeeDao;
import com.speridian.benefits2.model.dao.DependentDao;
import com.speridian.benefits2.model.dao.EmpBenPlansYrlyOptsDao;
import com.speridian.benefits2.model.dao.EmployeeDao;
import com.speridian.benefits2.model.dao.PlanEmployeeDeniesDao;
import com.speridian.benefits2.model.pojo.BenefitPlan;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployee;
import com.speridian.benefits2.model.pojo.Dependent;
import com.speridian.benefits2.model.pojo.EmpBenPlansYrlyOpts;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.PlanEmployeeDenies;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.ws.client.mirror.soap.MirrorDataService;

/**
 * 
 * <pre>
 * Service interface for Employee related processes
 * </pre>
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 05-Feb-2017
 *
 */
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao employeeDao ;
	
	@Autowired
	BenefitsPlanEmployeeDao benefitPlanEmployeeDao;
	
	@Autowired
	EmpBenPlansYrlyOptsDao empBenPlansYrlyOptsDao;
	
	@Autowired
	DependentDao dependentDao;
	
	@Autowired
	PlanEmployeeDeniesDao planEmployeeDeniesDao;
	
	
	@Override
	public List<Employee> listAll() {
		return employeeDao.listAll();
		
	}

	@Override
	public Employee get(Integer employeeId) {
		return employeeDao.get(employeeId);
	}

	@Override
	public BenefitPlanEmployee getPlanEmployee(Integer planId,
			Integer employeeId, String fiscalYear) {
		return benefitPlanEmployeeDao.get(planId, employeeId, fiscalYear);
	}
	
	@Override
	public PlanEmployeeDenies getPlanEmployeeDeny (Integer planId, Integer employeeId, String fiscalYear) {
		return planEmployeeDeniesDao.get(planId, employeeId, fiscalYear);
	}

	@Override
	public List<String> listAllDesignations() {
		return employeeDao.listAllDesignations();
	}

	@Override
	public List<String> listAllDesignations(String deptName) {
		return employeeDao.listAllDesignations(deptName);
	}
	
	@Override
	public void insert(EmpBenPlansYrlyOpts empBenPlansYrlyOpts) {
		empBenPlansYrlyOptsDao.insert(empBenPlansYrlyOpts);
		
	}

	@Override
	public EmpBenPlansYrlyOpts getPlanYrlyOpts(Integer employeeId,
			String fiscalYear) {
		return empBenPlansYrlyOptsDao.get(fiscalYear, employeeId);
	}

	@Override
	public void insert(EmployeePlanBean employeePlanBean,EmpBenPlansYrlyOpts benPlansYrlyOpts) {
		BenefitPlanEmployee benefitPlanEmployee = new BenefitPlanEmployee();
		benefitPlanEmployee.setBenefitPlan(employeePlanBean.getPlan());
		benefitPlanEmployee.setEmployee(employeePlanBean.getEmployee());
		
		benefitPlanEmployee.setActive(false);
		benefitPlanEmployee.setEffFrom(employeePlanBean.getPlan().getEffFrom());
		benefitPlanEmployee.setEffTill(employeePlanBean.getPlan().getEffTill());
		//benefitPlanEmployee.setPlanCategory(employeePlanBean.getPlan().);
		benefitPlanEmployee.setStatus(BenefitsConstants.EMP_PLAN_OPT_STATUS_NOT_APPROVED);
		benefitPlanEmployee.setFiscalYear(benPlansYrlyOpts.getFiscalYear());
		benefitPlanEmployee.setEmpBenYearlyOpt(benPlansYrlyOpts);
		benefitPlanEmployeeDao.insert(benefitPlanEmployee);
	}
	
	@Override
	public EmpBenPlansYrlyOpts getPlanYrlyOpts (Integer yealryOptId){
		return empBenPlansYrlyOptsDao.get(yealryOptId);
	}
	
	@Override
	public Boolean editYearlyOpt (EmpBenPlansYrlyOpts opt){
		return empBenPlansYrlyOptsDao.update(opt);
	}

	@Override
	public List<Employee> listAll(String empcode, String desg, String dept) {
		
		return employeeDao.listAll(empcode, desg, dept);
	}

	@Override
	public List<BenefitPlanEmployee> listPlanEmployee(Integer empid,
			String fiscalYear, Boolean active) {
		return benefitPlanEmployeeDao.getByIds(empid, fiscalYear, active);
	}
	
	@Override
	public Boolean addNewEmployee(Employee employee){
		return employeeDao.insert(employee);
	}
	
	@Override
	public Boolean editEmployee(Employee employee){
		return employeeDao.update(employee);
	}
	
	@Override
	public Employee getEmployeeByCode(String employeeCode){
		return employeeDao.getByEmployeeCode(employeeCode);
	}
	
	@Override
	public List<String> listAllRelationships(){
		return dependentDao.listRelationships();
	}
	
	@Override
	public List<Dependent> listAllDependents(Integer employeeId){
		return dependentDao.listByEmployee(employeeId);
	}
	
	@Override
	public Dependent getDependent(Integer employeeId, String relationship){
		return dependentDao.get(employeeId, relationship);
	}
	
	@Override
	public List<Dependent> listAllDependents(Integer employeeId, String relationship, Short ageFrom, Short ageTo){
		return dependentDao.listAll(employeeId, relationship, ageFrom, ageTo);
	}
	
	@Override
	public List<Dependent> listAllDependents(Integer employeeId, List<String> relationships){
		return dependentDao.listAll(employeeId, relationships);
	}
	
	
	@Override
	public Dependent getDependent(Integer dependentId){
		return dependentDao.get(dependentId);
	}
	

}
