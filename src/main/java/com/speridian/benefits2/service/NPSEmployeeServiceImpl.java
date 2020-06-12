package com.speridian.benefits2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.model.dao.NPSEmployeeDao;
import com.speridian.benefits2.model.pojo.NPSDeniedEmployees;
import com.speridian.benefits2.model.pojo.NPSDenyReasonsMaster;
import com.speridian.benefits2.model.pojo.NPSEmployee;
import com.speridian.benefits2.model.pojo.NPSEmployeeSlabHistory;
import com.speridian.benefits2.model.pojo.PFDeniedEmployees;
import com.speridian.benefits2.model.pojo.PFEmployee;
import com.speridian.benefits2.model.util.BenefitsConstants;

public class NPSEmployeeServiceImpl implements NPSEmployeeService{

	@Autowired
	NPSEmployeeDao npsEmployeeDao;
	
	public NPSEmployee getById(Integer npsEmployeeId) {
		return npsEmployeeDao.getById(npsEmployeeId);
	}

	@Override
	public boolean save(NPSEmployee npsemployee) {
	
		return npsEmployeeDao.save(npsemployee);
	}


	@Override
	public List<NPSEmployee> listAll() {
		List<NPSEmployee> npsEmployees=npsEmployeeDao.listAll();
		List<NPSEmployee> employees=new ArrayList<NPSEmployee>();
		for(NPSEmployee npsEmployee:npsEmployees){
			if(!npsEmployee.getStatus().equals(BenefitsConstants.NPS_EMPLOYEE_STATUS_SAVED)){
				employees.add(npsEmployee);
			}
		}
		
		return employees;
	}

	@Override
	public NPSEmployee getByEmpId(Integer empId) {
		return npsEmployeeDao.getByEmpId(empId);
	}

	@Override
	public boolean update(NPSEmployee npsEmployee) {
		return npsEmployeeDao.update(npsEmployee);
	}

	@Override
	public List<NPSEmployee> listAllnpsEmployeesSlabChangeRequested() {
		return npsEmployeeDao.listAllNPSEmployeesSlabChangeRequested();
	}

	@Override
	public boolean saveHistory(NPSEmployeeSlabHistory npsemployeeHistory) {
	return npsEmployeeDao.saveHistory(npsemployeeHistory);
	}

	@Override
	public List<NPSEmployeeSlabHistory> listSlabChangeByNPSEmpId(
			Integer npsEmployeeId) {
		return npsEmployeeDao.listSlabChangeByNPSEmpId(npsEmployeeId);
	}

	@Override
	public List<NPSEmployee> listAllMonthlyEmp(String month, String year) {
		
		return npsEmployeeDao.listAllMonthlyEmp(month, year);
	}

	@Override
	public List<NPSEmployeeSlabHistory> listVolSlab(String changedEntity,String status,String npsEmpStatus) {
		return npsEmployeeDao.listVolSlab(changedEntity,status,npsEmpStatus);
	}

	@Override
	public NPSEmployeeSlabHistory getSlabHistory(Integer slabHistoryId) {
		return npsEmployeeDao.getSlabHistory(slabHistoryId);
	}

	@Override
	public List<NPSEmployeeSlabHistory> listHistoryByFiscalYear(String fiscalYear,String changedEntity,Integer npsEmpId) {
		return npsEmployeeDao.getSlabHistoryListByFiscalYear(fiscalYear,changedEntity,npsEmpId);
	}

	@Override
	public NPSDeniedEmployees getDeniedEmployeesByFiscalYear(String fiscalYear,
			Integer empId) {
		return npsEmployeeDao.getDeniedEmployeesByFiscalYear(fiscalYear, empId);
	}

	@Override
	public boolean saveDeniedEmployee(NPSDeniedEmployees npsDeniedEmployees) {
		return npsEmployeeDao.saveDeniedEmployees(npsDeniedEmployees);
	}

	@Override
	public List<NPSDenyReasonsMaster> listAllDenyReasons() {
		return npsEmployeeDao.listAllDenyReasons();
	}

	@Override
	public List<NPSDeniedEmployees> listAllDeniedEmployees() {
		return npsEmployeeDao.listAllDeniedEmployees();
	}

	@Override
	public boolean delete(NPSEmployee npsEmployee) {
		return npsEmployeeDao.delete(npsEmployee);
		
	}
	

	
}
