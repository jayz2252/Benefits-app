package com.speridian.benefits2.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.speridian.benefits2.model.pojo.BenefitPlan;
import com.speridian.benefits2.model.pojo.BenefitPlanDependency;
import com.speridian.benefits2.model.pojo.BenefitsProperty;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.HomePageNotification;
import com.speridian.benefits2.model.pojo.Hospital;
import com.speridian.benefits2.model.pojo.IncomeTaxSlab;
import com.speridian.benefits2.model.pojo.UserRole;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.service.BenefitPlanService;
import com.speridian.benefits2.service.EmployeeService;
import com.speridian.benefits2.service.HomePageNotificationService;
import com.speridian.benefits2.service.SettingsService;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.util.AuthorizationUtil;
import com.speridian.benefits2.util.DataTypeUtil;

/**
 * 
 * <pre>
 * Controller for Control Panel-->Settings
 * </pre>
 * 
 * @author jithin.kuriakose
 * @since 23-Feb-2017
 * 
 */
@Controller
public class SettingsController {

	AppContext appContext;

	@Autowired
	HomePageNotificationService homePageNotificationService;

	@Autowired
	SettingsService settingsService;

	@Autowired
	BenefitPlanService benefitPlanService;

	@Autowired
	EmployeeService employeeService;

	/*@RequestMapping(value = "/home/controlPanel/settings/properties/{success}")
	public ModelAndView showCommonProperties(HttpServletRequest request,
			@PathVariable("success") Integer success) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"manageProperties");

		List<BenefitsProperty> properties = settingsService.listAllProperties();

		mav.addObject("props", properties);

		if (success.equals(-1)) {
			mav.addObject("errorMessage",
					"Error while saving properties, please contact System Administrator");
		} else if (success.equals(1)) {
			mav.addObject("successMessage",
					"Successfully saved the properties..");
		}

		return mav;
	}*/

	@RequestMapping(value = "/home/controlPanel/settings/properties/save", method = RequestMethod.POST)
	public String saveCommonProperties(HttpServletRequest request,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");
		String nextView = AuthorizationUtil.authorizeAdmin(appContext,
				"manageProperties").getViewName();

		List<BenefitsProperty> properties = settingsService.listAllProperties();
		Boolean hasError = false;

		for (BenefitsProperty property : properties) {
			String value = request.getParameter(property.getPropertyCode());
			property.setPropertyValue(value);
			if (!settingsService.editProperty(property)) {
				hasError = true;
				break;
			}
		}
		if (hasError) {
			return "redirect:/home/controlPanel/settings/properties/-1";
		} else {
			return "redirect:/home/controlPanel/settings/properties/1";
		}
	}

	@RequestMapping(value = "/home/controlPanel/settings/properties/mutualDependency")
	public ModelAndView mutualDependency(HttpServletRequest request,
			HttpServletResponse response) {

		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"mutualDependency");

		List<BenefitPlanDependency> allPlansBenifitPlans = benefitPlanService
				.listAllBenefitPlan();
		mav.addObject("plans", allPlansBenifitPlans);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/settings/properties/addNewMutualDependency", method = RequestMethod.GET)
	public ModelAndView addNewMutualDependency(HttpServletRequest request,
			HttpServletResponse response) {

		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"addNewMutualDependency");
		List<BenefitPlan> allPlans = benefitPlanService.listAll();
		mav.addObject("plans", allPlans);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/settings/properties/addNewMutualDependency", method = RequestMethod.POST)
	public String SaveNewMutualDependency(HttpServletRequest request,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");
		int plan1 = Integer.parseInt(request.getParameter("plan1"));
		int plan2 = Integer.parseInt(request.getParameter("plan2"));

		BenefitPlan plan1Pojo = new BenefitPlan();
		plan1Pojo.setBenefitPlanId(plan1);

		BenefitPlan plan2Pojo = new BenefitPlan();
		plan2Pojo.setBenefitPlanId(plan2);

		BenefitPlanDependency dependency = new BenefitPlanDependency();
		dependency.setPlan1(plan1Pojo);
		dependency.setPlan2(plan2Pojo);
		dependency.setCreatedBy(appContext.getUserName());
		dependency.setUpdatedBy(appContext.getUserName());

		ModelAndView mav = null;
		if (benefitPlanService.addDependency(dependency)) {

			mav = AuthorizationUtil
					.authorizeAdmin(appContext,
							"redirect://home/controlPanel/settings/properties/mutualDependency");
		} else {
			mav = AuthorizationUtil
					.authorizeAdmin(appContext,
							"redirect://home/controlPanel/settings/properties/addNewMutualDependency");
		}

		return mav.getViewName();
	}

	@RequestMapping(value = "/home/controlPanel/settings/properties/adminSettings", method = RequestMethod.GET)
	public ModelAndView adminSettings(HttpServletRequest request,
			HttpServletResponse response) {

		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"adminSettings");

		List<UserRole> allPlans = benefitPlanService.listAllUserRoles();

		mav.addObject("plans", allPlans);
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/settings/properties/addNewUser", method = RequestMethod.GET)
	public ModelAndView addNewUser(HttpServletRequest request,
			HttpServletResponse response) {

		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"addNewUser");

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/settings/properties/addNewUser", method = RequestMethod.POST)
	public String addNewUserRole(HttpServletRequest request,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");
		String userName = request.getParameter("userName");
		String role = request.getParameter("role");
		UserRole addUser = new UserRole();
		addUser.setUserName(userName);
		addUser.setRole(role);
		addUser.setCreatedBy(appContext.getUserName());
		addUser.setUpdatedBy(appContext.getUserName());
		ModelAndView mav = null;
		if (benefitPlanService.insertUser(addUser)) {
			mav = AuthorizationUtil
					.authorizeAdmin(appContext,
							"redirect://home/controlPanel/settings/properties/adminSettings");
		} else {
			mav = AuthorizationUtil
					.authorizeAdmin(appContext,
							"redirect://home/controlPanel/settings/properties/addNewUser");
		}

		return mav.getViewName();
	}

	@RequestMapping(value = "/home/controlPanel/settings/properties/deleteUser/{userId}", method = RequestMethod.GET)
	public ModelAndView deleteUser(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "userId") Integer userId) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");

		UserRole userRolePojo = new UserRole();

		userRolePojo.setUserRoleId(userId);
		benefitPlanService.delete(userRolePojo);

		ModelAndView mav = AuthorizationUtil
				.authorizeAdmin(appContext,
						"redirect://home/controlPanel/settings/properties/adminSettings");

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/settings/properties/searchUser", method = RequestMethod.POST)
	public ModelAndView searchEmployee(HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");
		String empCode = request.getParameter("empCode").toUpperCase();

		ModelAndView mv = new ModelAndView("addNewUser");

		Employee employee = employeeService.getEmployeeByCode(empCode);

		if (employee != null) {
			mv.addObject("employee", employee);
		} else {
			mv.setViewName("redirect:/home/controlPanel/settings/properties/addNewUser");
			redirectAttributes.addFlashAttribute("ErrorMessage",
					"*Please check the employee code");

		}

		return mv;
	}

	// Home Page Notifications

	@RequestMapping(value = "/home/controlPanel/settings/properties/homePageNotifications")
	public ModelAndView showHomePageNotification(HttpServletRequest request,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"homePageNotification");

		List<HomePageNotification> homePageNotifications = homePageNotificationService.listAllNotifications();
		mav.addObject("notifications", homePageNotifications);
		
		
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/settings/properties/homePageNotifications/edit/{notificationId}")
	public ModelAndView showEditHomePageNotification(HttpServletRequest request,
			@PathVariable(value = "notificationId") int notificationId) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");

		ModelAndView mav = new ModelAndView();

		HomePageNotification homePageNotification = homePageNotificationService.get(notificationId);

		if (homePageNotification != null) {
			mav = AuthorizationUtil.authorizeAdmin(appContext, "editHomePageNotification");
			mav.addObject("notification", homePageNotification);
			mav.addObject("mode", "edit");
		} else {
			mav = AuthorizationUtil.authorizeAdmin(appContext, "homePageNotification");
			List<HomePageNotification> homePageNotifications = homePageNotificationService.listAllNotifications();
			mav.addObject("notifications", homePageNotifications);
		}
		return mav;
	}
	
	
	@RequestMapping(value = "/home/controlPanel/settings/properties/homePageNotifications/edit/save", method = RequestMethod.POST)
	public ModelAndView editHomePageNotification(HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"homePageNotification");

		Integer notificationId = Integer.parseInt(request.getParameter("notificationId"));

		HomePageNotification homePageNotification = homePageNotificationService.get(notificationId);

		homePageNotification.setMessage(request.getParameter("message"));
		
		
		String validFrom = request.getParameter("validFrom");
		Date dateFrom = DataTypeUtil.toDateFromStringddMMMMMyyyy(validFrom);
		homePageNotification.setValidFrom(dateFrom);
		
		String validTill = request.getParameter("validTill");
		Date dateTill = DataTypeUtil.toDateFromStringddMMMMMyyyy(validTill);
		homePageNotification.setValidTill(dateTill);
		
		if (homePageNotificationService.update(homePageNotification)) {
			mav.addObject("saveStatus", true);
		} else {
			mav.addObject("saveStatus", false);
		}

		List<HomePageNotification> homePageNotifications = homePageNotificationService.listAllNotifications();
		
		
		
		mav.addObject("notifications", homePageNotifications);

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/settings/properties/homePageNotifications/add", method = RequestMethod.GET)
	public ModelAndView addHomePageNotifications(HttpServletRequest request,
			HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"editHomePageNotification");
		mav = AuthorizationUtil.authorizeAdmin(appContext, "editHomePageNotification");
		mav.addObject("mode", "add");
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/settings/properties/homePageNotifications/save", method = RequestMethod.POST)
	public ModelAndView showAddHomePageNotification(HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"homePageNotification");

		HomePageNotification homePageNotification = new HomePageNotification();

		String message = request.getParameter("message");
		homePageNotification.setMessage(message);
		
		String validFrom = request.getParameter("validFrom");
		Date dateFrom = DataTypeUtil.toDateFromStringddMMMMMyyyy(validFrom);
		homePageNotification.setValidFrom(dateFrom);
		
		String validTill = request.getParameter("validTill");
		Date dateTill = DataTypeUtil.toDateFromStringddMMMMMyyyy(validTill);
		homePageNotification.setValidTill(dateTill);

		homePageNotificationService.insert(homePageNotification);

		List<HomePageNotification> homePageNotifications = homePageNotificationService.listAllNotifications();
		mav.addObject("notifications", homePageNotifications);

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/settings/properties/homePageNotifications/delete/{notificationId}")
	public ModelAndView deleteHomePageNotfication(HttpServletRequest request,
			@PathVariable(value = "notificationId") Integer notificationId) {
		appContext = (AppContext) request.getSession().getAttribute(
				"appContext");

		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
				"redirect:/home/controlPanel/settings/properties/homePageNotifications");

		if (homePageNotificationService.remove(notificationId)) {
			mav.addObject("saveStatus", true);
		} else {
			mav.addObject("saveStatus", false);
		}
		return mav;
	}
	
	@RequestMapping(value = "/home/controlPanel/settings/properties/{success}")
	public ModelAndView viewAllPropertyGroups(HttpServletRequest request, HttpServletResponse response, @PathVariable(value="success") Integer success)
	{
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		
		ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "managePropertyGroup");
		List<BenefitsProperty> flexiGroupList = settingsService.listAllGroups(BenefitsConstants.BEN_PROP_CONSTANT_FLEXI_PLAN);
		List<BenefitsProperty> insGroupList = settingsService.listAllGroups(BenefitsConstants.BEN_PROP_CONSTANT_GRP_INS_PLAN);
		List<BenefitsProperty> itGroupList = settingsService.listAllGroups(BenefitsConstants.BEN_PROP_CONSTANT_IT_DECLARATION);
		List<BenefitsProperty> ltaGroupList = settingsService.listAllGroups(BenefitsConstants.BEN_PROP_CONSTANT_LTA);
		List<BenefitsProperty> pfGroupList = settingsService.listAllGroups(BenefitsConstants.BEN_PROP_CONSTANT_PF);
		List<BenefitsProperty> genGroupList = settingsService.listAllGroups(BenefitsConstants.BEN_PROP_CONSTANT_GENERAL);
		
		mav.addObject("flexiList", flexiGroupList);
		mav.addObject("insList", insGroupList);
		mav.addObject("itList", itGroupList);
		mav.addObject("ltaList", ltaGroupList);
		mav.addObject("pfList", pfGroupList);
		mav.addObject("genList", genGroupList);
		
		if (success.equals(-1)) {
			mav.addObject("errorMessage",
					"Error while saving properties, please contact System Administrator");
		} else if (success.equals(1)) {
			mav.addObject("successMessage",
					"Successfully saved the properties..");
		}
		
		return mav;
	}
	
}
