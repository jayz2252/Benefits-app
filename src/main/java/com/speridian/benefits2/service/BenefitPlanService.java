package com.speridian.benefits2.service;

import java.util.Date;
import java.util.List;

import com.speridian.benefits2.model.pojo.BenefitPlan;
import com.speridian.benefits2.model.pojo.BenefitPlanBand;
import com.speridian.benefits2.model.pojo.BenefitPlanCategory;
import com.speridian.benefits2.model.pojo.BenefitPlanCategoryMisc;
import com.speridian.benefits2.model.pojo.BenefitPlanClaim;
import com.speridian.benefits2.model.pojo.BenefitPlanClaimDetail;
import com.speridian.benefits2.model.pojo.BenefitPlanDependency;
import com.speridian.benefits2.model.pojo.BenefitPlanDirectClaim;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployee;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployeeClaimPeriod;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployeeDetail;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployeeDoc;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployeeField;
import com.speridian.benefits2.model.pojo.BenefitPlanField;
import com.speridian.benefits2.model.pojo.EmpBenPlansYrlyOpts;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.PlanDenyReasonsMaster;
import com.speridian.benefits2.model.pojo.PlanEmployeeDenies;
import com.speridian.benefits2.model.pojo.UserRole;

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
public interface BenefitPlanService {
	
	public static final String STEP_ONE_MAIN = "main";
	public static final String STEP_TWO_AMOUNT = "amount";
	public static final String STEP_THREE_CONTACTS = "contacts";
	public static final String STEP_FOUR_REQUIRED_DOCS = "documents";
	public static final String STEP_FIVE_SUMMARY = "summary";
	public static final String STEP_SIX_SAVED = "saved";
	
	public List<BenefitPlan> listAllActive(String currentFiscalYear);
	public List<BenefitPlan> listAll();
	public List<BenefitPlan> listAllForAdmin();


	public List<Integer> listDependencies(Integer planId);
	public List<BenefitPlanEmployee> getByIds(String fiscalYear,Integer empId); 
	public List<BenefitPlanClaimDetail> getBy(Integer claimId);
	public List<BenefitPlanField> listAllFields(Integer planId);
	public List<BenefitPlanEmployeeField> listAllField(Integer planEmployeeFieldId);
		
	
	public List<String> listAllBands ();
	public List<String> listAllBandsForPlan(Integer planId);
	
	public BenefitPlan get(Integer benefitPlanId);
	public BenefitPlanEmployee getById(Integer benefitPlanEmployeeId);
	public BenefitPlanEmployeeDoc getDoc(Integer benefitPlanEmpDocId);
	public List<BenefitPlanEmployeeDoc> getDocs(Integer planEmployeeId);
	
	public Boolean add(BenefitPlan benefitPlan);
	public Boolean edit(BenefitPlan benefitPlan);
	
	
	public Boolean insert(EmpBenPlansYrlyOpts empBenPlansYrlyOpts);
	public Boolean update(EmpBenPlansYrlyOpts benPlansYrlyOpts);
	public Boolean updateStatus(Integer benefitPlanId);
	public Boolean update(BenefitPlanEmployeeDoc benefitPlanEmployeeDoc);
	
	
	public List<BenefitPlanEmployee> listEmployees (Integer planId);
	public List<BenefitPlanEmployee> listEmployees (Integer planId,String fiscalYear,String status);
	
	public BenefitPlanCategory getCategory(Integer categoryId);
	public BenefitPlanCategoryMisc getMisc(Integer miscId);
	
	public Boolean addPlanEmployee(BenefitPlanEmployee planEmployeee);
	
	public BenefitPlanEmployee getPlanEmployee(Integer planEmployeeId);
	
	public List<BenefitPlanClaim> listMyClaims(Integer planEmployeeId);
	
	public BenefitPlanEmployeeDetail getPlanEmployeeDetail(Integer planEmployeeId, Integer miscId);
	
	public Integer getUniqueClaimId();
	
	public Boolean addNewClaim (BenefitPlanClaim claim);
	
	public BenefitPlanClaim getClaim (Integer claimId);
	
	public Boolean edit(BenefitPlanClaim claim);
	
	public Boolean editPlanEmployee(BenefitPlanEmployee planEmployee);
	
	public List<BenefitPlanClaim> listAllApprovals(Integer planId, String fy);

	public List<BenefitPlanClaim> listClaims(Integer planEmployeeId);
	public List<BenefitPlanClaimDetail> listOtherClaims(Integer benefitPlanClaimId);
	
	
	public BenefitPlanClaim getByClaimId(Integer claimId);

	
	public List<BenefitPlanClaim> searchClaims(String claimRefNo, String empInfo, Integer planId, String fiscalYear, String status);
	public List<BenefitPlanClaim> listAllClaimsForFy(String fy);
	List<BenefitPlanEmployee> listByYearlyOpt(Integer yearlyOptId);
	List<BenefitPlanEmployee> listByYearlyOpt(Integer yearlyOptId,
			Boolean active);
	
	public Boolean removeClaim (BenefitPlanClaim claim);
	public List<BenefitPlanDirectClaim> listDirectClaimsByPlan(Integer planEmpId);
	public void saveDirectClaimsByPlan(BenefitPlanDirectClaim benefitPlanDirectClaim);
	public Integer getUniqueDirectClaimId();
	
	public BenefitPlanBand getPlanBand (Integer planId, String band);
	public Boolean addDependency (BenefitPlanDependency dependency);
	
	public List<BenefitPlanDependency> listAllBenefitPlan();
	public List<UserRole> listAllUserRoles();
	public boolean insertUser(UserRole userRole);
	public boolean delete(UserRole userRole);
	
	public List<BenefitPlanEmployeeClaimPeriod> listAllClaims(Integer benefitPlanEmployee,Date periodFrom,Date periodTo);
	public List<BenefitPlanEmployeeClaimPeriod> listPreviousClaims(Integer planEmployeeId);
	
	public BenefitPlanEmployeeClaimPeriod getClaims(Integer claimPeriodId);
	public List<BenefitPlanEmployeeClaimPeriod> listAllClaimPeriods();
	public boolean update(BenefitPlanEmployeeClaimPeriod benefitPlanEmployeeClaimPeriods);
	
	public Boolean insert(BenefitPlanEmployeeClaimPeriod claimPeriod);
	public List<BenefitPlanEmployee> listPlanEmployees(String fiscalYear, Integer planId);
	
	
	public List<PlanDenyReasonsMaster> listAllDenyReasons();
	public Boolean insert(PlanDenyReasonsMaster planDenyReasonsMaster);
	public Boolean update(PlanDenyReasonsMaster planDenyReasonsMaster);
	
	public List<PlanEmployeeDenies> listDenies();
	public Boolean insert(PlanEmployeeDenies planEmployeeDenies);
	public Boolean update(PlanEmployeeDenies planEmployeeDenies);
	public List<PlanEmployeeDenies> getByDenyId(Integer empId);
	public List<PlanEmployeeDenies> listDenies(Integer planId);
	public void rollBackApprovedPlans(BenefitPlanEmployee benefitPlanEmployee);

	public void approveSelected(BenefitPlanEmployee benefitPlanEmployee);

	
	public BenefitPlanDirectClaim getDirectClaim (Integer directClaimId);
	public Boolean updatePlanEmployee(BenefitPlanEmployee benefitPlanEmployee);
	List<BenefitPlanEmployeeField> listAllField(Integer planEmployeeId, String type);
	public List<BenefitPlan> listAllActive(String currentFiscalYear, Employee employee);
	public PlanDenyReasonsMaster getDenyReason(int parseInt);
}
