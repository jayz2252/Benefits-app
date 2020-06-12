package com.speridian.benefits2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.model.dao.BenefitsPropertyDao;
import com.speridian.benefits2.model.pojo.BenefitsProperty;

/**
 * 
 * <pre>
 * Service implementation for Settings module
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 23-Feb-2017
 *
 */
public class SettingsServiceImpl implements SettingsService {

	@Autowired
	BenefitsPropertyDao benefitsPropertyDao;
	
	@Override
	public List<BenefitsProperty> listAllProperties() {
		return benefitsPropertyDao.listAll(true);	
	}
	
	@Override
	public Boolean editProperty(BenefitsProperty property){
		return benefitsPropertyDao.update(property);
	}

	@Override
	public BenefitsProperty getPropertyByCode(String propertyCode) {
		// TODO Auto-generated method stub
		return benefitsPropertyDao.get(propertyCode);
	}

	@Override
	public List<BenefitsProperty> listAllGroups(String propertyGroup) {
		return benefitsPropertyDao.listAllGroups(propertyGroup);
	}
	
	

}
