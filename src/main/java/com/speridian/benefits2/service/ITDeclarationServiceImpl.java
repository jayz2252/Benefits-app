package com.speridian.benefits2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speridian.benefits2.model.dao.EmpInvestmentDetailDao;
import com.speridian.benefits2.model.dao.EmpRentDetailDao;
import com.speridian.benefits2.model.dao.ITDeclarationDao;
import com.speridian.benefits2.model.dao.ITDocmanHistoryDao;
import com.speridian.benefits2.model.dao.ITReportDao;
import com.speridian.benefits2.model.dao.ITEmployeeDao;
import com.speridian.benefits2.model.pojo.ITEmployeeInvestment;
import com.speridian.benefits2.model.pojo.EmpRentDetail;
import com.speridian.benefits2.model.pojo.ITCategory;
import com.speridian.benefits2.model.pojo.ITInvestmentField;
import com.speridian.benefits2.model.pojo.ITDocmanHistory;
import com.speridian.benefits2.model.pojo.ITEmployeeHouseLoan;
import com.speridian.benefits2.model.pojo.ITEmployee;
import com.speridian.benefits2.model.pojo.ItHouseLoanField;

/**
 * 
 * <pre>
 * Service class for Benefit Plan related processes
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 05-Feb-2017
 *
 */
@Service
public class ITDeclarationServiceImpl implements ITDeclarationService {

	@Autowired
	ITDeclarationDao itDeclarationDao;
	
	@Autowired
	ITEmployeeDao yearlyEmployeeDeclarationDao;
	
	@Autowired
	ITReportDao itReportDao;
	
	@Autowired
	EmpInvestmentDetailDao investmentDetailDao;
	
	@Autowired
	EmpRentDetailDao empRentDetailDao;
	

	


	@Override
	public List<ITCategory> listAllCategories() {
		// TODO Auto-generated method stub
		return itDeclarationDao.listAllCategories();
	}


	@Override
	public List<ITInvestmentField> listAllFeildsInCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		return itDeclarationDao.listAllFeildsInCategory(categoryId);
	}

	/*@Override
	public boolean insertITDeclarationSection(
			ITDeclarationSection itDeclarationSection) {
		
		return itDeclarationDao.insertSection(itDeclarationSection);
	}*/


	@Override
	public boolean insertITDeclarationInvestments(
			ITInvestmentField itDeclarationInvestments) {
		// TODO Auto-generated method stub
		return itDeclarationDao.insertInvestment(itDeclarationInvestments);
	}


	@Override
	public boolean updateITDeclarationInvestments(
			ITInvestmentField itDeclarationInvestments) {
		// TODO Auto-generated method stub
		return itDeclarationDao.updateInvestment(itDeclarationInvestments);
	}


	@Override
	public boolean deleteITDeclarationInvestments(
			Integer investmentId) {
		// TODO Auto-generated method stub
		return itDeclarationDao.deleteInvestment(investmentId);
	}


	@Override
	public ITInvestmentField findInvestmentById(Integer investmentId) {
		// TODO Auto-generated method stub
		return itDeclarationDao.findInvestmentsByInvestmentId(investmentId);
	}


	@Override
	public ITCategory findCategoryByName(char categoryName) {
		// TODO Auto-generated method stub
		return itDeclarationDao.findCategoryByName(categoryName);
	}


	@Override
	public ITInvestmentField findInvestmentsByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		return itDeclarationDao.findInvestmentsByCategory(categoryId);
	}


	@Override
	public List<ItHouseLoanField> listAllFieldsInCategoryD(Integer categoryId) {
		// TODO Auto-generated method stub
		return itDeclarationDao.listAllFieldsInCategoryD(categoryId);
	}
	
	@Override
	public boolean insertHousingLoanCategoryD(ItHouseLoanField itHouseLoanField) {
		// TODO Auto-generated method stub
		return itDeclarationDao.insertHousingLoan(itHouseLoanField);
	}


	@Override
	public boolean deleteITHousingLoan(Integer houseFieldId) {
		// TODO Auto-generated method stub
		return itDeclarationDao.deleteITHousingLoan(houseFieldId);
	}


	@Override
	public boolean updateHousingLoan(ItHouseLoanField itHouseLoanField) {
		// TODO Auto-generated method stub
		return itDeclarationDao.updateHousingLoan(itHouseLoanField);
	}


	@Override
	public ItHouseLoanField findITHousingLoanId(Integer houseFieldId) {
		return itDeclarationDao.findITHousingLoanId(houseFieldId);
	}
	
	@Override
	public List<ITEmployee> listAll() {
		return itDeclarationDao.listAll();
	}


	@Override
	public List<ITInvestmentField> listAllInvestments(Integer categoryId) {
		return itDeclarationDao.listAllInvestments(categoryId);
	}


	@Override
	public List<ItHouseLoanField> listAllFields(Integer categoryId) {
		return itDeclarationDao.listAllFields(categoryId);
	}


	@Override
	public ITEmployee getById(Integer declarationId) {
		return yearlyEmployeeDeclarationDao.get(declarationId);
	}


	@Override
	public List<ITInvestmentField> list(Integer yearlyDeclarationId) {
		return itReportDao.listAll(yearlyDeclarationId);
	}


	


	@Override
	public List<EmpRentDetail> listRentDeatils(Integer yearlyDeclarationId) {
		return itReportDao.listAllRentDetails(yearlyDeclarationId);
	}


	@Override
	public List<ITEmployeeHouseLoan> listHouseLoans(
			Integer yearlyDeclarationId) {
		return itReportDao.listAllLoans(yearlyDeclarationId);
	}
	
/*	@Override
	public boolean insertITyearlyEmployeeDeclaration(
			ITEmployee yearlyEmployeeDeclaration) {
		
		return yearlyEmployeeDeclarationDao.insert(yearlyEmployeeDeclaration);
	}


	@Override
	public List<ITEmployee> getYearlyEmpDeclarationByIdNFiscalYear(
			Integer employeeId,String fiscalYear) {
		
		return yearlyEmployeeDeclarationDao.listAll(employeeId, fiscalYear);
	}*/


	@Override
	public List<ITEmployeeInvestment> findInvestmentDetailByEmpId(Integer empId) {
		// TODO Auto-generated method stub
		return investmentDetailDao.getInvestmentDetailByEmpId(empId);
	}


	@Override
	public List<EmpRentDetail> findRentDetailByEmpId(Integer empId) {
		return empRentDetailDao.getRentDetailByEmpId(empId);
	}

	@Override
	public List<ITEmployeeHouseLoan> getHouseLoans(Integer empID) {
		return itDeclarationDao.getHouseLoans(empID);
	}

/*	@Override
	public List<ITDocmanHistory> listAll(Integer empId, String section,String fiscalYear) {
		return historyDao.listAll(empId, section ,fiscalYear);
	}*/


	


	@Override
	public ITEmployee listAll(String employeeCode) {
		return yearlyEmployeeDeclarationDao.listAll(employeeCode);
	
	}


	@Override
	public boolean insertITyearlyEmployeeDeclaration(ITEmployee yearlyEmployeeDeclaration) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<ITEmployee> getYearlyEmpDeclarationByIdNFiscalYear(Integer employeeId, String fiscalYear) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Boolean update(ITEmployee declaration) {
		// TODO Auto-generated method stub
		return (yearlyEmployeeDeclarationDao.update(declaration) != null ? true : false);
	}

	@Override
	public ITEmployee listAll(String employeeCode,String fiscalYear) {
		
		return yearlyEmployeeDeclarationDao.listAll(employeeCode,fiscalYear);
	}


	@Override
	public List<ITDocmanHistory> listAll(Integer empId, String section, String fiscalYear) {
		// TODO Auto-generated method stub
		return null;
	}
	
	


}
