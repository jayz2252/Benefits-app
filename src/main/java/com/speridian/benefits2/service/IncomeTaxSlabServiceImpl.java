package com.speridian.benefits2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.model.dao.IncomeTaxSlabDao;
import com.speridian.benefits2.model.pojo.IncomeTaxSlab;

public class IncomeTaxSlabServiceImpl implements IncomeTaxSlabService{
	
	@Autowired
	IncomeTaxSlabDao incomeTaxSlabDao;

	@Override
	public IncomeTaxSlab get(Integer incomeTaxSlabId) {
		return incomeTaxSlabDao.get(incomeTaxSlabId);
	}

	@Override
	public List<IncomeTaxSlab> listAll() {
		return incomeTaxSlabDao.listAll();
	}

	@Override
	public List<IncomeTaxSlab> listAll(String fiscalYear) {
		return incomeTaxSlabDao.listAll(fiscalYear);
	}

	@Override
	public boolean insert(IncomeTaxSlab incomeTaxSlab) {
		return incomeTaxSlabDao.insert(incomeTaxSlab);
	}

	@Override
	public boolean update(IncomeTaxSlab incomeTaxSlab) {
		return incomeTaxSlabDao.update(incomeTaxSlab);
	}
	
	@Override
	public boolean remove(Integer incomeTaxSlabId)
	{
		return incomeTaxSlabDao.delete(incomeTaxSlabId);
	}
}
