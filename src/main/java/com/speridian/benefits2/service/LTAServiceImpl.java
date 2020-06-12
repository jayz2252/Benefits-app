package com.speridian.benefits2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.beans.LTAEmployeeVO;
import com.speridian.benefits2.model.dao.DependentDao;
import com.speridian.benefits2.model.dao.LTAEmployeeDao;
import com.speridian.benefits2.model.dao.LTAEmployeeDependentDao;
import com.speridian.benefits2.model.pojo.Dependent;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.LTAEmployee;
import com.speridian.benefits2.model.pojo.LTAEmployeeDependent;

public class LTAServiceImpl implements LTAService{

	@Autowired
	LTAEmployeeDao lTAEmployeeDao;
	
	@Autowired
	LTAEmployeeDependentDao ltaEmployeeDependentDao;
	
	@Autowired
	DependentDao dependentDao;
	
	@Override
	public Boolean insert(LTAEmployee ltaEmployee) {
		return lTAEmployeeDao.insert(ltaEmployee);
	}
	
	

	@Override
	public List<LTAEmployee> listAll(String ltaEmployeeId, String block) {
		return lTAEmployeeDao.get(ltaEmployeeId, block);
	}

	@Override
	public LTAEmployee getById(Integer ltaEmployeeId) {
		return lTAEmployeeDao.getById(ltaEmployeeId);
	}

	@Override
	public Boolean update(LTAEmployee ltaEmployee) {
		return lTAEmployeeDao.update(ltaEmployee);
	}

	@Override
	public List<LTAEmployeeDependent> get(Integer ltaEmployeeId) {
		return ltaEmployeeDependentDao.listAll(ltaEmployeeId);
	}

	@Override
	public List<LTAEmployee> get(Integer employeeId, String block) {
		return lTAEmployeeDao.listAll(employeeId, block);
	}
	
	public List<LTAEmployee> gett(Integer employeeId, String block) {
		return lTAEmployeeDao.listAlll(employeeId, block);
	}

	@Override
	public Dependent getdepenedent(Integer dependentId) {
		return dependentDao.get(dependentId);
	}
	
	@Override
	public Boolean removeLta(Integer ltaEmployeeId){
		return lTAEmployeeDao.delete(ltaEmployeeId); 
	}

	@Override
	public LTAEmployee getByEId(Integer employeeId) {
		return lTAEmployeeDao.getByEId(employeeId); 
	}



	@Override
	public LTAEmployee getSaved(Integer employeeId, String ltaYear) {
		
		return lTAEmployeeDao.getSavedREquest(employeeId,ltaYear);
	}



	@Override
	public LTAEmployeeDependent getLTADependence(Integer deptId,Integer position,LTAEmployee ltaEmployee) {
		
		return lTAEmployeeDao.getLTADependence(deptId, position,ltaEmployee);
	}



	@Override
	public Boolean deleteAllDependents(Integer employeeId) {
		
		return lTAEmployeeDao.deleteAllDependents(employeeId);
	}



	@Override
	public List<LTAEmployeeDependent> getLTADependences(Integer ltaEmployeeId) {
		
		return lTAEmployeeDao.getLTADependences(ltaEmployeeId);
	}



	@Override
	public Employee getHrApprovedBy(String hrApprovedBy) {
		
		return lTAEmployeeDao.getHrApprovedBy(hrApprovedBy);
	}



	
	/*@Override
	public Boolean save(LTAEmployee ltaEmployee) {
		return lTAEmployeeDao.save(ltaEmployee);
	}*/

}
