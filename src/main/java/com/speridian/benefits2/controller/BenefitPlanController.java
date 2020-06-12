package com.speridian.benefits2.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DefaultEditorKit.BeepAction;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.DateUtil;
import org.jboss.logging.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.speridian.benefits2.beans.BenefitPlanBean;
import com.speridian.benefits2.beans.ClaimPeriodBean;
import com.speridian.benefits2.beans.DependentCategoryBean;
import com.speridian.benefits2.beans.DocumentVO;
import com.speridian.benefits2.beans.EmployeePlanBean;
import com.speridian.benefits2.beans.EmployeePlanClaimBean;
import com.speridian.benefits2.beans.LTASearchBean;
import com.speridian.benefits2.beans.SearchBean;
import com.speridian.benefits2.email.EmailFormatter;
import com.speridian.benefits2.email.EmailProperties;
import com.speridian.benefits2.email.EmailService;
import com.speridian.benefits2.model.pojo.BenefitPlan;
import com.speridian.benefits2.model.pojo.BenefitPlanBand;
import com.speridian.benefits2.model.pojo.BenefitPlanCategory;
import com.speridian.benefits2.model.pojo.BenefitPlanCategoryMisc;
import com.speridian.benefits2.model.pojo.BenefitPlanClaim;
import com.speridian.benefits2.model.pojo.BenefitPlanContact;
import com.speridian.benefits2.model.pojo.BenefitPlanDependentCategory;
import com.speridian.benefits2.model.pojo.BenefitPlanDirectClaim;
import com.speridian.benefits2.model.pojo.BenefitPlanDocument;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployee;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployeeClaimPeriod;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployeeDependentDetail;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployeeDetail;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployeeDoc;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployeeField;
import com.speridian.benefits2.model.pojo.BenefitPlanField;
import com.speridian.benefits2.model.pojo.BenefitPortalFileImport;
import com.speridian.benefits2.model.pojo.BenefitsProperty;
import com.speridian.benefits2.model.pojo.Dependent;
import com.speridian.benefits2.model.pojo.EmpBenPlansYrlyOpts;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.PlanDenyReasonsMaster;
import com.speridian.benefits2.model.pojo.PlanEmployeeDenies;
import com.speridian.benefits2.model.pojo.TaxRegime;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.re.BenefitPlanRE;
import com.speridian.benefits2.service.BenefitPlanService;
import com.speridian.benefits2.service.BenefitPortalFileImportService;
import com.speridian.benefits2.service.EmployeeService;
import com.speridian.benefits2.service.SettingsService;
import com.speridian.benefits2.service.TaxRegimeServiceImpl;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.util.AuthorizationUtil;
import com.speridian.benefits2.util.DataTypeUtil;
import com.speridian.benefits2.ws.client.docman.rest.DocManProperties;
import com.speridian.benefits2.ws.client.docman.rest.DocmanRestClient;
import com.speridian.benefits2.ws.constants.BenefitsWSConstants;
import com.speridian.benefits2.model.util.BenefitsConstants;

/**
 * 
 * <pre>
 * Controller take care of benefit plans CRUDs
 * </pre>
 * 
 * @author jithin.kuriakose
 * @since 05-Feb-2017
 * 
 */
@Controller
@SessionAttributes({ "planPojo", "empPlanYearlyOpts", "searchBean" })
public class BenefitPlanController {

	@Autowired
	BenefitPortalFileImportService benefitPortalFileImportService;

	@Autowired
	BenefitPlanService benefitPlanService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	BenefitPlanRE benefitPlanRE;

	@Autowired
	SettingsService settingsService;

	@Autowired
	DocmanRestClient docmanRestClient;
	
	TaxRegimeServiceImpl taxRegimeServiceImpl=new TaxRegimeServiceImpl();

	AppContext appContext;

	/**
	 * Shows the very first page(manageBenefitPlans.jsp) of all non-deleted
	 * Benefit Plans
	 * 
	 * @param request
	 * @param response
	 * @return mav
	 */
	@RequestMapping(value = "/home/controlPanel/flexiPlans", method = RequestMethod.GET)
	public ModelAndView showManagePlans(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "manageBenefitPlans");

		List<BenefitPlan> plans = benefitPlanService.listAllForAdmin();
		mav.addObject("plans", plans);

		return mav;
	}

	
	
	/**
	 * Shows the add new plan page(addNewBenefitPlan.jsp)
	 * 
	 * @param request
	 * @param response
	 * @return mav
	 */
	@RequestMapping(value = "/home/controlPanel/flexiPlans/new", method = RequestMethod.GET)
	public ModelAndView showAddPlans(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "editBenefitPlan");

		BenefitPlan benefitPlan = new BenefitPlan();
		BenefitPlanBean planBean = new BenefitPlanBean();
		planBean.setMode("add");
		planBean.setStep(BenefitPlanService.STEP_ONE_MAIN);
		mav.addObject("planBean", planBean);
		mav.addObject("planPojo", benefitPlan);

		return mav;
	}

	/**
	 * Add a new plan to database Shows the add new plan
	 * page(addNewBenefitPlan.jsp)
	 * 
	 * @param request
	 * @param response
	 * @param planBean
	 * @return mav
	 */

	@RequestMapping(value = "/home/controlPanel/flexiPlans/new/amountBreakup", method = RequestMethod.POST)
	public ModelAndView savePlanAmount(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("planBean") BenefitPlanBean planBean) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = null;
		System.out.println("**********inside amount breakup*******" + planBean);
		BenefitPlan benefitPlan = (BenefitPlan) request.getSession().getAttribute("planPojo");
		System.out.println("****pojo***" + benefitPlan);
		mav = AuthorizationUtil.authorizeAdmin(appContext, "editBenefitPlanAmount");
		if (planBean.getMode().equals("add")) {
			planBean.setYearlyClaim("0");

			planBean.setYearlyDeduction("0");
			benefitPlan.setActive(Boolean.TRUE);
			benefitPlan.setDeleted(Boolean.FALSE);
			benefitPlan.setPlanName(planBean.getPlanName());
			benefitPlan.setPlanDesc(planBean.getPlanDesc());
			benefitPlan.setActive(planBean.getActive());
			benefitPlan.setClaimDocumentsRequired(planBean.getClaimDocsRequired());

			if (planBean.getEffFrom() != null) {
				benefitPlan.setEffFrom(DataTypeUtil.toDateFromStringddMMMMMyyyy(planBean.getEffFrom()));
			}
			if (planBean.getEffTill() != null) {
				benefitPlan.setEffTill(DataTypeUtil.toDateFromStringddMMMMMyyyy(planBean.getEffTill()));
			}
			benefitPlan.setActive(planBean.getActive());

			benefitPlan.setUpdatedBy(appContext.getUserName());
			benefitPlan.setCreatedBy(appContext.getUserName());

		} else {
			if (benefitPlan.getClaimType().equals(BenefitsConstants.BENEFIT_PLAN_CLAIM_TYPE_FLAT)) {
				planBean.setYearlyClaim(benefitPlan.getYearlyClaim().toString());
				planBean.setYearlyDeduction(benefitPlan.getYearlyDeduction().toString());
			}
			/*
			 * else if (benefitPlan.getClaimType().equals(BenefitsConstants.
			 * BENEFIT_PLAN_CLAIM_TYPE_CATEGORYWISE)) {
			 * 
			 * }
			 */
			planBean.setClaimType(benefitPlan.getClaimType());

			benefitPlan.setPlanName(planBean.getPlanName());
			benefitPlan.setPlanDesc(planBean.getPlanDesc());
			benefitPlan.setActive(planBean.getActive());
			benefitPlan.setClaimDocumentsRequired(planBean.getClaimDocsRequired());

			if (planBean.getEffFrom() != null) {
				benefitPlan.setEffFrom(DataTypeUtil.toDateFromStringddMMMMMyyyy(planBean.getEffFrom()));
			}
			if (planBean.getEffTill() != null) {
				benefitPlan.setEffTill(DataTypeUtil.toDateFromStringddMMMMMyyyy(planBean.getEffTill()));
			}
			benefitPlan.setActive(planBean.getActive());
		}

		List<String> bands = benefitPlanService.listAllBands();
		mav.addObject("bands", bands);
		List<String> relationships = employeeService.listAllRelationships();
		mav.addObject("relationships", relationships);

		mav.addObject("planPojo", benefitPlan);
		mav.addObject("planBean", planBean);

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/new/contacts", method = RequestMethod.POST)
	public ModelAndView savePlanContacts(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("planBean") BenefitPlanBean planBean) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = null;
		System.out.println("**********inside amount contacts*******" + planBean);
		BenefitPlan benefitPlan = (BenefitPlan) request.getSession().getAttribute("planPojo");
		System.out.println("*****pojo***" + benefitPlan);
		mav = AuthorizationUtil.authorizeAdmin(appContext, "editBenefitPlanContacts");
		/*
		 * if(planBean.getMode().equals("edit")) {
		 * planBean.setContactLength(benefitPlan.getContacts().size()); }
		 */
		String claimType = planBean.getClaimType();

		benefitPlan.setClaimType(claimType);
		benefitPlan.setClaimFrequency(planBean.getClaimFrequency());

		if (BenefitsConstants.BENEFIT_PLAN_CLAIM_TYPE_FLAT.equals(claimType)) {
			benefitPlan.setYearlyClaim(new BigDecimal(planBean.getYearlyClaim()));
			benefitPlan.setYearlyDeduction(new BigDecimal(planBean.getYearlyDeduction()));
		} else if (BenefitsConstants.BENEFIT_PLAN_CLAIM_TYPE_BANDWISE.equals(claimType)) {
			List<String> bands = benefitPlanService.listAllBands();
			List<BenefitPlanBand> planBands = new ArrayList<BenefitPlanBand>();
			for (String band : bands) {
				String bandAmount = request.getParameter("bandAmount" + band);
				if (bandAmount != null && !("".equals(bandAmount))) {
					BenefitPlanBand planBand = new BenefitPlanBand();
					planBand.setBenefitPlan(benefitPlan);
					planBand.setBand(band);
					planBand.setAmount(new BigDecimal(bandAmount));

					planBand.setCreatedBy(appContext.getUserName());
					planBand.setUpdatedBy(appContext.getUserName());

					planBands.add(planBand);
				}
			}
			benefitPlan.setPlanBands(planBands);
			benefitPlan.setPlanCategories(null);
			mav.addObject("planBean", planBean);
			mav.addObject("planPojo", benefitPlan);
		} else if (BenefitsConstants.BENEFIT_PLAN_CLAIM_TYPE_CATEGORYWISE.equals(claimType)) {
			List<BenefitPlanCategory> planCategories = new ArrayList<BenefitPlanCategory>();
			int categoryCount = 0;
			try {
				categoryCount = Integer.parseInt(request.getParameter("ctgCount"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (categoryCount != 0) {
				for (int i = 1; i <= categoryCount; i++) {
					String categoryName = request.getParameter("ctg" + i + "Name");
					String categoryAmount = request.getParameter("ctg" + i + "Amount");
					String categoryTotalAmount = request.getParameter("ctg" + i + "TotalAmount");

					if (categoryName == null || "".equals(categoryName)) {
						categoryName = "Category " + i;
					}

					if (categoryAmount != null && !("".equals(categoryAmount)) && categoryTotalAmount != null
							&& !("".equals(categoryTotalAmount))) {
						BenefitPlanCategory category = new BenefitPlanCategory();
						category.setBenefitPlan(benefitPlan);
						category.setCategoryAmount(new BigDecimal(categoryAmount));
						category.setCategoryName(categoryName);
						category.setCategoryTotalAmount(new BigDecimal(categoryTotalAmount));

						List<BenefitPlanCategoryMisc> listMiscs = null;

						for (int j = 1; j <= 2; j++) {
							String miscName = request.getParameter("misc" + j + "Ctg" + i + "Name");
							String miscAmount = request.getParameter("misc" + j + "Ctg" + i + "Amount");
							if ((miscAmount != null && miscName != null && !("".equals(miscAmount))
									&& !("".equals(miscName))) && j == 1) {

								listMiscs = new ArrayList<BenefitPlanCategoryMisc>();
							}
							if ((miscAmount != null && miscName != null && !("".equals(miscAmount))
									&& !("".equals(miscName))) && j == 2 && listMiscs == null) {

								listMiscs = new ArrayList<BenefitPlanCategoryMisc>();
							}

							if (miscAmount != null && miscName != null && !("".equals(miscAmount))
									&& !("".equals(miscName))) {
								BenefitPlanCategoryMisc misc = new BenefitPlanCategoryMisc();
								misc.setPlanCategory(category);
								misc.setMiscName(miscName);
								misc.setMiscAmount(new BigDecimal(miscAmount));

								misc.setCreatedBy(appContext.getUserName());
								misc.setUpdatedBy(appContext.getUserName());

								listMiscs.add(misc);

								System.out.println(miscName + " - " + miscAmount + " - " + categoryName + " - "
										+ categoryAmount + " - " + categoryTotalAmount);
							}
						}
						category.setMiscs(listMiscs);

						category.setCreatedBy(appContext.getUserName());
						category.setUpdatedBy(appContext.getUserName());

						planCategories.add(category);
					}
				}
				benefitPlan.setPlanCategories(planCategories);
				benefitPlan.setPlanBands(null);
			}

		} else if (BenefitsConstants.BENEFIT_PLAN_CLAIM_TYPE_DEPENDENTWISE.equals(claimType)) {
			benefitPlan.setYearlyClaim(new BigDecimal(planBean.getYearlyClaim()));
			benefitPlan.setYearlyDeduction(new BigDecimal(planBean.getYearlyDeduction()));

			List<BenefitPlanDependentCategory> depCategories = new ArrayList<BenefitPlanDependentCategory>();
			for (int i = 1; i <= 10; i++) {
				String relationship = request.getParameter("depRelationship" + i);
				Short minAge = null;
				Short maxAge = null;
				if (request.getParameter("depMinimumAge" + i) != null
						&& !("".equals(request.getParameter("depMinimumAge" + i)))) {
					minAge = Short.parseShort(request.getParameter("depMinimumAge" + i));
				}
				if (request.getParameter("depMaximumAge" + i) != null
						&& !("".equals(request.getParameter("depMaximumAge" + i)))) {
					maxAge = Short.parseShort(request.getParameter("depMaximumAge" + i));
				}

				String yearlyClaim = request.getParameter("depYearlyClaim" + i);
				String yearlyDeduction = request.getParameter("depYearlyDeduction" + i);
				if (relationship != null && !("".equals(relationship)) && minAge != null && maxAge != null
						&& yearlyClaim != null && !("".equals(yearlyClaim)) && yearlyDeduction != null
						&& !("".equals(yearlyDeduction))) {
					BenefitPlanDependentCategory depCategory = new BenefitPlanDependentCategory(benefitPlan,
							relationship, minAge, maxAge, new BigDecimal(yearlyClaim), new BigDecimal(yearlyDeduction));
					depCategory.setUpdatedBy(appContext.getUserName());
					depCategory.setCreatedBy(appContext.getUserName());
					depCategory.setUpdatedDate(new Date());
					depCategory.setCreatedDate(new Date());

					depCategories.add(depCategory);
				}
			}
			benefitPlan.setDependentCategories(depCategories);
		}

		List<String> designations = employeeService.listAllDesignations("HR");

		mav.addObject("designations", designations);
		mav.addObject("planBean", planBean);
		mav.addObject("planPojo", benefitPlan);

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/new/docs", method = RequestMethod.POST)
	public ModelAndView savePlanDocs(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("planBean") BenefitPlanBean planBean) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = null;
		System.out.println("**********inside docs*******" + planBean);
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		mav = AuthorizationUtil.authorizeAdmin(appContext, "editBenefitPlanDocs");

		BenefitPlan benefitPlan = (BenefitPlan) request.getSession().getAttribute("planPojo");
		System.out.println("*pojo***" + benefitPlan);

		if (planBean.getMode().equals("edit")) {
			planBean.setDocumentLength(benefitPlan.getDocuments().size());
		}

		List<BenefitPlanContact> contacts = new ArrayList<BenefitPlanContact>();
		for (int i = 1; i <= 3; i++) {
			String contactName = request.getParameter("contact" + i + "Name");
			String contactEmail = request.getParameter("contact" + i + "Email");
			String contactPhone1 = request.getParameter("contact" + i + "Phone1");
			String contactPhone2 = request.getParameter("contact" + i + "Phone2");
			String contactDesignation = request.getParameter("contact" + i + "Designation");

			String primaryContactFlag = request.getParameter("contact" + i + "primaryContactFlag");
			Boolean primaryPhoneFlag = true;

			if (contactName != null && contactEmail != null && !("".equals(contactName))
					&& !("".equals(contactEmail))) {

				if (primaryContactFlag == null) {
					primaryPhoneFlag = false;
				} else if (primaryContactFlag.equalsIgnoreCase("on")) {
					primaryPhoneFlag = true;
				}

				BenefitPlanContact contact = new BenefitPlanContact();
				contact.setBenefitPlan(benefitPlan);

				contact.setDesignation(contactDesignation);
				contact.setEmail(contactEmail);
				contact.setFullName(contactName);
				contact.setPhone1(contactPhone1);
				contact.setPhone2(contactPhone2);

				contact.setPrimary(primaryPhoneFlag);

				contact.setUpdatedBy(appContext.getUserName());
				contact.setCreatedBy(appContext.getUserName());

				contacts.add(contact);
			}

		}
		benefitPlan.setContacts(contacts);

		mav.addObject("planBean", planBean);
		mav.addObject("planPojo", benefitPlan);

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/new/summary", method = RequestMethod.POST)
	public ModelAndView savePlanSummary(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("planBean") BenefitPlanBean planBean) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = null;
		System.out.println("**********inside summary*******" + planBean);
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		mav = AuthorizationUtil.authorizeAdmin(appContext, "editBenefitPlanSummary");
		BenefitPlan benefitPlan = (BenefitPlan) request.getSession().getAttribute("planPojo");
		System.out.println("*pojo***" + benefitPlan);
		List<BenefitPlanDocument> documents = new ArrayList<BenefitPlanDocument>();
		for (int i = 1; i <= 5; i++) {
			String documentName = request.getParameter("doc" + i + "Name");
			String mandatoryFlag = request.getParameter("doc" + i + "Mandatory");
			System.out.println("---Mandatory Flag---" + mandatoryFlag);

			/*
			 * Boolean mandatory =
			 * Boolean.parseBoolean(request.getParameter("doc" + i +
			 * "Mandatory"));
			 */
			Boolean mandatory = true;

			if (documentName != null && !("".equals(documentName))) {

				if (mandatoryFlag == null) {
					mandatory = false;
				} else if (mandatoryFlag.equalsIgnoreCase("on")) {
					mandatory = true;
				}

				BenefitPlanDocument document = new BenefitPlanDocument();
				document.setBenefitPlan(benefitPlan);
				document.setDocumentName(documentName);
				document.setMandatory(mandatory);

				document.setUpdatedBy(appContext.getUserName());
				document.setCreatedBy(appContext.getUserName());

				documents.add(document);
			}
		}
		if (documents.size() > 0) {
			benefitPlan.setOptDocumentsRequired(Boolean.TRUE);
		} else {
			benefitPlan.setOptDocumentsRequired(Boolean.FALSE);
		}

		benefitPlan.setDocuments(documents);

		mav.addObject("savedStatus", null);

		mav.addObject("planBean", planBean);
		mav.addObject("planPojo", benefitPlan);

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/new/save", method = RequestMethod.POST)
	public ModelAndView savePlan(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("planBean") BenefitPlanBean planBean) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = null;

		appContext = (AppContext) request.getSession().getAttribute("appContext");
		mav = AuthorizationUtil.authorizeAdmin(appContext, "editBenefitPlanSummary");

		BenefitPlan benefitPlan = (BenefitPlan) request.getSession().getAttribute("planPojo");
		System.out.println("*pojo***" + benefitPlan);
		if (benefitPlanService.add(benefitPlan)) {
			mav.addObject("savedStatus", Boolean.TRUE);
		} else {
			mav.addObject("savedStatus", Boolean.FALSE);
		}
		mav.addObject("planBean", planBean);
		mav.addObject("planPojo", benefitPlan);

		return mav;
	}

	/**
	 * Shows the edit plan page(addNewBenefitPlan.jsp)
	 * 
	 * @param request
	 * @param response
	 * @return mav
	 */
	@RequestMapping(value = "/home/controlPanel/flexiPlans/new/edit", method = RequestMethod.POST)
	public ModelAndView showEditPlans(@ModelAttribute("planBean") BenefitPlanBean planBean,
			HttpServletRequest request) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = null;

		BenefitPlan benefitPlan = (BenefitPlan) request.getSession().getAttribute("planPojo");

		System.out.println("**********inside edit*******" + planBean);
		if (benefitPlan != null) {

			mav = AuthorizationUtil.authorizeAdmin(appContext, "editBenefitPlan");

			System.out.println("*pojo***" + benefitPlan);

			planBean.setActive(benefitPlan.getActive());
			planBean.setClaimType(benefitPlan.getClaimType());
			if (benefitPlan.getEffFrom() != null) {
				planBean.setEffFrom(DataTypeUtil.toDateddMMMMMyyyy(benefitPlan.getEffFrom()));
			}

			if (benefitPlan.getEffTill() != null) {
				planBean.setEffTill(DataTypeUtil.toDateddMMMMMyyyy(benefitPlan.getEffTill()));
			}
			planBean.setPlanDesc(benefitPlan.getPlanDesc());
			planBean.setPlanName(benefitPlan.getPlanName());

			if (benefitPlan.getYearlyClaim() != null) {
				planBean.setYearlyClaim(benefitPlan.getYearlyClaim().toPlainString());
			}

			if (benefitPlan.getYearlyDeduction() != null) {
				planBean.setYearlyDeduction(benefitPlan.getYearlyDeduction().toPlainString());
			}
			planBean.setMode("edit");

			mav.addObject("planBean", planBean);
		} else {
			mav = AuthorizationUtil.authorizeAdmin(appContext, "manageBenefitPlans");
		}

		return mav;
	}

	/**
	 * Edit a plan Shows the edit plan page(addNewBenefitPlan.jsp)
	 * 
	 * @param request
	 * @param response
	 * @return mav
	 */

	@RequestMapping(value = "/home/controlPanel/flexiPlans/edit/{planId}", method = RequestMethod.GET)
	public ModelAndView showEditPlans(@PathVariable(value = "planId") int planId, HttpServletRequest request) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = null;

		BenefitPlan benefitPlan = benefitPlanService.get(planId);

		if (benefitPlan != null) {

			mav = AuthorizationUtil.authorizeAdmin(appContext, "editBenefitPlan");

			BenefitPlanBean planBean = new BenefitPlanBean();

			planBean.setActive(benefitPlan.getActive());
			planBean.setClaimType(benefitPlan.getClaimType());
			if (benefitPlan.getEffFrom() != null) {
				planBean.setEffFrom(DataTypeUtil.toDateddMMMMMyyyy(benefitPlan.getEffFrom()));
			}

			if (benefitPlan.getEffTill() != null) {
				planBean.setEffTill(DataTypeUtil.toDateddMMMMMyyyy(benefitPlan.getEffTill()));
			}
			planBean.setPlanDesc(benefitPlan.getPlanDesc());
			planBean.setPlanName(benefitPlan.getPlanName());

			if (benefitPlan.getYearlyClaim() != null) {
				planBean.setYearlyClaim(benefitPlan.getYearlyClaim().toPlainString());
			}

			if (benefitPlan.getYearlyDeduction() != null) {
				planBean.setYearlyDeduction(benefitPlan.getYearlyDeduction().toPlainString());
			}
			planBean.setMode("edit");
			planBean.setStep(benefitPlanService.STEP_ONE_MAIN);

			mav.addObject("planBean", planBean);
		} else {
			mav = AuthorizationUtil.authorizeAdmin(appContext, "manageBenefitPlans");
		}

		return mav;
	}

	/**
	 * Edit a plan Shows the edit plan page(addNewBenefitPlan.jsp)
	 * 
	 * @param request
	 * @param response
	 * @return mav
	 */
	@RequestMapping(value = "/home/controlPanel/flexiPlans/edit/new", method = RequestMethod.POST)
	public ModelAndView editPlans(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("planBean") BenefitPlanBean planBean) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		// Integer planId = Integer.parseInt(request.getParameter("planId"));
		String step = planBean.getStep();

		ModelAndView mav = null;

		// mav = AuthorizationUtil.authorizeAdmin(appContext,
		// "editBenefitPlan");

		if (BenefitPlanService.STEP_ONE_MAIN.equals(step)) {
			BenefitPlan benefitPlan = null;
			mav = AuthorizationUtil.authorizeAdmin(appContext, "editBenefitPlanAmount");
			planBean.setStep(BenefitPlanService.STEP_TWO_AMOUNT);
			List<String> bands = benefitPlanService.listAllBands();
			mav.addObject("bands", bands);

			benefitPlan = new BenefitPlan();
			benefitPlan.setActive(Boolean.TRUE);
			benefitPlan.setDeleted(Boolean.FALSE);
			benefitPlan.setPlanName(planBean.getPlanName());
			benefitPlan.setPlanDesc(planBean.getPlanDesc());
			benefitPlan.setActive(planBean.getActive());
			benefitPlan.setClaimDocumentsRequired(planBean.getClaimDocsRequired());

			if (planBean.getEffFrom() != null) {
				benefitPlan.setEffFrom(DataTypeUtil.toDateFromStringddMMMMMyyyy(planBean.getEffFrom()));
			}
			if (planBean.getEffTill() != null) {
				benefitPlan.setEffTill(DataTypeUtil.toDateFromStringddMMMMMyyyy(planBean.getEffTill()));
			}
			benefitPlan.setActive(planBean.getActive());

			benefitPlan.setUpdatedBy(appContext.getUserName());
			benefitPlan.setCreatedBy(appContext.getUserName());

			mav.addObject("planPojo", benefitPlan);
			mav.addObject("planBean", planBean);
		} else if (BenefitPlanService.STEP_TWO_AMOUNT.equals(step)) {
			BenefitPlan benefitPlan = (BenefitPlan) request.getSession().getAttribute("planPojo");
			mav = AuthorizationUtil.authorizeAdmin(appContext, "editBenefitPlanContacts");

			String claimType = planBean.getClaimType();

			benefitPlan.setClaimType(claimType);
			benefitPlan.setClaimFrequency(planBean.getClaimFrequency());

			if (BenefitsConstants.BENEFIT_PLAN_CLAIM_TYPE_FLAT.equals(claimType)) {
				benefitPlan.setYearlyClaim(new BigDecimal(planBean.getYearlyClaim()));
				benefitPlan.setYearlyDeduction(new BigDecimal(planBean.getYearlyDeduction()));
			} else if (BenefitsConstants.BENEFIT_PLAN_CLAIM_TYPE_BANDWISE.equals(claimType)) {
				List<String> bands = benefitPlanService.listAllBands();
				List<BenefitPlanBand> planBands = new ArrayList<BenefitPlanBand>();
				for (String band : bands) {
					String bandAmount = request.getParameter("bandAmount" + band);
					if (bandAmount != null && !("".equals(bandAmount))) {
						BenefitPlanBand planBand = new BenefitPlanBand();
						planBand.setBenefitPlan(benefitPlan);
						planBand.setBand(band);
						planBand.setAmount(new BigDecimal(bandAmount));

						planBand.setCreatedBy(appContext.getUserName());
						planBand.setUpdatedBy(appContext.getUserName());

						planBands.add(planBand);
					}
				}
				benefitPlan.setPlanBands(planBands);
				benefitPlan.setPlanCategories(null);
				mav.addObject("planBean", planBean);
				mav.addObject("planPojo", benefitPlan);
			} else if (BenefitsConstants.BENEFIT_PLAN_CLAIM_TYPE_CATEGORYWISE.equals(claimType)) {
				List<BenefitPlanCategory> planCategories = new ArrayList<BenefitPlanCategory>();
				int categoryCount = 0;
				try {
					categoryCount = Integer.parseInt(request.getParameter("ctgCount"));
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (categoryCount != 0) {
					for (int i = 1; i <= categoryCount; i++) {
						String categoryName = request.getParameter("ctg" + i + "Name");
						String categoryAmount = request.getParameter("ctg" + i + "Amount");
						String categoryTotalAmount = request.getParameter("ctg" + i + "TotalAmount");

						if (categoryName == null || "".equals(categoryName)) {
							categoryName = "Category " + i;
						}

						if (categoryAmount != null && !("".equals(categoryAmount)) && categoryTotalAmount != null
								&& !("".equals(categoryTotalAmount))) {
							BenefitPlanCategory category = new BenefitPlanCategory();
							category.setBenefitPlan(benefitPlan);
							category.setCategoryAmount(new BigDecimal(categoryAmount));
							category.setCategoryName(categoryName);
							category.setCategoryTotalAmount(new BigDecimal(categoryTotalAmount));

							List<BenefitPlanCategoryMisc> listMiscs = new ArrayList<BenefitPlanCategoryMisc>();

							for (int j = 1; j <= 2; j++) {
								String miscName = request.getParameter("misc" + j + "Ctg" + i + "Name");
								String miscAmount = request.getParameter("misc" + j + "Ctg" + i + "Amount");

								if (miscName == null || "".equals(miscName)) {
									miscName = "Misc " + j;
								}

								if (miscAmount != null && miscName != null && !("".equals(miscAmount))
										&& !("".equals(miscName))) {
									BenefitPlanCategoryMisc misc = new BenefitPlanCategoryMisc();
									misc.setPlanCategory(category);
									misc.setMiscName(miscName);
									misc.setMiscAmount(new BigDecimal(miscAmount));

									misc.setCreatedBy(appContext.getUserName());
									misc.setUpdatedBy(appContext.getUserName());

									listMiscs.add(misc);

									System.out.println(miscName + " - " + miscAmount + " - " + categoryName + " - "
											+ categoryAmount + " - " + categoryTotalAmount);
								}
							}
							category.setMiscs(listMiscs);

							category.setCreatedBy(appContext.getUserName());
							category.setUpdatedBy(appContext.getUserName());

							planCategories.add(category);
						}
					}
					benefitPlan.setPlanCategories(planCategories);
					benefitPlan.setPlanBands(null);
				}

			}
			List<String> designations = employeeService.listAllDesignations("HR");

			planBean.setStep(BenefitPlanService.STEP_THREE_CONTACTS);
			mav.addObject("designations", designations);
			mav.addObject("planBean", planBean);
			mav.addObject("planPojo", benefitPlan);
		} else if (BenefitPlanService.STEP_THREE_CONTACTS.equals(step)) {
			appContext = (AppContext) request.getSession().getAttribute("appContext");
			mav = AuthorizationUtil.authorizeAdmin(appContext, "editBenefitPlanDocs");

			BenefitPlan benefitPlan = (BenefitPlan) request.getSession().getAttribute("planPojo");

			List<BenefitPlanContact> contacts = new ArrayList<BenefitPlanContact>();
			for (int i = 1; i <= 3; i++) {
				String contactName = request.getParameter("contact" + i + "Name");
				String contactEmail = request.getParameter("contact" + i + "Email");
				String contactPhone1 = request.getParameter("contact" + i + "Phone1");
				String contactPhone2 = request.getParameter("contact" + i + "Phone1");
				String contactDesignation = request.getParameter("contact" + i + "Designation");

				if (contactName != null && contactEmail != null && !("".equals(contactName))
						&& !("".equals(contactEmail))) {
					BenefitPlanContact contact = new BenefitPlanContact();
					contact.setBenefitPlan(benefitPlan);

					contact.setDesignation(contactDesignation);
					contact.setEmail(contactEmail);
					contact.setFullName(contactName);
					contact.setPhone1(contactPhone1);
					contact.setPhone2(contactPhone2);

					contact.setUpdatedBy(appContext.getUserName());
					contact.setCreatedBy(appContext.getUserName());

					contacts.add(contact);
				}
			}
			benefitPlan.setContacts(contacts);
			planBean.setStep(BenefitPlanService.STEP_FOUR_REQUIRED_DOCS);

			mav.addObject("planBean", planBean);
			mav.addObject("planPojo", benefitPlan);
		} else if (BenefitPlanService.STEP_FOUR_REQUIRED_DOCS.equals(step)) {
			appContext = (AppContext) request.getSession().getAttribute("appContext");
			mav = AuthorizationUtil.authorizeAdmin(appContext, "editBenefitPlanSummary");
			BenefitPlan benefitPlan = (BenefitPlan) request.getSession().getAttribute("planPojo");

			List<BenefitPlanDocument> documents = new ArrayList<BenefitPlanDocument>();
			for (int i = 1; i <= 5; i++) {
				String documentName = request.getParameter("doc" + i + "Name");
				Boolean mandatory = Boolean.parseBoolean(request.getParameter("doc" + i + "Mandatory"));

				if (documentName != null && !("".equals(documentName))) {
					BenefitPlanDocument document = new BenefitPlanDocument();
					document.setBenefitPlan(benefitPlan);
					document.setDocumentName(documentName);
					document.setMandatory(mandatory);

					document.setUpdatedBy(appContext.getUserName());
					document.setCreatedBy(appContext.getUserName());

					documents.add(document);
				}
			}
			if (documents.size() > 0) {
				benefitPlan.setOptDocumentsRequired(Boolean.TRUE);
			} else {
				benefitPlan.setOptDocumentsRequired(Boolean.FALSE);
			}

			benefitPlan.setDocuments(documents);

			planBean.setStep(BenefitPlanService.STEP_FIVE_SUMMARY);
			mav.addObject("savedStatus", null);

			mav.addObject("planBean", planBean);
			mav.addObject("planPojo", benefitPlan);
		} else if (BenefitPlanService.STEP_FIVE_SUMMARY.equals(step)) {
			appContext = (AppContext) request.getSession().getAttribute("appContext");
			mav = AuthorizationUtil.authorizeAdmin(appContext, "editBenefitPlanSummary");

			planBean.setMode(BenefitPlanService.STEP_FIVE_SUMMARY);

			BenefitPlan benefitPlan = (BenefitPlan) request.getSession().getAttribute("planPojo");

			if ("edit".equals(planBean.getMode())) {
				if (benefitPlanService.edit(benefitPlan)) {
					mav.addObject("savedStatus", Boolean.TRUE);
				} else {
					mav.addObject("savedStatus", Boolean.FALSE);
				}
			}

			mav.addObject("planBean", planBean);
			mav.addObject("planPojo", benefitPlan);
		}

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/remove/{planId}", method = RequestMethod.GET)
	public ModelAndView removeFlexiPlan(HttpServletRequest request, @PathVariable(value = "planId") Integer planId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "redirect:/home/controlPanel/flexiPlans");

		BenefitPlan benefitPlan = benefitPlanService.get(planId);
		if (benefitPlan != null) {
			benefitPlan.setDeleted(true);
			benefitPlan.setUpdatedBy(appContext.getUserName());

			benefitPlanService.edit(benefitPlan);
		}

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/switchActive/{planId}", method = RequestMethod.GET)
	public ModelAndView switchActive(HttpServletRequest request, @PathVariable(value = "planId") Integer planId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "redirect:/home/controlPanel/flexiPlans");

		BenefitPlan benefitPlan = benefitPlanService.get(planId);

		if (benefitPlan != null) {
			if (benefitPlan.getActive()) {
				benefitPlan.setActive(false);
			} else {
				benefitPlan.setActive(true);
			}

			benefitPlan.setUpdatedBy(appContext.getUserName());

			benefitPlanService.edit(benefitPlan);
		}

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/view/{planId}", method = RequestMethod.GET)
	public ModelAndView showFlexiPlan(HttpServletRequest request, @PathVariable(value = "planId") Integer planId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "editBenefitPlanSummary");

		BenefitPlan benefitPlan = benefitPlanService.get(planId);
		BenefitPlanBean planBean = new BenefitPlanBean();
		planBean.setMode("edit");
		planBean.setStep(BenefitPlanService.STEP_FIVE_SUMMARY);

		mav.addObject("planBean", planBean);
		mav.addObject("planPojo", benefitPlan);
		return mav;
	}

	/**
	 * 
	 * Shows Employees opted benefit plans (benefitOptedEmployees.jsp)
	 * 
	 * @param
	 * @param
	 * @retuns ModelAndView
	 * 
	 */
	@RequestMapping(value = "/home/controlPanel/flexiPlans/optedEmployees", method = RequestMethod.GET)
	public ModelAndView showOptedEmployeesPage(HttpServletRequest request, HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "benefitOptedEmployees");
		List<BenefitPlan> allPlans = benefitPlanService.listAll();
		BenefitPlanEmployee b = new BenefitPlanEmployee();

		SearchBean searchBean = (SearchBean) request.getSession().getAttribute("searchBean");

		if (searchBean == null) {
			searchBean = new SearchBean();
			searchBean.setBenefitPlanId(0);
			searchBean.setStatus("");
			searchBean.setFiscalYear(appContext.getCurrentFiscalYear());
		}

		mav.addObject("bean", searchBean);

		if (allPlans != null && allPlans.size() > 0) {

			mav.addObject("plans", allPlans);
			BenefitPlan firstPlan = allPlans.get(0);
			// List<BenefitPlanEmployee> planEmployees =
			// benefitPlanService.listEmployees(firstPlan.getBenefitPlanId());
			List<BenefitPlanEmployee> planEmployees = benefitPlanService.listEmployees(searchBean.getBenefitPlanId(),
					searchBean.getFiscalYear(), searchBean.getStatus());
			mav.addObject("employees", planEmployees);
		}

		mav.addObject("yearclaim", b.getYearlyClaim());
		mav.addObject("yeardeduction", b.getYearlyDeduction());
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/optedEmployees", method = RequestMethod.POST)
	public ModelAndView searchOptedemployees(HttpServletRequest request, @ModelAttribute("bean") SearchBean bean,
			HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "benefitOptedEmployees");

		List<BenefitPlan> allPlans = benefitPlanService.listAll();

		mav.addObject("plans", allPlans);
		mav.addObject("searchBean", bean);

		List<BenefitPlanEmployee> planEmployees = benefitPlanService.listEmployees(bean.getBenefitPlanId(),
				bean.getFiscalYear(), bean.getStatus());

		System.out.println("****************bean *******" + bean);
		mav.addObject("bean", bean);
		mav.addObject("employees", planEmployees);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/optedEmployees/approve/{employeePlanId}", method = RequestMethod.GET)
	public ModelAndView showEmployeeBenefitPlan(HttpServletRequest request,
			@PathVariable(value = "employeePlanId") Integer employeePlanId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "approveEmployeePlan");

		BenefitPlanEmployee benefitPlanEmployee = benefitPlanService.getById(employeePlanId);

		if (benefitPlanEmployee.getBenefitPlan().getPromptFieldsOnOPT()) {
			List<BenefitPlanEmployeeField> benefitPlanEmployeeField = benefitPlanService.listAllField(employeePlanId);
			mav.addObject("benefitPlanEmployeeField", benefitPlanEmployeeField);
		}

		if (benefitPlanEmployee.getBenefitPlan().getOptDocumentsRequired()) {
			List<BenefitPlanEmployeeDoc> planEmployeeDocs = benefitPlanEmployee.getDocuments();
			List<DocumentVO> doclist = new ArrayList<DocumentVO>();
			for (BenefitPlanEmployeeDoc doc : planEmployeeDocs) {
				DocumentVO vo = new DocumentVO();
				vo.setDocumentType(doc.getBenefitPlanDocument().getDocumentName());

				if (doc.getDocmanUUId() != null && !"".equals(doc.getDocmanUUId()))
					vo.setDocManUuid(doc.getDocmanUUId());
				vo.setUploadUrl(docmanRestClient.getUploadUrl(doc.getDocmanUUId(), appContext.getUserLoginKey()));
				vo.setDownloadUrl(docmanRestClient.getDownloadUrl(doc.getDocmanUUId(), appContext.getUserLoginKey()));

				vo.setDocumentId(doc.getPlanEmployeeDocId() + "");

				doclist.add(vo);
			}
			mav.addObject("docList", doclist);
		}
		mav.addObject("employeePlan", benefitPlanEmployee);

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/optedEmployees/approve/{planEmployeeId}", method = RequestMethod.POST)
	public ModelAndView approveEmployeeBenefitPlan(HttpServletRequest request,
			@PathVariable(value = "planEmployeeId") Integer planEmployeeId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"redirect:/home/controlPanel/flexiPlans/optedEmployees");
		String action = request.getParameter("action");
		BenefitPlanEmployee planEmployee = benefitPlanService.getPlanEmployee(planEmployeeId);

		if (action.equals("approve")) {
			List<BenefitPlanEmployeeDoc> list = benefitPlanService.getDocs(planEmployeeId);
			if (list != null) {
				for (BenefitPlanEmployeeDoc benefitPlanEmployeeDoc : list) {
					benefitPlanEmployeeDoc.setVerified(true);
					benefitPlanService.update(benefitPlanEmployeeDoc);
				}
			}
			benefitPlanService.updateStatus(planEmployeeId);

			try {
				// EmailFormatter.approvemail(planEmployee);

				EmailService emailService = null;

				String subject = "Plan enrollment approved!";

				String body = EmailFormatter.approvemail(planEmployee);

				String to = planEmployee.getEmployee().getEmail();

				emailService = new EmailService(planEmployee.getEmployee().getEmail(),
						EmailProperties.getProperty("taxReturnsEmail") + "," + EmailProperties.getProperty("hrEmail"),
						body, subject);

				// String messageSubject =
				// "Benefits Portal
				// -"+benefitPlanEmployee.getEmployee().getFirstName()+"
				// "+benefitPlanEmployee.getEmployee().getLastName()+"'s
				// Employee Benefit Plan
				// Approved";
				Thread emailThread = new Thread(emailService);
				emailThread.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (action.equals("save")) {
			String docId = request.getParameter("docIds");
			// docId = docId.substring(0, docId.length() - 1);
			System.out.println("----------------inside approve--------");
			System.out.println("----------------docIds are--------" + docId);
			String[] docIds = docId.split(",");
			for (String docId1 : docIds) {
				BenefitPlanEmployeeDoc benefitPlanEmployeeDoc = benefitPlanService.getDoc(Integer.parseInt(docId1));

				benefitPlanEmployeeDoc.setVerified(true);
				benefitPlanService.update(benefitPlanEmployeeDoc);
			}
		}

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/optedEmployees/reject/{planEmployeeId}", method = RequestMethod.GET)
	public ModelAndView rejectEmployeeBenefitPlan(HttpServletRequest request,
			@PathVariable(value = "planEmployeeId") Integer planEmployeeId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"redirect:/home/controlPanel/flexiPlans/optedEmployees");

		BenefitPlanEmployee planEmployee = benefitPlanService.getById(planEmployeeId);
		String reason = request.getParameter("declineReason");

		if (planEmployee != null) {
			planEmployee.setStatus(BenefitsConstants.EMP_PLAN_OPT_STATUS_HR_REJECT);
			planEmployee.setDeclineReason(reason);
			benefitPlanService.updatePlanEmployee(planEmployee);
		}
		EmailFormatter.denymail(planEmployee);

		String subject = "Plan declined!";

		String body = EmailFormatter.denymail(planEmployee);

		String to = planEmployee.getEmployee().getEmail();

		StringBuffer ccBuffer = new StringBuffer();
		for (BenefitPlanContact contact : planEmployee.getBenefitPlan().getContacts()) {
			ccBuffer.append(contact.getEmail()).append(",");
		}

		EmailService emailService = new EmailService(to, ccBuffer.toString(), body, subject);

		Thread emailThread = new Thread(emailService);
		emailThread.start();

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/optedEmployees/view/{planEmployeeId}")
	public ModelAndView viewEmployeeBenefitPlan(HttpServletRequest request,
			@PathVariable(value = "planEmployeeId") Integer planEmployeeId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewBenefitOptedEmployee");
		BenefitPlanEmployee benefitPlanEmployee = benefitPlanService.getById(planEmployeeId);
		if (benefitPlanEmployee.getBenefitPlan().getPromptFieldsOnOPT()) {
			List<BenefitPlanEmployeeField> benefitPlanEmployeeField = benefitPlanService.listAllField(planEmployeeId);
			mav.addObject("benefitPlanEmployeeField", benefitPlanEmployeeField);
		}

		BenefitPlanBand planBand = benefitPlanService.getPlanBand(
				benefitPlanEmployee.getBenefitPlan().getBenefitPlanId(), appContext.getCurrentEmployee().getBand());

		mav.addObject("amount", planBand.getAmount());
		List<BenefitPlanEmployeeDoc> doclist = benefitPlanService.getDocs(planEmployeeId);
		mav.addObject("employeePlan", benefitPlanEmployee);
		if (doclist != null) {

			mav.addObject("docList", doclist);
		}
		return mav;

	}

	/**
	 * User specific pages starts here
	 */

	/**
	 * 
	 * 
	 * Shows myFlexi benefits plan from home
	 * 
	 * @param
	 * @param
	 * @retuns ModelAndView
	 * 
	 */
	@RequestMapping(value = "/home/myFlexiPlans", method = RequestMethod.GET)
	public ModelAndView showMyPlans(HttpServletRequest request, HttpServletResponse response) {

		appContext = (AppContext) request.getSession().getAttribute("appContext");
		System.out.println("************inside my flexi pans**********************************");
		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "employeeBenefitPlanMenu");
	    List<TaxRegime> taxRegimeresponse=taxRegimeServiceImpl.getTaxRegime(appContext.getEmpId(), appContext.getCurrentFiscalYear());
		if (appContext != null) {
			String currentFiscalYear = appContext.getCurrentFiscalYear();

			Integer empId = appContext.getEmpId();
			Employee employee = employeeService.get(empId);
			EmpBenPlansYrlyOpts benPlansYrlyOpts = employeeService.getPlanYrlyOpts(empId, currentFiscalYear);

			// checks whether already submitted,then only selected
			// plans should be displayed

			List<BenefitPlanEmployee> employeePlans = null;
			if (appContext.getOptingPeriodOver()) {
				/*
				 * opting period is over, so screen has to display only selected
				 * plans and disable submit button
				 */
				if (benPlansYrlyOpts != null) {
					if (benPlansYrlyOpts.getStatus().equals(BenefitsConstants.EMP_YERLY_PLAN_OPT_STATUS_SUBMITTED)) {
						System.out.println("*****submitted************");

						employeePlans = benefitPlanService.listByYearlyOpt(benPlansYrlyOpts.getYrlyOptId(),
								Boolean.TRUE);

						if (employeePlans.size() <= 0) {
							mav.addObject("warnMessage",
									"There is no active plans found for you in " + appContext.getCurrentFiscalYear());
						}

					} else {
						mav.addObject("warnMessage",
								"You have not Submitted your Flexi Plans for " + appContext.getCurrentFiscalYear());
					}
				} else {
					mav.addObject("warnMessage",
							"You have not Opted any Flexi Benefits Plans for " + appContext.getCurrentFiscalYear());
				}
				mav.addObject("employeePlans", employeePlans);
				mav.addObject("canSubmit", Boolean.FALSE);

			}

			/*
			 * if opting period is not over
			 */

			else {

				/*
				 * opting period is not over, so screen has to display all
				 * available plans
				 */

				mav.addObject("canSubmit", Boolean.TRUE);

				if (benPlansYrlyOpts == null) {
					benPlansYrlyOpts = new EmpBenPlansYrlyOpts();
					benPlansYrlyOpts.setEmployee(employee);
					benPlansYrlyOpts.setFiscalYear(currentFiscalYear);
					benPlansYrlyOpts.setStatus(BenefitsConstants.EMP_YERLY_PLAN_OPT_STATUS_OPEN);
					benPlansYrlyOpts.setCreatedBy(appContext.getCurrentEmployee().getUserName());
					employeeService.insert(benPlansYrlyOpts);
				}

				// keeping the value in session so that next methods can reuse
				// it

				List<BenefitPlan> allPlans = benefitPlanService.listAllActive(currentFiscalYear, employee);
				List<EmployeePlanBean> employeePlansList = new ArrayList<EmployeePlanBean>();

				if (allPlans.size() > 0) {
					if(taxRegimeresponse.isEmpty()!=true){
					for (BenefitPlan plan : allPlans) {
						Boolean availablePlan = false;
						
						if (plan.getClaimType().equals(BenefitsConstants.BENEFIT_PLAN_CLAIM_TYPE_BANDWISE)) {
							
							List<String> bandsForPlan = benefitPlanService.listAllBandsForPlan(plan.getBenefitPlanId());
							if (bandsForPlan.contains(employee.getBand())) {
								
									availablePlan = true;
								
								
							}
						} else if (plan.getClaimType().equals(BenefitsConstants.BENEFIT_PLAN_CLAIM_TYPE_CATEGORYWISE)) {
							
							if(taxRegimeresponse.get(0).getTaxRegime()==2 && plan.getPlanName().equals("Children's Education Allowance")){
								availablePlan=false;
							}else{
								availablePlan = true;
							}
						} else {
							if(taxRegimeresponse.get(0).getTaxRegime()==2 && plan.getPlanName().equals("Children's Education Allowance")){
								availablePlan=false;
							}else{
								availablePlan = true;
							}
						}

						if (availablePlan) {
							EmployeePlanBean planBean = new EmployeePlanBean(plan, employee);

							BenefitPlanEmployee planEmployee = employeeService.getPlanEmployee(plan.getBenefitPlanId(),
									empId, appContext.getCurrentFiscalYear());

							if (planEmployee != null) {
								if (planEmployee.getActive() && (planEmployee.getStatus().equals("HR_APPR")
										|| planEmployee.getStatus().equals("FIN_APPR"))) {
									planBean.setPlanStatus("OPTED");
								} else {
									planBean.setPlanStatus("SUBMITTED");
								}
								planBean.setPlanEmployeeId(planEmployee.getPlanEmployeeId());
							} else {
								PlanEmployeeDenies deny = employeeService.getPlanEmployeeDeny(plan.getBenefitPlanId(),
										empId, appContext.getCurrentFiscalYear());
								if (deny != null) {
									planBean.setPlanStatus("DENIED");
									planBean.setDenyReason(deny.getPlanDenyReasonsMaster() != null
											? deny.getPlanDenyReasonsMaster().getReasonDesc() : deny.getOtherReason());
								} else {
									planBean.setPlanStatus("NOT_OPTED");
								}
							}
							employeePlansList.add(planBean);
						}

					}
				}
				} else {
					mav.addObject("warnMessage", "No Flexi Plans available");
				}
				mav.addObject("availablePlanList", employeePlansList);
			}

			if (benPlansYrlyOpts != null) {
				mav.addObject("empPlanYearlyOpts", benPlansYrlyOpts);
			}

			if (request.getParameter("optStatus") != null) {
				mav.addObject("optStatus", request.getParameter("optStatus"));
			} else {
				mav.addObject("optStatus", 100);
			}
			// parameters for opting out
			List<PlanEmployeeDenies> employeeDenies = benefitPlanService
					.getByDenyId(appContext.getCurrentEmployee().getEmployeeId());
			/*
			 * List<BenefitPlanEmployee> list =
			 * benefitPlanService.getByIds(appContext.getCurrentFiscalYear(),
			 * appContext.getCurrentEmployee().getEmployeeId());
			 * if(employeeDenies != null && list != null) {
			 * if(employeeDenies.size()+list.size()==a) }
			 */
			mav.addObject("employeeDenies", employeeDenies);
			mav.addObject("appcontext", appContext);
			List<PlanDenyReasonsMaster> reasonsMasters = benefitPlanService.listAllDenyReasons();
			mav.addObject("reasons", reasonsMasters);

		}

		return mav;
	}

	@RequestMapping(value = "/home/myFlexiPlans/optout", method = RequestMethod.POST)
	public ModelAndView benefitPlanOptOut(HttpServletRequest request, HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");

		Date auditDate = new Date();

		EmpBenPlansYrlyOpts opts = (EmpBenPlansYrlyOpts) request.getSession().getAttribute("empPlanYearlyOpts");
		PlanEmployeeDenies denies = new PlanEmployeeDenies();
		denies.setEmployee(appContext.getCurrentEmployee());

		BenefitPlan benefitPlan = benefitPlanService.get(Integer.parseInt(request.getParameter("cPlanId")));
		EmpBenPlansYrlyOpts benPlansYrlyOpts = new EmpBenPlansYrlyOpts();
		benPlansYrlyOpts.setYrlyOptId(Integer.parseInt(request.getParameter("yrlyOptId")));

		denies.setEmpBenPlansYrlyOpts(benPlansYrlyOpts);
		denies.setBenefitPlan(benefitPlan);
		denies.setCreatedBy(appContext.getUserName());
		denies.setCreatedDate(auditDate);
		denies.setDeniedDate(auditDate);
		denies.setFiscalYear(appContext.getCurrentFiscalYear());
		String reasonId = request.getParameter("reasonId");
		if (reasonId.equals("0")) {
			denies.setPlanDenyReasonsMaster(null);
			denies.setOtherReason(request.getParameter("otherReason"));
		} else {
			PlanDenyReasonsMaster master = benefitPlanService.getDenyReason(Integer.parseInt(reasonId));
			denies.setPlanDenyReasonsMaster(master);
		}
		if (benefitPlanService.insert(denies)) {
			String subject = "Flexi Plan Opted Out for FY " + appContext.getCurrentFiscalYear();
			String body = EmailFormatter.flexiOptOut(denies);

			String to = denies.getEmployee().getEmail();

			StringBuffer ccBuffer = new StringBuffer();

			for (BenefitPlanContact contact : denies.getBenefitPlan().getContacts()) {
				ccBuffer.append(contact.getEmail()).append(",");
			}

			EmailService emailService = new EmailService(to, ccBuffer.toString(), body, subject);

			Thread emailThread = new Thread(emailService);
			emailThread.start();
		}
		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "redirect:/home/myFlexiPlans");

		return mav;

	}

	@RequestMapping(value = "/home/myFlexiPlans/details/{planId}")
	public ModelAndView showViewMyPlanDetails(@PathVariable Integer planId, HttpServletRequest request) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "employeeBenefitPlanDetails");

		EmpBenPlansYrlyOpts opts = (EmpBenPlansYrlyOpts) request.getSession().getAttribute("empPlanYearlyOpts");

		Boolean chooseButtonEnabled = true;

		// check if mutual dependency exists
		Boolean mutualDepenedency = false;
		List<Integer> planDependents = benefitPlanService.listDependencies(planId);

		List<BenefitPlanEmployee> benefitPlanEmployees = benefitPlanService.getByIds(appContext.getCurrentFiscalYear(),
				appContext.getEmpId());
		System.out.println("***dependents***" + planDependents);
		System.out.println("***planemployees***" + benefitPlanEmployees);

		if (planDependents != null && benefitPlanEmployees != null) {
			for (BenefitPlanEmployee benefitPlanEmployee : benefitPlanEmployees) {
				for (Integer plan : planDependents) {
					if (plan.equals(benefitPlanEmployee.getBenefitPlan().getBenefitPlanId())) {
						mutualDepenedency = true;
						System.out.println("****mutual dependency exists***");
						break;
					}
				}
			}
		}
		if (mutualDepenedency) {
			mav.addObject("dependency", "true");
			chooseButtonEnabled = false;
		} else {
			mav.addObject("dependency", "false");
			chooseButtonEnabled = true;
		}

		BenefitPlan benefitPlan = benefitPlanService.get(planId);

		if (BenefitsConstants.BENEFIT_PLAN_CLAIM_TYPE_BANDWISE.equals(benefitPlan.getClaimType())) {
			BenefitPlanBand planBand = benefitPlanService.getPlanBand(planId,
					appContext.getCurrentEmployee().getBand());

			if (planBand != null) {
				mav.addObject("planBand", planBand);
			} else {
				chooseButtonEnabled = false;
			}

		} else if (BenefitsConstants.BENEFIT_PLAN_CLAIM_TYPE_DEPENDENTWISE.equals(benefitPlan.getClaimType())) {
			List<DependentCategoryBean> qualifiedDependents = new ArrayList<DependentCategoryBean>();
			List<BenefitPlanDependentCategory> depCategories = benefitPlan.getDependentCategories();

			for (BenefitPlanDependentCategory category : depCategories) {
				List<Dependent> dependents = employeeService.listAllDependents(
						appContext.getCurrentEmployee().getEmployeeId(), category.getRelationship(),
						category.getMaximumAge(), category.getMinimumAge());

				for (Dependent dependent : dependents) {
					if (!(qualifiedDependents.contains(dependent))) {
						DependentCategoryBean depBean = new DependentCategoryBean(dependent.getDependentId() + "",
								dependent.getDependentName(), dependent.getRelationship(),
								category.getYearlyClaim().toPlainString(),
								category.getYearlyDeduction().toPlainString());
						qualifiedDependents.add(depBean);
					}
				}
			}
			mav.addObject("qualifiedDependents", qualifiedDependents);
		}

		/**
		 * checking whether any documents required for opting the plan
		 */
		if (benefitPlan.getOptDocumentsRequired()) {

			String docmanDocAvailablityServiceUrl = DocManProperties
					.getProperty(BenefitsWSConstants.PROP_DOCMAN_DOC_AVAILABILITY_REST_URL);
			mav.addObject("docAvailabilityRestUrl", docmanDocAvailablityServiceUrl);

			List<DocumentVO> listDocumentVo = new ArrayList<DocumentVO>();
			List<BenefitPlanDocument> documents = benefitPlan.getDocuments();

			for (BenefitPlanDocument document : documents) {
				DocumentVO documentVo = new DocumentVO(document.getPlanDocumentId() + "",
						benefitPlan.getPlanName() + "/" + appContext.getCurrentFiscalYear(), document.getDocumentName(),
						document.getPlanDocumentId() + "/" + appContext.getEmpId() + "/"
								+ appContext.getCurrentFiscalYear(),
						"Employee Code", appContext.getCurrentEmployee().getEmployeeCode());
				documentVo.setMandatory(document.getMandatory());

				String docmanUuid = docmanRestClient.getDocmanUuid(BenefitsWSConstants.SCREEN_CHOOSE_PLAN,
						appContext.getUserLoginKey(), documentVo.getFieldName(), documentVo.getRecordId(),
						documentVo.getRecordLabel(), documentVo.getRecordName(), appContext.getUserName(),
						documentVo.getDocumentType());
				documentVo.setDocManUuid(docmanUuid);

				documentVo.setUploadUrl(
						docmanRestClient.getUploadUrl(documentVo.getDocManUuid(), appContext.getUserLoginKey()));
				documentVo.setDownloadUrl(
						docmanRestClient.getDownloadUrl(documentVo.getDocManUuid(), appContext.getUserLoginKey()));

				listDocumentVo.add(documentVo);
			}
			mav.addObject("documents", listDocumentVo);
		}
		if (benefitPlan.getPromptFieldsOnOPT()) {
			List<BenefitPlanField> benefitPlanFields = benefitPlanService.listAllFields(planId);
			mav.addObject("benefitPlanFields", benefitPlanFields);
		}

		mav.addObject("chooseButtonEnabled", chooseButtonEnabled);
		mav.addObject("benefitPlan", benefitPlan);

		return mav;
	}

	@RequestMapping(value = "/home/myFlexiPlans/details/{planId}/optit", method = RequestMethod.POST)
	public ModelAndView benefitPlanOpt(HttpServletRequest request, HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");

		Date auditDate = new Date();

		EmpBenPlansYrlyOpts opts = (EmpBenPlansYrlyOpts) request.getSession().getAttribute("empPlanYearlyOpts");

		Integer planId = Integer.parseInt(request.getParameter("planId"));

		BenefitPlan benefitPlan = benefitPlanService.get(planId);
		BenefitPlanEmployee planEmployee = new BenefitPlanEmployee(appContext.getCurrentEmployee(), benefitPlan,
				new Date());

		if (BenefitsConstants.BENEFIT_PLAN_CLAIM_TYPE_CATEGORYWISE.equals(benefitPlan.getClaimType())) {
			Integer categoryId = Integer.parseInt(request.getParameter("finalCategoryId"));
			String csMiscIds = request.getParameter("finalMiscIds");
			String[] miscIds = null;
			if (csMiscIds != null && !("".equals(csMiscIds))) {
				miscIds = csMiscIds.split(",");
			}
			BenefitPlanCategory category = benefitPlanService.getCategory(categoryId);
			// /setting active as false, initially
			planEmployee.setPlanCategory(category);

			if (miscIds != null) {
				List<BenefitPlanEmployeeDetail> planEmployeeDetails = new ArrayList<BenefitPlanEmployeeDetail>();
				for (int i = 0; i < miscIds.length; i++) {
					BenefitPlanCategoryMisc misc = benefitPlanService.getMisc(Integer.parseInt(miscIds[0]));
					BenefitPlanEmployeeDetail detail = new BenefitPlanEmployeeDetail();
					detail.setMisc(misc);
					detail.setPlanEmployee(planEmployee);
					detail.setUpdatedBy(appContext.getUserName());
					detail.setCreatedBy(appContext.getUserName());

					planEmployeeDetails.add(detail);

				}
				planEmployee.setPlanEmployeeDetails(planEmployeeDetails);

			}
		} else if (BenefitsConstants.BENEFIT_PLAN_CLAIM_TYPE_DEPENDENTWISE.equals(benefitPlan.getClaimType())) {
			String dependentIds = request.getParameter("finalDepIds");
			if (dependentIds != null && !("".equals(dependentIds))) {
				List<BenefitPlanEmployeeDependentDetail> dependentDetails = new ArrayList<BenefitPlanEmployeeDependentDetail>();
				String[] depIds = dependentIds.split(",");
				for (int i = 0; i < depIds.length; i++) {
					Dependent dependent = employeeService.getDependent(Integer.parseInt(depIds[i]));

					if (dependent != null) {
						for (BenefitPlanDependentCategory depCtg : benefitPlan.getDependentCategories()) {
							if (depCtg.getRelationship().equals(dependent.getRelationship())) {
								BenefitPlanEmployeeDependentDetail dependentDetail = new BenefitPlanEmployeeDependentDetail();

								dependentDetail.setYearlyClaim(depCtg.getYearlyClaim());
								dependentDetail.setYearlyDeduction(depCtg.getYearlyDeduction());

								dependentDetail.setDependent(dependent);
								dependentDetail.setPlanDependentCategory(depCtg);
								dependentDetail.setPlanEmployee(planEmployee);

								dependentDetail.setUpdatedBy(appContext.getUserName());
								dependentDetail.setCreatedBy(appContext.getUserName());
								dependentDetail.setUpdatedDate(new Date());
								dependentDetail.setCreatedDate(new Date());

								dependentDetails.add(dependentDetail);

							}
						}
					}
				}
				planEmployee.setDependentDetails(dependentDetails);
			}

		}

		planEmployee.setOptingBand(appContext.getCurrentEmployee().getBand());
		planEmployee.setActive(true);
		planEmployee.setAmountClaimed(new BigDecimal(0));
		planEmployee.setAmountDeducted(new BigDecimal(0));
		planEmployee.setBenefitPlan(benefitPlan);
		planEmployee.setYearlyClaim(benefitPlanRE.getTotalYearlyClaim(planEmployee));
		planEmployee.setYearlyDeduction(benefitPlanRE.getTotalYearlyDeduction(planEmployee));
		planEmployee.setTaxableAmount(benefitPlanRE.getTotalYearlyClaim(planEmployee));
		planEmployee.setNonPayrolledAmount(new BigDecimal(0));
		opts.setStatus(BenefitsConstants.EMP_YERLY_PLAN_OPT_STATUS_SUBMITTED);
		benefitPlanService.update(opts);
		// planEmployee.setEmpBenYearlyOpt(opts);
		String fiscalYear = appContext.getCurrentFiscalYear();
		String[] fys = fiscalYear.split("-");
		if (fys != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date effTill;
			try {
				effTill = formatter.parse(fys[1] + "-03-31");
				System.out.println("**************nxt year******" + effTill);
				planEmployee.setEffTill(effTill);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		if (benefitPlan.getOptDocumentsRequired()) {
			List<BenefitPlanDocument> documents = benefitPlan.getDocuments();
			List<BenefitPlanEmployeeDoc> planEmployeeDocuments = new ArrayList<BenefitPlanEmployeeDoc>();
			for (BenefitPlanDocument document : documents) {
				String docmanUuid = null;
				try {
					docmanUuid = request.getParameter("docUuid" + document.getPlanDocumentId());
				} catch (Exception e) {
					e.printStackTrace();
				}

				BenefitPlanEmployeeDoc planEmployeeDoc = new BenefitPlanEmployeeDoc();

				planEmployeeDoc.setPlanEmployee(planEmployee);
				planEmployeeDoc.setBenefitPlanDocument(document);

				planEmployeeDoc.setDocmanStatus("Attached");
				if (docmanUuid != null && !"".equals(docmanUuid)) {
					planEmployeeDoc.setDocmanUUId(docmanUuid);
				}
				planEmployeeDoc.setLastAccessedBy(appContext.getUserName());
				planEmployeeDoc.setLastAccessedDate(auditDate);
				planEmployeeDoc.setVerified(Boolean.FALSE);

				planEmployeeDoc.setCreatedBy(appContext.getUserName());
				planEmployeeDoc.setCreatedDate(auditDate);
				planEmployeeDoc.setUpdatedBy(appContext.getUserName());
				planEmployeeDoc.setUpdatedDate(auditDate);

				planEmployeeDocuments.add(planEmployeeDoc);
			}
			planEmployee.setDocuments(planEmployeeDocuments);
		}

		/*
		 * checking field values
		 */
		if (benefitPlan.getPromptFieldsOnOPT()) {
			List<BenefitPlanField> optFields = benefitPlanService.listAllFields(planId);
			if (optFields != null && !optFields.isEmpty()) {
				List<BenefitPlanEmployeeField> empFields = new ArrayList<BenefitPlanEmployeeField>();
				for (BenefitPlanField field : optFields) {
					String userValue = request.getParameter(field.getFieldName());
					if (userValue != null && !"".equals(userValue)) {
						BenefitPlanEmployeeField empField = new BenefitPlanEmployeeField();
						empField.setPlanEmployee(planEmployee);
						empField.setField(field);
						empField.setApproved(false);
						empField.setValue(userValue);

						empField.setCreatedBy(appContext.getUserName());
						empField.setCreatedDate(auditDate);
						empField.setUpdatedBy(appContext.getUserName());
						empField.setUpdatedDate(auditDate);

						empFields.add(empField);
					}
				}
				planEmployee.setFields(empFields);
			}
		}

		String currentFy = appContext.getCurrentFiscalYear();
		String fyYearStartDate = currentFy.split("-")[0] + "-04-01";

		Date effDate = DataTypeUtil.toDateFromStringyyyyMMdd(fyYearStartDate);

		/*
		 * checking whether employees joining date greater than FY start date
		 * and assigning eff date based on that
		 */
		/*
		 * if
		 * (appContext.getCurrentEmployee().getDateOfJoin().compareTo(effDate)
		 * == 1) {
		 * 
		 * planEmployee.setEffFrom(opts.getEmployee().getDateOfJoin()); } else {
		 * planEmployee.setEffFrom(effDate); }
		 */

		List<BenefitsProperty> benefitsProperties = settingsService.listAllProperties();

		String cutOffDate = settingsService.getPropertyByCode(BenefitsConstants.PROP_CODE_YEARLY_OPT_CUT_OFF_DATE)
				.getPropertyValue();
		String enrollEffDate = settingsService.getPropertyByCode(BenefitsConstants.PROP_FLEXI_PLAN_ENROLL_EFF_DAY)
				.getPropertyValue();
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date newDate = new Date();
		String effDay = enrollEffDate + "-" + cutOffDate.split("-")[0] + "-" + currentFy.split("-")[0];
		// DateFormat effDayFormat = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println("*************eff day********" + effDay + "*************");
		System.out.println("===============================You ARE HERE DATE OF JOINING ============================");

		Date effDay1;
		try {
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			effDay1 = (Date) format.parse(effDay);

			if (newDate.compareTo(effDay1) > 0) {
				System.out.println(
						"===============================You ARE HERE inside IF DATE OF JOINING ============================");
				Calendar c = Calendar.getInstance();
				c.setTime(effDay1);
				c.add(Calendar.MONTH, 1);
				effDate = format1.parse(format1.format(c.getTime()));
				effDate.setDate(1);
				System.out.println("**********inside effdate is*******" + effDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		planEmployee.setEffFrom(effDate);
		System.out.println("============You are here date of joining===========" + effDate);

		planEmployee.setEmpBenYearlyOpt(opts);
		planEmployee.setEmployee(appContext.getCurrentEmployee());
		planEmployee.setFiscalYear(currentFy);
		planEmployee.setStatus(BenefitsConstants.EMP_PLAN_OPT_STATUS_NOT_APPROVED);

		planEmployee.setAmountClaimed(new BigDecimal(0));

		planEmployee.setCreatedBy(appContext.getUserName());
		planEmployee.setUpdatedBy(appContext.getUserName());

		if (benefitPlanService.addPlanEmployee(planEmployee)) {
			System.out.println("Successfuly created new PlanEmployee");

			System.out.println("Now sending email to employee");

			String subject = "New Flexi Plan Selected for FY " + appContext.getCurrentFiscalYear();
			String body = EmailFormatter.newPlanAdded(planEmployee);

			String to = planEmployee.getEmployee().getEmail();

			StringBuffer ccBuffer = new StringBuffer();
			for (BenefitPlanContact contact : planEmployee.getBenefitPlan().getContacts()) {
				ccBuffer.append(contact.getEmail()).append(",");
			}

			EmailService emailService = new EmailService(to, ccBuffer.toString(), body, subject);

			Thread emailThread = new Thread(emailService);
			emailThread.start();
		} else {
			System.err.println("Failed to create new PlanEmployee");
		}

		return new ModelAndView("redirect:/home/myFlexiPlans");

	}

	private boolean compareTo(String newDateString) {
		// TODO Auto-generated method stub
		return false;
	}

	@RequestMapping(value = "/home/myFlexiPlans/submitDocs/{planId}", method = RequestMethod.GET)
	public ModelAndView showSubmitClaimDocuments(HttpServletRequest request, HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "submitClaimDocs");

		// implmement logic here
		// @resource renjini.raj
		return mav;
	}

	// report based on employee

	@RequestMapping(value = "/home/controlPanel/reports/employee", method = RequestMethod.GET)
	public ModelAndView showEmployee(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "searchEmployee");

		List<Employee> employees = employeeService.listAll();
		Set<String> desgs = new HashSet<String>();
		Set<String> depts = new HashSet<String>();
		Set<String> manager_name = new HashSet<String>();

		SearchBean bean1 = new SearchBean();
		bean1.setDept(" ");
		bean1.setDesg(" ");
		bean1.setEmployeeCode(" ");
		mav.addObject("bean1", bean1);

		for (Employee employee : employees) {
			desgs.add(employee.getDesignationName());
			depts.add(employee.getDepartmentName());
			manager_name.add(employee.getManagerFullName());
		}
		mav.addObject("manager_name", manager_name);
		mav.addObject("designation", desgs);
		mav.addObject("dept", depts);
		mav.addObject("employees", employees);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/reports/employee", method = RequestMethod.POST)
	public ModelAndView searchEmployee(HttpServletRequest request, @ModelAttribute("bean") SearchBean bean1,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		String fiscalYear = appContext.getCurrentFiscalYear();
		List<EmployeePlanClaimBean> beans = new ArrayList<EmployeePlanClaimBean>();

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "searchEmployee");

		List<Employee> employees = employeeService.listAll();
		Set<String> desgs = new HashSet<String>();
		Set<String> depts = new HashSet<String>();

		for (Employee employee : employees) {
			desgs.add(employee.getDesignationName());
			depts.add(employee.getDepartmentName());

		}

		if (bean1.getEmployeeCode().equals(null) || bean1.getEmployeeCode().equals("")) {
			bean1.setEmployeeCode(" ");
		}

		mav.addObject("designation", desgs);
		mav.addObject("dept", depts);
		mav.addObject("employees", employees);
		mav.addObject("bean1", bean1);

		List<Employee> employeelist = employeeService.listAll(bean1.getEmployeeCode(), bean1.getDesg(),
				bean1.getDept());
		for (Employee employee : employeelist) {
			EmployeePlanClaimBean bean = new EmployeePlanClaimBean();
			List<BenefitPlanEmployee> benefitPlanEmployees = new ArrayList<BenefitPlanEmployee>();
			benefitPlanEmployees = employeeService.listPlanEmployee(employee.getEmployeeId(), fiscalYear, true);
			bean.setEmployee(employee);
			bean.setNoOfPlans(benefitPlanEmployees.size());
			beans.add(bean);
		}
		mav.addObject("bean", beans);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/reports/employee/search/view/{employeeId}", method = RequestMethod.GET)
	public ModelAndView viewEmployeePlans(@PathVariable(value = "employeeId") Integer employeeId,
			HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		String fiscalYear = appContext.getCurrentFiscalYear();

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewSearchedEmployee");

		EmployeePlanClaimBean bean = new EmployeePlanClaimBean();
		Employee employee = employeeService.get(employeeId);

		List<BenefitPlanEmployee> benefitPlanEmployees = new ArrayList<BenefitPlanEmployee>();
		List<BenefitPlanEmployee> newbenefitPlanEmployees = new ArrayList<BenefitPlanEmployee>();

		benefitPlanEmployees = employeeService.listPlanEmployee(employeeId, fiscalYear, true);
		for (BenefitPlanEmployee benefitPlanEmployee : benefitPlanEmployees) {
			benefitPlanEmployee = benefitPlanRE.calculateAmount(benefitPlanEmployee);
			newbenefitPlanEmployees.add(benefitPlanEmployee);
		}

		bean.setEmployee(employee);
		bean.setBenefitPlanEmployee(newbenefitPlanEmployees);
		bean.setNoOfPlans(newbenefitPlanEmployees.size());

		mav.addObject("bean", bean);
		return mav;
	}

	@RequestMapping(value = "/home/myFlexiPlans/details/optedPlan/{planEmployeeId}")
	public ModelAndView showSubmittedPlanDetails(HttpServletRequest request,
			@PathVariable("planEmployeeId") Integer planEmployeeId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "optedFlexiPlanDetails");

		BenefitPlanEmployee planEmployee = benefitPlanService.getPlanEmployee(planEmployeeId);
		planEmployee = benefitPlanRE.calculateAmount(planEmployee);

		if (planEmployee != null) {
			List<BenefitPlanClaim> allClaims = planEmployee.getClaims();
			int approvedCount = 0;
			int rejectedCount = 0;
			int pendingCount = 0;
			BigDecimal totalClaimAmountSubmited=new BigDecimal(0);
			BigDecimal approvedTotal = new BigDecimal(0);
			BigDecimal pendingAmount=new BigDecimal(0);
			BigDecimal rejectedAmount=new BigDecimal(0);
			for (BenefitPlanClaim claim : allClaims) {
				if (claim.getStatus().equals(BenefitsConstants.CLAIM_STATUS_FIN_APPR)
						|| claim.getStatus().equals(BenefitsConstants.CLAIM_STATUS_HR_APPR)) {
					approvedTotal=approvedTotal.add(claim.getTotalApprovedAmount());
					approvedCount++;
				} else if (claim.getStatus().equals(BenefitsConstants.CLAIM_STATUS_HR_REJECT)
						|| claim.getStatus().equals(BenefitsConstants.CLAIM_STATUS_FIN_REJECT)) {
					rejectedAmount=rejectedAmount.add(claim.getTotalRequestedAmount());
					rejectedCount++;
				} else if (claim.getStatus().equals(BenefitsConstants.CLAIM_STATUS_SUBMIT)) {
					pendingAmount=pendingAmount.add(claim.getTotalRequestedAmount());
					pendingCount++;
				}
				totalClaimAmountSubmited=totalClaimAmountSubmited.add(claim.getTotalRequestedAmount());
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

			if (planEmployee.getBenefitPlan().getOptDocumentsRequired()) {
				if (!planEmployee.getDocuments().isEmpty()) {
					List<DocumentVO> documents = new ArrayList<DocumentVO>();
					for (BenefitPlanEmployeeDoc doc : planEmployee.getDocuments()) {
						DocumentVO vo = new DocumentVO();
						vo.setDocManUuid(doc.getDocmanUUId());
						vo.setDownloadUrl(
								docmanRestClient.getDownloadUrl(doc.getDocmanUUId(), appContext.getUserLoginKey()));
						vo.setDocumentType(doc.getBenefitPlanDocument().getDocumentName());

						documents.add(vo);
					}
					mav.addObject("documents", documents);
					
				}
			}

			mav.addObject("hasError", false);
			mav.addObject("planEmployee", planEmployee);
			mav.addObject("approvedClaimCount", approvedCount);
			mav.addObject("rejectedClaimCount", rejectedCount);
			mav.addObject("pendingClaimCount", pendingCount);
			mav.addObject("claimPercentage", new BigDecimal(claimPercentage).setScale(0, RoundingMode.HALF_UP));
			mav.addObject("totalClaimAmountSubmited", totalClaimAmountSubmited);
			mav.addObject("approvedTotal", approvedTotal);
			mav.addObject("pendingAmount", pendingAmount);
			mav.addObject("rejectedAmount", rejectedAmount);

		} else {
			mav.addObject("hasError", true);
		}

		benefitPlanService.editPlanEmployee(planEmployee);

		return mav;
	}

	/**
	 * 
	 * Submit all plans for an year
	 * 
	 * @param
	 * @param
	 * @retuns String
	 * 
	 */
	@RequestMapping(value = "/home/myFlexiPlans/submitMyChoice/{optId}", method = RequestMethod.GET)
	public String submitMyChoice(HttpServletRequest request, @PathVariable("optId") Integer optId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		if (appContext != null) {
			EmpBenPlansYrlyOpts yearlyOpt = employeeService.getPlanYrlyOpts(optId);

			if (yearlyOpt == null) {
				yearlyOpt = employeeService.getPlanYrlyOpts(appContext.getCurrentEmployee().getEmployeeId(),
						appContext.getCurrentFiscalYear());
			}

			if (yearlyOpt != null) {
				List<BenefitPlanEmployee> optedPlans = benefitPlanService.listByYearlyOpt(optId);

				if (optedPlans.size() > 0) {
					Boolean planEmployeeUpdateError = false;
					for (BenefitPlanEmployee planEmployee : optedPlans) {
						planEmployee = benefitPlanRE.calculateAmount(planEmployee);
						planEmployee.setStatus(BenefitsConstants.EMP_PLAN_OPT_STATUS_NOT_APPROVED);
						planEmployee.setActive(Boolean.TRUE);
						planEmployee.setUpdatedBy(appContext.getUserName());

						if (!benefitPlanService.editPlanEmployee(planEmployee)) {
							planEmployeeUpdateError = true;
							break;
						}
					}
					if (!planEmployeeUpdateError) {
						yearlyOpt.setStatus(BenefitsConstants.EMP_YERLY_PLAN_OPT_STATUS_SUBMITTED);
						yearlyOpt.setUpdatedBy(appContext.getUserName());

						if (employeeService.editYearlyOpt(yearlyOpt)) {
							// optStatus 1 means success
							System.out.println("Yealry Plan updated, all plans updated");
							System.out.println("Now sending email");

							String subject = "Yearly Flexi Plan Enrolled";
							String body = EmailFormatter.flexiPlanChoiceRequest(optedPlans);
							String toList = appContext.getCurrentEmployee().getEmail();

							String ccTaxReturns = EmailProperties.getProperty(BenefitsConstants.PROP_TAX_RETURNS_EMAIL);
							EmailService emailService = new EmailService(toList, ccTaxReturns, body, subject);

							Thread emailThread = new Thread(emailService);
							emailThread.start();

							return "redirect:/home/myFlexiPlans?optStatus=1";
						} else {
							// optStatus -1 means system error
							return "redirect:/home/myFlexiPlans?optStatus=-1";
						}

					} else {
						// optStatus -1 means system error
						return "redirect:/home/myFlexiPlans?optStatus=-1";
					}
				} else {
					// optStatus 0 means no plans selected, can be treated as a
					// warning
					return "redirect:/home/myFlexiPlans?optStatus=0";
				}

			} else {
				// optStatus -1 means system error
				return "redirect:/home/myFlexiPlans?optStatus=-1";
			}
		}
		return "";
	}

	@RequestMapping(value = "/home/myFlexiPlans/viewMyDirectClaims/{planEmployeeId}")
	public ModelAndView viewMyPlanDetails(HttpServletRequest request,
			@PathVariable("planEmployeeId") Integer planEmployeeId) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "viewMyDirectClaims");

		List<BenefitPlanDirectClaim> viewClaims = benefitPlanService.listDirectClaimsByPlan(planEmployeeId);

		mav.addObject("viewClaims", viewClaims);
		System.out.println("====Success====");

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/claimDetails", method = RequestMethod.GET)
	public ModelAndView claimDetailsSearch(HttpServletRequest request, HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		SearchBean bean = (SearchBean) request.getSession().getAttribute("searchBean");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "MedicalReimbursement");
		List<BenefitPlan> allPlans = benefitPlanService.listAll();

		if (bean == null) {
			bean = new SearchBean();
			bean.setFiscalYear(appContext.getCurrentFiscalYear());
			bean.setBenefitPlanId(0);
		}

		mav.addObject("bean", bean);
		if (allPlans != null && allPlans.size() > 0) {

			mav.addObject("plans", allPlans);
		}
		mav.addObject("bean", bean);
		mav.addObject("searchBean", bean);

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/claimDetails", method = RequestMethod.POST)
	public ModelAndView claimDetails(HttpServletRequest request, @ModelAttribute("bean") SearchBean bean,
			HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "MedicalReimbursement");
		List<ClaimPeriodBean> beans = new ArrayList<ClaimPeriodBean>();
		List<BenefitPlan> Plans = benefitPlanService.listAll();

		List<BenefitPlanEmployee> allPlans = benefitPlanService.listPlanEmployees(bean.getFiscalYear(),
				bean.getBenefitPlanId());
		if (allPlans.size() <= 0) {

			mav.addObject("plans", Plans);

			mav.addObject("bean", bean);
			return mav;
		}
		int frequency = 12 / Integer.parseInt(allPlans.get(0).getBenefitPlan().getClaimFrequency());

		for (BenefitPlanEmployee benefitPlanEmployee : allPlans) {
			ClaimPeriodBean claimPeriodBean = new ClaimPeriodBean();
			claimPeriodBean.setPlanEmployee(benefitPlanEmployee);
			List<BenefitPlanEmployeeClaimPeriod> ClaimPeriods = benefitPlanService
					.listPreviousClaims(benefitPlanEmployee.getPlanEmployeeId());
			BigDecimal carry = BigDecimal.ZERO;
			Integer j = 1;
			BigDecimal nonPayrolledAmount = BigDecimal.ZERO;
			if (ClaimPeriods != null) {
				if (ClaimPeriods.size() > 1) {
					BenefitPlanEmployeeClaimPeriod claimPeriod2 = new BenefitPlanEmployeeClaimPeriod();
					BenefitPlanEmployeeClaimPeriod claimPeriod1 = ClaimPeriods.get(0);
					Integer size = ClaimPeriods.size();
					while (j < size) {
						claimPeriod2 = ClaimPeriods.get(j);
						carry = claimPeriod1.getCarryForward();
						if (carry.compareTo(BigDecimal.ZERO) < 0) {
							carry = carry.negate();
							claimPeriod2.setTotalApprovedAmount(claimPeriod2.getTotalApprovedAmount().add(carry));
							ClaimPeriods.set(j, claimPeriod2);

						} else if (carry.compareTo(BigDecimal.ZERO) > 0) {
							claimPeriod2.setAlottedAmount(claimPeriod2.getAlottedAmount().add(carry));
							ClaimPeriods.set(j, claimPeriod2);
						}
						claimPeriod1 = claimPeriod2;
						j++;
					}
					nonPayrolledAmount = claimPeriod2.getCarryForward();
				} else if (ClaimPeriods.size() == 1) {
					nonPayrolledAmount = ClaimPeriods.get(0).getCarryForward();
				}
			}

			BigDecimal qtryAmt = (benefitPlanEmployee.getYearlyDeduction().divide(new BigDecimal(12), 2,
					RoundingMode.HALF_UP))
							.multiply(new BigDecimal(benefitPlanEmployee.getBenefitPlan().getClaimFrequency()));
			beans.add(claimPeriodBean);
			Boolean allotedFlag = false;

			/*
			 * if we are not in last period,then create and fill all remaining
			 * peiods with zero except for alloted and approved for which carry
			 * should be considered.
			 */

			if (ClaimPeriods.size() < frequency) {

				for (int i = ClaimPeriods.size(); i < frequency; i++) {
					BigDecimal value = new BigDecimal(0);

					BenefitPlanEmployeeClaimPeriod claimPeriod = new BenefitPlanEmployeeClaimPeriod();

					if (allotedFlag == false) {
						if (nonPayrolledAmount.compareTo(BigDecimal.ZERO) > 0) {

							claimPeriod.setAlottedAmount(qtryAmt.add(nonPayrolledAmount));

						} else {
							claimPeriod.setAlottedAmount(qtryAmt);

						}
						if (nonPayrolledAmount.compareTo(BigDecimal.ZERO) < 0) {

							nonPayrolledAmount = nonPayrolledAmount.negate();

							if (nonPayrolledAmount.compareTo(qtryAmt) > 0) {
								claimPeriod.setTotalApprovedAmount(qtryAmt);

								nonPayrolledAmount = nonPayrolledAmount.subtract(qtryAmt);

							} else {
								claimPeriod.setTotalApprovedAmount(nonPayrolledAmount);
								nonPayrolledAmount = BigDecimal.ZERO;

							}
						}
						allotedFlag = true;
					} else {
						claimPeriod.setAlottedAmount(qtryAmt);

					}

					claimPeriod.setPlanEmployee(benefitPlanEmployee);
					claimPeriod.setTotalRequestedAmount(value);
					claimPeriod.setPayRolledAmount(value);
					ClaimPeriods.add(claimPeriod);
				}
			}
			BigDecimal taxableAmt = benefitPlanEmployee.getTaxableAmount();
			if (taxableAmt.equals(BigDecimal.ZERO)
					&& benefitPlanEmployee.getNonPayrolledAmount().compareTo(BigDecimal.ZERO) < 0) {
				taxableAmt = BigDecimal.ZERO;
			}
			claimPeriodBean.setTaxableAmt(taxableAmt);
			claimPeriodBean.setClaimPeriods(ClaimPeriods);
		}
		mav.addObject("listClaims", beans);
		mav.addObject("bean", bean);
		mav.addObject("frequency", frequency);
		if (allPlans != null && allPlans.size() > 0) {

			mav.addObject("plans", Plans);
		}

		return mav;
	}

	// Opted Out Employees

	@RequestMapping(value = "/home/controlPanel/flexiPlans/optedOutEmployees/search", method = RequestMethod.GET)
	public ModelAndView showOptedOutEmployees(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		SearchBean bean = (SearchBean) request.getSession().getAttribute("searchBean");

		mav = AuthorizationUtil.authorizeAdmin(appContext, "viewOptedOutEmployees");
		if (bean == null) {
			bean = new SearchBean();
			bean.setFiscalYear(appContext.getCurrentFiscalYear());
		}
		List<BenefitPlan> plans = benefitPlanService.listAllActive(appContext.getCurrentFiscalYear());
		if (bean.getBenefitPlanId() != null && bean.getBenefitPlanId() > 0) {
			List<PlanEmployeeDenies> planDenies = benefitPlanService.listDenies(bean.getBenefitPlanId());
			mav.addObject("planDenies", planDenies);
		}
		mav.addObject("bean", bean);
		mav.addObject("searchBean", bean);
		mav.addObject("plans", plans);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/optedOutEmployees/search", method = RequestMethod.POST)
	public ModelAndView searchOptedOutEmployees(HttpServletRequest request, @ModelAttribute("bean") SearchBean bean,
			HttpServletResponse response) {
		ModelAndView mav = null;
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		mav = AuthorizationUtil.authorizeAdmin(appContext, "viewOptedOutEmployees");

		List<BenefitPlan> plans = benefitPlanService.listAllActive(appContext.getCurrentFiscalYear());
		List<PlanEmployeeDenies> planDenies = benefitPlanService.listDenies(bean.getBenefitPlanId());

		mav.addObject("bean", bean);
		mav.addObject("searchBean", bean);
		mav.addObject("planDenies", planDenies);
		mav.addObject("plans", plans);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/optedEmployees/approveSelected", method = RequestMethod.GET)
	public ModelAndView approveSelected(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"redirect:/home/controlPanel/flexiPlans/optedEmployees");

		String selectedEmpIds = request.getParameter("approveSelected");
		System.out.println("selected employee Ids" + selectedEmpIds);

		String[] empIds = selectedEmpIds.split(",");
		Integer planEmpId = Integer.parseInt(empIds[0]);
		System.out.println("first Emp Id" + planEmpId);

		for (String ids : empIds) {

			Integer planEmpIds = Integer.parseInt(ids);
			System.out.println("Plan Employee Ids " + planEmpIds);

			BenefitPlanEmployee benefitPlanEmployee = benefitPlanService.getPlanEmployee(planEmpIds);

			List<Dependent> dependents = benefitPlanEmployee.getEmployee().getDependents();
			for (Dependent dependentList : dependents) {
				System.out.println("-------Dependent List --- " + dependentList.getDependentName());
			}

			List<BenefitPlanEmployeeDoc> docs = benefitPlanService.getDocs(planEmpIds);
			if (docs.size() == 0) {
				benefitPlanEmployee.setStatus(BenefitsConstants.EMP_PLAN_OPT_APPROVED);
				benefitPlanEmployee.setActive(true);
				System.out.println("Update starts here");
				benefitPlanService.approveSelected(benefitPlanEmployee);

				/**
				 * Email for selected plans approval
				 */
				EmailService emailService = null;
				// String messageSubject =
				// "Benefits Portal
				// -"+benefitPlanEmployee.getEmployee().getFirstName()+"
				// "+benefitPlanEmployee.getEmployee().getLastName()+"'s
				// Employee Benefit Plan
				// Approved";
				String messageSubject = "Flexi Plan Approved by HR";
				String messageBody = EmailFormatter.newPlanApproved(benefitPlanEmployee);

				emailService = new EmailService(benefitPlanEmployee.getEmployee().getEmail(),
						EmailProperties.getProperty("taxReturnsEmail") + "," + EmailProperties.getProperty("hrEmail"),
						messageBody, messageSubject);

				System.out.println("----------------Sending email---------------");
				Thread emailThread = new Thread(emailService);
				emailThread.start();

			}

			mav.addObject("dependents", dependents);

		}

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/fileImport", method = RequestMethod.GET)
	public ModelAndView PlanReportFileImport(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "benefitPortalFileImport");

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/flexiPlans/fileImport", method = RequestMethod.POST)
	public ModelAndView PlanReportFileUpload(MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "benefitPortalFileImport");
		/*
		 * if(file == null) { mav.addObject("noFileSeclected",
		 * "Please Choose a file to upload"); return new
		 * ModelAndView("redirect:/home/controlPanel/flexiPlans/fileImport"); }
		 */

		if (!ServletFileUpload.isMultipartContent(request)) {
			PrintWriter writer = response.getWriter();
			writer.println("No data to upload");
			writer.flush();
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyyyHHmmss");
		String date = simpleDateFormat.format(new Date());

		String FileNameFormatted = "SPR_BP_DIRECTCLAIMUPLOAD_" + date + ".xlsx";

		String fileNameUpload = file.getName();
		String fileType = file.getContentType();
		String fileOriginalName = file.getOriginalFilename();
		long fileSize = file.getSize();

		System.out.println("Uploaded File Name :" + fileNameUpload);
		System.out.println("Uploaded File Original Name :" + fileOriginalName);
		System.out.println("Uploaded File Type :" + fileType);
		System.out.println("Uploaded File Size :" + fileSize);

		String uploadPath = BenefitsConstants.FILE_IMPORT_DIRECT_CLAIM_UPLOAD_DIR + FileNameFormatted;
		// String uploadPath =
		// BenefitsConstants.FILE_IMPORT_DIRECT_CLAIM_UPLOAD_DIR+"_"+new Date();

		System.out.println("upload Path " + uploadPath);

		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			System.out.println("---------------File Upload Begining-------------");

			byte[] bytes = file.getBytes();
			file.transferTo(uploadDir);
			System.out.println("---------------File Upload End-------------");

			System.out.println("----------------------------------" + date);

			BenefitPortalFileImport benefitPortalFileImport = new BenefitPortalFileImport();
			benefitPortalFileImport.setClientFilename(fileOriginalName);
			benefitPortalFileImport.setFileName(FileNameFormatted);
			benefitPortalFileImport.setFileType(fileType);
			benefitPortalFileImport.setStatus(BenefitsConstants.FILE_IMPORT_PROCESS_UPLOADED);
			benefitPortalFileImport.setCreatedBy(appContext.getUserName());
			benefitPortalFileImport.setUpdatedBy(appContext.getUserName());

			if (benefitPortalFileImportService.insert(benefitPortalFileImport)) {
				mav.addObject("successMessage",
						"File Uploaded Successfully. File will be processed within the scheduled time, Approx ~1hour. Status will be intimated through E-Mail.");

			}

		} catch (Exception ex) {

			mav.addObject("errorMessage", "Error Uploading File. Please Try Again!");

		}

		return mav;

	}

}
