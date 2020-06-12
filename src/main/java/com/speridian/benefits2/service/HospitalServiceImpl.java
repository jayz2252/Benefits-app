package com.speridian.benefits2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.model.dao.HospitalDao;
import com.speridian.benefits2.model.pojo.Hospital;

/**
 * 
 * @author swathylekshmi.s
 *
 */
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	HospitalDao hospitalDao;
	
	@Override
	public Hospital get(Integer hospitalId) {
		
		return hospitalDao.get(hospitalId);
	}
	@Override
	public List<Hospital> listAll() {
		
		return hospitalDao.listAll();
	}

	@Override
	public boolean insert(Hospital hospital) {
		
		return hospitalDao.insert(hospital);
	}

	@Override
	public boolean update(Hospital hospital) {
		return hospitalDao.update(hospital);
	}
	@Override
	public boolean delete(Integer hospitalId) {
		
		return hospitalDao.delete(hospitalId);
	}

}
