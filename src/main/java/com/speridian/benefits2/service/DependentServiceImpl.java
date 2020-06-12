package com.speridian.benefits2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.model.dao.DependentDao;
import com.speridian.benefits2.model.pojo.Dependent;

/**
 * 
 * <pre>
 * Service class for Dependent related processes
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 05-Feb-2017
 *
 */
public class DependentServiceImpl implements DependentService {

	@Autowired
	DependentDao dependentDao;
	
	@Override
	public List<Dependent> listAll() {
		return dependentDao.listAll();
	}

}
