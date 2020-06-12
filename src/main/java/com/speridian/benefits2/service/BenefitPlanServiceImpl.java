package com.speridian.benefits2.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.speridian.benefits2.beans.BenefitPlanDirectClaimBean;
import com.speridian.benefits2.model.dao.BenefitPlanBandDao;
import com.speridian.benefits2.model.dao.BenefitPlanCategoryDao;
import com.speridian.benefits2.model.dao.BenefitPlanCategoryMiscDao;
import com.speridian.benefits2.model.dao.BenefitPlanClaimDao;
import com.speridian.benefits2.model.dao.BenefitPlanDao;
import com.speridian.benefits2.model.dao.BenefitPlanDependencyDao;
import com.speridian.benefits2.model.dao.BenefitPlanDirectClaimDao;
import com.speridian.benefits2.model.dao.BenefitPlanEmployeeClaimPeriodsDao;
import com.speridian.benefits2.model.dao.BenefitPlanEmployeeDetailDao;
import com.speridian.benefits2.model.dao.BenefitPlanEmployeeDocsDao;
import com.speridian.benefits2.model.dao.BenefitPlanEmployeeFieldsDao;
import com.speridian.benefits2.model.dao.BenefitPlanFieldDao;
import com.speridian.benefits2.model.dao.BenefitPlanFieldDaoImpl;
import com.speridian.benefits2.model.dao.BenefitsPlanEmployeeDao;
import com.speridian.benefits2.model.dao.BenefitsPlanClaimDetailDao;
import com.speridian.benefits2.model.dao.EmpBenPlansYrlyOptsDao;
import com.speridian.benefits2.model.dao.EmployeeDao;
import com.speridian.benefits2.model.dao.PlanDenyReasonsMasterDao;
import com.speridian.benefits2.model.dao.PlanEmployeeDeniesDao;
import com.speridian.benefits2.model.dao.UsersRoleDao;
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
import com.speridian.benefits2.model.util.BenefitsConstants;

/**
 * 
 * <pre>
 * Service class for Benefit Plan related processes
 * </pre>
 * 
 * @author jithin.kuriakose, swathy.raghu, aswin.jose
 * @since 05-Feb-2017
 * 
 */
public class BenefitPlanServiceImpl implements BenefitPlanService {

	@Autowired
	BenefitPlanDao benefitPlanDao;

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	BenefitsPlanEmployeeDao benefitPlanEmployeeDao;

	@Autowired
	BenefitPlanEmployeeDetailDao benefitPlanEmployeeDetailDao;

	@Autowired
	EmpBenPlansYrlyOptsDao benPlansYrlyOptsDao;

	@Autowired
	BenefitPlanBandDao benefitPlanBandDao;
	
	@Autowired
	BenefitPlanFieldDao benefitPlanFieldDao;
	

	@Autowired
	BenefitPlanCategoryDao benefitPlanCategoryDao;

	@Autowired
	BenefitPlanCategoryMiscDao benefitPlanCategoryMiscDao;

	@Autowired
	BenefitPlanEmployeeDocsDao benefitPlanEmployeeDocDao;

	@Autowired
	BenefitPlanClaimDao benefitPlanClaimDao;
	
	@Autowired
	BenefitsPlanClaimDetailDao claimDetailDao;
	
	@Autowired
	BenefitPlanDependencyDao benefitPlanDependencyDao;
	
	@Autowired
	BenefitPlanDirectClaimDao benefitPlanDirectClaimDao;
	
	@Autowired
	BenefitPlanEmployeeFieldsDao benefitPlanEmployeeFieldsDao;
	
	
	@Autowired
	UsersRoleDao usersRoleDao;
	

	@Autowired
	BenefitPlanEmployeeClaimPeriodsDao benefitPlanEmployeeClaimPeriodsDao;

	@Autowired
	PlanDenyReasonsMasterDao denyReasonsMasterDao;
	
	@Autowired
	PlanEmployeeDeniesDao deniesDao;
	
	@Override
	public List<BenefitPlan> listAllActive(String currentFiscalYear) {
		return benefitPlanDao
				.listCurrentActive(Boolean.TRUE, currentFiscalYear);
	}
	
	@Override
	public List<BenefitPlan> listAllActive(String currentFiscalYear, Employee employee) {
		List<BenefitPlan> plans = benefitPlanDao
				.listCurrentActive(Boolean.TRUE, currentFiscalYear);
		List<BenefitPlan> applicablePlans = null;
		
		if (plans != null && !plans.isEmpty()) {
			applicablePlans = new ArrayList<BenefitPlan>();
			for (BenefitPlan plan : plans) {
				boolean bandFound = false;
				if (plan.getPlanBands() != null && !plan.getPlanBands().isEmpty()) {
					for (BenefitPlanBand band : plan.getPlanBands()) {
						if (band.getBand().equals(employee.getBand())) {
							bandFound = true;
							break;
						}
					}
				}else {
					bandFound = true;
				}
				if (bandFound) {
					applicablePlans.add(plan);
				}
			}
		}
		
		return applicablePlans;
	}

	@Override
	public List<BenefitPlan> listAll() {
		return benefitPlanDao.listAll(true);
	}

	public List<BenefitPlan> listAllForAdmin() {
		return benefitPlanDao.listAllDeleted(false);
	}

	@Override
	public BenefitPlan get(Integer benefitPlanId) {
		return benefitPlanDao.get(benefitPlanId);
	}

	@Override
	public Boolean add(BenefitPlan benefitPlan) {
		return benefitPlanDao.insert(benefitPlan);
	}

	@Override
	public Boolean edit(BenefitPlan benefitPlan) {
		return benefitPlanDao.update(benefitPlan);
	}

	@Override
	public List<String> listAllBands() {
		return employeeDao.listAllBands();
	}

	@Override
	public Boolean insert(EmpBenPlansYrlyOpts empBenPlansYrlyOpts) {
		benPlansYrlyOptsDao.insert(empBenPlansYrlyOpts);
		return null;
	}

	@Override
	public Boolean update(EmpBenPlansYrlyOpts benPlansYrlyOpts) {
		benPlansYrlyOptsDao.update(benPlansYrlyOpts);
		return null;
	}

	@Override
	public List<String> listAllBandsForPlan(Integer planId) {
		return benefitPlanBandDao.listAllBands(planId);
	}
	
	@Override
	public List<BenefitPlanEmployee> listEmployees(Integer planId) {
		return benefitPlanEmployeeDao.listByPlan(planId);
	}

	@Override
	public List<BenefitPlanEmployee> listEmployees(Integer planId,
			String fiscalYear, String status) {
		return benefitPlanEmployeeDao.listByPlan(planId, fiscalYear, status);
	}

	@Override
	public Boolean updateStatus(Integer benefitPlanEmployeeId) {
		BenefitPlanEmployee benefitPlanEmployee = benefitPlanEmployeeDao
				.get(benefitPlanEmployeeId);
		benefitPlanEmployee.setStatus(BenefitsConstants.EMP_PLAN_OPT_APPROVED);

		return benefitPlanEmployeeDao.update(benefitPlanEmployee);
	}

	@Override
	public BenefitPlanEmployee getById(Integer benefitPlanEmployeeId) {

		return benefitPlanEmployeeDao.get(benefitPlanEmployeeId);
	}

	@Override
	public BenefitPlanCategory getCategory(Integer categoryId) {
		return benefitPlanCategoryDao.get(categoryId);
	}

	@Override
	public BenefitPlanCategoryMisc getMisc(Integer miscId) {
		return benefitPlanCategoryMiscDao.get(miscId);
	}

	@Override
	public Boolean addPlanEmployee(BenefitPlanEmployee planEmployeee) {
		return benefitPlanEmployeeDao.insert(planEmployeee);
	}

	
	@Override
	public BenefitPlanEmployeeDoc getDoc(Integer benefitPlanEmpDocId) {
		return benefitPlanEmployeeDocDao.get(benefitPlanEmpDocId);
	}

	@Override
	public Boolean update(BenefitPlanEmployeeDoc benefitPlanEmployeeDoc) {
		return benefitPlanEmployeeDocDao.update(benefitPlanEmployeeDoc);
	}

	@Override
	public List<BenefitPlanEmployeeDoc> getDocs(Integer planEmployeeId) {
		return benefitPlanEmployeeDocDao.getById(planEmployeeId);
	}

	@Override
	public BenefitPlanEmployee getPlanEmployee(Integer planEmployeeId) {
		return benefitPlanEmployeeDao.get(planEmployeeId);
	}

	@Override
	public List<BenefitPlanClaim> listMyClaims(Integer planEmployeeId) {
		return benefitPlanClaimDao.listAll(planEmployeeId);
	}

	@Override
	public BenefitPlanEmployeeDetail getPlanEmployeeDetail(
			Integer planEmployeeId, Integer miscId) {
		return benefitPlanEmployeeDetailDao.get(planEmployeeId, miscId);
	}

	@Override
	public Integer getUniqueClaimId() {
		return benefitPlanClaimDao.getNextId();
	}

	@Override
	public Boolean addNewClaim(BenefitPlanClaim claim) {
		return benefitPlanClaimDao.insert(claim);
	}

	@Override
	public BenefitPlanClaim getClaim(Integer claimId) {
		return benefitPlanClaimDao.get(claimId);
	}

	@Override
	public Boolean edit(BenefitPlanClaim claim) {
		return benefitPlanClaimDao.update(claim);
	}
	
	@Override
	public Boolean editPlanEmployee(BenefitPlanEmployee planEmployee){
		return benefitPlanEmployeeDao.update(planEmployee);
	}
	
	@Override
	public List<BenefitPlanClaim> listAllApprovals(Integer planId, String fy){
		return benefitPlanClaimDao.listAllToApprove(planId, fy);
	}
	
	
	@Override
	public List<Integer> listDependencies(Integer planId) {
		return benefitPlanDependencyDao.list(planId);
	}

	@Override
	public List<BenefitPlanEmployee> getByIds(String fiscalYear, Integer empId) {
		return benefitPlanEmployeeDao.getByIds(empId, fiscalYear);
	}
	
	@Override
	public List<BenefitPlanClaim> searchClaims(String claimRefNo, String empInfo, Integer planId, String fiscalYear, String status){
		return benefitPlanClaimDao.listAll(claimRefNo, empInfo, planId, fiscalYear, status);
	}
	
	@Override
	public List<BenefitPlanClaim> listAllClaimsForFy(String fy){
		return benefitPlanClaimDao.listAll(fy);
	}
	
	@Override
	public List<BenefitPlanEmployee> listByYearlyOpt(Integer yearlyOptId, Boolean active){
		return benefitPlanEmployeeDao.listByYealryOpt(yearlyOptId, active);
	}
	
	@Override
	public List<BenefitPlanEmployee> listByYearlyOpt(Integer yearlyOptId){
		return benefitPlanEmployeeDao.listByYealryOpt(yearlyOptId);
	}

	@Override
	public BenefitPlanClaim getByClaimId(Integer claimId) {
		return benefitPlanClaimDao.get(claimId);
	}

	@Override
	public List<BenefitPlanClaimDetail> getBy(Integer claimId) {
		return claimDetailDao.listAll(claimId);
	}
	
	@Override
	public Boolean removeClaim (BenefitPlanClaim claim){
		return benefitPlanClaimDao.delete(claim);
	}
	
	@Override
	public List<BenefitPlanDirectClaim> listDirectClaimsByPlan(Integer planEmpId)
	{
		return benefitPlanDirectClaimDao.listAll(planEmpId);
	}
	
	@Override
	public void saveDirectClaimsByPlan(BenefitPlanDirectClaim benefitPlanDirectClaim)
	{
		benefitPlanDirectClaimDao.update(benefitPlanDirectClaim);
	}

	@Override
	public Integer getUniqueDirectClaimId() {
		// TODO Auto-generated method stub
		return benefitPlanDirectClaimDao.getUniqueDirectClaimId();
	}
	@Override
	public List<BenefitPlanClaim> listClaims(Integer planEmployeeId) {
		return benefitPlanClaimDao.listAll(planEmployeeId);
	}
	
	@Override
	public List<BenefitPlanClaimDetail> listOtherClaims(Integer benefitPlanClaimId) {
		return benefitPlanClaimDao.listOtherClaims(benefitPlanClaimId);
	}
	
	
	@Override
	public BenefitPlanBand getPlanBand (Integer planId, String band){
		return benefitPlanBandDao.get(planId, band);
	}
	
	@Override
	public Boolean addDependency (BenefitPlanDependency dependency){
		return benefitPlanDependencyDao.insert(dependency);
	}
	
	@Override
	public List<BenefitPlanDependency> listAllBenefitPlan(){
		return benefitPlanDependencyDao.listAll();
	}
	
	
	@Override
	public List<UserRole> listAllUserRoles(){
		return usersRoleDao.listAll();
	}
	
	@Override
	public boolean insertUser(UserRole userRole){
		return usersRoleDao.insert(userRole);
	}
	
	@Override
	public boolean delete(UserRole userRole){
		return usersRoleDao.delete(userRole);
	}


	@Override
	public List<BenefitPlanEmployeeClaimPeriod> listAllClaims(
			Integer planEmployeeId, Date periodFrom, Date periodTo) {
		return benefitPlanEmployeeClaimPeriodsDao.listAll(planEmployeeId, periodFrom, periodTo);
	}

	@Override
	public List<BenefitPlanEmployeeClaimPeriod> listPreviousClaims(
			Integer planEmployeeId) {
		return benefitPlanEmployeeClaimPeriodsDao.listAll(planEmployeeId);
	}

	

	
	@Override
	public BenefitPlanEmployeeClaimPeriod getClaims(Integer claimPeriodId){
		return benefitPlanEmployeeClaimPeriodsDao.get(claimPeriodId);
	}
	
	@Override
	public List<BenefitPlanEmployeeClaimPeriod> listAllClaimPeriods(){
		return benefitPlanEmployeeClaimPeriodsDao.listAll();
	}
	@Override
	public boolean update(BenefitPlanEmployeeClaimPeriod benefitPlanEmployeeClaimPeriods){
		return benefitPlanEmployeeClaimPeriodsDao.update(benefitPlanEmployeeClaimPeriods);
	}

	@Override
	public Boolean insert(BenefitPlanEmployeeClaimPeriod claimPeriod) {
		return benefitPlanEmployeeClaimPeriodsDao.insert(claimPeriod);
	}

	@Override
	public List<BenefitPlanEmployee> listPlanEmployees(String fiscalYear,
			Integer planId) {
	
		return benefitPlanEmployeeDao.listAll(fiscalYear, planId);
	}

	@Override
	public List<PlanDenyReasonsMaster> listAllDenyReasons() {
		return denyReasonsMasterDao.listAll(true);
	}

	@Override
	public Boolean insert(PlanDenyReasonsMaster planDenyReasonsMaster) {
		return denyReasonsMasterDao.insert(planDenyReasonsMaster);
	}

	@Override
	public Boolean update(PlanDenyReasonsMaster planDenyReasonsMaster) {
		return denyReasonsMasterDao.update(planDenyReasonsMaster);
	}

	@Override
	public List<PlanEmployeeDenies> listDenies() {
		return deniesDao.listAll();
	}

	@Override
	public Boolean insert(PlanEmployeeDenies planEmployeeDenies) {
		return deniesDao.insert(planEmployeeDenies);
	}

	@Override
	public Boolean update(PlanEmployeeDenies planEmployeeDenies) {
		return deniesDao.update(planEmployeeDenies);
	}

	@Override
	public List<PlanEmployeeDenies> getByDenyId(Integer empId) {
		return deniesDao.getById(empId);
	}
	
	@Override
	public List<PlanEmployeeDenies> listDenies(Integer planId) {
		return deniesDao.listOfDenies(planId);
	}

	@Override
	public void rollBackApprovedPlans(BenefitPlanEmployee benefitPlanEmployee) {
	 benefitPlanEmployeeDao.rollBackApprovedPlans(benefitPlanEmployee);
		
	}

	@Override
	public void approveSelected(BenefitPlanEmployee benefitPlanEmployee) {
		benefitPlanEmployeeDao.update(benefitPlanEmployee);
		
	} 
	
	@Override
	public BenefitPlanDirectClaim getDirectClaim (Integer directClaimId){
		return benefitPlanDirectClaimDao.get(directClaimId);
	}

	@Override
	public Boolean updatePlanEmployee(BenefitPlanEmployee benefitPlanEmployee) {
		
		return benefitPlanEmployeeDao.update(benefitPlanEmployee);
	}

	@Override
	public List<BenefitPlanField> listAllFields(Integer planId) {
		
		return benefitPlanFieldDao.listAll(planId,"O",true);
		
	}
	
//@Override
//	public List<BenefitPlanEmployeeField> listAllField(Integer planEmployeeFieldId) {
		
	//	return benefitPlanEmployeeFieldsDao.listAllField(planEmployeeFieldId);
		
//	}

	@Override
	public List<BenefitPlanEmployeeField> listAllField(Integer planEmployeeId) {
		
		return benefitPlanEmployeeFieldsDao.listAllField(planEmployeeId,BenefitsConstants.BENEFIT_PLAN_FIELD_TYPE_ENROLL);  
	}

	@Override
	public List<BenefitPlanEmployeeField> listAllField(Integer planEmployeeId, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanDenyReasonsMaster getDenyReason(int reasonsMasterId) {
		
		return denyReasonsMasterDao.get(reasonsMasterId);
	}


}
