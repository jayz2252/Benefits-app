package com.speridian.benefits2.service;


import java.util.Date;
import java.util.List;


import com.speridian.benefits2.model.pojo.INSPlan;
import com.speridian.benefits2.model.pojo.INSPlanEmployeeClaim;
import com.speridian.benefits2.model.pojo.INSPlanTreatmentDetails;






public interface InsuranceService {
	
	//public INSPlan createInsurance(INSPlan plan);
	
	/*public List<Treatment> listTreatments();
	
	public void saveTreatments(Treatment treatment);
	
	public Treatment get(Integer treatmentId);
	
	public InsurancePlan insert(InsurancePlan insurance);
	
	public InsurancePlanTreatmentDetail savePlanDetail(InsurancePlanTreatmentDetail detail);*/
	
	public INSPlan insert(INSPlan insurance);
	public INSPlan update(INSPlan insurance);
	public Boolean updatePlan(Integer insPlanId);
	public INSPlanTreatmentDetails savePlanDetail(INSPlanTreatmentDetails detail);
	public List<INSPlan> listAll();
	public INSPlan get(Integer insPlanId);
	
	public List<INSPlanEmployeeClaim> listAll(String empCode,String fiscalYear,String claimType,String fromDate,String toDate,String claimRefNo);
	public List<INSPlanEmployeeClaim> listAll(String fiscalYear,String claimType,String status);

}
