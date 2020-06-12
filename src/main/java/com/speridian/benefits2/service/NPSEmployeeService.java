package com.speridian.benefits2.service;

import java.util.List;

import com.speridian.benefits2.model.pojo.NPSDeniedEmployees;
import com.speridian.benefits2.model.pojo.NPSDenyReasonsMaster;
import com.speridian.benefits2.model.pojo.NPSEmployee;
import com.speridian.benefits2.model.pojo.NPSEmployeeSlabHistory;

public interface NPSEmployeeService {
	
	public NPSEmployee getById(Integer npsEmployeeId);
	public boolean save(NPSEmployee npsemployee);

	public List<NPSEmployee> listAll();
	
	public List<NPSEmployee> listAllnpsEmployeesSlabChangeRequested();

	public boolean update(NPSEmployee npsEmployee);
	
	public NPSEmployee getByEmpId(Integer empid);
	
	public boolean saveHistory(NPSEmployeeSlabHistory npsemployeeHistory);
	
	public List<NPSEmployeeSlabHistory> listSlabChangeByNPSEmpId(Integer NPSEmployeeId);
	
	public List<NPSEmployee> listAllMonthlyEmp(String month, String year);
	
	public List<NPSEmployeeSlabHistory> listVolSlab(String changedEntity,String status,String npsEmpStatus);
	public List<NPSEmployeeSlabHistory> listHistoryByFiscalYear(String fiscalYear,String changedEntity,Integer npsEmpId);
	
	public NPSEmployeeSlabHistory getSlabHistory(Integer slabHistoryId);
	
	public NPSDeniedEmployees getDeniedEmployeesByFiscalYear(String fiscalYear,Integer empId);
	
	public boolean saveDeniedEmployee(NPSDeniedEmployees npsDeniedEmployees);
	public  List<NPSDenyReasonsMaster> listAllDenyReasons();
	public List<NPSDeniedEmployees> listAllDeniedEmployees();

	public boolean delete(NPSEmployee npsEmployee);

}
