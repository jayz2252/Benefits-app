package com.speridian.benefits2.service;

import java.util.List;

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
 * Service interface for Benefit Plan related processes
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 05-Feb-2017
 *
 */
public interface ITDeclarationService {

	public List<ITCategory> listAllCategories();

	public List<ITInvestmentField> listAllFeildsInCategory(Integer categoryId);

	public boolean insertITDeclarationInvestments(ITInvestmentField itDeclarationInvestments);

	public ITInvestmentField findInvestmentById(Integer investmentId);

	public boolean updateITDeclarationInvestments(ITInvestmentField itDeclarationInvestments);

	public boolean deleteITDeclarationInvestments(Integer investmentId);

	public ITCategory findCategoryByName(char categoryName);

	public ITInvestmentField findInvestmentsByCategory(Integer categoryId);

	public List<ItHouseLoanField> listAllFieldsInCategoryD(Integer categoryId);

	public boolean insertHousingLoanCategoryD(ItHouseLoanField itHouseLoanField);

	public boolean deleteITHousingLoan(Integer houseFieldId);

	public boolean updateHousingLoan(ItHouseLoanField itHouseLoanField);

	public ItHouseLoanField findITHousingLoanId(Integer houseFieldId);

	public List<ITEmployee> listAll();

	public List<ITInvestmentField> listAllInvestments(Integer categoryId);

	public List<ItHouseLoanField> listAllFields(Integer categoryId);

	public ITEmployee getById(Integer declarationId);

	public List<ITInvestmentField> list(Integer yearlyDeclarationId);

	public List<EmpRentDetail> listRentDeatils(Integer yearlyDeclarationId);

	public List<ITEmployeeHouseLoan> listHouseLoans(Integer yearlyDeclarationId);

	public boolean insertITyearlyEmployeeDeclaration(ITEmployee yearlyEmployeeDeclaration);

	public List<ITEmployee> getYearlyEmpDeclarationByIdNFiscalYear(Integer employeeId, String fiscalYear);

	public List<ITEmployeeInvestment> findInvestmentDetailByEmpId(Integer empId);

	public List<EmpRentDetail> findRentDetailByEmpId(Integer empId);

	public List<ITEmployeeHouseLoan> getHouseLoans(Integer empID);

	public List<ITDocmanHistory> listAll(Integer empId, String section, String fiscalYear);

	public Boolean update(ITEmployee declaration);

	public ITEmployee listAll(String employeeCode);

	public ITEmployee listAll(String employeeCode, String fiscalYear);

}
