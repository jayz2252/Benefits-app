package com.speridian.benefits2.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.speridian.benefits2.beans.BenefitPlanBean;
import com.speridian.benefits2.beans.INSPlanBean;
import com.speridian.benefits2.beans.INSPlanLoyaltyLevelDetailList;
import com.speridian.benefits2.beans.INSPlanTreatmentDetailList;
import com.speridian.benefits2.beans.TreatmentList;
import com.speridian.benefits2.model.pojo.BenefitPlan;
import com.speridian.benefits2.model.pojo.EmpBenPlansYrlyOpts;
import com.speridian.benefits2.model.pojo.INSPlan;
import com.speridian.benefits2.model.pojo.INSPlanDepDetails;
import com.speridian.benefits2.model.pojo.INSPlanLoyaltyLevelDetail;
import com.speridian.benefits2.model.pojo.INSPlanLoyaltyLevels;
import com.speridian.benefits2.model.pojo.INSPlanTreatmentDetails;
import com.speridian.benefits2.model.pojo.Treatment;
import com.speridian.benefits2.model.pojo.INSPlanLoyaltyLevels;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.service.INSPlanLoyaltyService;
import com.speridian.benefits2.service.InsuranceService;
import com.speridian.benefits2.service.TreatmentService;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.util.AuthorizationUtil;
import com.speridian.benefits2.util.DataTypeUtil;
import com.sun.xml.bind.v2.runtime.output.InPlaceDOMOutput;


@Controller
@SessionAttributes("INSPlanPojo")
public class INSPlanController {
	
	@Autowired
	InsuranceService insuranceService;
	/*
	@Autowired
	INSPlanLoyaltyService insPlanLoyaltyService;*/
	
	@Autowired
	TreatmentService treatmentService;
	AppContext appContext;
	
	/**
	 * Shows the add new INS plan page(addNewInsPlan.jsp)
	 * 
	 * @param request
	 * @param response
	 * @return mav
	 */
	@RequestMapping(value = "/home/controlPanel/insurancePlans/new", method = RequestMethod.GET)
	public ModelAndView showAddINSPlans(HttpServletRequest request,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");
		
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"addNewInsPlan");

		
		INSPlan insPlan = new INSPlan();
		INSPlanBean insPlanBean = new INSPlanBean();
		
		mav.addObject("INSPlanPojo", insPlan);
        mav.addObject("INSPlanBean", insPlanBean);
		mav.addObject("eaicIndicator", insPlanBean.getEaicIncluded());
        
     
        
		return mav;
	}
	
	
	
	/**
	 * Add a new plan to database and Showing the add new INS plan
	 * page(addNewInsPlan.jsp)
	 * 
	 * @param request
	 * @param response
	 * @param planBean
	 * @return mav
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/new/saveINSPlan", method = RequestMethod.POST)
	public ModelAndView saveINSPlanAmount(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("INSPlanBean") INSPlanBean insPlanBean) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");
		
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "INSPlantypeDetails");
		
		INSPlan insPlanPojo = (INSPlan) request.getSession().getAttribute("INSPlanPojo");

		if (insPlanPojo != null && insPlanBean != null){
			
			insPlanPojo.setPlanName(insPlanBean.getPlanName());
			insPlanPojo.setPlanDesc(insPlanBean.getPlanDesc());
			insPlanPojo.setEffFrom(DataTypeUtil.toDateFromStringddMMMMMyyyy(insPlanBean.getEffFrom()));
			insPlanPojo.setEffTill(DataTypeUtil.toDateFromStringddMMMMMyyyy(insPlanBean.getEffTill()));
			insPlanPojo.setEaicIncluded(insPlanBean.getEaicIncluded());
			insPlanPojo.setLoyaltyLevelIncluded(insPlanBean.getLoyaltyLevelIncluded());
			insPlanPojo.setOthTreatmentsAppicable(insPlanBean.getOthTreatmentsAppicable());
			insPlanPojo.setActive(insPlanBean.getActive());
			if(insPlanBean.getInsPlanId() != null) {
				insPlanPojo.setInsPlanId(Integer.valueOf(insPlanBean.getInsPlanId()));
			}
			
			
		}
		System.out.println("*****************"+insPlanPojo.getEaicIncluded()+"###########"+insPlanPojo.getLoyaltyLevelIncluded()+"&&&&&&&&&&&&&"+insPlanBean.getOthTreatmentsAppicable());
		INSPlanDepDetails depDetails = new INSPlanDepDetails();
		depDetails.getDepRelationship();
		depDetails.getEmpYearlyDeduction();
		depDetails.getCmpYearlyDeduction();
		depDetails.getYearlyCoverage();
		
		
		
		//INSPlan plan=   insuranceService.insert(insPlanPojo);

		mav.addObject("INSPlanPojo", insPlanPojo);
        mav.addObject("INSPlanBean", insPlanBean);
		mav.addObject("INSPlanId", insPlanBean.getInsPlanId());
        mav.addObject("eaicIndicator", insPlanBean.getEaicIncluded());
        mav.addObject("DepDetails", depDetails);
        
		System.out.println("*****************"+insPlanPojo.getInsPlanId());

		return mav;
	}

	
	/**
	 * Add a INS plan details to database 
	 * page(INSPlantypeDetails.jsp)
	 * 
	 * @param request
	 * @param response
	 * @param planBean
	 * @return mav
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/new/saveINSPlanDetails", method = RequestMethod.POST)
	public ModelAndView saveINSPlanDetails(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("INSPlanBean") INSPlanBean insPlanBean) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");
		request.getAttribute("INSPlanBean");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "planTreatmentDetails");
		//String eaciId = request.getParameter("eaicIndicator");
		//System.out.println("~~~~~~~~~~~ eaicIndicator ~~~~~~~~~~~  :  "+eaciId);
		INSPlan insPlanPojo = (INSPlan) request.getSession().getAttribute("INSPlanPojo");
		INSPlanDepDetails depDetailsPojo = (INSPlanDepDetails) request.getSession().getAttribute("DepDetails");
        System.out.println("----------------------------------------   : "+insPlanPojo.getInsPlanId());
        Integer insPlanId = insPlanPojo.getInsPlanId();
        String eaicYrlyCov = insPlanBean.getEaicYearlyCoverage();
        String eaicYrlyDedctn = insPlanBean.getEaicYearlyDeduction();
		if (insPlanPojo != null && insPlanBean != null){
			
			if(insPlanBean.getPlanType() != null && insPlanPojo.getPlanType() != null) {
				insPlanPojo.setPlanType(insPlanBean.getPlanType());
			}
			if(insPlanBean.getYearlyCoverage() != null && !insPlanBean.getYearlyCoverage().isEmpty()) {
				insPlanPojo.setYearlyCoverage(new BigDecimal(insPlanBean.getYearlyCoverage()));
			}
			if(eaicYrlyDedctn != null && !eaicYrlyDedctn.isEmpty()) {
				insPlanPojo.setEaicYearlyDeduction(new BigDecimal(insPlanBean.getEaicYearlyDeduction()));
			}
			if(eaicYrlyCov != null && !eaicYrlyCov.isEmpty()) {
				insPlanPojo.setEaicYearlyCoverage(new BigDecimal(insPlanBean.getEaicYearlyCoverage()));
			}
			
		}
		
		INSPlanDepDetails depDetails = null;
		List<INSPlanDepDetails> depDetailsList = new ArrayList<INSPlanDepDetails>();
		
		
		for(int i = 1; i <= 10; i++) {
			
			String depRelationship = request.getParameter("dependent"+i);
			String empContribution = request.getParameter("empContribution"+i);
			String comContribution = request.getParameter("comContridution"+i);
			String yearlyCov = request.getParameter("covrage"+i);
			
			 depDetails = new INSPlanDepDetails();
			
			
			if(depRelationship != null && !depRelationship.equals("0")) {
				depDetails.setDepRelationship(depRelationship);
			}
			if(empContribution != null && !empContribution.isEmpty()) {
				depDetails.setEmpYearlyDeduction(new BigDecimal(empContribution));
			}
			if(comContribution != null && !comContribution.isEmpty()) {
				depDetails.setCmpYearlyDeduction(new BigDecimal(comContribution));
			}
			if(yearlyCov != null && !yearlyCov.isEmpty()) {
				depDetails.setYearlyCoverage(new BigDecimal(yearlyCov));
			}
			
			depDetailsList.add(depDetails);
			
		}
		
		 insPlanPojo.setDependentDetails(depDetailsList);
		
		
				List<Treatment> list =	treatmentService.listAll();
				TreatmentList treatmentList = new TreatmentList();
				treatmentList.setTreatments(list);
				
				
				mav.addObject("treatmentList", treatmentList);
			if(insPlanBean.getYearlyCoverage() != null & !insPlanBean.getYearlyCoverage().isEmpty()) {
				mav.addObject("coverageAmount", new BigDecimal(insPlanBean.getYearlyCoverage()));
			}
				
			
		mav.addObject("INSPlanPojo", insPlanPojo);
        mav.addObject("INSPlanBean", insPlanBean);
	    mav.addObject("INSPlanId", insPlanId);
	    System.out.println("**********************````````````````*"+insPlanId);
		return mav;
	}
	
	@RequestMapping(value = "/home/controlPanel/insurancePlans/new/saveTreatments", method = RequestMethod.POST)
	public ModelAndView saveTreatments(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("treatments") INSPlanTreatmentDetailList treatmentDetailList) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");
		
		INSPlan insPlanPojo = (INSPlan) request.getSession().getAttribute("INSPlanPojo");
		//INSPlan insPlanID = (INSPlan) request.getSession().getAttribute("INSPlanId");
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : "+insPlanID);
		
		
		
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "INSLoyaltyLevel");
		INSPlanTreatmentDetails model = new INSPlanTreatmentDetails();
		
		List<INSPlanTreatmentDetails> treatments = null;
		
		if(treatmentDetailList != null){
			 treatments = treatmentDetailList.getPlanTreatmentDetails();
			for(INSPlanTreatmentDetails item:treatments){
				model.setTreatment(item.getTreatment());
				model.setMaxCoverage(item.getMaxCoverage());
				model.setMaxCoveragePercentage(item.getMaxCoveragePercentage());
				model.setInsPlan(insPlanPojo);
				//              insuranceService.savePlanDetail(model);
				
			}
			
		}
		insPlanPojo.setTreatments(treatments);
		
		/*INSPlanLoyaltyLevels insPlanLoyaltyLevels = new INSPlanLoyaltyLevels();
				mav.addObject("INSPlanLylty", insPlanLoyaltyLevels);*/
		mav.addObject("INSPlanPojo", insPlanPojo);	
		
		
		
		return mav;
	}
	
	
	/*@RequestMapping(value = "/home/controlPanel/insurancePlans/new/saveINSLoyalty", method = RequestMethod.POST)
	public ModelAndView saveINSLoyaltyLevel(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("loyaltyDetail") INSPlanLoyaltyLevelDetailList insPlanLoyaltyLevelsList) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");
		
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "InsSummaryDetails");
		INSPlanLoyaltyLevels INSLoyltmodel = new INSPlanLoyaltyLevels();
		
		INSPlan plan = new INSPlan();
		plan.setInsPlanId(12);
		
		if(treatmentDetailList != null){
			List<INSPlanTreatmentDetails> treatments = treatmentDetailList.getPlanTreatmentDetails();
			for(INSPlanTreatmentDetails item:treatments){
				model.setTreatment(item.getTreatment());
				model.setMaxCoverage(item.getMaxCoverage());
				System.out.println("cov %:"+item.getMaxCoveragePercentage());
				model.setMaxCoveragePercentage(item.getMaxCoveragePercentage());
				model.setInsPlan(plan);
				insuranceService.savePlanDetail(model);
				
			}
		}
		return mav;
	}*/
	
	@RequestMapping(value = "/home/controlPanel/insurancePlans/new/saveINSLoyalty", method = RequestMethod.POST)
	public ModelAndView saveINSLoyaltyLevel(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("loyalty") INSPlanLoyaltyLevels levels
			
			) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");
		
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "InsSummaryDetails");
		INSPlan insPlanPojo = (INSPlan) request.getSession().getAttribute("INSPlanPojo");
		System.out.println("Loaykty level name    "+ levels.getLoyaltyLevelName());
		
		INSPlanLoyaltyLevels loyaltyLevels = null;
		List<INSPlanLoyaltyLevels> loyaltyLevelList = new ArrayList<INSPlanLoyaltyLevels>();
		for(int j=1 ; j<=5 ;j++) {
			
			String loyaltyName = request.getParameter("loyaltyLevelName"+j);
			String loyaltyDesc = request.getParameter("loyaltyLevelDesc"+j);
			String minYear = request.getParameter("loyaltyMinYears"+j);
			Integer loyaltyMinYears = null;
			
			if(minYear != null){
				 loyaltyMinYears =Integer.parseInt(minYear);
			}
			
			String maxYear = request.getParameter("loyaltyMaxYears"+j); 
			Integer loyaltyMaxYears = null;
			if(maxYear != null) {
				 loyaltyMaxYears = Integer.parseInt(maxYear);
			}
			
			
			loyaltyLevels = new INSPlanLoyaltyLevels();
			
			if(loyaltyName != null && !loyaltyName.isEmpty()) {
				loyaltyLevels.setLoyaltyLevelName(loyaltyName);
			}
			if(loyaltyDesc != null && !loyaltyDesc.isEmpty()) {
				loyaltyLevels.setLoyaltyLevelDesc(loyaltyDesc);
			}
			if(loyaltyMaxYears != null) {
				loyaltyLevels.setLoyaltyMaxYears(loyaltyMaxYears);
			}
			if(loyaltyMinYears != null) {
				loyaltyLevels.setLoyaltyMinYears(loyaltyMinYears);
			}
			
			
			loyaltyLevelList.add(loyaltyLevels);
			
			
			
		}
		INSPlanLoyaltyLevelDetail detail = null;
		List<INSPlanLoyaltyLevelDetail> detailList = new ArrayList<INSPlanLoyaltyLevelDetail>();
        if(loyaltyLevelList != null) {
			
			
		
			for(int i = 1 ; i<=5 ; i++) {
				
				String dependentName = request.getParameter("dependent"+i);
				String employeeYearlyDeduction = request.getParameter("employeeYearlyDeduction"+i);
				String companyYearlyDeduction = request.getParameter("companyYearlyDeduction"+i);
				
				detail = new INSPlanLoyaltyLevelDetail();
				
				if(dependentName != null & !dependentName.isEmpty()) {
					detail.setDepRelationship(dependentName);
				}
				if(companyYearlyDeduction != null && !companyYearlyDeduction.isEmpty())  {
					detail.setCompanyYearlyDeduction(new BigDecimal(companyYearlyDeduction));
				}
				if(companyYearlyDeduction != null && !companyYearlyDeduction.isEmpty()) {
					detail.setEmployeeYearlyDeduction(new BigDecimal(employeeYearlyDeduction));
				}
				
				detailList.add(detail);
				
			}
		}
		
		for(INSPlanLoyaltyLevels item: loyaltyLevelList){
			
			item.setDetails(detailList);
			
		}
		
		
		insPlanPojo.setLoyaltyLevels(loyaltyLevelList);
		
	//nsPlanPojo.setDependentDetails(detailList);
		
		/*
		INSPlanLoyaltyLevels insLevels = new INSPlanLoyaltyLevels();
		List<INSPlanLoyaltyLevelDetail> modelLevel = levels.getDetails();
		*/
		//System.out.println("****************** getDepRelationship"+modelLevel.get(0).getDepRelationship());
		//System.out.println("************** "+levels.getLoyaltyLevelName());
		/*List<INSPlanLoyaltyLevels> loyaltyList = new ArrayList<INSPlanLoyaltyLevels>();
		loyaltyList.add(levels);
		insPlanPojo.setLoyaltyLevels(loyaltyList);*/
	//nsPlanPojo.setDependentDetails(detailList);
		
		
		mav.addObject("INSPlanPojo", insPlanPojo);
		
		return mav;
	}
	
	@RequestMapping(value = "/home/controlPanel/insurancePlans/new/InsPlanSummarySave", method = RequestMethod.POST)
	public ModelAndView saveINSLoyaltySummarySave(HttpServletRequest request,
			HttpServletResponse response
			
			) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");
		
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "InsSummaryDetails");
		INSPlan insPlanPojo = (INSPlan) request.getSession().getAttribute("INSPlanPojo");
		//System.out.println("Loaykty level name"+ levels.getLoyaltyLevelName());
		
		
		
		
		mav.addObject("INSPlanPojo", insPlanPojo);

		INSPlan plan=   insuranceService.insert(insPlanPojo);
		insuranceService.update(insPlanPojo);
        System.out.println("****************   planId"+insPlanPojo.getInsPlanId());

		return mav;
	}
	
	@RequestMapping(value="/home/controlPanel/viewInsurancePlans", method = RequestMethod.GET)
	public ModelAndView viewInsurancePlans(HttpServletRequest request, HttpServletResponse response)
	{
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewInsurancePlans");
		
		List<INSPlan> insPlans = insuranceService.listAll();
		mav.addObject("insPlans", insPlans);
		
		return mav;
	}	
	
	@RequestMapping(value="/home/controlPanel/viewInsurancePlans/viewPlan/{insPlanId}")
	public ModelAndView viewEachInsPlan(HttpServletRequest request, HttpServletResponse response, @PathVariable(value="insPlanId") Integer insPlanId)
	{
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewEachInsPlan");
		
		INSPlan insPlan = insuranceService.get(insPlanId);
		mav.addObject("insPlan", insPlan);
		return mav;
	}
}

