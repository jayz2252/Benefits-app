package com.speridian.benefits2.service;

import java.util.List;

import com.speridian.benefits2.beans.LTAEmployeeVO;
import com.speridian.benefits2.model.pojo.Dependent;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.LTAEmployee;
import com.speridian.benefits2.model.pojo.LTAEmployeeDependent;
import com.speridian.benefits2.model.pojo.PFEmployee;

public interface LTAService {
	
	public Boolean insert(LTAEmployee ltaEmployee);
	public LTAEmployee getById(Integer ltaEmployeeId);
	public List<LTAEmployee> listAll(String ltaEmployeeId,String block);
	public Boolean update(LTAEmployee ltaEmployee);
	public List<LTAEmployeeDependent> get(Integer ltaEmployeeId);
	public List<LTAEmployee> get(Integer employeeId,String block);
	public List<LTAEmployee> gett(Integer employeeId,String block);
	public Dependent getdepenedent(Integer DependentId);
	public Boolean removeLta(Integer ltaEmployeeId);
	public LTAEmployee getByEId(Integer employeeId);
	/*public Boolean save(LTAEmployee ltaEmployee);*/
	public LTAEmployee getSaved(Integer employeeId, String ltaYear);
	public LTAEmployeeDependent getLTADependence(Integer deptId,Integer postion,LTAEmployee ltaEmployee);
	//public List<LTAEmployeeDependent> getLTADependenceList(Integer ltaEmployeeId);
	public Boolean deleteAllDependents(Integer employeeId);
	public List<LTAEmployeeDependent> getLTADependences(Integer ltaEmployeeId);
	public Employee getHrApprovedBy(String hrApprovedBy);
}
