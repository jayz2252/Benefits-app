package com.speridian.benefits2.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.speridian.benefits2.beans.Feild;

import com.speridian.benefits2.beans.SearchBean;
import com.speridian.benefits2.model.dao.BenefitsPropertyDao;
import com.speridian.benefits2.model.dao.EmployeeDao;
import com.speridian.benefits2.model.pojo.BenefitPlan;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployee;
import com.speridian.benefits2.model.pojo.BenefitsProperty;
import com.speridian.benefits2.model.pojo.ITEmployeeInvestment;
import com.speridian.benefits2.model.pojo.EmpRentDetail;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.ITCategory;
import com.speridian.benefits2.model.pojo.ITDeclarationFeilds;
import com.speridian.benefits2.model.pojo.ITInvestmentField;
import com.speridian.benefits2.model.pojo.ITDocmanHistory;
import com.speridian.benefits2.model.pojo.ITEmployee;
import com.speridian.benefits2.model.pojo.ITEmployeeHouseLoan;

import com.speridian.benefits2.model.pojo.ItHouseLoanField;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.service.ITDeclarationService;
import com.speridian.benefits2.service.ITReportService;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.util.AuthorizationUtil;
import com.speridian.benefits2.ws.client.docman.rest.DocmanRestClient;
import com.speridian.benefits2.ws.constants.BenefitsWSConstants;

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
public class ITController {

	@Autowired
	ITReportService iTReportService;

	@Autowired
	ITDeclarationService iTDeclarationService;

	@Autowired
	EmployeeDao employeeDao;

	@Autowired
	BenefitsPropertyDao propertyDao;

	@Autowired
	DocmanRestClient docmanRestClient;

	/**
	 * Shows the very first page(manageBenefitPlans.jsp) of all non-deleted
	 * Benefit Plans
	 * 
	 * @param request
	 * @param response
	 * @return mav
	 */

	@RequestMapping(value = "/home/controlPanel/ITdeclaration/adminpage", method = RequestMethod.GET)
	public ModelAndView showAdminITdeclaration() {
		ModelAndView mv = new ModelAndView("adminITDeclaration", "iTDeclarationFeilds", new ITDeclarationFeilds());
		List<ITInvestmentField> itDeclarationFeildsA = new ArrayList<ITInvestmentField>();
		List<ITInvestmentField> itDeclarationFeildsB = new ArrayList<ITInvestmentField>();
		List<ItHouseLoanField> itDeclarationFieldsD = new ArrayList<ItHouseLoanField>();
		List<ItHouseLoanField> itDeclarationFieldsE = new ArrayList<ItHouseLoanField>();
		List<ItHouseLoanField> itDeclarationFieldsF = new ArrayList<ItHouseLoanField>();
		List<ItHouseLoanField> itDeclarationFieldsG = new ArrayList<ItHouseLoanField>();

		char categoryName1 = 'A';
		char categoryName2 = 'B';
		char categoryName4 = 'D';
		char categoryName5 = 'E';
		char categoryName6 = 'F';
		char categoryName7 = 'G';

		ITCategory category1 = iTDeclarationService.findCategoryByName(categoryName1);
		Integer categoryId1 = category1.getCategoryId();
		System.out.println("============category Id 1========" + categoryId1);
		List<ITInvestmentField> itDeclarationFeilds1 = iTDeclarationService
				.listAllFeildsInCategory(category1.getCategoryId());
		itDeclarationFeildsA.addAll(itDeclarationFeilds1);

		ITCategory category2 = iTDeclarationService.findCategoryByName(categoryName2);
		Integer categoryId2 = category2.getCategoryId();
		System.out.println("============category Id 2========" + categoryId2);
		List<ITInvestmentField> itDeclarationFeilds2 = iTDeclarationService
				.listAllFeildsInCategory(category2.getCategoryId());
		itDeclarationFeildsB.addAll(itDeclarationFeilds2);

		ITCategory category4 = iTDeclarationService.findCategoryByName(categoryName4);
		Integer categoryId4 = category4.getCategoryId();
		System.out.println("============category Id 4========" + categoryId4);
		List<ItHouseLoanField> itDeclarationFeilds4 = iTDeclarationService
				.listAllFieldsInCategoryD(category4.getCategoryId());
		itDeclarationFieldsD.addAll(itDeclarationFeilds4);

		ITCategory category5 = iTDeclarationService.findCategoryByName(categoryName5);
		Integer categoryId5 = category5.getCategoryId();
		System.out.println("============category Id 5========" + categoryId5);
		List<ItHouseLoanField> itDeclarationFeilds5 = iTDeclarationService
				.listAllFieldsInCategoryD(category5.getCategoryId());
		itDeclarationFieldsE.addAll(itDeclarationFeilds5);

		ITCategory category6 = iTDeclarationService.findCategoryByName(categoryName6);
		Integer categoryId6 = category6.getCategoryId();
		System.out.println("============category Id 6========" + categoryId6);
		List<ItHouseLoanField> itDeclarationFeilds6 = iTDeclarationService
				.listAllFieldsInCategoryD(category6.getCategoryId());
		itDeclarationFieldsF.addAll(itDeclarationFeilds6);

		ITCategory category7 = iTDeclarationService.findCategoryByName(categoryName7);
		Integer categoryId7 = category7.getCategoryId();
		System.out.println("============category Id 7========" + categoryId7);
		List<ItHouseLoanField> itDeclarationFeilds7 = iTDeclarationService
				.listAllFieldsInCategoryD(category7.getCategoryId());
		itDeclarationFieldsG.addAll(itDeclarationFeilds7);

		Collections.sort(itDeclarationFeildsA, new Comparator<ITInvestmentField>() {
			public int compare(ITInvestmentField fieldA1, ITInvestmentField fieldA2) {
				return fieldA1.getFieldIndex().compareTo(fieldA2.getFieldIndex());
			}
		});

		mv.addObject("feilds1", itDeclarationFeildsA);
		mv.addObject("feilds2", itDeclarationFeildsB);
		mv.addObject("fields4", itDeclarationFieldsD);
		mv.addObject("fields5", itDeclarationFieldsE);
		mv.addObject("fields6", itDeclarationFieldsF);
		mv.addObject("fields7", itDeclarationFieldsG);
		return mv;
	}

	@RequestMapping(value = "/ITdeclaration/saveNewInvestmentForA", method = RequestMethod.POST)
	public String saveNewInvestmentA(@ModelAttribute("iTDeclarationFeilds") ITDeclarationFeilds iTDeclarationFeilds,
			HttpServletRequest request) {

		String buttonAction = request.getParameter("buttonAction");
		System.out.println("BUTTON ACTION : " + buttonAction);
		if (null != buttonAction && !buttonAction.isEmpty()) {
			if (buttonAction.equals("ADD_A")) {
				ITInvestmentField itDeclarationInvestments = new ITInvestmentField();

				if (null != request.getParameter("adddescA") && !(request.getParameter("adddescA").isEmpty())
						&& null != request.getParameter("addsectA") && !(request.getParameter("addsectA").isEmpty())) {
					char categoryName = 'A';

					String fieldIndex = request.getParameter("fieldIndexA");
					System.out.println("---------------" + fieldIndex);
					Integer fieldIndexValue = Integer.parseInt(fieldIndex);
					System.out.println("--------------------Field Index--------------" + fieldIndexValue);

					ITCategory category = iTDeclarationService.findCategoryByName(categoryName);
					Integer categoryId = category.getCategoryId();
					List<ITInvestmentField> itDeclarationFeildsA = iTDeclarationService.listAllInvestments(categoryId);
					int fieldListASize = itDeclarationFeildsA.size() + 1;

					itDeclarationInvestments.setCategory(category);
					itDeclarationInvestments.setSection(request.getParameter("addsectA"));
					itDeclarationInvestments.setDescription(request.getParameter("adddescA"));
					itDeclarationInvestments.setFieldIndex(fieldListASize);
					iTDeclarationService.insertITDeclarationInvestments(itDeclarationInvestments);

				}
			} else if (buttonAction.equals("DELETE")) {

				String investmentId = request.getParameter("investmentId");
				System.out.println("investmentId : " + investmentId);
				iTDeclarationService.deleteITDeclarationInvestments(Integer.parseInt(investmentId));

			} else if (buttonAction.equals("EDIT")) {

				String investmentId = request.getParameter("investmentId");
				System.out.println("investmentId : " + investmentId);
				ITInvestmentField itDeclarationInvestments2 = iTDeclarationService
						.findInvestmentById(Integer.parseInt(investmentId));
				itDeclarationInvestments2.setDescription(request.getParameter("editdescA"));
				itDeclarationInvestments2.setSection(request.getParameter("editsectA"));
				iTDeclarationService.updateITDeclarationInvestments(itDeclarationInvestments2);

			}

		}
		return "redirect:/home/controlPanel/ITdeclaration/adminpage";
	}

	@RequestMapping(value = "/ITdeclaration/saveNewInvestmentForB", method = RequestMethod.POST)
	public String saveNewInvestmentB(@ModelAttribute("iTDeclarationFeilds") ITDeclarationFeilds iTDeclarationFeilds,
			HttpServletRequest request) {

		String buttonAction1 = request.getParameter("buttonAction1");
		System.out.println("BUTTON ACTION : " + buttonAction1);
		if (null != buttonAction1 && !buttonAction1.isEmpty()) {
			if (buttonAction1.equals("ADD_B")) {
				ITInvestmentField itDeclarationInvestments = new ITInvestmentField();

				if (null != request.getParameter("adddescB") && !(request.getParameter("adddescB").isEmpty())
						&& null != request.getParameter("addsectB") && !(request.getParameter("addsectB").isEmpty())) {
					char categoryName = 'B';

					ITCategory category = iTDeclarationService.findCategoryByName(categoryName);
					Integer categoryId = category.getCategoryId();
					List<ITInvestmentField> itDeclarationFeildsB = iTDeclarationService.listAllInvestments(categoryId);
					int fieldListBSize = itDeclarationFeildsB.size() + 1;

					itDeclarationInvestments.setCategory(category);
					itDeclarationInvestments.setSection(request.getParameter("addsectB"));
					itDeclarationInvestments.setDescription(request.getParameter("adddescB"));
					itDeclarationInvestments.setFieldIndex(fieldListBSize);
					iTDeclarationService.insertITDeclarationInvestments(itDeclarationInvestments);

				}

			} else if (buttonAction1.equals("DELETE") || ("DELETE").equals(buttonAction1)) {
				String investmentId = request.getParameter("investmentId1");
				System.out.println("investmentId : " + investmentId);
				iTDeclarationService.deleteITDeclarationInvestments(Integer.parseInt(investmentId));
			} else if (buttonAction1.equals("EDIT")) {

				String investmentId = request.getParameter("investmentId1");
				ITInvestmentField itDeclarationInvestments2 = iTDeclarationService
						.findInvestmentById(Integer.parseInt(investmentId));
				itDeclarationInvestments2.setDescription(request.getParameter("editdescB"));
				itDeclarationInvestments2.setSection(request.getParameter("editsectB"));
				iTDeclarationService.updateITDeclarationInvestments(itDeclarationInvestments2);
			}

		}
		return "redirect:/home/controlPanel/ITdeclaration/adminpage";
	}

	@RequestMapping(value = "/ITdeclaration/saveNewInvestmentForD", method = RequestMethod.POST)
	public String saveNewInvestmentD(@ModelAttribute("iTHouseLoanFields") ItHouseLoanField iTHouseLoanField,
			HttpServletRequest request) {

		System.out.println("==============SECTION D ADD============");
		String buttonAction4 = request.getParameter("buttonAction4");
		System.out.println("BUTTON ACTION : " + buttonAction4);
		if (null != buttonAction4 && !buttonAction4.isEmpty()) {
			if (buttonAction4.equals("ADD_D")) {

				ItHouseLoanField itHouseLoanField2 = new ItHouseLoanField();
				if (null != request.getParameter("adddescD") && !(request.getParameter("adddescD").isEmpty())
						&& null != request.getParameter("addsectD") && !(request.getParameter("addsectD").isEmpty())) {
					char categoryName = 'D';

					ITCategory category = iTDeclarationService.findCategoryByName(categoryName);
					Integer categoryId = category.getCategoryId();
					List<ItHouseLoanField> itDeclarationFeildsD = iTDeclarationService.listAllFields(categoryId);
					int fieldListDSize = itDeclarationFeildsD.size() + 1;

					String fieldName = request.getParameter("adddescD");
					String type = request.getParameter("addsectD");
					itHouseLoanField2.setFieldIndex(fieldListDSize);

					itHouseLoanField2.setCategory(category);
					itHouseLoanField2.setFieldLabel(fieldName);
					itHouseLoanField2.setType(type);

					iTDeclarationService.insertHousingLoanCategoryD(itHouseLoanField2);

				}

			} else if (buttonAction4.equals("DELETE") || ("DELETE").equals(buttonAction4)) {
				String houseFieldId = request.getParameter("investmentId4");
				System.out.println("HouseFieldId : " + houseFieldId);
				iTDeclarationService.deleteITHousingLoan(Integer.parseInt(houseFieldId));
			} else if (buttonAction4.equals("EDIT")) {

				String houseFieldId = request.getParameter("investmentId4");
				ItHouseLoanField itHouseLoanField = iTDeclarationService
						.findITHousingLoanId(Integer.parseInt(houseFieldId));

				String fieldname = request.getParameter("editsectD");
				String fieldtype = request.getParameter("edittypeD");
				itHouseLoanField.setFieldLabel(fieldname);
				itHouseLoanField.setType(fieldtype);

				iTDeclarationService.updateHousingLoan(itHouseLoanField);
			}

		}
		return "redirect:/home/controlPanel/ITdeclaration/adminpage";
	}

	@RequestMapping(value = "/ITdeclaration/saveNewInvestmentForE", method = RequestMethod.POST)
	public String saveNewInvestmentE(@ModelAttribute("iTHouseLoanFields") ItHouseLoanField iTHouseLoanField,
			HttpServletRequest request) {
		System.out.println("==============SECTION E ADD============");
		String buttonAction5 = request.getParameter("buttonAction5");
		System.out.println("BUTTON ACTION : " + buttonAction5);
		if (null != buttonAction5 && !buttonAction5.isEmpty()) {
			if (buttonAction5.equals("ADD_E")) {

				ItHouseLoanField itHouseLoanField2 = new ItHouseLoanField();
				if (null != request.getParameter("adddescE") && !(request.getParameter("adddescE").isEmpty())
						&& null != request.getParameter("addsectE") && !(request.getParameter("addsectE").isEmpty())) {
					char categoryName = 'E';

					ITCategory category = iTDeclarationService.findCategoryByName(categoryName);
					Integer categoryId = category.getCategoryId();
					List<ItHouseLoanField> itDeclarationFeildsE = iTDeclarationService.listAllFields(categoryId);
					int fieldListESize = itDeclarationFeildsE.size() + 1;

					String fieldName = request.getParameter("adddescE");
					String type = request.getParameter("addsectE");

					itHouseLoanField2.setFieldIndex(fieldListESize);
					itHouseLoanField2.setCategory(category);
					itHouseLoanField2.setFieldLabel(fieldName);
					itHouseLoanField2.setType(type);
					iTDeclarationService.insertHousingLoanCategoryD(itHouseLoanField2);

				}

			} else if (buttonAction5.equals("DELETE") || ("DELETE").equals(buttonAction5)) {
				String houseFieldId = request.getParameter("investmentId5");
				System.out.println("HouseFieldId : " + houseFieldId);
				iTDeclarationService.deleteITHousingLoan(Integer.parseInt(houseFieldId));
			} else if (buttonAction5.equals("EDIT")) {
				System.out.println("-----Edit Action-----");
				String houseFieldId = request.getParameter("investmentId5");
				ItHouseLoanField itHouseLoanField = iTDeclarationService
						.findITHousingLoanId(Integer.parseInt(houseFieldId));

				String fieldname = request.getParameter("editsectE");
				String fieldtype = request.getParameter("edittypeE");
				itHouseLoanField.setFieldLabel(fieldname);
				itHouseLoanField.setType(fieldtype);

				iTDeclarationService.updateHousingLoan(itHouseLoanField);
			}

		}

		return "redirect:/home/controlPanel/ITdeclaration/adminpage";
	}

	@RequestMapping(value = "/ITdeclaration/saveNewInvestmentForF", method = RequestMethod.POST)
	public String saveNewInvestmentF(@ModelAttribute("iTHouseLoanFields") ItHouseLoanField iTHouseLoanField,
			HttpServletRequest request) {
		System.out.println("==============SECTION F ADD============");
		String buttonAction6 = request.getParameter("buttonAction6");
		System.out.println("BUTTON ACTION : " + buttonAction6);
		if (null != buttonAction6 && !buttonAction6.isEmpty()) {
			if (buttonAction6.equals("ADD_F")) {

				ItHouseLoanField itHouseLoanField2 = new ItHouseLoanField();
				if (null != request.getParameter("adddescF") && !(request.getParameter("adddescF").isEmpty())
						&& null != request.getParameter("addsectF") && !(request.getParameter("addsectF").isEmpty())) {
					char categoryName = 'F';

					ITCategory category = iTDeclarationService.findCategoryByName(categoryName);
					Integer categoryId = category.getCategoryId();
					List<ItHouseLoanField> itDeclarationFeildsF = iTDeclarationService.listAllFields(categoryId);
					int fieldListFSize = itDeclarationFeildsF.size() + 1;

					String fieldName = request.getParameter("adddescF");
					String type = request.getParameter("addsectF");

					itHouseLoanField2.setFieldIndex(fieldListFSize);
					itHouseLoanField2.setCategory(category);
					itHouseLoanField2.setFieldLabel(fieldName);
					itHouseLoanField2.setType(type);

					iTDeclarationService.insertHousingLoanCategoryD(itHouseLoanField2);

				}

			} else if (buttonAction6.equals("DELETE") || ("DELETE").equals(buttonAction6)) {
				String houseFieldId = request.getParameter("investmentId6");
				System.out.println("HouseFieldId : " + houseFieldId);
				iTDeclarationService.deleteITHousingLoan(Integer.parseInt(houseFieldId));
			} else if (buttonAction6.equals("EDIT")) {

				String houseFieldId = request.getParameter("investmentId6");
				ItHouseLoanField itHouseLoanField = iTDeclarationService
						.findITHousingLoanId(Integer.parseInt(houseFieldId));

				String fieldname = request.getParameter("editsectF");
				String fieldtype = request.getParameter("edittypeF");
				itHouseLoanField.setFieldLabel(fieldname);
				itHouseLoanField.setType(fieldtype);

				iTDeclarationService.updateHousingLoan(itHouseLoanField);
			}

		}

		return "redirect:/home/controlPanel/ITdeclaration/adminpage";
	}

	@RequestMapping(value = "/ITdeclaration/saveNewInvestmentForG", method = RequestMethod.POST)
	public String saveNewInvestmentG(@ModelAttribute("iTHouseLoanFields") ItHouseLoanField iTHouseLoanField,
			HttpServletRequest request) {
		System.out.println("==============SECTION G ADD============");
		String buttonAction7 = request.getParameter("buttonAction7");
		System.out.println("BUTTON ACTION : " + buttonAction7);
		if (null != buttonAction7 && !buttonAction7.isEmpty()) {
			if (buttonAction7.equals("ADD_G")) {

				ItHouseLoanField itHouseLoanField2 = new ItHouseLoanField();
				if (null != request.getParameter("adddescG") && !(request.getParameter("adddescG").isEmpty())
						&& null != request.getParameter("addsectG") && !(request.getParameter("addsectG").isEmpty())) {
					char categoryName = 'G';

					ITCategory category = iTDeclarationService.findCategoryByName(categoryName);
					Integer categoryId = category.getCategoryId();
					List<ItHouseLoanField> itDeclarationFeildsG = iTDeclarationService.listAllFields(categoryId);
					int fieldListGSize = itDeclarationFeildsG.size() + 1;

					String fieldName = request.getParameter("adddescG");
					String type = request.getParameter("addsectG");

					itHouseLoanField2.setFieldIndex(fieldListGSize);
					itHouseLoanField2.setCategory(category);
					itHouseLoanField2.setFieldLabel(fieldName);
					itHouseLoanField2.setType(type);

					iTDeclarationService.insertHousingLoanCategoryD(itHouseLoanField2);

				}

			} else if (buttonAction7.equals("DELETE") || ("DELETE").equals(buttonAction7)) {
				String houseFieldId = request.getParameter("investmentId7");
				System.out.println("HouseFieldId : " + houseFieldId);
				iTDeclarationService.deleteITHousingLoan(Integer.parseInt(houseFieldId));
			} else if (buttonAction7.equals("EDIT")) {

				String houseFieldId = request.getParameter("investmentId7");
				ItHouseLoanField itHouseLoanField = iTDeclarationService
						.findITHousingLoanId(Integer.parseInt(houseFieldId));

				String fieldname = request.getParameter("editsectG");
				String fieldtype = request.getParameter("edittypeG");
				itHouseLoanField.setFieldLabel(fieldname);
				itHouseLoanField.setType(fieldtype);

				iTDeclarationService.updateHousingLoan(itHouseLoanField);
			}

		}

		return "redirect:/home/controlPanel/ITdeclaration/adminpage";
	}

	@RequestMapping(value = "/ITdeclaration/viewEmployeeList", method = RequestMethod.GET)
	public ModelAndView showAllEmployee(HttpServletRequest request, HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mv = AuthorizationUtil.authorizeAdmin(appContext, "adminViews");

		List<ITEmployee> declarations = iTDeclarationService.listAll();
		mv.addObject("employeeList", declarations);
		mv.addObject("sessionKey", appContext.getUserLoginKey());

		return mv;
	}


	@RequestMapping(value = "/home/controlPanel/unlockITDeclaration", method = RequestMethod.GET)
	public ModelAndView showOptedEmployeesPage(HttpServletRequest request, HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "adminUnlockITForm");
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/unlockITDeclaration/search", method = RequestMethod.POST)
	public ModelAndView searchEmployees(HttpServletRequest request, HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "adminUnlockITForm");

		BenefitsProperty fiscalYear = propertyDao.get(BenefitsConstants.SYS_PROP_CURR_FISCAL_YEAR);
		String empCode = request.getParameter("empCode");
		ITEmployee declaration = iTDeclarationService.listAll(empCode, fiscalYear.getPropertyValue());
		List<ITEmployee> declarations = new ArrayList<ITEmployee>();
		if (declaration != null) {
			declarations.add(declaration);
		}
		mav.addObject("declaration", declarations);

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/unlockITDeclaration/{declarationId}", method = RequestMethod.GET)
	public ModelAndView UnlockITDeclaration(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "declarationId") Integer declarationId) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "adminUnlockITForm");

		ITEmployee declaration = iTDeclarationService.getById(declarationId);
		if (declaration != null) {
			declaration.setStatus(BenefitsConstants.IT_STATUS_OPEN);
			if (iTDeclarationService.update(declaration)) {
				mav.addObject("message", "Successfully unlocked Declaration Form");
			} else {
				mav.addObject("message", "Failed to unlock Declaration Form");
			}
		}

		return mav;
	}

}
