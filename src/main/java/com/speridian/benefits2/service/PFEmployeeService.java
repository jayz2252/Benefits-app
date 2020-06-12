package com.speridian.benefits2.service;

import java.util.List;

import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.PFDeniedEmployees;
import com.speridian.benefits2.model.pojo.PFDenyReasonsMaster;
import com.speridian.benefits2.model.pojo.PFEmployee;
import com.speridian.benefits2.model.pojo.PFEmployeeSlabHistory;

public interface PFEmployeeService {
	
	public PFEmployee getById(Integer pfEmployeeId);
	public boolean save(PFEmployee pfemployee);

	public List<PFEmployee> listAll();
	
	public List<PFEmployee> listAllPfEmployeesSlabChangeRequested();

	public boolean update(PFEmployee pfEmployee);
	
	public PFEmployee getByEmpId(Integer empid);
	
	public boolean saveHistory(PFEmployeeSlabHistory pfemployeeHistory);
	
	public List<PFEmployeeSlabHistory> listSlabChangeByPFEmpId(Integer pfEmployeeId);
	
	public List<PFEmployee> listAllMonthlyEmp(String month, String year);
	
	public List<PFEmployeeSlabHistory> listVolSlab(String changedEntity,String status,String pfEmpStatus);
	public List<PFEmployeeSlabHistory> listHistoryByFiscalYear(String fiscalYear,String changedEntity,Integer pfEmpId);
	
	public PFEmployeeSlabHistory getSlabHistory(Integer slabHistoryId);
	
	public PFDeniedEmployees getDeniedEmployeesByFiscalYear(String fiscalYear,Integer empId);
	
	public boolean saveDeniedEmployee(PFDeniedEmployees pfDeniedEmployees);
	public  List<PFDenyReasonsMaster> listAllDenyReasons();
	public List<PFDeniedEmployees> listAllDeniedEmployees();

	public boolean delete(PFEmployee pfEmployee);

}
