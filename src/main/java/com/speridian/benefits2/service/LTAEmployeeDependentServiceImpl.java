package com.speridian.benefits2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.model.dao.BenefitsGenericDao;
import com.speridian.benefits2.model.dao.LTAEmployeeDependentDao;
import com.speridian.benefits2.model.pojo.LTAEmployeeDependent;

public class LTAEmployeeDependentServiceImpl extends BenefitsGenericDao implements LTAEmployeeDependentService{

	@Autowired
	LTAEmployeeDependentDao ltaEmployeeDependentDao;
	
	@Override
	public List<LTAEmployeeDependent> listAll() {
		return ltaEmployeeDependentDao.listAll();
	}

	@Override
	public LTAEmployeeDependent get(Integer ltaDependentId) {
		return ltaEmployeeDependentDao.get(ltaDependentId);
	}

	@Override
	public boolean insert(LTAEmployeeDependent ltaEmployeeDependent) {
		return ltaEmployeeDependentDao.insert(ltaEmployeeDependent);
	}

	@Override
	public boolean update(LTAEmployeeDependent ltaEmployeeDependent) {
		return ltaEmployeeDependentDao.update(ltaEmployeeDependent);
	}

	@Override
	public boolean remove(Integer ltaDependentId) {
		return ltaEmployeeDependentDao.delete(ltaDependentId);
	}

	
}
