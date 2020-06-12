package com.speridian.benefits2.service;

import java.util.List;

import com.speridian.benefits2.model.pojo.BenefitsProperty;

/**
 * 
 * <pre>
 * Service interface for Settings module
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 23-Feb-2017
 *
 */
public interface SettingsService {
	
	public List<BenefitsProperty> listAllProperties();

	public Boolean editProperty(BenefitsProperty property);

	public BenefitsProperty getPropertyByCode(String propertyCode);
	
	public List<BenefitsProperty> listAllGroups(String propertyGroup);
}
