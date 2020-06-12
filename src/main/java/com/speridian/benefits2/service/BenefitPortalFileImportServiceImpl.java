package com.speridian.benefits2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.model.dao.BenefitPortalFileImportDao;
import com.speridian.benefits2.model.pojo.BenefitPortalFileImport;

public class BenefitPortalFileImportServiceImpl implements BenefitPortalFileImportService {

	@Autowired
	BenefitPortalFileImportDao benefitPortalFileImportDao;
	
	@Override
	public boolean insert(BenefitPortalFileImport benefitPortalFileImport){
		return benefitPortalFileImportDao.insert(benefitPortalFileImport);
	}
	

}
