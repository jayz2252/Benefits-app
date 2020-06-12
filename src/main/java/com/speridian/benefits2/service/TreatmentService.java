package com.speridian.benefits2.service;

import java.util.List;

import com.speridian.benefits2.model.pojo.Treatment;

/**
 * 
 * @author swathylekshmi.s
 *
 */
public interface TreatmentService {

	
	public Treatment get(Integer treatmentId);
	public List<Treatment> listAll();
	
	
	public boolean insert(Treatment treatment);
	public boolean update(Treatment treatment);
	public boolean remove(Integer treatmentId);
}
