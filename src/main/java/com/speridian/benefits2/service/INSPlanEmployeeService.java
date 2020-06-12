package com.speridian.benefits2.service;

import java.util.List;

import com.speridian.benefits2.beans.CityWrapper;
import com.speridian.benefits2.model.pojo.Hospital;
import com.speridian.benefits2.model.pojo.INSCategoryMaster;
import com.speridian.benefits2.model.pojo.INSPlan;
import com.speridian.benefits2.model.pojo.INSPlanDepDetails;
import com.speridian.benefits2.model.pojo.INSPlanEmployee;
import com.speridian.benefits2.model.pojo.INSPlanEmployeeClaim;
import com.speridian.benefits2.model.pojo.INSPlanEmployeeDetails;
import com.speridian.benefits2.model.pojo.INSPlanLoyaltyLevelDetail;
import com.speridian.benefits2.model.pojo.INSPlanLoyaltyLevels;
import com.speridian.benefits2.model.pojo.INSPlanOffice;
import com.speridian.benefits2.model.pojo.InsPlanEmployeeClaimBill;
import com.speridian.benefits2.model.pojo.InsPlanEmployeeClaimBillDetail;
import com.speridian.benefits2.model.pojo.InsPlanEmployeeClaimPafDetail;
import com.speridian.benefits2.model.pojo.Treatment;

/**
 * 
 * <pre>
 * Service Interface for Insurance Plan Employee module
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 16-May-2017
 *
 */
public interface INSPlanEmployeeService {

	public INSPlanEmployee getPlanEmployee(Integer empId, String fiscalYear);
	public List<INSPlan> listAllActive();
	public INSPlanEmployee gets(Integer empId);
	public INSPlan getPlan(Integer planId);
	public INSPlanLoyaltyLevels getLoyaltyLevel(Integer planId, Integer experience);
	public INSPlanDepDetails getPlanDetail (Integer planId, String relationship);
	public INSPlanLoyaltyLevels getLoyaltyLevel(Integer loyaltyId);
	public Boolean savePlanEmployee(INSPlanEmployee planEmployee);
	public INSPlanEmployee getPlanEmployee(Integer planEmployeeId);
	public List<Treatment> listAllTreatments ();
	public List<String> listAllStates ();
	public List<CityWrapper> listAllCities ();
	public List<Hospital> listAllHospitals();
	public Integer getNextClaimId();
	public Hospital getHospital(Integer hospitalId);
	public Treatment getTreatment (Integer treatmentId);
	public Boolean savePafClaim(INSPlanEmployeeClaim claim, InsPlanEmployeeClaimPafDetail paf);
	public List<INSPlanEmployeeClaim> listClaimByEmployee (Integer employeeId, String status, String fiscalYear, String claimType);
	public InsPlanEmployeeClaimPafDetail getPafDetail(Integer claimId);

	public INSPlanEmployeeClaim getClaim(Integer claimId);

	public List<INSPlanEmployee> listAllEnrolledEmployees();
	public boolean updatePlanEmployee(INSPlanEmployee planEmployee);
	public Boolean updateInsClaim (INSPlanEmployeeClaim claim, InsPlanEmployeeClaimPafDetail pafDetail);
	public List<INSPlanEmployeeClaim> listAllClaims (Integer planEmployeeId);
	
	public Boolean update(InsPlanEmployeeClaimPafDetail claimPafDetail);
	public Boolean update(INSPlanEmployeeDetails planEmployeeDetails);
	public Boolean remove(INSPlanEmployeeDetails planEmployeeDetails);
	public Boolean update(INSPlanEmployeeClaim claim);

	
	public InsPlanEmployeeClaimPafDetail getClaimPafDetail(Integer claimPafDetailId);
	
	public List<INSPlanEmployeeDetails> listAllDetails();
	
	public List<INSPlanDepDetails> listAll(Integer planId);
	
	public List<INSPlanEmployee> listAllEmp(Integer planId);
	public List<INSPlanEmployee> listAllEmp(Integer planId,String fiscalYear);
	public List<INSPlanEmployeeDetails> listAllPlanEmp(Integer planEmpId);
	

	
	public List<InsPlanEmployeeClaimBillDetail> listAllDetailsByClaimId(Integer claimBillId);
	public List<InsPlanEmployeeClaimBill> listAllBillsByClaimId(Integer claimId);
	
	public Integer insert(InsPlanEmployeeClaimPafDetail detail);
	public List<INSCategoryMaster> listAllCategories();
	public InsPlanEmployeeClaimBill getBillById(Integer claimBillId);
	public INSCategoryMaster getCategoryById(Integer categoryId);
	public Boolean updateBillDetail(InsPlanEmployeeClaimBillDetail billDetail);
	public Boolean updateBill(InsPlanEmployeeClaimBill bill);
	public Boolean insertBillDetail(InsPlanEmployeeClaimBillDetail billDetail);
	public Integer insertBill(InsPlanEmployeeClaimBill bill);
	public Integer insertClaim(INSPlanEmployeeClaim claim);

	public InsPlanEmployeeClaimBillDetail getBillDetailById(Integer billDetailId);

	
	public InsPlanEmployeeClaimBillDetail getById(Integer billId);
	
	public List<INSPlan> listEligiblePlans(String office);
	public INSPlanLoyaltyLevelDetail getLoyaltyDetail (Integer loyaltyId, String depRelationship);
	
	public List<INSPlanEmployee> listMonthlyEmpList(String month, Integer insPlanId, String year);
	public List<INSPlanEmployee> listPlanEmployees(String fiscalYear, String status);
	public INSPlanEmployeeDetails getPlanEmployeeDetail (Integer planEmployeeId, Integer dependentId);
	public boolean updatePlanEmployee(INSPlanEmployee planEmployee, Boolean updateDetails) ;
	public INSPlanEmployeeClaim getEmployeePAFDetails(Integer planEmployeeId);
	public Integer getClaimAmount(Integer planEmployeeId);
	public void deleteBills(List<InsPlanEmployeeClaimBillDetail> billDetail);
	public List<String> getCities(String state);
	public List<Hospital> listAllHospitalsBYCity(String city);
}
