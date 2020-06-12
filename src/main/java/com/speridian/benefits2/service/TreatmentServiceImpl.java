package com.speridian.benefits2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.model.dao.HospitalDao;
import com.speridian.benefits2.model.dao.TreatmentDao;
import com.speridian.benefits2.model.pojo.Treatment;

/**
 * 
 * @author swathylekshmi.s
 *
 */
public class TreatmentServiceImpl implements TreatmentService {

	@Autowired
	TreatmentDao treatmentDao;
	@Override
	public Treatment get(Integer treatmentId) {
		// TODO Auto-generated method stub
		return treatmentDao.get(treatmentId);
	}

	@Override
	public List<Treatment> listAll() {
		// TODO Auto-generated method stub
		return treatmentDao.listAll() ;
	}

	@Override
	public boolean insert(Treatment treatment) {
		
		return treatmentDao.insert(treatment);
	}

	@Override
	public boolean update(Treatment treatment) {
		
		return treatmentDao.update(treatment);
	}

	@Override
	public boolean remove(Integer treatmentId) {
		return treatmentDao.delete(treatmentId);
	}

	
		
	

}
