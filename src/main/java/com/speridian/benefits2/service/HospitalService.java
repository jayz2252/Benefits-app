package com.speridian.benefits2.service;

import java.util.List;

import com.speridian.benefits2.model.pojo.Hospital;
/**
 * 
 * @author swathylekshmi.s
 *
 */

public interface HospitalService {

	public Hospital get(Integer hospitalId);
	public List<Hospital> listAll();
	
	
	public boolean insert(Hospital hospital);
	public boolean update(Hospital hospital);
	public boolean delete(Integer hospitalId);
}
