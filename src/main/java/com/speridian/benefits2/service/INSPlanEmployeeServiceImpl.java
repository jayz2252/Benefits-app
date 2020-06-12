package com.speridian.benefits2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.beans.CityWrapper;
import com.speridian.benefits2.model.dao.HospitalDao;
import com.speridian.benefits2.model.dao.INSCategoryMasterDao;
import com.speridian.benefits2.model.dao.INSPlaNOfficeDao;
import com.speridian.benefits2.model.dao.INSPlanDao;
import com.speridian.benefits2.model.dao.INSPlanDepDetailsDao;
import com.speridian.benefits2.model.dao.INSPlanEmployeeClaimDao;
import com.speridian.benefits2.model.dao.INSPlanEmployeeDao;
import com.speridian.benefits2.model.dao.INSPlanLoyaltyLevelsDao;
import com.speridian.benefits2.model.dao.InsPlanEmployeeClaimBillDao;
import com.speridian.benefits2.model.dao.InsPlanEmployeeClaimBillDetailDao;
import com.speridian.benefits2.model.dao.InsPlanEmployeeClaimPafDetailDao;
import com.speridian.benefits2.model.dao.TreatmentDao;
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
import com.speridian.benefits2.model.util.BenefitsConstants;

/**
 * 
 * <pre>
 * Purpose of class
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 16-May-2017
 *
 */
public class INSPlanEmployeeServiceImpl implements INSPlanEmployeeService {

	@Autowired
	INSPlanEmployeeDao insPlanEmployeeDao;

	@Autowired
	INSPlanDao insPlanDao;

	@Autowired
	INSPlanLoyaltyLevelsDao insPlanLoyaltyLevelsDao;

	@Autowired
	INSPlanDepDetailsDao insPlanDepDetailsDao;

	@Autowired
	INSPlanEmployeeClaimDao insPlanEmployeeClaimDao;

	@Autowired
	TreatmentDao treatmentDao;

	@Autowired
	HospitalDao hospitalDao;

	@Autowired
	InsPlanEmployeeClaimPafDetailDao insPlanEmployeeClaimPafDetailDao;

	@Autowired
	InsPlanEmployeeClaimBillDao insPlanEmployeeClaimBillDao;

	@Autowired
	INSPlaNOfficeDao insPlaNOfficeDao;

	@Autowired
	InsPlanEmployeeClaimBillDetailDao insPlanEmployeeClaimBillDetailDao;

	@Autowired
	INSCategoryMasterDao insCategoryMasterDao;

	@Override
	public INSPlanEmployee getPlanEmployee(Integer empId, String fiscalYear) {
		return insPlanEmployeeDao.get(empId, fiscalYear);
	}

	@Override
	public List<INSPlan> listAllActive() {
		return insPlanDao.listAll(Boolean.TRUE);
	}

	@Override
	public INSPlan getPlan(Integer planId) {
		return insPlanDao.get(planId);
	}

	@Override
	public INSPlanLoyaltyLevels getLoyaltyLevel(Integer planId, Integer experience) {
		return insPlanLoyaltyLevelsDao.get(planId, experience);
	}

	@Override
	public INSPlanDepDetails getPlanDetail(Integer planId, String relationship) {
		return insPlanDepDetailsDao.get(planId, relationship);
	}

	@Override
	public INSPlanLoyaltyLevels getLoyaltyLevel(Integer loyaltyId) {
		return insPlanLoyaltyLevelsDao.get(loyaltyId);
	}

	@Override
	public Boolean savePlanEmployee(INSPlanEmployee planEmployee) {
		return insPlanEmployeeDao.insert(planEmployee);
	}

	@Override
	public INSPlanEmployee getPlanEmployee(Integer planEmployeeId) {
		return insPlanEmployeeDao.get(planEmployeeId);
	}

	@Override
	public List<Treatment> listAllTreatments() {
		return treatmentDao.listAll();
	}

	@Override
	public List<String> listAllStates() {
		return hospitalDao.listAllStates();
	}

	@Override
	public List<CityWrapper> listAllCities() {
		List<Object[]> citiesResult = hospitalDao.listAllCities();
		List<CityWrapper> cities = new ArrayList<CityWrapper>();

		for (Object[] obj : citiesResult) {
			CityWrapper city = new CityWrapper(((String) obj[0]), ((String) obj[1]));
			cities.add(city);
		}
		return cities;
	}

	@Override
	public List<Hospital> listAllHospitals() {
		return hospitalDao.listAll();
	}

	@Override
	public Integer getNextClaimId() {
		return insPlanEmployeeClaimDao.getNextClaimId();
	}

	@Override
	public Hospital getHospital(Integer hospitalId) {
		return hospitalDao.get(hospitalId);
	}

	@Override
	public Treatment getTreatment(Integer treatmentId) {
		return treatmentDao.get(treatmentId);
	}

	@Override
	public Boolean savePafClaim(INSPlanEmployeeClaim claim, InsPlanEmployeeClaimPafDetail paf) {
		return insPlanEmployeeClaimDao.insertPafClaim(claim, paf);
	}

	@Override
	public List<INSPlanEmployeeClaim> listClaimByEmployee(Integer employeeId, String status, String fiscalYear,
			String claimType) {
		return insPlanEmployeeClaimDao.listAll(fiscalYear, status, employeeId, claimType);
	}

	@Override
	public InsPlanEmployeeClaimPafDetail getPafDetail(Integer claimId) {
		return insPlanEmployeeClaimPafDetailDao.get(claimId);
	}

	@Override
	public Boolean updateInsClaim(INSPlanEmployeeClaim claim, InsPlanEmployeeClaimPafDetail pafDetail) {
		return insPlanEmployeeClaimDao.update(claim, pafDetail);
	}

	@Override
	public List<INSPlanEmployeeClaim> listAllClaims(Integer planEmployeeId) {
		return insPlanEmployeeClaimDao.listAll(planEmployeeId);
	}

	@Override
	public List<INSPlanEmployee> listAllEnrolledEmployees() {
		return insPlanEmployeeDao.listAll();
	}

	@Override
	public boolean updatePlanEmployee(INSPlanEmployee planEmployee) {
		return insPlanEmployeeDao.update(planEmployee);
	}

	@Override
	public boolean updatePlanEmployee(INSPlanEmployee planEmployee, Boolean updateDetails) {
		if (updateDetails) {
			return insPlanEmployeeDao.syncPlanEmployee(planEmployee);
		} else {
			return insPlanEmployeeDao.update(planEmployee);
		}
	}

	@Override
	public INSPlanEmployeeClaim getClaim(Integer claimId) {
		return insPlanEmployeeClaimDao.get(claimId);
	}

	@Override
	public Boolean update(InsPlanEmployeeClaimPafDetail claimPafDetail) {
		return insPlanEmployeeClaimPafDetailDao.update(claimPafDetail);
	}

	@Override
	public Boolean update(INSPlanEmployeeClaim claim) {
		return insPlanEmployeeClaimDao.update(claim);
	}

	@Override
	public Boolean update(INSPlanEmployeeDetails planEmployeeDetails) {
		return insPlanEmployeeDao.update(planEmployeeDetails);
	}

	@Override
	public Boolean remove(INSPlanEmployeeDetails planEmployeeDetails) {
		return insPlanEmployeeDao.remove(planEmployeeDetails);
	}

	@Override
	public InsPlanEmployeeClaimPafDetail getClaimPafDetail(Integer claimPafDetailId) {
		return insPlanEmployeeClaimPafDetailDao.getClaimPafDetail(claimPafDetailId);
	}

	@Override
	public List<INSPlanEmployeeDetails> listAllDetails() {
		return insPlanEmployeeDao.listplanEmpDetails();
	}

	@Override
	public List<INSPlanDepDetails> listAll(Integer planId) {
		return insPlanDepDetailsDao.listAll(planId);
	}

	@Override
	public List<INSPlanEmployee> listAllEmp(Integer planId) {
		return insPlanEmployeeDao.listAllEmp(planId);
	}
	
	@Override
	public List<INSPlanEmployee> listAllEmp(Integer planId,String fiscalYear) {
		return insPlanEmployeeDao.listAllEmp(planId,fiscalYear);
	}

	@Override
	public List<INSPlanEmployeeDetails> listAllPlanEmp(Integer planEmpId) {
		return insPlanEmployeeDao.listAllDetails(planEmpId);
	}

	@Override
	public List<InsPlanEmployeeClaimBillDetail> listAllDetailsByClaimId(Integer claimBillId) {
		return insPlanEmployeeClaimBillDetailDao.listAll(claimBillId);
	}

	@Override
	public List<InsPlanEmployeeClaimBill> listAllBillsByClaimId(Integer claimId) {
		return insPlanEmployeeClaimBillDao.listAll(claimId);
	}

	@Override
	public Integer insert(InsPlanEmployeeClaimPafDetail detail) {
		return insPlanEmployeeClaimPafDetailDao.insert(detail);
	}

	@Override
	public List<INSCategoryMaster> listAllCategories() {
		return insCategoryMasterDao.listAll();
	}

	@Override
	public InsPlanEmployeeClaimBill getBillById(Integer claimBillId) {
		return insPlanEmployeeClaimBillDao.get(claimBillId);
	}

	@Override
	public INSCategoryMaster getCategoryById(Integer categoryId) {

		return insCategoryMasterDao.get(categoryId);
	}

	@Override
	public Boolean updateBillDetail(InsPlanEmployeeClaimBillDetail billDetail) {

		return insPlanEmployeeClaimBillDetailDao.update(billDetail);
	}

	@Override
	public Boolean updateBill(InsPlanEmployeeClaimBill bill) {

		return insPlanEmployeeClaimBillDao.update(bill);
	}

	@Override
	public Boolean insertBillDetail(InsPlanEmployeeClaimBillDetail billDetail) {

		return insPlanEmployeeClaimBillDetailDao.insert(billDetail);
	}

	@Override
	public Integer insertBill(InsPlanEmployeeClaimBill bill) {

		return insPlanEmployeeClaimBillDao.insert(bill);
	}

	@Override
	public Integer insertClaim(INSPlanEmployeeClaim claim) {

		return insPlanEmployeeClaimDao.insert(claim);
	}

	@Override
	public InsPlanEmployeeClaimBillDetail getBillDetailById(Integer billDetailId) {

		return insPlanEmployeeClaimBillDetailDao.get(billDetailId);
	}

	@Override
	public InsPlanEmployeeClaimBillDetail getById(Integer billId) {
		return insPlanEmployeeClaimBillDetailDao.get(billId);
	}

	@Override
	public List<INSPlan> listEligiblePlans(String office) {
		List<INSPlanOffice> planOffices = insPlaNOfficeDao.getbyoffice(office);

		List<INSPlan> plans = new ArrayList<INSPlan>();

		for (INSPlanOffice planOffice : planOffices) {
			plans.add(planOffice.getInsPlan());
		}
		return plans;
	}

	@Override
	public INSPlanLoyaltyLevelDetail getLoyaltyDetail(Integer loyaltyId, String depRelationship) {
		return insPlanLoyaltyLevelsDao.getLoyaltyDetails(loyaltyId, depRelationship);
	}

	@Override
	public List<INSPlanEmployee> listMonthlyEmpList(String month, Integer insPlanId, String year) {
		return insPlanEmployeeDao.listMonthlyEmpList(month, insPlanId, year);
	}

	@Override
	public List<INSPlanEmployee> listPlanEmployees(String fiscalYear, String status) {
		// TODO Auto-generated method stub
		return insPlanEmployeeDao.listPlanEmployees(fiscalYear, status);
	}

	@Override
	public INSPlanEmployee gets(Integer empId) {
		return insPlanEmployeeDao.gets(empId);
	}

	@Override
	public INSPlanEmployeeDetails getPlanEmployeeDetail(Integer planEmployeeId, Integer dependentId) {
		return insPlanEmployeeDao.getDetail(planEmployeeId, dependentId);
	}

	@Override
	public INSPlanEmployeeClaim getEmployeePAFDetails(Integer planEmployeeId) {
		return insPlanEmployeeClaimDao.getEmployeePAFDetails(planEmployeeId);
	}

	@Override
	public Integer getClaimAmount(Integer planEmployeeId) {
		return insPlanEmployeeClaimDao.getClaimAmount(planEmployeeId);
	}

	@Override
	public void deleteBills(List<InsPlanEmployeeClaimBillDetail> billDetail) {
		insPlanEmployeeClaimBillDetailDao.deleteBills(billDetail);
		
	}

	@Override
	public List<String> getCities(String state) {
		
		return hospitalDao.listAllCities(state);
	}

	@Override
	public List<Hospital> listAllHospitalsBYCity(String city) {
		
		return hospitalDao.listAllHospitals(city);
	}

}
