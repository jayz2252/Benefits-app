package com.speridian.benefits2.service;




import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speridian.benefits2.model.dao.INSPlanDao;
import com.speridian.benefits2.model.dao.INSPlanEmployeeClaimDao;
import com.speridian.benefits2.model.dao.InsurancePlanTreatmentDetailsDao;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployee;
import com.speridian.benefits2.model.pojo.INSPlan;
import com.speridian.benefits2.model.pojo.INSPlanEmployeeClaim;
import com.speridian.benefits2.model.pojo.INSPlanTreatmentDetails;



@Service
public class InsuranceServiceImpl implements InsuranceService {
	
	@Autowired
	INSPlanDao insurancePlanDao;
	
	@Autowired
	InsurancePlanTreatmentDetailsDao insurancePlanTreatmentDetailsDao;
	
	@Autowired
	INSPlanEmployeeClaimDao insPlanEmployeeClaimDao;
	

/*
	@Override
	public INSPlan createInsurance(INSPlan plan) {
		return InsurancePlanDao.insert(plan);
		//InsurancePlan p=	insurancePlanDao.insert(plan);
	//return p;
//return null;
		
	}*/

	/*@Override
	public List<Treatment> listTreatments() {
		
		List<Treatment> list = treatmentDao.listAll();
		
		return list;
	}

	@Override
	public void saveTreatments(Treatment treatment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Treatment get(Integer treatmentId) {
		// TODO Auto-generated method stub
		return treatmentDao.get(treatmentId);
	}

	@Override
	public InsurancePlan insert(InsurancePlan insurance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InsurancePlanTreatmentDetail savePlanDetail(InsurancePlanTreatmentDetail detail) {
		// TODO Auto-generated method stub
		//return insurancePlanDao.savePlanDetail(detail);
return null;
	}*/
	
	@Override
	public INSPlanTreatmentDetails savePlanDetail(INSPlanTreatmentDetails detail) {
		// TODO Auto-generated method stub
		return insurancePlanTreatmentDetailsDao.savePlanDetail(detail);

	}
	
	
	@Override
	public INSPlan insert(INSPlan insurance) {
		// TODO Auto-generated method stub
	   return insurancePlanDao.insert(insurance);
		
	}


	@Override
	public INSPlan update(INSPlan insurance) {
		// TODO Auto-generated method stub
		insurancePlanDao.update(insurance);
		return null;
	}


	@Override
	public Boolean updatePlan(Integer insPlanId) {
		// TODO Auto-generated method stub
	//	insurancePlanDao.updatePlan(insPlanId);
		return null;
	}



	@Override
	public List<INSPlanEmployeeClaim> listAll(String empCode,
			String fiscalYear, String claimType, String fromDate, String toDate,
			String claimRefNo) {
		
		return insPlanEmployeeClaimDao.listAll(empCode, fiscalYear, claimType, fromDate, toDate, claimRefNo);
	}


	@Override
	public List<INSPlan> listAll() {
		return insurancePlanDao.listAll();
	}


	@Override
	public INSPlan get(Integer insPlanId) {
		return insurancePlanDao.get(insPlanId);
	}


	@Override
	public List<INSPlanEmployeeClaim> listAll(String fiscalYear,
			String claimType, String status) {
		// TODO Auto-generated method stub
		return insPlanEmployeeClaimDao.listAll(fiscalYear, status, claimType);
	}



	
	

}
