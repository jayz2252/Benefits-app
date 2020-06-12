package com.speridian.benefits2.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.speridian.benefits2.beans.LoginBean;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.UserRole;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.model.util.BenefitsUtil;
import com.speridian.benefits2.service.EmployeeService;
import com.speridian.benefits2.service.SecurityService;
import com.speridian.benefits2.service.SettingsService;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.util.DataTypeUtil;
import com.speridian.benefits2.ws.client.mirror.types.AuthenticateX0020TheX0020UserX0020WithX0020AD;
import com.speridian.benefits2.ws.client.mirror.types.AuthenticateX0020TheX0020UserX0020WithX0020ADResponse;
import com.speridian.benefits2.ws.client.mirror.types.ToX0020InactivateX0020LoginX0020Key;
import com.speridian.benefits2.ws.client.mirror.types.ToX0020InactivateX0020LoginX0020KeyResponse;


/**
 * 
 * <pre>
 * Controller responsible for authentication process login & logout
 * </pre>
 * 
 * @author jithin.kuriakose, swathy.raghu
 * @since 05-Feb-2017
 * 
 */
@Controller
@SessionAttributes({ "appContext", "redirectUrl" })
public class LoginController {

	@Autowired
	SecurityService securityService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	SettingsService setttingsService;

	/**
	 * 
	 * Shows the login page
	 * 
	 * @param
	 * @param
	 * @retuns ModelAndView
	 * 
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("login");
		String redirectUrl = request.getParameter("redirectUrl");
		if (redirectUrl != null) {
			mav.addObject("redirectUrl", redirectUrl);
		}
		LoginBean bean = new LoginBean();
		mav.addObject("loginBean", bean);
		return mav;
	}

	/**
	 * 
	 * 
	 * Performs the login
	 * 
	 * @param
	 * @param
	 * @retuns ModelAndView
	 * 
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView doLogin(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("loginBean") LoginBean bean) {
		ModelAndView mav = new ModelAndView();
		Boolean isValidUser = false;
		Date auditDate = new Date();

		String redirectUrl = (String) request.getSession().getAttribute("redirectUrl");
		try {
			if (bean.getUserName() != null && !("".equals(bean.getUserName())) && bean.getPassword() != null
					&& !("".equals(bean.getPassword()))) {
				AuthenticateX0020TheX0020UserX0020WithX0020AD authenticateRequest = new AuthenticateX0020TheX0020UserX0020WithX0020AD();
				authenticateRequest.setUserName(bean.getUserName());
				authenticateRequest.setPassword(bean.getPassword());
				authenticateRequest.setClientIPAddress(request.getRemoteAddr());

				AuthenticateX0020TheX0020UserX0020WithX0020ADResponse authenticateResponse = securityService
						.authenticateUser(authenticateRequest);

				if (authenticateResponse.getAuthenticateX0020TheX0020UserX0020WithX0020ADResult() != null) {

					Integer empId = null;
					try {
						empId = authenticateResponse.getAuthenticateX0020TheX0020UserX0020WithX0020ADResult()
								.getEmployeeID();

					} catch (Exception e) {
						e.printStackTrace();
					}
					Employee currentEmployee = employeeService.get(empId);

					if (currentEmployee != null) {
						if (currentEmployee.getActive() && !currentEmployee.getIsRelieved()
								&& !currentEmployee.getEmployeeCode().contains("SPCW")) {
							/*
							 * TODO take applicable offices from properties
							 */

							if ("Pankanis - Mumbai".equals(currentEmployee.getParentOffice())
									|| "Speridian - Mumbai".equals(currentEmployee.getParentOffice())
									|| "Speridian-Kochi".equals(currentEmployee.getParentOffice())
									|| "Speridian - Trivandrum".equals(currentEmployee.getParentOffice())
									|| "Speridian - Bangalore".equals(currentEmployee.getParentOffice())
									|| "Sesame - Calicut".equals(currentEmployee.getParentOffice())
									|| "Delhi NCR - Speridian".equals(currentEmployee.getParentOffice())) {
								AppContext appContext = new AppContext();

								appContext.setCurrentEmployee(currentEmployee);

								appContext.setEmpId(authenticateResponse
										.getAuthenticateX0020TheX0020UserX0020WithX0020ADResult().getEmployeeID());
								appContext.setUserLoginKey(authenticateResponse
										.getAuthenticateX0020TheX0020UserX0020WithX0020ADResult().getSessionKey());
								appContext.setLoginTime(new Date());
								appContext.setUserName(bean.getUserName());

								/*
								 * users table entry
								 */

								securityService.addUser(bean, appContext);
								securityService.addUserLog(bean, appContext);

								/*
								 * validating roles in app context
								 */
								UserRole userRole = securityService.getUserRole(bean.getUserName());
								if (userRole != null) {
									appContext.setRole(userRole.getRole());
									if (userRole.getRole().equals(BenefitsConstants.USER_ROLE_SYSTEM_ADMIN)) {
										appContext.setAdmin(true);
										appContext.setRole(BenefitsConstants.USER_ROLE_SYSTEM_ADMIN);
									}

									else if (userRole.getRole().equals(BenefitsConstants.FIN_ROLE_EMPLOYEE)) {
										appContext.setAdmin(true);
										appContext.setRole(BenefitsConstants.FIN_ROLE_EMPLOYEE);
									} else if (userRole.getRole().equals((BenefitsConstants.USER_ROLE_INS_ADMIN))) {
										appContext.setAdmin(true);
										appContext.setRole(BenefitsConstants.USER_ROLE_INS_ADMIN);
									} else if (userRole.getRole().equals((BenefitsConstants.HR_ROLE_EMPLOYEE))) {
										appContext.setAdmin(true);
										appContext.setRole(BenefitsConstants.HR_ROLE_EMPLOYEE);
									} else if (userRole.getRole().equals((BenefitsConstants.GENERAL_MANAGEMENT_USER))) {
										appContext.setAdmin(true);
										appContext.setRole(BenefitsConstants.GENERAL_MANAGEMENT_USER);
									} else {
										appContext.setAdmin(false);
										appContext.setRole(BenefitsConstants.USER_ROLE_EMPLOYEE);
									}
								} else {
									appContext.setAdmin(false);
									appContext.setRole(BenefitsConstants.USER_ROLE_EMPLOYEE);
								}

								/**
								 * setting current fiscal year from BENEFITS_PORTAL_PROPERTIES
								 */
								String currentFiscalYear = securityService
										.getPortalProperty(BenefitsConstants.PROP_CODE_CURR_FISCAL_YEAR)
										.getPropertyValue();
								String currentLtaBlock = securityService
										.getPortalProperty(BenefitsConstants.PROP_LTA_CURRENT_BLOCK).getPropertyValue();

								String currentInsuranceFiscaYear = securityService
										.getPortalProperty(BenefitsConstants.PROP_CODE_INS_CURR_FISCAL_YEAR)
										.getPropertyValue();

								appContext.setCurrentFiscalYear(currentFiscalYear);
								appContext.setCurrentInsuranceFiscalYear(currentInsuranceFiscaYear);

								appContext.setCurrentLtaBlock(currentLtaBlock);

								Boolean flexiPlanNoCutOffDateForEnroll = Boolean.parseBoolean(securityService
										.getPortalProperty(BenefitsConstants.PROP_CODE_FLEXI_PLAN_NO_CUT_OFF_DATE)
										.getPropertyValue());

								if (flexiPlanNoCutOffDateForEnroll) {
									appContext.setOptingPeriodOver(Boolean.FALSE);
								} else {
									/**
									 * getting the current opt period
									 */

									String yearlyOptingCutOffDay = securityService
											.getPortalProperty(BenefitsConstants.PROP_CODE_YEARLY_OPT_CUT_OFF_DATE)
											.getPropertyValue();
									Date yearlyOptingCutOffDate = DataTypeUtil.toDateFromStringyyyyMMdd(
											BenefitsUtil.getStartYear(currentFiscalYear) + "-" + yearlyOptingCutOffDay);

									if (auditDate.compareTo(yearlyOptingCutOffDate) > 0) {
										/*
										 * this means current date is after the cut of date, so checking new joinee
										 * condition
										 */
										Integer newJoineeCutOffDays = Integer.parseInt(securityService
												.getPortalProperty(BenefitsConstants.PROP_CODE_NEW_JOINEE_CUT_OFF_DAYS)
												.getPropertyValue());

										Date joiningDate = currentEmployee.getDateOfJoin();
										Calendar joiningDateCalendar = Calendar.getInstance();
										joiningDateCalendar.setTime(joiningDate);

										/*
										 * adding cut off days to the joining date
										 */
										joiningDateCalendar.add(Calendar.DAY_OF_YEAR, newJoineeCutOffDays);

										Date d = joiningDateCalendar.getTime();

										if (auditDate.compareTo(joiningDateCalendar.getTime()) > 0) {
											/*
											 * this means current date is after joningDate + cut off days, so opting
											 * period is over
											 */
											appContext.setOptingPeriodOver(Boolean.TRUE);
										} else {
											/*
											 * this means current date is before or equals joningDate + cut off days, so
											 * opting period is not over
											 */
											appContext.setOptingPeriodOver(Boolean.FALSE);
										}

									} else {
										/*
										 * this means current date is before or equals yearly cut off date, so opting
										 * period is not over
										 */
										appContext.setOptingPeriodOver(Boolean.FALSE);
									}
								}

								/**
								 * fetching all financial year LOVs while logging in and setting to appContext
								 * for performance enhancement so a re login is required if this property change
								 * happens I know this is quite weired approach, but it is a common pattern
								 * followed if user changes any system property, he/she will have a feel that it
								 * has some impact on the system, so I need to re login :) so all pages with FY
								 * lov has to use ${appContext.fiscalYears} for option tag
								 */
								String[] fySplit = securityService
										.getPortalProperty(BenefitsConstants.PROP_FY_YEARS_LOV).getPropertyValue()
										.split(",");
								if (fySplit != null && fySplit.length > 0) {
									List<String> fys = new ArrayList<String>();
									for (int i = 0; i < fySplit.length; i++) {
										fys.add(fySplit[i]);
									}
									appContext.setFiscalYears(fys);
								}
								mav.addObject("appContext", appContext);
								isValidUser = true;
							} else {
								mav.addObject("error", "Benefits Portal is available only for Indian Employees");
							}
						} else {
							mav.addObject("error",
									"You are no more an active employee or not authorized to use Benefits Portal. Please contact HR Team for any further concerns");
						}
					} else {
						mav.addObject("error",
								"Your employee record(s) are not yet available with Benefits Portal, please try after 24 hours or contact HR Team");
					}
				} else {
					mav.addObject("error", "Invalid Username or Password");
				}
			} else {
				mav.addObject("error", "Username & Password is mandatory");
			}
		} catch (Exception e) {
			isValidUser = false;
			mav.addObject("error", "Oops, something went wrong, please contact System Administrator...");
			e.printStackTrace();
		}

		if (!isValidUser) {
			mav.addObject("loginBean", bean);
			mav.setViewName("login");
		} else {
			if (redirectUrl != null && !("".equals(redirectUrl))) {
				mav.setViewName("redirect:" + redirectUrl);
			} else {
				mav.setViewName("redirect:/home");
			}
		}
		return mav;
	}

	/**
	 * Log out function, shows login page
	 * 
	 * @param request
	 * @param response
	 * @return mav
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String doLogout(HttpSession session, Model model) {
		AppContext appContext = (AppContext) session.getAttribute("appContext");

		if (appContext != null) {
			if (appContext.getUserLoginKey() != null) {
				try {

					// updating entry in userslog table
					securityService.updateUsersLog(appContext.getUserLoginKey());

					ToX0020InactivateX0020LoginX0020Key logoutRequest = new ToX0020InactivateX0020LoginX0020Key();
					logoutRequest.setLoginKey(appContext.getUserLoginKey());

					ToX0020InactivateX0020LoginX0020KeyResponse logoutResponse = securityService
							.inActiveLoginKey(logoutRequest);
				} catch (Exception e) {
					System.err.println("Unable in validate login key " + appContext.getUserName());
					e.printStackTrace();
				}
			}
			System.err.println("Removing appContext from current session");
			session.removeAttribute("appContext");
			session.removeAttribute("redirectUrl");
		}
		System.err.println("Invalidating current session");
		session.invalidate();
		if (model.containsAttribute("appContext")) {
			System.err.println("Removing appContext from future session (OR from Spring model)");
			model.asMap().remove("appContext");
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/comeback", method = RequestMethod.GET)
	public String comeback(HttpServletRequest request, HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");

		if (appContext != null) {
			if (appContext.getUserName() != null && appContext.getUserLoginKey() != null) {
				return "redirect:/home";
			} else {
				return "redirect:/logout";
			}
		} else {
			return "redirect:/logout";
		}
	}

}
