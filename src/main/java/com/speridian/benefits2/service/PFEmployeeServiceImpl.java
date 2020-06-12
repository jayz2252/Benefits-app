package com.speridian.benefits2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.model.dao.PFEmployeeDao;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.PFDeniedEmployees;
import com.speridian.benefits2.model.pojo.PFDenyReasonsMaster;
import com.speridian.benefits2.model.pojo.PFEmployee;
import com.speridian.benefits2.model.pojo.PFEmployeeSlabHistory;
import com.speridian.benefits2.model.util.BenefitsConstants;

public class PFEmployeeServiceImpl implements PFEmployeeService{

	@Autowired
	PFEmployeeDao pfEmployeeDao;
	
	public PFEmployee getById(Integer pfEmployeeId) {
		return pfEmployeeDao.getById(pfEmployeeId);
	}

	@Override
	public boolean save(PFEmployee pfemployee) {
		// TODO Auto-generated method stub
		return pfEmployeeDao.save(pfemployee);
	}


	@Override
	public List<PFEmployee> listAll() {
		List<PFEmployee> pfEmployees=pfEmployeeDao.listAll();
		List<PFEmployee> employees=new ArrayList<PFEmployee>();
		for(PFEmployee pfEmployee:pfEmployees){
			if(!pfEmployee.getStatus().equals(BenefitsConstants.PF_EMPLOYEE_STATUS_SAVED)){
				employees.add(pfEmployee);
			}
		}
		
		return employees;
	}

	@Override
	public PFEmployee getByEmpId(Integer empId) {
		return pfEmployeeDao.getByEmpId(empId);
	}

	@Override
	public boolean update(PFEmployee pfEmployee) {
		return pfEmployeeDao.update(pfEmployee);
	}

	@Override
	public List<PFEmployee> listAllPfEmployeesSlabChangeRequested() {
		return pfEmployeeDao.listAllPfEmployeesSlabChangeRequested();
	}

	@Override
	public boolean saveHistory(PFEmployeeSlabHistory pfemployeeHistory) {
	return pfEmployeeDao.saveHistory(pfemployeeHistory);
	}

	@Override
	public List<PFEmployeeSlabHistory> listSlabChangeByPFEmpId(
			Integer pfEmployeeId) {
		return pfEmployeeDao.listSlabChangeByPFEmpId(pfEmployeeId);
	}

	@Override
	public List<PFEmployee> listAllMonthlyEmp(String month, String year) {
		
		return pfEmployeeDao.listAllMonthlyEmp(month, year);
	}

	@Override
	public List<PFEmployeeSlabHistory> listVolSlab(String changedEntity,String status,String pfEmpStatus) {
		return pfEmployeeDao.listVolSlab(changedEntity,status,pfEmpStatus);
	}

	@Override
	public PFEmployeeSlabHistory getSlabHistory(Integer slabHistoryId) {
		return pfEmployeeDao.getSlabHistory(slabHistoryId);
	}

	@Override
	public List<PFEmployeeSlabHistory> listHistoryByFiscalYear(String fiscalYear,String changedEntity,Integer pfEmpId) {
		return pfEmployeeDao.getSlabHistoryListByFiscalYear(fiscalYear,changedEntity,pfEmpId);
	}

	@Override
	public PFDeniedEmployees getDeniedEmployeesByFiscalYear(String fiscalYear,
			Integer empId) {
		return pfEmployeeDao.getDeniedEmployeesByFiscalYear(fiscalYear, empId);
	}

	@Override
	public boolean saveDeniedEmployee(PFDeniedEmployees pfDeniedEmployees) {
		return pfEmployeeDao.saveDeniedEmployees(pfDeniedEmployees);
	}

	@Override
	public List<PFDenyReasonsMaster> listAllDenyReasons() {
		return pfEmployeeDao.listAllDenyReasons();
	}

	@Override
	public List<PFDeniedEmployees> listAllDeniedEmployees() {
		return pfEmployeeDao.listAllDeniedEmployees();
	}

	@Override
	public boolean delete(PFEmployee pfEmployee) {
		return pfEmployeeDao.delete(pfEmployee);
		
	}
	

	
}
