package com.speridian.benefits2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.model.dao.ITReportDao;
import com.speridian.benefits2.model.pojo.EmpRentDetail;
import com.speridian.benefits2.model.pojo.ITInvestmentField;
import com.speridian.benefits2.model.pojo.ITEmployee;
import com.speridian.benefits2.model.pojo.ITEmployeeHouseLoan;
import com.speridian.benefits2.model.pojo.ITEmployeeInvestment;
import com.speridian.benefits2.model.pojo.ItHouseLoanField;

public class ITReportServiceImpl implements ITReportService {

	@Autowired
	ITReportDao itReporstDao;

	public List<ITEmployeeHouseLoan> listAllHouseLoanDataValues(Integer hlfieldId, Integer itEmployeeId) {

		return itReporstDao.listAllHouseLoanDataValues(hlfieldId, itEmployeeId);
	}

	@Override
	public List<ITEmployeeInvestment> listAllInvestmentDataValues(Integer investId, Integer itEmployeeId) {

		return itReporstDao.listAllInvestmentDataValues(investId, itEmployeeId);
	}

	@Override
	public List<ITEmployee> listAllYearlyEmployeeDetails(String fiscalYear, String itMode) {

		return itReporstDao.listAllYearlyEmployeeDetails(fiscalYear, itMode);
	}

	@Override
	public List<ItHouseLoanField> listAllHouseLoanFieldsData() {

		return itReporstDao.listAllHouseLoanFieldsData();
	}

	@Override
	public List<ITInvestmentField> listAllInvestmentFieldsData() {

		return itReporstDao.listAllInvestmentFieldsData();
	}

	@Override
	public List<EmpRentDetail> listAllRentDetailsByEmpIdAndFY(Integer itEmployeeId, String fiscalYr) {
		return itReporstDao.listAllRentDetailsByEmpIdAndFY(itEmployeeId, fiscalYr);
	}

}
