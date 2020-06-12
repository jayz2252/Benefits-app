package com.speridian.benefits2.controller;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.DocumentException;
import com.speridian.benefits2.beans.BenefitPlanDirectClaimBean;
import com.speridian.benefits2.beans.DocumentVO;
import com.speridian.benefits2.beans.PFEmployeeResponse;
import com.speridian.benefits2.beans.SearchBean;
import com.speridian.benefits2.email.EmailFormatter;
import com.speridian.benefits2.email.EmailProperties;
import com.speridian.benefits2.email.EmailService;
import com.speridian.benefits2.model.pojo.BenefitPlan;
import com.speridian.benefits2.model.pojo.BenefitPlanCategoryMisc;
import com.speridian.benefits2.model.pojo.BenefitPlanClaim;
import com.speridian.benefits2.model.pojo.BenefitPlanClaimDetail;
import com.speridian.benefits2.model.pojo.BenefitPlanContact;
import com.speridian.benefits2.model.pojo.BenefitPlanDirectClaim;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployee;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployeeClaimPeriod;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployeeDetail;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployeeDoc;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployeeField;
import com.speridian.benefits2.model.pojo.Dependent;
import com.speridian.benefits2.model.pojo.PFEmployee;
import com.speridian.benefits2.model.pojo.PFEmployeeSlabHistory;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.model.util.BenefitsUtil;
//import com.speridian.benefits2.model.util.BenefitsUtil;
import com.speridian.benefits2.re.BenefitPlanRE;
import com.speridian.benefits2.service.BenefitPlanService;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.util.AuthorizationUtil;
import com.speridian.benefits2.util.DataTypeUtil;
import com.speridian.benefits2.writers.pdf.VehicleEnrollmentPdfReport;
import com.speridian.benefits2.ws.client.docman.rest.DocManProperties;
import com.speridian.benefits2.ws.client.docman.rest.DocmanRestClient;
import com.speridian.benefits2.ws.constants.BenefitsWSConstants;

@Controller
@SessionAttributes({ "searchBean" })
public class BenefitPlanClaimController {

	@Autowired
	BenefitPlanService benefitPlanService;

	@Autowired
	BenefitPlanRE benefitPlanRE;

	@Autowired
	DocmanRestClient docmanRestClient;

	AppContext appContext;

	@RequestMapping(value = "/home/myFlexiPlans/myClaims/{planEmployeeId}")
	public ModelAndView showMyClaims(HttpServletRequest request,
			@PathVariable("planEmployeeId") Integer planEmployeeId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "manageMyClaims");

		BenefitPlanEmployee planEmployee = benefitPlanService.getPlanEmployee(planEmployeeId);

		planEmployee = benefitPlanRE.calculateAmount(planEmployee);

		if (planEmployee != null) {

			benefitPlanService.editPlanEmployee(planEmployee);
			List<BenefitPlanClaim> allClaims = planEmployee.getClaims();
			int approvedCount = 0;
			int rejectedCount = 0;
			int pendingCount = 0;
			BigDecimal approvedTotal = new BigDecimal(0);

			for (BenefitPlanClaim claim : allClaims) {
				if (claim.getStatus().equals(BenefitsConstants.CLAIM_STATUS_FIN_APPR)) {
					approvedCount++;
				} else if (claim.getStatus().equals(BenefitsConstants.CLAIM_STATUS_HR_REJECT)
						|| claim.getStatus().equals(BenefitsConstants.CLAIM_STATUS_FIN_REJECT)) {
					rejectedCount++;
				} else if (claim.getStatus().equals(BenefitsConstants.CLAIM_STATUS_SUBMIT)
						|| claim.getStatus().equals(BenefitsConstants.CLAIM_STATUS_HR_APPR)) {
					pendingCount++;
				}
			}

			double claimPercentage = 0;

			if (planEmployee.getAmountClaimed() != null) {
				if (planEmployee.getYearlyDeduction() != null || planEmployee.getYearlyDeduction().doubleValue() != 0) {
					claimPercentage = planEmployee.getAmountClaimed().doubleValue()
							/ planEmployee.getYearlyDeduction().doubleValue();

				} else if (planEmployee.getAmountDeducted().doubleValue() != 0) {
					claimPercentage = planEmployee.getAmountClaimed().doubleValue()
							/ planEmployee.getAmountDeducted().doubleValue();

				} else {
					claimPercentage = 0;
				}
				claimPercentage *= 100;
			}

			mav.addObject("planEmployee", planEmployee);
			mav.addObject("approvedClaimCount", approvedCount);
			mav.addObject("rejectedClaimCount", rejectedCount);
			mav.addObject("pendingClaimCount", pendingCount);
			mav.addObject("claimPercentage", new BigDecimal(claimPercentage).setScale(2, RoundingMode.HALF_UP));

		}

		if (planEmployee.getBenefitPlan().getClaimDocumentsRequired()) {
			List<BenefitPlanClaim> claims = benefitPlanService.listMyClaims(planEmployeeId);
			for (BenefitPlanClaim claim : claims) {
				String docmanUuid = docmanRestClient.getDocmanUuid(BenefitsWSConstants.SCREEN_FLEXI_NEW_CLAIM,
						appContext.getUserLoginKey(),
						planEmployee.getBenefitPlan().getPlanName() + "/" + appContext.getCurrentFiscalYear(),
						claim.getClaimRefNo(), "Employee Code", planEmployee.getEmployee().getEmployeeCode(),
						appContext.getUserName(), "Scanned copy of Bills");

				claim.setDocmanUuid(docmanUuid);
				claim.setUploadUrl(docmanRestClient.getUploadUrl(docmanUuid, appContext.getUserLoginKey()));
				claim.setDownloadUrl(docmanRestClient.getDownloadUrl(docmanUuid, appContext.getUserLoginKey()));

			}
			mav.addObject("myClaims", claims);
		} else {
			mav.addObject("warnMessage", "No claim request required for this Plan");
		}

		return mav;
	}

	@RequestMapping(value = "/home/myFlexiPlans/myClaims/new/{planEmployeeId}", method = RequestMethod.GET)
	public ModelAndView showNewClaimRequest(HttpServletRequest request,
			@PathVariable("planEmployeeId") Integer planEmployeeId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "newClaimRequest");

		BenefitPlanEmployee planEmployee = benefitPlanService.getPlanEmployee(planEmployeeId);

		mav.addObject("planEmployee", planEmployee);
		mav.addObject("name", planEmployee.getEmployee().getFirstName());

		/*
		 * mav.addObject("totalDeduction",planEmployee.getAmountClaimed());
		 * mav.addObject("yourAmount",
		 * planEmployee.getBenefitPlan().getYearlyDeduction());
		 */
		BigDecimal totalDeduction = planEmployee.getAmountClaimed();
		BigDecimal yourAmount = planEmployee.getBenefitPlan().getYearlyDeduction();

		if (totalDeduction.compareTo(yourAmount) >= 0) {
			mav.addObject("show", false);
		} else {
			mav.addObject("show", true);
		}

		BenefitPlanClaim claim = new BenefitPlanClaim(planEmployee, appContext.getCurrentFiscalYear(), null);
		claim.setBenefitPlanClaimId(benefitPlanService.getUniqueClaimId());
		claim.setClaimRefNo(BenefitsUtil.generateClaimRefNo(claim.getBenefitPlanClaimId(),
				planEmployee.getBenefitPlan().getPlanName(), appContext.getCurrentEmployee().getEmployeeCode(),
				appContext.getCurrentFiscalYear()));
		claim.setStatus(BenefitsConstants.CLAIM_STATUS_OPEN);
		claim.setCreatedBy(appContext.getUserName());
		claim.setUpdatedBy(appContext.getUserName());

		/*
		 * String docmanDocAvailablityServiceUrl = DocManProperties
		 * .getProperty(BenefitsWSConstants.
		 * PROP_DOCMAN_DOC_AVAILABILITY_REST_URL);
		 * mav.addObject("docAvailabilityRestUrl",
		 * docmanDocAvailablityServiceUrl);
		 */

		String docmanUuid = docmanRestClient.getDocmanUuid(BenefitsWSConstants.SCREEN_FLEXI_NEW_CLAIM,
				appContext.getUserLoginKey(),
				planEmployee.getBenefitPlan().getPlanName() + "/" + appContext.getCurrentFiscalYear(),
				claim.getClaimRefNo(), "Employee Code", planEmployee.getEmployee().getEmployeeCode(),
				appContext.getUserName(), "Scanned copy of Bills");

		mav.addObject("DocumentAvailable",
				docmanRestClient.getDocumentAvailablityRestUrl(docmanUuid, appContext.getUserLoginKey()));

		claim.setDocmanUuid(docmanUuid);
		claim.setUploadUrl(docmanRestClient.getUploadUrl(docmanUuid, appContext.getUserLoginKey()));
		claim.setDownloadUrl(docmanRestClient.getDownloadUrl(docmanUuid, appContext.getUserLoginKey()));

		String claimFrequency = claim.getPlanEmployee().getBenefitPlan().getClaimFrequency();
		System.out.println("==========Claim Frequency======" + claimFrequency);

		Date currentDate = new Date();

		String fiscalYear = appContext.getCurrentFiscalYear();
		String fiscalYearStartYear[] = fiscalYear.split("-");

		System.out.println("Fiscal Year : " + fiscalYear);
		System.out.println("Fiscal Year Start Date: " + fiscalYearStartYear[0]);

		String YearSplit = (String) fiscalYearStartYear[0].substring(0, 2);
		System.out.println("------Year Splitted---" + YearSplit);

		String initialDate = "01-04-" + fiscalYearStartYear[0];
		String endDate = "31-03-" + YearSplit + fiscalYearStartYear[1];

		System.out.println("Initial Date : " + initialDate);
		System.out.println("-------Current Date--------" + currentDate);
		System.out.println("End Date : " + endDate);

		Date startDate = DataTypeUtil.toDateFromStringddMMyyyy(initialDate);
		Date endFiscalYearDate = DataTypeUtil.toDateFromStringddMMyyyy(endDate);

		System.out.println("-----------Converted Start Date---------" + startDate);
		System.out.println("------------Converted End Date ----------" + endFiscalYearDate);

		Calendar currDate = Calendar.getInstance();
		currDate.setTime(currentDate);

		int currentDateMonth = currDate.get(Calendar.MONTH) + 1;

		System.out.println("-------CurrentDateMonth------" + currentDateMonth);

		int claimFreq = Integer.parseInt(claimFrequency);

		Integer periodCalculation = (currentDateMonth - 4) / claimFreq;

		/*
		 * System.out.println("=======Period Calculation======" +
		 * periodCalculation);
		 * 
		 * Calendar periodFrom = Calendar.getInstance();
		 * periodFrom.setTime(currentDate); periodFrom.roll(Calendar.MONTH,
		 * periodCalculation); int firstDate =
		 * periodFrom.getActualMinimum(Calendar.DATE);
		 * periodFrom.set(Calendar.DATE, firstDate); Date periodFromDate =
		 * periodFrom.getTime();
		 * System.out.println("--------Final Period From------" +
		 * periodFromDate);
		 * 
		 * Calendar periodTo = Calendar.getInstance();
		 * periodTo.setTime(periodFromDate); periodTo.roll(Calendar.MONTH,
		 * claimFreq - 1); int lastDate =
		 * periodTo.getActualMaximum(Calendar.DATE); periodTo.set(Calendar.DATE,
		 * lastDate); Date periodToDate = periodTo.getTime();
		 * System.out.println("--------Final Period To------" + periodToDate);
		 */

		Date effFrom = claim.getPlanEmployee().getEffFrom();
		Date effTill = new Date();

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(effFrom);
		cal1.set(Calendar.MONTH, (cal1.get(Calendar.MONTH) + claimFreq));
		cal1.add(Calendar.DATE, -1);
		effTill = cal1.getTime();
		Date today = new Date();
		// Date today = new Date();
		Integer c = 12 / claimFreq;
		while (c != 0) {

			if (effFrom.compareTo(today) <= 0 && effTill.compareTo(today) > 0) {
				break;
			}

			System.out.println(claimFreq);
			Calendar cal = Calendar.getInstance();
			cal.setTime(effFrom);
			cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + claimFreq));
			effFrom = cal.getTime();

			cal.setTime(effFrom);
			cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + claimFreq));
			cal.add(Calendar.DATE, -1);
			effTill = cal.getTime();

			c--;
		}

		claim.setPeriodFrom(effFrom);
		claim.setPeriodTo(effTill);

		if (benefitPlanService.addNewClaim(claim)) {
			/*
			 * creating list of misc catoegories for CTGY wise plans
			 */
			List<BenefitPlanCategoryMisc> miscs = new ArrayList<BenefitPlanCategoryMisc>();

			/*
			 * Creating default category for all plans
			 */
			BenefitPlanCategoryMisc mainMisc = new BenefitPlanCategoryMisc();
			mainMisc.setCategoryMiscId(0);
			mainMisc.setMiscName("Main Category/Not Applicable");
			miscs.add(mainMisc);

			if (BenefitsConstants.BENEFIT_PLAN_CLAIM_TYPE_CATEGORYWISE
					.equals(planEmployee.getBenefitPlan().getClaimType())) {
				List<BenefitPlanEmployeeDetail> details = planEmployee.getPlanEmployeeDetails();

				for (BenefitPlanEmployeeDetail detail : details) {
					BenefitPlanCategoryMisc misc = detail.getMisc();
					miscs.add(misc);
				}
			}

			mav.addObject("claim", claim);
			mav.addObject("categories", miscs);

			// commented for FUEL REIMBURSEMENT PLAN
			// need to handle using a plan property whether claim is related to
			// dependent or
			// not
			/*
			 * List<Dependent> dependents = appContext.getCurrentEmployee()
			 * .getDependents(); mav.addObject("dependents", dependents);
			 */
		} else {
			mav.setViewName("redirect:/home/myFlexiPlans/myClaims/" + planEmployeeId);
		}

		return mav;
	}

	@RequestMapping(value = "/home/myFlexiPlans/myClaims/new/save", method = RequestMethod.POST)
	public String createNewClaim(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, DocumentException {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		Integer claimId = Integer.parseInt(request.getParameter("claimId"));
		BenefitPlanClaim claim = benefitPlanService.getClaim(claimId);

		Integer totalBills = Integer.parseInt(request.getParameter("rowCount"));
		BigDecimal totalRequestedAmount = new BigDecimal(0);

		Integer firstMiscId = Integer.parseInt(request.getParameter("claimCtg"));
		Integer firstDepId = Integer.parseInt(request.getParameter("claimDep"));
		String firstBillNo = request.getParameter("claimBillNo");
		String firstBillDate = request.getParameter("claimBillDate");
		String firstBillAmount = request.getParameter("claimBillAmount");
		String firstBiller = request.getParameter("claimBiller");
		String firstBillComments = request.getParameter("claimComments");

		if (firstBillNo != null && firstBillDate != null && firstBillAmount != null && firstBiller != null) {
			/*
			 * creating new claim
			 */

			/*
			 * creating list of claim details
			 */
			List<BenefitPlanClaimDetail> claimDetails = new ArrayList<BenefitPlanClaimDetail>();

			/*
			 * this is for first row of bills table
			 */
			BenefitPlanClaimDetail firstClaimDetail = new BenefitPlanClaimDetail(claim, firstBillNo, firstBiller,
					DataTypeUtil.toDateFromStringyyyyMMdd(firstBillDate), new BigDecimal(firstBillAmount));

			firstClaimDetail.setApprovedAmount(new BigDecimal(0.0));
			firstClaimDetail.setBillDesc(firstBillComments);

			if (firstDepId > 0) {
				Dependent firstDep = new Dependent();
				firstDep.setDependentId(firstDepId);

				firstClaimDetail.setDependent(firstDep);
			}
			if (firstMiscId != 0) {
				firstClaimDetail.setPlanEmployeeDetail(benefitPlanService
						.getPlanEmployeeDetail(claim.getPlanEmployee().getPlanEmployeeId(), firstMiscId));
			}

			firstClaimDetail.setCreatedBy(appContext.getUserName());
			firstClaimDetail.setUpdatedBy(appContext.getUserName());

			totalRequestedAmount = totalRequestedAmount.add(firstClaimDetail.getRequestedAmount());

			claimDetails.add(firstClaimDetail);

			/*
			 * If table row count is more than zero, then iterate This was the
			 * only method for me to achieve this. not sure whether is this a
			 * complex logic or not If God helps, we will consider to re-write
			 * this code
			 */
			if (totalBills > 1) {
				for (int i = 2; i <= totalBills; i++) {

					Integer miscId = Integer.parseInt(request.getParameter("claimCtg" + i));
					Integer depId = Integer.parseInt(request.getParameter("claimDep" + i));
					String billNo = request.getParameter("claimBillNo" + i);
					String billDate = request.getParameter("claimBillDate" + i);
					String billAmount = request.getParameter("claimBillAmount" + i);
					String biller = request.getParameter("claimBiller" + i);
					String billComments = request.getParameter("claimComments" + i);

					BenefitPlanClaimDetail claimDetail = new BenefitPlanClaimDetail(claim, billNo, biller,
							DataTypeUtil.toDateFromStringyyyyMMdd(billDate), new BigDecimal(billAmount));

					claimDetail.setApprovedAmount(new BigDecimal(0.0));
					claimDetail.setBillDesc(billComments);

					if (depId > 0) {
						Dependent dep = new Dependent();
						dep.setDependentId(depId);

						claimDetail.setDependent(dep);
					}

					if (miscId != null) {
						claimDetail.setPlanEmployeeDetail(benefitPlanService
								.getPlanEmployeeDetail(claim.getPlanEmployee().getPlanEmployeeId(), miscId));
					}

					claimDetail.setCreatedBy(appContext.getUserName());
					claimDetail.setUpdatedBy(appContext.getUserName());

					totalRequestedAmount = totalRequestedAmount.add(claimDetail.getRequestedAmount());

					claimDetails.add(claimDetail);
				}
			}

			claim.setDetails(claimDetails);
			claim.setTotalRequestedAmount(totalRequestedAmount);
			claim.setTotalApprovedAmount(new BigDecimal(0));
			claim.setSubmittedDate(new Date());
			claim.setUpdatedBy(appContext.getUserName());
			claim.setStatus(BenefitsConstants.CLAIM_STATUS_SUBMIT);

			/*
			 * CLAIM PERIOD CALCULATION
			 */

			BigDecimal monthly_deduction = (claim.getPlanEmployee().getYearlyDeduction()).divide(new BigDecimal(12), 2,
					RoundingMode.HALF_UP);
			BigDecimal quarterlyAmt = monthly_deduction
					.multiply(new BigDecimal(claim.getPlanEmployee().getBenefitPlan().getClaimFrequency()));

			List<BenefitPlanEmployeeClaimPeriod> claimPeriods = benefitPlanService.listAllClaims(
					claim.getPlanEmployee().getPlanEmployeeId(), claim.getPeriodFrom(), claim.getPeriodTo());
			BenefitPlanEmployeeClaimPeriod claimPeriod = null;

			if (claimPeriods != null && claimPeriods.size() > 0) {
				claimPeriod = claimPeriods.get(0);

				claimPeriod.setNoOfClaims(claimPeriod.getNoOfClaims() + 1);

				claimPeriod.setTotalRequestedAmount(
						claimPeriod.getTotalRequestedAmount().add(claim.getTotalRequestedAmount()));

				claimPeriod.setUpdatedDate(new Date());
				claimPeriod.setUpdatedBy(appContext.getUserName());

				benefitPlanService.update(claimPeriod);

			} else {
				claimPeriod = new BenefitPlanEmployeeClaimPeriod();
				claimPeriod.setPeriodFrom(claim.getPeriodFrom());
				claimPeriod.setPeriodTo(claim.getPeriodTo());
				claimPeriod.setPlanEmployee(claim.getPlanEmployee());
				claimPeriod.setNoOfClaims(1);
				claimPeriod.setAlottedAmount(quarterlyAmt);
				claimPeriod.setTotalRequestedAmount(claim.getTotalRequestedAmount());
				claimPeriod.setCarryForward(BigDecimal.ZERO);
				claimPeriod.setTotalApprovedAmount(BigDecimal.ZERO);
				claimPeriod.setPayRolledAmount(BigDecimal.ZERO);
				claimPeriod.setUpdatedDate(new Date());
				claimPeriod.setUpdatedBy(appContext.getUserName());
				claimPeriod.setCreatedDate(new Date());
				claimPeriod.setCreatedBy(appContext.getUserName());

				benefitPlanService.insert(claimPeriod);

			}
			claim.setUpdatedDate(new Date());

			// finished
			/*
			 * Adding attachment to the mail
			 */
			VehicleEnrollmentPdfReport writer = new VehicleEnrollmentPdfReport();
			writer.addDetails(writer.DETAIL_BENEFIT_PLAN_VEHICLE, claim.getDetails());

			String fileName = writer.write(claim);

			EmailService emailService = null;
			String messageSubject = "Benefits Portal - New Claim requested Successfully";

			String messageBody = EmailFormatter.newClaimRequest(claim);

			List<BenefitPlanContact> ccList = new ArrayList<BenefitPlanContact>();
			ccList = claim.getPlanEmployee().getBenefitPlan().getContacts();
			String cc = "";
			for (BenefitPlanContact sb : ccList) {
				cc = sb.getEmail() + ",";
			}

			emailService = new EmailService(appContext.getCurrentEmployee().getEmail(), cc, messageBody,
					messageSubject,fileName,true);

			System.out.println("----------------Sending email---------------");
			Thread emailThread = new Thread(emailService);
			emailThread.start();

			if (benefitPlanService.edit(claim)) {
				return "redirect:/home/myFlexiPlans/myClaims/" + claim.getPlanEmployee().getPlanEmployeeId();
			} else {
				return "";
			}
		}

		return "";
	}

	/**
	 * hard delete a claim
	 * 
	 * Purpose of method
	 * 
	 * @param
	 * @param
	 * @retuns String
	 * 
	 */
	@RequestMapping(value = "/home/myFlexiPlans/myClaims/remove/{claimId}")
	public String removeClaim(HttpServletRequest request, @PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		BenefitPlanClaim claim = benefitPlanService.getClaim(claimId);
		if (claim != null) {
			if (claim.getPlanEmployee().getEmployee().getEmployeeId()
					.equals(appContext.getCurrentEmployee().getEmployeeId())
					&& claim.getStatus().equals(BenefitsConstants.CLAIM_STATUS_SUBMIT)) {
				if (benefitPlanService.removeClaim(claim)) {
					return "redirect:/home/myFlexiPlans/myClaims/" + claim.getPlanEmployee().getPlanEmployeeId();
				}
			}
		}
		return "";
	}

	/**
	 * 
	 * 
	 * Shows view all claims page with search
	 * 
	 * @param
	 * @param
	 * @retuns ModelAndView
	 * 
	 */
	@RequestMapping(value = "/home/controlPanel/flexiPlans/searchClaims", method = RequestMethod.GET)
	public ModelAndView showSearchedClaimsPage(HttpServletRequest request, HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		SearchBean searchBean = (SearchBean) request.getSession().getAttribute("searchBean");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "searchedClaims");
		List<BenefitPlan> allPlans = benefitPlanService.listAll();

		if (searchBean == null) {
			searchBean = new SearchBean();
			searchBean.setBenefitPlanId(0);
			searchBean.setStatus("");
			searchBean.setFiscalYear(appContext.getCurrentFiscalYear());
		}
		mav.addObject("bean", searchBean);
		// SearchBean bean = new SearchBean();
		// bean.setClaimRefNo(" ");
		// bean.setFiscalYear(" ");
		// bean.setStatus(" ");
		// bean.setBenefitPlanId(0);
		// bean.setEmployeeCode(" ");
		String role = appContext.getRole();
		mav.addObject("role", role);
		if (allPlans != null && allPlans.size() > 0) {
			mav.addObject("plans", allPlans);
			BenefitPlan firstPlan = allPlans.get(0);
			List<BenefitPlanClaim> benefitPlanClaim = benefitPlanService.searchClaims(searchBean.getClaimRefNo(),
					searchBean.getEmployeeCode(), searchBean.getBenefitPlanId(), searchBean.getFiscalYear(),
					searchBean.getStatus());
			mav.addObject("planClaims", benefitPlanClaim);
		}

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/searchClaims", method = RequestMethod.POST)
	public ModelAndView searchedClaims(HttpServletRequest request, @ModelAttribute("bean") SearchBean bean,
			HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "searchedClaims");
		List<BenefitPlan> allPlans = benefitPlanService.listAll();

		if (bean.getClaimRefNo().equals(null) || bean.getClaimRefNo().equals("")) {
			bean.setClaimRefNo(" ");

		}
		if (bean.getEmployeeCode().equals(null) || bean.getEmployeeCode().equals("")) {
			bean.setEmployeeCode(" ");
		}
		String role = appContext.getRole();
		mav.addObject("role", role);
		mav.addObject("plans", allPlans);
		List<BenefitPlanClaim> benefitPlanClaim = benefitPlanService.searchClaims(bean.getClaimRefNo(),
				bean.getEmployeeCode(), bean.getBenefitPlanId(), bean.getFiscalYear(), bean.getStatus());
		mav.addObject("bean", bean);
		mav.addObject("planClaims", benefitPlanClaim);
		return mav;
	}

	/*
	 * Benefits Claim - FIN APPROVE Selected
	 * /home/controlPanel/flexiPlans/claims/finApproveSelected
	 * 
	 */
	@RequestMapping(value = "/home/controlPanel/flexiPlans/searchClaims/finApproveSelected")
	public ModelAndView approveSelectedClaims(HttpServletRequest request, HttpServletResponse response) {
		try {
			appContext = (AppContext) request.getSession().getAttribute("appContext");
			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
					"redirect:/home/controlPanel/flexiPlans/searchClaims");

			System.out.println("-------------------You are here------------------");

			String selectedIDs = request.getParameter("approveSelected");
			System.out.println("++++++++++++++++++++++++++++++" + selectedIDs + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			String[] claimIds = selectedIDs.split(",");

			for (String ids : claimIds) {

				// Integer claimID = Integer.parseInt(claimIds[0]);
				// System.out.println("*********** : "+claimID);

				Integer benClaimId = Integer.parseInt(ids);
				System.out.println("*********** : " + benClaimId);
				Date auditDate = new Date();

				BenefitPlanClaim claim = benefitPlanService.getClaim(benClaimId);

				/*
				 * calculating claim periods to record
				 * alloted,approved,carry,pay rolled amounts of each period.
				 */

				BigDecimal monthly_deduction = (claim.getPlanEmployee().getYearlyDeduction()).divide(new BigDecimal(12),
						2, RoundingMode.HALF_UP);
				BigDecimal quarterlyAmt = monthly_deduction
						.multiply(new BigDecimal(claim.getPlanEmployee().getBenefitPlan().getClaimFrequency()));
				BenefitPlanEmployee benefitPlanEmployee = benefitPlanService
						.getById(claim.getPlanEmployee().getPlanEmployeeId());
				BigDecimal taxableAmt = benefitPlanEmployee.getTaxableAmount().subtract(claim.getTotalApprovedAmount());
				if (taxableAmt.compareTo(new BigDecimal(0)) > 0) {
					benefitPlanEmployee.setTaxableAmount(taxableAmt);
				} else {
					benefitPlanEmployee.setTaxableAmount(new BigDecimal(0));
				}
				List<BenefitPlanEmployeeClaimPeriod> claimPeriods = benefitPlanService.listAllClaims(
						claim.getPlanEmployee().getPlanEmployeeId(), claim.getPeriodFrom(), claim.getPeriodTo());
				BenefitPlanEmployeeClaimPeriod claimPeriod = null;
				BigDecimal payRolledAmt = new BigDecimal(0);

				/* if the claimperiod is null for the perticular period */
				if (claimPeriods == null || claimPeriods.size() == 0 || claimPeriods.isEmpty()) {
					BenefitPlanEmployeeClaimPeriod period = new BenefitPlanEmployeeClaimPeriod();
					claimPeriods = new ArrayList<BenefitPlanEmployeeClaimPeriod>();
					Date currentDate = new Date();
					String claimFrequency = claim.getPlanEmployee().getBenefitPlan().getClaimFrequency();
					String fiscalYear = appContext.getCurrentFiscalYear();
					String fiscalYearStartYear[] = fiscalYear.split("-");

					System.out.println("Fiscal Year : " + fiscalYear);
					System.out.println("Fiscal Year Start Date: " + fiscalYearStartYear[0]);

					String YearSplit = (String) fiscalYearStartYear[0].substring(0, 2);
					System.out.println("------Year Splitted---" + YearSplit);

					String initialDate = "01-04-" + fiscalYearStartYear[0];
					String endDate = "31-03-" + YearSplit + fiscalYearStartYear[1];

					System.out.println("Initial Date : " + initialDate);
					System.out.println("-------Current Date--------" + currentDate);
					System.out.println("End Date : " + endDate);

					Date startDate = DataTypeUtil.toDateFromStringddMMyyyy(initialDate);
					Date endFiscalYearDate = DataTypeUtil.toDateFromStringddMMyyyy(endDate);

					System.out.println("-----------Converted Start Date---------" + startDate);
					System.out.println("------------Converted End Date ----------" + endFiscalYearDate);

					Calendar currDate = Calendar.getInstance();
					currDate.setTime(currentDate);

					int currentDateMonth = currDate.get(Calendar.MONTH) + 1;

					System.out.println("-------CurrentDateMonth------" + currentDateMonth);

					int claimFreq = Integer.parseInt(claimFrequency);

					Integer periodCalculation = (currentDateMonth - 4) / claimFreq;
					Date effFrom = claim.getPlanEmployee().getEffFrom();
					Date effTill = new Date();
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(effFrom);
					cal1.set(Calendar.MONTH, (cal1.get(Calendar.MONTH) + claimFreq));
					cal1.add(Calendar.DATE, -1);
					effTill = cal1.getTime();
					Date today = new Date();
					// Date today = new Date();
					Integer c = 12 / claimFreq;
					while (c != 0) {
						if (effFrom.compareTo(today) <= 0 && effTill.compareTo(today) > 0) {
							break;
						}
						System.out.println(claimFreq);
						Calendar cal = Calendar.getInstance();
						cal.setTime(effFrom);
						cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + claimFreq));
						effFrom = cal.getTime();
						cal.setTime(effFrom);
						cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + claimFreq));
						cal.add(Calendar.DATE, -1);
						effTill = cal.getTime();

						c--;
					}
					period.setPeriodFrom(effFrom);
					period.setPeriodTo(effTill);
					period.setPlanEmployee(claim.getPlanEmployee());
					period.setNoOfClaims(1);
					period.setAlottedAmount(quarterlyAmt);
					period.setTotalRequestedAmount(claim.getTotalRequestedAmount());
					period.setCarryForward(BigDecimal.ZERO);
					period.setTotalApprovedAmount(BigDecimal.ZERO);
					period.setPayRolledAmount(BigDecimal.ZERO);
					period.setUpdatedDate(new Date());
					period.setUpdatedBy(appContext.getUserName());
					period.setCreatedDate(new Date());
					period.setCreatedBy(appContext.getUserName());
					benefitPlanService.insert(period);
					claimPeriods.add(period);
				}

				/* Till here */

				if (claimPeriods.get(0).getTotalApprovedAmount() != null
						&& claimPeriods.get(0).getTotalApprovedAmount() != BigDecimal.ZERO) {
					claimPeriod = claimPeriods.get(0);

					claimPeriod.setTotalApprovedAmount(
							claimPeriod.getTotalApprovedAmount().add(claim.getTotalApprovedAmount()));
					BigDecimal carry = claimPeriod.getCarryForward().subtract(claim.getTotalApprovedAmount());
					claimPeriod.setCarryForward(carry);
					if (carry.compareTo(new BigDecimal(0)) <= 0) {
						payRolledAmt = quarterlyAmt;

					} else {
						if (benefitPlanEmployee.getNonPayrolledAmount().compareTo(new BigDecimal(0)) >= 0) {
							if (claimPeriod.getTotalApprovedAmount().compareTo(quarterlyAmt) >= 0) {

								payRolledAmt = quarterlyAmt;
							} else {
								payRolledAmt = claimPeriod.getTotalApprovedAmount();
							}
						} else {
							payRolledAmt = claim.getTotalApprovedAmount()
									.subtract(benefitPlanEmployee.getNonPayrolledAmount());
						}
					}

					if (carry.compareTo((new BigDecimal(0))) < 0) {
						benefitPlanEmployee.setNonPayrolledAmount(carry);

					} else {
						benefitPlanEmployee.setNonPayrolledAmount(carry);

					}

					claimPeriod.setCarryForward(carry);
					claimPeriod.setPayRolledAmount(payRolledAmt);
					claimPeriod.setUpdatedDate(new Date());
					claimPeriod.setUpdatedBy(appContext.getUserName());

					benefitPlanService.update(claimPeriod);
					benefitPlanService.editPlanEmployee(benefitPlanEmployee);
				} else {
					// BenefitPlanEmployeeClaimPeriod claimPeriod = new
					// BenefitPlanEmployeeClaimPeriod();
					claimPeriod = claimPeriods.get(0);
					claimPeriod.setTotalApprovedAmount(claim.getTotalApprovedAmount());
					BigDecimal carry = new BigDecimal(0);
					BigDecimal nonPayRolledAmt = new BigDecimal(0);

					carry = (quarterlyAmt.add(benefitPlanEmployee.getNonPayrolledAmount())
							.subtract(claim.getTotalApprovedAmount()));
					if (carry.compareTo(new BigDecimal(0)) <= 0) {
						payRolledAmt = quarterlyAmt;

					} else {
						if (benefitPlanEmployee.getNonPayrolledAmount().compareTo(new BigDecimal(0)) >= 0) {
							if (claim.getTotalApprovedAmount().compareTo(quarterlyAmt) > 0) {
								payRolledAmt = quarterlyAmt;
							} else {
								payRolledAmt = claim.getTotalApprovedAmount();
							}
						} else {
							payRolledAmt = claim.getTotalApprovedAmount()
									.subtract(benefitPlanEmployee.getNonPayrolledAmount());
						}
					}

					if (carry.compareTo((new BigDecimal(0))) < 0) {
						benefitPlanEmployee.setNonPayrolledAmount(carry);

					} else {
						benefitPlanEmployee.setNonPayrolledAmount(carry);

					}

					claimPeriod.setCarryForward(carry);
					claimPeriod.setPayRolledAmount(payRolledAmt);
					claimPeriod.setUpdatedDate(new Date());
					claimPeriod.setUpdatedBy(appContext.getUserName());

					benefitPlanService.editPlanEmployee(benefitPlanEmployee);
					benefitPlanService.update(claimPeriod);

				}

				claim.setUpdatedBy(appContext.getUserName());
				claim.setUpdatedDate(auditDate);
				claim.setStatus(BenefitsConstants.CLAIM_STATUS_FIN_APPR);
				claim.setFinApprovedBy(appContext.getCurrentEmployee().getUserName());
				// claim.setFinReason(request.getParameter("approveComments"));
				/**
				 * Sending email notification for users
				 */
				EmailService emailService = null;
				if (benefitPlanService.edit(claim)) {

					String messageSubject = "Benefits Portal - Claim Request has been Approved";
					String messageBody = EmailFormatter.claimRequestApproved(claim);
					emailService = new EmailService(claim.getPlanEmployee().getEmployee().getEmail(),
							EmailProperties.getProperty(BenefitsConstants.PROP_HR_EMAIL) + ","
									+ EmailProperties.getProperty(BenefitsConstants.PROP_FINANCE_MAIL) + ","
									+ EmailProperties.getProperty(BenefitsConstants.PROP_TAX_RETURNS_EMAIL),
							messageBody,

							messageSubject);

				}

				System.out.println("Sending email..");
				Thread emailThread = new Thread(emailService);
				emailThread.start();

			}
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 
	 * pending approval Claims for a particular plans
	 * 
	 * @param
	 * @param
	 * @retuns ModelAndView
	 * 
	 */
	@RequestMapping(value = "/home/controlPanel/flexiPlans/claims/{planId}", method = RequestMethod.GET)
	public ModelAndView showAllClaims(HttpServletRequest request, @PathVariable("planId") Integer planId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "manageClaims");

		List<BenefitPlanClaim> claims = benefitPlanService.listAllApprovals(planId, appContext.getCurrentFiscalYear());

		mav.addObject("claims", claims);

		return mav;
	}

	/**
	 * 
	 * 
	 * Shows admin apporve view for a particular claim
	 * 
	 * @param
	 * @param
	 * @retuns ModelAndView
	 * 
	 */
	@RequestMapping(value = "/home/controlPanel/flexiPlans/claims/approveView/{claimId}", method = RequestMethod.GET)
	public ModelAndView showApproveClaims(HttpServletRequest request, @PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "approveClaim");

		BenefitPlanClaim claim = benefitPlanService.getClaim(claimId); // all
																		// claims
		List<BenefitPlanClaimDetail> claimDetails = claim.getDetails(); // all
																		// claim
																		// details

		List<BenefitPlanClaimDetail> claimNot = benefitPlanService.listOtherClaims(claimId); // other
																								// claim
																								// details

		if (!claimNot.isEmpty() || claimNot != null) {
			Map<String, List<BenefitPlanClaim>> duplicateBills = new HashMap<String, List<BenefitPlanClaim>>();
			for (BenefitPlanClaimDetail detailOfCurrent : claimDetails) {
				for (BenefitPlanClaimDetail detailOfNot : claimNot) {
					if (detailOfCurrent.getBillNo().equals(detailOfNot.getBillNo())) {
						List<BenefitPlanClaim> duplicateClaimsForCurrentBill = null;
						if (duplicateBills.containsKey(detailOfCurrent.getBillNo())) {
							duplicateClaimsForCurrentBill = duplicateBills.get(detailOfCurrent.getBillNo());
						} else {
							duplicateClaimsForCurrentBill = new ArrayList<BenefitPlanClaim>();
						}
						duplicateClaimsForCurrentBill.add(detailOfNot.getClaim());
						duplicateBills.put(detailOfCurrent.getBillNo(), duplicateClaimsForCurrentBill);

					}
				}
			}
			if (!duplicateBills.isEmpty()) {
				mav.addObject("duplicatesFound", true);
				mav.addObject("duplicates", duplicateBills);
			} else {
				mav.addObject("duplicatesFound", false);
			}
		}

		BenefitPlanEmployee planEmployee = benefitPlanService
				.getPlanEmployee(claim.getPlanEmployee().getPlanEmployeeId());

		String docmanUuid = docmanRestClient.getDocmanUuid(BenefitsWSConstants.SCREEN_FLEXI_NEW_CLAIM,
				appContext.getUserLoginKey(),
				planEmployee.getBenefitPlan().getPlanName() + "/" + appContext.getCurrentFiscalYear(),
				claim.getClaimRefNo(), "Employee Code", planEmployee.getEmployee().getEmployeeCode(),
				appContext.getUserName(), "Scanned copy of Bills");

		claim.setDocmanUuid(docmanUuid);
		claim.setUploadUrl(docmanRestClient.getUploadUrl(docmanUuid, appContext.getUserLoginKey()));
		claim.setDownloadUrl(docmanRestClient.getDownloadUrl(docmanUuid, appContext.getUserLoginKey()));
		/*
		 * for (String billno : duplicateBills.keySet())
		 * mav.addObject("BillNo",billno);
		 * 
		 * for (BenefitPlanClaim claims : duplicateBills.values())
		 * mav.addObject("Claims", claims.getClaimRefNo());
		 */

		BenefitPlanEmployee benefitPlanEmployee = benefitPlanService
				.getById(claim.getPlanEmployee().getPlanEmployeeId());
		if (benefitPlanEmployee.getBenefitPlan().getPromptFieldsOnOPT()) {
			List<BenefitPlanEmployeeField> benefitPlanEmployeeField = benefitPlanService
					.listAllField(claim.getPlanEmployee().getPlanEmployeeId());
			mav.addObject("benefitPlanEmployeeField", benefitPlanEmployeeField);
		}
		mav.addObject("claim", claim);
		mav.addObject("employeePlan", benefitPlanEmployee);

		return mav;
	}

	/**
	 * 
	 * HR Approves a claim request
	 * 
	 * @param
	 * @param
	 * @retuns String
	 * 
	 */
	@RequestMapping(value = "/home/controlPanel/flexiPlans/claims/approveView/approve", method = RequestMethod.POST)
	public String approveClaim(HttpServletRequest request, HttpServletResponse response) {

		try {
			appContext = (AppContext) request.getSession().getAttribute("appContext");
			Date auditDate = new Date();

			Integer claimId = Integer.parseInt(request.getParameter("claimId"));

			BenefitPlanClaim claim = benefitPlanService.getClaim(claimId);

			if (claim != null) {

				List<BenefitPlanClaimDetail> claimDetails = claim.getDetails();

				BigDecimal approvedTotal = new BigDecimal(request.getParameter("claimTotal"));

				String claimDetailIds = request.getParameter("claimDetailIds");
				Boolean approved = false;

				if (claimDetailIds == null || "".equals(claimDetailIds)) {
					for (BenefitPlanClaimDetail detail : claimDetails) {
						detail.setApprovedAmount(new BigDecimal(0));
						detail.setUpdatedDate(auditDate);
						detail.setUpdatedBy(appContext.getUserName());
						String approveComments = request
								.getParameter("approveComments" + detail.getBenefitPlanClaimDetailId());
						detail.setBillDesc(approveComments);
						// benefitPlanService.update(detail);
					}

					claim.setTotalApprovedAmount(new BigDecimal(0));
					claim.setStatus(BenefitsConstants.CLAIM_STATUS_HR_REJECT);

					claim.setHrApprovedBy(appContext.getCurrentEmployee().getUserName());
					claim.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
					claim.setUpdatedDate(auditDate);
					if (benefitPlanService.edit(claim)) {

						EmailService emailService = null;
						String messageSubject = "Benefits Portal - Claim Request has been Rejected By HR Team";
						String messageBody = EmailFormatter.newClaimReject(claim);
						emailService = new EmailService(claim.getPlanEmployee().getEmployee().getEmail(), appContext

								.getCurrentEmployee().getEmail(), messageBody,

								messageSubject);

						System.out.println("Sending email..");
						Thread emailThread = new Thread(emailService);
						emailThread.start();

					}

				} else {
					String[] claimDetailsIdArray = claimDetailIds.split(",");
					for (BenefitPlanClaimDetail detail : claimDetails) {
						String approveComments = request
								.getParameter("approveComments" + detail.getBenefitPlanClaimDetailId());
						String approvedAmount = request
								.getParameter("approvedAmount" + detail.getBenefitPlanClaimDetailId());
						for (int i = 0; i < claimDetailsIdArray.length; i++) {
							if (detail.getBenefitPlanClaimDetailId() == Integer.parseInt(claimDetailsIdArray[i])) {
								detail.setApprovedAmount(new BigDecimal(approvedAmount));

								i = claimDetailsIdArray.length;
							} else {

								detail.setApprovedAmount(new BigDecimal(0));
							}
							detail.setBillDesc(approveComments);
						}
						detail.setUpdatedDate(auditDate);
						detail.setUpdatedBy(appContext.getUserName());
					}

					claim.setTotalApprovedAmount(approvedTotal);
					claim.setStatus(BenefitsConstants.CLAIM_STATUS_HR_APPR);
					claim.setHrApprovedBy(appContext.getUserName());
					approved = true;

					claim.setDetails(claimDetails);
					claim.setUpdatedBy(appContext.getCurrentEmployee().getFirstName() + " "
							+ appContext.getCurrentEmployee().getLastName());
					claim.setUpdatedDate(auditDate);

					if (benefitPlanService.edit(claim)) {
						BenefitPlanEmployee planEmployee = benefitPlanRE.calculateAmount(
								benefitPlanService.getPlanEmployee(claim.getPlanEmployee().getPlanEmployeeId()));
						if (benefitPlanService.editPlanEmployee(planEmployee)) {

							/**
							 * Sending email notification for financial head
							 */
							EmailService emailService = null;
							if (benefitPlanService.edit(claim)) {
								String messageSubject = "Benefits Portal - Claim Request has been Approved By HR";
								String messageBody = EmailFormatter.claimRequestApprovedHR(claim);
								emailService = new EmailService(
										EmailProperties.getProperty(BenefitsConstants.PROP_FINANCE_MAIL), "",
										messageBody, messageSubject);

							}
							System.out.println("Sending email..");
							Thread emailThread = new Thread(emailService);
							emailThread.start();

						}
					}

				}
				return "redirect:/home/controlPanel/flexiPlans/claims/"
						+ claim.getPlanEmployee().getBenefitPlan().getBenefitPlanId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/claims/approveView/reject/{claimId}")
	public ModelAndView rejectClaim(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "approveClaim");
		Date auditDate = new Date();

		BenefitPlanClaim claim = benefitPlanService.getClaim(claimId);
		claim.setStatus(BenefitsConstants.CLAIM_STATUS_HR_REJECT);
		String reason = request.getParameter("comments");
		List<BenefitPlanClaimDetail> details = claim.getDetails();
		for (BenefitPlanClaimDetail detail : details) {
			detail.setBillDesc(reason);
			detail.setApprovedAmount(new BigDecimal(0));
			// benefitPlanService.update(detail);

		}
		// claim.setFinReason(reason);
		claim.setHrApprovedBy(appContext.getCurrentEmployee().getUserName());
		claim.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
		claim.setUpdatedDate(auditDate);
		if (benefitPlanService.edit(claim)) {
			mav.addObject("message", "Succesfully Rejected Claim Request");

			EmailService emailService = null;
			String messageSubject = "Benefits Portal - Claim Request has been Rejected By HR Team";
			String messageBody = EmailFormatter.newClaimReject(claim);
			emailService = new EmailService(claim.getPlanEmployee().getEmployee().getEmail(), appContext

					.getCurrentEmployee().getEmail(), messageBody,

					messageSubject);

			System.out.println("Sending email..");
			Thread emailThread = new Thread(emailService);
			emailThread.start();

		} else {
			mav.addObject("message", "Failed to Reject Claim Request");
		}
		return mav;

	}

	/*
	 * Financial approve for claims
	 */

	@RequestMapping(value = "/home/controlPanel/flexiPlans/claims/finApproveView/{claimId}", method = RequestMethod.GET)
	public ModelAndView showFinApproveClaims(HttpServletRequest request, @PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "approveFinClaim");

		BenefitPlanClaim claim = benefitPlanService.getClaim(claimId);

		BenefitPlanEmployee planEmployee = benefitPlanService
				.getPlanEmployee(claim.getPlanEmployee().getPlanEmployeeId());

		String docmanUuid = docmanRestClient.getDocmanUuid(BenefitsWSConstants.SCREEN_FLEXI_NEW_CLAIM,
				appContext.getUserLoginKey(),
				planEmployee.getBenefitPlan().getPlanName() + "/" + appContext.getCurrentFiscalYear(),
				claim.getClaimRefNo(), "Employee Code", planEmployee.getEmployee().getEmployeeCode(),
				appContext.getUserName(), "Scanned copy of Bills");

		claim.setDocmanUuid(docmanUuid);
		claim.setUploadUrl(docmanRestClient.getUploadUrl(docmanUuid, appContext.getUserLoginKey()));
		claim.setDownloadUrl(docmanRestClient.getDownloadUrl(docmanUuid, appContext.getUserLoginKey()));
		mav.addObject("claim", claim);

		BenefitPlanEmployee benefitPlanEmployee = benefitPlanService
				.getById(claim.getPlanEmployee().getPlanEmployeeId());
		if (benefitPlanEmployee.getBenefitPlan().getPromptFieldsOnOPT()) {
			List<BenefitPlanEmployeeField> benefitPlanEmployeeField = benefitPlanService
					.listAllField(claim.getPlanEmployee().getPlanEmployeeId());
			mav.addObject("benefitPlanEmployeeField", benefitPlanEmployeeField);
		}
		mav.addObject("employeePlan", benefitPlanEmployee);

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/claims/finApproveView/approve", method = RequestMethod.POST)
	public String approveFinClaim(HttpServletRequest request, HttpServletResponse response) {
		try {
			appContext = (AppContext) request.getSession().getAttribute("appContext");
			Date auditDate = new Date();
			Integer claimId = Integer.parseInt(request.getParameter("claimId"));
			BenefitPlanClaim claim = benefitPlanService.getClaim(claimId);

			/*
			 * calculating claim periods to record alloted,approved,carry,pay
			 * rolled amounts of each period.
			 */

			BigDecimal monthly_deduction = (claim.getPlanEmployee().getYearlyDeduction()).divide(new BigDecimal(12), 2,
					RoundingMode.HALF_UP);
			BigDecimal quarterlyAmt = monthly_deduction
					.multiply(new BigDecimal(claim.getPlanEmployee().getBenefitPlan().getClaimFrequency()));
			BenefitPlanEmployee benefitPlanEmployee = benefitPlanService
					.getById(claim.getPlanEmployee().getPlanEmployeeId());
			BigDecimal taxableAmt = benefitPlanEmployee.getTaxableAmount().subtract(claim.getTotalApprovedAmount());
			if (taxableAmt.compareTo(new BigDecimal(0)) > 0) {
				benefitPlanEmployee.setTaxableAmount(taxableAmt);
			} else {
				benefitPlanEmployee.setTaxableAmount(new BigDecimal(0));
			}
			/*
			 * List<BenefitPlanEmployeeClaimPeriod> claimPeriods =
			 * benefitPlanService
			 * .listAllClaims(claim.getPlanEmployee().getPlanEmployeeId(),
			 * claim.getPeriodFrom(), claim.getPeriodTo());
			 */
			List<BenefitPlanEmployeeClaimPeriod> claimPeriods = benefitPlanService.listAllClaims(
					claim.getPlanEmployee().getPlanEmployeeId(), claim.getPeriodFrom(), claim.getPeriodTo());
			BenefitPlanEmployeeClaimPeriod claimPeriod = null;
			BigDecimal payRolledAmt = new BigDecimal(0);

			/* if the claimperiod is null for the perticular period */
			if (claimPeriods == null || claimPeriods.size() == 0 || claimPeriods.isEmpty()) {
				BenefitPlanEmployeeClaimPeriod period = new BenefitPlanEmployeeClaimPeriod();
				claimPeriods = new ArrayList<BenefitPlanEmployeeClaimPeriod>();
				Date currentDate = new Date();
				String claimFrequency = claim.getPlanEmployee().getBenefitPlan().getClaimFrequency();
				String fiscalYear = appContext.getCurrentFiscalYear();
				String fiscalYearStartYear[] = fiscalYear.split("-");

				System.out.println("Fiscal Year : " + fiscalYear);
				System.out.println("Fiscal Year Start Date: " + fiscalYearStartYear[0]);

				String YearSplit = (String) fiscalYearStartYear[0].substring(0, 2);
				System.out.println("------Year Splitted---" + YearSplit);

				String initialDate = "01-04-" + fiscalYearStartYear[0];
				String endDate = "31-03-" + YearSplit + fiscalYearStartYear[1];

				System.out.println("Initial Date : " + initialDate);
				System.out.println("-------Current Date--------" + currentDate);
				System.out.println("End Date : " + endDate);

				Date startDate = DataTypeUtil.toDateFromStringddMMyyyy(initialDate);
				Date endFiscalYearDate = DataTypeUtil.toDateFromStringddMMyyyy(endDate);

				System.out.println("-----------Converted Start Date---------" + startDate);
				System.out.println("------------Converted End Date ----------" + endFiscalYearDate);

				Calendar currDate = Calendar.getInstance();
				currDate.setTime(currentDate);

				int currentDateMonth = currDate.get(Calendar.MONTH) + 1;

				System.out.println("-------CurrentDateMonth------" + currentDateMonth);

				int claimFreq = Integer.parseInt(claimFrequency);

				Integer periodCalculation = (currentDateMonth - 4) / claimFreq;
				Date effFrom = claim.getPlanEmployee().getEffFrom();
				Date effTill = new Date();
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(effFrom);
				cal1.set(Calendar.MONTH, (cal1.get(Calendar.MONTH) + claimFreq));
				cal1.add(Calendar.DATE, -1);
				effTill = cal1.getTime();
				Date today = new Date();
				// Date today = new Date();
				Integer c = 12 / claimFreq;
				while (c != 0) {
					if (effFrom.compareTo(today) <= 0 && effTill.compareTo(today) > 0) {
						break;
					}
					System.out.println(claimFreq);
					Calendar cal = Calendar.getInstance();
					cal.setTime(effFrom);
					cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + claimFreq));
					effFrom = cal.getTime();
					cal.setTime(effFrom);
					cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + claimFreq));
					cal.add(Calendar.DATE, -1);
					effTill = cal.getTime();

					c--;
				}
				period.setPeriodFrom(effFrom);
				period.setPeriodTo(effTill);
				period.setPlanEmployee(claim.getPlanEmployee());
				period.setNoOfClaims(1);
				period.setAlottedAmount(quarterlyAmt);
				period.setTotalRequestedAmount(claim.getTotalRequestedAmount());
				period.setCarryForward(BigDecimal.ZERO);
				period.setTotalApprovedAmount(BigDecimal.ZERO);
				period.setPayRolledAmount(BigDecimal.ZERO);
				period.setUpdatedDate(new Date());
				period.setUpdatedBy(appContext.getUserName());
				period.setCreatedDate(new Date());
				period.setCreatedBy(appContext.getUserName());
				benefitPlanService.insert(period);
				claimPeriods.add(period);
			}

			/* Till here */

			if (claimPeriods.get(0).getTotalApprovedAmount() != null
					&& claimPeriods.get(0).getTotalApprovedAmount() != BigDecimal.ZERO) {
				claimPeriod = claimPeriods.get(0);

				claimPeriod.setTotalApprovedAmount(
						claimPeriod.getTotalApprovedAmount().add(claim.getTotalApprovedAmount()));
				BigDecimal carry = claimPeriod.getCarryForward().subtract(claim.getTotalApprovedAmount());
				claimPeriod.setCarryForward(carry);
				if (carry.compareTo(new BigDecimal(0)) <= 0) {
					payRolledAmt = quarterlyAmt;

				} else {
					if (benefitPlanEmployee.getNonPayrolledAmount().compareTo(new BigDecimal(0)) >= 0) {
						if (claimPeriod.getTotalApprovedAmount().compareTo(quarterlyAmt) >= 0) {

							payRolledAmt = quarterlyAmt;
						} else {
							payRolledAmt = claimPeriod.getTotalApprovedAmount();
						}
					} else {
						payRolledAmt = claim.getTotalApprovedAmount()
								.subtract(benefitPlanEmployee.getNonPayrolledAmount());
					}
				}

				if (carry.compareTo((new BigDecimal(0))) < 0) {
					benefitPlanEmployee.setNonPayrolledAmount(carry);

				} else {
					benefitPlanEmployee.setNonPayrolledAmount(carry);

				}

				claimPeriod.setCarryForward(carry);
				claimPeriod.setPayRolledAmount(payRolledAmt);
				claimPeriod.setUpdatedDate(new Date());
				claimPeriod.setUpdatedBy(appContext.getUserName());

				benefitPlanService.update(claimPeriod);
				benefitPlanService.editPlanEmployee(benefitPlanEmployee);
			} else {
				// BenefitPlanEmployeeClaimPeriod claimPeriod = new
				// BenefitPlanEmployeeClaimPeriod();
				claimPeriod = claimPeriods.get(0);
				claimPeriod.setTotalApprovedAmount(claim.getTotalApprovedAmount());
				BigDecimal carry = new BigDecimal(0);
				BigDecimal nonPayRolledAmt = new BigDecimal(0);

				carry = (quarterlyAmt.add(benefitPlanEmployee.getNonPayrolledAmount())
						.subtract(claim.getTotalApprovedAmount()));
				if (carry.compareTo(new BigDecimal(0)) <= 0) {
					payRolledAmt = quarterlyAmt;

				} else {
					if (benefitPlanEmployee.getNonPayrolledAmount().compareTo(new BigDecimal(0)) >= 0) {
						if (claim.getTotalApprovedAmount().compareTo(quarterlyAmt) > 0) {
							payRolledAmt = quarterlyAmt;
						} else {
							payRolledAmt = claim.getTotalApprovedAmount();
						}
					} else {
						payRolledAmt = claim.getTotalApprovedAmount()
								.subtract(benefitPlanEmployee.getNonPayrolledAmount());
					}
				}

				if (carry.compareTo((new BigDecimal(0))) < 0) {
					benefitPlanEmployee.setNonPayrolledAmount(carry);

				} else {
					benefitPlanEmployee.setNonPayrolledAmount(carry);

				}

				claimPeriod.setCarryForward(carry);
				claimPeriod.setPayRolledAmount(payRolledAmt);
				claimPeriod.setUpdatedDate(new Date());
				claimPeriod.setUpdatedBy(appContext.getUserName());

				benefitPlanService.editPlanEmployee(benefitPlanEmployee);
				benefitPlanService.update(claimPeriod);

			}

			claim.setUpdatedBy(appContext.getUserName());
			claim.setUpdatedDate(auditDate);
			claim.setStatus(BenefitsConstants.CLAIM_STATUS_FIN_APPR);
			claim.setFinApprovedBy(appContext.getCurrentEmployee().getUserName());
			// claim.setFinReason(request.getParameter("approveComments"));
			/**
			 * Sending email notification for users
			 */
			EmailService emailService = null;
			if (benefitPlanService.edit(claim)) {

				String messageSubject = "Benefits Portal - Claim Request has been Approved";
				String messageBody = EmailFormatter.claimRequestApproved(claim);
				emailService = new EmailService(claim.getPlanEmployee().getEmployee().getEmail(),
						EmailProperties.getProperty(BenefitsConstants.PROP_HR_EMAIL) + ","
								+ EmailProperties.getProperty(BenefitsConstants.PROP_FINANCE_MAIL) + ","
								+ EmailProperties.getProperty(BenefitsConstants.PROP_TAX_RETURNS_EMAIL),
						messageBody,

						messageSubject);

			}

			System.out.println("Sending email..");
			Thread emailThread = new Thread(emailService);
			emailThread.start();

			return "redirect:/home/controlPanel/flexiPlans/claims/"
					+ claim.getPlanEmployee().getBenefitPlan().getBenefitPlanId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
		/*
		 * return "redirect:/home/controlPanel/flexiPlans/claims/" +
		 * claim.getPlanEmployee().getBenefitPlan().getBenefitPlanId();
		 */
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/claims/finApproveView/reject/{claimId}")
	public ModelAndView rejectFinClaim(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("claimId") Integer claimId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "approveFinClaim");
		Date auditDate = new Date();

		BenefitPlanClaim claim = benefitPlanService.getClaim(claimId);
		claim.setStatus(BenefitsConstants.CLAIM_STATUS_FIN_REJECT);
		claim.setFinApprovedBy(appContext.getCurrentEmployee().getUserName());
		claim.setUpdatedDate(auditDate);
		/*
		 * //claim.setFinReason(request.getParameter("approveComments"));
		 * EmailService emailService = null; if (benefitPlanService.edit(claim))
		 * { String messageSubject =
		 * "Benefits Portal - Claim Request has been Rejected By Finance Team";
		 * String messageBody = EmailFormatter.newClaimfinReject(claim);
		 * emailService = new EmailService(
		 * claim.getPlanEmployee().getEmployee().getEmail(), appContext
		 * 
		 * .getCurrentEmployee().getEmail() + "," + EmailProperties
		 * .getProperty(BenefitsConstants.PROP_FINANCE_MAIL), messageBody,
		 * 
		 * messageSubject); } System.out.println("Sending email.."); Thread
		 * emailThread = new Thread(emailService); emailThread.start();
		 */

		return mav;

	}

	/**
	 * shows all simple claims for a plan Employee
	 * 
	 * Purpose of method
	 * 
	 * @param
	 * @param
	 * @retuns ModelAndView
	 * 
	 */
	@RequestMapping(value = "/home/controlPanel/flexiPlans/simpleClaims/{planEmployeeId}", method = RequestMethod.GET)
	public ModelAndView showAllSimpleClaims(HttpServletRequest request, @PathVariable("planId") Integer pathId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "");

		/**
		 * write code here
		 */

		return mav;
	}

	/**
	 * 
	 * 
	 * Shows upload page for simple claim upload excel
	 * 
	 * @param
	 * @param
	 * @return ModelAndView
	 * 
	 */
	@RequestMapping(value = "/home/controlPanel/flexiPlans/directClaims/{planId}")
	public ModelAndView showUploadSimpleClaim(HttpServletRequest request, @PathVariable("planId") Integer planId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "simpleClaimUpload");

		/**
		 * write code here
		 */
		List<BenefitPlanDirectClaim> claims = benefitPlanService.listDirectClaimsByPlan(planId);
		mav.addObject("claims", claims);
		mav.addObject("planId", planId);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/directClaim/new/{planId}", method = RequestMethod.GET)
	public ModelAndView uploadSimpleClaimAdd(HttpServletRequest request, @PathVariable("planId") Integer planId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		BenefitPlanEmployee planEmployee = benefitPlanService.getPlanEmployee(planId);
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "addDirectClaims");
		BenefitPlanDirectClaimBean claim = new BenefitPlanDirectClaimBean();
		Integer nextClaimId = benefitPlanService.getUniqueDirectClaimId();
		claim.setDirectClaimId(nextClaimId + "");
		claim.setClaimRefNo(BenefitsUtil.generateClaimRefNo(nextClaimId, planEmployee.getBenefitPlan().getPlanName(),
				appContext.getCurrentEmployee().getEmployeeCode(), appContext.getCurrentFiscalYear()));
		mav.addObject("claims", claim);

		request.getSession().setAttribute("planId", planId);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/directClaim/new/{planId}", method = RequestMethod.POST)
	public String uploadSimpleClaimAddSubmit(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("claims") BenefitPlanDirectClaimBean benefitPlanDirectClaimBean,
			@PathVariable Integer planId) throws ParseException {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		System.out.println("====you are here===");
		Integer employeePlanId = (Integer) request.getSession().getAttribute("planId");

		BenefitPlanDirectClaim benefitPlanDirectClaim = benefitPlanService
				.getDirectClaim(Integer.parseInt(benefitPlanDirectClaimBean.getDirectClaimId()));

		BenefitPlanEmployee benefitPlanEmployee = benefitPlanService.getPlanEmployee(employeePlanId);

		benefitPlanDirectClaim.setPlanEmployee(benefitPlanEmployee);

		benefitPlanDirectClaim.setExtRefNo(benefitPlanDirectClaimBean.getExtRefNo());
		benefitPlanDirectClaim.setAmount(new BigDecimal(benefitPlanDirectClaimBean.getAmount()));
		benefitPlanDirectClaim
				.setPeriodFrom(DataTypeUtil.toDateFromStringddMMMMMyyyy(benefitPlanDirectClaimBean.getPeriodFrom()));
		benefitPlanDirectClaim
				.setPeriodTo(DataTypeUtil.toDateFromStringddMMMMMyyyy(benefitPlanDirectClaimBean.getPeriodTo()));
		benefitPlanDirectClaim
				.setIssuedDate(DataTypeUtil.toDateFromStringddMMMMMyyyy(benefitPlanDirectClaimBean.getIssuedDate()));
		benefitPlanDirectClaim.setIssuedBy(benefitPlanDirectClaimBean.getIssuedBy());
		benefitPlanDirectClaim.setCreatedBy(appContext.getUserName());
		benefitPlanDirectClaim.setUpdatedBy(appContext.getUserName());
		benefitPlanDirectClaim.setFiscalYear(appContext.getCurrentFiscalYear());
		benefitPlanDirectClaim.setStatus(BenefitsConstants.CLAIM_STATUS_HR_APPR);

		System.out.println("====you are here===2");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"redirect://home/controlPanel/flexiPlans/directClaims/" + employeePlanId);
		benefitPlanService.saveDirectClaimsByPlan(benefitPlanDirectClaim);
		/*
		 * 
		 * 
		 * Email after direct claim upload success
		 *//**
			 * Sending email notification for users
			 */
		// System.out.println("=============="+benefitPlanEmployee.getEmployee().getEmail());
		// System.out.println("=============="+appContext.getCurrentEmployee().getEmail());

		EmailService emailService = null;
		String messageSubject = "Benefits Portal - Direct Claim Added Successfully";

		String messageBody = EmailFormatter.uploadDirectClaimSuccess(benefitPlanDirectClaim, benefitPlanEmployee);

		emailService = new EmailService(benefitPlanEmployee.getEmployee().getEmail(),
				appContext.getCurrentEmployee().getEmail(), messageBody, messageSubject);

		System.out.println("----------------Sending email---------------");
		Thread emailThread = new Thread(emailService);
		emailThread.start();

		System.out.println("====you are here===3");
		return mav.getViewName();
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/rollBackApprovedPlans/{planEmployeeId}")
	public ModelAndView rollBackApprovedPlans(HttpServletRequest request,
			@PathVariable("planEmployeeId") Integer planEmployeeId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = null;

		List<BenefitPlanClaim> claims = benefitPlanService.listClaims(planEmployeeId);
		Integer claimSize = claims.size();
		System.out.println("Claim size" + claimSize);
		BenefitPlanEmployee benefitPlanEmployee = benefitPlanService.getById(planEmployeeId);

		if (benefitPlanEmployee.getActive() == false || claimSize != 0) {
			mav = AuthorizationUtil.authorizeAdmin(appContext, "redirect:/home/controlPanel/flexiPlans/optedEmployees");

		} else {
			mav = AuthorizationUtil.authorizeAdmin(appContext, "rollBackApprovedPlans");
		}

		if (benefitPlanEmployee.getBenefitPlan().getOptDocumentsRequired()) {
			List<BenefitPlanEmployeeDoc> planEmployeeDocs = benefitPlanEmployee.getDocuments();
			/*
			 * List<DocumentVO> doclist = new ArrayList<DocumentVO>(); for
			 * (BenefitPlanEmployeeDoc doc : planEmployeeDocs) { DocumentVO vo =
			 * new DocumentVO(); vo.setDocumentType(doc.getBenefitPlanDocument()
			 * .getDocumentName());
			 * 
			 * if (doc.getDocmanUUId() != null &&
			 * !"".equals(doc.getDocmanUUId()))
			 * vo.setDocManUuid(doc.getDocmanUUId());
			 * vo.setUploadUrl(docmanRestClient.getUploadUrl(
			 * doc.getDocmanUUId(), appContext.getUserLoginKey()));
			 * vo.setDownloadUrl(docmanRestClient.getDownloadUrl(
			 * doc.getDocmanUUId(), appContext.getUserLoginKey()));
			 * 
			 * vo.setDocumentId(doc.getPlanEmployeeDocId() + "");
			 * 
			 * doclist.add(vo); } mav.addObject("docList", doclist);
			 */
		}
		mav.addObject("employeePlan", benefitPlanEmployee);
		mav.addObject("claimSize", claimSize);
		mav.addObject("claims", claims);
		mav.addObject("planEmployeeId", planEmployeeId);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/rollBackApprovedPlans/new/{planEmployeeId}")
	public ModelAndView rollBackApprovedPlansProcess(HttpServletRequest request,
			@PathVariable("planEmployeeId") Integer planEmployeeId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		/*
		 * ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
		 * "rollBackApprovedPlans");
		 */
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"redirect:/home/controlPanel/flexiPlans/optedEmployees");

		BenefitPlanEmployee benefitPlanEmployee = benefitPlanService.getPlanEmployee(planEmployeeId);
		benefitPlanEmployee.setPlanEmployeeId(planEmployeeId);
		benefitPlanEmployee.setActive(true);
		benefitPlanEmployee.setStatus(BenefitsConstants.EMP_PLAN_OPT_STATUS_NOT_APPROVED);
		benefitPlanEmployee.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
		benefitPlanService.rollBackApprovedPlans(benefitPlanEmployee);

		if (benefitPlanEmployee.getStatus() == BenefitsConstants.EMP_PLAN_OPT_STATUS_NOT_APPROVED) {
			System.out.println("---- Status is : Not Approved ----");
			System.out.println("---- Status is :  ----" + benefitPlanEmployee.getStatus());
		}

		System.out.println("------------roll back process-----------");

		mav.addObject("planEmpId", planEmployeeId);
		return mav;
	}

}
