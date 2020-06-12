package com.speridian.benefits2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speridian.benefits2.model.dao.TaxRegimeDao;
import com.speridian.benefits2.model.dao.TaxRegimeDaoImpl;
import com.speridian.benefits2.model.pojo.TaxRegime;

@Service
public class TaxRegimeServiceImpl implements TaxRegimeService {

	TaxRegimeDaoImpl taxRegimeDao= new TaxRegimeDaoImpl();

	@Override
	public List<TaxRegime> getTaxRegime(Integer employeeCode, String fiscalYear) {
		
		return taxRegimeDao.getTaxRegimeOFEmployee(employeeCode, fiscalYear);
	}

	@Override
	public boolean saveTaxRegime(TaxRegime taxRegime) {
		return taxRegimeDao.saveTaxRegime(taxRegime);
	}
	

}
