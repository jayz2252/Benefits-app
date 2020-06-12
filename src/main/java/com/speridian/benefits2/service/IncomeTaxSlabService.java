package com.speridian.benefits2.service;

import java.util.List;

import com.speridian.benefits2.model.pojo.IncomeTaxSlab;
/**
 * 
 */
public interface IncomeTaxSlabService 
{
	public IncomeTaxSlab get(Integer incomeTaxSlabId);
	public List<IncomeTaxSlab> listAll();
	public List<IncomeTaxSlab> listAll(String fiscalYear);
	
	public boolean insert(IncomeTaxSlab incomeTaxSlab);
	public boolean update(IncomeTaxSlab incomeTaxSlab);
	boolean remove(Integer incomeTaxSlabId);
}
