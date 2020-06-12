package com.speridian.benefits2.service;

import java.util.List;

import com.speridian.benefits2.model.pojo.EmpRentDetail;
import com.speridian.benefits2.model.pojo.ITInvestmentField;
import com.speridian.benefits2.model.pojo.ITEmployee;
import com.speridian.benefits2.model.pojo.ITEmployeeHouseLoan;
import com.speridian.benefits2.model.pojo.ITEmployeeInvestment;
import com.speridian.benefits2.model.pojo.ItHouseLoanField;

public interface ITReportService {

	public List<ITEmployeeHouseLoan> listAllHouseLoanDataValues(Integer hlfieldId,Integer itEmployeeId);
	public List<ITEmployeeInvestment> listAllInvestmentDataValues(Integer investId,Integer empId);
	public List<ItHouseLoanField> listAllHouseLoanFieldsData();
	public List<ITInvestmentField> listAllInvestmentFieldsData();
	public List<EmpRentDetail> listAllRentDetailsByEmpIdAndFY(Integer itEmployeeId,String fiscalYr);
	public List<ITEmployee> listAllYearlyEmployeeDetails(String fiscalYear, String itModeDeclaration);
}
