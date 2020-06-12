package com.speridian.benefits2.controller;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.SystemProperties;
import org.hibernate.loader.custom.Return;
import org.hibernate.stat.internal.CategorizedStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.DocumentException;
import com.speridian.benefits2.beans.CategoryBean;
import com.speridian.benefits2.beans.CityWrapper;
import com.speridian.benefits2.beans.INSBillDetailBean;
import com.speridian.benefits2.beans.INSEligibleDependentVO;
import com.speridian.benefits2.beans.InsuranceSearchBean;
import com.speridian.benefits2.beans.PreAuthFormBean;
import com.speridian.benefits2.beans.SearchBean;
import com.speridian.benefits2.email.EmailFormatter;
import com.speridian.benefits2.email.EmailProperties;
import com.speridian.benefits2.email.EmailService;
import com.speridian.benefits2.model.pojo.BenefitPlan;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployee;
import com.speridian.benefits2.model.pojo.Dependent;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.Hospital;
import com.speridian.benefits2.model.pojo.INSCategoryMaster;
import com.speridian.benefits2.model.pojo.INSPlan;
import com.speridian.benefits2.model.pojo.INSPlanDepDetails;
import com.speridian.benefits2.model.pojo.INSPlanEmployee;
import com.speridian.benefits2.model.pojo.INSPlanEmployeeClaim;
import com.speridian.benefits2.model.pojo.INSPlanEmployeeDetails;
import com.speridian.benefits2.model.pojo.INSPlanLoyaltyLevelDetail;
import com.speridian.benefits2.model.pojo.INSPlanLoyaltyLevels;
import com.speridian.benefits2.model.pojo.InsPlanEmployeeClaimBill;
import com.speridian.benefits2.model.pojo.InsPlanEmployeeClaimBillDetail;
import com.speridian.benefits2.model.pojo.InsPlanEmployeeClaimPafDetail;
import com.speridian.benefits2.model.pojo.PFEmployee;
import com.speridian.benefits2.model.pojo.Treatment;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.model.util.InsUtil;
import com.speridian.benefits2.re.InsurancePlanRE;
import com.speridian.benefits2.service.EmployeeService;
import com.speridian.benefits2.service.INSPlanEmployeeService;
import com.speridian.benefits2.service.InsuranceService;
import com.speridian.benefits2.service.SettingsService;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.util.AuthorizationUtil;
import com.speridian.benefits2.util.DataTypeUtil;
import com.speridian.benefits2.writers.pdf.INSClaimReportPdfWriter;
import com.speridian.benefits2.ws.client.docman.rest.DocmanRestClient;
import com.speridian.benefits2.ws.constants.BenefitsWSConstants;

/**
 * 
 * <pre>
 * Controller for Insurance and Employee related flows
 * e.g. Enrolment, claim submission, pre-authorization form etc.
 * </pre>
 * 
 * @author jithin.kuriakose
 * @since 17-May-2017
 * 
 */
@Controller
@SessionAttributes("newJoineeEnrolMode")
public class INSPlanEmployeeController {

	AppContext appContext;

	@Autowired
	INSPlanEmployeeService insPlanEmployeeService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	InsurancePlanRE insurancePlanRE;

	@Autowired
	InsuranceService insuranceService;

	@Autowired
	SettingsService settingsService;

	@Autowired
	DocmanRestClient docmanRestClient;

	Date auditDate;

	DateFormat df;

	@RequestMapping(value = "/home/myInsurancePlan", method = RequestMethod.GET)
	public ModelAndView showMyInsurancePlan(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "employeeInsPlan");

		INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(
				appContext.getCurrentEmployee().getEmployeeId(), appContext.getCurrentInsuranceFiscalYear());

		/*
		 * if (planEmployee != null &&
		 * BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SAVE.equals(planEmployee.
		 * getStatus())) { List<INSPlan> activePlans = insPlanEmployeeService
		 * .listEligiblePlans(appContext.getCurrentEmployee().getParentOffice())
		 * ; mav.addObject("allPlans", activePlans); mav.addObject("saved",
		 * true);
		 * 
		 * } else {
		 */
		/*
		 * to get the saved enrollment to do agian
		 */
		if (planEmployee != null) {
			if (planEmployee.getStatus().equals(BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SAVE)) {
				planEmployee = null;
			}
		}
		if (planEmployee != null) {

			INSPlanEmployeeClaim claim = insPlanEmployeeService
					.getEmployeePAFDetails(planEmployee.getInsPlanEmployeeId());
			if (claim == null) {
				mav.addObject("canSubmitClaim", false);
			} else {
				mav.addObject("canSubmitClaim", true);
			}

			mav.addObject("optingEnabled", false);

			mav.addObject("planEmployee", planEmployee);
			mav.addObject("enroled", Boolean.TRUE);

			if (BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_HR_APPROVED.equals(planEmployee.getStatus())) {
				mav.addObject("submitClaimEnabled", true);
			} else {
				mav.addObject("submitClaimEnabled", false);
			}

			/*
			 * double claimPercentage = 0; if
			 * (BenefitsConstants.INS_PLAN_TYPE_FAMILY_POOL_COVERAGE.equals(
			 * planEmployee.getInsPlan().getPlanType())) { claimPercentage =
			 * (planEmployee.getAmountClaimed().doubleValue()) /
			 * (planEmployee.getSumInsured().doubleValue()) * 100; } else if
			 * (BenefitsConstants.INS_PLAN_TYPE_INDIVIDUAL_COVERAGE
			 * .equals(planEmployee.getInsPlan().getPlanType())) {
			 * 
			 * } else {
			 * 
			 * I don't know what to do otherwise :-(
			 * 
			 * } mav.addObject("claimPercentage", claimPercentage);
			 */

		} else {
			/*
			 * getting current date
			 */
			Calendar today = Calendar.getInstance();
			/*
			 * rolling one day back, because after method will strictly validate
			 * time component
			 */
			today.roll(Calendar.DAY_OF_YEAR, -1);

			String normalEnrolmentCutOffDateMMDDD = settingsService
					.getPropertyByCode(BenefitsConstants.PROP_INS_CUT_OFF_DATE).getPropertyValue();
			String year = appContext.getCurrentInsuranceFiscalYear().split("-")[0];

			String normalEnrolmentCutoffDateString = year + "-" + normalEnrolmentCutOffDateMMDDD;
			Date normalEnrolmentCutoffDate = DataTypeUtil.toDateFromStringyyyyMMdd(normalEnrolmentCutoffDateString);

			if (today.getTime().after(normalEnrolmentCutoffDate)) {
				/*
				 * enrolment period over, normal enrolment case
				 */
				int newJoineeCutOffDays = Integer.parseInt(settingsService
						.getPropertyByCode(BenefitsConstants.PROP_INS_NEW_JOINEE_CUT_OFF_DAYS).getPropertyValue());

				Calendar joiningDate = Calendar.getInstance();
				joiningDate.setTime(appContext.getCurrentEmployee().getDateOfJoin());

				joiningDate.roll(Calendar.DAY_OF_YEAR, newJoineeCutOffDays);

				if (today.getTime().after(joiningDate.getTime())) {
					/*
					 * enrolment period over both cases normal enrolment case &
					 * new joinee
					 */
					mav.addObject("optingEnabled", false);
					mav.addObject("enroled", Boolean.FALSE);
					mav.addObject("enrolLastDate", normalEnrolmentCutoffDate);
				} else {
					/*
					 * new joinee mode
					 */
					mav.addObject("optingEnabled", true);
					List<INSPlan> activePlans = insPlanEmployeeService
							.listEligiblePlans(appContext.getCurrentEmployee().getParentOffice());
					mav.addObject("allPlans", activePlans);
					mav.addObject("enrolLastDate", joiningDate.getTime());
					mav.addObject("newJoineeEnrolMode", true);
				}
			} else {
				mav.addObject("optingEnabled", true);
				List<INSPlan> activePlans = insPlanEmployeeService
						.listEligiblePlans(appContext.getCurrentEmployee().getParentOffice());
				mav.addObject("allPlans", activePlans);
				mav.addObject("enrolLastDate", normalEnrolmentCutoffDate);
			}
		}

		/* } */
		return mav;
	}

	@RequestMapping(value = "/home/myInsurancePlan/planDetails/{planId}", method = RequestMethod.GET)
	public ModelAndView showEnrollPlan(@PathVariable(value = "planId") int planId, HttpServletRequest request) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "insPlanEnrolDetails");

		INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(
				appContext.getCurrentEmployee().getEmployeeId(), appContext.getCurrentInsuranceFiscalYear());

		Employee employee = appContext.getCurrentEmployee();
		INSPlan plan = null;
		if (planEmployee != null) {
			plan = planEmployee.getInsPlan();
		} else {
			plan = insPlanEmployeeService.getPlan(planId);
		}
		mav.addObject("plan", plan);

		Calendar dojCalendar = Calendar.getInstance();
		dojCalendar.setTime(employee.getDateOfJoin());

		Calendar currentCalendar = Calendar.getInstance();

		/*
		 * calculating employee years of experience
		 */
		int employeeExperience = currentCalendar.get(Calendar.YEAR) - dojCalendar.get(Calendar.YEAR);
		/*
		 * calculating month difference if month difference is less than 10,
		 * years of experience will reduce one. this logic is to allow employees
		 * who has 2 year 10 months experience to avail 3 year loyalty and to
		 * block any one having 2 year 9 months to block availing 3 year loyalty
		 */
		int employeeExperienceMonthDiff = currentCalendar.get(Calendar.MONTH) - dojCalendar.get(Calendar.MONTH);

		if (employeeExperienceMonthDiff < 0) {
			employeeExperience--;
		}

		double selfEmployeeContribution = 0, selfCompanyContribution = 0, selfEaic = 0;

		int selfId = employeeService.getDependent(appContext.getCurrentEmployee().getEmployeeId(), "Self")
				.getDependentId();

		INSPlanLoyaltyLevels loyaltyLevel = insPlanEmployeeService.getLoyaltyLevel(plan.getInsPlanId(),
				employeeExperience);

		if (planEmployee != null && BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SAVE.equals(planEmployee.getStatus())) {
			mav.addObject("saved", true);
			mav.addObject("empContribution", planEmployee.getYearlyEmpDeduction());
			loyaltyLevel = planEmployee.getLoyaltyLevel();

		} else {
			mav.addObject("saved", false);
			loyaltyLevel = insPlanEmployeeService.getLoyaltyLevel(plan.getInsPlanId(), employeeExperience);
		}

		List<INSEligibleDependentVO> eligibleDependents = new ArrayList<INSEligibleDependentVO>();

		List<String> relationships = new ArrayList<String>();

		if (loyaltyLevel != null) {
			mav.addObject("loyaltyApplicable", Boolean.TRUE);
			mav.addObject("loyalty", loyaltyLevel);

			for (INSPlanLoyaltyLevelDetail loyaltyDetail : loyaltyLevel.getDetails()) {
				List<Dependent> dependents = employeeService.listAllDependents(employee.getEmployeeId(),
						loyaltyDetail.getDepRelationship(), new Short("120"), new Short("0"));

				for (Dependent dep : dependents) {
					INSEligibleDependentVO vo = new INSEligibleDependentVO(dep.getDependentId(), dep.getDependentName(),
							dep.getRelationship(), dep.getGender());

					vo.setDepDateOfBirth(dep.getDateOfBirth());
					vo.setEmployeeContribution(loyaltyDetail.getEmployeeYearlyDeduction());
					vo.setCompanyContribution(loyaltyDetail.getCompanyYearlyDeduction());
					vo.setEaicYearlyDeduction(plan.getEaicYearlyDeduction());
					vo.setDepId(dep.getDependentId());

					if (planEmployee != null
							&& BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SAVE.equals(planEmployee.getStatus())) {

						INSPlanEmployeeDetails existingSavedPlanEmployeeDetail = insPlanEmployeeService
								.getPlanEmployeeDetail(planEmployee.getInsPlanEmployeeId(), dep.getDependentId());

						if (existingSavedPlanEmployeeDetail != null) {
							vo.setIsINSEnroled(Boolean.TRUE);
							if (existingSavedPlanEmployeeDetail.getEaicEnrolled()) {
								vo.setIsEAICEnroled(Boolean.TRUE);
							} else {
								vo.setIsEAICEnroled(Boolean.FALSE);
							}
						} else {
							vo.setIsINSEnroled(Boolean.FALSE);
							vo.setIsINSEnroled(Boolean.FALSE);
						}

					}

					if ("Self".equals(dep.getRelationship())) {
						vo.setIsSelf(Boolean.TRUE);
						selfCompanyContribution = loyaltyDetail.getCompanyYearlyDeduction().doubleValue();
						selfEmployeeContribution = loyaltyDetail.getEmployeeYearlyDeduction().doubleValue();
						selfEaic = plan.getEaicYearlyDeduction().doubleValue();
					} else {
						vo.setIsSelf(Boolean.FALSE);
					}

					eligibleDependents.add(vo);
				}

				if ("Self".equals(loyaltyDetail.getDepRelationship())) {
					selfCompanyContribution = loyaltyDetail.getCompanyYearlyDeduction().doubleValue();
					selfEmployeeContribution = loyaltyDetail.getEmployeeYearlyDeduction().doubleValue();
					selfEaic = plan.getEaicYearlyDeduction().doubleValue();
				}
			}
		} else {
			mav.addObject("loyaltyApplicable", Boolean.FALSE);

			List<INSPlanDepDetails> planDepDetails = plan.getDependentDetails();
			for (INSPlanDepDetails insDep : planDepDetails) {
				Boolean dependentsChecked = true;
				List<Dependent> dependents = employeeService.listAllDependents(employee.getEmployeeId(),
						insDep.getDepRelationship(), new Short("120"), new Short("0"));
				/*
				 * if (planEmployee != null &&
				 * BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SAVE
				 * .equals(planEmployee.getStatus())) {
				 * List<INSPlanEmployeeDetails> planEmployeeDetails =
				 * insPlanEmployeeService.listAllPlanEmp(planEmployee.
				 * getInsPlanEmployeeId());
				 * 
				 * }
				 */

				for (Dependent dep : dependents) {
					INSEligibleDependentVO vo = new INSEligibleDependentVO(dep.getDependentId(), dep.getDependentName(),
							dep.getRelationship(), dep.getGender());

					vo.setDepDateOfBirth(dep.getDateOfBirth());
					vo.setEmployeeContribution(insDep.getEmpYearlyDeduction());
					vo.setCompanyContribution(insDep.getCmpYearlyDeduction());
					vo.setEaicYearlyDeduction(plan.getEaicYearlyDeduction());
					vo.setDepId(dep.getDependentId());

					if (planEmployee != null
							&& BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SAVE.equals(planEmployee.getStatus())) {

						INSPlanEmployeeDetails existingSavedPlanEmployeeDetail = insPlanEmployeeService
								.getPlanEmployeeDetail(planEmployee.getInsPlanEmployeeId(), dep.getDependentId());

						if (existingSavedPlanEmployeeDetail != null) {
							vo.setIsINSEnroled(Boolean.TRUE);
							if (existingSavedPlanEmployeeDetail.getEaicEnrolled()) {
								vo.setIsEAICEnroled(Boolean.TRUE);
							} else {
								vo.setIsEAICEnroled(Boolean.FALSE);
							}
						} else {
							vo.setIsINSEnroled(Boolean.FALSE);
							vo.setIsINSEnroled(Boolean.FALSE);
						}

					}

					if ("Self".equals(dep.getRelationship())) {
						vo.setIsSelf(Boolean.TRUE);

						selfCompanyContribution = insDep.getCmpYearlyDeduction().doubleValue();
						selfEmployeeContribution = insDep.getEmpYearlyDeduction().doubleValue();
						selfEaic = plan.getEaicYearlyDeduction().doubleValue();
					} else {
						vo.setIsSelf(Boolean.FALSE);
					}

					eligibleDependents.add(vo);
				}

			}
		}
		mav.addObject("selfEmployeeContribution", selfEmployeeContribution);
		mav.addObject("selfCompanyContribution", selfCompanyContribution);
		mav.addObject("selfId", selfId);
		mav.addObject("eligibleDependents", eligibleDependents);
		mav.addObject("isAdmin", Boolean.FALSE);
		mav.addObject("planEmployeeId", planId);

		return mav;
	}

	@RequestMapping(value = "/home/myInsurancePlan/planDetails/{planId}/optits", method = RequestMethod.POST)
	public String savePlan(@PathVariable(value = "planId") int planId, HttpServletRequest request) {

		try {

			appContext = (AppContext) request.getSession().getAttribute("appContext");
			INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(
					appContext.getCurrentEmployee().getEmployeeId(), appContext.getCurrentInsuranceFiscalYear());

			if (planEmployee != null
					&& BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SAVE.equals(planEmployee.getStatus())) {
				Boolean newJoineeEnrolMode = false;
				Boolean flags = false;
				try {
					newJoineeEnrolMode = (Boolean) request.getSession().getAttribute("newJoineeEnrolMode") == null
							? false : true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				Date auditDate = new Date();
				INSPlan plan = insPlanEmployeeService.getPlan(planId);

				String loyaltyId = request.getParameter("loyaltyId");
				String insEnrolledDependentsString = request.getParameter("insEnrolledDep");
				String eaicEnrolledDependentsString = request.getParameter("eaicEnrolledDep");
				String companyYearlyDeduction = request.getParameter("cmpYearlyDeduction");
				String employeeYearlyDeduction = request.getParameter("empYearlyDeduction");

				if (insEnrolledDependentsString != null && !("".equals(insEnrolledDependentsString))) {
					/*
					 * if (companyYearlyDeduction != null &&
					 * !("".equals(companyYearlyDeduction)) &&
					 * employeeYearlyDeduction != null &&
					 * !("".equals(employeeYearlyDeduction))) {
					 */
					String[] insEnrolledDependentsArray = insEnrolledDependentsString.split(",");
					String[] eaicEnrolledDependentsArray = eaicEnrolledDependentsString.split(",");

					/*
					 * finding validity periodsappContext = (AppContext)
					 * request.getSession().getAttribute( "appContext");
					 * INSPlanEmployee planEmployee = new INSPlanEmployee();
					 * INSPlan plan = insPlanEmployeeService.getPlan(planId);
					 * 
					 * String insEnrolledDependentsString = request
					 * .getParameter("insEnrolledDep"); String
					 * eaicEnrolledDependentsString = request
					 * .getParameter("eaicEnrolledDep"); String
					 * companyYearlyDeduction = request
					 * .getParameter("cmpYearlyDeduction"); String
					 * employeeYearlyDeduction = request
					 * .getParameter("empYearlyDeduction"); String[]
					 * insEnrolledDependentsArray = insEnrolledDependentsString
					 * .split(","); for (int i = 0; i <
					 * insEnrolledDependentsArray.length; i++) { Dependent
					 * dependent = employeeService.getDependent(Integer
					 * .parseInt(insEnrolledDependentsArray[i])); if (dependent
					 * != null) { INSPlanEmployeeDetails planEmployeeDetail =
					 * new INSPlanEmployeeDetails();
					 * planEmployeeDetail.setDependent(dependent);
					 * planEmployeeDetail.setPlanEmployee(planEmployee); }
					 * 
					 * } return "redirect:/home/myInsurancePlan"; }
					 */
					String startYearString = appContext.getCurrentInsuranceFiscalYear().split("-")[0];
					String nextYearString = "20" + appContext.getCurrentInsuranceFiscalYear().split("-")[1];

					String normalPlanValidtyPeriodString = settingsService
							.getPropertyByCode(BenefitsConstants.PROP_INS_NORMAL_VALIDITY).getPropertyValue();
					String validityFromMMDD = normalPlanValidtyPeriodString.split("~")[0];
					String validityToMMDD = normalPlanValidtyPeriodString.split("~")[1];

					String validityFromString = startYearString + "-" + validityFromMMDD;
					String validityToString = nextYearString + "-" + validityToMMDD;

					Date validityFrom = DataTypeUtil.toDateFromStringyyyyMMdd(validityFromString);
					Date validityTo = DataTypeUtil.toDateFromStringyyyyMMdd(validityToString);

					/*
					 * finding total effective months
					 */

					Integer cutOffMonth = Integer.parseInt(validityToMMDD.split("-")[0]);
					cutOffMonth--;

					Calendar startCalendar = new GregorianCalendar();
					if (newJoineeEnrolMode) {
						startCalendar.setTime(appContext.getCurrentEmployee().getDateOfJoin());
					} else {
						startCalendar.setTime(validityFrom);
					}

					Calendar endCalendar = new GregorianCalendar();
					endCalendar.setTime(validityTo);

					int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
					int diffMonth = (diffYear * 12)
							+ (endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH));

					/*
					 * EAIC monthly deduction
					 */

					double eaicMonthlyDeduction = plan.getEaicYearlyDeduction().doubleValue() / 12;

					planEmployee.setActive(Boolean.FALSE);
					planEmployee.setAmountClaimed(new BigDecimal(0));
					planEmployee.setTotalEffMonths(diffMonth);

					if (newJoineeEnrolMode) {
						planEmployee.setEffFrom(appContext.getCurrentEmployee().getDateOfJoin());
					} else {
						planEmployee.setEffFrom(validityFrom);
					}
					planEmployee.setEffTill(validityTo);
					planEmployee.setEmployee(appContext.getCurrentEmployee());
					planEmployee.setEnrolledDate(auditDate);
					planEmployee.setFiscalYear(appContext.getCurrentInsuranceFiscalYear());
					planEmployee.setInsPlan(plan);
					planEmployee.setStatus(BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SAVE);
					planEmployee.setSumInsured(plan.getYearlyCoverage());

					planEmployee.setYearlyEmpDeduction(new BigDecimal(employeeYearlyDeduction));
					planEmployee.setYearlyCmpDeduction(new BigDecimal(companyYearlyDeduction));

					if (loyaltyId == null || "".equals(loyaltyId)) {
						/*
						 * No Loyalty applicable
						 */
						planEmployee.setIsLoyaltyMode(Boolean.FALSE);
					} else {
						/*
						 * Loyalty applicable
						 */
						INSPlanLoyaltyLevels loyaltyLevel = insPlanEmployeeService
								.getLoyaltyLevel(Integer.parseInt(loyaltyId));

						if (loyaltyLevel != null) {
							planEmployee.setIsLoyaltyMode(Boolean.TRUE);
							planEmployee.setLoyaltyLevel(loyaltyLevel);
						}
					}

					/*
					 * creating planEmployeeDetails
					 */

					List<INSPlanEmployeeDetails> planEmployeeDetails = new ArrayList<INSPlanEmployeeDetails>();
					BigDecimal eaicTotalDeduction = new BigDecimal(0);
					Boolean eaicEnrolled = Boolean.FALSE;
					for (int i = 0; i < insEnrolledDependentsArray.length; i++) {
						Dependent dependent = employeeService
								.getDependent(Integer.parseInt(insEnrolledDependentsArray[i]));

						if (dependent != null) {
							INSPlanEmployeeDetails planEmployeeDetail = new INSPlanEmployeeDetails();
							planEmployeeDetail.setDependent(dependent);
							planEmployeeDetail.setPlanEmployee(planEmployee);

							/*
							 * checking loyalty level details exists or not
							 */
							INSPlanLoyaltyLevelDetail loyaltyDetail = null;
							if (planEmployee.getIsLoyaltyMode()) {
								/*
								 * loyalty level detail exists only if loyalty
								 * mode is true
								 */
								loyaltyDetail = insPlanEmployeeService.getLoyaltyDetail(
										planEmployee.getLoyaltyLevel().getInsPlanLoyaltyLevelsId(),
										dependent.getRelationship());
							}

							/*
							 * loyalty level detail exists
							 */
							if (loyaltyDetail != null) {
								double employeeMonthlyContribution = loyaltyDetail.getEmployeeYearlyDeduction()
										.doubleValue() / 12;

								double companyMonthlyContribution = loyaltyDetail.getCompanyYearlyDeduction()
										.doubleValue() / 12;

								planEmployeeDetail.setLoyaltyLevelDetail(loyaltyDetail);

								if (BenefitsConstants.INS_PLAN_TYPE_FAMILY_POOL_COVERAGE.equals(plan.getPlanType())) {
									planEmployeeDetail.setSumInsured(plan.getYearlyCoverage());
								} else if (BenefitsConstants.INS_PLAN_TYPE_INDIVIDUAL_COVERAGE
										.equals(plan.getPlanType())) {
									if (loyaltyDetail != null) {
										planEmployeeDetail.setSumInsured(plan.getYearlyCoverage());
									}
								}

								if (loyaltyDetail != null) {
									planEmployeeDetail.setYearlyCmpDeduction(
											new BigDecimal(companyMonthlyContribution * 12));
									planEmployeeDetail.setYearlyEmpDeduction(
											new BigDecimal(employeeMonthlyContribution * 12));
								}
								for (int j = 0; j < eaicEnrolledDependentsArray.length; j++) {
									if (eaicEnrolledDependentsArray[j].equals(insEnrolledDependentsArray[i])) {
										planEmployeeDetail.setEaicEnrolled(Boolean.TRUE);
										planEmployeeDetail.setYearlyEaicDeduction(
												new BigDecimal(eaicMonthlyDeduction * 12));
										planEmployeeDetail.setEaicSumInsured(plan.getEaicYearlyCoverage());
										eaicTotalDeduction = eaicTotalDeduction.add(plan.getEaicYearlyDeduction());
										eaicEnrolled = Boolean.TRUE;
									}
								}

								if (planEmployeeDetail.getEaicEnrolled() == null) {
									planEmployeeDetail.setEaicEnrolled(Boolean.FALSE);
								}
							} else { /*
										 * loyalty level detail not exists
										 */
								INSPlanDepDetails planDetail = insPlanEmployeeService.getPlanDetail(plan.getInsPlanId(),
										dependent.getRelationship());

								double employeeMonthlyContribution = planDetail.getEmpYearlyDeduction().doubleValue()
										/ 12;

								double companyMonthlyContribution = planDetail.getCmpYearlyDeduction().doubleValue()
										/ 12;

								if (BenefitsConstants.INS_PLAN_TYPE_FAMILY_POOL_COVERAGE.equals(plan.getPlanType())) {
									planEmployeeDetail.setSumInsured(plan.getYearlyCoverage());
								} else if (BenefitsConstants.INS_PLAN_TYPE_INDIVIDUAL_COVERAGE
										.equals(plan.getPlanType())) {
									if (planDetail != null) {
										planEmployeeDetail.setSumInsured(planDetail.getYearlyCoverage());
									}
								}

								if (planDetail != null) {
									planEmployeeDetail.setYearlyCmpDeduction(
											new BigDecimal(companyMonthlyContribution * 12));
									planEmployeeDetail.setYearlyEmpDeduction(
											new BigDecimal(employeeMonthlyContribution * 12));
								}
								for (int j = 0; j < eaicEnrolledDependentsArray.length; j++) {
									if (eaicEnrolledDependentsArray[j].equals(insEnrolledDependentsArray[i])) {
										planEmployeeDetail.setEaicEnrolled(Boolean.TRUE);
										planEmployeeDetail.setYearlyEaicDeduction(
												new BigDecimal(eaicMonthlyDeduction * 12));
										planEmployeeDetail.setEaicSumInsured(plan.getEaicYearlyCoverage());
										eaicTotalDeduction = eaicTotalDeduction.add(plan.getEaicYearlyDeduction());
										eaicEnrolled = Boolean.TRUE;
									}
								}

								if (planEmployeeDetail.getEaicEnrolled() == null) {
									planEmployeeDetail.setEaicEnrolled(Boolean.FALSE);
								}

							}
							if (planEmployeeDetail.getCreatedBy() == null) {
								planEmployeeDetail.setCreatedBy(appContext.getUserName());
							}
							if (planEmployeeDetail.getCreatedDate() == null) {
								planEmployeeDetail.setCreatedDate(auditDate);
							}

							planEmployeeDetail.setUpdatedBy(appContext.getUserName());
							planEmployeeDetail.setUpdatedDate(auditDate);

							planEmployeeDetails.add(planEmployeeDetail);

						}
					}

					/*
					 * List<INSPlanEmployeeDetails> existingDetails =
					 * insPlanEmployeeService
					 * .listAllPlanEmp(planEmployees.getInsPlanEmployeeId());
					 * 
					 * for (INSPlanEmployeeDetails existingDetail :
					 * existingDetails) { if
					 * (!planEmployeeDetails.contains(existingDetail)) {
					 * insPlanEmployeeService.remove(existingDetail); } }
					 * 
					 * List<INSPlanEmployeeDetails> tobeDeletedDetails = new
					 * ArrayList<INSPlanEmployeeDetails>();
					 * tobeDeletedDetails.addAll(empdetails); Boolean
					 * tobeDeletedFlag = false;
					 * 
					 * tobeDeletedFlag =
					 * tobeDeletedDetails.removeAll(planEmployeeDetails);
					 * 
					 * if (tobeDeletedFlag) { for (INSPlanEmployeeDetails
					 * tobeDeletedDetail : tobeDeletedDetails) {
					 * insPlanEmployeeService.remove(tobeDeletedDetail); } }
					 */

					planEmployee.setDetails(planEmployeeDetails);

					if (eaicEnrolled) {
						planEmployee.setYearlyEaicDeduction(eaicTotalDeduction);
						planEmployee.setEaicSumInsured(plan.getEaicYearlyCoverage());
					}

					planEmployee.setEnrolledDate(auditDate);

					if (planEmployee.getCreatedBy() == null) {
						planEmployee.setCreatedBy(appContext.getUserName());
					}
					if (planEmployee.getCreatedDate() == null) {
						planEmployee.setCreatedDate(auditDate);
					}
					planEmployee.setUpdatedBy(appContext.getUserName());
					planEmployee.setUpdatedDate(auditDate);

					insPlanEmployeeService.updatePlanEmployee(planEmployee, true);
					return "redirect:/home/myInsurancePlan/planDetails/" + planId;
				}
			} else {
				Boolean newJoineeEnrolMode = false;
				try {
					newJoineeEnrolMode = (Boolean) request.getSession().getAttribute("newJoineeEnrolMode") == null
							? false : true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				Date auditDate = new Date();
				INSPlan plan = insPlanEmployeeService.getPlan(planId);

				String loyaltyId = request.getParameter("loyaltyId");
				String insEnrolledDependentsString = request.getParameter("insEnrolledDep");
				String eaicEnrolledDependentsString = request.getParameter("eaicEnrolledDep");
				String companyYearlyDeduction = request.getParameter("cmpYearlyDeduction");
				String employeeYearlyDeduction = request.getParameter("empYearlyDeduction");

				if (insEnrolledDependentsString != null && !("".equals(insEnrolledDependentsString))) {
					/*
					 * if (companyYearlyDeduction != null &&
					 * !("".equals(companyYearlyDeduction)) &&
					 * employeeYearlyDeduction != null &&
					 * !("".equals(employeeYearlyDeduction))) {
					 */
					String[] insEnrolledDependentsArray = insEnrolledDependentsString.split(",");
					String[] eaicEnrolledDependentsArray = eaicEnrolledDependentsString.split(",");

					/*
					 * finding validity periodsappContext = (AppContext)
					 * request.getSession().getAttribute( "appContext");
					 * INSPlanEmployee planEmployee = new INSPlanEmployee();
					 * INSPlan plan = insPlanEmployeeService.getPlan(planId);
					 * 
					 * String insEnrolledDependentsString = request
					 * .getParameter("insEnrolledDep"); String
					 * eaicEnrolledDependentsString = request
					 * .getParameter("eaicEnrolledDep"); String
					 * companyYearlyDeduction = request
					 * .getParameter("cmpYearlyDeduction"); String
					 * employeeYearlyDeduction = request
					 * .getParameter("empYearlyDeduction"); String[]
					 * insEnrolledDependentsArray = insEnrolledDependentsString
					 * .split(","); for (int i = 0; i <
					 * insEnrolledDependentsArray.length; i++) { Dependent
					 * dependent = employeeService.getDependent(Integer
					 * .parseInt(insEnrolledDependentsArray[i])); if (dependent
					 * != null) { INSPlanEmployeeDetails planEmployeeDetail =
					 * new INSPlanEmployeeDetails();
					 * planEmployeeDetail.setDependent(dependent);
					 * planEmployeeDetail.setPlanEmployee(planEmployee); }
					 * 
					 * } return "redirect:/home/myInsurancePlan"; }
					 */
					String startYearString = appContext.getCurrentInsuranceFiscalYear().split("-")[0];
					String nextYearString = "20" + appContext.getCurrentInsuranceFiscalYear().split("-")[1];

					String normalPlanValidtyPeriodString = settingsService
							.getPropertyByCode(BenefitsConstants.PROP_INS_NORMAL_VALIDITY).getPropertyValue();
					String validityFromMMDD = normalPlanValidtyPeriodString.split("~")[0];
					String validityToMMDD = normalPlanValidtyPeriodString.split("~")[1];

					String validityFromString = startYearString + "-" + validityFromMMDD;
					String validityToString = nextYearString + "-" + validityToMMDD;

					Date validityFrom = DataTypeUtil.toDateFromStringyyyyMMdd(validityFromString);
					Date validityTo = DataTypeUtil.toDateFromStringyyyyMMdd(validityToString);

					/*
					 * finding total effective months
					 */

					Integer cutOffMonth = Integer.parseInt(validityToMMDD.split("-")[0]);
					cutOffMonth--;

					Calendar startCalendar = new GregorianCalendar();
					if (newJoineeEnrolMode) {
						startCalendar.setTime(appContext.getCurrentEmployee().getDateOfJoin());
					} else {
						startCalendar.setTime(validityFrom);
					}

					Calendar endCalendar = new GregorianCalendar();
					endCalendar.setTime(validityTo);

					int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
					int diffMonth = (diffYear * 12)
							+ (endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH));

					/*
					 * EAIC monthly deduction
					 */

					double eaicMonthlyDeduction = plan.getEaicYearlyDeduction().doubleValue() / 12;

					planEmployee = new INSPlanEmployee();
					planEmployee.setActive(Boolean.FALSE);
					planEmployee.setAmountClaimed(new BigDecimal(0));
					planEmployee.setTotalEffMonths(diffMonth);

					if (newJoineeEnrolMode) {
						planEmployee.setEffFrom(appContext.getCurrentEmployee().getDateOfJoin());
					} else {
						planEmployee.setEffFrom(validityFrom);
					}
					planEmployee.setEffTill(validityTo);
					planEmployee.setEmployee(appContext.getCurrentEmployee());
					planEmployee.setEnrolledDate(auditDate);
					planEmployee.setFiscalYear(appContext.getCurrentInsuranceFiscalYear());
					planEmployee.setInsPlan(plan);
					planEmployee.setStatus(BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SAVE);
					planEmployee.setSumInsured(plan.getYearlyCoverage());

					planEmployee.setYearlyEmpDeduction(new BigDecimal(employeeYearlyDeduction));
					planEmployee.setYearlyCmpDeduction(new BigDecimal(companyYearlyDeduction));

					if (loyaltyId == null || "".equals(loyaltyId)) {
						/*
						 * No Loyalty applicable
						 */
						planEmployee.setIsLoyaltyMode(Boolean.FALSE);
					} else {
						/*
						 * Loyalty applicable
						 */
						INSPlanLoyaltyLevels loyaltyLevel = insPlanEmployeeService
								.getLoyaltyLevel(Integer.parseInt(loyaltyId));

						if (loyaltyLevel != null) {
							planEmployee.setIsLoyaltyMode(Boolean.TRUE);
							planEmployee.setLoyaltyLevel(loyaltyLevel);
						}
					}

					/*
					 * creating planEmployeeDetails
					 */

					List<INSPlanEmployeeDetails> planEmployeeDetails = new ArrayList<INSPlanEmployeeDetails>();
					BigDecimal eaicTotalDeduction = new BigDecimal(0);
					Boolean eaicEnrolled = Boolean.FALSE;
					for (int i = 0; i < insEnrolledDependentsArray.length; i++) {
						Dependent dependent = employeeService
								.getDependent(Integer.parseInt(insEnrolledDependentsArray[i]));

						if (dependent != null) {
							INSPlanEmployeeDetails planEmployeeDetail = new INSPlanEmployeeDetails();
							planEmployeeDetail.setDependent(dependent);
							planEmployeeDetail.setPlanEmployee(planEmployee);

							/*
							 * checking loyalty level details exists or not
							 */
							INSPlanLoyaltyLevelDetail loyaltyDetail = null;
							if (planEmployee.getIsLoyaltyMode()) {
								/*
								 * loyalty level detail exists only if loyalty
								 * mode is true
								 */
								loyaltyDetail = insPlanEmployeeService.getLoyaltyDetail(
										planEmployee.getLoyaltyLevel().getInsPlanLoyaltyLevelsId(),
										dependent.getRelationship());
							}

							/*
							 * loyalty level detail exists
							 */
							if (loyaltyDetail != null) {
								double employeeMonthlyContribution = loyaltyDetail.getEmployeeYearlyDeduction()
										.doubleValue() / 12;

								double companyMonthlyContribution = loyaltyDetail.getCompanyYearlyDeduction()
										.doubleValue() / 12;

								planEmployeeDetail.setLoyaltyLevelDetail(loyaltyDetail);

								if (BenefitsConstants.INS_PLAN_TYPE_FAMILY_POOL_COVERAGE.equals(plan.getPlanType())) {
									planEmployeeDetail.setSumInsured(plan.getYearlyCoverage());
								} else if (BenefitsConstants.INS_PLAN_TYPE_INDIVIDUAL_COVERAGE
										.equals(plan.getPlanType())) {
									if (loyaltyDetail != null) {
										planEmployeeDetail.setSumInsured(plan.getYearlyCoverage());
									}
								}

								if (loyaltyDetail != null) {
									planEmployeeDetail.setYearlyCmpDeduction(
											new BigDecimal(companyMonthlyContribution * diffMonth));
									planEmployeeDetail.setYearlyEmpDeduction(
											new BigDecimal(employeeMonthlyContribution * diffMonth));
								}
								for (int j = 0; j < eaicEnrolledDependentsArray.length; j++) {
									if (eaicEnrolledDependentsArray[j].equals(insEnrolledDependentsArray[i])) {
										planEmployeeDetail.setEaicEnrolled(Boolean.TRUE);
										planEmployeeDetail.setYearlyEaicDeduction(
												new BigDecimal(eaicMonthlyDeduction * diffMonth));
										planEmployeeDetail.setEaicSumInsured(plan.getEaicYearlyCoverage());
										eaicTotalDeduction = eaicTotalDeduction.add(plan.getEaicYearlyDeduction());
										eaicEnrolled = Boolean.TRUE;
									}
								}

								if (planEmployeeDetail.getEaicEnrolled() == null) {
									planEmployeeDetail.setEaicEnrolled(Boolean.FALSE);
								}
							} else { /*
										 * loyalty level detail not exists
										 */
								INSPlanDepDetails planDetail = insPlanEmployeeService.getPlanDetail(plan.getInsPlanId(),
										dependent.getRelationship());

								double employeeMonthlyContribution = planDetail.getEmpYearlyDeduction().doubleValue()
										/ 12;

								double companyMonthlyContribution = planDetail.getCmpYearlyDeduction().doubleValue()
										/ 12;

								if (BenefitsConstants.INS_PLAN_TYPE_FAMILY_POOL_COVERAGE.equals(plan.getPlanType())) {
									planEmployeeDetail.setSumInsured(plan.getYearlyCoverage());
								} else if (BenefitsConstants.INS_PLAN_TYPE_INDIVIDUAL_COVERAGE
										.equals(plan.getPlanType())) {
									if (planDetail != null) {
										planEmployeeDetail.setSumInsured(planDetail.getYearlyCoverage());
									}
								}

								if (planDetail != null) {
									planEmployeeDetail.setYearlyCmpDeduction(
											new BigDecimal(companyMonthlyContribution * diffMonth));
									planEmployeeDetail.setYearlyEmpDeduction(
											new BigDecimal(employeeMonthlyContribution * diffMonth));
								}
								for (int j = 0; j < eaicEnrolledDependentsArray.length; j++) {
									if (eaicEnrolledDependentsArray[j].equals(insEnrolledDependentsArray[i])) {
										planEmployeeDetail.setEaicEnrolled(Boolean.TRUE);
										planEmployeeDetail.setYearlyEaicDeduction(
												new BigDecimal(eaicMonthlyDeduction * diffMonth));
										planEmployeeDetail.setEaicSumInsured(plan.getEaicYearlyCoverage());
										eaicTotalDeduction = eaicTotalDeduction.add(plan.getEaicYearlyDeduction());
										eaicEnrolled = Boolean.TRUE;
									}
								}

								if (planEmployeeDetail.getEaicEnrolled() == null) {
									planEmployeeDetail.setEaicEnrolled(Boolean.FALSE);
								}

							}
							planEmployeeDetail.setCreatedBy(appContext.getUserName());
							planEmployeeDetail.setUpdatedBy(appContext.getUserName());
							planEmployeeDetail.setCreatedDate(auditDate);
							planEmployeeDetail.setUpdatedDate(auditDate);

							planEmployeeDetails.add(planEmployeeDetail);
						}
					}

					planEmployee.setDetails(planEmployeeDetails);
					if (eaicEnrolled) {
						planEmployee.setYearlyEaicDeduction(eaicTotalDeduction);
						planEmployee.setEaicSumInsured(plan.getEaicYearlyCoverage());
					}
					planEmployee.setUpdatedBy(appContext.getUserName());
					planEmployee.setCreatedBy(appContext.getUserName());
					planEmployee.setCreatedDate(auditDate);
					planEmployee.setUpdatedDate(auditDate);
					insPlanEmployeeService.savePlanEmployee(planEmployee);
					return "redirect:/home/myInsurancePlan/planDetails/" + planId;

				}
				/* } */
				/*
				 * failure case
				 */
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:/home/myInsurancePlan/planDetails/" + planId;
	}

	@RequestMapping(value = "/home/myInsurancePlan/planDetails/{planId}/optit", method = RequestMethod.POST)
	public String enrolPlan(@PathVariable(value = "planId") int planId, HttpServletRequest request) {

		appContext = (AppContext) request.getSession().getAttribute("appContext");
		INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(
				appContext.getCurrentEmployee().getEmployeeId(), appContext.getCurrentInsuranceFiscalYear());
		Boolean newJoineeEnrolMode = false;
		Boolean flag = false;
		try {
			newJoineeEnrolMode = (Boolean) request.getSession().getAttribute("newJoineeEnrolMode") == null ? false
					: true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ((planEmployee != null
				&& BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SAVE.equals(planEmployee.getStatus()))) {
			Date auditDate = new Date();
			INSPlan plan = insPlanEmployeeService.getPlan(planId);
			String loyaltyId = request.getParameter("loyaltyId");
			String insEnrolledDependentsString = request.getParameter("insEnrolledDeps");
			String eaicEnrolledDependentsString = request.getParameter("eaicEnrolledDeps");
			String companyYearlyDeduction = request.getParameter("cmpYearlyDeductions");
			String employeeYearlyDeduction = request.getParameter("empYearlyDeductions");
			/*
			 * planEmployees
			 * .setStatus(BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SUBMITTED);
			 * planEmployees.setSumInsured(plan.getYearlyCoverage());
			 * planEmployees.setYearlyEmpDeduction(new BigDecimal(
			 * employeeYearlyDeduction));
			 * planEmployees.setYearlyCmpDeduction(new BigDecimal(
			 * companyYearlyDeduction));
			 */
			if (insEnrolledDependentsString != null && !("".equals(insEnrolledDependentsString))) {
				if (companyYearlyDeduction != null && !("".equals(companyYearlyDeduction))
						&& employeeYearlyDeduction != null && !("".equals(employeeYearlyDeduction))) {
					String[] insEnrolledDependentsArray = insEnrolledDependentsString.split(",");
					String[] eaicEnrolledDependentsArray = eaicEnrolledDependentsString.split(",");

					/*
					 * finding validity periodsappContext = (AppContext)
					 * request.getSession().getAttribute( "appContext");
					 * INSPlanEmployee planEmployee = new INSPlanEmployee();
					 * INSPlan plan = insPlanEmployeeService.getPlan(planId);
					 * 
					 * String insEnrolledDependentsString = request
					 * .getParameter("insEnrolledDep"); String
					 * eaicEnrolledDependentsString = request
					 * .getParameter("eaicEnrolledDep"); String
					 * companyYearlyDeduction = request
					 * .getParameter("cmpYearlyDeduction"); String
					 * employeeYearlyDeduction = request
					 * .getParameter("empYearlyDeduction"); String[]
					 * insEnrolledDependentsArray = insEnrolledDependentsString
					 * .split(","); for (int i = 0; i <
					 * insEnrolledDependentsArray.length; i++) { Dependent
					 * dependent = employeeService.getDependent(Integer
					 * .parseInt(insEnrolledDependentsArray[i])); if (dependent
					 * != null) { INSPlanEmployeeDetails planEmployeeDetail =
					 * new INSPlanEmployeeDetails();
					 * planEmployeeDetail.setDependent(dependent);
					 * planEmployeeDetail.setPlanEmployee(planEmployee); }
					 * 
					 * } return "redirect:/home/myInsurancePlan"; }
					 */
					String startYearString = appContext.getCurrentInsuranceFiscalYear().split("-")[0];
					String nextYearString = "20" + appContext.getCurrentInsuranceFiscalYear().split("-")[1];

					String normalPlanValidtyPeriodString = settingsService
							.getPropertyByCode(BenefitsConstants.PROP_INS_NORMAL_VALIDITY).getPropertyValue();
					String validityFromMMDD = normalPlanValidtyPeriodString.split("~")[0];
					String validityToMMDD = normalPlanValidtyPeriodString.split("~")[1];

					String validityFromString = startYearString + "-" + validityFromMMDD;
					String validityToString = nextYearString + "-" + validityToMMDD;

					Date validityFrom = DataTypeUtil.toDateFromStringyyyyMMdd(validityFromString);
					Date validityTo = DataTypeUtil.toDateFromStringyyyyMMdd(validityToString);

					/*
					 * finding total effective months
					 */

					Integer cutOffMonth = Integer.parseInt(validityToMMDD.split("-")[0]);
					cutOffMonth--;

					Calendar startCalendar = new GregorianCalendar();
					if (newJoineeEnrolMode) {
						startCalendar.setTime(appContext.getCurrentEmployee().getDateOfJoin());
					} else {
						startCalendar.setTime(validityFrom);
					}

					Calendar endCalendar = new GregorianCalendar();
					endCalendar.setTime(validityTo);

					int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
					int diffMonth = (diffYear * 12)
							+ (endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH));

					/*
					 * EAIC monthly deduction
					 */

					double eaicMonthlyDeduction = plan.getEaicYearlyDeduction().doubleValue() / 12;

					planEmployee.setActive(Boolean.FALSE);
					planEmployee.setAmountClaimed(new BigDecimal(0));
					planEmployee.setTotalEffMonths(diffMonth);

					if (newJoineeEnrolMode) {
						planEmployee.setEffFrom(appContext.getCurrentEmployee().getDateOfJoin());
					} else {
						planEmployee.setEffFrom(validityFrom);
					}
					planEmployee.setEffTill(validityTo);
					planEmployee.setEmployee(appContext.getCurrentEmployee());
					/* planEmployee.setEnrolledDate(auditDate); */
					planEmployee.setFiscalYear(appContext.getCurrentInsuranceFiscalYear());
					planEmployee.setInsPlan(plan);
					planEmployee.setStatus(BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SUBMITTED);
					planEmployee.setSumInsured(plan.getYearlyCoverage());
					planEmployee.setYearlyEmpDeduction(new BigDecimal(employeeYearlyDeduction));
					planEmployee.setYearlyCmpDeduction(new BigDecimal(companyYearlyDeduction));

					if (loyaltyId == null || "".equals(loyaltyId)) {
						/*
						 * No Loyalty applicable
						 */
						planEmployee.setIsLoyaltyMode(Boolean.FALSE);
					} else {
						/*
						 * Loyalty applicable
						 */
						INSPlanLoyaltyLevels loyaltyLevel = insPlanEmployeeService
								.getLoyaltyLevel(Integer.parseInt(loyaltyId));

						if (loyaltyLevel != null) {
							planEmployee.setIsLoyaltyMode(Boolean.TRUE);
							planEmployee.setLoyaltyLevel(loyaltyLevel);
						}
					}

					/*
					 * creating planEmployeeDetails
					 */

					List<INSPlanEmployeeDetails> planEmployeeDetails = new ArrayList<INSPlanEmployeeDetails>();
					BigDecimal eaicTotalDeduction = new BigDecimal(0);
					Boolean eaicEnrolled = Boolean.FALSE;
					for (int i = 0; i < insEnrolledDependentsArray.length; i++) {
						Dependent dependent = employeeService
								.getDependent(Integer.parseInt(insEnrolledDependentsArray[i]));

						if (dependent != null) {
							INSPlanEmployeeDetails planEmployeeDetail = new INSPlanEmployeeDetails();
							planEmployeeDetail.setDependent(dependent);
							planEmployeeDetail.setPlanEmployee(planEmployee);

							/*
							 * checking loyalty level details exists or not
							 */
							INSPlanLoyaltyLevelDetail loyaltyDetail = null;
							if (planEmployee.getIsLoyaltyMode()) {
								/*
								 * loyalty level det/ail exists only if loyalty
								 * mode is true
								 */
								loyaltyDetail = insPlanEmployeeService.getLoyaltyDetail(
										planEmployee.getLoyaltyLevel().getInsPlanLoyaltyLevelsId(),
										dependent.getRelationship());
							}

							/*
							 * loyalty level detail exists
							 */
							if (loyaltyDetail != null) {
								double employeeMonthlyContribution = loyaltyDetail.getEmployeeYearlyDeduction()
										.doubleValue() / 12;

								double companyMonthlyContribution = loyaltyDetail.getCompanyYearlyDeduction()
										.doubleValue() / 12;

								planEmployeeDetail.setLoyaltyLevelDetail(loyaltyDetail);

								if (BenefitsConstants.INS_PLAN_TYPE_FAMILY_POOL_COVERAGE.equals(plan.getPlanType())) {
									planEmployeeDetail.setSumInsured(plan.getYearlyCoverage());
								} else if (BenefitsConstants.INS_PLAN_TYPE_INDIVIDUAL_COVERAGE
										.equals(plan.getPlanType())) {
									if (loyaltyDetail != null) {
										planEmployeeDetail.setSumInsured(plan.getYearlyCoverage());
									}
								}

								if (loyaltyDetail != null) {
									planEmployeeDetail.setYearlyCmpDeduction(
											new BigDecimal(companyMonthlyContribution * 12));
									planEmployeeDetail.setYearlyEmpDeduction(
											new BigDecimal(employeeMonthlyContribution * 12));
								}
								for (int j = 0; j < eaicEnrolledDependentsArray.length; j++) {
									if (eaicEnrolledDependentsArray[j].equals(insEnrolledDependentsArray[i])) {
										planEmployeeDetail.setEaicEnrolled(Boolean.TRUE);
										planEmployeeDetail.setYearlyEaicDeduction(
												new BigDecimal(eaicMonthlyDeduction * 12));
										planEmployeeDetail.setEaicSumInsured(plan.getEaicYearlyCoverage());
										eaicTotalDeduction = eaicTotalDeduction.add(plan.getEaicYearlyDeduction());
										eaicEnrolled = Boolean.TRUE;
									}
								}

								if (planEmployeeDetail.getEaicEnrolled() == null) {
									planEmployeeDetail.setEaicEnrolled(Boolean.FALSE);
								}
							} else { /*
										 * loyalty level detail not exists
										 */
								INSPlanDepDetails planDetail = insPlanEmployeeService.getPlanDetail(plan.getInsPlanId(),
										dependent.getRelationship());

								double employeeMonthlyContribution = planDetail.getEmpYearlyDeduction().doubleValue()
										/ 12;

								double companyMonthlyContribution = planDetail.getCmpYearlyDeduction().doubleValue()
										/ 12;

								if (BenefitsConstants.INS_PLAN_TYPE_FAMILY_POOL_COVERAGE.equals(plan.getPlanType())) {
									planEmployeeDetail.setSumInsured(plan.getYearlyCoverage());
								} else if (BenefitsConstants.INS_PLAN_TYPE_INDIVIDUAL_COVERAGE
										.equals(plan.getPlanType())) {
									if (planDetail != null) {
										planEmployeeDetail.setSumInsured(planDetail.getYearlyCoverage());
									}
								}

								if (planDetail != null) {
									planEmployeeDetail.setYearlyCmpDeduction(
											new BigDecimal(companyMonthlyContribution * 12));
									planEmployeeDetail.setYearlyEmpDeduction(
											new BigDecimal(employeeMonthlyContribution * 12));
								}
								for (int j = 0; j < eaicEnrolledDependentsArray.length; j++) {
									if (eaicEnrolledDependentsArray[j].equals(insEnrolledDependentsArray[i])) {
										planEmployeeDetail.setEaicEnrolled(Boolean.TRUE);
										planEmployeeDetail.setYearlyEaicDeduction(
												new BigDecimal(eaicMonthlyDeduction * 12));
										planEmployeeDetail.setEaicSumInsured(plan.getEaicYearlyCoverage());
										eaicTotalDeduction = eaicTotalDeduction.add(plan.getEaicYearlyDeduction());
										eaicEnrolled = Boolean.TRUE;
									}
								}

								if (planEmployeeDetail.getEaicEnrolled() == null) {
									planEmployeeDetail.setEaicEnrolled(Boolean.FALSE);
								}

							}
							planEmployeeDetail.setCreatedBy(appContext.getUserName());
							planEmployeeDetail.setUpdatedBy(appContext.getUserName());
							planEmployeeDetail.setCreatedDate(auditDate);
							planEmployeeDetail.setUpdatedDate(auditDate);

							planEmployeeDetails.add(planEmployeeDetail);
						}

					}

					planEmployee.setDetails(planEmployeeDetails);

					if (eaicEnrolled) {
						planEmployee.setYearlyEaicDeduction(eaicTotalDeduction);
						planEmployee.setEaicSumInsured(plan.getEaicYearlyCoverage());
					}

					if (planEmployee.getCreatedBy() == null) {
						planEmployee.setCreatedBy(appContext.getUserName());
					}

					if (planEmployee.getCreatedDate() == null) {
						planEmployee.setCreatedDate(auditDate);
					}

					planEmployee.setUpdatedBy(appContext.getUserName());
					planEmployee.setUpdatedDate(auditDate);
					planEmployee.setEnrolledDate(auditDate);

					if (insPlanEmployeeService.updatePlanEmployee(planEmployee, true)) {
						EmailService emailService = null;
						String emailSubject = "Group Health Insurance - Enrollment Requested for FY ";
						String emailBody = EmailFormatter.insPlanEnrollRequested(planEmployee);

						emailService = new EmailService(planEmployee.getEmployee().getEmail(),
								EmailProperties.getProperty(BenefitsConstants.PROP_INS_DESK_EMAIL), emailBody,
								emailSubject

										+ appContext.getCurrentInsuranceFiscalYear());

						Thread emailThread = new Thread(emailService);
						emailThread.start();
					}

					return "redirect:/home/myInsurancePlan";
				}
			}
		} else {
			Date auditDate = new Date();

			INSPlan plan = insPlanEmployeeService.getPlan(planId);

			String loyaltyId = request.getParameter("loyaltyId");
			String insEnrolledDependentsString = request.getParameter("insEnrolledDeps");
			String eaicEnrolledDependentsString = request.getParameter("eaicEnrolledDeps");
			String companyYearlyDeduction = request.getParameter("cmpYearlyDeductions");
			String employeeYearlyDeduction = request.getParameter("empYearlyDeductions");

			if (insEnrolledDependentsString != null && !("".equals(insEnrolledDependentsString))) {
				if (companyYearlyDeduction != null && !("".equals(companyYearlyDeduction))
						&& employeeYearlyDeduction != null && !("".equals(employeeYearlyDeduction))) {
					String[] insEnrolledDependentsArray = insEnrolledDependentsString.split(",");
					String[] eaicEnrolledDependentsArray = eaicEnrolledDependentsString.split(",");

					/*
					 * finding validity periodsappContext = (AppContext)
					 * request.getSession().getAttribute( "appContext");
					 * INSPlanEmployee planEmployee = new INSPlanEmployee();
					 * INSPlan plan = insPlanEmployeeService.getPlan(planId);
					 * 
					 * String insEnrolledDependentsString = request
					 * .getParameter("insEnrolledDep"); String
					 * eaicEnrolledDependentsString = request
					 * .getParameter("eaicEnrolledDep"); String
					 * companyYearlyDeduction = request
					 * .getParameter("cmpYearlyDeduction"); String
					 * employeeYearlyDeduction = request
					 * .getParameter("empYearlyDeduction"); String[]
					 * insEnrolledDependentsArray = insEnrolledDependentsString
					 * .split(","); for (int i = 0; i <
					 * insEnrolledDependentsArray.length; i++) { Dependent
					 * dependent = employeeService.getDependent(Integer
					 * .parseInt(insEnrolledDependentsArray[i])); if (dependent
					 * != null) { INSPlanEmployeeDetails planEmployeeDetail =
					 * new INSPlanEmployeeDetails();
					 * planEmployeeDetail.setDependent(dependent);
					 * planEmployeeDetail.setPlanEmployee(planEmployee); }
					 * 
					 * } return "redirect:/home/myInsurancePlan"; }
					 */
					String startYearString = appContext.getCurrentInsuranceFiscalYear().split("-")[0];
					String nextYearString = "20" + appContext.getCurrentInsuranceFiscalYear().split("-")[1];

					String normalPlanValidtyPeriodString = settingsService
							.getPropertyByCode(BenefitsConstants.PROP_INS_NORMAL_VALIDITY).getPropertyValue();
					String validityFromMMDD = normalPlanValidtyPeriodString.split("~")[0];
					String validityToMMDD = normalPlanValidtyPeriodString.split("~")[1];

					String validityFromString = startYearString + "-" + validityFromMMDD;
					String validityToString = nextYearString + "-" + validityToMMDD;

					Date validityFrom = DataTypeUtil.toDateFromStringyyyyMMdd(validityFromString);
					Date validityTo = DataTypeUtil.toDateFromStringyyyyMMdd(validityToString);

					/*
					 * finding total effective months
					 */

					Integer cutOffMonth = Integer.parseInt(validityToMMDD.split("-")[0]);
					cutOffMonth--;

					Calendar startCalendar = new GregorianCalendar();
					if (newJoineeEnrolMode) {
						startCalendar.setTime(appContext.getCurrentEmployee().getDateOfJoin());
					} else {
						startCalendar.setTime(validityFrom);
					}

					Calendar endCalendar = new GregorianCalendar();
					endCalendar.setTime(validityTo);

					int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
					int diffMonth = (diffYear * 12)
							+ (endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH));

					/*
					 * EAIC monthly deduction
					 */

					double eaicMonthlyDeduction = plan.getEaicYearlyDeduction().doubleValue() / 12;

					planEmployee = new INSPlanEmployee();
					planEmployee.setActive(Boolean.FALSE);
					planEmployee.setAmountClaimed(new BigDecimal(0));
					planEmployee.setTotalEffMonths(diffMonth);

					if (newJoineeEnrolMode) {
						planEmployee.setEffFrom(appContext.getCurrentEmployee().getDateOfJoin());
					} else {
						planEmployee.setEffFrom(validityFrom);
					}
					planEmployee.setEffTill(validityTo);
					planEmployee.setEmployee(appContext.getCurrentEmployee());
					planEmployee.setEnrolledDate(auditDate);
					planEmployee.setFiscalYear(appContext.getCurrentInsuranceFiscalYear());
					planEmployee.setInsPlan(plan);
					planEmployee.setStatus(BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SUBMITTED);
					planEmployee.setSumInsured(plan.getYearlyCoverage());
					planEmployee.setYearlyEmpDeduction(new BigDecimal(employeeYearlyDeduction));
					planEmployee.setYearlyCmpDeduction(new BigDecimal(companyYearlyDeduction));

					if (loyaltyId == null || "".equals(loyaltyId)) {
						/*
						 * No Loyalty applicable
						 */
						planEmployee.setIsLoyaltyMode(Boolean.FALSE);
					} else {
						/*
						 * Loyalty applicable
						 */
						INSPlanLoyaltyLevels loyaltyLevel = insPlanEmployeeService
								.getLoyaltyLevel(Integer.parseInt(loyaltyId));

						if (loyaltyLevel != null) {
							planEmployee.setIsLoyaltyMode(Boolean.TRUE);
							planEmployee.setLoyaltyLevel(loyaltyLevel);
						}
					}

					/*
					 * creating planEmployeeDetails
					 */

					List<INSPlanEmployeeDetails> planEmployeeDetails = new ArrayList<INSPlanEmployeeDetails>();
					BigDecimal eaicTotalDeduction = new BigDecimal(0);
					Boolean eaicEnrolled = Boolean.FALSE;
					for (int i = 0; i < insEnrolledDependentsArray.length; i++) {
						Dependent dependent = employeeService
								.getDependent(Integer.parseInt(insEnrolledDependentsArray[i]));

						if (dependent != null) {
							INSPlanEmployeeDetails planEmployeeDetail = new INSPlanEmployeeDetails();
							planEmployeeDetail.setDependent(dependent);
							planEmployeeDetail.setPlanEmployee(planEmployee);

							/*
							 * checking loyalty level details exists or not
							 */
							INSPlanLoyaltyLevelDetail loyaltyDetail = null;
							if (planEmployee.getIsLoyaltyMode()) {
								/*
								 * loyalty level detail exists only if loyalty
								 * mode is true
								 */
								loyaltyDetail = insPlanEmployeeService.getLoyaltyDetail(
										planEmployee.getLoyaltyLevel().getInsPlanLoyaltyLevelsId(),
										dependent.getRelationship());
							}

							/*
							 * loyalty level detail exists
							 */
							if (loyaltyDetail != null) {
								double employeeMonthlyContribution = loyaltyDetail.getEmployeeYearlyDeduction()
										.doubleValue() / 12;

								double companyMonthlyContribution = loyaltyDetail.getCompanyYearlyDeduction()
										.doubleValue() / 12;

								planEmployeeDetail.setLoyaltyLevelDetail(loyaltyDetail);

								if (BenefitsConstants.INS_PLAN_TYPE_FAMILY_POOL_COVERAGE.equals(plan.getPlanType())) {
									planEmployeeDetail.setSumInsured(plan.getYearlyCoverage());
								} else if (BenefitsConstants.INS_PLAN_TYPE_INDIVIDUAL_COVERAGE
										.equals(plan.getPlanType())) {
									if (loyaltyDetail != null) {
										planEmployeeDetail.setSumInsured(plan.getYearlyCoverage());
									}
								}

								if (loyaltyDetail != null) {
									planEmployeeDetail.setYearlyCmpDeduction(
											new BigDecimal(companyMonthlyContribution * 12));
									planEmployeeDetail.setYearlyEmpDeduction(
											new BigDecimal(employeeMonthlyContribution * 12));
								}
								for (int j = 0; j < eaicEnrolledDependentsArray.length; j++) {
									if (eaicEnrolledDependentsArray[j].equals(insEnrolledDependentsArray[i])) {
										planEmployeeDetail.setEaicEnrolled(Boolean.TRUE);
										planEmployeeDetail.setYearlyEaicDeduction(
												new BigDecimal(eaicMonthlyDeduction * 12));
										planEmployeeDetail.setEaicSumInsured(plan.getEaicYearlyCoverage());
										eaicTotalDeduction = eaicTotalDeduction.add(plan.getEaicYearlyDeduction());
										eaicEnrolled = Boolean.TRUE;
									}
								}

								if (planEmployeeDetail.getEaicEnrolled() == null) {
									planEmployeeDetail.setEaicEnrolled(Boolean.FALSE);
								}
							} else { /*
										 * loyalty level detail not exists
										 */
								INSPlanDepDetails planDetail = insPlanEmployeeService.getPlanDetail(plan.getInsPlanId(),
										dependent.getRelationship());

								double employeeMonthlyContribution = planDetail.getEmpYearlyDeduction().doubleValue()
										/ 12;

								double companyMonthlyContribution = planDetail.getCmpYearlyDeduction().doubleValue()
										/ 12;

								if (BenefitsConstants.INS_PLAN_TYPE_FAMILY_POOL_COVERAGE.equals(plan.getPlanType())) {
									planEmployeeDetail.setSumInsured(plan.getYearlyCoverage());
								} else if (BenefitsConstants.INS_PLAN_TYPE_INDIVIDUAL_COVERAGE
										.equals(plan.getPlanType())) {
									if (planDetail != null) {
										planEmployeeDetail.setSumInsured(planDetail.getYearlyCoverage());
									}
								}

								if (planDetail != null) {
									planEmployeeDetail.setYearlyCmpDeduction(
											new BigDecimal(companyMonthlyContribution * 12));
									planEmployeeDetail.setYearlyEmpDeduction(
											new BigDecimal(employeeMonthlyContribution * 12));
								}
								for (int j = 0; j < eaicEnrolledDependentsArray.length; j++) {
									if (eaicEnrolledDependentsArray[j].equals(insEnrolledDependentsArray[i])) {
										planEmployeeDetail.setEaicEnrolled(Boolean.TRUE);
										planEmployeeDetail.setYearlyEaicDeduction(
												new BigDecimal(eaicMonthlyDeduction * 12));
										planEmployeeDetail.setEaicSumInsured(plan.getEaicYearlyCoverage());
										eaicTotalDeduction = eaicTotalDeduction.add(plan.getEaicYearlyDeduction());
										eaicEnrolled = Boolean.TRUE;
									}
								}

								if (planEmployeeDetail.getEaicEnrolled() == null) {
									planEmployeeDetail.setEaicEnrolled(Boolean.FALSE);
								}

							}
							planEmployeeDetail.setCreatedBy(appContext.getUserName());
							planEmployeeDetail.setUpdatedBy(appContext.getUserName());
							planEmployeeDetail.setCreatedDate(auditDate);
							planEmployeeDetail.setUpdatedDate(auditDate);

							planEmployeeDetails.add(planEmployeeDetail);
						}
					}

					planEmployee.setDetails(planEmployeeDetails);
					if (eaicEnrolled) {
						planEmployee.setYearlyEaicDeduction(eaicTotalDeduction);
						planEmployee.setEaicSumInsured(plan.getEaicYearlyCoverage());
					}
					planEmployee.setUpdatedBy(appContext.getUserName());
					planEmployee.setCreatedBy(appContext.getUserName());
					planEmployee.setCreatedDate(auditDate);
					planEmployee.setEnrolledDate(auditDate);
					planEmployee.setUpdatedDate(auditDate);

					if (insPlanEmployeeService.savePlanEmployee(planEmployee)) {
						/*
						 * success case
						 */

						EmailService emailService = null;
						String emailSubject = "Group Health Insurance - Enrollment Requested for FY ";

						String emailBody = EmailFormatter.insPlanEnrollRequested(planEmployee);

						emailService = new EmailService(planEmployee.getEmployee().getEmail(),
								EmailProperties.getProperty(BenefitsConstants.PROP_INS_DESK_EMAIL), emailBody,
								emailSubject

										+ appContext.getCurrentInsuranceFiscalYear());

						Thread emailThread = new Thread(emailService);
						emailThread.start();

					}
					return "redirect:/home/myInsurancePlan";
				}
			}
			/*
			 * failure case
			 */

		}

		return "redirect:/home/myInsurancePlan";
	}

	@RequestMapping(value = "/home/myInsurancePlan/enrollDetails/{planEmployeeId}", method = RequestMethod.GET)
	public ModelAndView viewEnrolledPlanDetails(@PathVariable(value = "planEmployeeId") int planEmployeeId,
			HttpServletRequest request) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = null;

		INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(planEmployeeId);

		planEmployee = insurancePlanRE.calculateClaims(planEmployee);
		double claimPercentage = 0;
		if (BenefitsConstants.INS_PLAN_TYPE_FAMILY_POOL_COVERAGE.equals(planEmployee.getInsPlan().getPlanType())) {
			claimPercentage = (planEmployee.getAmountClaimed().doubleValue())
					/ (planEmployee.getSumInsured().doubleValue()) * 100;
		} else if (BenefitsConstants.INS_PLAN_TYPE_INDIVIDUAL_COVERAGE
				.equals(planEmployee.getInsPlan().getPlanType())) {

		} else {
			/*
			 * I don't know what to do otherwise :-(
			 */
		}

		if (planEmployee != null) {
			mav = AuthorizationUtil.authorizeUser(appContext, "insPlanMyEnrollmentDetails");
			mav.addObject("planEmployee", planEmployee);
			mav.addObject("claimPercentage", claimPercentage);
		}

		return mav;
	}

	@RequestMapping(value = "/home/myInsurancePlan/{planEmployeeId}/preAuth/new", method = RequestMethod.GET)
	public ModelAndView showPreAuthForm(@PathVariable(value = "planEmployeeId") int planEmployeeId,
			HttpServletRequest request) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = null;
		try {
			INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(planEmployeeId);
			// Integer claimedAmount =
			// insPlanEmployeeService.getClaimAmount(planEmployeeId);
			if (planEmployee != null) {
				if (planEmployee.getEmployee().getEmployeeId()
						.equals(appContext.getCurrentEmployee().getEmployeeId())) {
					mav = AuthorizationUtil.authorizeUser(appContext, "insPreAuthForm");
					mav.addObject("adminMode", "false");
					mav.addObject("planEmployee", planEmployee);

					List<INSPlanEmployeeClaim> claims = insPlanEmployeeService.listClaimByEmployee(
							planEmployee.getEmployee().getEmployeeId(), BenefitsConstants.INS_CLAIM_STATUS_SAVED,
							planEmployee.getFiscalYear(), BenefitsConstants.INS_CLAIM_TYPE_PAF_SUBMISSION);

					PreAuthFormBean preAuthBean = null;
					String claimRefNo = null;
					String docmanUuid = null;
					if (claims != null && claims.size() > 0) {
						INSPlanEmployeeClaim claim = claims.get(0);
						preAuthBean = new PreAuthFormBean(claim.getInsPlanEmployeeClaimId());
						claimRefNo = claim.getClaimRefno();

						if (claim.getDocmanUuid() == null || "".equals(claim.getDocmanUuid())) {
							/*
							 * Generating document directory for the claim
							 */
							docmanUuid = docmanRestClient.getDocmanUuid(BenefitsWSConstants.SCREEN_NEW_INS_CLAIM,
									appContext.getUserLoginKey(), planEmployee.getInsPlan().getPlanName(), claimRefNo,
									"Employee Code", planEmployee.getEmployee().getEmployeeCode(),
									planEmployee.getEmployee().getUserName(),
									BenefitsConstants.DOC_TYPE_INS_CLAIM_DOCS);
						} else {
							docmanUuid = claim.getDocmanUuid();
						}

						InsPlanEmployeeClaimPafDetail paf = insPlanEmployeeService
								.getPafDetail(claim.getInsPlanEmployeeClaimId());

						if (paf != null) {
							if (paf.getHospital() != null) {
								Hospital hospital = insPlanEmployeeService
										.getHospital(paf.getHospital().getHospitalId());
								paf.setHospital(hospital);
							} else {
								paf.setHospital(null);
								/*
								 * paf.setOtherHospital(preAuthBean.
								 * getOtherHospital());
								 */
							}
							preAuthBean.setAmountRequired(paf.getAmountAdvanceRequired());

							preAuthBean.setComments(paf.getEmployeeComments());
							preAuthBean.setEstimatedMedicalExpense(paf.getAmountEstimatedExpense());
							preAuthBean.setFiscalYear(planEmployee.getFiscalYear());
							preAuthBean.setOtherTreatment(paf.getOtherTreatment());
							preAuthBean.setIllnessType(paf.getIllnessType());

							if (paf.getDependent() != null) {
								preAuthBean.setMemberRelationship(paf.getDependent().getRelationship());
								preAuthBean.setMemberDob(new SimpleDateFormat("dd-MMM-yyyy")
										.format(paf.getDependent().getDateOfBirth()));
								preAuthBean.setMemberId(paf.getDependent().getDependentId());
							}
							preAuthBean.setPrescriberContactNo(paf.getPrescriberContactNo());
							preAuthBean.setPrescriberEmail(paf.getPrescriberEmail());
							preAuthBean.setPrescriberName(paf.getPrescriberName());

							preAuthBean.setProContactNo(paf.getPrescriberContactNo());
							preAuthBean.setProEmail(paf.getProEmail());
							preAuthBean.setProName(paf.getProName());
							preAuthBean.setSpecialistContactNo(paf.getSpecialistContactNo());
							preAuthBean.setSpecialistEmail(paf.getSpecialistEmail());
							preAuthBean.setSpecialistName(paf.getSpecialistName());
							preAuthBean.setSpeclistServiceRequired(paf.getIsSpecialistServicesAvailed());
							if (paf.getHospital() != null) {
								preAuthBean.setState(paf.getHospital().getState());
								preAuthBean.setCity(paf.getHospital().getCity());
								preAuthBean.setHospitalId(paf.getHospital().getHospitalId());
								mav.addObject("hospitalId", preAuthBean.getHospitalId());
								mav.addObject("hospitalSelected", true);
							} else {
								preAuthBean.setState(paf.getOtherHospitalState());
								preAuthBean.setCity(paf.getOtherHospitalCity());
								preAuthBean.setOtherHospital(paf.getOtherHospital());
								mav.addObject("hospitalId", 0);
								mav.addObject("hospitalSelected", true);
							}
							if (paf.getTreatment() != null) {
								preAuthBean.setTreatmentId(paf.getTreatment().getTreatmentId());
							}
							preAuthBean.setOtherTreatment(paf.getOtherTreatment());

							mav.addObject("pafMode", "update");
						}
					} else {
						preAuthBean = new PreAuthFormBean(insPlanEmployeeService.getNextClaimId());
						claimRefNo = InsUtil.generateClaimRefNo(preAuthBean.getClaimId(),
								planEmployee.getInsPlan().getPlanName(),
								appContext.getCurrentEmployee().getEmployeeCode(), appContext.getCurrentFiscalYear());
						preAuthBean.setFiscalYear(appContext.getCurrentInsuranceFiscalYear());
						mav.addObject("hospitalId", "new");
						/*
						 * Generating document directory for the claim
						 */
						docmanUuid = docmanRestClient.getDocmanUuid(BenefitsWSConstants.SCREEN_NEW_INS_CLAIM,
								appContext.getUserLoginKey(), planEmployee.getInsPlan().getPlanName(), claimRefNo,
								"Employee Code", planEmployee.getEmployee().getEmployeeCode(),
								planEmployee.getEmployee().getUserName(), BenefitsConstants.DOC_TYPE_INS_CLAIM_DOCS);
						mav.addObject("hospitalSelected", false);
						mav.addObject("pafMode", "insert");
					}

					String uploadUrl = docmanRestClient.getUploadUrl(docmanUuid, appContext.getUserLoginKey());
					String downloadUrl = docmanRestClient.getDownloadUrl(docmanUuid, appContext.getUserLoginKey());

					mav.addObject("docUploadUrl", uploadUrl);
					mav.addObject("docDownloadUrl", downloadUrl);
					mav.addObject("docmanUuid", docmanUuid);
					mav.addObject("claimRefNo", claimRefNo);

					mav.addObject("preAuthBean", preAuthBean);

					List<Treatment> treatments = insPlanEmployeeService.listAllTreatments();
					mav.addObject("treatments", treatments);

					List<String> states = insPlanEmployeeService.listAllStates();
					List<CityWrapper> cities = insPlanEmployeeService.listAllCities();
					List<Hospital> hospitals = insPlanEmployeeService.listAllHospitals();

					mav.addObject("states", states);
					mav.addObject("cities", cities);
					mav.addObject("hospitals", hospitals);
					// mav.addObject("claimedAmount",
					// planEmployee.getSumInsured().intValue() - claimedAmount);
				} else {
					mav = new ModelAndView("redirect:/home/myInsurancePlan");
				}
			} else {
				mav = new ModelAndView("redirect:/home/myInsurancePlan");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/home/myInsurancePlan/{planEmployeeId}/preAuth/new", method = RequestMethod.POST)
	public String saveSubmitPreAuthForm(@PathVariable(value = "planEmployeeId") int planEmployeeId,
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("preAuthBean") PreAuthFormBean pafBean) {

		appContext = (AppContext) request.getSession().getAttribute("appContext");

		String action = request.getParameter("formAction");
		String pafMode = request.getParameter("pafMode");
		String docmanUuid = request.getParameter("docmanUuid");

		INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(planEmployeeId);

		Dependent member = employeeService.getDependent(pafBean.getMemberId());
		Treatment treatment = insPlanEmployeeService.getTreatment(pafBean.getTreatmentId());
		Hospital hospital = insPlanEmployeeService.getHospital(pafBean.getHospitalId());

		auditDate = new Date();

		String claimRefNo = InsUtil.generateClaimRefNo(pafBean.getClaimId(), planEmployee.getInsPlan().getPlanName(),
				appContext.getCurrentEmployee().getEmployeeCode(), appContext.getCurrentFiscalYear());

		INSPlanEmployeeClaim claim = new INSPlanEmployeeClaim();
		claim.setInsPlanEmployeeClaimId(pafBean.getClaimId());
		claim.setClaimRefno(claimRefNo);
		claim.setClaimType(BenefitsConstants.INS_CLAIM_TYPE_PAF_SUBMISSION);
		claim.setApproved(Boolean.FALSE);
		claim.setApprovedDate(null);
		claim.setBalanceAmount(new BigDecimal(0));
		claim.setFiscalYear(appContext.getCurrentInsuranceFiscalYear());
		claim.setPlanEmployee(planEmployee);
		claim.setTotalReqAmount(pafBean.getAmountRequired());
		claim.setTotalApprovedAmount(new BigDecimal(0));
		claim.setDocmanUuid(docmanUuid);
		claim.setBillSubmitted(false);
		claim.setUpdatedDate(new Date());
		claim.setCreatedDate(new Date());
		claim.setUpdatedBy(appContext.getUserName());
		claim.setCreatedBy(appContext.getUserName());

		/*
		 * PAF details
		 */

		InsPlanEmployeeClaimPafDetail paf = null;

		if ("update".equals(pafMode)) {
			paf = insPlanEmployeeService.getPafDetail(claim.getInsPlanEmployeeClaimId());
		} else {
			paf = new InsPlanEmployeeClaimPafDetail();
		}

		paf.setClaim(claim);
		paf.setAmountAdvanceRequired(pafBean.getAmountRequired());
		paf.setAmountEstimatedExpense(pafBean.getEstimatedMedicalExpense());

		paf.setDependent(member);
		paf.setTreatment(treatment);
		paf.setOtherTreatment(pafBean.getOtherTreatment());
		paf.setHospital(hospital);
		paf.setOtherHospital(pafBean.getOtherHospital());
		if (hospital == null) {
			paf.setOtherHospitalCity(pafBean.getCity());
			paf.setOtherHospitalState(pafBean.getState());
		}

		paf.setEmployeeComments(pafBean.getComments());
		paf.setIllnessType(pafBean.getIllnessType());

		paf.setPafFiledBy(appContext.getUserName());
		paf.setPafFiledbyRole(appContext.getRole());

		paf.setPrescriberName(pafBean.getPrescriberName());
		paf.setPrescriberEmail(pafBean.getPrescriberEmail());
		paf.setPrescriberContactNo(pafBean.getPrescriberContactNo());

		paf.setIsSpecialistServicesAvailed(pafBean.getSpeclistServiceRequired());
		paf.setSpecialistName(pafBean.getSpecialistName());
		paf.setSpecialistEmail(pafBean.getSpecialistEmail());
		paf.setSpecialistContactNo(pafBean.getSpecialistContactNo());

		paf.setProName(pafBean.getProName());
		paf.setProEmail(pafBean.getProEmail());
		paf.setProContactNo(pafBean.getProContactNo());
		paf.setIsPAF(true);

		paf.setUpdatedBy(appContext.getUserName());

		if ("save".equals(action)) {
			claim.setRequestedDate(null);
			claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SAVED);
			paf.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SAVED);
		} else if ("submit".equals(action)) {
			claim.setRequestedDate(auditDate);
			claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);
			paf.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);
		}
		INSClaimReportPdfWriter writer = new INSClaimReportPdfWriter();
		String fileName = null;
		try {
			fileName = writer.write(paf);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ("update".equals(pafMode)) {
			/*
			 * update
			 */
			paf.setUpdatedBy(appContext.getUserName());
			claim.setUpdatedBy(appContext.getUserName());

			if (insPlanEmployeeService.updateInsClaim(claim, paf)) {

				if (paf.getStatus().equals(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED)) {
					// Sending Email

					EmailService emailService = null;
					String messageSubject = "Group Health Insurance - PAF Recieved for FY "
							+ appContext.getCurrentInsuranceFiscalYear();
					String messageBody = EmailFormatter.insNewClaimRequestSubm(claim);
					String cc = EmailProperties.getProperty(BenefitsConstants.PROP_INS_DESK_EMAIL);

					emailService = new EmailService(claim.getPlanEmployee().getEmployee().getEmail(), cc, messageBody,
							messageSubject, fileName, true);
					System.out.println("+++++++++++++++++Email Sending Started+++++++++++++++");

					Thread emailThread = new Thread(emailService);
					emailThread.start();
				}
				return "redirect://home/myInsurancePlan";
			}
		} else if ("insert".equals(pafMode)) {
			paf.setCreatedBy(appContext.getUserName());
			if (insPlanEmployeeService.savePafClaim(claim, paf)) {
				if (paf.getStatus().equals(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED)) {
					// Sending Email

					EmailService emailService = null;
					String messageSubject = "Group Health Insurance - PAF Recieved for FY "
							+ appContext.getCurrentInsuranceFiscalYear();
					String messageBody = EmailFormatter.insNewClaimRequestSubm(claim);

					emailService = new EmailService(claim.getPlanEmployee().getEmployee().getEmail(),
							EmailProperties.getProperty(BenefitsConstants.PROP_INS_DESK_EMAIL), messageBody,
							messageSubject, fileName, true);
					System.out.println("+++++++++++++++++Email Sending Started+++++++++++++++");

					Thread emailThread = new Thread(emailService);
					emailThread.start();
				}
				return "redirect://home/myInsurancePlan";
			}
		}

		return null;
	}

	@RequestMapping(value = "/home/myInsurancePlan/{planEmployeeId}/viewClaims")
	public ModelAndView viewMyClaims(@PathVariable("planEmployeeId") Integer planEmployeeId, HttpServletRequest request,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "viewMyInsClaims");

		INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(planEmployeeId);

		if (planEmployee != null) {

			planEmployee = insurancePlanRE.calculateClaims(planEmployee);
			double amountClaimed = 0;
			double sumInsured = 1;

			if (planEmployee.getAmountClaimed() != null) {
				amountClaimed = planEmployee.getAmountClaimed().doubleValue();
			}

			if (planEmployee.getSumInsured() != null) {
				sumInsured = planEmployee.getSumInsured().doubleValue();
			}

			double claimPercentage = 0;
			if (BenefitsConstants.INS_PLAN_TYPE_FAMILY_POOL_COVERAGE.equals(planEmployee.getInsPlan().getPlanType())) {
				claimPercentage = (amountClaimed / sumInsured);
			} else if (BenefitsConstants.INS_PLAN_TYPE_INDIVIDUAL_COVERAGE
					.equals(planEmployee.getInsPlan().getPlanType())) {

			} else {
				/*
				 * I don't know what to do otherwise :-(
				 */
			}

			if (planEmployee.getEmployee().getEmployeeId().equals(appContext.getCurrentEmployee().getEmployeeId())) {
				mav = AuthorizationUtil.authorizeUser(appContext, "viewMyInsClaims");
				List<INSPlanEmployeeClaim> claims = insPlanEmployeeService.listAllClaims(planEmployeeId);
				List<INSPlanEmployeeClaim> savedClaim = new ArrayList<INSPlanEmployeeClaim>();
				List<INSPlanEmployeeClaim> otherClaims = new ArrayList<INSPlanEmployeeClaim>();
				mav.addObject("savedExits", false);
				for (INSPlanEmployeeClaim claim : claims) {
					if (claim.getStatus().equals(BenefitsConstants.INS_CLAIM_STATUS_SAVED)) {
						savedClaim.add(claim);
						mav.addObject("savedExits", true);
					} else {
						otherClaims.add(claim);
					}
				}
				mav.addObject("claims", otherClaims);
				mav.addObject("savedClaims", savedClaim);
				mav.addObject("claimPercentage", claimPercentage);
				mav.addObject("planEmployeeID", planEmployeeId);
			} else {
				mav = AuthorizationUtil.authorizeUser(appContext, "redirect:/home/myInsurancePlan");
			}
		} else {
			mav = AuthorizationUtil.authorizeUser(appContext, "redirect:/home/myInsurancePlan");
		}

		return mav;

	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees", method = RequestMethod.GET)
	public ModelAndView viewInsuracneEnrolledEmployees(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewInsuranceEnrolledEmployees");

		List<INSPlanEmployee> insurancePlanEmployees = new ArrayList<INSPlanEmployee>();
		SearchBean bean = new SearchBean();

		bean.setFiscalYear(" ");
		bean.setStatus(" ");
		mav.addObject("bean", bean);
		System.out.println("------------------Insurance Employeeessss - - - - - " + insurancePlanEmployees);
		mav.addObject("insurancePlanEmployees", insurancePlanEmployees);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees", method = RequestMethod.POST)
	public ModelAndView searchOptedemployees(HttpServletRequest request, @ModelAttribute("bean") SearchBean bean,
			HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewInsuranceEnrolledEmployees");

		List<INSPlanEmployee> planEmployees = insPlanEmployeeService.listPlanEmployees(bean.getFiscalYear(),
				bean.getStatus());
		System.out.println("****************bean *******" + bean);
		mav.addObject("bean", bean);
		request.getSession().removeAttribute("preAuthBean");
		mav.addObject("insurancePlanEmployees", planEmployees);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/approvalView/{insPlanEmployeeId}", method = RequestMethod.GET)
	public ModelAndView approvalViewInsuranceEmployee(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "insPlanEmployeeId") Integer insPlanEmployeeId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "approvalViewInsuranceEnrolledEmployees");

		INSPlanEmployee insPlanEmployee = insPlanEmployeeService.getPlanEmployee(insPlanEmployeeId);

		mav.addObject("insPlanEmployee", insPlanEmployee);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/approve/{insPlanEmployeeId}", method = RequestMethod.GET)
	public ModelAndView approveInsuranceEnrolledEmployee(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "insPlanEmployeeId") Integer insPlanEmployeeId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"redirect:/home/controlPanel/insurancePlans/optedEmployees");
		INSPlanEmployee insPlanEmployee = insPlanEmployeeService.getPlanEmployee(insPlanEmployeeId);
		insPlanEmployee.setStatus(BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_HR_APPROVED);
		insPlanEmployee.setActive(Boolean.TRUE);
		if (insPlanEmployeeService.updatePlanEmployee(insPlanEmployee)) {
			// Sending Email

			EmailService emailService = null;
			String messageSubject = "Group Health Insurance - Enrollment Approved for FY "
					+ appContext.getCurrentInsuranceFiscalYear();
			String messageBody = EmailFormatter.insPlanEnrollApprove(insPlanEmployee);

			emailService = new EmailService(insPlanEmployee.getEmployee().getEmail(), "", messageBody, messageSubject);
			System.out.println("+++++++++++++++++Email Sending Started+++++++++++++++");

			Thread emailThread = new Thread(emailService);
			emailThread.start();

			System.out.println("----------------Update Plan employee status in progress ------");

		}

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/approveSelected", method = RequestMethod.GET)
	public ModelAndView approveSelectedInsuranceEmployeeDetails(HttpServletRequest request,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"redirect:/home/controlPanel/insurancePlans/optedEmployees");
		String selectedIDs = request.getParameter("approveSelected");
		System.out.println("++++++++++++++++++++++++++++++" + selectedIDs + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		String[] empIDs = selectedIDs.split(",");

		Integer insEmpID = Integer.parseInt(empIDs[0]);

		for (String ids : empIDs) {
			Integer planEmployeeIds = Integer.parseInt(ids);

			INSPlanEmployee insPlanEmployee = insPlanEmployeeService.getPlanEmployee(planEmployeeIds);

			insPlanEmployee.setStatus(BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_HR_APPROVED);
			insPlanEmployee.setActive(Boolean.TRUE);
			System.out.println("----------------------update begins ----------------" + insPlanEmployee.getStatus());
			insPlanEmployeeService.updatePlanEmployee(insPlanEmployee);
			System.out.println("----------------------update end ----------------");

			// Sending Email

			EmailService emailService = null;
			String messageSubject = "Group Health Insurance - Enrollment Approved for FY "
					+ appContext.getCurrentInsuranceFiscalYear();
			String messageBody = EmailFormatter.insPlanEnrollApprove(insPlanEmployee);

			emailService = new EmailService(insPlanEmployee.getEmployee().getEmail(), "", messageBody, messageSubject);
			System.out.println("+++++++++++++++++Email Sending Started+++++++++++++++");

			Thread emailThread = new Thread(emailService);
			emailThread.start();

		}

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/viewDetails/{insPlanEmployeeId}", method = RequestMethod.GET)
	public ModelAndView viewInsuranceEmployeeDetails(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "insPlanEmployeeId") Integer insPlanEmployeeId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewInsuranceEnrolledEmployeeDetails");

		INSPlanEmployee insPlanEmployee = insPlanEmployeeService.getPlanEmployee(insPlanEmployeeId);

		mav.addObject("insPlanEmployee", insPlanEmployee);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/reject/{insPlanEmployeeId}", method = RequestMethod.GET)
	public ModelAndView rejectInsuranceEnrolledEmployee(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "insPlanEmployeeId") Integer insPlanEmployeeId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"redirect:/home/controlPanel/insurancePlans/optedEmployees");
		INSPlanEmployee insPlanEmployee = insPlanEmployeeService.getPlanEmployee(insPlanEmployeeId);
		insPlanEmployee.setStatus(BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_REJECT);

		insPlanEmployeeService.updatePlanEmployee(insPlanEmployee);

		mav.addObject("insPlanEmployee", insPlanEmployee);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/{planEmployeeId}/paf/new", method = RequestMethod.GET)
	public ModelAndView newPreAuthFromByHR(@PathVariable("planEmployeeId") Integer planEmployeeId,
			HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = null;
		try {
			INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(planEmployeeId);
			// Integer claimedAmount =
			// insPlanEmployeeService.getClaimAmount(planEmployeeId);
			if (planEmployee != null) {
				mav = AuthorizationUtil.authorizeAdmin(appContext, "insPreAuthForm");
				mav.addObject("adminMode", "true");
				mav.addObject("planEmployee", planEmployee);

				List<INSPlanEmployeeClaim> claims = insPlanEmployeeService.listClaimByEmployee(
						planEmployee.getEmployee().getEmployeeId(), BenefitsConstants.INS_CLAIM_STATUS_SAVED,
						planEmployee.getFiscalYear(), BenefitsConstants.INS_CLAIM_TYPE_PAF_SUBMISSION);

				PreAuthFormBean preAuthBean = null;
				String claimRefNo = null;
				String docmanUuid = null;
				if (claims != null && claims.size() > 0) {
					INSPlanEmployeeClaim claim = claims.get(0);
					preAuthBean = new PreAuthFormBean(claim.getInsPlanEmployeeClaimId());
					claimRefNo = claim.getClaimRefno();

					if (claim.getDocmanUuid() == null || "".equals(claim.getDocmanUuid())) {
						/*
						 * Generating document directory for the claim
						 */
						docmanUuid = docmanRestClient.getDocmanUuid(BenefitsWSConstants.SCREEN_NEW_INS_CLAIM,
								appContext.getUserLoginKey(), planEmployee.getInsPlan().getPlanName(), claimRefNo,
								"Employee Code", planEmployee.getEmployee().getEmployeeCode(),
								planEmployee.getEmployee().getUserName(), BenefitsConstants.DOC_TYPE_INS_CLAIM_DOCS);
					} else {
						docmanUuid = claim.getDocmanUuid();
					}

					InsPlanEmployeeClaimPafDetail paf = insPlanEmployeeService
							.getPafDetail(claim.getInsPlanEmployeeClaimId());

					if (paf != null) {

						if (paf.getHospital() != null) {
							Hospital hospital = insPlanEmployeeService.getHospital(paf.getHospital().getHospitalId());
							paf.setHospital(hospital);
						} else {
							paf.setHospital(null);
							/*
							 * paf.setOtherHospital(preAuthBean.getOtherHospital
							 * ());
							 */
						}
						preAuthBean.setAmountRequired(paf.getAmountAdvanceRequired());

						preAuthBean.setComments(paf.getEmployeeComments());
						preAuthBean.setEstimatedMedicalExpense(paf.getAmountEstimatedExpense());
						preAuthBean.setFiscalYear(planEmployee.getFiscalYear());

						preAuthBean.setIllnessType(paf.getIllnessType());
						preAuthBean.setMemberDob(
								new SimpleDateFormat("dd-MMM-yyyy").format(paf.getDependent().getDateOfBirth()));
						preAuthBean.setMemberId(paf.getDependent().getDependentId());
						if (paf.getDependent() != null) {
							preAuthBean.setMemberRelationship(paf.getDependent().getRelationship());
						}
						preAuthBean.setPrescriberContactNo(paf.getPrescriberContactNo());
						preAuthBean.setPrescriberEmail(paf.getPrescriberEmail());
						preAuthBean.setPrescriberName(paf.getPrescriberName());

						preAuthBean.setProContactNo(paf.getPrescriberContactNo());
						preAuthBean.setProEmail(paf.getProEmail());
						preAuthBean.setProName(paf.getProName());
						preAuthBean.setSpecialistContactNo(paf.getSpecialistContactNo());
						preAuthBean.setSpecialistEmail(paf.getSpecialistEmail());
						preAuthBean.setSpecialistName(paf.getSpecialistName());
						preAuthBean.setSpeclistServiceRequired(paf.getIsSpecialistServicesAvailed());
						if (paf.getHospital() != null) {
							preAuthBean.setState(paf.getHospital().getState());
							preAuthBean.setCity(paf.getHospital().getCity());
							preAuthBean.setHospitalId(paf.getHospital().getHospitalId());
							mav.addObject("hospitalId", preAuthBean.getHospitalId());
							mav.addObject("hospitalSelected", true);
						} else {
							preAuthBean.setState(paf.getOtherHospitalState());
							preAuthBean.setCity(paf.getOtherHospitalCity());
							preAuthBean.setOtherHospital(paf.getOtherHospital());
							preAuthBean.setHospitalId(0);
							mav.addObject("hospitalSelected", true);
							mav.addObject("hospitalId", '0');
						}
						if (paf.getTreatment() != null) {
							preAuthBean.setTreatmentId(paf.getTreatment().getTreatmentId());
						}
						preAuthBean.setOtherTreatment(paf.getOtherTreatment());

						mav.addObject("pafMode", "update");
					}
				} else {
					preAuthBean = new PreAuthFormBean(insPlanEmployeeService.getNextClaimId());
					claimRefNo = InsUtil.generateClaimRefNo(preAuthBean.getClaimId(),
							planEmployee.getInsPlan().getPlanName(), appContext.getCurrentEmployee().getEmployeeCode(),
							appContext.getCurrentInsuranceFiscalYear());
					preAuthBean.setFiscalYear(appContext.getCurrentInsuranceFiscalYear());
					mav.addObject("hospitalId", "new");

					/*
					 * Generating document directory for the claim
					 */
					docmanUuid = docmanRestClient.getDocmanUuid(BenefitsWSConstants.SCREEN_NEW_INS_CLAIM,
							appContext.getUserLoginKey(), planEmployee.getInsPlan().getPlanName(), claimRefNo,
							"Employee Code", planEmployee.getEmployee().getEmployeeCode(),
							planEmployee.getEmployee().getUserName(), BenefitsConstants.DOC_TYPE_INS_CLAIM_DOCS);

					mav.addObject("pafMode", "insert");
					mav.addObject("hospitalSelected", false);
				}

				String uploadUrl = docmanRestClient.getUploadUrl(docmanUuid, appContext.getUserLoginKey());
				String downloadUrl = docmanRestClient.getDownloadUrl(docmanUuid, appContext.getUserLoginKey());

				mav.addObject("docUploadUrl", uploadUrl);
				mav.addObject("docDownloadUrl", downloadUrl);
				mav.addObject("docmanUuid", docmanUuid);

				mav.addObject("claimRefNo", claimRefNo);

				mav.addObject("preAuthBean", preAuthBean);

				List<Treatment> treatments = insPlanEmployeeService.listAllTreatments();
				mav.addObject("treatments", treatments);

				List<String> states = insPlanEmployeeService.listAllStates();
				List<CityWrapper> cities = insPlanEmployeeService.listAllCities();
				List<Hospital> hospitals = insPlanEmployeeService.listAllHospitals();

				mav.addObject("states", states);
				mav.addObject("cities", cities);
				mav.addObject("hospitals", hospitals);
				// mav.addObject("claimedAmount",
				// planEmployee.getSumInsured().intValue() - claimedAmount);

			} else {
				mav = AuthorizationUtil.authorizeAdmin(appContext,
						"redirect:/home/controlPanel/insurancePlans/optedEmployees/");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	/**
	 * 
	 * 
	 * Allows to search for claims
	 * 
	 * @param
	 * @param
	 * @retuns ModelAndView
	 * 
	 */
	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims", method = RequestMethod.GET)
	public ModelAndView showSearchedClaimsPage(HttpServletRequest request, HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "insSearchClaims");

		InsuranceSearchBean bean = new InsuranceSearchBean();
		mav.addObject("bean", bean);

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims", method = RequestMethod.POST)
	public ModelAndView searchedClaims(HttpServletRequest request, @ModelAttribute("bean") InsuranceSearchBean bean,
			HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "insSearchClaims");

		mav.addObject("role", appContext.getRole());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String fromDate = null;
		String toDate = null;
		try {
			if (bean.getFromDate() != null && !bean.getFromDate().equals("")) {

				fromDate = dateFormat.format(dateFormat.parse(bean.getFromDate()));

			}
			if (bean.getToDate() != null && !bean.getToDate().equals("")) {
				toDate = dateFormat.format(dateFormat.parse(bean.getToDate()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<INSPlanEmployeeClaim> claims = insuranceService.listAll(bean.getEmpCode(), bean.getFiscalYear(),
				bean.getClaimType(), fromDate, toDate, bean.getClaimRefNo());

		mav.addObject("claims", claims);
		mav.addObject("appContext", appContext);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/PAFHRApprove/{claimId}", method = RequestMethod.GET)
	public ModelAndView showApproveClaims(HttpServletRequest request, @PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "pafINSApprove");
		PreAuthFormBean preAuthBean = new PreAuthFormBean();

		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
		InsPlanEmployeeClaimPafDetail claimPafDetails = insPlanEmployeeService.getPafDetail(claimId);

		preAuthBean.setClaimId(claim.getInsPlanEmployeeClaimId());

		preAuthBean.setAmountRequired(claimPafDetails.getAmountAdvanceRequired());

		if (claimPafDetails.getHospital() != null) {
			preAuthBean.setState(claimPafDetails.getHospital().getState());
			preAuthBean.setHospitalId(claimPafDetails.getHospital().getHospitalId());
			preAuthBean.setCity(claimPafDetails.getHospital().getCity());
			mav.addObject("otherHospital", false);

		} else {
			preAuthBean.setState(claimPafDetails.getOtherHospitalState());
			preAuthBean.setCity(claimPafDetails.getOtherHospitalCity());
			preAuthBean.setOtherHospital(claimPafDetails.getOtherHospital());
			mav.addObject("otherHospital", true);
		}
		preAuthBean.setComments(claimPafDetails.getEmployeeComments());
		preAuthBean.setEstimatedMedicalExpense(claimPafDetails.getAmountEstimatedExpense());
		preAuthBean.setFiscalYear(claim.getFiscalYear());
		preAuthBean.setIllnessType(claimPafDetails.getIllnessType());
		preAuthBean.setOtherTreatment(claimPafDetails.getOtherTreatment());
		try {
			preAuthBean.setMemberDob(
					new SimpleDateFormat("dd-MMM-yyyy").format(claimPafDetails.getDependent().getDateOfBirth()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		preAuthBean.setMemberId(claimPafDetails.getDependent().getDependentId());
		preAuthBean.setMemberRelationship(claimPafDetails.getDependent().getRelationship());
		preAuthBean.setPrescriberContactNo(claimPafDetails.getPrescriberContactNo());
		preAuthBean.setPrescriberEmail(claimPafDetails.getPrescriberEmail());
		preAuthBean.setPrescriberName(claimPafDetails.getPrescriberName());

		preAuthBean.setProContactNo(claimPafDetails.getPrescriberContactNo());
		preAuthBean.setProEmail(claimPafDetails.getProEmail());
		preAuthBean.setProName(claimPafDetails.getProName());
		preAuthBean.setSpecialistContactNo(claimPafDetails.getSpecialistContactNo());
		preAuthBean.setSpecialistEmail(claimPafDetails.getSpecialistEmail());
		preAuthBean.setSpecialistName(claimPafDetails.getSpecialistName());
		preAuthBean.setSpeclistServiceRequired(claimPafDetails.getIsSpecialistServicesAvailed());
		if (claimPafDetails.getTreatment() != null) {
			preAuthBean.setTreatmentId(claimPafDetails.getTreatment().getTreatmentId());
		} else {
			preAuthBean.setOtherTreatment(claimPafDetails.getOtherTreatment());

		}

		mav.addObject("preAuthBean", preAuthBean);

		mav.addObject("claim", claim);
		mav.addObject("claimDetails", claimPafDetails);
		mav.addObject("planEmployee", claim.getPlanEmployee());
		return mav;
	}

	/**
	 * 
	 * HR Approves an insurance PAF claim request
	 * 
	 * @param
	 * @param
	 * @retuns String
	 * 
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/PAFHRApprove/{claimId}/approve", method = RequestMethod.POST)
	public String approveClaimINS(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		Date auditDate = new Date();

		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
		String comments = request.getParameter("hrcomments");
		InsPlanEmployeeClaimPafDetail claimPafDetails = insPlanEmployeeService.getPafDetail(claimId);
		claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_HR_APPROVED);
		claim.setUpdatedBy(appContext.getUserName());
		claim.setApproved(true);
		claim.setTotalApprovedAmount(new BigDecimal(request.getParameter("totalApproved")));
		claim.setApprovedDate(auditDate);
		claim.setBalanceAmount(claim.getTotalApprovedAmount());
		claim.setUpdatedDate(auditDate);

		if (insPlanEmployeeService.update(claim)) {
			if (claimPafDetails != null) {
				claimPafDetails.setStatus(BenefitsConstants.INS_CLAIM_STATUS_HR_APPROVED);
				claimPafDetails.setPafReviewedBy(appContext.getUserName());
				claimPafDetails.setLastApprover(BenefitsConstants.HR_ROLE_EMPLOYEE);
				claimPafDetails.setNextApprover(BenefitsConstants.USER_ROLE_INS_ADMIN);
				// claimPafDetails.setNextApprover();
				claimPafDetails.setUpdatedBy(appContext.getUserName());
				claimPafDetails.setUpdatedDate(auditDate);
				claimPafDetails.setHrComments(comments);
				/*
				 * claimPafDetails.setAmountAdvanceApproved(new
				 * BigDecimal(request .getParameter("totalAdvanceApproved")));
				 */
				claimPafDetails.setAmountTotalApproved(new BigDecimal(request.getParameter("totalApproved")));
				claimPafDetails.setAmountApprovedInstallment1(new BigDecimal(request.getParameter("ins1")));
				claimPafDetails.setAmountApprovedInstallment2(new BigDecimal(request.getParameter("ins2")));
				insPlanEmployeeService.update(claimPafDetails);

			}

			EmailService emailService = null;
			String messageSubject = "Benefits Portal - Insurance PAF Insurance Desk Verified";

			String messageBody = EmailFormatter.insClaimRequestIDVerify(claim);

			emailService = new EmailService(EmailProperties.getProperty(BenefitsConstants.PROP_INS_COMMITTE_EMAIL), "",
					messageBody, messageSubject);

			System.out.println("----------------Sending email---------------");
			Thread emailThread = new Thread(emailService);
			emailThread.start();
		}

		return "redirect:/home/controlPanel/insurancePlans/searchClaims";

	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/PAFHRApprove/reject/{claimId}", method = RequestMethod.GET)
	public String rejectClaim(HttpServletRequest request, @PathVariable("claimId") Integer claimId,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		Date auditDate = new Date();

		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
		InsPlanEmployeeClaimPafDetail claimPafDetails = insPlanEmployeeService.getPafDetail(claimId);
		claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_HR_REJECTED);
		claim.setUpdatedBy(appContext.getUserName());
		claim.setUpdatedDate(auditDate);
		if (insPlanEmployeeService.update(claim)) {

			EmailService emailService = null;
			String messageSubject = "Benefits Portal - PAF Insurance Desk Rejected";

			String messageBody = EmailFormatter.insClaimRequestHRReject(claim);

			emailService = new EmailService(claim.getPlanEmployee().getEmployee().getEmail(), "", messageBody,
					messageSubject);

			System.out.println("----------------Sending email---------------");
			Thread emailThread = new Thread(emailService);
			emailThread.start();

			if (claimPafDetails != null) {
				claimPafDetails.setHrComments(request.getParameter("hrcomments"));
				claimPafDetails.setStatus(BenefitsConstants.INS_CLAIM_STATUS_HR_REJECTED);
				claimPafDetails.setPafReviewedBy(appContext.getUserName());
				claimPafDetails.setLastApprover(BenefitsConstants.HR_ROLE_EMPLOYEE);
				claimPafDetails.setNextApprover(BenefitsConstants.USER_ROLE_INS_ADMIN);
				claimPafDetails.setUpdatedBy(appContext.getUserName());
				claimPafDetails.setUpdatedDate(auditDate);
				claimPafDetails.setPafReviewedByRole(appContext.getRole());
				insPlanEmployeeService.update(claimPafDetails);

			}

		}

		return "redirect:/home/controlPanel/insurancePlans/searchClaims";

	}

	/*
	 * insurance committe approval
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/PAFINSApprove/{claimId}", method = RequestMethod.GET)
	public ModelAndView showApproveClaimsINS(HttpServletRequest request, @PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "pAFHRApprove");
		PreAuthFormBean preAuthBean = new PreAuthFormBean();

		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
		InsPlanEmployeeClaimPafDetail claimPafDetails = insPlanEmployeeService.getPafDetail(claimId);

		preAuthBean.setClaimId(claim.getInsPlanEmployeeClaimId());
		preAuthBean.setAmountRequired(claimPafDetails.getAmountAdvanceRequired());
		preAuthBean.setAmountApproved(claimPafDetails.getAmountTotalApproved());
		if (claimPafDetails.getHospital() != null) {
			preAuthBean.setState(claimPafDetails.getHospital().getState());
			preAuthBean.setHospitalId(claimPafDetails.getHospital().getHospitalId());
			preAuthBean.setCity(claimPafDetails.getHospital().getCity());
			mav.addObject("otherHospital", false);

		} else {
			preAuthBean.setOtherHospital(claimPafDetails.getOtherHospital());
			preAuthBean.setCity(claimPafDetails.getOtherHospitalCity());
			preAuthBean.setState(claimPafDetails.getOtherHospitalState());
			mav.addObject("otherHospital", true);
		}
		preAuthBean.setComments(claimPafDetails.getEmployeeComments());
		preAuthBean.setHrComments(claimPafDetails.getHrComments());
		preAuthBean.setEstimatedMedicalExpense(claimPafDetails.getAmountEstimatedExpense());
		preAuthBean.setFiscalYear(claim.getFiscalYear());
		preAuthBean.setIllnessType(claimPafDetails.getIllnessType());
		preAuthBean.setOtherTreatment(claimPafDetails.getOtherTreatment());
		preAuthBean.setMemberDob(
				new SimpleDateFormat("dd-MMM-yyyy").format(claimPafDetails.getDependent().getDateOfBirth()));
		preAuthBean.setMemberId(claimPafDetails.getDependent().getDependentId());
		preAuthBean.setMemberRelationship(claimPafDetails.getDependent().getRelationship());
		preAuthBean.setPrescriberContactNo(claimPafDetails.getPrescriberContactNo());
		preAuthBean.setPrescriberEmail(claimPafDetails.getPrescriberEmail());
		preAuthBean.setPrescriberName(claimPafDetails.getPrescriberName());

		preAuthBean.setProContactNo(claimPafDetails.getPrescriberContactNo());
		preAuthBean.setProEmail(claimPafDetails.getProEmail());
		preAuthBean.setProName(claimPafDetails.getProName());
		preAuthBean.setSpecialistContactNo(claimPafDetails.getSpecialistContactNo());
		preAuthBean.setSpecialistEmail(claimPafDetails.getSpecialistEmail());
		preAuthBean.setSpecialistName(claimPafDetails.getSpecialistName());
		preAuthBean.setSpeclistServiceRequired(claimPafDetails.getIsSpecialistServicesAvailed());

		if (claimPafDetails.getTreatment() != null) {
			preAuthBean.setTreatmentId(claimPafDetails.getTreatment().getTreatmentId());
		} else {
			preAuthBean.setOtherTreatment(claimPafDetails.getOtherTreatment());

		}

		mav.addObject("amt1", claimPafDetails.getAmountApprovedInstallment1());
		mav.addObject("amt2", claimPafDetails.getAmountApprovedInstallment2());
		mav.addObject("preAuthBean", preAuthBean);

		mav.addObject("claim", claim);
		mav.addObject("claimDetails", claimPafDetails);
		mav.addObject("planEmployee", claim.getPlanEmployee());
		return mav;
	}

	/**
	 * 
	 * ins Approves an insurance PAF claim request
	 * 
	 * @param
	 * @param
	 * @retuns String
	 * 
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/PAFINSApprove/{claimId}/approve", method = RequestMethod.POST)
	public String approveClaim(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		Date auditDate = new Date();

		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
		String comments = request.getParameter("inscomments");
		InsPlanEmployeeClaimPafDetail claimPafDetails = insPlanEmployeeService.getPafDetail(claimId);
		claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_APPROVED);
		claim.setUpdatedBy(appContext.getUserName());
		claim.setTotalPaidAmt(BigDecimal.ZERO);
		claim.setUpdatedDate(auditDate);
		if (insPlanEmployeeService.update(claim)) {
			if (claimPafDetails != null) {
				claimPafDetails.setStatus(BenefitsConstants.INS_CLAIM_STATUS_APPROVED);
				claimPafDetails.setPafReviewedBy(appContext.getUserName());
				claimPafDetails.setLastApprover(BenefitsConstants.USER_ROLE_INS_ADMIN);
				claimPafDetails.setNextApprover(null);
				claimPafDetails.setUpdatedBy(appContext.getUserName());
				claimPafDetails.setUpdatedDate(auditDate);
				claimPafDetails.setInsDeskComments(comments);
				claimPafDetails.setPafReviewedByRole(appContext.getRole());
				claimPafDetails.setAmount1Status(false);
				claimPafDetails.setAmount2Status(false);

				insPlanEmployeeService.update(claimPafDetails);

			}

			EmailService emailService = null;
			String messageSubject = "Benefits Portal - PAF Insurance Committe Approved";

			String messageBody = EmailFormatter.insClaimRequestInsCommitteeVerify(claim);

			emailService = new EmailService(EmailProperties.getProperty(BenefitsConstants.PROP_FINANCE_MAIL),
					EmailProperties.getProperty(BenefitsConstants.PROP_INS_COMMITTE_EMAIL), messageBody,
					messageSubject);

			System.out.println("----------------Sending email---------------");
			Thread emailThread = new Thread(emailService);
			emailThread.start();

		}
		return "redirect:/home/controlPanel/insurancePlans/searchClaims";

	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/PAFINSApprove/reject/{claimId}", method = RequestMethod.GET)
	public String rejectClaimINS(HttpServletRequest request, @PathVariable("claimId") Integer claimId,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		Date auditDate = new Date();

		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
		InsPlanEmployeeClaimPafDetail claimPafDetails = insPlanEmployeeService.getPafDetail(claimId);
		claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_REJECTED);

		claim.setUpdatedBy(appContext.getUserName());
		claim.setUpdatedDate(auditDate);

		if (insPlanEmployeeService.update(claim)) {

			EmailService emailService = null;
			String messageSubject = "Benefits Portal - PAF Insurance Committe Rejected";

			String messageBody = EmailFormatter.insClaimRequestIDReject(claim);

			emailService = new EmailService(claim.getPlanEmployee().getEmployee().getEmail(), "", messageBody,
					messageSubject);

			System.out.println("----------------Sending email---------------");
			Thread emailThread = new Thread(emailService);
			emailThread.start();

			if (claimPafDetails != null) {
				claimPafDetails.setInsDeskComments(request.getParameter("insComments"));
				claimPafDetails.setStatus(BenefitsConstants.INS_CLAIM_STATUS_REJECTED);
				claimPafDetails.setPafReviewedBy(appContext.getUserName());
				claimPafDetails.setLastApprover(BenefitsConstants.USER_ROLE_INS_ADMIN);
				// claimPafDetails.setNextApprover(BenefitsConstants.USER_ROLE_INS_ADMIN);
				claimPafDetails.setUpdatedBy(appContext.getUserName());
				claimPafDetails.setUpdatedDate(auditDate);
				insPlanEmployeeService.update(claimPafDetails);
			}
		}
		return "redirect:/home/controlPanel/insurancePlans/searchClaims";
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/{planEmployeeId}/paf/new", method = RequestMethod.POST)
	public String savePreAuthFormByHR(@PathVariable("planEmployeeId") Integer planEmployeeId,
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("preAuthBean") PreAuthFormBean pafBean) {

		String action = request.getParameter("formAction");
		String pafMode = request.getParameter("pafMode");

		INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(planEmployeeId);

		Dependent member = employeeService.getDependent(pafBean.getMemberId());
		Treatment treatment = insPlanEmployeeService.getTreatment(pafBean.getTreatmentId());
		Hospital hospital = insPlanEmployeeService.getHospital(pafBean.getHospitalId());

		auditDate = new Date();

		String claimRefNo = InsUtil.generateClaimRefNo(pafBean.getClaimId(), planEmployee.getInsPlan().getPlanName(),
				planEmployee.getEmployee().getEmployeeCode(), appContext.getCurrentFiscalYear());

		INSPlanEmployeeClaim claim = new INSPlanEmployeeClaim();
		claim.setInsPlanEmployeeClaimId(pafBean.getClaimId());
		claim.setClaimRefno(claimRefNo);
		claim.setClaimType(BenefitsConstants.INS_CLAIM_TYPE_PAF_SUBMISSION);
		claim.setApproved(Boolean.FALSE);
		claim.setApprovedDate(null);
		claim.setBalanceAmount(new BigDecimal(0));
		claim.setFiscalYear(appContext.getCurrentInsuranceFiscalYear());
		claim.setPlanEmployee(planEmployee);
		claim.setTotalReqAmount(pafBean.getAmountRequired());
		claim.setTotalApprovedAmount(new BigDecimal(0));
		claim.setBillSubmitted(false);
		claim.setUpdatedDate(new Date());
		claim.setUpdatedBy(appContext.getUserName());
		claim.setCreatedDate(new Date());
		claim.setCreatedBy(appContext.getUserName());

		/*
		 * PAF details
		 */

		InsPlanEmployeeClaimPafDetail paf = null;

		if ("update".equals(pafMode)) {
			paf = insPlanEmployeeService.getPafDetail(claim.getInsPlanEmployeeClaimId());
		} else {
			paf = new InsPlanEmployeeClaimPafDetail();
		}

		paf.setClaim(claim);
		paf.setAmountAdvanceRequired(pafBean.getAmountRequired());
		paf.setAmountEstimatedExpense(pafBean.getEstimatedMedicalExpense());

		paf.setDependent(member);
		paf.setTreatment(treatment);
		paf.setOtherTreatment(pafBean.getOtherTreatment());
		if (hospital == null) {
			paf.setOtherHospital(pafBean.getOtherHospital());
			paf.setOtherHospitalCity(pafBean.getCity());
			paf.setOtherHospitalState(pafBean.getState());
		}
		paf.setHospital(hospital);
		paf.setEmployeeComments(pafBean.getComments());
		paf.setIllnessType(pafBean.getIllnessType());

		paf.setPafFiledBy(appContext.getUserName());
		paf.setPafFiledbyRole(appContext.getRole());

		paf.setPrescriberName(pafBean.getPrescriberName());
		paf.setPrescriberEmail(pafBean.getPrescriberEmail());
		paf.setPrescriberContactNo(pafBean.getPrescriberContactNo());

		paf.setIsSpecialistServicesAvailed(pafBean.getSpeclistServiceRequired());
		paf.setSpecialistName(pafBean.getSpecialistName());
		paf.setSpecialistEmail(pafBean.getSpecialistEmail());
		paf.setSpecialistContactNo(pafBean.getSpecialistContactNo());

		paf.setProName(pafBean.getProName());
		paf.setProEmail(pafBean.getProEmail());
		paf.setProContactNo(pafBean.getProContactNo());

		paf.setUpdatedBy(appContext.getUserName());

		if ("save".equals(action)) {
			claim.setRequestedDate(null);
			claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SAVED);
			paf.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SAVED);
		} else if ("submit".equals(action)) {
			claim.setRequestedDate(auditDate);
			claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);
			paf.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);
		}
		INSClaimReportPdfWriter writer = new INSClaimReportPdfWriter();
		String fileName = null;
		try {
			fileName = writer.write(paf);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if ("update".equals(pafMode)) {
			/*
			 * update
			 */
			paf.setUpdatedBy(appContext.getUserName());
			claim.setUpdatedBy(appContext.getUserName());

			if (insPlanEmployeeService.updateInsClaim(claim, paf)) {

				if (paf.getStatus().equals(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED)) {
					// Sending Email

					EmailService emailService = null;
					String messageSubject = "Group Health Insurance - PAF Recieved for FY "
							+ appContext.getCurrentInsuranceFiscalYear();
					String messageBody = EmailFormatter.insNewClaimRequestSubm(claim);
					String cc = EmailProperties.getProperty(BenefitsConstants.PROP_INS_DESK_EMAIL);

					emailService = new EmailService(claim.getPlanEmployee().getEmployee().getEmail(), cc, messageBody,
							messageSubject, fileName, true);
					System.out.println("+++++++++++++++++Email Sending Started+++++++++++++++");

					Thread emailThread = new Thread(emailService);
					emailThread.start();
				}
				return "redirect:/home/controlPanel/insurancePlans/optedEmployees";
			}
		} else if ("insert".equals(pafMode)) {
			paf.setCreatedBy(appContext.getUserName());
			if (insPlanEmployeeService.savePafClaim(claim, paf)) {
				if (paf.getStatus().equals(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED)) {
					// Sending Email

					EmailService emailService = null;
					String messageSubject = "Group Health Insurance - PAF Recieved for FY "
							+ appContext.getCurrentInsuranceFiscalYear();
					String messageBody = EmailFormatter.insNewClaimRequestSubm(claim);

					emailService = new EmailService(claim.getPlanEmployee().getEmployee().getEmail(),
							EmailProperties.getProperty(BenefitsConstants.PROP_INS_DESK_EMAIL), messageBody,
							messageSubject, fileName, true);
					System.out.println("+++++++++++++++++Email Sending Started+++++++++++++++");

					Thread emailThread = new Thread(emailService);
					emailThread.start();
				}
				return "redirect:/home/controlPanel/insurancePlans/optedEmployees";
			}
		}

		return null;
	}

	/*
	 * // Bills Approval
	 * 
	 * @RequestMapping(value =
	 * "/home/controlPanel/insurancePlans/searchClaims/billsHRApprove/{claimId}"
	 * , method = RequestMethod.GET) public ModelAndView
	 * showApproveBills(HttpServletRequest request,
	 * 
	 * @PathVariable("claimId") Integer claimId) { appContext = (AppContext)
	 * request.getSession().getAttribute( "appContext"); ModelAndView mav =
	 * AuthorizationUtil.authorizeAdmin(appContext, "insHRApprove");
	 * PreAuthFormBean preAuthBean = new PreAuthFormBean();
	 * 
	 * INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
	 * 
	 * List<InsPlanEmployeeClaimBill> claimBills = insPlanEmployeeService
	 * .listAllBillsByClaimId(claimId);
	 * 
	 * InsPlanEmployeeClaimPafDetail claimPafDetails = new
	 * InsPlanEmployeeClaimPafDetail();
	 * 
	 * preAuthBean.setClaimId(claim.getInsPlanEmployeeClaimId());
	 * preAuthBean.setAmountRequired(claimPafDetails
	 * .getAmountAdvanceRequired());
	 * preAuthBean.setCity(claimPafDetails.getHospital().getCity());
	 * preAuthBean.setComments(claimPafDetails.getEmployeeComments());
	 * preAuthBean.setEstimatedMedicalExpense(claimPafDetails
	 * .getAmountEstimatedExpense());
	 * preAuthBean.setFiscalYear(claim.getFiscalYear()); preAuthBean
	 * .setHospitalId(claimPafDetails.getHospital().getHospitalId());
	 * preAuthBean.setIllnessType(claimPafDetails.getIllnessType());
	 * preAuthBean.setMemberDob(new SimpleDateFormat("dd-MMM-yyyy")
	 * .format(claimPafDetails.getDependent().getDateOfBirth())); preAuthBean
	 * .setMemberId(claimPafDetails.getDependent().getDependentId());
	 * preAuthBean.setMemberRelationship(claimPafDetails.getDependent()
	 * .getRelationship()); preAuthBean.setPrescriberContactNo(claimPafDetails
	 * .getPrescriberContactNo());
	 * preAuthBean.setPrescriberEmail(claimPafDetails.getPrescriberEmail());
	 * preAuthBean.setPrescriberName(claimPafDetails.getPrescriberName());
	 * 
	 * preAuthBean.setProContactNo(claimPafDetails.getPrescriberContactNo());
	 * preAuthBean.setProEmail(claimPafDetails.getProEmail());
	 * preAuthBean.setProName(claimPafDetails.getProName());
	 * preAuthBean.setSpecialistContactNo(claimPafDetails
	 * .getSpecialistContactNo());
	 * preAuthBean.setSpecialistEmail(claimPafDetails.getSpecialistEmail());
	 * preAuthBean.setSpecialistName(claimPafDetails.getSpecialistName());
	 * preAuthBean.setSpeclistServiceRequired(claimPafDetails
	 * .getIsSpecialistServicesAvailed());
	 * preAuthBean.setState(claimPafDetails.getHospital().getState());
	 * preAuthBean.setTreatmentId(claimPafDetails.getTreatment()
	 * .getTreatmentId());
	 * 
	 * INSBillDetailBean bean = new INSBillDetailBean(); List<INSBillDetailBean>
	 * beans = new ArrayList<INSBillDetailBean>();
	 * 
	 * for (InsPlanEmployeeClaimBill bill : claimBills) {
	 * List<InsPlanEmployeeClaimBillDetail> billDetails = insPlanEmployeeService
	 * .listAllDetailsByClaimId(bill.getClaimBillId()); bean.setClaimBill(bill);
	 * bean.setBillDetails(billDetails);
	 * 
	 * beans.add(bean); } preAuthBean.setBean(beans);
	 * request.getSession().setAttribute("preAuthBean", preAuthBean);
	 * mav.addObject("preAuthBean", preAuthBean);
	 * 
	 * mav.addObject("claim", claim); mav.addObject("claimDetails",
	 * claimPafDetails); mav.addObject("planEmployee", claim.getPlanEmployee());
	 * return mav; }
	 * 
	 * @RequestMapping(value =
	 * "/home/controlPanel/insurancePlans/searchClaims/billsHRApprove/billDetail/{billId}"
	 * , method = RequestMethod.GET) public ModelAndView showBillDetail(
	 * 
	 * @PathVariable(value = "billId") int billId, HttpServletRequest request,
	 * HttpServletResponse response) {
	 * 
	 * ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
	 * "insBillsDetails");
	 * 
	 * PreAuthFormBean preAuthBean = (PreAuthFormBean) request.getSession()
	 * .getAttribute("preAuthBean");
	 * 
	 * List<InsPlanEmployeeClaimBillDetail> detail = null;
	 * 
	 * InsPlanEmployeeClaimBill bill = null;
	 * 
	 * List<INSBillDetailBean> beans = preAuthBean.getBean();
	 * 
	 * for (INSBillDetailBean bean2 : beans) { if
	 * (bean2.getClaimBill().getClaimBillId() == billId) { bill =
	 * bean2.getClaimBill(); detail = bean2.getBillDetails(); break; }
	 * 
	 * }
	 * 
	 * mav.addObject("bill", bill); mav.addObject("billDetails", detail);
	 * 
	 * return mav;
	 * 
	 * }
	 * 
	 * @RequestMapping(value =
	 * "/home/controlPanel/insurancePlans/searchClaims/billsHRApprove/billDetail/{billId}"
	 * , method = RequestMethod.POST) public String saveBillDetails(
	 * 
	 * @PathVariable(value = "billId") int billId, HttpServletRequest request,
	 * HttpServletResponse response) {
	 * 
	 * ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
	 * "insBillsDetails");
	 * 
	 * PreAuthFormBean preAuthBean = (PreAuthFormBean) request.getSession()
	 * .getAttribute("preAuthBean");
	 * 
	 * List<INSBillDetailBean> detail = null;
	 * 
	 * InsPlanEmployeeClaimBill bill = null;
	 * 
	 * List<InsPlanEmployeeClaimBillDetail> billDetails1 = new
	 * ArrayList<InsPlanEmployeeClaimBillDetail>();
	 * 
	 * List<INSBillDetailBean> beans = preAuthBean.getBean(); BigDecimal
	 * totAmtApproved = new BigDecimal( request.getParameter("totAmt")); String
	 * bilDetailIds = request.getParameter("approveIds"); String[] ids =
	 * bilDetailIds.split(",");
	 * 
	 * for (int i = 0; i < ids.length - 1; i++) { InsPlanEmployeeClaimBillDetail
	 * billDetail = insPlanEmployeeService
	 * .getBillDetailById(Integer.parseInt(ids[i])); bill =
	 * billDetail.getClaimBill(); if (billDetail != null) { billDetail
	 * .setStatus(BenefitsConstants.INS_CLAIM_STATUS_APPROVED);
	 * billDetail.setUpdatedBy(appContext.getUserName());
	 * billDetail.setUpdatedDate(new Date()); billDetails1.add(billDetail); }
	 * 
	 * }
	 * 
	 * String rejIds = request.getParameter("rejIds"); String[] ids1 =
	 * rejIds.split(",");
	 * 
	 * for (int i = 0; i < ids1.length - 1; i++) {
	 * InsPlanEmployeeClaimBillDetail billDetail = insPlanEmployeeService
	 * .getBillDetailById(Integer.parseInt(ids1[i])); if (billDetail != null) {
	 * billDetail .setStatus(BenefitsConstants.INS_CLAIM_STATUS_REJECTED);
	 * billDetail.setUpdatedBy(appContext.getUserName());
	 * billDetail.setUpdatedDate(new Date()); billDetails1.add(billDetail); }
	 * 
	 * }
	 * 
	 * bill.setAmountApproved(totAmtApproved);
	 * bill.setHrComments(request.getParameter("hrComments")); if (ids.length >
	 * 0) { bill.setStatus(BenefitsConstants.INS_CLAIM_STATUS_APPROVED); } else
	 * { bill.setStatus(BenefitsConstants.INS_CLAIM_STATUS_REJECTED); }
	 * bill.setUpdatedBy(appContext.getUserName()); bill.setUpdatedDate(new
	 * Date());
	 * 
	 * INSBillDetailBean bean = new INSBillDetailBean();
	 * bean.setClaimBill(bill); bean.setBillDetails(billDetails1);
	 * 
	 * // replace session Bean with Updated details and store it back to //
	 * session int j = 0; for (INSBillDetailBean detailBean : beans) { if
	 * (detailBean.getClaimBill().getClaimBillId() == bill .getClaimBillId()) {
	 * beans.add(j, bean); } j++; } preAuthBean.setBean(beans);
	 * request.getSession().setAttribute("preAuthBean", preAuthBean); return
	 * "redirect:/home/controlPanel/insurancePlans/searchClaims/billsHRApprove/"
	 * + bill.getClaim().getInsPlanEmployeeClaimId();
	 * 
	 * }
	 * 
	 * 
	 * 
	 * HR Approves an insurance Bill claim request
	 * 
	 * @param
	 * 
	 * @param
	 * 
	 * @retuns String
	 * 
	 * 
	 * @RequestMapping(value =
	 * "/home/controlPanel/insurancePlans/searchClaims/billsHRApprove/approve",
	 * method = RequestMethod.POST) public String
	 * approveBills(HttpServletRequest request, HttpServletResponse response) {
	 * appContext = (AppContext) request.getSession().getAttribute(
	 * "appContext"); Date auditDate = new Date();
	 * 
	 * Integer claimId = Integer.parseInt(request.getParameter("claimId"));
	 * INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
	 * String comments = request.getParameter("hrcomments");
	 * 
	 * PreAuthFormBean preAuthBean = (PreAuthFormBean) request.getSession()
	 * .getAttribute("preAuthBean");
	 * 
	 * List<INSBillDetailBean> beans = preAuthBean.getBean();
	 * 
	 * claim.setTotalApprovedAmount(new BigDecimal(request
	 * .getParameter("totalAmount")));
	 * claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_HR_APPROVED); if
	 * (insPlanEmployeeService.update(claim)) { for (INSBillDetailBean bean :
	 * beans) { InsPlanEmployeeClaimBill bill = bean.getClaimBill();
	 * insPlanEmployeeService.updateBill(bill);
	 * List<InsPlanEmployeeClaimBillDetail> billDetails = bean
	 * .getBillDetails(); for (InsPlanEmployeeClaimBillDetail billDetail :
	 * billDetails) { insPlanEmployeeService.updateBillDetail(billDetail); } }
	 * 
	 * EmailService emailService = null; String messageSubject =
	 * "Benefits Portal - Insurance Insurance Desk Verified";
	 * 
	 * String messageBody = EmailFormatter.insClaimRequestHRVerify(claim);
	 * 
	 * emailService = new EmailService( EmailProperties
	 * .getProperty(BenefitsConstants.PROP_INS_COMMITTE_EMAIL), "", messageBody,
	 * messageSubject);
	 * 
	 * System.out.println("----------------Sending email---------------");
	 * Thread emailThread = new Thread(emailService); emailThread.start();
	 * 
	 * } return "redirect:/home/controlPanel/insurancePlans/searchClaims";
	 * 
	 * }
	 * 
	 * @RequestMapping(value =
	 * "/home/controlPanel/insurancePlans/searchClaims/billsHRApprove/reject",
	 * method = RequestMethod.GET) public String
	 * rejectBillsHR(HttpServletRequest request, HttpServletResponse response) {
	 * appContext = (AppContext) request.getSession().getAttribute(
	 * "appContext"); Date auditDate = new Date();
	 * 
	 * Integer claimId = Integer.parseInt(request.getParameter("claimId"));
	 * INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
	 * String comments = request.getParameter("hrcomments");
	 * 
	 * PreAuthFormBean preAuthBean = (PreAuthFormBean) request.getSession()
	 * .getAttribute("preAuthBean");
	 * 
	 * List<INSBillDetailBean> beans = preAuthBean.getBean();
	 * 
	 * claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_HR_REJECTED); if
	 * (insPlanEmployeeService.update(claim)) { for (INSBillDetailBean bean :
	 * beans) { InsPlanEmployeeClaimBill bill = bean.getClaimBill();
	 * bill.setStatus(BenefitsConstants.INS_CLAIM_STATUS_REJECTED);
	 * bill.setAmountApproved(BigDecimal.ZERO);
	 * insPlanEmployeeService.updateBill(bill);
	 * 
	 * List<InsPlanEmployeeClaimBillDetail> billDetails = bean
	 * .getBillDetails(); for (InsPlanEmployeeClaimBillDetail billDetail :
	 * billDetails) { billDetail
	 * .setStatus(BenefitsConstants.INS_CLAIM_STATUS_REJECTED);
	 * billDetail.setTotalCost(BigDecimal.ZERO);
	 * insPlanEmployeeService.updateBillDetail(billDetail); } }
	 * 
	 * EmailService emailService = null; String messageSubject =
	 * "Benefits Portal - Insurance Insurance Desk Rejected";
	 * 
	 * String messageBody = "";
	 * 
	 * emailService = new EmailService( EmailProperties
	 * .getProperty(BenefitsConstants.PROP_INS_COMMITTE_EMAIL), "", messageBody,
	 * messageSubject);
	 * 
	 * System.out.println("----------------Sending email---------------");
	 * Thread emailThread = new Thread(emailService); emailThread.start();
	 * 
	 * } return "redirect:/home/controlPanel/insurancePlans/searchClaims";
	 * 
	 * }
	 */

	/*
	 * 
	 * 
	 * Hr Approve Bills
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/{claimId}/billsHRApprove/new", method = RequestMethod.GET)
	public ModelAndView newBillsApproveByHR(@PathVariable("claimId") Integer claimId, HttpServletRequest request,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = null;

		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
		BigDecimal totAmt = BigDecimal.ZERO;
		if (claim != null) {
			mav = AuthorizationUtil.authorizeAdmin(appContext, "billsHRApprove");
			mav.addObject("adminMode", "true");
			mav.addObject("planEmployee", claim.getPlanEmployee());

			InsPlanEmployeeClaimPafDetail claimPafDetails = insPlanEmployeeService.getPafDetail(claimId);
			if (claimPafDetails != null) {
				PreAuthFormBean preAuthBean = null;
				String claimRefNo = null;

				preAuthBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthHRBean");
				if (preAuthBean != null && preAuthBean.getClaimId() == claimId) {
					// List<INSBillDetailBean> beans =preAuthBean.getBean();
					if (preAuthBean.getBean() != null) {

						for (INSBillDetailBean bean : preAuthBean.getBean()) {
							System.out.println("*************Bean**********" + bean);
							if (bean.getClaimBill() != null) {
								totAmt = totAmt.add(bean.getClaimBill().getAmountVerified());
							}
						}
					}
					preAuthBean.setAmountApproved(totAmt);

					mav.addObject("pafMode", "edit");

				} else {
					preAuthBean = new PreAuthFormBean(claimId);

					preAuthBean.setClaimId(claim.getInsPlanEmployeeClaimId());
					preAuthBean.setAmountRequired(claim.getTotalPaidAmt());
					if (claimPafDetails.getHospital() != null) {
						preAuthBean.setCity(claimPafDetails.getHospital().getCity());
						preAuthBean.setHospitalId(claimPafDetails.getHospital().getHospitalId());
						preAuthBean.setState(claimPafDetails.getHospital().getState());
						mav.addObject("hospitalSelected", true);
					} else {
						preAuthBean.setCity(claimPafDetails.getOtherHospitalCity());
						preAuthBean.setState(claimPafDetails.getOtherHospitalState());
						preAuthBean.setHospitalId(0);
						preAuthBean.setOtherHospital(claimPafDetails.getOtherHospital());
						mav.addObject("hospitalSelected", true);
					}
					if (claimPafDetails.getTreatment() != null) {
						preAuthBean.setTreatmentId(claimPafDetails.getTreatment().getTreatmentId());
					}

					preAuthBean.setComments(claimPafDetails.getEmployeeComments());
					preAuthBean.setEstimatedMedicalExpense(claimPafDetails.getAmountEstimatedExpense());
					preAuthBean.setFiscalYear(claim.getFiscalYear());

					preAuthBean.setIllnessType(claimPafDetails.getIllnessType());
					preAuthBean.setMemberDob(new SimpleDateFormat("dd-MMM-yyyy")
							.format(claimPafDetails.getDependent().getDateOfBirth()));
					preAuthBean.setMemberId(claimPafDetails.getDependent().getDependentId());
					preAuthBean.setMemberRelationship(claimPafDetails.getDependent().getRelationship());
					preAuthBean.setPrescriberContactNo(claimPafDetails.getPrescriberContactNo());
					preAuthBean.setPrescriberEmail(claimPafDetails.getPrescriberEmail());
					preAuthBean.setPrescriberName(claimPafDetails.getPrescriberName());

					preAuthBean.setProContactNo(claimPafDetails.getPrescriberContactNo());
					preAuthBean.setProEmail(claimPafDetails.getProEmail());
					preAuthBean.setProName(claimPafDetails.getProName());
					preAuthBean.setSpecialistContactNo(claimPafDetails.getSpecialistContactNo());
					preAuthBean.setSpecialistEmail(claimPafDetails.getSpecialistEmail());
					preAuthBean.setSpecialistName(claimPafDetails.getSpecialistName());
					preAuthBean.setSpeclistServiceRequired(claimPafDetails.getIsSpecialistServicesAvailed());

					List<INSBillDetailBean> beans = new ArrayList<INSBillDetailBean>();

					List<InsPlanEmployeeClaimBill> bills = insPlanEmployeeService.listAllBillsByClaimId(claimId);
					for (InsPlanEmployeeClaimBill bill : bills) {

						List<InsPlanEmployeeClaimBillDetail> billDetails = insPlanEmployeeService
								.listAllDetailsByClaimId(bill.getClaimBillId());
						INSBillDetailBean bean = new INSBillDetailBean();
						bean.setClaimBill(bill);
						bean.setBillDetails(billDetails);
						beans.add(bean);

					}
					preAuthBean.setBean(beans);
					preAuthBean.setIsStart(true);
					preAuthBean.setPlanEmployeeId(claim.getPlanEmployee().getInsPlanEmployeeId());
					preAuthBean.setAmountRequired(claim.getTotalReqAmount());
					preAuthBean.setAmountApproved(BigDecimal.ZERO);

					/*
					 * BigDecimal ClaimMaxAmt =
					 * planEmployee.getSumInsured().subtract
					 * (planEmployee.getAmountClaimed());
					 * 
					 * mav.addObject("ClaimMaxAmt", ClaimMaxAmt);
					 */
					mav.addObject("pafMode", "insert");

				}

				mav.addObject("claimRefNo", claim.getClaimRefno());
				mav.addObject("claimDetails", claimPafDetails);

				List<Treatment> treatments = insPlanEmployeeService.listAllTreatments();
				mav.addObject("treatments", treatments);

				List<String> states = insPlanEmployeeService.listAllStates();
				List<CityWrapper> cities = insPlanEmployeeService.listAllCities();
				List<Hospital> hospitals = insPlanEmployeeService.listAllHospitals();
				List<INSCategoryMaster> categories = insPlanEmployeeService.listAllCategories();
				List<INSBillDetailBean> beans = preAuthBean.getBean();

				mav.addObject("beans", beans);

				mav.addObject("preAuthBean", preAuthBean);
				request.getSession().setAttribute("preAuthHRBean", preAuthBean);
				mav.addObject("categories", categories);
				mav.addObject("states", states);
				mav.addObject("cities", cities);
				mav.addObject("hospitals", hospitals);
				mav.addObject("planEmployeeId1", claim.getPlanEmployee().getInsPlanEmployeeId());
			} else {
				mav = AuthorizationUtil.authorizeAdmin(appContext,
						"redirect:/home/controlPanel/insurancePlans/optedEmployees");
			}
		}

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/bills/new/billDetail/{billNo}", method = RequestMethod.GET)
	public ModelAndView approveBillDetail(@ModelAttribute("preAuthBean") PreAuthFormBean pafBean,
			@PathVariable("billNo") Integer billNo, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "insBillDetailsHRApprove");

		INSPlanEmployeeClaim claim = null;

		INSPlanEmployeeClaim billClaim = null;
		Boolean status = false;

		Dependent member = employeeService.getDependent(pafBean.getMemberId());

		INSBillDetailBean currentBean = new INSBillDetailBean();

		PreAuthFormBean preAuthBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthHRBean");

		InsPlanEmployeeClaimBill bill = new InsPlanEmployeeClaimBill();
		List<InsPlanEmployeeClaimBillDetail> detail = new ArrayList<InsPlanEmployeeClaimBillDetail>();
		List<INSBillDetailBean> currentBeans = preAuthBean.getBean();

		for (INSBillDetailBean bean2 : currentBeans) {
			if (bean2.getClaimBill().getClaimBillId().intValue() == billNo.intValue()) {
				bill = bean2.getClaimBill();
				detail = bean2.getBillDetails();
				break;
			}

		}
		BigDecimal total = bill.getAmountVerified();
		BigDecimal totalr = bill.getAmountRequested();
		BigDecimal verifiedAmt = bill.getAmountVerified();
		if (verifiedAmt != null) {
			totalr = bill.getAmountRequested().subtract(verifiedAmt);
		}

		mav.addObject("totalr", totalr);
		mav.addObject("total", total);

		List<INSCategoryMaster> categories = insPlanEmployeeService.listAllCategories();
		mav.addObject("bill", bill);
		mav.addObject("billDetails", detail);
		mav.addObject("categories", categories);

		mav.addObject("billNo", billNo);
		return mav;

	}

	/*
	 * 
	 * HR Approves an insurance Bill claim request
	 * 
	 * @param
	 * 
	 * @param
	 * 
	 * @retuns String
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/{claimId}/billsHRApprove/approve", method = RequestMethod.POST)
	public String approveBills(@ModelAttribute("PreAuthFormBean") PreAuthFormBean preAuthFormBean,
			HttpServletRequest request, HttpServletResponse response, @PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		Date auditDate = new Date();

		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
		String comments = request.getParameter("hrcomments");

		PreAuthFormBean preAuthBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthHRBean");

		List<INSBillDetailBean> beans = preAuthBean.getBean();

		claim.setTotalApprovedAmount(preAuthFormBean.getAmountApproved());
		claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_HR_APPROVED);

		if (insPlanEmployeeService.update(claim)) {
			InsPlanEmployeeClaimPafDetail claimPafDetail = insPlanEmployeeService
					.getPafDetail(claim.getInsPlanEmployeeClaimId());
			claimPafDetail.setNextApprover(BenefitsConstants.USER_ROLE_INS_ADMIN);
			claimPafDetail.setHrComments(comments);
			claimPafDetail.setHrApprovedBy(appContext.getUserName());
			insPlanEmployeeService.update(claimPafDetail);

			for (INSBillDetailBean bean : beans) {
				InsPlanEmployeeClaimBill bill = bean.getClaimBill();
				insPlanEmployeeService.updateBill(bill);
				List<InsPlanEmployeeClaimBillDetail> billDetails = bean.getBillDetails();
				for (InsPlanEmployeeClaimBillDetail billDetail : billDetails) {
					insPlanEmployeeService.updateBillDetail(billDetail);
				}
			}
			request.getSession().removeAttribute("preAuthHRBean");
			EmailService emailService = null;
			String messageSubject = "Benefits Portal - Bill Claim Request Verified by Insurance Desk";

			String messageBody = EmailFormatter.insClaimRequestIDVerify(claim);

			emailService = new EmailService(EmailProperties.getProperty(BenefitsConstants.PROP_INS_COMMITTE_EMAIL), "",
					messageBody, messageSubject);

			System.out.println("----------------Sending email---------------");
			Thread emailThread = new Thread(emailService);
			emailThread.start();

		}
		request.getSession().removeAttribute("preAuthHRBean");
		return "redirect:/home/controlPanel/insurancePlans/searchClaims";

	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/billsHRApprove/reject", method = RequestMethod.GET)
	public String rejectBillHR(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		Date auditDate = new Date();

		Integer claimId = Integer.parseInt(request.getParameter("claimId"));
		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
		String comments = request.getParameter("hrcomments");

		PreAuthFormBean preAuthBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthHRBean");

		List<INSBillDetailBean> beans = preAuthBean.getBean();

		claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_HR_REJECTED);
		if (insPlanEmployeeService.update(claim)) {

			InsPlanEmployeeClaimPafDetail claimPafDetail = insPlanEmployeeService
					.getPafDetail(claim.getInsPlanEmployeeClaimId());
			claimPafDetail.setNextApprover(BenefitsConstants.USER_ROLE_INS_ADMIN);
			claimPafDetail.setHrComments(comments);
			claimPafDetail.setHrApprovedBy(appContext.getUserName());
			insPlanEmployeeService.update(claimPafDetail);

			for (INSBillDetailBean bean : beans) {

				InsPlanEmployeeClaimBill bill = bean.getClaimBill();
				bill.setStatus(BenefitsConstants.INS_CLAIM_STATUS_REJECTED);
				bill.setAmountApproved(BigDecimal.ZERO);
				insPlanEmployeeService.updateBill(bill);

				List<InsPlanEmployeeClaimBillDetail> billDetails = bean.getBillDetails();
				for (InsPlanEmployeeClaimBillDetail billDetail : billDetails) {
					billDetail.setStatus(BenefitsConstants.INS_CLAIM_STATUS_REJECTED);
					billDetail.setTotalCost(BigDecimal.ZERO);
					insPlanEmployeeService.updateBillDetail(billDetail);
				}
			}
			request.getSession().removeAttribute("preAuthHRBean");
			EmailService emailService = null;
			String messageSubject = "Benefits Portal - Bill Claim Request Rejected by Insurance Desk";

			String messageBody = EmailFormatter.insClaimRequestIDReject(claim);

			emailService = new EmailService(
					EmailProperties.getProperty(claim.getPlanEmployee().getEmployee().getEmail()), "", messageBody,
					messageSubject);

			System.out.println("----------------Sending email---------------");
			Thread emailThread = new Thread(emailService);
			emailThread.start();

		}
		request.getSession().removeAttribute("preAuthHRBean");
		return "redirect:/home/controlPanel/insurancePlans/searchClaims";

	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/billDetailsHRApprove/new/save/{billNo}", method = RequestMethod.POST)
	public String approveBillDetail(@PathVariable("billNo") Integer billNo, HttpServletRequest request,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		List<InsPlanEmployeeClaimBillDetail> billDetails = new ArrayList<InsPlanEmployeeClaimBillDetail>();

		List<INSBillDetailBean> tempBeans = new ArrayList<INSBillDetailBean>();

		String ids = request.getParameter("apprIds");
		String[] apprids = ids.split(",");
		List<String> apprId = Arrays.asList(apprids);

		InsPlanEmployeeClaimBill bill = insPlanEmployeeService.getBillById(billNo);
		bill.setAmountVerified(new BigDecimal(request.getParameter("totalAmount")));
		bill.setStatus(BenefitsConstants.INS_CLAIM_STATUS_HR_APPROVED);
		bill.setUpdatedBy(appContext.getUserName());
		bill.setUpdatedDate(auditDate);
		PreAuthFormBean preAuthFormBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthHRBean");
		List<INSBillDetailBean> beans = preAuthFormBean.getBean();
		for (INSBillDetailBean bean : beans) {
			if (bean.getClaimBill().getClaimBillId().intValue() == billNo.intValue()) {
				for (InsPlanEmployeeClaimBillDetail billDetail2 : bean.getBillDetails()) {
					if (apprId.contains(billDetail2.getClaimBillDetailId().toString())) {

						billDetail2.setStatus(BenefitsConstants.INS_CLAIM_STATUS_HR_APPROVED);
						billDetail2.setApprovedAmount(
								new BigDecimal(request.getParameter("apprAmt" + billDetail2.getClaimBillDetailId())));
						billDetail2.setUpdatedBy(appContext.getUserName());
						billDetail2.setUpdatedDate(auditDate);
						billDetails.add(billDetail2);
					} else {

						billDetail2.setStatus(BenefitsConstants.INS_CLAIM_STATUS_HR_REJECTED);
						billDetail2.setUpdatedBy(appContext.getUserName());
						billDetail2.setUpdatedDate(auditDate);
						billDetails.add(billDetail2);
					}
				}

				bean.setBillDetails(billDetails);
				bean.setClaimBill(bill);
			}
			tempBeans.add(bean);
		}
		preAuthFormBean.setBean(tempBeans);

		request.getSession().setAttribute("preAuthHRBean", preAuthFormBean);
		return "redirect:/home/controlPanel/insurancePlans/optedEmployees/"
				+ bill.getClaim().getInsPlanEmployeeClaimId() + "/billsHRApprove/new";
	}

	/*
	 * 
	 * finished
	 */

	/*
	 * 
	 * Add new Bill for claim
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/{planEmployeeId}/bills/new", method = RequestMethod.GET)
	public ModelAndView newBillsByHR(@PathVariable("planEmployeeId") Integer planEmployeeId, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			appContext = (AppContext) request.getSession().getAttribute("appContext");
			ModelAndView mav = null;
			INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(planEmployeeId);
			// Integer claimedAmount =
			// insPlanEmployeeService.getClaimAmount(planEmployeeId);
			INSPlanEmployeeClaim claim = insPlanEmployeeService.getEmployeePAFDetails(planEmployeeId);

			BigDecimal totAmt = BigDecimal.ZERO;
			if (planEmployee != null) {
				mav = AuthorizationUtil.authorizeAdmin(appContext, "insBillsForm");
				mav.addObject("adminMode", "true");
				mav.addObject("planEmployee", planEmployee);

				PreAuthFormBean preAuthBean = null;
				String claimRefNo = null;
				preAuthBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthBean");

				if (claim != null && preAuthBean == null) {
					preAuthBean = new PreAuthFormBean();

				}
				if (claim == null) {
					claim = new INSPlanEmployeeClaim();
					mav.addObject("pafRequestedAmount", 0);
				} else {
					mav.addObject("pafRequestedAmount", claim.getTotalReqAmount().intValue());
				}

				if (preAuthBean != null) {

					if (claim.getClaimPafDetail() != null) {
						preAuthBean.setClaimId(insPlanEmployeeService.getNextClaimId());
						preAuthBean.setPlanEmployeeId(planEmployeeId);
						preAuthBean.setAmountRequired(claim.getClaimPafDetail().getAmountAdvanceRequired());
						preAuthBean.setAmountApproved(claim.getClaimPafDetail().getAmountTotalApproved());
						if (claim.getClaimPafDetail().getHospital() != null) {
							preAuthBean.setCity(claim.getClaimPafDetail().getHospital().getCity());
							preAuthBean.setState(claim.getClaimPafDetail().getHospital().getState());
							preAuthBean.setHospitalId(claim.getClaimPafDetail().getHospital().getHospitalId());
							mav.addObject("hospitalId", preAuthBean.getHospitalId());
							mav.addObject("hospitalSelected", true);
						} else {
							preAuthBean.setCity(claim.getClaimPafDetail().getOtherHospitalCity());
							preAuthBean.setState(claim.getClaimPafDetail().getOtherHospitalState());
							preAuthBean.setOtherHospital(claim.getClaimPafDetail().getOtherHospital());
							mav.addObject("hospitalId", 0);
							mav.addObject("hospitalSelected", true);
						}
						preAuthBean.setComments(claim.getClaimPafDetail().getEmployeeComments());
						preAuthBean.setHrComments(claim.getClaimPafDetail().getHrComments());
						preAuthBean.setEstimatedMedicalExpense(claim.getClaimPafDetail().getAmountEstimatedExpense());
						preAuthBean.setFiscalYear(claim.getFiscalYear());

						preAuthBean.setIllnessType(claim.getClaimPafDetail().getIllnessType());
						try {
							preAuthBean.setMemberDob(new SimpleDateFormat("dd-MMM-yyyy")
									.format(claim.getClaimPafDetail().getDependent().getDateOfBirth()));
						} catch (Exception e) {
							preAuthBean.setMemberDob(null);
						}
						preAuthBean.setMemberId(claim.getClaimPafDetail().getDependent().getDependentId());
						preAuthBean.setMemberRelationship(claim.getClaimPafDetail().getDependent().getRelationship());
						preAuthBean.setPrescriberContactNo(claim.getClaimPafDetail().getPrescriberContactNo());
						preAuthBean.setPrescriberEmail(claim.getClaimPafDetail().getPrescriberEmail());
						preAuthBean.setPrescriberName(claim.getClaimPafDetail().getPrescriberName());

						preAuthBean.setProContactNo(claim.getClaimPafDetail().getPrescriberContactNo());
						preAuthBean.setProEmail(claim.getClaimPafDetail().getProEmail());
						preAuthBean.setProName(claim.getClaimPafDetail().getProName());
						preAuthBean.setSpecialistContactNo(claim.getClaimPafDetail().getSpecialistContactNo());
						preAuthBean.setSpecialistEmail(claim.getClaimPafDetail().getSpecialistEmail());
						preAuthBean.setSpecialistName(claim.getClaimPafDetail().getSpecialistName());
						preAuthBean
								.setSpeclistServiceRequired(claim.getClaimPafDetail().getIsSpecialistServicesAvailed());

						preAuthBean.setTreatmentId(claim.getClaimPafDetail().getTreatment().getTreatmentId());
					} else {
						mav.addObject("hospitalId", preAuthBean.getHospitalId());
						mav.addObject("hospitalSelected", true);
					}

					// List<INSBillDetailBean> beans =preAuthBean.getBean();
					if (preAuthBean.getBean() != null) {
						List<INSBillDetailBean> beans = new ArrayList<INSBillDetailBean>();
						for (INSBillDetailBean bean : preAuthBean.getBean()) {
							System.out.println("*************Bean**********" + bean);
							if (bean.getClaimBill() != null) {
								if (bean.getClaimBill().getAmountRequested().intValue() != 0) {
									totAmt = totAmt.add(bean.getClaimBill().getAmountRequested());
									beans.add(bean);
								}
							}
						}
						preAuthBean.setBean(beans);

					}
					preAuthBean.setAmountRequired(totAmt);

					mav.addObject("pafMode", "edit");

				} else {
					preAuthBean = new PreAuthFormBean(insPlanEmployeeService.getNextClaimId());
					preAuthBean.setFiscalYear(appContext.getCurrentInsuranceFiscalYear());
					preAuthBean.setIsStart(true);
					preAuthBean.setPlanEmployeeId(planEmployeeId);
					preAuthBean.setAmountRequired(BigDecimal.ZERO);
					preAuthBean.setPlanEmployeeId(planEmployeeId);
					mav.addObject("hospitalSelected", false);
					mav.addObject("hospitalId", "new");
					mav.addObject("pafMode", "insert");

				}

				claimRefNo = InsUtil.generateClaimRefNo(preAuthBean.getClaimId(),
						planEmployee.getInsPlan().getPlanName(), planEmployee.getEmployee().getEmployeeCode(),
						appContext.getCurrentFiscalYear());

				mav.addObject("claimRefNo", claimRefNo);

				List<Treatment> treatments = insPlanEmployeeService.listAllTreatments();
				mav.addObject("treatments", treatments);

				List<String> states = insPlanEmployeeService.listAllStates();
				List<CityWrapper> cities = insPlanEmployeeService.listAllCities();
				List<Hospital> hospitals = insPlanEmployeeService.listAllHospitals();
				List<INSCategoryMaster> categories = insPlanEmployeeService.listAllCategories();
				List<INSBillDetailBean> beans = preAuthBean.getBean();

				mav.addObject("beans", beans);
				if (beans == null) {
					mav.addObject("beansize", 0);
				} else {
					mav.addObject("beansize", beans.size());
				}

				mav.addObject("preAuthBean", preAuthBean);
				request.getSession().setAttribute("preAuthBean", preAuthBean);
				mav.addObject("categories", categories);
				mav.addObject("states", states);
				mav.addObject("cities", cities);
				mav.addObject("hospitals", hospitals);
				mav.addObject("planEmployeeId1", planEmployeeId);
				// mav.addObject("claimedAmount",
				// planEmployee.getSumInsured().intValue() - claimedAmount);
				try {
					if (!preAuthBean.getBean().isEmpty()) {
						mav.addObject("countLoop1", preAuthBean.getBean().size() - 1);
						mav.addObject("countLoop", preAuthBean.getBean().size() - 1);

					} else {
						mav.addObject("countLoop1", 0);
						mav.addObject("countLoop", 0);
					}
				} catch (Exception e) {
					mav.addObject("countLoop1", 0);
					mav.addObject("countLoop", 0);
				}

				// mav.addObject("claim", claim);
			} else {
				mav = AuthorizationUtil.authorizeAdmin(appContext,
						"redirect:/home/controlPanel/insurancePlans/optedEmployees");
			}
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/bills/new/billDetail/{billNo}", method = RequestMethod.POST)
	public ModelAndView saveBillDetail(@ModelAttribute("preAuthBean") PreAuthFormBean pafBean,
			@PathVariable("billNo") Integer billNo, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "insBillsDetails");

		INSPlanEmployeeClaim claim = null;

		INSPlanEmployeeClaim billClaim = null;
		Boolean status = false;

		Dependent member = employeeService.getDependent(pafBean.getMemberId());

		INSBillDetailBean currentBean = new INSBillDetailBean();

		PreAuthFormBean preAuthBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthBean");

		List<INSBillDetailBean> currentBeans = new ArrayList<INSBillDetailBean>();
		List<INSBillDetailBean> newBeans = new ArrayList<INSBillDetailBean>();
		currentBeans = preAuthBean.getBean();

		Integer rowCount = Integer.parseInt(request.getParameter("rowCount"));
		List<INSBillDetailBean> beans = new ArrayList<INSBillDetailBean>();

		for (Integer i = 0; i <= rowCount; i++) {
			INSBillDetailBean bean = new INSBillDetailBean();
			Boolean isNullRow = true;
			InsPlanEmployeeClaimBill bill = new InsPlanEmployeeClaimBill();
			bill.setAmountRequested(new BigDecimal(0));
			bill.setAmountApproved(new BigDecimal(0));
			bill.setAmountVerified(new BigDecimal(0));
			String billNo1 = request.getParameter("billNo" + i);
			if (billNo1 != null && !billNo1.equals("")) {
				isNullRow = false;
				bill.setBillNo(billNo1);
			}
			String billIssuer = request.getParameter("billIssuer" + i);
			if (billIssuer != null && !billIssuer.equals("")) {
				isNullRow = false;
				bill.setBillIssuer(billIssuer);
			}

			String billDate = request.getParameter("billDate" + i);
			if (billDate != null && !billDate.equals("")) {
				isNullRow = false;
				try {
					bill.setBillDate(new SimpleDateFormat("yyyy-MM-dd").parse(billDate));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String totalAmt = request.getParameter("totalAmt" + i);
			if (totalAmt != null && !totalAmt.equals("")) {
				isNullRow = false;
				bill.setAmountRequested(new BigDecimal(totalAmt));
			}

			bill.setClaim(claim);
			bill.setClaimPaf(null);
			bill.setCreatedBy(appContext.getUserName());
			bill.setCreatedDate(new Date());
			bill.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);

			bill.setDependent(member);
			bill.setUpdatedBy(appContext.getUserName());
			if (isNullRow == false || billNo >= i) {

				bean.setClaimBill(bill);
				if (preAuthBean.getBean() != null) {

					if (preAuthBean.getBean().size() >= i + 1 && preAuthBean.getBean().get(i) != null) {
						bean.setBillDetails(preAuthBean.getBean().get(i).getBillDetails());
					}

				}
				if (billNo == i) {
					currentBean = bean;
					if (currentBean != null && currentBean.getClaimBill() != null) {
						mav.addObject("total", currentBean.getClaimBill().getAmountRequested());
					}

				}

				newBeans.add(bean);

			}

		}
		pafBean.setBean(newBeans);
		pafBean.setComments(request.getParameter("comments"));
		pafBean.setPlanEmployeeId(preAuthBean.getPlanEmployeeId());
		request.getSession().setAttribute("preAuthBean", pafBean);

		List<INSCategoryMaster> categories = insPlanEmployeeService.listAllCategories();
		if (currentBean != null) {
			mav.addObject("bean", currentBean);
		}
		mav.addObject("categories", categories);
		mav.addObject("countLoop", 0);
		try {
			mav.addObject("countLoop1", pafBean.getBean().get(billNo).getBillDetails().size());
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("countLoop1", 0);
		}

		mav.addObject("billNo", billNo);
		mav.addObject("planEmployeeId", preAuthBean.getPlanEmployeeId());
		return mav;

	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/{planEmployeeId}/bills/new/save", method = RequestMethod.POST)
	public String saveBills(@PathVariable(value = "planEmployeeId") int planEmployeeId,
			@ModelAttribute("preAuthBean") PreAuthFormBean pafBean, HttpServletRequest request,
			HttpServletResponse response) {

		PreAuthFormBean prAuthFormBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthBean");
		pafBean.setBean(prAuthFormBean.getBean());

		String comments = (String) request.getSession().getAttribute("comments");
		System.out.println(comments);
		String action = request.getParameter("formAction");
		String pafMode = request.getParameter("pafMode");

		INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(planEmployeeId);

		Dependent member = employeeService.getDependent(pafBean.getMemberId());
		Treatment treatment = insPlanEmployeeService.getTreatment(pafBean.getTreatmentId());

		auditDate = new Date();

		INSPlanEmployeeClaim claim = null;

		String claimRefNo = InsUtil.generateClaimRefNo(pafBean.getClaimId(), planEmployee.getInsPlan().getPlanName(),
				appContext.getCurrentEmployee().getEmployeeCode(), appContext.getCurrentFiscalYear());

		claim = new INSPlanEmployeeClaim();
		claim.setInsPlanEmployeeClaimId(pafBean.getClaimId());
		claim.setClaimRefno(claimRefNo);
		claim.setClaimType(BenefitsConstants.INS_CLAIM_TYPE_BILL_SUBMISSION);
		claim.setApproved(Boolean.FALSE);
		claim.setApprovedDate(null);
		claim.setBalanceAmount(new BigDecimal(0));
		/* claim.setFiscalYear(appContext.getCurrentInsuranceFiscalYear()); */
		claim.setFiscalYear(planEmployee.getFiscalYear());
		claim.setPlanEmployee(planEmployee);
		claim.setTotalReqAmount(pafBean.getAmountRequired());
		claim.setTotalApprovedAmount(new BigDecimal(0));
		claim.setTotalPaidAmt(new BigDecimal(0));
		claim.setClaimPafDetail(null);
		claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);
		claim.setCreatedBy(appContext.getUserName());
		claim.setCreatedDate(new Date());
		claim.setRequestedDate(new Date());
		claim.setBillSubmitted(true);
		claim.setUpdatedDate(new Date());
		claim.setUpdatedBy(appContext.getUserName());
		Integer claimId = (Integer) insPlanEmployeeService.insertClaim(claim);
		if (claimId != null) {

			claim.setInsPlanEmployeeClaimId(claimId);
			// Insert a paf entry to store form fields.But isPAF field will be
			// false.
			InsPlanEmployeeClaimPafDetail paf = new InsPlanEmployeeClaimPafDetail();

			paf.setClaim(claim);
			paf.setAmountAdvanceRequired(pafBean.getAmountRequired());
			paf.setAmountEstimatedExpense(pafBean.getEstimatedMedicalExpense());

			paf.setDependent(member);
			paf.setTreatment(treatment);
			if (pafBean.getHospitalId() != 0) {
				Hospital hospital = insPlanEmployeeService.getHospital(pafBean.getHospitalId());
				paf.setHospital(hospital);
			} else {
				paf.setHospital(null);
				paf.setOtherHospital(pafBean.getOtherHospital());
			}
			paf.setEmployeeComments(pafBean.getComments());
			paf.setIllnessType(pafBean.getIllnessType());

			paf.setPafFiledBy(appContext.getUserName());
			paf.setPafFiledbyRole(appContext.getRole());

			paf.setPrescriberName(pafBean.getPrescriberName());
			paf.setPrescriberEmail(pafBean.getPrescriberEmail());
			paf.setPrescriberContactNo(pafBean.getPrescriberContactNo());

			paf.setIsSpecialistServicesAvailed(pafBean.getSpeclistServiceRequired());
			paf.setSpecialistName(pafBean.getSpecialistName());
			paf.setSpecialistEmail(pafBean.getSpecialistEmail());
			paf.setSpecialistContactNo(pafBean.getSpecialistContactNo());

			paf.setProName(pafBean.getProName());
			paf.setProEmail(pafBean.getProEmail());
			paf.setProContactNo(pafBean.getProContactNo());

			paf.setIsPAF(false);
			paf.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);

			paf.setUpdatedBy(appContext.getUserName());
			paf.setCreatedBy(appContext.getUserName());
			insPlanEmployeeService.insert(paf);
			/*
			 * Bill details
			 */

			List<INSBillDetailBean> beans = pafBean.getBean();

			List<InsPlanEmployeeClaimBillDetail> billDetails = new ArrayList<InsPlanEmployeeClaimBillDetail>();

			for (INSBillDetailBean bean : beans) {
				InsPlanEmployeeClaimBill bill = bean.getClaimBill();

				bill.setClaim(claim);
				bill.setClaimPaf(null);
				bill.setCreatedBy(appContext.getUserName());
				bill.setCreatedDate(new Date());
				bill.setStatus(BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SUBMITTED);

				bill.setDependent(member);
				bill.setUpdatedBy(appContext.getUserName());
				Integer billId = insPlanEmployeeService.insertBill(bill);
				bill.setClaimBillId(billId);

				billDetails = bean.getBillDetails();

				if (bean.getBillDetails() != null && bean.getBillDetails().size() > 0) {
					for (InsPlanEmployeeClaimBillDetail billDetail1 : billDetails) {
						billDetail1.setClaimBill(bill);
						billDetail1.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);
						billDetail1.setCreatedBy(appContext.getUserName());
						billDetail1.setCreatedDate(new Date());

						insPlanEmployeeService.insertBillDetail(billDetail1);
					}

				}

			}
			/*
			 * update the pFA detail
			 */
			INSPlanEmployeeClaim claimPFA = insPlanEmployeeService.getEmployeePAFDetails(planEmployeeId);
			if (claimPFA != null) {
				claimPFA.setBillSubmitted(true);
				claimPFA.setUpdatedBy(appContext.getUserName());
				claimPFA.setUpdatedDate(new Date());
				insPlanEmployeeService.update(claimPFA);
			}
			INSClaimReportPdfWriter writer = new INSClaimReportPdfWriter();
			String fileName = null;
			try {
				fileName = writer.write(paf);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getSession().removeAttribute("preAuthBean");
			String messageSubject = "Benefits Portal - Insurance Claim Bills Submitted";

			String messageBody = EmailFormatter.insClaimBillSubmitted(claim);

			EmailService emailService = new EmailService(
					EmailProperties.getProperty(BenefitsConstants.PROP_INS_DESK_EMAIL) + ","
							+ planEmployee.getEmployee().getEmail(),
					"", messageBody, messageSubject, fileName, true);

			System.out.println("----------------Sending email---------------");
			Thread emailThread = new Thread(emailService);
			emailThread.start();
		}
		request.getSession().removeAttribute("preAuthBean");
		return "redirect:/home/controlPanel/insurancePlans/optedEmployees";
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/billDetails/new/save/{billNo}", method = RequestMethod.POST)
	public String saveBills(@PathVariable("billNo") Integer billNo, HttpServletRequest request,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		InsPlanEmployeeClaimBillDetail billDetail = null;
		List<InsPlanEmployeeClaimBillDetail> billDetails = new ArrayList<InsPlanEmployeeClaimBillDetail>();
		Integer rowCount = Integer.parseInt(request.getParameter("rowCount"));

		for (int i = 0; i <= rowCount; i++) {
			Boolean isNullRow = true;
			billDetail = new InsPlanEmployeeClaimBillDetail();
			String categoryId = request.getParameter("categoryId" + i);
			if (categoryId != null && !categoryId.equals("")) {
				isNullRow = false;
				Integer category1 = Integer.parseInt(categoryId);
				INSCategoryMaster category = insPlanEmployeeService.getCategoryById(category1);
				billDetail.setCategory(category);
			}

			String item = request.getParameter("item" + i);
			if (item != null && !item.equals("")) {
				isNullRow = false;
				billDetail.setItem(item);
			}

			String quantity = request.getParameter("quantity" + i);

			if (quantity != null && !quantity.equals("")) {
				isNullRow = false;
				billDetail.setQuantity(Integer.parseInt(quantity));
			}
			String total = request.getParameter("totalAmt" + i);
			if (total != null && !total.equals("")) {
				isNullRow = false;
				BigDecimal totalAmt = new BigDecimal(total);
				billDetail.setTotalCost(totalAmt);
			}

			String unitCost = request.getParameter("unitCost" + i);
			if (unitCost != null && !unitCost.equals("")) {
				isNullRow = false;
				BigDecimal cost = new BigDecimal(unitCost);
				billDetail.setUnitCost(cost);
			}

			if (isNullRow == false) {
				billDetails.add(billDetail);
			}

		}

		PreAuthFormBean preAuthFormBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthBean");
		BigDecimal totalAmount = BigDecimal.ZERO;
		String amt = request.getParameter("totalAmount");
		List<INSBillDetailBean> tempbeans = preAuthFormBean.getBean();
		INSBillDetailBean bean1 = tempbeans.get(billNo);
		if (bean1 == null) {
			bean1 = new INSBillDetailBean();

		} else {
			InsPlanEmployeeClaimBill bill = bean1.getClaimBill();
			if (bill == null) {
				bill = new InsPlanEmployeeClaimBill();

				if (amt != null) {
					totalAmount = new BigDecimal(amt);

				}
				bill.setAmountRequested(totalAmount);
				bean1.setClaimBill(bill);
			} else {
				if (amt != null) {
					totalAmount = new BigDecimal(amt);

				}
				bill.setAmountRequested(totalAmount);
			}
		}
		bean1.setBillDetails(billDetails);

		tempbeans.set(billNo, bean1);
		preAuthFormBean.setBean(tempbeans);

		request.getSession().setAttribute("preAuthBean", preAuthFormBean);
		return "redirect:/home/controlPanel/insurancePlans/optedEmployees/" + preAuthFormBean.getPlanEmployeeId()
				+ "/bills/new#tabBillInfo";
	}

	/*
	 * 
	 * Paf BIll Submission
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/{claimId}/PAFbills/new", method = RequestMethod.GET)
	public ModelAndView newPAFBillsByHR(@PathVariable("claimId") Integer claimId, HttpServletRequest request,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = null;

		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
		BigDecimal totAmt = BigDecimal.ZERO;
		if (claim != null) {
			mav = AuthorizationUtil.authorizeAdmin(appContext, "insBillsForm");
			mav.addObject("adminMode", "true");
			// mav.addObject("planEmployee", planEmployee);

			PreAuthFormBean preAuthBean = null;
			String claimRefNo = null;
			preAuthBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthBean");
			if (preAuthBean != null && preAuthBean.getClaimId() == claimId) {
				// List<INSBillDetailBean> beans =preAuthBean.getBean();
				if (preAuthBean.getBean() != null) {

					for (INSBillDetailBean bean : preAuthBean.getBean()) {
						System.out.println("*************Bean**********" + bean);
						if (bean.getClaimBill() != null) {
							totAmt = totAmt.add(bean.getClaimBill().getAmountRequested());
						}
					}
				}
				preAuthBean.setAmountRequired(totAmt);

				mav.addObject("pafMode", "edit");

			} else {
				preAuthBean = new PreAuthFormBean(claimId);
				preAuthBean.setFiscalYear(appContext.getCurrentInsuranceFiscalYear());
				preAuthBean.setIsStart(true);
				preAuthBean.setPlanEmployeeId(claim.getPlanEmployee().getInsPlanEmployeeId());
				preAuthBean.setAmountRequired(BigDecimal.ZERO);
				InsPlanEmployeeClaimPafDetail claimPafDetails = insPlanEmployeeService.getPafDetail(claimId);

				preAuthBean.setClaimId(claim.getInsPlanEmployeeClaimId());
				preAuthBean.setAmountRequired(claim.getTotalPaidAmt());
				preAuthBean.setCity(claimPafDetails.getHospital().getCity());
				preAuthBean.setComments(claimPafDetails.getEmployeeComments());
				preAuthBean.setEstimatedMedicalExpense(claimPafDetails.getAmountEstimatedExpense());
				preAuthBean.setFiscalYear(claim.getFiscalYear());
				preAuthBean.setHospitalId(claimPafDetails.getHospital().getHospitalId());
				preAuthBean.setIllnessType(claimPafDetails.getIllnessType());
				preAuthBean.setMemberDob(
						new SimpleDateFormat("dd-MMM-yyyy").format(claimPafDetails.getDependent().getDateOfBirth()));
				preAuthBean.setMemberId(claimPafDetails.getDependent().getDependentId());
				preAuthBean.setMemberRelationship(claimPafDetails.getDependent().getRelationship());
				preAuthBean.setPrescriberContactNo(claimPafDetails.getPrescriberContactNo());
				preAuthBean.setPrescriberEmail(claimPafDetails.getPrescriberEmail());
				preAuthBean.setPrescriberName(claimPafDetails.getPrescriberName());

				preAuthBean.setProContactNo(claimPafDetails.getPrescriberContactNo());
				preAuthBean.setProEmail(claimPafDetails.getProEmail());
				preAuthBean.setProName(claimPafDetails.getProName());
				preAuthBean.setSpecialistContactNo(claimPafDetails.getSpecialistContactNo());
				preAuthBean.setSpecialistEmail(claimPafDetails.getSpecialistEmail());
				preAuthBean.setSpecialistName(claimPafDetails.getSpecialistName());
				preAuthBean.setSpeclistServiceRequired(claimPafDetails.getIsSpecialistServicesAvailed());
				preAuthBean.setState(claimPafDetails.getHospital().getState());
				preAuthBean.setTreatmentId(claimPafDetails.getTreatment().getTreatmentId());

				mav.addObject("pafMode", "insert");

			}

			claimRefNo = InsUtil.generateClaimRefNo(preAuthBean.getClaimId(),
					claim.getPlanEmployee().getInsPlan().getPlanName(),
					claim.getPlanEmployee().getEmployee().getEmployeeCode(),
					appContext.getCurrentInsuranceFiscalYear());

			mav.addObject("claimRefNo", claimRefNo);

			List<Treatment> treatments = insPlanEmployeeService.listAllTreatments();
			mav.addObject("treatments", treatments);

			List<String> states = insPlanEmployeeService.listAllStates();
			List<CityWrapper> cities = insPlanEmployeeService.listAllCities();
			List<Hospital> hospitals = insPlanEmployeeService.listAllHospitals();
			List<INSCategoryMaster> categories = insPlanEmployeeService.listAllCategories();
			List<INSBillDetailBean> beans = preAuthBean.getBean();

			mav.addObject("beans", beans);
			if (beans == null) {
				mav.addObject("beansize", 0);
			} else {
				mav.addObject("beansize", beans.size());
			}

			mav.addObject("preAuthBean", preAuthBean);
			request.getSession().setAttribute("preAuthBean", preAuthBean);
			mav.addObject("categories", categories);
			mav.addObject("states", states);
			mav.addObject("cities", cities);
			mav.addObject("hospitals", hospitals);
			mav.addObject("planEmployeeId1", claim.getPlanEmployee().getInsPlanEmployeeId());
		} else {
			mav = AuthorizationUtil.authorizeAdmin(appContext,
					"redirect:/home/controlPanel/insurancePlans/optedEmployees");
		}

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/PAFbills/new/billDetail/{billNo}", method = RequestMethod.POST)
	public ModelAndView savePAFBillDetail(@ModelAttribute("preAuthBean") PreAuthFormBean pafBean,
			@PathVariable("billNo") Integer billNo, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "insBillsDetails");

		INSPlanEmployeeClaim claim = null;

		INSPlanEmployeeClaim billClaim = null;
		Boolean status = false;

		Dependent member = employeeService.getDependent(pafBean.getMemberId());

		INSBillDetailBean currentBean = new INSBillDetailBean();

		PreAuthFormBean preAuthBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthBean");

		List<INSBillDetailBean> currentBeans = new ArrayList<INSBillDetailBean>();
		List<INSBillDetailBean> newBeans = new ArrayList<INSBillDetailBean>();
		currentBeans = preAuthBean.getBean();
		Integer rowCount = Integer.parseInt(request.getParameter("rowCount"));
		List<INSBillDetailBean> beans = new ArrayList<INSBillDetailBean>();

		for (Integer i = 0; i <= rowCount; i++) {
			INSBillDetailBean bean = new INSBillDetailBean();
			Boolean isNullRow = true;
			InsPlanEmployeeClaimBill bill = new InsPlanEmployeeClaimBill();
			bill.setAmountRequested(pafBean.getAmountRequired());
			bill.setAmountApproved(new BigDecimal(0));
			bill.setAmountVerified(new BigDecimal(0));
			String billNo1 = request.getParameter("billNo" + i);
			if (billNo1 != null && !billNo1.equals("")) {
				isNullRow = false;
				bill.setBillNo(billNo1);
			}
			String billIssuer = request.getParameter("billIssuer" + i);
			if (billIssuer != null && !billIssuer.equals("")) {
				isNullRow = false;
				bill.setBillIssuer(billIssuer);
			}

			String billDate = request.getParameter("billDate" + i);
			if (billDate != null && !billDate.equals("")) {
				isNullRow = false;
				try {
					bill.setBillDate(new SimpleDateFormat("dd-MMM-yyyy").parse(billDate));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String totalAmt = request.getParameter("totalAmt" + i);
			if (totalAmt != null && !totalAmt.equals("")) {
				isNullRow = false;
				bill.setAmountRequested(new BigDecimal(totalAmt));
			}

			bill.setClaim(claim);
			bill.setClaimPaf(null);
			bill.setCreatedBy(appContext.getUserName());
			bill.setCreatedDate(new Date());
			bill.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);

			bill.setDependent(member);
			bill.setUpdatedBy(appContext.getUserName());
			if (isNullRow == false || billNo >= i) {

				bean.setClaimBill(bill);
				if (preAuthBean.getBean() != null) {

					if (preAuthBean.getBean().size() >= i + 1 && preAuthBean.getBean().get(i) != null) {
						bean.setBillDetails(preAuthBean.getBean().get(i).getBillDetails());
					}

				}
				if (billNo == i) {
					currentBean = bean;
					if (currentBean != null && currentBean.getClaimBill() != null) {
						mav.addObject("total", currentBean.getClaimBill().getAmountRequested());
					}

				}

				newBeans.add(bean);

			}

		}
		pafBean.setBean(newBeans);

		pafBean.setPlanEmployeeId(preAuthBean.getPlanEmployeeId());
		request.getSession().setAttribute("preAuthBean", pafBean);

		List<INSCategoryMaster> categories = insPlanEmployeeService.listAllCategories();
		if (currentBean != null) {
			mav.addObject("bean", currentBean);
		}
		mav.addObject("categories", categories);

		mav.addObject("billNo", billNo);
		return mav;

	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/{planEmployeeId}/PAFbills/new/save", method = RequestMethod.GET)
	public String savePAFBills(@PathVariable(value = "planEmployeeId") int planEmployeeId, HttpServletRequest request,
			HttpServletResponse response) {

		PreAuthFormBean pafBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthBean");

		String action = request.getParameter("formAction");
		String pafMode = request.getParameter("pafMode");

		INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(planEmployeeId);

		Dependent member = employeeService.getDependent(pafBean.getMemberId());
		Treatment treatment = insPlanEmployeeService.getTreatment(pafBean.getTreatmentId());
		Hospital hospital = insPlanEmployeeService.getHospital(pafBean.getHospitalId());

		auditDate = new Date();

		INSPlanEmployeeClaim claim = null;

		String claimRefNo = InsUtil.generateClaimRefNo(pafBean.getClaimId(), planEmployee.getInsPlan().getPlanName(),
				appContext.getCurrentEmployee().getEmployeeCode(), appContext.getCurrentFiscalYear());

		claim = new INSPlanEmployeeClaim();
		claim.setInsPlanEmployeeClaimId(pafBean.getClaimId());
		claim.setClaimRefno(claimRefNo);
		claim.setClaimType(BenefitsConstants.INS_CLAIM_TYPE_BILL_SUBMISSION);
		claim.setApproved(Boolean.FALSE);
		claim.setApprovedDate(null);
		claim.setBalanceAmount(new BigDecimal(0));
		claim.setFiscalYear(appContext.getCurrentInsuranceFiscalYear());
		claim.setPlanEmployee(planEmployee);
		claim.setTotalReqAmount(pafBean.getAmountRequired());
		claim.setTotalApprovedAmount(new BigDecimal(0));
		claim.setTotalPaidAmt(new BigDecimal(0));
		claim.setClaimPafDetail(null);
		claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);
		claim.setCreatedBy(appContext.getUserName());
		claim.setCreatedDate(new Date());
		claim.setRequestedDate(new Date());
		claim.setClaimType(BenefitsConstants.INS_CLAIM_TYPE_BILL_SUBMISSION);
		claim.setUpdatedDate(new Date());
		claim.setUpdatedBy(appContext.getUserName());
		Integer claimId = (Integer) insPlanEmployeeService.insertClaim(claim);
		if (claimId != null) {

			claim.setInsPlanEmployeeClaimId(claimId);
			// Insert a paf entry to store form fields.But isPAF field will be
			// false.
			InsPlanEmployeeClaimPafDetail paf = insPlanEmployeeService.getPafDetail(claim.getInsPlanEmployeeClaimId());

			paf.setAmountAdvanceRequired(pafBean.getAmountRequired());

			// paf.setIsPAF(false);
			paf.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);

			paf.setUpdatedBy(appContext.getUserName());
			paf.setCreatedBy(appContext.getUserName());
			insPlanEmployeeService.update(paf);

			/* * Bill details */

			List<INSBillDetailBean> beans = pafBean.getBean();

			List<InsPlanEmployeeClaimBillDetail> billDetails = new ArrayList<InsPlanEmployeeClaimBillDetail>();

			for (INSBillDetailBean bean : beans) {
				InsPlanEmployeeClaimBill bill = bean.getClaimBill();

				bill.setClaim(claim);
				bill.setClaimPaf(null);
				bill.setCreatedBy(appContext.getUserName());
				bill.setCreatedDate(new Date());
				bill.setStatus(BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SUBMITTED);

				bill.setDependent(member);
				bill.setUpdatedBy(appContext.getUserName());
				Integer billId = insPlanEmployeeService.insertBill(bill);
				bill.setClaimBillId(billId);

				billDetails = bean.getBillDetails();

				if (bean.getBillDetails() != null && bean.getBillDetails().size() > 0) {
					for (InsPlanEmployeeClaimBillDetail billDetail1 : billDetails) {
						billDetail1.setClaimBill(bill);
						billDetail1.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);
						billDetail1.setCreatedBy(appContext.getUserName());
						billDetail1.setCreatedDate(new Date());

						insPlanEmployeeService.insertBillDetail(billDetail1);
					}

				}

			}
			INSClaimReportPdfWriter writer = new INSClaimReportPdfWriter();
			String fileName = null;
			try {
				fileName = writer.write(paf);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getSession().removeAttribute("preAuthBean");
			EmailService emailService = null;
			String messageSubject = "Benefits Portal - Insurance PAF Bills Submitted";

			String messageBody = EmailFormatter.insClaimBillSubmitted(claim);

			emailService = new EmailService(EmailProperties.getProperty(BenefitsConstants.PROP_INS_DESK_EMAIL) + ","
					+ planEmployee.getEmployee().getEmail(), "", messageBody, messageSubject, fileName, true);

			System.out.println("----------------Sending email---------------");
			Thread emailThread = new Thread(emailService);
			emailThread.start();
		}

		return "redirect:/home/controlPanel/insurancePlans/optedEmployees";
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/PAFbillDetails/new/save/{billNo}", method = RequestMethod.POST)
	public String savePAFBills(@PathVariable("billNo") Integer billNo, HttpServletRequest request,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		InsPlanEmployeeClaimBillDetail billDetail = null;
		List<InsPlanEmployeeClaimBillDetail> billDetails = new ArrayList<InsPlanEmployeeClaimBillDetail>();
		Integer rowCount = Integer.parseInt(request.getParameter("rowCount"));

		for (int i = 0; i <= rowCount; i++) {
			Boolean isNullRow = true;
			billDetail = new InsPlanEmployeeClaimBillDetail();
			String categoryId = request.getParameter("categoryId" + i);
			if (categoryId != null && !categoryId.equals("")) {
				isNullRow = false;
				Integer category1 = Integer.parseInt(categoryId);
				INSCategoryMaster category = insPlanEmployeeService.getCategoryById(category1);
				billDetail.setCategory(category);
			}

			String item = request.getParameter("item" + i);
			if (item != null && !item.equals("")) {
				isNullRow = false;
				billDetail.setItem(item);
			}

			String quantity = request.getParameter("quantity" + i);

			if (quantity != null && !quantity.equals("")) {
				isNullRow = false;
				billDetail.setQuantity(Integer.parseInt(quantity));
			}
			String total = request.getParameter("totalAmt" + i);
			if (total != null && !total.equals("")) {
				isNullRow = false;
				BigDecimal totalAmt = new BigDecimal(total);
				billDetail.setTotalCost(totalAmt);
			}

			if (isNullRow == false) {
				billDetails.add(billDetail);
			}

		}

		PreAuthFormBean preAuthFormBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthBean");
		BigDecimal totalAmount = BigDecimal.ZERO;
		String amt = request.getParameter("totalAmount");
		List<INSBillDetailBean> tempbeans = preAuthFormBean.getBean();
		INSBillDetailBean bean1 = tempbeans.get(billNo);
		if (bean1 == null) {
			bean1 = new INSBillDetailBean();

		} else {
			InsPlanEmployeeClaimBill bill = bean1.getClaimBill();
			if (bill == null) {
				bill = new InsPlanEmployeeClaimBill();

				if (amt != null) {
					totalAmount = new BigDecimal(amt);

				}
				bill.setAmountRequested(totalAmount);
				bean1.setClaimBill(bill);
			} else {
				if (amt != null) {
					totalAmount = new BigDecimal(amt);

				}
				bill.setAmountRequested(totalAmount);
			}
		}
		bean1.setBillDetails(billDetails);

		tempbeans.set(billNo, bean1);
		preAuthFormBean.setBean(tempbeans);

		request.getSession().setAttribute("preAuthBean", preAuthFormBean);
		return "redirect:/home/controlPanel/insurancePlans/optedEmployees/" + preAuthFormBean.getPlanEmployeeId()
				+ "/bills/new";
	}

	/*
	 * 
	 * 
	 * ins_committe approval
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/billsINSApprove/{claimId}", method = RequestMethod.GET)
	public ModelAndView showApproveBillsINS(HttpServletRequest request,

			@PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "insINSApprove");
		PreAuthFormBean preAuthBean = new PreAuthFormBean();
		try {

			INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);

			List<InsPlanEmployeeClaimBill> claimBills = insPlanEmployeeService.listAllBillsByClaimId(claimId);

			InsPlanEmployeeClaimPafDetail claimPafDetails = insPlanEmployeeService.getPafDetail(claimId);
			preAuthBean.setClaimId(claim.getInsPlanEmployeeClaimId());

			if (claimPafDetails != null) {

				preAuthBean.setAmountRequired(claimPafDetails.getAmountAdvanceRequired());
				if (claimPafDetails.getHospital() != null) {
					preAuthBean.setCity(claimPafDetails.getHospital().getCity());
					preAuthBean.setHospitalId(claimPafDetails.getHospital().getHospitalId());
					preAuthBean.setState(claimPafDetails.getHospital().getState());
				}

				preAuthBean.setEstimatedMedicalExpense(claimPafDetails.getAmountEstimatedExpense());
				preAuthBean.setFiscalYear(claim.getFiscalYear());

				preAuthBean.setIllnessType(claimPafDetails.getIllnessType());
				if (claimPafDetails.getDependent() != null) {
					preAuthBean.setMemberDob(new SimpleDateFormat("dd-MMM-yyyy")
							.format(claimPafDetails.getDependent().getDateOfBirth()));
					preAuthBean.setMemberId(claimPafDetails.getDependent().getDependentId());
					preAuthBean.setMemberRelationship(claimPafDetails.getDependent().getRelationship());

				}
				if (claimPafDetails.getHospital() != null) {
					preAuthBean.setCity(claimPafDetails.getHospital().getCity());
					preAuthBean.setState(claimPafDetails.getHospital().getState());
					preAuthBean.setHospitalId(claimPafDetails.getHospital().getHospitalId());
					preAuthBean.setHospitalName(claimPafDetails.getHospital().getHospitalName());

				} else {
					preAuthBean.setCity(claimPafDetails.getOtherHospitalCity());
					preAuthBean.setState(claimPafDetails.getOtherHospitalState());
					preAuthBean.setHospitalId(0);
					preAuthBean.setHospitalName(claimPafDetails.getOtherHospital());
				}

				preAuthBean.setPrescriberContactNo(claimPafDetails.getPrescriberContactNo());
				preAuthBean.setPrescriberEmail(claimPafDetails.getPrescriberEmail());
				preAuthBean.setPrescriberName(claimPafDetails.getPrescriberName());

				preAuthBean.setProContactNo(claimPafDetails.getPrescriberContactNo());
				preAuthBean.setProEmail(claimPafDetails.getProEmail());
				preAuthBean.setProName(claimPafDetails.getProName());
				preAuthBean.setSpecialistContactNo(claimPafDetails.getSpecialistContactNo());
				preAuthBean.setSpecialistEmail(claimPafDetails.getSpecialistEmail());
				preAuthBean.setSpecialistName(claimPafDetails.getSpecialistName());
				preAuthBean.setSpeclistServiceRequired(claimPafDetails.getIsSpecialistServicesAvailed());

				preAuthBean.setComments(claimPafDetails.getEmployeeComments());
				preAuthBean.setHrComments(claimPafDetails.getHrComments());
				if (claimPafDetails.getTreatment() != null) {
					preAuthBean.setTreatmentId(claimPafDetails.getTreatment().getTreatmentId());
				}

			}

			List<CategoryBean> categoryBeans = new ArrayList<CategoryBean>();
			List<INSCategoryMaster> categoryMasters = insPlanEmployeeService.listAllCategories();
			for (INSCategoryMaster categoryMaster : categoryMasters) {
				BigDecimal reqAmt = BigDecimal.ZERO;
				BigDecimal apprAmt = BigDecimal.ZERO;
				BigDecimal rejAmt = BigDecimal.ZERO;

				CategoryBean bean = new CategoryBean();
				for (InsPlanEmployeeClaimBill bill : claimBills) {
					List<InsPlanEmployeeClaimBillDetail> billDetails = insPlanEmployeeService
							.listAllDetailsByClaimId(bill.getClaimBillId());
					for (InsPlanEmployeeClaimBillDetail billDetail : billDetails) {
						if (billDetail.getCategory().getCategoryId().intValue() == categoryMaster.getCategoryId()
								.intValue()) {
							reqAmt = reqAmt.add(billDetail.getTotalCost());
							if (billDetail.getStatus().equals(BenefitsConstants.INS_CLAIM_STATUS_HR_APPROVED)) {
								apprAmt = apprAmt.add(billDetail.getApprovedAmount());
							}
						}
					}

				}
				rejAmt = reqAmt.subtract(apprAmt);
				if (reqAmt != BigDecimal.ZERO) {
					bean.setCategoryId(categoryMaster.getCategoryId());
					bean.setCategoryName(categoryMaster.getCategoryDesc());
					bean.setReqAmt(reqAmt);
					bean.setApprAmt(apprAmt);
					bean.setRejAmt(rejAmt);

					categoryBeans.add(bean);
				}

			}
			mav.addObject("categoryLIst", categoryBeans);
			INSBillDetailBean bean = new INSBillDetailBean();
			List<INSBillDetailBean> beans = new ArrayList<INSBillDetailBean>();

			for (InsPlanEmployeeClaimBill bill : claimBills) {
				List<InsPlanEmployeeClaimBillDetail> billDetails = insPlanEmployeeService
						.listAllDetailsByClaimId(bill.getClaimBillId());
				bean.setClaimBill(bill);
				bean.setBillDetails(billDetails);

				beans.add(bean);
			}
			preAuthBean.setBean(beans);

			mav.addObject("preAuthBean", preAuthBean);
			List<InsPlanEmployeeClaimBill> bills = insPlanEmployeeService
					.listAllBillsByClaimId(claim.getInsPlanEmployeeClaimId());
			mav.addObject("bills", bills);
			mav.addObject("claim", claim);
			mav.addObject("now", new Date());
			mav.addObject("claimRefNo", claim.getClaimRefno());
			mav.addObject("claimDetails", claimPafDetails);
			mav.addObject("planEmployee", claim.getPlanEmployee());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/billsHRApprove/billDetail/{billId}", method = RequestMethod.GET)
	public ModelAndView showBillDetail(

			@PathVariable(value = "billId") int billId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "insBillsDetailsINSApprove");

		List<InsPlanEmployeeClaimBillDetail> detail = null;

		InsPlanEmployeeClaimBill bill = insPlanEmployeeService.getBillById(billId);

		if (bill != null) {

			mav.addObject("bill", bill);
			mav.addObject("claimId", bill.getClaim().getInsPlanEmployeeClaimId());
			detail = insPlanEmployeeService.listAllDetailsByClaimId(bill.getClaimBillId());
			mav.addObject("billDetails", detail);

		}
		return mav;

	}

	/*
	 * 
	 * INS Approves an insurance Bill claim request
	 * 
	 * @param
	 * 
	 * @param
	 * 
	 * @retuns String
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/billsINSApprove/{claimId}/approve", method = RequestMethod.POST)
	public String approveBillsINS(@ModelAttribute("preAuthBean") PreAuthFormBean preAuthBean,
			HttpServletRequest request, HttpServletResponse response, @PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		Date auditDate = new Date();

		try {

			INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
			String comments = request.getParameter("insComments");

			List<InsPlanEmployeeClaimBill> bills = insPlanEmployeeService.listAllBillsByClaimId(claimId);

			claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_APPROVED);

			if (insPlanEmployeeService.update(claim)) {
				InsPlanEmployeeClaimPafDetail claimPafDetail = insPlanEmployeeService
						.getPafDetail(claim.getInsPlanEmployeeClaimId());
				claimPafDetail.setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_APPROVED);
				claimPafDetail.setAmountTotalApproved(claim.getTotalApprovedAmount());
				claimPafDetail.setInsDeskComments(comments);
				insPlanEmployeeService.update(claimPafDetail);

				for (InsPlanEmployeeClaimBill bill : bills) {
					bill.setAmountApproved(bill.getAmountVerified());
					bill.setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_APPROVED);
					insPlanEmployeeService.updateBill(bill);
					List<InsPlanEmployeeClaimBillDetail> billDetails = insPlanEmployeeService
							.listAllDetailsByClaimId(bill.getClaimBillId());
					for (InsPlanEmployeeClaimBillDetail billDetail : billDetails) {
						billDetail.setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_APPROVED);
						insPlanEmployeeService.updateBillDetail(billDetail);
					}
				}

				EmailService emailService = null;
				String messageSubject = "Benefits Portal - Insurance Claim Verified By Insurance Committe";

				String messageBody = EmailFormatter.insClaimRequestInsCommitteeVerify(claim);

				emailService = new EmailService(EmailProperties.getProperty(BenefitsConstants.GENERAL_MANAGEMENT_EMAIL),
						"", messageBody, messageSubject);

				System.out.println("----------------Sending email---------------");
				Thread emailThread = new Thread(emailService);
				emailThread.start();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/home/controlPanel/insurancePlans/searchClaims";

	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/billsINSApprove/{claimId}/reject", method = RequestMethod.GET)
	public String rejectBillsHR(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		Date auditDate = new Date();

		Integer claimId = Integer.parseInt(request.getParameter("claimId"));
		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
		String comments = request.getParameter("insComments");

		List<InsPlanEmployeeClaimBill> bills = insPlanEmployeeService.listAllBillsByClaimId(claimId);

		claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_REJECTED);

		if (insPlanEmployeeService.update(claim)) {
			for (InsPlanEmployeeClaimBill bill : bills) {
				InsPlanEmployeeClaimPafDetail claimPafDetail = insPlanEmployeeService
						.getPafDetail(claim.getInsPlanEmployeeClaimId());
				claimPafDetail.setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_REJECTED);
				claimPafDetail.setInsDeskComments(comments);
				insPlanEmployeeService.update(claimPafDetail);
				bill.setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_REJECTED);
				insPlanEmployeeService.updateBill(bill);
				List<InsPlanEmployeeClaimBillDetail> billDetails = insPlanEmployeeService
						.listAllDetailsByClaimId(bill.getClaimBillId());
				for (InsPlanEmployeeClaimBillDetail billDetail : billDetails) {
					billDetail.setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_REJECTED);
					insPlanEmployeeService.updateBillDetail(billDetail);
				}
			}

			EmailService emailService = null;
			String messageSubject = "Benefits Portal - Insurance Claim Rejected By Insurance Committee";

			String messageBody = EmailFormatter.insClaimRequestHRReject(claim);

			emailService = new EmailService(
					EmailProperties.getProperty(claim.getPlanEmployee().getEmployee().getEmail()), "", messageBody,
					messageSubject);

			System.out.println("----------------Sending email---------------");
			Thread emailThread = new Thread(emailService);
			emailThread.start();

		}
		return "redirect:/home/controlPanel/insurancePlans/searchClaims";

	}

	// Finished insurance approval

	/*
	 * 
	 * 
	 * General Management approval
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/billsGMApprove/{claimId}", method = RequestMethod.GET)
	public ModelAndView showApproveBillsGM(HttpServletRequest request,

			@PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "insGMApprove");
		PreAuthFormBean preAuthBean = new PreAuthFormBean();

		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);

		List<InsPlanEmployeeClaimBill> claimBills = insPlanEmployeeService.listAllBillsByClaimId(claimId);

		InsPlanEmployeeClaimPafDetail claimPafDetails = insPlanEmployeeService.getPafDetail(claimId);
		preAuthBean.setClaimId(claim.getInsPlanEmployeeClaimId());

		if (claimPafDetails != null) {

			preAuthBean.setAmountRequired(claimPafDetails.getAmountAdvanceRequired());
			if (claimPafDetails.getHospital() != null) {
				preAuthBean.setCity(claimPafDetails.getHospital().getCity());
				preAuthBean.setHospitalId(claimPafDetails.getHospital().getHospitalId());
				preAuthBean.setState(claimPafDetails.getHospital().getState());
			}

			preAuthBean.setEstimatedMedicalExpense(claimPafDetails.getAmountEstimatedExpense());
			preAuthBean.setFiscalYear(claim.getFiscalYear());

			preAuthBean.setIllnessType(claimPafDetails.getIllnessType());
			if (claimPafDetails.getDependent() != null) {
				preAuthBean.setMemberDob(
						new SimpleDateFormat("dd-MMM-yyyy").format(claimPafDetails.getDependent().getDateOfBirth()));
				preAuthBean.setMemberId(claimPafDetails.getDependent().getDependentId());
				preAuthBean.setMemberRelationship(claimPafDetails.getDependent().getRelationship());

			}
			preAuthBean.setPrescriberContactNo(claimPafDetails.getPrescriberContactNo());
			preAuthBean.setPrescriberEmail(claimPafDetails.getPrescriberEmail());
			preAuthBean.setPrescriberName(claimPafDetails.getPrescriberName());

			preAuthBean.setProContactNo(claimPafDetails.getPrescriberContactNo());
			preAuthBean.setProEmail(claimPafDetails.getProEmail());
			preAuthBean.setProName(claimPafDetails.getProName());
			preAuthBean.setSpecialistContactNo(claimPafDetails.getSpecialistContactNo());
			preAuthBean.setSpecialistEmail(claimPafDetails.getSpecialistEmail());
			preAuthBean.setSpecialistName(claimPafDetails.getSpecialistName());
			preAuthBean.setSpeclistServiceRequired(claimPafDetails.getIsSpecialistServicesAvailed());

			if (claimPafDetails.getHospital() != null) {
				preAuthBean.setState(claimPafDetails.getHospital().getState());
				preAuthBean.setCity(claimPafDetails.getHospital().getCity());
				preAuthBean.setHospitalName(claimPafDetails.getHospital().getHospitalName());
			} else {
				preAuthBean.setCity(claimPafDetails.getOtherHospitalCity());
				preAuthBean.setHospitalId(0);
				preAuthBean.setState(claimPafDetails.getOtherHospitalState());
				preAuthBean.setHospitalName(claimPafDetails.getOtherHospital());
			}

			preAuthBean.setComments(claimPafDetails.getEmployeeComments());
			preAuthBean.setHrComments(claimPafDetails.getHrComments());
			preAuthBean.setInsComments(claimPafDetails.getInsDeskComments());
			if (claimPafDetails.getTreatment() != null) {
				preAuthBean.setTreatmentId(claimPafDetails.getTreatment().getTreatmentId());
			}

		}

		// category wise

		List<CategoryBean> categoryBeans = new ArrayList<CategoryBean>();
		List<INSCategoryMaster> categoryMasters = insPlanEmployeeService.listAllCategories();
		for (INSCategoryMaster categoryMaster : categoryMasters) {
			BigDecimal reqAmt = BigDecimal.ZERO;
			BigDecimal apprAmt = BigDecimal.ZERO;
			BigDecimal rejAmt = BigDecimal.ZERO;

			CategoryBean bean = new CategoryBean();
			for (InsPlanEmployeeClaimBill bill : claimBills) {
				List<InsPlanEmployeeClaimBillDetail> billDetails = insPlanEmployeeService
						.listAllDetailsByClaimId(bill.getClaimBillId());
				for (InsPlanEmployeeClaimBillDetail billDetail : billDetails) {
					if (billDetail.getCategory().getCategoryId() == categoryMaster.getCategoryId()) {
						if (billDetail.getTotalCost() != null) {
							reqAmt = reqAmt.add(billDetail.getTotalCost());
						}
						if (billDetail.getStatus().equals(BenefitsConstants.INS_CLAIM_STATUS_INS_APPROVED)) {
							if (billDetail.getApprovedAmount() != null) {
								apprAmt = apprAmt.add(billDetail.getApprovedAmount());
							}
						}
					}
				}

			}
			rejAmt = reqAmt.subtract(apprAmt);
			if (reqAmt != BigDecimal.ZERO) {
				bean.setCategoryId(categoryMaster.getCategoryId());
				bean.setCategoryName(categoryMaster.getCategoryDesc());
				bean.setReqAmt(reqAmt);
				bean.setApprAmt(apprAmt);
				bean.setRejAmt(rejAmt);

				categoryBeans.add(bean);
			}

		}
		mav.addObject("categoryLIst", categoryBeans);

		INSBillDetailBean bean = new INSBillDetailBean();
		List<INSBillDetailBean> beans = new ArrayList<INSBillDetailBean>();

		for (InsPlanEmployeeClaimBill bill : claimBills) {
			List<InsPlanEmployeeClaimBillDetail> billDetails = insPlanEmployeeService
					.listAllDetailsByClaimId(bill.getClaimBillId());
			bean.setClaimBill(bill);
			bean.setBillDetails(billDetails);

			beans.add(bean);
		}
		preAuthBean.setBean(beans);

		mav.addObject("preAuthBean", preAuthBean);
		List<InsPlanEmployeeClaimBill> bills = insPlanEmployeeService
				.listAllBillsByClaimId(claim.getInsPlanEmployeeClaimId());
		mav.addObject("bills", bills);
		mav.addObject("claim", claim);
		mav.addObject("claimRefNo", claim.getClaimRefno());
		mav.addObject("now", new Date());
		mav.addObject("claimDetails", claimPafDetails);
		mav.addObject("planEmployee", claim.getPlanEmployee());
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/billsGMApprove/billDetail/{billId}", method = RequestMethod.GET)
	public ModelAndView showBillDetailGM(

			@PathVariable(value = "billId") int billId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "insBillDeatailsGm");

		List<InsPlanEmployeeClaimBillDetail> detail = null;

		InsPlanEmployeeClaimBill bill = insPlanEmployeeService.getBillById(billId);

		if (bill != null) {

			mav.addObject("bill", bill);
			mav.addObject("claimId", bill.getClaim().getInsPlanEmployeeClaimId());
			detail = insPlanEmployeeService.listAllDetailsByClaimId(bill.getClaimBillId());
			mav.addObject("billDetails", detail);

		}
		return mav;

	}

	/*
	 * 
	 * GM Approves an insurance Bill claim request
	 * 
	 * @param
	 * 
	 * @param
	 * 
	 * @retuns String
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/billsGMApprove/{claimId}/approve", method = RequestMethod.POST)
	public String approveBillsGM(@ModelAttribute("preAuthBean") PreAuthFormBean preAuthBean, HttpServletRequest request,
			HttpServletResponse response, @PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		Date auditDate = new Date();

		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
		String comments = request.getParameter("gmComments");

		List<InsPlanEmployeeClaimBill> bills = insPlanEmployeeService.listAllBillsByClaimId(claimId);

		claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_APPROVED);
		claim.setApproved(true);
		if (insPlanEmployeeService.update(claim)) {
			InsPlanEmployeeClaimPafDetail claimPafDetail = insPlanEmployeeService
					.getPafDetail(claim.getInsPlanEmployeeClaimId());
			claimPafDetail.setStatus(BenefitsConstants.INS_CLAIM_STATUS_APPROVED);

			claimPafDetail.setGmComments(comments);
			insPlanEmployeeService.update(claimPafDetail);

			for (InsPlanEmployeeClaimBill bill : bills) {

				bill.setStatus(BenefitsConstants.INS_CLAIM_STATUS_APPROVED);
				insPlanEmployeeService.updateBill(bill);
				List<InsPlanEmployeeClaimBillDetail> billDetails = insPlanEmployeeService
						.listAllDetailsByClaimId(bill.getClaimBillId());
				for (InsPlanEmployeeClaimBillDetail billDetail : billDetails) {
					billDetail.setStatus(BenefitsConstants.INS_CLAIM_STATUS_APPROVED);
					insPlanEmployeeService.updateBillDetail(billDetail);
				}
			}

			EmailService emailService = null;
			String messageSubject = "Benefits Portal - Insurance Claim Bills Approved";

			String messageBody = EmailFormatter.insClaimRequestFinalApprove(claim);

			emailService = new EmailService(EmailProperties.getProperty(BenefitsConstants.PROP_FINANCE_MAIL),
					EmailProperties.getProperty(BenefitsConstants.PROP_FINANCE_MAIL_AND_GM), messageBody,
					messageSubject);

			System.out.println("----------------Sending email---------------");
			Thread emailThread = new Thread(emailService);
			emailThread.start();

		}
		List<INSPlanEmployeeClaim> claims = insuranceService.listAll(appContext.getCurrentInsuranceFiscalYear(),
				BenefitsConstants.INS_CLAIM_TYPE_BILL_SUBMISSION, BenefitsConstants.INS_CLAIM_STATUS_INS_APPROVED);
		if (claims != null && claims.size() > 0) {
			return "redirect:/home/controlPanel/insurancePlans/searchClaims/billsGMApprove/"
					+ claims.get(0).getInsPlanEmployeeClaimId();
		} else {
			return "redirect:/home/controlPanel/insurancePlans/searchClaims";

		}
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/billsGMApprove/{claimId}/reject", method = RequestMethod.GET)
	public String rejectBillsGM(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		Date auditDate = new Date();

		Integer claimId = Integer.parseInt(request.getParameter("claimId"));
		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
		String comments = request.getParameter("gmComments");

		List<InsPlanEmployeeClaimBill> bills = insPlanEmployeeService.listAllBillsByClaimId(claimId);

		claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_REJECTED);

		claim.setApproved(false);
		if (insPlanEmployeeService.update(claim)) {

			InsPlanEmployeeClaimPafDetail claimPafDetail = insPlanEmployeeService
					.getPafDetail(claim.getInsPlanEmployeeClaimId());
			claimPafDetail.setStatus(BenefitsConstants.INS_CLAIM_STATUS_REJECTED);
			claimPafDetail.setGmComments(comments);
			claim.setTotalApprovedAmount(BigDecimal.ZERO);
			insPlanEmployeeService.update(claimPafDetail);

			for (InsPlanEmployeeClaimBill bill : bills) {

				bill.setStatus(BenefitsConstants.INS_CLAIM_STATUS_REJECTED);
				bill.setAmountApproved(BigDecimal.ZERO);
				insPlanEmployeeService.updateBill(bill);
				List<InsPlanEmployeeClaimBillDetail> billDetails = insPlanEmployeeService
						.listAllDetailsByClaimId(bill.getClaimBillId());
				for (InsPlanEmployeeClaimBillDetail billDetail : billDetails) {
					billDetail.setStatus(BenefitsConstants.INS_CLAIM_STATUS_REJECTED);
					insPlanEmployeeService.updateBillDetail(billDetail);
				}
			}

			EmailService emailService = null;
			String messageSubject = "Benefits Portal - Insurance Claim Bills Rejected";

			String messageBody = EmailFormatter.insClaimRequestFinalRejected(claim);

			emailService = new EmailService(claim.getPlanEmployee().getEmployee().getEmail(),
					EmailProperties.getProperty(BenefitsConstants.PROP_FINANCE_MAIL_AND_GM), messageBody,
					messageSubject);

			System.out.println("----------------Sending email---------------");
			Thread emailThread = new Thread(emailService);
			emailThread.start();

		}
		List<INSPlanEmployeeClaim> claims = insuranceService.listAll(appContext.getCurrentInsuranceFiscalYear(),
				BenefitsConstants.INS_CLAIM_TYPE_BILL_SUBMISSION, BenefitsConstants.INS_CLAIM_STATUS_INS_APPROVED);
		if (claims != null && claims.size() > 0) {
			return "redirect:/home/controlPanel/insurancePlans/searchClaims/billsGMApprove/"
					+ claims.get(0).getInsPlanEmployeeClaimId();
		} else {
			return "redirect:/home/controlPanel/insurancePlans/searchClaims";

		}
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/billUploads/{claimId}", method = RequestMethod.GET)
	public ModelAndView showBillUpload(HttpServletRequest request, @PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "billUploadDetails");

		InsPlanEmployeeClaimPafDetail insPlanEmployeeClaimPafDetail = insPlanEmployeeService.getPafDetail(claimId);
		INSPlanEmployeeClaim insPlanEmployeeClaim = insPlanEmployeeService.getClaim(claimId);

		PreAuthFormBean preAuthFormBean = new PreAuthFormBean();

		preAuthFormBean.setClaimId(claimId);
		preAuthFormBean.setPlanEmployeeId(insPlanEmployeeClaim.getPlanEmployee().getInsPlanEmployeeId());

		mav.addObject("insPlanEmployeeClaimPafDetail", insPlanEmployeeClaimPafDetail);

		mav.addObject("preAuthFormBean", preAuthFormBean);
		return mav;
	}

	/*
	 * 
	 * paf pay
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/PAFPay/{insPlanEmployeeClaimId}")
	public ModelAndView viewPAFPay(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "insPlanEmployeeClaimId") Integer insPlanEmployeeClaimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "pafPayView");

		// INSPlanEmployeeClaim claim =
		// insPlanEmployeeService.getClaim(insPlanEmployeeClaimId);
		InsPlanEmployeeClaimPafDetail insPlanEmployeeClaimPafDetail = insPlanEmployeeService
				.getPafDetail(insPlanEmployeeClaimId);

		mav.addObject("claimPafDetail", insPlanEmployeeClaimPafDetail);

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/BillsPay/Paid/{claimPafId}", method = RequestMethod.GET)
	public ModelAndView payPAF(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "claimPafId") Integer claimPafId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"redirect:/home/controlPanel/insurancePlans/searchClaims");

		String pafValues = request.getParameter("paySelected");
		InsPlanEmployeeClaimPafDetail claimPafDetail = insPlanEmployeeService.getClaimPafDetail(claimPafId);
		// INSPlanEmployeeClaim claim =
		// insPlanEmployeeService.getClaim(claimId);

		System.out.println("++++++++++++++++++++++++++++++" + pafValues + "+++++++++++++++++++++++++++++");

		BigDecimal totalPayedAmount;
		BigDecimal amountApprovedInstallment1;
		BigDecimal amountApprovedInstallment2;

		totalPayedAmount = claimPafDetail.getClaim().getTotalPaidAmt();
		amountApprovedInstallment1 = claimPafDetail.getAmountApprovedInstallment1();
		amountApprovedInstallment2 = claimPafDetail.getAmountApprovedInstallment2();

		if (pafValues.contains("1") && pafValues.contains("2")) {
			claimPafDetail.setAmount1Status(true);
			claimPafDetail.setAmount2Status(true);
			totalPayedAmount = totalPayedAmount.add(amountApprovedInstallment1);
			totalPayedAmount = totalPayedAmount.add(amountApprovedInstallment2);
			claimPafDetail.getClaim().setTotalPaidAmt(totalPayedAmount);
			claimPafDetail.getClaim().setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_PAID);
			claimPafDetail.setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_PAID);

		}

		else if (pafValues.contains("1")) {
			claimPafDetail.setAmount1Status(true);
			if (claimPafDetail.getAmount2Status() == null || claimPafDetail.getAmount2Status() == false) {

				totalPayedAmount = totalPayedAmount.add(amountApprovedInstallment1);
				claimPafDetail.getClaim().setTotalPaidAmt(totalPayedAmount);
				claimPafDetail.getClaim().setStatus("PART_PAID");
				claimPafDetail.setStatus("PART_PAID");

			} else {
				totalPayedAmount = totalPayedAmount.add(amountApprovedInstallment1);
				claimPafDetail.getClaim().setTotalPaidAmt(totalPayedAmount);
				claimPafDetail.getClaim().setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_PAID);
				claimPafDetail.setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_PAID);

			}
		}

		else if (pafValues.contains("2")) {
			claimPafDetail.setAmount2Status(true);
			if (claimPafDetail.getAmount1Status() == null || claimPafDetail.getAmount1Status() == false) {

				totalPayedAmount = totalPayedAmount.add(amountApprovedInstallment2);
				claimPafDetail.getClaim().setTotalPaidAmt(totalPayedAmount);
				claimPafDetail.getClaim().setStatus("PART_PAID");
				claimPafDetail.setStatus("PART_PAID");
			} else {
				totalPayedAmount = totalPayedAmount.add(amountApprovedInstallment2);
				claimPafDetail.getClaim().setTotalPaidAmt(totalPayedAmount);
				claimPafDetail.getClaim().setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_PAID);
				claimPafDetail.setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_PAID);
			}

		}

		System.out.println("=======================================" + totalPayedAmount
				+ "=============================================");

		insPlanEmployeeService.update(claimPafDetail);
		insPlanEmployeeService.update(claimPafDetail.getClaim());

		EmailService emailService = null;
		String messageSubject = "Benefits Portal - Insurance Claim PAF Amount Paid";

		String messageBody = EmailFormatter.insPAFAmountPaid(claimPafDetail);

		emailService = new EmailService(claimPafDetail.getClaim().getPlanEmployee().getEmployee().getEmail(), "",
				messageBody, messageSubject);

		System.out.println("----------------Sending email---------------");
		Thread emailThread = new Thread(emailService);
		emailThread.start();

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/viewPlanReport")
	public ModelAndView viewInsPlanReportDownlaod(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "insPlanReportDownlaod");

		// List<INSPlanEmployeeDetails> planEmployeeDetails =
		// insPlanEmployeeService.listAllDetails();
		List<INSPlan> insPlans = insuranceService.listAll();
		// mav.addObject("planEmpDetails", planEmployeeDetails);
		mav.addObject("insPlans", insPlans);

		return mav;
	}

	/*
	 * 
	 * Bills pay
	 */
	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/payBills/{claimId}", method = RequestMethod.GET)
	public ModelAndView showPayBills(HttpServletRequest request, @PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "payBills");

		INSPlanEmployeeClaim insPlanEmployeeClaim = insPlanEmployeeService.getClaim(claimId);

		List<InsPlanEmployeeClaimBill> billList = insPlanEmployeeService.listAllBillsByClaimId(claimId);

		mav.addObject("billList", billList);
		mav.addObject("insPlanEmployeeClaim", insPlanEmployeeClaim);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/payBills/Paid/{insPlanEmployeeClaimId}", method = RequestMethod.GET)
	public ModelAndView payBills(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "insPlanEmployeeClaimId") Integer insPlanEmployeeClaimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"redirect:/home/controlPanel/insurancePlans/searchClaims");

		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(insPlanEmployeeClaimId);
		String insPlanEmployeeClaimIds = request.getParameter("paySelected");
		String totalAmountApproved = request.getParameter("totalAdded");
		// BigDecimal totalAmount = new BigDecimal(totalAmountApproved);

		System.out.println("===============Total Amount ======== " + totalAmountApproved);

		if (insPlanEmployeeClaimIds.equals("all")) {
			List<InsPlanEmployeeClaimBill> bills = insPlanEmployeeService.listAllBillsByClaimId(insPlanEmployeeClaimId);
			for (InsPlanEmployeeClaimBill bill : bills) {
				bill.setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_PAID);
				insPlanEmployeeService.updateBill(bill);
			}
			claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_PAID);
			insPlanEmployeeService.update(claim);
		} else {

			String[] billIDs = insPlanEmployeeClaimIds.split(",");

			// Integer billEmpID = Integer.parseInt(billIDs[0]);
			if (billIDs.length == (insPlanEmployeeService.listAllBillsByClaimId(insPlanEmployeeClaimId).size())) {
				claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_INS_PAID);
				insPlanEmployeeService.update(claim);
			}
			for (String ids : billIDs) {
				Integer claimBillId = Integer.parseInt(ids);
				InsPlanEmployeeClaimBill claimBill = insPlanEmployeeService.getBillById(claimBillId);
				claimBill.setStatus("PAID");
				// claimBill.setAmountApproved(totalAmount);

				insPlanEmployeeService.updateBill(claimBill);
			}
		}

		EmailService emailService = null;
		String messageSubject = "Benefits Portal - Insurance Claim Bill Amount Paid";

		String messageBody = EmailFormatter.insBillAmountPaid(claim);

		emailService = new EmailService(claim.getPlanEmployee().getEmployee().getEmail(), "", messageBody,
				messageSubject);

		System.out.println("----------------Sending email---------------");
		Thread emailThread = new Thread(emailService);
		emailThread.start();

		return mav;
	}

	@RequestMapping(value = "/home/myInsurancePlan/viewClaims/{claimId}/details")
	public ModelAndView showClaimDetailsToUser(@PathVariable(value = "claimId") Integer claimId,
			HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "viewInsClaimDetails");
		if (claimId != null) {
			INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);

			if (claim != null) {
				if (claim.getClaimPafDetail().getHospital() != null) {
					mav.addObject("selectedHospital", true);
				} else {
					mav.addObject("selectedHospital", false);
				}
				if (claim.getClaimType().equals(BenefitsConstants.INS_CLAIM_TYPE_BILL_SUBMISSION)) {
					List<InsPlanEmployeeClaimBill> bills = insPlanEmployeeService
							.listAllBillsByClaimId(claim.getInsPlanEmployeeClaimId());
					mav.addObject("bills", bills);
					mav.addObject("isBill", true);
				} else {
					mav.addObject("bills", null);
					mav.addObject("isBill", false);
				}

				mav.addObject("planEmployeeId", claim.getPlanEmployee().getInsPlanEmployeeId());
				mav.addObject("claim", claim);

			}
		}
		return mav;
	}

	/*
	 * 
	 * 
	 * view
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/viewClaim/{claimId}", method = RequestMethod.GET)
	public ModelAndView viewClaim(HttpServletRequest request,

			@PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewinsForm");
		PreAuthFormBean preAuthBean = new PreAuthFormBean();

		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);

		List<InsPlanEmployeeClaimBill> claimBills = insPlanEmployeeService.listAllBillsByClaimId(claimId);

		InsPlanEmployeeClaimPafDetail claimPafDetails = insPlanEmployeeService.getPafDetail(claimId);
		preAuthBean.setClaimId(claim.getInsPlanEmployeeClaimId());

		if (claimPafDetails != null) {

			preAuthBean.setAmountRequired(claimPafDetails.getAmountAdvanceRequired());
			if (claimPafDetails.getHospital() != null) {
				preAuthBean.setCity(claimPafDetails.getHospital().getCity());
				preAuthBean.setHospitalId(claimPafDetails.getHospital().getHospitalId());
				preAuthBean.setState(claimPafDetails.getHospital().getState());
			} else {
				preAuthBean.setCity(claimPafDetails.getOtherHospitalCity());
				preAuthBean.setState(claimPafDetails.getOtherHospitalState());
				preAuthBean.setHospitalId(0);
				preAuthBean.setHospitalName(claimPafDetails.getOtherHospital());

			}

			preAuthBean.setEstimatedMedicalExpense(claimPafDetails.getAmountEstimatedExpense());
			preAuthBean.setFiscalYear(claim.getFiscalYear());

			preAuthBean.setIllnessType(claimPafDetails.getIllnessType());
			if (claimPafDetails.getDependent() != null) {
				preAuthBean.setMemberDob(
						new SimpleDateFormat("dd-MMM-yyyy").format(claimPafDetails.getDependent().getDateOfBirth()));
				preAuthBean.setMemberId(claimPafDetails.getDependent().getDependentId());
				preAuthBean.setMemberRelationship(claimPafDetails.getDependent().getRelationship());

			}
			preAuthBean.setPrescriberContactNo(claimPafDetails.getPrescriberContactNo());
			preAuthBean.setPrescriberEmail(claimPafDetails.getPrescriberEmail());
			preAuthBean.setPrescriberName(claimPafDetails.getPrescriberName());

			preAuthBean.setProContactNo(claimPafDetails.getPrescriberContactNo());
			preAuthBean.setProEmail(claimPafDetails.getProEmail());
			preAuthBean.setProName(claimPafDetails.getProName());
			preAuthBean.setSpecialistContactNo(claimPafDetails.getSpecialistContactNo());
			preAuthBean.setSpecialistEmail(claimPafDetails.getSpecialistEmail());
			preAuthBean.setSpecialistName(claimPafDetails.getSpecialistName());
			preAuthBean.setSpeclistServiceRequired(claimPafDetails.getIsSpecialistServicesAvailed());

			preAuthBean.setComments(claimPafDetails.getEmployeeComments());
			preAuthBean.setHrComments(claimPafDetails.getHrComments());
			preAuthBean.setInsComments(claimPafDetails.getInsDeskComments());
			if (claimPafDetails.getTreatment() != null) {
				preAuthBean.setTreatmentId(claimPafDetails.getTreatment().getTreatmentId());
			}

		}
		INSBillDetailBean bean = new INSBillDetailBean();
		List<INSBillDetailBean> beans = new ArrayList<INSBillDetailBean>();

		for (InsPlanEmployeeClaimBill bill : claimBills) {
			List<InsPlanEmployeeClaimBillDetail> billDetails = insPlanEmployeeService
					.listAllDetailsByClaimId(bill.getClaimBillId());
			bean.setClaimBill(bill);
			bean.setBillDetails(billDetails);

			beans.add(bean);
		}
		preAuthBean.setBean(beans);

		mav.addObject("preAuthBean", preAuthBean);
		List<InsPlanEmployeeClaimBill> bills = insPlanEmployeeService
				.listAllBillsByClaimId(claim.getInsPlanEmployeeClaimId());
		mav.addObject("bills", bills);
		mav.addObject("claim", claim);
		mav.addObject("claimRefNo", claim.getClaimRefno());
		mav.addObject("now", new Date());
		mav.addObject("claimDetails", claimPafDetails);
		mav.addObject("planEmployee", claim.getPlanEmployee());
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/viewClaim/details/{billId}", method = RequestMethod.GET)
	public ModelAndView viewBillDetails(

			@PathVariable(value = "billId") int billId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewinsBillsDetails");

		List<InsPlanEmployeeClaimBillDetail> detail = null;

		InsPlanEmployeeClaimBill bill = insPlanEmployeeService.getBillById(billId);

		if (bill != null) {
			mav.addObject("viewClaim", true);
			mav.addObject("bill", bill);
			mav.addObject("claimId", bill.getClaim().getInsPlanEmployeeClaimId());
			detail = insPlanEmployeeService.listAllDetailsByClaimId(bill.getClaimBillId());
			mav.addObject("billDetails", detail);

		}
		return mav;

	}

	/*
	 * view Bill details at employee side
	 */
	@RequestMapping(value = "/home/myInsurancePlan/viewClaims/details/{billId}", method = RequestMethod.GET)
	public ModelAndView viewBillDetailsEmployee(

			@PathVariable(value = "billId") int billId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "viewinsBillsDetails");

		List<InsPlanEmployeeClaimBillDetail> detail = null;

		InsPlanEmployeeClaimBill bill = insPlanEmployeeService.getBillById(billId);

		if (bill != null) {
			mav.addObject("viewClaimEmployee", true);
			mav.addObject("bill", bill);
			mav.addObject("claimId", bill.getClaim().getInsPlanEmployeeClaimId());
			detail = insPlanEmployeeService.listAllDetailsByClaimId(bill.getClaimBillId());
			mav.addObject("billDetails", detail);

		}
		return mav;

	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/searchClaims/viewPAFDetails/{claimId}")
	public ModelAndView viewPAFDetails(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewPAFDetails");
		PreAuthFormBean preAuthBean = new PreAuthFormBean();

		INSPlanEmployeeClaim claim = insPlanEmployeeService.getClaim(claimId);
		InsPlanEmployeeClaimPafDetail claimPafDetails = insPlanEmployeeService.getPafDetail(claimId);

		preAuthBean.setClaimId(claim.getInsPlanEmployeeClaimId());
		preAuthBean.setAmountRequired(claimPafDetails.getAmountAdvanceRequired());
		preAuthBean.setAmountApproved(claimPafDetails.getAmountTotalApproved());

		preAuthBean.setComments(claimPafDetails.getEmployeeComments());
		preAuthBean.setHrComments(claimPafDetails.getHrComments());
		preAuthBean.setEstimatedMedicalExpense(claimPafDetails.getAmountEstimatedExpense());
		preAuthBean.setFiscalYear(claim.getFiscalYear());

		preAuthBean.setIllnessType(claimPafDetails.getIllnessType());
		preAuthBean.setMemberDob(
				new SimpleDateFormat("dd-MMM-yyyy").format(claimPafDetails.getDependent().getDateOfBirth()));
		preAuthBean.setMemberId(claimPafDetails.getDependent().getDependentId());
		preAuthBean.setMemberRelationship(claimPafDetails.getDependent().getRelationship());
		preAuthBean.setPrescriberContactNo(claimPafDetails.getPrescriberContactNo());
		preAuthBean.setPrescriberEmail(claimPafDetails.getPrescriberEmail());
		preAuthBean.setPrescriberName(claimPafDetails.getPrescriberName());

		preAuthBean.setProContactNo(claimPafDetails.getPrescriberContactNo());
		preAuthBean.setProEmail(claimPafDetails.getProEmail());
		preAuthBean.setProName(claimPafDetails.getProName());
		preAuthBean.setSpecialistContactNo(claimPafDetails.getSpecialistContactNo());
		preAuthBean.setSpecialistEmail(claimPafDetails.getSpecialistEmail());
		preAuthBean.setSpecialistName(claimPafDetails.getSpecialistName());
		preAuthBean.setSpeclistServiceRequired(claimPafDetails.getIsSpecialistServicesAvailed());
		if (claimPafDetails.getHospital() != null) {
			preAuthBean.setState(claimPafDetails.getHospital().getState());
			preAuthBean.setCity(claimPafDetails.getHospital().getCity());
			preAuthBean.setHospitalId(claimPafDetails.getHospital().getHospitalId());
			preAuthBean.setTreatmentId(claimPafDetails.getTreatment().getTreatmentId());
			mav.addObject("selectedHospital", true);
		} else {
			preAuthBean.setState(claimPafDetails.getOtherHospitalState());
			preAuthBean.setCity(claimPafDetails.getOtherHospitalCity());
			preAuthBean.setHospitalId(0);
			preAuthBean.setOtherHospital(claimPafDetails.getOtherHospital());
			mav.addObject("selectedHospital", false);
		}
		mav.addObject("amt1", claimPafDetails.getAmountApprovedInstallment1());
		mav.addObject("amt2", claimPafDetails.getAmountApprovedInstallment2());
		mav.addObject("preAuthBean", preAuthBean);

		mav.addObject("claim", claim);
		mav.addObject("claimDetails", claimPafDetails);
		mav.addObject("planEmployee", claim.getPlanEmployee());
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/viewMonthlyPlanReport", method = RequestMethod.GET)
	public ModelAndView viewInsMonthlyReport(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "insMonthlyReport");

		List<INSPlan> insPlans = insuranceService.listAll();

		mav.addObject("insPlans", insPlans);
		return mav;
	}

	@RequestMapping(value = "/home/myInsurancePlan/{planEmployeeId}/newClaim", method = RequestMethod.GET)
	public ModelAndView newEmployeeClaimDetails(@PathVariable("planEmployeeId") Integer planEmployeeId,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			appContext = (AppContext) request.getSession().getAttribute("appContext");
			ModelAndView mav = null;
			INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(planEmployeeId);
			Integer claimedAmount = insPlanEmployeeService.getClaimAmount(planEmployeeId);
			INSPlanEmployeeClaim claim = insPlanEmployeeService.getEmployeePAFDetails(planEmployeeId);

			BigDecimal totAmt = BigDecimal.ZERO;
			if (planEmployee != null) {
				mav = AuthorizationUtil.authorizeUser(appContext, "insEmployeeBillsForm");
				mav.addObject("planEmployee", planEmployee);

				PreAuthFormBean preAuthBean = null;
				String claimRefNo = null;
				preAuthBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthBean");

				if (claim != null && preAuthBean == null) {
					preAuthBean = new PreAuthFormBean();

				}
				if (claim == null) {
					claim = new INSPlanEmployeeClaim();
					mav.addObject("pafRequestedAmount", 0);
				} else {
					mav.addObject("pafRequestedAmount", claim.getTotalReqAmount().intValue());
				}

				if (preAuthBean != null) {

					if (claim.getClaimPafDetail() != null) {
						preAuthBean.setClaimId(insPlanEmployeeService.getNextClaimId());
						preAuthBean.setPlanEmployeeId(planEmployeeId);
						preAuthBean.setAmountRequired(claim.getClaimPafDetail().getAmountAdvanceRequired());
						preAuthBean.setAmountApproved(claim.getClaimPafDetail().getAmountTotalApproved());
						try {
							preAuthBean.setCity(claim.getClaimPafDetail().getHospital().getCity());
							preAuthBean.setState(claim.getClaimPafDetail().getHospital().getState());
							preAuthBean.setHospitalId(claim.getClaimPafDetail().getHospital().getHospitalId());
							mav.addObject("hospitalId", preAuthBean.getHospitalId());
							mav.addObject("hospitalSelected", true);
						} catch (Exception e) {
							preAuthBean.setCity(claim.getClaimPafDetail().getOtherHospitalCity());
							preAuthBean.setState(claim.getClaimPafDetail().getOtherHospitalState());
							preAuthBean.setOtherHospital(claim.getClaimPafDetail().getOtherHospital());
							preAuthBean.setHospitalId(0);
							mav.addObject("hospitalId", 0);
							mav.addObject("hospitalSelected", true);
						}
						preAuthBean.setComments(claim.getClaimPafDetail().getEmployeeComments());
						preAuthBean.setHrComments(claim.getClaimPafDetail().getHrComments());
						preAuthBean.setEstimatedMedicalExpense(claim.getClaimPafDetail().getAmountEstimatedExpense());
						preAuthBean.setFiscalYear(claim.getFiscalYear());

						preAuthBean.setIllnessType(claim.getClaimPafDetail().getIllnessType());
						try {
							preAuthBean.setMemberDob(new SimpleDateFormat("dd-MMM-yyyy")
									.format(claim.getClaimPafDetail().getDependent().getDateOfBirth()));
						} catch (Exception e) {
							preAuthBean.setMemberDob(null);
						}
						preAuthBean.setMemberId(claim.getClaimPafDetail().getDependent().getDependentId());
						preAuthBean.setMemberRelationship(claim.getClaimPafDetail().getDependent().getRelationship());
						preAuthBean.setPrescriberContactNo(claim.getClaimPafDetail().getPrescriberContactNo());
						preAuthBean.setPrescriberEmail(claim.getClaimPafDetail().getPrescriberEmail());
						preAuthBean.setPrescriberName(claim.getClaimPafDetail().getPrescriberName());

						preAuthBean.setProContactNo(claim.getClaimPafDetail().getPrescriberContactNo());
						preAuthBean.setProEmail(claim.getClaimPafDetail().getProEmail());
						preAuthBean.setProName(claim.getClaimPafDetail().getProName());
						preAuthBean.setSpecialistContactNo(claim.getClaimPafDetail().getSpecialistContactNo());
						preAuthBean.setSpecialistEmail(claim.getClaimPafDetail().getSpecialistEmail());
						preAuthBean.setSpecialistName(claim.getClaimPafDetail().getSpecialistName());
						preAuthBean
								.setSpeclistServiceRequired(claim.getClaimPafDetail().getIsSpecialistServicesAvailed());

						preAuthBean.setTreatmentId(claim.getClaimPafDetail().getTreatment().getTreatmentId());
					} else {
						mav.addObject("hospitalId", preAuthBean.getHospitalId());
						mav.addObject("hospitalSelected", true);
					}
					// List<INSBillDetailBean> beans =preAuthBean.getBean();
					if (preAuthBean.getBean() != null) {
						List<INSBillDetailBean> beans = new ArrayList<INSBillDetailBean>();
						for (INSBillDetailBean bean : preAuthBean.getBean()) {
							System.out.println("*************Bean**********" + bean);
							if (bean.getClaimBill() != null) {
								if (bean.getClaimBill().getAmountRequested().intValue() != 0) {
									totAmt = totAmt.add(bean.getClaimBill().getAmountRequested());
									beans.add(bean);
								}
							}
						}
						preAuthBean.setBean(beans);

					}
					preAuthBean.setAmountRequired(totAmt);

					mav.addObject("pafMode", "edit");

				} else {
					preAuthBean = new PreAuthFormBean(insPlanEmployeeService.getNextClaimId());
					preAuthBean.setFiscalYear(appContext.getCurrentInsuranceFiscalYear());
					preAuthBean.setIsStart(true);
					preAuthBean.setPlanEmployeeId(planEmployeeId);
					preAuthBean.setAmountRequired(BigDecimal.ZERO);
					preAuthBean.setPlanEmployeeId(planEmployeeId);
					mav.addObject("hospitalId", "new");
					mav.addObject("hospitalSelected", false);
					mav.addObject("pafMode", "insert");

				}

				claimRefNo = InsUtil.generateClaimRefNo(preAuthBean.getClaimId(),
						planEmployee.getInsPlan().getPlanName(), planEmployee.getEmployee().getEmployeeCode(),
						appContext.getCurrentFiscalYear());

				mav.addObject("claimRefNo", claimRefNo);

				List<Treatment> treatments = insPlanEmployeeService.listAllTreatments();
				mav.addObject("treatments", treatments);

				List<String> states = insPlanEmployeeService.listAllStates();
				List<CityWrapper> cities = insPlanEmployeeService.listAllCities();
				List<Hospital> hospitals = insPlanEmployeeService.listAllHospitals();
				List<INSCategoryMaster> categories = insPlanEmployeeService.listAllCategories();
				List<INSBillDetailBean> beans = preAuthBean.getBean();

				mav.addObject("beans", beans);
				if (beans == null) {
					mav.addObject("beansize", 0);
				} else {
					mav.addObject("beansize", beans.size());
				}

				mav.addObject("preAuthBean", preAuthBean);
				request.getSession().setAttribute("preAuthBean", preAuthBean);
				mav.addObject("categories", categories);
				mav.addObject("states", states);
				mav.addObject("cities", cities);
				mav.addObject("hospitals", hospitals);
				mav.addObject("planEmployeeId1", planEmployeeId);
				mav.addObject("claimedAmount", planEmployee.getSumInsured().intValue() - claimedAmount);
				try {
					if (!preAuthBean.getBean().isEmpty()) {
						mav.addObject("countLoop1", preAuthBean.getBean().size() - 1);
						mav.addObject("countLoop", preAuthBean.getBean().size() - 1);

					} else {
						mav.addObject("countLoop1", 0);
						mav.addObject("countLoop", 0);
					}
				} catch (Exception e) {
					mav.addObject("countLoop1", 0);
					mav.addObject("countLoop", 0);
				}

				// mav.addObject("claim", claim);
			} else {
				mav = AuthorizationUtil.authorizeUser(appContext, "redirect:/home/myInsurancePlan");
			}
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/home/myInsurancePlan/{planEmployeeId}/newClaim/save", method = RequestMethod.POST)
	public String employeeSaveBills(@PathVariable(value = "planEmployeeId") int planEmployeeId,
			@ModelAttribute("preAuthBean") PreAuthFormBean pafBean, HttpServletRequest request,
			HttpServletResponse response) {

		PreAuthFormBean preAuthFormBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthBean");
		pafBean.setBean(preAuthFormBean.getBean());

		String comments = request.getParameter("comments");
		System.out.println(comments);
		String action = request.getParameter("formAction");
		String pafMode = request.getParameter("pafMode");

		INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(planEmployeeId);

		Dependent member = employeeService.getDependent(pafBean.getMemberId());
		Treatment treatment = insPlanEmployeeService.getTreatment(pafBean.getTreatmentId());

		auditDate = new Date();

		INSPlanEmployeeClaim claim = new INSPlanEmployeeClaim();

		String claimRefNo = InsUtil.generateClaimRefNo(pafBean.getClaimId(), planEmployee.getInsPlan().getPlanName(),
				appContext.getCurrentEmployee().getEmployeeCode(), appContext.getCurrentFiscalYear());

		// claim = new INSPlanEmployeeClaim();

		claim.setInsPlanEmployeeClaimId(pafBean.getClaimId());

		claim.setClaimRefno(claimRefNo);
		claim.setClaimType(BenefitsConstants.INS_CLAIM_TYPE_BILL_SUBMISSION);
		claim.setApproved(Boolean.FALSE);
		claim.setApprovedDate(null);
		claim.setBalanceAmount(new BigDecimal(0));
		claim.setFiscalYear(appContext.getCurrentInsuranceFiscalYear());
		claim.setPlanEmployee(planEmployee);
		claim.setTotalReqAmount(pafBean.getAmountRequired());
		claim.setTotalApprovedAmount(new BigDecimal(0));
		claim.setTotalPaidAmt(new BigDecimal(0));
		claim.setClaimPafDetail(null);
		claim.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);
		claim.setCreatedBy(appContext.getUserName());
		claim.setCreatedDate(new Date());
		claim.setBillSubmitted(true);
		claim.setRequestedDate(new Date());
		claim.setUpdatedDate(new Date());
		claim.setUpdatedBy(appContext.getUserName());
		Integer claimId = (Integer) insPlanEmployeeService.insertClaim(claim);
		if (claimId != null) {

			claim.setInsPlanEmployeeClaimId(claimId);
			// Insert a paf entry to store form fields.But isPAF field will
			// be
			// false.

			InsPlanEmployeeClaimPafDetail paf = new InsPlanEmployeeClaimPafDetail();
			paf.setCreatedBy(appContext.getUserName());

			paf.setClaim(claim);
			paf.setAmountAdvanceRequired(pafBean.getAmountRequired());
			paf.setAmountEstimatedExpense(pafBean.getEstimatedMedicalExpense());

			paf.setDependent(member);
			paf.setTreatment(treatment);
			if (pafBean.getHospitalId() != 0) {
				Hospital hospital = insPlanEmployeeService.getHospital(pafBean.getHospitalId());
				paf.setHospital(hospital);
			} else {
				paf.setHospital(null);
				paf.setOtherHospital(pafBean.getOtherHospital());
			}
			paf.setEmployeeComments(pafBean.getComments());
			paf.setIllnessType(pafBean.getIllnessType());

			paf.setPafFiledBy(appContext.getUserName());
			paf.setPafFiledbyRole(appContext.getRole());

			paf.setPrescriberName(pafBean.getPrescriberName());
			paf.setPrescriberEmail(pafBean.getPrescriberEmail());
			paf.setPrescriberContactNo(pafBean.getPrescriberContactNo());

			paf.setIsSpecialistServicesAvailed(pafBean.getSpeclistServiceRequired());
			paf.setSpecialistName(pafBean.getSpecialistName());
			paf.setSpecialistEmail(pafBean.getSpecialistEmail());
			paf.setSpecialistContactNo(pafBean.getSpecialistContactNo());

			paf.setProName(pafBean.getProName());
			paf.setProEmail(pafBean.getProEmail());
			paf.setProContactNo(pafBean.getProContactNo());

			paf.setIsPAF(false);
			paf.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);

			paf.setUpdatedBy(appContext.getUserName());

			insPlanEmployeeService.insert(paf);

			/*
			 * Bill details
			 */

			List<INSBillDetailBean> beans = pafBean.getBean();

			List<InsPlanEmployeeClaimBillDetail> billDetails = new ArrayList<InsPlanEmployeeClaimBillDetail>();

			for (INSBillDetailBean bean : beans) {
				InsPlanEmployeeClaimBill bill = bean.getClaimBill();

				bill.setClaim(claim);
				bill.setClaimPaf(null);
				bill.setCreatedBy(appContext.getUserName());
				bill.setCreatedDate(new Date());
				bill.setStatus(BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SUBMITTED);

				bill.setDependent(member);
				bill.setUpdatedBy(appContext.getUserName());
				Integer billId = insPlanEmployeeService.insertBill(bill);
				bill.setClaimBillId(billId);

				billDetails = bean.getBillDetails();

				if (bean.getBillDetails() != null && bean.getBillDetails().size() > 0) {
					for (InsPlanEmployeeClaimBillDetail billDetail1 : billDetails) {
						billDetail1.setClaimBill(bill);
						billDetail1.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);
						billDetail1.setCreatedBy(appContext.getUserName());
						billDetail1.setCreatedDate(new Date());

						insPlanEmployeeService.insertBillDetail(billDetail1);
					}

				}

			}
			/*
			 * update the pFA detail
			 */
			INSPlanEmployeeClaim claimPFA = insPlanEmployeeService.getEmployeePAFDetails(planEmployeeId);
			if (claimPFA != null) {
				claimPFA.setBillSubmitted(true);
				claimPFA.setUpdatedBy(appContext.getUserName());
				claimPFA.setUpdatedDate(new Date());
				insPlanEmployeeService.update(claimPFA);
			}
			INSClaimReportPdfWriter writer = new INSClaimReportPdfWriter();
			String fileName = null;
			try {
				fileName = writer.write(paf);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getSession().removeAttribute("preAuthBean");
			String messageSubject = "Benefits Portal - Insurance Claim Bills Submitted";

			String messageBody = EmailFormatter.insClaimBillSubmitted(claim);

			EmailService emailService = new EmailService(
					EmailProperties.getProperty(BenefitsConstants.PROP_INS_DESK_EMAIL) + ","
							+ planEmployee.getEmployee().getEmail(),
					"", messageBody, messageSubject, fileName, true);

			System.out.println("----------------Sending email---------------");
			Thread emailThread = new Thread(emailService);
			emailThread.start();
		}
		request.getSession().removeAttribute("preAuthBean");
		return "redirect:/home/myInsurancePlan";

	}

	@RequestMapping(value = "/home/myInsurancePlan/bills/new/billDetail/{billNo}", method = RequestMethod.POST)
	public ModelAndView employeeSaveBillDetail(@ModelAttribute("preAuthBean") PreAuthFormBean pafBean,
			@PathVariable("billNo") Integer billNo, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "insEmployeeBillsDetails");

		INSPlanEmployeeClaim claim = null;

		INSPlanEmployeeClaim billClaim = null;
		Boolean status = false;

		Dependent member = employeeService.getDependent(pafBean.getMemberId());

		INSBillDetailBean currentBean = new INSBillDetailBean();

		PreAuthFormBean preAuthBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthBean");

		List<INSBillDetailBean> currentBeans = new ArrayList<INSBillDetailBean>();
		List<INSBillDetailBean> newBeans = new ArrayList<INSBillDetailBean>();
		currentBeans = preAuthBean.getBean();

		Integer rowCount = Integer.parseInt(request.getParameter("rowCount"));
		List<INSBillDetailBean> beans = new ArrayList<INSBillDetailBean>();

		for (Integer i = 0; i <= rowCount; i++) {
			INSBillDetailBean bean = new INSBillDetailBean();
			Boolean isNullRow = true;
			InsPlanEmployeeClaimBill bill = new InsPlanEmployeeClaimBill();
			// bill.setAmountRequested(pafBean.getAmountRequired());
			bill.setAmountRequested(new BigDecimal(0));
			bill.setAmountApproved(new BigDecimal(0));
			bill.setAmountVerified(new BigDecimal(0));
			String billNo1 = request.getParameter("billNo" + i);
			if (billNo1 != null && !billNo1.equals("")) {
				isNullRow = false;
				bill.setBillNo(billNo1);
			}
			String billIssuer = request.getParameter("billIssuer" + i);
			if (billIssuer != null && !billIssuer.equals("")) {
				isNullRow = false;
				bill.setBillIssuer(billIssuer);
			}

			String billDate = request.getParameter("billDate" + i);
			if (billDate != null && !billDate.equals("")) {
				isNullRow = false;
				try {
					bill.setBillDate(new SimpleDateFormat("yyyy-MM-dd").parse(billDate));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String totalAmt = request.getParameter("totalAmt" + i);
			if (totalAmt != null && !totalAmt.equals("")) {
				isNullRow = false;
				bill.setAmountRequested(new BigDecimal(totalAmt));
			}

			bill.setClaim(claim);
			bill.setClaimPaf(null);
			bill.setCreatedBy(appContext.getUserName());
			bill.setCreatedDate(new Date());
			bill.setStatus(BenefitsConstants.INS_CLAIM_STATUS_SUBMITTED);

			bill.setDependent(member);
			bill.setUpdatedBy(appContext.getUserName());
			if (isNullRow == false || billNo >= i) {

				bean.setClaimBill(bill);
				if (preAuthBean.getBean() != null) {

					if (preAuthBean.getBean().size() >= i + 1 && preAuthBean.getBean().get(i) != null) {
						bean.setBillDetails(preAuthBean.getBean().get(i).getBillDetails());
					}

				}
				if (billNo == i) {
					currentBean = bean;
					if (currentBean != null && currentBean.getClaimBill() != null) {
						mav.addObject("total", currentBean.getClaimBill().getAmountRequested());
					}

				}

				newBeans.add(bean);

			}

		}
		pafBean.setBean(newBeans);
		pafBean.setComments(request.getParameter("comments"));
		pafBean.setPlanEmployeeId(preAuthBean.getPlanEmployeeId());
		request.getSession().setAttribute("preAuthBean", pafBean);

		List<INSCategoryMaster> categories = insPlanEmployeeService.listAllCategories();
		if (currentBean != null) {
			mav.addObject("bean", currentBean);
		}
		mav.addObject("categories", categories);

		mav.addObject("billNo", billNo);
		mav.addObject("countLoop", 0);
		try {
			mav.addObject("countLoop1", pafBean.getBean().get(billNo).getBillDetails().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("planEmployeeId", preAuthBean.getPlanEmployeeId());
		return mav;

	}

	@RequestMapping(value = "/home/myInsurancePlan/bills/new/billDetail/new/save/{billNo}", method = RequestMethod.POST)
	public String employeeSaveBills(@PathVariable("billNo") Integer billNo, HttpServletRequest request,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		InsPlanEmployeeClaimBillDetail billDetail = null;
		List<InsPlanEmployeeClaimBillDetail> billDetails = new ArrayList<InsPlanEmployeeClaimBillDetail>();
		Integer rowCount = Integer.parseInt(request.getParameter("rowCount"));

		for (int i = 0; i <= rowCount; i++) {
			Boolean isNullRow = true;
			billDetail = new InsPlanEmployeeClaimBillDetail();
			String categoryId = request.getParameter("categoryId" + i);
			if (categoryId != null && !categoryId.equals("")) {
				isNullRow = false;
				Integer category1 = Integer.parseInt(categoryId);
				INSCategoryMaster category = insPlanEmployeeService.getCategoryById(category1);
				billDetail.setCategory(category);
			}

			String item = request.getParameter("item" + i);
			if (item != null && !item.equals("")) {
				isNullRow = false;
				billDetail.setItem(item);
			}

			String quantity = request.getParameter("quantity" + i);

			if (quantity != null && !quantity.equals("")) {
				isNullRow = false;
				billDetail.setQuantity(Integer.parseInt(quantity));
			}
			String total = request.getParameter("totalAmt" + i);
			if (total != null && !total.equals("")) {
				isNullRow = false;
				BigDecimal totalAmt = new BigDecimal(total);
				billDetail.setTotalCost(totalAmt);
			}

			String unitCost = request.getParameter("unitCost" + i);
			if (unitCost != null && !unitCost.equals("")) {
				isNullRow = false;
				BigDecimal cost = new BigDecimal(unitCost);
				billDetail.setUnitCost(cost);
			}

			if (isNullRow == false) {
				billDetails.add(billDetail);
			}

		}

		PreAuthFormBean preAuthFormBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthBean");
		BigDecimal totalAmount = BigDecimal.ZERO;
		String amt = request.getParameter("totalAmount");
		List<INSBillDetailBean> tempbeans = preAuthFormBean.getBean();
		INSBillDetailBean bean1 = tempbeans.get(billNo);
		if (bean1 == null) {
			bean1 = new INSBillDetailBean();

		} else {
			InsPlanEmployeeClaimBill bill = bean1.getClaimBill();
			if (bill == null) {
				bill = new InsPlanEmployeeClaimBill();

				if (amt != null) {
					totalAmount = new BigDecimal(amt);

				}
				bill.setAmountRequested(totalAmount);
				bean1.setClaimBill(bill);
			} else {
				if (amt != null) {
					totalAmount = new BigDecimal(amt);

				}
				bill.setAmountRequested(totalAmount);
			}
		}
		bean1.setBillDetails(billDetails);

		tempbeans.set(billNo, bean1);
		preAuthFormBean.setBean(tempbeans);

		request.getSession().setAttribute("preAuthBean", preAuthFormBean);

		return "redirect:/home/myInsurancePlan/" + preAuthFormBean.getPlanEmployeeId() + "/newClaim#tabBillInfo";
	}

	@RequestMapping(value = "/home/myInsurancePlan/bills/new/billDetail/remove/{billNo}", method = RequestMethod.POST)
	public String employeeremoveBillDetail(@PathVariable("billNo") Integer billNo, HttpServletRequest request,
			HttpServletResponse response) {
		PreAuthFormBean preAuthBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthBean");
		billNo--;
		try {
			if (preAuthBean.getBean().get(billNo).getBillDetails() != null
					|| !preAuthBean.getBean().get(billNo).getBillDetails().isEmpty()) {
				List<InsPlanEmployeeClaimBillDetail> billDetail = preAuthBean.getBean().get(billNo).getBillDetails();
				preAuthBean.getBean().get(billNo).setBillDetails(new ArrayList<InsPlanEmployeeClaimBillDetail>());

				insPlanEmployeeService.deleteBills(billDetail);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		request.getSession().setAttribute("preAuthBean", preAuthBean);
		request.getSession().setAttribute("billNo", billNo);
		return "redirect:/home/myInsurancePlan/" + preAuthBean.getPlanEmployeeId() + "/newClaim#tabBillInfo";
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/bills/new/billDetail/remove/{billNo}", method = RequestMethod.POST)
	public String adminremoveBillDetail(@PathVariable("billNo") Integer billNo, HttpServletRequest request,
			HttpServletResponse response) {
		PreAuthFormBean preAuthBean = (PreAuthFormBean) request.getSession().getAttribute("preAuthBean");
		billNo--;
		try {
			if (preAuthBean.getBean().get(billNo).getBillDetails() != null
					|| !preAuthBean.getBean().get(billNo).getBillDetails().isEmpty()) {
				List<InsPlanEmployeeClaimBillDetail> billDetail = preAuthBean.getBean().get(billNo).getBillDetails();

				preAuthBean.getBean().get(billNo).setBillDetails(new ArrayList<InsPlanEmployeeClaimBillDetail>());

				insPlanEmployeeService.deleteBills(billDetail);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		request.getSession().setAttribute("preAuthBean", preAuthBean);
		request.getSession().setAttribute("billNo", billNo);
		return "redirect:/home/controlPanel/insurancePlans/optedEmployees/" + preAuthBean.getPlanEmployeeId()
				+ "/bills/new";
	}
	
	
	/*
	 * the following 2 methods will help the admin to add/remove  dependence from insurence after it is HR_APPROVED  
	 */

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/{planEmployeeId}/addDependents", method = RequestMethod.GET)
	public ModelAndView showEnrollPlanToAddDependence(@PathVariable(value = "planEmployeeId") int planEmployeeId,
			HttpServletRequest request) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "insPlanEnrolDetails");

		INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(planEmployeeId);

		Employee employee = planEmployee.getEmployee();

		INSPlan plan = planEmployee.getInsPlan();

		mav.addObject("plan", plan);
		mav.addObject("isAdmin", Boolean.TRUE);

		Calendar dojCalendar = Calendar.getInstance();
		dojCalendar.setTime(employee.getDateOfJoin());

		Calendar currentCalendar = Calendar.getInstance();

		/*
		 * calculating employee years of experience
		 */
		int employeeExperience = currentCalendar.get(Calendar.YEAR) - dojCalendar.get(Calendar.YEAR);
		/*
		 * calculating month difference if month difference is less than 10,
		 * years of experience will reduce one. this logic is to allow employees
		 * who has 2 year 10 months experience to avail 3 year loyalty and to
		 * block any one having 2 year 9 months to block availing 3 year loyalty
		 */
		int employeeExperienceMonthDiff = currentCalendar.get(Calendar.MONTH) - dojCalendar.get(Calendar.MONTH);

		if (employeeExperienceMonthDiff < 0) {
			employeeExperience--;
		}

		double selfEmployeeContribution = 0, selfCompanyContribution = 0, selfEaic = 0;

		int selfId = employeeService.getDependent(appContext.getCurrentEmployee().getEmployeeId(), "Self")
				.getDependentId();

		INSPlanLoyaltyLevels loyaltyLevel = insPlanEmployeeService.getLoyaltyLevel(plan.getInsPlanId(),
				employeeExperience);

		
			mav.addObject("saved", true);
			mav.addObject("empContribution", planEmployee.getYearlyEmpDeduction());
			loyaltyLevel = planEmployee.getLoyaltyLevel();

		
		List<INSEligibleDependentVO> eligibleDependents = new ArrayList<INSEligibleDependentVO>();

		List<String> relationships = new ArrayList<String>();

		if (loyaltyLevel != null) {
			mav.addObject("loyaltyApplicable", Boolean.TRUE);
			mav.addObject("loyalty", loyaltyLevel);

			for (INSPlanLoyaltyLevelDetail loyaltyDetail : loyaltyLevel.getDetails()) {
				List<Dependent> dependents = employeeService.listAllDependents(employee.getEmployeeId(),
						loyaltyDetail.getDepRelationship(), new Short("120"), new Short("0"));

				for (Dependent dep : dependents) {
					INSEligibleDependentVO vo = new INSEligibleDependentVO(dep.getDependentId(), dep.getDependentName(),
							dep.getRelationship(), dep.getGender());

					vo.setDepDateOfBirth(dep.getDateOfBirth());
					vo.setEmployeeContribution(loyaltyDetail.getEmployeeYearlyDeduction());
					vo.setCompanyContribution(loyaltyDetail.getCompanyYearlyDeduction());
					vo.setEaicYearlyDeduction(plan.getEaicYearlyDeduction());
					vo.setDepId(dep.getDependentId());

					if (planEmployee != null) {

						INSPlanEmployeeDetails existingSavedPlanEmployeeDetail = insPlanEmployeeService
								.getPlanEmployeeDetail(planEmployee.getInsPlanEmployeeId(), dep.getDependentId());

						if (existingSavedPlanEmployeeDetail != null) {
							vo.setIsINSEnroled(Boolean.TRUE);
							if (existingSavedPlanEmployeeDetail.getEaicEnrolled()) {
								vo.setIsEAICEnroled(Boolean.TRUE);
							} else {
								vo.setIsEAICEnroled(Boolean.FALSE);
							}
						} else {
							vo.setIsINSEnroled(Boolean.FALSE);
							vo.setIsINSEnroled(Boolean.FALSE);
						}

					}

					if ("Self".equals(dep.getRelationship())) {
						vo.setIsSelf(Boolean.TRUE);
						selfCompanyContribution = loyaltyDetail.getCompanyYearlyDeduction().doubleValue();
						selfEmployeeContribution = loyaltyDetail.getEmployeeYearlyDeduction().doubleValue();
						selfEaic = plan.getEaicYearlyDeduction().doubleValue();
					} else {
						vo.setIsSelf(Boolean.FALSE);
					}

					eligibleDependents.add(vo);
				}

				if ("Self".equals(loyaltyDetail.getDepRelationship())) {
					selfCompanyContribution = loyaltyDetail.getCompanyYearlyDeduction().doubleValue();
					selfEmployeeContribution = loyaltyDetail.getEmployeeYearlyDeduction().doubleValue();
					selfEaic = plan.getEaicYearlyDeduction().doubleValue();
				}
			}
		} else {
			mav.addObject("loyaltyApplicable", Boolean.FALSE);

			List<INSPlanDepDetails> planDepDetails = plan.getDependentDetails();
			for (INSPlanDepDetails insDep : planDepDetails) {
				Boolean dependentsChecked = true;
				List<Dependent> dependents = employeeService.listAllDependents(employee.getEmployeeId(),
						insDep.getDepRelationship(), new Short("120"), new Short("0"));
				/*
				 * if (planEmployee != null &&
				 * BenefitsConstants.INS_PLAN_EMPLOYEE_STATUS_SAVE
				 * .equals(planEmployee.getStatus())) {
				 * List<INSPlanEmployeeDetails> planEmployeeDetails =
				 * insPlanEmployeeService.listAllPlanEmp(planEmployee.
				 * getInsPlanEmployeeId());
				 * 
				 * }
				 */

				for (Dependent dep : dependents) {
					INSEligibleDependentVO vo = new INSEligibleDependentVO(dep.getDependentId(), dep.getDependentName(),
							dep.getRelationship(), dep.getGender());

					vo.setDepDateOfBirth(dep.getDateOfBirth());
					vo.setEmployeeContribution(insDep.getEmpYearlyDeduction());
					vo.setCompanyContribution(insDep.getCmpYearlyDeduction());
					vo.setEaicYearlyDeduction(plan.getEaicYearlyDeduction());
					vo.setDepId(dep.getDependentId());

					if (planEmployee != null) {

						INSPlanEmployeeDetails existingSavedPlanEmployeeDetail = insPlanEmployeeService
								.getPlanEmployeeDetail(planEmployee.getInsPlanEmployeeId(), dep.getDependentId());

						if (existingSavedPlanEmployeeDetail != null) {
							vo.setIsINSEnroled(Boolean.TRUE);
							if (existingSavedPlanEmployeeDetail.getEaicEnrolled()) {
								vo.setIsEAICEnroled(Boolean.TRUE);
							} else {
								vo.setIsEAICEnroled(Boolean.FALSE);
							}
						} else {
							vo.setIsINSEnroled(Boolean.FALSE);
							vo.setIsINSEnroled(Boolean.FALSE);
						}

					}

					if ("Self".equals(dep.getRelationship())) {
						vo.setIsSelf(Boolean.TRUE);

						selfCompanyContribution = insDep.getCmpYearlyDeduction().doubleValue();
						selfEmployeeContribution = insDep.getEmpYearlyDeduction().doubleValue();
						selfEaic = plan.getEaicYearlyDeduction().doubleValue();
					} else {
						vo.setIsSelf(Boolean.FALSE);
					}

					eligibleDependents.add(vo);
				}

			}
		}
		mav.addObject("selfEmployeeContribution", selfEmployeeContribution);
		mav.addObject("selfCompanyContribution", selfCompanyContribution);
		mav.addObject("selfId", selfId);
		mav.addObject("eligibleDependents", eligibleDependents);
		mav.addObject("planEmployeeId", planEmployeeId);

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/insurancePlans/optedEmployees/{planEmployeeId}/addDependents/enroll", method = RequestMethod.POST)
	public String enrolPlanWithNewDependence(@PathVariable(value = "planEmployeeId") int planEmployeeId,
			HttpServletRequest request) {

		appContext = (AppContext) request.getSession().getAttribute("appContext");
		INSPlanEmployee planEmployee = insPlanEmployeeService.getPlanEmployee(planEmployeeId);
		Boolean newJoineeEnrolMode = false;
		Boolean flag = false;
		try {
			newJoineeEnrolMode = (Boolean) request.getSession().getAttribute("newJoineeEnrolMode") == null ? false
					: true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Date auditDate = new Date();
		INSPlan plan = planEmployee.getInsPlan();
		
		String insEnrolledDependentsString = request.getParameter("insEnrolledDeps");
		String eaicEnrolledDependentsString = request.getParameter("eaicEnrolledDeps");
		String companyYearlyDeduction = request.getParameter("cmpYearlyDeductions");
		String employeeYearlyDeduction = request.getParameter("empYearlyDeductions");
		
		if (insEnrolledDependentsString != null && !("".equals(insEnrolledDependentsString))) {
			if (companyYearlyDeduction != null && !("".equals(companyYearlyDeduction))
					&& employeeYearlyDeduction != null && !("".equals(employeeYearlyDeduction))) {
				String[] insEnrolledDependentsArray = insEnrolledDependentsString.split(",");
				String[] eaicEnrolledDependentsArray = eaicEnrolledDependentsString.split(",");

				
				String startYearString = appContext.getCurrentInsuranceFiscalYear().split("-")[0];
				String nextYearString = "20" + appContext.getCurrentInsuranceFiscalYear().split("-")[1];

				String normalPlanValidtyPeriodString = settingsService
						.getPropertyByCode(BenefitsConstants.PROP_INS_NORMAL_VALIDITY).getPropertyValue();
				String validityFromMMDD = normalPlanValidtyPeriodString.split("~")[0];
				String validityToMMDD = normalPlanValidtyPeriodString.split("~")[1];

				String validityFromString = startYearString + "-" + validityFromMMDD;
				String validityToString = nextYearString + "-" + validityToMMDD;

				Date validityFrom = DataTypeUtil.toDateFromStringyyyyMMdd(validityFromString);
				Date validityTo = DataTypeUtil.toDateFromStringyyyyMMdd(validityToString);

				/*
				 * finding total effective months
				 */

				Integer cutOffMonth = Integer.parseInt(validityToMMDD.split("-")[0]);
				cutOffMonth--;

				Calendar startCalendar = new GregorianCalendar();
				
					startCalendar.setTime(validityFrom);
				

				Calendar endCalendar = new GregorianCalendar();
				endCalendar.setTime(validityTo);

				int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
				int diffMonth = (diffYear * 12)
						+ (endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH));

				
				planEmployee.setYearlyEmpDeduction(new BigDecimal(employeeYearlyDeduction));
				planEmployee.setYearlyCmpDeduction(new BigDecimal(companyYearlyDeduction));

				

				/*
				 * creating planEmployeeDetails
				 */

				List<INSPlanEmployeeDetails> planEmployeeDetails = new ArrayList<INSPlanEmployeeDetails>();
				BigDecimal eaicTotalDeduction = new BigDecimal(0);
				Boolean eaicEnrolled = Boolean.FALSE;
				for (int i = 0; i < insEnrolledDependentsArray.length; i++) {
					Dependent dependent = employeeService
							.getDependent(Integer.parseInt(insEnrolledDependentsArray[i]));

					if (dependent != null) {
						INSPlanEmployeeDetails planEmployeeDetail = new INSPlanEmployeeDetails();
						planEmployeeDetail.setDependent(dependent);
						planEmployeeDetail.setPlanEmployee(planEmployee);

						/*
						 * checking loyalty level details exists or not
						 */
						INSPlanLoyaltyLevelDetail loyaltyDetail = null;
						if (planEmployee.getIsLoyaltyMode()) {
							/*
							 * loyalty level det/ail exists only if loyalty
							 * mode is true
							 */
							loyaltyDetail = insPlanEmployeeService.getLoyaltyDetail(
									planEmployee.getLoyaltyLevel().getInsPlanLoyaltyLevelsId(),
									dependent.getRelationship());
						}

						/*
						 * loyalty level detail exists
						 */
						if (loyaltyDetail != null) {
							double employeeMonthlyContribution = loyaltyDetail.getEmployeeYearlyDeduction()
									.doubleValue() / 12;

							double companyMonthlyContribution = loyaltyDetail.getCompanyYearlyDeduction()
									.doubleValue() / 12;

							planEmployeeDetail.setLoyaltyLevelDetail(loyaltyDetail);

							if (BenefitsConstants.INS_PLAN_TYPE_FAMILY_POOL_COVERAGE.equals(plan.getPlanType())) {
								planEmployeeDetail.setSumInsured(plan.getYearlyCoverage());
							} else if (BenefitsConstants.INS_PLAN_TYPE_INDIVIDUAL_COVERAGE
									.equals(plan.getPlanType())) {
								if (loyaltyDetail != null) {
									planEmployeeDetail.setSumInsured(plan.getYearlyCoverage());
								}
							}

							if (loyaltyDetail != null) {
								planEmployeeDetail.setYearlyCmpDeduction(
										new BigDecimal(companyMonthlyContribution * diffMonth));
								planEmployeeDetail.setYearlyEmpDeduction(
										new BigDecimal(employeeMonthlyContribution * diffMonth));
							}
							for (int j = 0; j < eaicEnrolledDependentsArray.length; j++) {
								if (eaicEnrolledDependentsArray[j].equals(insEnrolledDependentsArray[i])) {
									planEmployeeDetail.setEaicEnrolled(Boolean.TRUE);
									planEmployeeDetail.setYearlyEaicDeduction(
											new BigDecimal(employeeMonthlyContribution * diffMonth));
									planEmployeeDetail.setEaicSumInsured(plan.getEaicYearlyCoverage());
									eaicTotalDeduction = eaicTotalDeduction.add(plan.getEaicYearlyDeduction());
									eaicEnrolled = Boolean.TRUE;
								}
							}

							if (planEmployeeDetail.getEaicEnrolled() == null) {
								planEmployeeDetail.setEaicEnrolled(Boolean.FALSE);
							}
						} else { /*
									 * loyalty level detail not exists
									 */
							INSPlanDepDetails planDetail = insPlanEmployeeService.getPlanDetail(plan.getInsPlanId(),
									dependent.getRelationship());

							double employeeMonthlyContribution = planDetail.getEmpYearlyDeduction().doubleValue()
									/ 12;

							double companyMonthlyContribution = planDetail.getCmpYearlyDeduction().doubleValue()
									/ 12;

							if (BenefitsConstants.INS_PLAN_TYPE_FAMILY_POOL_COVERAGE.equals(plan.getPlanType())) {
								planEmployeeDetail.setSumInsured(plan.getYearlyCoverage());
							} else if (BenefitsConstants.INS_PLAN_TYPE_INDIVIDUAL_COVERAGE
									.equals(plan.getPlanType())) {
								if (planDetail != null) {
									planEmployeeDetail.setSumInsured(planDetail.getYearlyCoverage());
								}
							}

							if (planDetail != null) {
								planEmployeeDetail.setYearlyCmpDeduction(
										new BigDecimal(companyMonthlyContribution * diffMonth));
								planEmployeeDetail.setYearlyEmpDeduction(
										new BigDecimal(employeeMonthlyContribution * diffMonth));
							}
							for (int j = 0; j < eaicEnrolledDependentsArray.length; j++) {
								if (eaicEnrolledDependentsArray[j].equals(insEnrolledDependentsArray[i])) {
									planEmployeeDetail.setEaicEnrolled(Boolean.TRUE);
									planEmployeeDetail.setYearlyEaicDeduction(
											new BigDecimal(employeeMonthlyContribution * diffMonth));
									planEmployeeDetail.setEaicSumInsured(plan.getEaicYearlyCoverage());
									eaicTotalDeduction = eaicTotalDeduction.add(plan.getEaicYearlyDeduction());
									eaicEnrolled = Boolean.TRUE;
								}
							}

							if (planEmployeeDetail.getEaicEnrolled() == null) {
								planEmployeeDetail.setEaicEnrolled(Boolean.FALSE);
							}

						}
						planEmployeeDetail.setCreatedBy(appContext.getUserName());
						planEmployeeDetail.setUpdatedBy(appContext.getUserName());
						planEmployeeDetail.setCreatedDate(auditDate);
						planEmployeeDetail.setUpdatedDate(auditDate);

						planEmployeeDetails.add(planEmployeeDetail);
					}

				}

				planEmployee.setDetails(planEmployeeDetails);

				if (eaicEnrolled) {
					planEmployee.setYearlyEaicDeduction(eaicTotalDeduction);
					planEmployee.setEaicSumInsured(plan.getEaicYearlyCoverage());
				}

				if (planEmployee.getCreatedBy() == null) {
					planEmployee.setCreatedBy(appContext.getUserName());
				}

				if (planEmployee.getCreatedDate() == null) {
					planEmployee.setCreatedDate(auditDate);
				}

				planEmployee.setUpdatedBy(appContext.getUserName());
				planEmployee.setUpdatedDate(auditDate);
				planEmployee.setEnrolledDate(auditDate);

				if (insPlanEmployeeService.updatePlanEmployee(planEmployee, true)) {
					
				}

				return "redirect:/home/controlPanel/insurancePlans/optedEmployees";
			}
		}
	
		System.out.println("Failed..");

		return null;
	}
}
