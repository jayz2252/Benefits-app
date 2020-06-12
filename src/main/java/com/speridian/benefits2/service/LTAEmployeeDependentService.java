package com.speridian.benefits2.service;

import java.util.List;

import com.speridian.benefits2.model.pojo.IncomeTaxSlab;
import com.speridian.benefits2.model.pojo.LTAEmployeeDependent;

public interface LTAEmployeeDependentService {

		
	public List<LTAEmployeeDependent> listAll();
	public LTAEmployeeDependent get(Integer ltaDependentId);
	
	public boolean insert(LTAEmployeeDependent ltaEmployeeDependent);
	public boolean update(LTAEmployeeDependent ltaEmployeeDependent);
	boolean remove(Integer ltaDependentId);
}
