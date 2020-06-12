package com.speridian.benefits2.service;

import java.util.List;

import com.speridian.benefits2.model.pojo.TaxRegime;
/**
 * 
 * @author rajan.jha
 *
 */

public interface TaxRegimeService {
	public List<TaxRegime> getTaxRegime(Integer employeeCode, String fiscalYear);
	
	public boolean saveTaxRegime(TaxRegime taxRegime);

}
