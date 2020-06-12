package com.speridian.benefits2.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.speridian.benefits2.beans.LTAEmployeeVO;
import com.speridian.benefits2.beans.LTAHistoryBean;
import com.speridian.benefits2.beans.LTASearchBean;
import com.speridian.benefits2.email.EmailFormatter;
import com.speridian.benefits2.email.EmailProperties;
import com.speridian.benefits2.email.EmailService;
import com.speridian.benefits2.model.pojo.BenefitsProperty;
import com.speridian.benefits2.model.pojo.Dependent;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.LTAEmployee;
import com.speridian.benefits2.model.pojo.LTAEmployeeDependent;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.service.LTAService;
import com.speridian.benefits2.service.SettingsService;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.util.AuthorizationUtil;
import com.speridian.benefits2.util.DataTypeUtil;
import com.speridian.benefits2.ws.client.mirror.soap.MirrorDataService;
import com.speridian.benefits2.ws.client.mirror.soap.ObjectFactory;
import com.speridian.benefits2.ws.client.mirror.types.GetX0020TheX0020LeaveX0020Details;
import com.speridian.benefits2.ws.client.mirror.types.GetX0020TheX0020LeaveX0020DetailsResponse;
import com.speridian.benefits2.ws.client.mirror.types.LeaveInfo;
import com.speridian.benefits2.ws.client.mirror.types.ServiceAuthenticationHeader;

@Controller
public class LTAController {

	@Autowired
	SettingsService settingsService;

	@Autowired
	LTAService lTAService;

	@Autowired
	MirrorDataService mirrorDataService;

	@RequestMapping(value = "/home/lta/new")
	public ModelAndView viewRequestForm(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		mav = AuthorizationUtil.authorizeUser(appContext, "ltaRequestForm");
		LTAEmployee ltaEmployee;
		String fiscalYear = appContext.getCurrentFiscalYear();
		String ltaYear = appContext.getCurrentLtaBlock();
		ltaEmployee = lTAService.getSaved(appContext.getCurrentEmployee().getEmployeeId(), ltaYear);

		/*
		 * //LTAEmployee ltaEmployee =
		 * lTAService.getById(Integer.parseInt(request
		 * .getClass()..getParameter("ltaEmployeeId")));
		 */
		LTAEmployee ltaEmployeevo = lTAService.getByEId(appContext.getCurrentEmployee().getEmployeeId());
		if (!(ltaEmployeevo == null)) {
			ltaEmployee.setActualFare(ltaEmployeevo.getActualFare());
			ltaEmployee.setDestination1(ltaEmployeevo.getDestination1());
			ltaEmployee.setOrigin(ltaEmployeevo.getOrigin());
			ltaEmployee.setPeriodTill(ltaEmployeevo.getPeriodTill());
			ltaEmployee.setPeriodFrom(ltaEmployeevo.getPeriodFrom());
			ltaEmployee.setRouteDescription(ltaEmployeevo.getRouteDescription());
			ltaEmployee.setShortestFare(ltaEmployeevo.getShortestFare());
			ltaEmployee.setDependents(ltaEmployeevo.getDependents());
			ltaEmployee.setLtaEmployeeId(ltaEmployeevo.getLtaEmployeeId());
			mav.addObject("found", true);
		}
		/* LTAEmployeeDependent dependent = new LTAEmployeeDependent(); */
		List<LTAEmployeeDependent> dependent;
		if (ltaEmployee == null) {
			ltaEmployee = new LTAEmployee();
			// dependent =(List<LTAEmployeeDependent>) new
			// LTAEmployeeDependent();
			dependent = new ArrayList<LTAEmployeeDependent>();
		} else {
			dependent = ltaEmployee.getDependents();

			for (LTAEmployeeDependent dependent2 : dependent) {
				System.out.println("**********" + dependent2.getModeOfTransport() + "***************");
			}
		}

		mav.addObject("ltaEmployee", ltaEmployee);
		mav.addObject("ltaEmployeevo", ltaEmployeevo);

		/*
		 * String fiscalYear = appContext.getCurrentFiscalYear(); String ltaYear
		 * = appContext.getCurrentLtaBlock();
		 */
		int maxLtaPerBlock = Integer
				.parseInt(settingsService.getPropertyByCode(BenefitsConstants.PROP_MAX_LTA_AVAIL).getPropertyValue());

		List<LTAEmployee> existingLtaRequests = lTAService.get(appContext.getCurrentEmployee().getEmployeeId(),
				ltaYear);

		if ((existingLtaRequests == null)
				|| (existingLtaRequests != null && existingLtaRequests.size() < maxLtaPerBlock)) {

			/**
			 * Populating all available leave requests for the current block
			 */

			/**
			 * getting LTA request date periods in order to validate with Mirror
			 * converting to XmlGregorianCalendar for WS request
			 */
			String periodFrom = "01-01-" + ltaYear.split("-")[0];
			String periodTo = "31-12-20" + ltaYear.split("-")[1];

			GregorianCalendar fromCalendar = new GregorianCalendar();
			fromCalendar.setTime(DataTypeUtil.toDateFromStringddMMyyyy(periodFrom));

			GregorianCalendar toCalendar = new GregorianCalendar();
			toCalendar.setTime(DataTypeUtil.toDateFromStringddMMyyyy(periodTo));

			XMLGregorianCalendar fromDateXml = null;
			XMLGregorianCalendar toDateXml = null;

			try {
				fromDateXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(fromCalendar);
				toDateXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(toCalendar);

				/**
				 * WS request body/paramaters
				 */
				GetX0020TheX0020LeaveX0020Details leaveRequest = new GetX0020TheX0020LeaveX0020Details();
				leaveRequest.setEmployeeId(appContext.getCurrentEmployee().getEmployeeId());
				leaveRequest.setFromDate(fromDateXml);
				leaveRequest.setToDate(toDateXml);

				/**
				 * WS request header
				 */
				ServiceAuthenticationHeader authenticationHeader = new ObjectFactory()
						.createServiceAuthenticationHeader();

				/**
				 * calling WS
				 */
				GetX0020TheX0020LeaveX0020DetailsResponse leaveResponse = mirrorDataService.getMirrorDataServiceSoap()
						.getLeaveDetails(leaveRequest, authenticationHeader);

				if (leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult() != null
						&& leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo() != null
						&& leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo().size() > 0) {
					mav.addObject("availableLeaves",
							leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			/**
			* 
			*/

			List<Dependent> dependents = appContext.getCurrentEmployee().getDependents();
			mav.addObject("dependents", dependents);
			/* mav.addObject("dependent", dependent); */
			if (dependent == null || dependent.isEmpty()) {

				mav.addObject("rowcount", 1);
			} else {
				mav.addObject("dependent", dependent);
				mav.addObject("rowcount", dependent.size());
			}
			mav.addObject("ltaInEligible", false);
			mav.addObject("ltaBalance",
					maxLtaPerBlock - (existingLtaRequests == null ? 0 : existingLtaRequests.size()));
		} else {
			mav.addObject("ltaBalance", 0);
			mav.addObject("ltaInEligible", true);
			mav.addObject("error",
					"You cannot submit more than " + maxLtaPerBlock + " LTA Request(s) in a Block Year...!!!");
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			mav.addObject("jsPeriodFrom", objectMapper.writeValueAsString(ltaEmployee.getPeriodFrom()));
			mav.addObject("jsPeriodTill", objectMapper.writeValueAsString(ltaEmployee.getPeriodTill()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/home/lta/new/viewHistory")
	public ModelAndView viewHistory(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		mav = AuthorizationUtil.authorizeUser(appContext, "viewLTAHistory");

		List<LTAHistoryBean> beans = new ArrayList<LTAHistoryBean>();

		Employee employee = appContext.getCurrentEmployee();

		List<LTAEmployee> list = lTAService.gett(employee.getEmployeeId(), appContext.getCurrentLtaBlock());
		for (LTAEmployee ltaEmployee : list) {
			LTAHistoryBean bean = new LTAHistoryBean();
			List<LTAEmployeeDependent> dependents = lTAService.get(ltaEmployee.getLtaEmployeeId());
			bean.setLTAEmployee(ltaEmployee);
			bean.setDependents(dependents);
			beans.add(bean);

		}
		mav.addObject("ltaRequests", beans);
		return mav;
	}

	@RequestMapping(value = "/home/ltaModule/new/save", method = RequestMethod.POST, params = { "savewithoutsubmit",
			"!submits" })
	public ModelAndView saveUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("ltaEmployee") LTAEmployee ltaEmployee) {
		try {
			AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
			ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "ltaRequestForm");
			/*
			 * the origin reach the controller as follows if we entered in UI as
			 * abcd this reaches the controller as abcd,abcd the following code
			 * is added to avoid this
			 */
			String orgin = ltaEmployee.getOrigin();

			int position;
			int length;
			if (orgin.length() % 2 == 0) {
				position = orgin.length() / 2 - 1;
				length = 2;
			} else {
				position = orgin.length() / 2;
				length = 1;
			}
			String middleElement = orgin.substring(position, position + length);
			if (middleElement.equals(",")) {

				orgin = orgin.substring(0, position);
			}
			ltaEmployee.setOrigin(orgin);
			/*
			 * It ends here
			 */

			Date auditDate = new Date();

			Employee employee = appContext.getCurrentEmployee();

			/**
			 * getting LTA request date periods in order to validate with Mirror
			 * converting to XmlGregorianCalendar for WS request
			 */
			Date periodFrom = ltaEmployee.getPeriodFrom();
			Date periodTo = ltaEmployee.getPeriodTill();

			GregorianCalendar fromCalendar = new GregorianCalendar();
			fromCalendar.setTime(periodFrom);

			GregorianCalendar toCalendar = new GregorianCalendar();
			toCalendar.setTime(periodTo);

			XMLGregorianCalendar fromDateXml = null;
			XMLGregorianCalendar toDateXml = null;

			try {
				fromDateXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(fromCalendar);
				toDateXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(toCalendar);

				/**
				 * WS request body/paramaters
				 */
				GetX0020TheX0020LeaveX0020Details leaveRequest = new GetX0020TheX0020LeaveX0020Details();
				leaveRequest.setEmployeeId(employee.getEmployeeId());
				leaveRequest.setFromDate(fromDateXml);
				leaveRequest.setToDate(toDateXml);

				/**
				 * WS request header
				 */
				ServiceAuthenticationHeader authenticationHeader = new ObjectFactory()
						.createServiceAuthenticationHeader();

				/**
				 * calling WS
				 */
				GetX0020TheX0020LeaveX0020DetailsResponse leaveResponse = mirrorDataService.getMirrorDataServiceSoap()
						.getLeaveDetails( leaveRequest, authenticationHeader);

				if (leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult() != null
						&& leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo() != null
						&& leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo().size() > 0) {
					List<LeaveInfo> leaves = leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo();

					GregorianCalendar mirrorStartDate = null;
					GregorianCalendar mirrorEndDate = null;

					int ltaLeaveDays = Integer.parseInt(settingsService
							.getPropertyByCode(BenefitsConstants.PROP_LTA_LEAVE_DAYS).getPropertyValue());

					int noOfDays = 0;
					for (LeaveInfo leave : leaves) {

						if (leave.getApprovedBy() != 0) {

							Calendar fromDate = leave.getFromDate().toGregorianCalendar();
							Calendar toDate = leave.getToDate().toGregorianCalendar();

							int leaveDiff = (toDate.get(Calendar.DAY_OF_YEAR) - fromDate.get(Calendar.DAY_OF_YEAR)) + 1;

							noOfDays += leaveDiff;

							// checking the from date and todate is monday or
							// Friday

							if (fromDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
									|| toDate.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
								noOfDays += 2;
							}
						}
					}

					ltaEmployee.setBlock(appContext.getCurrentLtaBlock());
					ltaEmployee.setFiscalYear(appContext.getCurrentFiscalYear());
					ltaEmployee.setEmployee(employee);
					ltaEmployee.setApprovedAmt(new BigDecimal(0));
					ltaEmployee.setStatus(BenefitsConstants.LTA_REQUEST_STATUS_SAVED);

					Integer totalDeps = Integer.parseInt(request.getParameter("rowCount"));

					List<LTAEmployeeDependent> dependents = new ArrayList<LTAEmployeeDependent>();

					/*
					 * this is for first row of dependent table
					 */
					String ltaYear = appContext.getCurrentLtaBlock();
					LTAEmployee ltaEmployee1 = lTAService.getSaved(appContext.getCurrentEmployee().getEmployeeId(),
							ltaYear);
					LTAEmployeeDependent firstLtaDependent;
					if (ltaEmployee1 == null) {
						firstLtaDependent = new LTAEmployeeDependent();
					} else {
						Boolean success = lTAService.deleteAllDependents(ltaEmployee1.getEmployee().getEmployeeId());
						// firstLtaDependent =
						// lTAService.getLTADependence(Integer.parseInt(request.getParameter("lTADep")),0,ltaEmployee1);
						firstLtaDependent = new LTAEmployeeDependent();
					}

					Dependent firstDep = lTAService.getdepenedent(Integer.parseInt(request.getParameter("lTADep")));
					firstLtaDependent.setDependent(firstDep);

					firstLtaDependent.setTravellingClass(request.getParameter("travellingClass"));
					firstLtaDependent.setFare(new BigDecimal(request.getParameter("costOfFare")));
					firstLtaDependent.setDestination(request.getParameter("destination"));
					firstLtaDependent.setOrigin(request.getParameter("origin"));

					ltaEmployee.setCreatedDate(new Date());
					String mode = request.getParameter("modeOfTransport");
					if (mode.equals("Others")) {
						mode = request.getParameter("otherTransportMode");
					}

					firstLtaDependent.setModeOfTransport(mode);

					firstLtaDependent.setLtaEmployee(ltaEmployee);

					firstLtaDependent.setCreatedBy(appContext.getUserName());
					firstLtaDependent.setUpdatedBy(appContext.getUserName());
					firstLtaDependent.setCreatedDate(auditDate);
					firstLtaDependent.setUpdatedDate(auditDate);

					dependents.add(firstLtaDependent);

					if (totalDeps > 1) {
						for (int i = 2; i <= totalDeps; i++) {

							LTAEmployeeDependent ltaDependent = new LTAEmployeeDependent();
							;

							Integer depId = Integer.parseInt(request.getParameter("lTADep" + i));
							/*
							 * ltaDependent = lTAService.getLTADependence(depId,
							 * i-1,ltaEmployee1); if (ltaDependent == null) {
							 * ltaDependent = new LTAEmployeeDependent(); }
							 */
							Dependent dep = lTAService.getdepenedent(depId);
							ltaDependent.setDependent(dep);

							ltaDependent.setTravellingClass(request.getParameter("travellingClass" + i));
							ltaDependent.setFare(new BigDecimal(request.getParameter("costOfFare" + i)));
							ltaDependent.setDestination(request.getParameter("destination" + i));

							ltaDependent.setOrigin(request.getParameter("origin" + i));
							String mode1 = request.getParameter("modeOfTransport" + i);
							if (mode1.equals("Others")) {
								mode1 = request.getParameter("otherTransportMode" + i);
							}

							ltaDependent.setModeOfTransport(mode1);

							ltaDependent.setLtaEmployee(ltaEmployee);

							ltaDependent.setCreatedBy(appContext.getUserName());
							ltaDependent.setUpdatedBy(appContext.getUserName());

							ltaDependent.setCreatedBy(appContext.getUserName());
							ltaDependent.setUpdatedBy(appContext.getUserName());
							ltaDependent.setCreatedDate(auditDate);
							ltaDependent.setUpdatedDate(auditDate);
							dependents.add(ltaDependent);
						}
					}

					ltaEmployee.setDependents(dependents);

					ltaEmployee.setCreatedBy(appContext.getUserName());
					ltaEmployee.setUpdatedBy(appContext.getUserName());
					ltaEmployee.setCreatedDate(auditDate);
					ltaEmployee.setUpdatedDate(auditDate);
					List<LTAEmployeeDependent> dependentss = null;
					if (ltaEmployee1 == null) {
						if (lTAService.insert(ltaEmployee)) {
							// dependentss=ltaEmployee.getDependents();
							dependentss = lTAService.get(ltaEmployee.getLtaEmployeeId());

							System.out.println("----------------saved---------------");
							mav.addObject("ltaEmployee", ltaEmployee);

						} else {
							mav.addObject("ltaEmployee", ltaEmployee);
							mav.addObject("error",
									"Failed to create new LTA Request, please contact System Administrator/HR");
						}
					} else {
						int savedLtaId = ltaEmployee1.getLtaEmployeeId();
						ltaEmployee1 = ltaEmployee;
						ltaEmployee1.setLtaEmployeeId(savedLtaId);
						if (lTAService.update(ltaEmployee1)) {
							dependentss = lTAService.get(ltaEmployee1.getLtaEmployeeId());
							System.out.println("----------------Updated---------------");
							mav.addObject("ltaEmployee", ltaEmployee1);
						} else {
							mav.addObject("ltaEmployee", ltaEmployee1);
							mav.addObject("error",
									"Failed to create new LTA Request, please contact System Administrator/HR");
						}
					}

					mav.addObject("dependent", dependentss);

					mav.addObject("rowcount", dependentss.size());

					mav.addObject("ltaInEligible", false);

				} else {
					mav.addObject("error",
							"There is no leave requests for this date range. Please provide the same date range as you applied in the mirror.");
					String ltaYear = appContext.getCurrentLtaBlock();
					LTAEmployee ltaEmployee1 = lTAService.getSaved(appContext.getCurrentEmployee().getEmployeeId(),
							ltaYear);
					List<LTAEmployeeDependent> dependentss = null;
					if(ltaEmployee1 != null){
					dependentss=lTAService.get(ltaEmployee1.getLtaEmployeeId());
					mav.addObject("dependent", dependentss);
					}
					if (dependentss != null) {
						mav.addObject("rowcount", dependentss.size());

					}else{
						mav.addObject("rowcount", 1);

					}
					
				}
			} catch (DatatypeConfigurationException e) {
				mav.addObject("error", "System Error Occured with date range, please contact System Administrator/HR");
			} catch (Exception e) {
				mav.addObject("error", "System Error Occured, please contact System Administrator/HR");
				e.printStackTrace();
			}
			List<Dependent> deps = appContext.getCurrentEmployee().getDependents();
			mav.addObject("dependents", deps);
			mav.addObject("saved", true);

			/* add the leaves available to the left side */
			String fiscalYear = appContext.getCurrentFiscalYear();
			String ltaYear = appContext.getCurrentLtaBlock();
			int maxLtaPerBlock = Integer.parseInt(
					settingsService.getPropertyByCode(BenefitsConstants.PROP_MAX_LTA_AVAIL).getPropertyValue());

			List<LTAEmployee> existingLtaRequests = lTAService.get(appContext.getCurrentEmployee().getEmployeeId(),
					ltaYear);

			if ((existingLtaRequests == null)
					|| (existingLtaRequests != null && existingLtaRequests.size() < maxLtaPerBlock)) {

				String periodFroms = "01-01-" + ltaYear.split("-")[0];
				String periodTos = "31-12-20" + ltaYear.split("-")[1];

				GregorianCalendar fromCalendars = new GregorianCalendar();
				fromCalendars.setTime(DataTypeUtil.toDateFromStringddMMyyyy(periodFroms));

				GregorianCalendar toCalendars = new GregorianCalendar();
				toCalendars.setTime(DataTypeUtil.toDateFromStringddMMyyyy(periodTos));

				XMLGregorianCalendar fromDateXmls = null;
				XMLGregorianCalendar toDateXmls = null;

				try {
					fromDateXmls = DatatypeFactory.newInstance().newXMLGregorianCalendar(fromCalendars);
					toDateXmls = DatatypeFactory.newInstance().newXMLGregorianCalendar(toCalendars);
					System.out.println("*******************"+toDateXmls+"****************************");
					/**
					 * WS request body/paramaters
					 */
					GetX0020TheX0020LeaveX0020Details leaveRequests = new GetX0020TheX0020LeaveX0020Details();
					leaveRequests.setEmployeeId(appContext.getCurrentEmployee().getEmployeeId());
					leaveRequests.setFromDate(fromDateXmls);
					leaveRequests.setToDate(toDateXmls);

					/**
					 * WS request header
					 */
					ServiceAuthenticationHeader authenticationHeaders = new ObjectFactory()
							.createServiceAuthenticationHeader();

					/**
					 * calling WS
					 */
					GetX0020TheX0020LeaveX0020DetailsResponse leaveResponses = mirrorDataService
							.getMirrorDataServiceSoap().getLeaveDetails( leaveRequests, authenticationHeaders);

					if (leaveResponses.getGetX0020TheX0020LeaveX0020DetailsResult() != null
							&& leaveResponses.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo() != null
							&& leaveResponses.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo().size() > 0) {
						mav.addObject("availableLeaves",
								leaveResponses.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			mav.addObject("ltaBalance",
					maxLtaPerBlock - (existingLtaRequests == null ? 0 : existingLtaRequests.size()));
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				mav.addObject("jsPeriodFrom", objectMapper.writeValueAsString(ltaEmployee.getPeriodFrom()));
				mav.addObject("jsPeriodTill", objectMapper.writeValueAsString(ltaEmployee.getPeriodTill()));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return mav;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/home/ltaModule/new/save", method = RequestMethod.POST, params = { "submits",
			"!savewithoutsubmit" })
	public ModelAndView saveLtaForm(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("ltaEmployee") LTAEmployee ltaEmployee) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "ltaRequestForm");

		// LTAEmployee ltaEmployee =
		// lTAService.getByEId(appContext.getCurrentEmployee().getEmployeeId());
		ltaEmployee = lTAService.getByEId(appContext.getCurrentEmployee().getEmployeeId());
		if (ltaEmployee == null) {
			// ltaEmployee=new LTAEmployee();
			Employee employee = appContext.getCurrentEmployee();
			LTAEmployeeVO ltaEmployeeVO = new LTAEmployeeVO();
			Date auditDate = new Date();

			SimpleDateFormat formatter = new SimpleDateFormat("d MMM,yyyy");
			String from = request.getParameter("periodFrom");
			String to = request.getParameter("periodTill");

			Date periodFrom = null;
			Date periodTo = null;
			try {
				periodFrom = formatter.parse(from);
				periodTo = formatter.parse(to);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Date periodFrom = ltaEmployee.getPeriodFrom();
			// Date periodTo = ltaEmployee.getPeriodTill();
			GregorianCalendar fromCalendar = new GregorianCalendar();
			fromCalendar.setTime(periodFrom);

			GregorianCalendar toCalendar = new GregorianCalendar();
			toCalendar.setTime(periodTo);

			XMLGregorianCalendar fromDateXml = null;
			XMLGregorianCalendar toDateXml = null;

			try {
				fromDateXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(fromCalendar);
				toDateXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(toCalendar);

				/**
				 * WS request body/paramaters
				 */
				GetX0020TheX0020LeaveX0020Details leaveRequest = new GetX0020TheX0020LeaveX0020Details();
				leaveRequest.setEmployeeId(employee.getEmployeeId());
				leaveRequest.setFromDate(fromDateXml);
				leaveRequest.setToDate(toDateXml);

				/**
				 * WS request header
				 */
				ServiceAuthenticationHeader authenticationHeader = new ObjectFactory()
						.createServiceAuthenticationHeader();

				/**
				 * calling WS
				 */
				GetX0020TheX0020LeaveX0020DetailsResponse leaveResponse = mirrorDataService.getMirrorDataServiceSoap()
						.getLeaveDetails( leaveRequest, authenticationHeader);

				if (leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult() != null
						&& leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo() != null
						&& leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo().size() > 0
						) {
					List<LeaveInfo> leaves = leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo();

					GregorianCalendar mirrorStartDate = null;
					GregorianCalendar mirrorEndDate = null;

					int ltaLeaveDays = Integer.parseInt(settingsService
							.getPropertyByCode(BenefitsConstants.PROP_LTA_LEAVE_DAYS).getPropertyValue());

					int noOfDays = 0;
					for (LeaveInfo leave : leaves) {

						if (leave.getApprovedBy() != 0) {

							Calendar fromDate = leave.getFromDate().toGregorianCalendar();
							Calendar toDate = leave.getToDate().toGregorianCalendar();

							int leaveDiff = (toDate.get(Calendar.DAY_OF_YEAR) - fromDate.get(Calendar.DAY_OF_YEAR)) + 1;

							noOfDays += leaveDiff;

							// checking the from date and todate is monday or
							// Friday

							if (fromDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
									|| toDate.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
								noOfDays += 2;
							}
						}
					}
					ltaEmployeeVO.setBlock(appContext.getCurrentLtaBlock());
					ltaEmployeeVO.setFiscalYear(appContext.getCurrentFiscalYear());
					ltaEmployeeVO.setEmployee(appContext.getCurrentEmployee());
					ltaEmployeeVO.setApprovedAmt(new BigDecimal(0));
					ltaEmployeeVO.setRouteDescription(request.getParameter("routeDescription"));
					ltaEmployeeVO.setActualFare(new BigDecimal(request.getParameter("actualFare")));
					ltaEmployeeVO.setDestination1(request.getParameter("destination"));
					ltaEmployeeVO.setOrigin(request.getParameter("origin"));
					ltaEmployeeVO.setPeriodFrom(periodFrom);
					ltaEmployeeVO.setPeriodTill(periodTo);

					ltaEmployeeVO.setShortestFare(new BigDecimal(request.getParameter("shortestFare")));
					Integer totalDeps = Integer.parseInt(request.getParameter("rowCount"));

					List<LTAEmployeeDependent> dependents = new ArrayList<LTAEmployeeDependent>();

					/*
					 * swathy.raghu this is for first row of dependent table
					 */
					LTAEmployeeDependent firstLtaDependent = new LTAEmployeeDependent();

					Dependent firstDep = lTAService.getdepenedent(Integer.parseInt(request.getParameter("lTADep")));
					firstLtaDependent.setDependent(firstDep);

					firstLtaDependent.setTravellingClass(request.getParameter("travellingClass"));
					firstLtaDependent.setFare(new BigDecimal(request.getParameter("costOfFare")));
					firstLtaDependent.setDestination(request.getParameter("destination"));
					firstLtaDependent.setOrigin(request.getParameter("origin"));

					ltaEmployeeVO.setCreatedDate(new Date());
					String mode = request.getParameter("modeOfTransport");
					if (mode.equals("Others")) {
						mode = request.getParameter("otherTransportMode");
					}

					firstLtaDependent.setModeOfTransport(mode);

					firstLtaDependent.setLtaEmployee(ltaEmployee);

					firstLtaDependent.setCreatedBy(appContext.getUserName());
					firstLtaDependent.setUpdatedBy(appContext.getUserName());
					firstLtaDependent.setCreatedDate(auditDate);
					firstLtaDependent.setUpdatedDate(auditDate);

					dependents.add(firstLtaDependent);

					if (totalDeps > 1) {
						for (int i = 2; i <= totalDeps; i++) {

							LTAEmployeeDependent ltaDependent = new LTAEmployeeDependent();
							Integer depId = Integer.parseInt(request.getParameter("lTADep" + i));
							Dependent dep = lTAService.getdepenedent(depId);
							ltaDependent.setDependent(dep);

							ltaDependent.setTravellingClass(request.getParameter("travellingClass" + i));
							ltaDependent.setFare(new BigDecimal(request.getParameter("costOfFare" + i)));
							ltaDependent.setDestination(request.getParameter("destination" + i));

							ltaDependent.setOrigin(request.getParameter("origin" + i));
							String mode1 = request.getParameter("modeOfTransport" + i);
							if (mode1.equals("Others")) {
								mode1 = request.getParameter("otherTransportMode" + i);
							}

							ltaDependent.setModeOfTransport(mode1);

							ltaDependent.setLtaEmployee(ltaEmployee);

							ltaDependent.setCreatedBy(appContext.getUserName());
							ltaDependent.setUpdatedBy(appContext.getUserName());

							ltaDependent.setCreatedBy(appContext.getUserName());
							ltaDependent.setUpdatedBy(appContext.getUserName());
							ltaDependent.setCreatedDate(auditDate);
							ltaDependent.setUpdatedDate(auditDate);
							dependents.add(ltaDependent);
						}
					}

					ltaEmployeeVO.setDependents(dependents);

					ltaEmployeeVO.setCreatedBy(appContext.getUserName());
					ltaEmployeeVO.setUpdatedBy(appContext.getUserName());
					ltaEmployeeVO.setCreatedDate(auditDate);
					ltaEmployeeVO.setUpdatedDate(auditDate);
					ltaEmployeeVO.setStatus(BenefitsConstants.LTA_REQUEST_STATUS_SUBMIT);

					LTAEmployee ltaEmployees = new LTAEmployee();
					ltaEmployees.setActualFare(ltaEmployeeVO.getActualFare());
					ltaEmployees.setApprovedAmt(ltaEmployeeVO.getApprovedAmt());
					ltaEmployees.setDestination1(ltaEmployeeVO.getDestination1());
					ltaEmployees.setBlock(ltaEmployeeVO.getBlock());
					ltaEmployees.setEmployee(ltaEmployeeVO.getEmployee());
					ltaEmployees.setCreatedBy(ltaEmployeeVO.getCreatedBy());
					// ltaEmployees.setDependents(ltaEmployeeVO.getDependents());
					for (LTAEmployeeDependent ltadependences : dependents) {
						ltadependences.setLtaEmployee(ltaEmployees);
					}

					ltaEmployees.setFiscalYear(appContext.getCurrentFiscalYear());

					ltaEmployees.setDependents(dependents);
					ltaEmployees.setOrigin(ltaEmployeeVO.getOrigin());
					ltaEmployees.setNoOfLeave(ltaEmployeeVO.getNoOfLeave());
					ltaEmployees.setPeriodFrom(ltaEmployeeVO.getPeriodFrom());
					ltaEmployees.setPeriodTill(ltaEmployeeVO.getPeriodTill());
					ltaEmployees.setShortestFare(ltaEmployeeVO.getShortestFare());
					ltaEmployees.setStatus(ltaEmployeeVO.getStatus());
					ltaEmployees.setCreatedDate(ltaEmployeeVO.getCreatedDate());
					ltaEmployees.setRouteDescription(ltaEmployeeVO.getRouteDescription());
					try {
						boolean no=lTAService.insert(ltaEmployees);
					} catch (Exception e) {
						mav.addObject("error", "System Error Occured, please contact System Administrator/HR");
						e.printStackTrace();
					}

					EmailService emailService = null;
					String messageSubject = "New LTA Request Submitted";

					String messageBody = EmailFormatter.ltaRequestSubmission(ltaEmployees,
							appContext.getCurrentEmployee());

					String toMail = EmailProperties.getProperty(BenefitsConstants.PROP_HR_EMAIL);

					emailService = new EmailService(toMail, appContext.getCurrentEmployee().getEmail(), messageBody,
							messageSubject);

					System.out.println("----------------Sending email---------------");
					Thread emailThread = new Thread(emailService);
				emailThread.start();

					mav.addObject("message", "Succesfully created new LTA Request");
					mav.setViewName("viewLTAHistory");

					List<LTAHistoryBean> beans = new ArrayList<LTAHistoryBean>();
					List<LTAEmployee> list = lTAService.get(appContext.getCurrentEmployee().getEmployeeId(),
							appContext.getCurrentLtaBlock());

					for (LTAEmployee lta : list) {
						LTAHistoryBean bean = new LTAHistoryBean();
						List<LTAEmployeeDependent> tempDependents = lTAService.get(ltaEmployee.getLtaEmployeeId());
						bean.setLTAEmployee(lta);
						bean.setDependents(tempDependents);
						beans.add(bean);
					}
					System.out.println("----------------saved---------------");
					/*
					 * } else { mav.addObject("ltaEmployee", ltaEmployee);
					 * mav.addObject("error",
					 * "Failed to create new LTA Request, please contact System Administrator/HR"
					 * ); }
					 */
				} else {

					mav.addObject("error",
							"There is no leave requests for this date range. Please provide the same date range as you applied in the mirror.");
				}
			} /*
				 * else { mav.addObject( "error",
				 * "There is no approved leave requests for this date range. Please provide the same date range as you applied in the mirror."
				 * ); swathy.raghu} }else
				 * 
				 * { mav.addObject("error",
				 * "There is no leave requests for this date range. Please provide the same date range as you applied in the mirror."
				 * ); }}
				 */catch (Exception e) {
				mav.addObject("error", "System Error Occured, please contact System Administrator/HR");
				e.printStackTrace();
			}
			List<Dependent> deps = appContext.getCurrentEmployee().getDependents();
			mav.addObject("dependents", deps);
			mav.addObject("send", false);
			mav.addObject("ltaEmployee", new LTAEmployee());

			/* add the leaves available to the left side */
			String fiscalYear = appContext.getCurrentFiscalYear();
			String ltaYear = appContext.getCurrentLtaBlock();
			int maxLtaPerBlock = Integer.parseInt(
					settingsService.getPropertyByCode(BenefitsConstants.PROP_MAX_LTA_AVAIL).getPropertyValue());

			List<LTAEmployee> existingLtaRequests = lTAService.get(appContext.getCurrentEmployee().getEmployeeId(),
					ltaYear);

			if ((existingLtaRequests == null)
					|| (existingLtaRequests != null && existingLtaRequests.size() < maxLtaPerBlock)) {

				String periodFroms = "01-01-" + ltaYear.split("-")[0];
				String periodTos = "31-12-20" + ltaYear.split("-")[1];

				GregorianCalendar fromCalendars = new GregorianCalendar();
				fromCalendars.setTime(DataTypeUtil.toDateFromStringddMMyyyy(periodFroms));

				GregorianCalendar toCalendars = new GregorianCalendar();
				toCalendars.setTime(DataTypeUtil.toDateFromStringddMMyyyy(periodTos));

				XMLGregorianCalendar fromDateXmls = null;
				XMLGregorianCalendar toDateXmls = null;

				try {
					fromDateXmls = DatatypeFactory.newInstance().newXMLGregorianCalendar(fromCalendars);
					toDateXmls = DatatypeFactory.newInstance().newXMLGregorianCalendar(toCalendars);

					/**
					 * WS request body/paramaters
					 */
					GetX0020TheX0020LeaveX0020Details leaveRequests = new GetX0020TheX0020LeaveX0020Details();
					leaveRequests.setEmployeeId(appContext.getCurrentEmployee().getEmployeeId());
					leaveRequests.setFromDate(fromDateXmls);
					leaveRequests.setToDate(toDateXml);

					/**
					 * WS request header
					 */
					ServiceAuthenticationHeader authenticationHeaders = new ObjectFactory()
							.createServiceAuthenticationHeader();

					/**
					 * calling WS
					 */
					GetX0020TheX0020LeaveX0020DetailsResponse leaveResponses = mirrorDataService
							.getMirrorDataServiceSoap().getLeaveDetails(leaveRequests, authenticationHeaders);

					if (leaveResponses.getGetX0020TheX0020LeaveX0020DetailsResult() != null
							&& leaveResponses.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo() != null
							&& leaveResponses.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo().size() > 0) {
						mav.addObject("availableLeaves",
								leaveResponses.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			mav.addObject("rowcount", 1);
			mav.addObject("ltaBalance",
					maxLtaPerBlock - (existingLtaRequests == null ? 0 : existingLtaRequests.size()));
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				mav.addObject("jsPeriodFrom", objectMapper
						.writeValueAsString(DatatypeFactory.newInstance().newXMLGregorianCalendar(fromCalendar)));
				mav.addObject("jsPeriodTill", objectMapper
						.writeValueAsString(DatatypeFactory.newInstance().newXMLGregorianCalendar(toCalendar)));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return mav;
		} else {

			Employee employee = appContext.getCurrentEmployee();
			LTAEmployeeVO ltaEmployeeVO = new LTAEmployeeVO();
			Date auditDate = new Date();
			List<LTAEmployeeDependent> dependents = null;
			SimpleDateFormat formatter = new SimpleDateFormat("d MMM,yyyy");
			String from = request.getParameter("periodFrom");
			String to = request.getParameter("periodTill");

			Date periodFrom = null;
			Date periodTo = null;
			try {
				periodFrom = formatter.parse(from);
				periodTo = formatter.parse(to);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Date periodFrom = ltaEmployee.getPeriodFrom();
			// Date periodTo = ltaEmployee.getPeriodTill();
			GregorianCalendar fromCalendar = new GregorianCalendar();
			fromCalendar.setTime(periodFrom);

			GregorianCalendar toCalendar = new GregorianCalendar();
			toCalendar.setTime(periodTo);

			XMLGregorianCalendar fromDateXml = null;
			XMLGregorianCalendar toDateXml = null;

			try {
				fromDateXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(fromCalendar);
				toDateXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(toCalendar);

				/**
				 * WS request body/paramaters
				 */
				GetX0020TheX0020LeaveX0020Details leaveRequest = new GetX0020TheX0020LeaveX0020Details();
				leaveRequest.setEmployeeId(employee.getEmployeeId());
				leaveRequest.setFromDate(fromDateXml);
				leaveRequest.setToDate(toDateXml);

				/**
				 * WS request header
				 */
				ServiceAuthenticationHeader authenticationHeader = new ObjectFactory()
						.createServiceAuthenticationHeader();

				/**
				 * calling WS
				 */
				GetX0020TheX0020LeaveX0020DetailsResponse leaveResponse = mirrorDataService.getMirrorDataServiceSoap()
						.getLeaveDetails(leaveRequest, authenticationHeader);

				if (leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult() != null
						&& leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo() != null
						&& leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo().size() > 0) {
					List<LeaveInfo> leaves = leaveResponse.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo();

					GregorianCalendar mirrorStartDate = null;
					GregorianCalendar mirrorEndDate = null;

					int ltaLeaveDays = Integer.parseInt(settingsService
							.getPropertyByCode(BenefitsConstants.PROP_LTA_LEAVE_DAYS).getPropertyValue());

					int noOfDays = 0;
					for (LeaveInfo leave : leaves) {

						if (leave.getApprovedBy() != 0) {

							Calendar fromDate = leave.getFromDate().toGregorianCalendar();
							Calendar toDate = leave.getToDate().toGregorianCalendar();

							int leaveDiff = (toDate.get(Calendar.DAY_OF_YEAR) - fromDate.get(Calendar.DAY_OF_YEAR)) + 1;

							noOfDays += leaveDiff;

							// checking the from date and todate is monday or
							// Friday

							if (fromDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
									|| toDate.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
								noOfDays += 2;
							}
						}
					}
					// Date auditDate = new Date();
					ltaEmployee.setBlock(appContext.getCurrentLtaBlock());
					ltaEmployee.setFiscalYear(appContext.getCurrentFiscalYear());
					ltaEmployee.setEmployee(appContext.getCurrentEmployee());
					ltaEmployee.setApprovedAmt(new BigDecimal(0));
					ltaEmployee.setActualFare(new BigDecimal(request.getParameter("actualFare")));
					ltaEmployee.setDestination1(request.getParameter("destination"));
					ltaEmployee.setOrigin(request.getParameter("origin"));
					ltaEmployee.setShortestFare(new BigDecimal(request.getParameter("shortestFare")));
					Integer totalDeps = Integer.parseInt(request.getParameter("rowCount"));

					/*
					 * swathy.raghu this is for first row of dependent table
					 */
					dependents = new ArrayList<LTAEmployeeDependent>();
					LTAEmployeeDependent firstLtaDependent = new LTAEmployeeDependent();
					Boolean sucess = lTAService.deleteAllDependents(ltaEmployee.getEmployee().getEmployeeId());
					Dependent firstDep = lTAService.getdepenedent(Integer.parseInt(request.getParameter("lTADep")));
					firstLtaDependent.setDependent(firstDep);

					firstLtaDependent.setTravellingClass(request.getParameter("travellingClass"));
					firstLtaDependent.setFare(new BigDecimal(request.getParameter("costOfFare")));
					firstLtaDependent.setDestination(request.getParameter("destination"));
					firstLtaDependent.setOrigin(request.getParameter("origin"));

					ltaEmployee.setCreatedDate(new Date());
					String mode = request.getParameter("modeOfTransport");
					if (mode.equals("Others")) {
						mode = request.getParameter("otherTransportMode");
					}

					firstLtaDependent.setModeOfTransport(mode);

					firstLtaDependent.setLtaEmployee(ltaEmployee);

					firstLtaDependent.setCreatedBy(appContext.getUserName());
					firstLtaDependent.setUpdatedBy(appContext.getUserName());
					firstLtaDependent.setCreatedDate(auditDate);
					firstLtaDependent.setUpdatedDate(auditDate);

					dependents.add(firstLtaDependent);

					if (totalDeps > 1) {
						for (int i = 2; i <= totalDeps; i++) {

							LTAEmployeeDependent ltaDependent = new LTAEmployeeDependent();
							Integer depId = Integer.parseInt(request.getParameter("lTADep" + i));
							Dependent dep = lTAService.getdepenedent(depId);
							ltaDependent.setDependent(dep);

							ltaDependent.setTravellingClass(request.getParameter("travellingClass" + i));
							ltaDependent.setFare(new BigDecimal(request.getParameter("costOfFare" + i)));
							ltaDependent.setDestination(request.getParameter("destination" + i));

							ltaDependent.setOrigin(request.getParameter("origin" + i));
							String mode1 = request.getParameter("modeOfTransport" + i);
							if (mode1.equals("Others")) {
								mode1 = request.getParameter("otherTransportMode" + i);
							}

							ltaDependent.setModeOfTransport(mode1);

							ltaDependent.setLtaEmployee(ltaEmployee);

							ltaDependent.setCreatedBy(appContext.getUserName());
							ltaDependent.setUpdatedBy(appContext.getUserName());

							ltaDependent.setCreatedBy(appContext.getUserName());
							ltaDependent.setUpdatedBy(appContext.getUserName());
							ltaDependent.setCreatedDate(auditDate);
							ltaDependent.setUpdatedDate(auditDate);
							dependents.add(ltaDependent);
						}
					}

					/*
					 * set lta_employee_id as the lta_employee_id in
					 * lta_employees to lta_employee_dependence
					 */
					for (LTAEmployeeDependent ltaEmployeeDependents : dependents) {
						ltaEmployeeDependents.setLtaEmployee(ltaEmployee);
					}
					/* Ends here */

					ltaEmployee.setDependents(dependents);

					ltaEmployee.setCreatedBy(appContext.getUserName());
					ltaEmployee.setUpdatedBy(appContext.getUserName());
					ltaEmployee.setCreatedDate(auditDate);
					ltaEmployee.setUpdatedDate(auditDate);
					ltaEmployee.setStatus(BenefitsConstants.LTA_REQUEST_STATUS_SUBMIT);
					lTAService.update(ltaEmployee);

					EmailService emailService = null;
					String messageSubject = "New LTA Request Submitted";

					String messageBody = EmailFormatter.ltaRequestSubmission(ltaEmployee,
							appContext.getCurrentEmployee());

					String toMail = EmailProperties.getProperty(BenefitsConstants.PROP_HR_EMAIL);

					emailService = new EmailService(toMail, appContext.getCurrentEmployee().getEmail(), messageBody,
							messageSubject);

					System.out.println("----------------Sending email---------------");
					Thread emailThread = new Thread(emailService);
					emailThread.start();

					mav.addObject("message", "Succesfully created new LTA Request");
					mav.setViewName("viewLTAHistory");

					List<LTAHistoryBean> beans = new ArrayList<LTAHistoryBean>();
					List<LTAEmployee> list = lTAService.get(appContext.getCurrentEmployee().getEmployeeId(),
							appContext.getCurrentLtaBlock());

					for (LTAEmployee lta : list) {
						LTAHistoryBean bean = new LTAHistoryBean();
						List<LTAEmployeeDependent> tempDependents = lTAService.get(ltaEmployee.getLtaEmployeeId());
						bean.setLTAEmployee(lta);
						bean.setDependents(tempDependents);
						beans.add(bean);
					}

				} else {
					dependents = lTAService.getLTADependences(ltaEmployee.getLtaEmployeeId());
					mav.addObject("error",
							"There is no leave requests for this date range. Please provide the same date range as you applied in the mirror.");

				}
			} catch (Exception e) {
				mav.addObject("error", "System Error Occured, please contact System Administrator/HR");
				e.printStackTrace();
			}
			List<Dependent> deps = appContext.getCurrentEmployee().getDependents();
			mav.addObject("dependents", deps);
			mav.addObject("dependent", dependents);
			mav.addObject("rowcount", dependents.size());
			mav.addObject("send", false);
			mav.addObject("ltaEmployee", ltaEmployee);

			/* add the leaves available to the left side */
			String fiscalYear = appContext.getCurrentFiscalYear();
			String ltaYear = appContext.getCurrentLtaBlock();
			int maxLtaPerBlock = Integer.parseInt(
					settingsService.getPropertyByCode(BenefitsConstants.PROP_MAX_LTA_AVAIL).getPropertyValue());

			List<LTAEmployee> existingLtaRequests = lTAService.get(appContext.getCurrentEmployee().getEmployeeId(),
					ltaYear);

			if ((existingLtaRequests == null)
					|| (existingLtaRequests != null && existingLtaRequests.size() < maxLtaPerBlock)) {

				String periodFroms = "01-01-" + ltaYear.split("-")[0];
				String periodTos = "31-12-20" + ltaYear.split("-")[1];

				GregorianCalendar fromCalendars = new GregorianCalendar();
				fromCalendars.setTime(DataTypeUtil.toDateFromStringddMMyyyy(periodFroms));

				GregorianCalendar toCalendars = new GregorianCalendar();
				toCalendars.setTime(DataTypeUtil.toDateFromStringddMMyyyy(periodTos));

				XMLGregorianCalendar fromDateXmls = null;
				XMLGregorianCalendar toDateXmls = null;

				try {
					fromDateXmls = DatatypeFactory.newInstance().newXMLGregorianCalendar(fromCalendars);
					toDateXmls = DatatypeFactory.newInstance().newXMLGregorianCalendar(toCalendars);

					/**
					 * WS request body/paramaters
					 */
					GetX0020TheX0020LeaveX0020Details leaveRequests = new GetX0020TheX0020LeaveX0020Details();
					leaveRequests.setEmployeeId(appContext.getCurrentEmployee().getEmployeeId());
					leaveRequests.setFromDate(fromDateXmls);
					leaveRequests.setToDate(toDateXmls);

					/**
					 * WS request header
					 */
					ServiceAuthenticationHeader authenticationHeaders = new ObjectFactory()
							.createServiceAuthenticationHeader();

					/**
					 * calling WS
					 */
					GetX0020TheX0020LeaveX0020DetailsResponse leaveResponses = mirrorDataService
							.getMirrorDataServiceSoap().getLeaveDetails( leaveRequests, authenticationHeaders);

					if (leaveResponses.getGetX0020TheX0020LeaveX0020DetailsResult() != null
							&& leaveResponses.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo() != null
							&& leaveResponses.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo().size() > 0) {
						mav.addObject("availableLeaves",
								leaveResponses.getGetX0020TheX0020LeaveX0020DetailsResult().getLeaveInfo());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			mav.addObject("ltaBalance",
					maxLtaPerBlock - (existingLtaRequests == null ? 0 : existingLtaRequests.size()));
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				mav.addObject("jsPeriodFrom", objectMapper
						.writeValueAsString(DatatypeFactory.newInstance().newXMLGregorianCalendar(fromCalendar)));
				mav.addObject("jsPeriodTill", objectMapper
						.writeValueAsString(DatatypeFactory.newInstance().newXMLGregorianCalendar(toCalendar)));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mav;
		}
	}

	@RequestMapping(value = "/home/controlPanel/lta/search", method = RequestMethod.GET)
	public ModelAndView showLtaRequest(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		mav = AuthorizationUtil.authorizeAdmin(appContext, "viewLtaRequest");
		BenefitsProperty property = settingsService.getPropertyByCode(BenefitsConstants.PROP_LTA_BLOCKS_LOV);
		String block = property.getPropertyValue();
		String[] blocks = block.split(",");
		mav.addObject("blocks", blocks);
		LTASearchBean searchBean = new LTASearchBean();
		mav.addObject("searchBean", searchBean);
		mav.addObject("role", appContext.getRole());

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/lta/search", method = RequestMethod.POST)
	public ModelAndView searchLtaRequests(@ModelAttribute("searchBean") LTASearchBean searchBean,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		mav = AuthorizationUtil.authorizeAdmin(appContext, "viewLtaRequest");

		if (searchBean.getLtaEmployeeId().equalsIgnoreCase("")) {
			searchBean.setLtaEmployeeId("0");
		}
		List<LTAEmployee> ltaEmployees = lTAService.listAll(searchBean.getLtaEmployeeId(), searchBean.getBlock());
		mav.addObject("ltaEmployees", ltaEmployees);
		mav.addObject("role", appContext.getRole());
		BenefitsProperty property = settingsService.getPropertyByCode(BenefitsConstants.PROP_LTA_BLOCKS_LOV);
		String block = property.getPropertyValue();
		String[] blocks = block.split(",");
		mav.addObject("blocks", blocks);

		mav.addObject("searchBean", searchBean);

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/lta/search/approve/{ltaEmployeeId}", method = RequestMethod.GET)
	public ModelAndView showRequest(@PathVariable(value = "ltaEmployeeId") Integer ltaEmployeeId,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		mav = AuthorizationUtil.authorizeAdmin(appContext, "ltaRequestApprove");
		mav.addObject("role", appContext.getRole());
		List<LTAEmployeeDependent> dependents = lTAService.get(ltaEmployeeId);
		mav.addObject("ltaDependents", dependents);

		LTAEmployee ltaEmployee = lTAService.getById(ltaEmployeeId);
		mav.addObject("ltaEmployee", ltaEmployee);

		return mav;

	}

	@RequestMapping(value = "/home/controlPanel/lta/search/approve", method = RequestMethod.POST)
	public ModelAndView approveRequest(@ModelAttribute("searchBean") LTASearchBean searchBean,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		mav = AuthorizationUtil.authorizeAdmin(appContext, "ltaRequestApprove");
		LTAEmployee ltaEmployee = lTAService.getById(Integer.parseInt(request.getParameter("ltaEmployeeId")));
		ltaEmployee.setApprovedAmt(new BigDecimal(request.getParameter("approvedAmt")));
		ltaEmployee.setStatus(BenefitsConstants.LTA_REQUEST_STATUS_HR_APPR);
		ltaEmployee.setHrApprovedBy(appContext.getCurrentEmployee().getUserName());
		if (lTAService.update(ltaEmployee)) {

			mav.addObject("message", "Succesfully approved LTA Request");
			// sending email
			EmailService emailService = null;
			String messageSubject = "Benefits Portal - LTA request approved";

			String messageBody = EmailFormatter.ltaRequestApproval(ltaEmployee);

			String ccLta1 = EmailProperties.getProperty(BenefitsConstants.PROP_TAX_RETURNS_EMAIL);
			String ccLta2 = EmailProperties.getProperty(BenefitsConstants.PROP_HR_EMAIL);

			emailService = new EmailService(ltaEmployee.getEmployee().getEmail(), ccLta1 + "," + ccLta2, messageBody,
					messageSubject);

			System.out.println("----------------Sending email---------------");
			Thread emailThread = new Thread(emailService);
			emailThread.start();
			ModelAndView nextView = AuthorizationUtil.authorizeAdmin(appContext,
					"redirect:/home/controlPanel/lta/search");
			return nextView;
		} else {
			ltaEmployee = new LTAEmployee();
			mav.addObject("message", "Failed to approve LTA Request");
		}
		mav.addObject("role", appContext.getRole());
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/lta/search/reject/{ltaEmployeeId}", method = RequestMethod.GET)
	public ModelAndView rejectRequest(@PathVariable("ltaEmployeeId") Integer ltaEmployeeId, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = null;

		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		// AuthorizationUtil.authorizeAdmin(appContext,
		// "redirect:home/controlPanel/lta/search");
		mav = AuthorizationUtil.authorizeAdmin(appContext, "redirect:/home/controlPanel/lta/search");
		LTAEmployee ltaEmployee = lTAService.getById(ltaEmployeeId);
		String reason = request.getParameter("declineReason");
		mav.addObject("role", appContext.getRole());
		if (ltaEmployee != null) {
			ltaEmployee.setStatus(BenefitsConstants.LTA_REQUEST_STATUS_HR_REJECT);
			ltaEmployee.setDeclineReason(reason);
			// benefitPlanService.updatePlanEmployee(planEmployee);
		}

		ltaEmployee.setHrApprovedBy(appContext.getCurrentEmployee().getUserName());
		if (lTAService.update(ltaEmployee)) {

			mav.addObject("message", "Rejected LTA request");
			EmailFormatter.ltaReject(ltaEmployee);

			String subject = "LTA REQUEST REJECTED!";

			String body = EmailFormatter.ltaReject(ltaEmployee);

			String to = ltaEmployee.getEmployee().getEmail();

			EmailService emailService = new EmailService(to, EmailProperties.getProperty("hrEmail"), body, subject);

			Thread emailThread = new Thread(emailService);
			emailThread.start();
			ModelAndView nextView = AuthorizationUtil.authorizeAdmin(appContext,
					"redirect:/home/controlPanel/lta/search");
			return nextView;

		} else {
			ltaEmployee = new LTAEmployee();
			mav.addObject("message", "Failed to reject LTA Request");
		}
		return mav;

	}

	@RequestMapping(value = "/home/controlPanel/lta/search/finApprove/{ltaEmployeeId}", method = RequestMethod.GET)
	public ModelAndView showFinRequest(@PathVariable(value = "ltaEmployeeId") Integer ltaEmployeeId,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		mav = AuthorizationUtil.authorizeAdmin(appContext, "ltaFinApprove");
		mav.addObject("role", appContext.getRole());
		List<LTAEmployeeDependent> dependents = lTAService.get(ltaEmployeeId);
		mav.addObject("ltaDependents", dependents);

		LTAEmployee ltaEmployee = lTAService.getById(ltaEmployeeId);
		Employee hrApprovedBY = lTAService.getHrApprovedBy(ltaEmployee.getHrApprovedBy());
		mav.addObject("hrApprovedBy", hrApprovedBY.getFirstName() + " " + hrApprovedBY.getLastName());
		mav.addObject("ltaEmployee", ltaEmployee);

		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/lta/search/finApprove", method = RequestMethod.POST)
	public ModelAndView finApproveRequest(@ModelAttribute("searchBean") LTASearchBean searchBean,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		mav = AuthorizationUtil.authorizeAdmin(appContext, "ltaFinApprove");
		LTAEmployee ltaEmployee = lTAService.getById(Integer.parseInt(request.getParameter("ltaEmployeeId")));
		mav.addObject("role", appContext.getRole());
		ltaEmployee.setStatus(BenefitsConstants.LTA_REQUEST_STATUS_FIN_APPR);
		ltaEmployee.setFinApprovedBy(appContext.getCurrentEmployee().getUserName());
		if (lTAService.update(ltaEmployee)) {

			mav.addObject("message", "Succesfully approved LTA Request");
			ModelAndView nextView = AuthorizationUtil.authorizeAdmin(appContext,
					"redirect:/home/controlPanel/lta/search");
			return nextView;
		} else {
			ltaEmployee = new LTAEmployee();
			mav.addObject("message", "Failed to approve LTA Request");
		}
		return mav;
	}

	@RequestMapping(value = "/home/controlPanel/lta/search/finReject/{ltaEmployeeId}", method = RequestMethod.GET)
	public ModelAndView finRejectRequest(@PathVariable("ltaEmployeeId") Integer ltaEmployeeId,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		mav = AuthorizationUtil.authorizeAdmin(appContext, "ltaFinApprove");
		LTAEmployee ltaEmployee = lTAService.getById(ltaEmployeeId);

		ltaEmployee.setStatus(BenefitsConstants.LTA_REQUEST_STATUS_FIN_REJECT);
		ltaEmployee.setFinApprovedBy(appContext.getCurrentEmployee().getUserName());
		if (lTAService.update(ltaEmployee)) {

			mav.addObject("message", "Rejected LTA request");
			ModelAndView nextView = AuthorizationUtil.authorizeAdmin(appContext,
					"redirect:/home/controlPanel/lta/search");
			return nextView;
		} else {
			ltaEmployee = new LTAEmployee();
			mav.addObject("message", "Failed to reject LTA Request");
		}
		mav.addObject("role", appContext.getRole());
		return mav;
	}

	@RequestMapping(value = "/home/lta/remove/{ltaEmployeeId}", method = RequestMethod.GET)
	public String deleteLtaRequest(@PathVariable("ltaEmployeeId") Integer ltaEmployeeId, HttpServletRequest request,
			HttpServletResponse response) {
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		String nextView = AuthorizationUtil.authorizeUser(appContext, "redirect:/home/lta/new/viewHistory")
				.getViewName();

		lTAService.removeLta(ltaEmployeeId);

		return nextView;
	}

}
