//package com.speridian.benefits2.controller;
//
//	import java.util.Date;
//	import java.util.List;
//
//	import javax.servlet.http.HttpServletRequest;
//	import javax.servlet.http.HttpServletResponse;
//
//	import org.springframework.beans.factory.annotation.Autowired;
//	import org.springframework.stereotype.Controller;
//	import org.springframework.web.bind.annotation.ModelAttribute;
//	import org.springframework.web.bind.annotation.PathVariable;
//	import org.springframework.web.bind.annotation.RequestMapping;
//	import org.springframework.web.bind.annotation.RequestMethod;
//	import org.springframework.web.servlet.ModelAndView;
//
//	import com.speridian.benefits2.beans.PFEmployeeResponse;
//	import com.speridian.benefits2.beans.PFEmployeeVO;
//	import com.speridian.benefits2.email.EmailFormatter;
//	import com.speridian.benefits2.email.EmailProperties;
//	import com.speridian.benefits2.email.EmailService;
//	import com.speridian.benefits2.model.pojo.BenefitsProperty;
//	import com.speridian.benefits2.model.pojo.PFDeniedEmployees;
//	import com.speridian.benefits2.model.pojo.PFDenyReasonsMaster;
//	import com.speridian.benefits2.model.pojo.PFEmployee;
//	import com.speridian.benefits2.model.pojo.PFEmployeeSlabHistory;
//	import com.speridian.benefits2.model.util.BenefitsConstants;
//	import com.speridian.benefits2.service.PFEmployeeService;
//	import com.speridian.benefits2.service.SettingsService;
//	import com.speridian.benefits2.util.AppContext;
//	import com.speridian.benefits2.util.AuthorizationUtil;
//	import com.speridian.benefits2.util.DataTypeUtil;
//	import com.speridian.benefits2.ws.client.docman.rest.DocmanRestClient;
//	import com.speridian.benefits2.ws.client.mirror.soap.MirrorDataService;
//	import com.speridian.benefits2.ws.client.mirror.soap.ObjectFactory;
//	import com.speridian.benefits2.ws.client.mirror.types.GetX0020PFX0020DetailsX0020OfX0020TheX0020Employee;
//	import com.speridian.benefits2.ws.client.mirror.types.GetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResponse;
//	import com.speridian.benefits2.ws.client.mirror.types.ServiceAuthenticationHeader;
//	import com.speridian.benefits2.ws.constants.BenefitsWSConstants;
//
//	@Controller
//	public class TestControllerNps {
//
//		AppContext appContext;
//
//		@Autowired
//		PFEmployeeService pfEmployeeService;
//
//		@Autowired
//		MirrorDataService mirrorDataService;
//
//		@Autowired
//		SettingsService settingsService;
//
//		@Autowired
//		DocmanRestClient docmanRestClient;
//
//		/*
//		 * Employee PF DashBoard
//		 */
//		@RequestMapping(value = "/home/myEnpsHome", method = RequestMethod.GET)
//		public ModelAndView viewPFDashBoard(HttpServletRequest request, HttpServletResponse response) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "employeePFHome");
//			System.out.println("-----Inside My EPF----");
//			PFEmployee pFEmployee = pfEmployeeService.getByEmpId(appContext.getEmpId());
//			// PFEmployee pFEmployee = null;
//			PFEmployeeResponse empResponse = employeeAvailableSlabs(appContext.getEmpId());
//
//			List<PFDenyReasonsMaster> pfDenyReasonsMaster = pfEmployeeService.listAllDenyReasons();
//
//			mav.addObject("optOutEnabled", false);
//			if (pFEmployee == null) {
//				/*
//				 * 
//				 * Here we will be checking if employee prefered opt out not
//				 */
//				PFDeniedEmployees pfDeniedEmployees = pfEmployeeService
//						.getDeniedEmployeesByFiscalYear(appContext.getCurrentFiscalYear(), appContext.getEmpId());
//
//				if (pfDeniedEmployees == null) {
//					mav.addObject("optOutEnabled", true);
//				}
//
//				if (empResponse.getAvailableSlab().equals("notApplicable")
//						|| empResponse.getAvailableSlab().equals("invalid")) {
//					mav.addObject("optingEnabled", false);
//				} else {
//
//					mav.addObject("optingEnabled", true);
//					mav.addObject("planSlab", empResponse.getAvailableSlab());
//				}
//
//			} else {
//
//				List<PFEmployeeSlabHistory> pfHistoryList = pfEmployeeService.listHistoryByFiscalYear(
//						appContext.getCurrentFiscalYear(), BenefitsConstants.PF_CHANGED_ENTITY_VOLAMOUNT,
//						pFEmployee.getPfEmployeeId());
//
//				System.out.println(
//						"-----------History List with FISCAL YEAR AND CHANGED ENTITY VOL PF---- " + pfHistoryList.size());
//				/* if(pfHistoryList==null || pfHistoryList.size()==0) */
//
//				/*
//				 * slabChangePossible - Only for employees with slab as Fixed and
//				 * status as HR_APPR can change their slab to VARIABLE Provided his
//				 * availableSlabs should be fixedOrVariable
//				 */
//				if (pFEmployee.getStatus().equals(BenefitsConstants.PF_EMPLOYEE_STATUS_SAVED)) {
//					mav.addObject("optingEnabled", true);
//					mav.addObject("optOutEnabled", true);
//				}
//
//				if (empResponse.getAvailableSlab().equals("fixedOrVariable")) {
//					if (pFEmployee.getStatus().equals(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED)
//							&& pFEmployee.getOptedSlab().equals(BenefitsConstants.PF_EMPLOYEE_SLAB_FIXED)) {
//						mav.addObject("slabChangePossible", true);
//					}
//				}
//
//				/*
//				 * 
//				 * voluntaryAmountChangePossible - Only fixed or variable slab
//				 * employees with their status as HR_APPR can change their voluntary
//				 * amount;
//				 */
//
//				if (pfHistoryList == null || pfHistoryList.size() <= 0) {
//					if (pFEmployee.getOptedSlab().equals(BenefitsConstants.PF_EMPLOYEE_SLAB_MANDATORY)) {
//						mav.addObject("voluntaryAmountChangePossible", false);
//					} else if (pFEmployee.getOptedSlab().equals(BenefitsConstants.PF_EMPLOYEE_SLAB_FIXED)
//							&& pFEmployee.getStatus().equals(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED)) {
//						mav.addObject("voluntaryAmountChangePossible", true);
//					} else if (pFEmployee.getOptedSlab().equals(BenefitsConstants.PF_EMPLOYEE_SLAB_VARIABLE)
//							&& pFEmployee.getStatus().equals(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED)) {
//						mav.addObject("voluntaryAmountChangePossible", true);
//					}
//				} else {
//					mav.addObject("voluntaryAmountChangePossible", false);
//				}
//				if (empResponse.getAvailableSlab().equals("notApplicable")
//						|| empResponse.getAvailableSlab().equals("invalid")) {
//					mav.addObject("optingEnabled", false);
//					/*
//					 * 
//					 * Here Employees with PF moved to Onsite - Then they will close
//					 * their existing PF. But the Details will be still available in
//					 * DB.
//					 */
//
//				} else {
//
//					// mav.addObject("optingEnabled", false);
//					mav.addObject("enroled", Boolean.TRUE);
//					mav.addObject("employeeDetails", pFEmployee);
//				}
//			}
//
//			mav.addObject("uanNumber", empResponse.getUan());
//			mav.addObject("reasons", pfDenyReasonsMaster);
//			// System.out.println("------------account number
//			// ----------"+pFEmployee.getPfAcNo());
//			// mav.addObject("pfacno",pFEmployee.getPfAcNo());
//			return mav;
//		}
//
//		/*
//		 * 
//		 * Save Opt out Reason and redirect to myEPF
//		 */
//		@RequestMapping(value = "/home/myEnpsHome/optOutNPS", method = RequestMethod.POST)
//		public ModelAndView optOutPF(HttpServletRequest request, HttpServletResponse response) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//
//			ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "redirect:/home/myEpfHome");
//			try {
//				System.out.println("--- Save PF Deny Reason --- ");
//
//				String reasonId = request.getParameter("reasonId");
//
//				/*
//				 * 
//				 * new change
//				 */
//
//				PFEmployee pfEmployee = pfEmployeeService.getByEmpId(appContext.getEmpId());
//				/*
//				 * List<PFEmployeeSlabHistory> pfEmployeeSlabHistory =
//				 * pfEmployeeService
//				 * .listSlabChangeByPFEmpId(pfEmployee.getPfEmployeeId());
//				 */
//	            try{
//	            	if (pfEmployee.getStatus().equalsIgnoreCase(BenefitsConstants.PF_EMPLOYEE_STATUS_SAVED)) {
//	    				// pfEmployeeService.delete(pfEmployeeSlabHistory);
//	    				pfEmployeeService.delete(pfEmployee);
//	    			}
//	            }catch(Exception e){
//	            	e.printStackTrace();
//	            }
//				
//
//				Date auditDate = new Date();
//
//				PFDeniedEmployees pfDeniedEmployees = new PFDeniedEmployees();
//				pfDeniedEmployees.setCreatedBy(appContext.getUserName());
//				pfDeniedEmployees.setCreatedDate(auditDate);
//				pfDeniedEmployees.setUpdatedBy(appContext.getUserName());
//				pfDeniedEmployees.setUpdatedDate(auditDate);
//				pfDeniedEmployees.setFiscalYear(appContext.getCurrentFiscalYear());
//
//				if (reasonId.equals("0")) {
//					String otherReasonDesc = request.getParameter("otherReason");
//					pfDeniedEmployees.setOtherReasons(otherReasonDesc);
//				} else {
//					PFDenyReasonsMaster denyReasonsMaster = new PFDenyReasonsMaster();
//					denyReasonsMaster.setReasonId(Integer.parseInt(reasonId));
//					pfDeniedEmployees.setReasonId(denyReasonsMaster);
//				}
//
//				pfDeniedEmployees.setEmployee(appContext.getCurrentEmployee());
//
//				if (pfEmployeeService.saveDeniedEmployee(pfDeniedEmployees)) {
//
//					EmailService emailService = null;
//					String messageSubject = "PF Plan Opt Out";
//					String messageBody = EmailFormatter.pfOptOutMail(pfDeniedEmployees);
//
//					String ccPFEmployee = EmailProperties.getProperty(BenefitsConstants.PROP_PF_ENROLLED_CC_EMAIL);
//
//					emailService = new EmailService(pfDeniedEmployees.getEmployee().getEmail(), ccPFEmployee, messageBody,
//							messageSubject);
//					Thread emailThread = new Thread(emailService);
//					emailThread.start();
//
//					System.out.println("----EMPLOYEE DENIED PROVIDENT FUND -------");
//
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return mav;
//		}
//
//		/*
//		 * 
//		 * View Employee PF Details
//		 */
//		@RequestMapping(value = "/home/myEnpsHome/viewDetails", method = RequestMethod.GET)
//		public ModelAndView viewPFDetails(HttpServletRequest request, HttpServletResponse response) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "employeePFDetailsView");
//
//			PFEmployee pfEmployee = pfEmployeeService.getByEmpId(appContext.getEmpId());
//			PFEmployeeResponse empResponse = employeeAvailableSlabs(appContext.getEmpId());
//
//			mav.addObject("downloadUrl",
//					docmanRestClient.getDownloadUrl(pfEmployee.getDocmanUUId(), appContext.getUserLoginKey()));
//			mav.addObject("pfEmployee", pfEmployee);
//			mav.addObject("empResponse", empResponse);
//			return mav;
//		}
//
//		/*
//		 * 
//		 * Employee PF - change voluntary pf amount this is applicable only for
//		 * fixed and fixedOrVariable slab employees provided their enrollment status
//		 * should be HR_APPR
//		 */
//
//		@RequestMapping(value = "/home/myEnpsHome/changeVolAmount", method = RequestMethod.GET)
//		public ModelAndView viewVolAmountChange(HttpServletRequest request, HttpServletResponse response) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "employeePFVoluntaryAmountChange");
//
//			PFEmployeeVO pfemployeevo = new PFEmployeeVO();
//			PFEmployee pfemployee = pfEmployeeService.getByEmpId(appContext.getEmpId());
//
//			pfemployeevo.setFormVoluntaryPF(pfemployee.getFormVoluntaryPF());
//			mav.addObject("employeePFBean", pfemployeevo);
//
//			return mav;
//
//		}
//
//		/*
//		 * 
//		 * Employee - PF voluntary amount change submission.
//		 */
//
//		@RequestMapping(value = "/home/npsEnrollment/changeVolAmountSubmit", method = RequestMethod.POST)
//		public ModelAndView volAmountChangeSubmit(HttpServletRequest request, HttpServletResponse response,
//				@ModelAttribute("employeePFBean") PFEmployeeVO pfemployeevo) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "redirect:/home/myEpfHome");
//
//			Date auditDate = new Date();
//			String currentVolAmount = request.getParameter("volAmountDb");
//			
//			Integer currentAmount = Integer.parseInt(currentVolAmount);
//			System.out.println("--------^^^^^^^^^ vol AMount in DB ^^^^^^----------" + currentVolAmount);
//			System.out.println("************NEWWWWWWWWWWWWWWWWWW****************" + pfemployeevo.getFormVoluntaryPF());
//
//			PFEmployee pfemployee = pfEmployeeService.getByEmpId(appContext.getEmpId());
//			PFEmployeeResponse empResponse = employeeAvailableSlabs(appContext.getEmpId());
//
//			pfemployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_VOL_CHANGE_SUBMITTED);
//			pfemployee.setFormVoluntaryPF(pfemployeevo.getFormVoluntaryPF());
//
//			pfemployee.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//
//			pfemployee.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//
//			// here check whether the date opted is before or after 21st of the
//			// opted month
//
//			BenefitsProperty property = settingsService.getPropertyByCode(BenefitsConstants.PF_ENROLL_EFF_DAY);
//			Integer cutoffDate = Integer.parseInt(property.getPropertyValue());
//
//			Date currDate = new Date();
//
//			if (currDate.getDate() > cutoffDate) {
//				currDate.setDate(1);
//				currDate.setMonth(currDate.getMonth() + 1);
//				System.out.println("**********Customized Date***********" + currDate);
//				pfemployee.setEffFrom(currDate);
//
//			} else {
//				pfemployee.setEffFrom(auditDate);
//			}
//
//			PFEmployeeSlabHistory pfemployeeHistory = new PFEmployeeSlabHistory();
//
//			pfemployeeHistory.setPfEmployee(pfemployee);
//			pfemployeeHistory.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//			pfemployeeHistory.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_VOL_CHANGE_SUBMITTED);
//			pfemployeeHistory.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//			pfemployeeHistory.setChangedEntity(BenefitsConstants.PF_CHANGED_ENTITY_VOLAMOUNT);
//			// here change required
//			pfemployeeHistory.setEntityFrom(currentVolAmount);
//			pfemployeeHistory.setEntityTo(pfemployeevo.getFormVoluntaryPF());
//
//			pfEmployeeService.saveHistory(pfemployeeHistory);
//
//			if (pfEmployeeService.update(pfemployee)) {
//
//				// Sending Email
//
//				EmailService emailService = null;
//				String messageSubject = "PF voluntary Amount Change Submitted";
//				String messageBody = EmailFormatter
//
//						.pfVolAmountChangeSubmit(pfemployee, currentVolAmount);
//
//				String ccPFEmployee = EmailProperties.getProperty(BenefitsConstants.PROP_PF_ENROLLED_CC_EMAIL);
//
//				emailService = new EmailService(pfemployee.getEmployee().getEmail(), ccPFEmployee, messageBody,
//						messageSubject);
//				Thread emailThread = new Thread(emailService);
//				emailThread.start();
//			}
//
//			mav.addObject("volAmountSuccessMsg",
//					"Your request for Voluntary amount change has been successfully completed.");
//			return mav;
//		}
//
//		/*
//		 * 
//		 * VIEW Employee Slab change to variable - only for fixed slab employees and
//		 * status : HR_APPR
//		 */
//		@RequestMapping(value = "/home/myEnpsHome/changeToVariable", method = RequestMethod.GET)
//		public ModelAndView viewSlabChangeToVariable(HttpServletRequest request, HttpServletResponse response) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "employeePFSlabChangeToVariable");
//
//			PFEmployeeVO pfemployeevo = new PFEmployeeVO();
//			PFEmployee pfemployee = pfEmployeeService.getByEmpId(appContext.getEmpId());
//
//			System.out.println("----- Employee Opted Slab--- : " + pfemployee.getOptedSlab());
//			pfemployeevo.setOptedSlab(pfemployee.getOptedSlab());
//			mav.addObject("employeePFBean", pfemployeevo);
//
//			return mav;
//
//		}
//
//		/*
//		 * Submit Employee Slab change to variable - only for fixed slab employees
//		 * and status : HR_APPR
//		 */
//
//		@RequestMapping(value = "/home/npsEnrollment/changeToVariableSubmit", method = RequestMethod.POST)
//		public ModelAndView volchangeToVariableSubmit(HttpServletRequest request, HttpServletResponse response,
//				@ModelAttribute("employeePFBean") PFEmployeeVO pfemployeevo) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "redirect:/home/myEpfHome");
//
//			Date auditDate = new Date();
//			PFEmployee pfemployee = pfEmployeeService.getByEmpId(appContext.getEmpId());
//			PFEmployeeResponse empResponse = employeeAvailableSlabs(appContext.getEmpId());
//
//			pfemployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_SLAB_CHANGE_SUBMITTED);
//			pfemployee.setOptedSlab(BenefitsConstants.PF_EMPLOYEE_SLAB_VARIABLE);
//
//			pfemployee.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//
//			// Sending Email
//
//			pfemployee.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//			// here check whether the date opted is before or after 21st of the
//			// opted month
//
//			BenefitsProperty property = settingsService.getPropertyByCode(BenefitsConstants.PF_ENROLL_EFF_DAY);
//			Integer cutoffDate = Integer.parseInt(property.getPropertyValue());
//
//			Date currDate = new Date();
//
//			if (currDate.getDate() > cutoffDate) {
//				currDate.setDate(1);
//				currDate.setMonth(currDate.getMonth() + 1);
//				System.out.println("**********Customized Date***********" + currDate);
//				pfemployee.setEffFrom(currDate);
//
//			} else {
//				pfemployee.setEffFrom(auditDate);
//			}
//
//			PFEmployeeSlabHistory pfemployeeHistory = new PFEmployeeSlabHistory();
//
//			pfemployeeHistory.setPfEmployee(pfemployee);
//			pfemployeeHistory.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//			pfemployeeHistory.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_SLAB_CHANGE_SUBMITTED);
//			pfemployeeHistory.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//			pfemployeeHistory.setChangedEntity(BenefitsConstants.PF_CHANGED_ENTITY_SLAB);
//			// here change required
//			pfemployeeHistory.setEntityFrom(BenefitsConstants.PF_CHANGED_ENTITY_SLAB_FROM);
//			pfemployeeHistory.setEntityTo(BenefitsConstants.PF_CHANGED_ENTITY_SLAB_TO);
//
//			pfEmployeeService.saveHistory(pfemployeeHistory);
//
//			if (pfEmployeeService.update(pfemployee)) {
//				// Sending Email
//
//				EmailService emailService = null;
//				String messageSubject = "PF Slab Change Request Submitted";
//				String messageBody = EmailFormatter.pfEnrollmentChanged(pfemployee);
//				String ccPFEmployee = EmailProperties.getProperty(BenefitsConstants.PROP_PF_ENROLLED_CC_EMAIL);
//
//				emailService = new EmailService(pfemployee.getEmployee().getEmail(), ccPFEmployee, messageBody,
//						messageSubject);
//				Thread emailThread = new Thread(emailService);
//				emailThread.start();
//			}
//
//			mav.addObject("slabChangeSuccessMsg", "Your slab change request to Variable has been successfully completed.");
//
//			return mav;
//
//		}
//
//		// Change Voluntary amount search employees
//
//		@RequestMapping(value = "/home/controlPanel/nps/changeVolAmountSearch", method = RequestMethod.GET)
//		public ModelAndView viewPFVolAmountSearch(HttpServletRequest request, HttpServletResponse response) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewPFVolAmountChangeEmployees");
//
//			List<PFEmployeeSlabHistory> volSlabHistories = pfEmployeeService.listVolSlab(
//					BenefitsConstants.PF_CHANGED_ENTITY_VOLAMOUNT, BenefitsConstants.PF_EMPLOYEE_STATUS_HR_REJECTED,
//					BenefitsConstants.PF_EMPLOYEE_STATUS_VOL_CHANGE_SUBMITTED);
//			System.out.println("******PF EMPLOYEE*********" + volSlabHistories);
//			mav.addObject("volSlabHistories", volSlabHistories);
//
//			return mav;
//		}
//
//		/*
//		 * approve selected vol Amount changed Employees here logic - change
//		 */
//
//		@RequestMapping(value = "/home/controlPanel/nps/volAmountchangeSearch/approveSelected")
//		public ModelAndView volchangeToVariableApproval(HttpServletRequest request, HttpServletResponse response) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			Date auditDate = new Date();
//
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
//					"redirect:/home/controlPanel/pf/changeVolAmountSearch");
//
//			String selectedIDs = request.getParameter("approveSelected");
//			System.out.println("++++++++++++++++++++++++++++++" + selectedIDs + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//			String[] slabIds = selectedIDs.split(",");
//
//			for (String ids : slabIds) {
//				Integer pfSlabIDs = Integer.parseInt(ids);
//
//				PFEmployeeSlabHistory slabHistory = pfEmployeeService.getSlabHistory(pfSlabIDs);
//				PFEmployee pfEmployee = pfEmployeeService.getById(slabHistory.getPfEmployee().getPfEmployeeId());
//
//				slabHistory.setPfEmployee(slabHistory.getPfEmployee());
//				slabHistory.setChangedEntity(BenefitsConstants.PF_CHANGED_ENTITY_VOLAMOUNT);
//				slabHistory.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED);
//				slabHistory.setEntityFrom(slabHistory.getEntityFrom());
//				slabHistory.setEntityTo(slabHistory.getEntityTo());
//				slabHistory.setCreatedBy(appContext.getUserName());
//				slabHistory.setCreatedDate(auditDate);
//				pfEmployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED);
//
//				pfEmployeeService.update(pfEmployee);
//				if (pfEmployeeService.saveHistory(slabHistory)) {
//					// Sending Email
//
//					EmailService emailService = null;
//					String messageSubject = "PF Voluntary Amount Change Approved";
//					String messageBody = EmailFormatter.pfVolAmountChangeApproved(slabHistory);
//					String ccPFEmployee = EmailProperties.getProperty(BenefitsConstants.PROP_HR_EMAIL);
//
//					emailService = new EmailService(slabHistory.getPfEmployee().getEmployee().getEmail(), ccPFEmployee,
//							messageBody, messageSubject);
//					Thread emailThread = new Thread(emailService);
//					emailThread.start();
//				}
//			}
//			return mav;
//
//		}
//
//		// single approval view - Vol Amount Change
//
//		@RequestMapping(value = "/home/controlPanel/nps/volAmountchangeSearch/approvalView/{pfEmpSlabHistoryId}", method = RequestMethod.GET)
//		public ModelAndView volchangeToVariableSingleApproval(HttpServletRequest request, HttpServletResponse response,
//				@PathVariable(value = "pfEmpSlabHistoryId") Integer pfEmpSlabHistoryId) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewPFEmployeeVolAmountDetails");
//
//			PFEmployeeSlabHistory slabHistory = pfEmployeeService.getSlabHistory(pfEmpSlabHistoryId);
//			mav.addObject("slabHistory", slabHistory);
//			return mav;
//
//		}
//
//		// single approval submit- Vol Amount Change
//
//		@RequestMapping(value = "/home/controlPanel/nps/search/volAmountApprove/{pfEmpSlabHistoryId}", method = RequestMethod.GET)
//		public ModelAndView volchangeToVariableSingleApprovalSubmit(HttpServletRequest request,
//				HttpServletResponse response, @PathVariable(value = "pfEmpSlabHistoryId") Integer pfEmpSlabHistoryId) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			Date auditDate = new Date();
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
//					"redirect:/home/controlPanel/pf/changeVolAmountSearch");
//			try {
//				PFEmployeeSlabHistory slabHistory = pfEmployeeService.getSlabHistory(pfEmpSlabHistoryId);
//				PFEmployee pfEmployee = pfEmployeeService.getById(slabHistory.getPfEmployee().getPfEmployeeId());
//
//				slabHistory.setPfEmployee(slabHistory.getPfEmployee());
//				slabHistory.setChangedEntity(BenefitsConstants.PF_CHANGED_ENTITY_VOLAMOUNT);
//				slabHistory.setEntityFrom(slabHistory.getEntityFrom());
//				slabHistory.setEntityTo(slabHistory.getEntityTo());
//				slabHistory.setCreatedBy(appContext.getUserName());
//				slabHistory.setCreatedDate(auditDate);
//				slabHistory.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED);
//				pfEmployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED);
//
//				pfEmployeeService.update(pfEmployee);
//
//				pfEmployeeService.saveHistory(slabHistory);
//				mav.addObject("slabHistory", slabHistory);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return mav;
//
//		}
//
//		// Voluntary Amount Reject
//
//		@RequestMapping(value = "/home/controlPanel/volAmountApprove/reject/{pfEmpSlabHistoryId}", method = RequestMethod.GET)
//		public ModelAndView volAmountReject(HttpServletRequest request, HttpServletResponse response,
//				@PathVariable(value = "pfEmpSlabHistoryId") Integer pfEmpSlabHistoryId) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//
//			Date auditDate = new Date();
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
//					"redirect:/home/controlPanel/pf/changeVolAmountSearch");
//
//			PFEmployeeSlabHistory slabHistory = pfEmployeeService.getSlabHistory(pfEmpSlabHistoryId);
//			PFEmployee pfEmployee = pfEmployeeService.getById(slabHistory.getPfEmployee().getPfEmployeeId());
//
//			slabHistory.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_REJECTED);
//			slabHistory.setPfEmployee(slabHistory.getPfEmployee());
//			slabHistory.setChangedEntity(BenefitsConstants.PF_CHANGED_ENTITY_VOLAMOUNT);
//			slabHistory.setEntityFrom(slabHistory.getEntityFrom());
//			slabHistory.setEntityTo(slabHistory.getEntityTo());
//			slabHistory.setCreatedBy(appContext.getUserName());
//			slabHistory.setCreatedDate(auditDate);
//			pfEmployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_REJECTED);
//			if (pfEmployeeService.saveHistory(slabHistory)) {
//
//				EmailService emailService = null;
//				String messageSubject = "Voluntary PF Request Rejected by HR";
//				String messageBody = EmailFormatter.pfVolAmountChangeRejected(slabHistory.getPfEmployee());
//				String ccPFEmployee = EmailProperties.getProperty(BenefitsConstants.PROP_PF_ENROLLED_CC_EMAIL);
//
//				emailService = new EmailService(pfEmployee.getEmployee().getEmail(), ccPFEmployee, messageBody,
//						messageSubject);
//				System.out.println("+++++++++++++++++Email Sending Started+++++++++++++++");
//
//				Thread emailThread = new Thread(emailService);
//				emailThread.start();
//
//			}
//
//			return mav;
//		}
//
//		/*
//		 * 
//		 * PF Enrolment - New Requirment - From dashboard - Form View
//		 */
//		@RequestMapping(value = "/home/npsEnrollment", method = RequestMethod.GET)
//		public ModelAndView employeePFEnrollment(HttpServletRequest request, HttpServletResponse response) {
//
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "employeeProvidentFundEnrollmentForm");
//
//			PFEmployee pfemployee = pfEmployeeService.getByEmpId(appContext.getEmpId());
//
//			PFEmployeeResponse empResponse = employeeAvailableSlabs(appContext.getEmpId());
//			PFEmployeeVO pfemployeevo = new PFEmployeeVO();
//
//			if (pfemployee == null) {
//				pfemployeevo.setEmployee(appContext.getCurrentEmployee());
//				pfemployeevo.setFormEmpName(appContext.getCurrentEmployee().getFirstName() + " "
//						+ appContext.getCurrentEmployee().getLastName());
//				pfemployeevo.setFormDOB(DataTypeUtil.toDateddMMMMMyyyy(appContext.getCurrentEmployee().getDateOfBirth()));
//				pfemployeevo.setFormGender(appContext.getCurrentEmployee().getGender());
//				pfemployeevo.setFormMobile(appContext.getCurrentEmployee().getMobileNo());
//				pfemployeevo.setFormPan(appContext.getCurrentEmployee().getPan());
//				pfemployeevo.setFormOffEmail(appContext.getCurrentEmployee().getEmail());
//				/*
//				 * create documentuuid
//				 */
//				String docmanUUId = docmanRestClient.getDocmanUuid(BenefitsWSConstants.SCREEN_PF_SUBMISSION,
//						appContext.getUserLoginKey(), BenefitsConstants.BEN_PROP_CONSTANT_PF_AADHAR,
//						appContext.getCurrentEmployee().getEmployeeCode() + "/" + appContext.getCurrentFiscalYear(),
//						"Employee Code", appContext.getCurrentEmployee().getEmployeeCode(), appContext.getUserName(),
//						BenefitsConstants.BEN_PROP_CONSTANT_PF_DOC_TYPE_AADHAR);
//				pfemployeevo.setDocmanUUId(docmanUUId);
//
//			} else {
//				System.out.println("-------SLAB IN DB---- : " + pfemployee.getOptedSlab());
//				pfemployeevo.setOptedSlab(pfemployee.getOptedSlab());
//				pfemployeevo.setFormEmpName(pfemployee.getFormEmpName());
//
//				if (pfemployee.getFormDOB() != null) {
//					pfemployeevo.setFormDOB(DataTypeUtil.toDateddMMMMMyyyy(pfemployee.getFormDOB()));
//				}
//				pfemployeevo.setFormGender(pfemployee.getFormGender());
//				pfemployeevo.setFormMaritalStatus(pfemployee.getFormMaritalStatus());
//				pfemployeevo.setFormGuardianName(pfemployee.getFormGuardianName());
//				pfemployeevo.setFormMobile(pfemployee.getFormMobile());
//				pfemployeevo.setFormEmail(pfemployee.getFormEmail());
//				pfemployeevo.setFormPan(pfemployee.getFormPan());
//				pfemployeevo.setFormAadharNo(pfemployee.getFormAadharNo());
//				pfemployeevo.setFormCurrentAddress(pfemployee.getFormCurrentAddress());
//				pfemployeevo.setFormPermanentAddress(pfemployee.getFormPermanentAddress());
//				pfemployeevo.setFormPrevEstablishment(pfemployee.getFormPrevEstablishment());
//				pfemployeevo.setFormPrevPfAccNo(pfemployee.getFormPrevPfAccNo());
//
//				if (pfemployee.getFormPrevDOJ() != null) {
//					pfemployeevo.setFormPrevDOJ(DataTypeUtil.toDateddMMMMMyyyy(pfemployee.getFormPrevDOJ()));
//				}
//				if (pfemployee.getFormPrevDOL() != null) {
//					pfemployeevo.setFormPrevDOL(DataTypeUtil.toDateddMMMMMyyyy(pfemployee.getFormPrevDOL()));
//				}
//
//				pfemployeevo.setUan(pfemployee.getUan());
//				pfemployeevo.setActive(pfemployee.getActive());
//				pfemployeevo.setStatus(pfemployee.getStatus());
//				pfemployeevo.setAgreeTermsConditions(pfemployee.getAgreeTermsConditions());
//				// pfemployeevo.setEffFrom(DataTypeUtil.toDateddMMMMMyyyy(pfemployee.getEffFrom()));
//				pfemployeevo.setUpdatedBy(pfemployee.getUpdatedBy());
//				pfemployeevo.setCreatedBy(pfemployee.getCreatedBy());
//
//				pfemployeevo.setFormOffEmail(appContext.getCurrentEmployee().getEmail());
//				pfemployeevo.setFormVoluntaryPF(pfemployee.getFormVoluntaryPF());
//				if (pfemployee.getDocmanUUId() == null) {
//					pfemployeevo.setDocmanUUId(pfemployee.getDocmanUUId());
//				} else {
//					String docmanUUId = docmanRestClient.getDocmanUuid(BenefitsWSConstants.SCREEN_PF_SUBMISSION,
//							appContext.getUserLoginKey(), BenefitsConstants.BEN_PROP_CONSTANT_PF_AADHAR,
//							appContext.getCurrentEmployee().getEmployeeCode() + "/" + appContext.getCurrentFiscalYear(),
//							"Employee Code", appContext.getCurrentEmployee().getEmployeeCode(), appContext.getUserName(),
//							BenefitsConstants.BEN_PROP_CONSTANT_PF_DOC_TYPE_AADHAR);
//					pfemployeevo.setDocmanUUId(docmanUUId);
//				}
//
//			}
//
//			System.out.println("-------PF EMPLOYEE STATUS----------" + pfemployeevo.getStatus());
//
//			if (pfemployeevo.getStatus() == null
//					|| pfemployeevo.getStatus().equals(BenefitsConstants.PF_EMPLOYEE_STATUS_SAVED)) {
//				mav.addObject("enrollmentPossible", true);
//			}
//			pfemployeevo.setUploadUrl(
//					docmanRestClient.getUploadUrl(pfemployeevo.getDocmanUUId(), appContext.getUserLoginKey()));
//			pfemployeevo.setDownloadUrl(
//					docmanRestClient.getDownloadUrl(pfemployeevo.getDocmanUUId(), appContext.getUserLoginKey()));
//
//			mav.addObject("employeePFBean", pfemployeevo);
//			mav.addObject("planSlab", empResponse.getAvailableSlab());
//			// mav.addObject("document", null);
//
//			return mav;
//		}
//
//		/*
//		 * PF Form - Save without submit from dashboard enrollment
//		 */
//
//		@RequestMapping(value = "/home/npsEnrollment/saveWithoutSubmit", method = RequestMethod.POST)
//		public ModelAndView employeePFEnrollmentSaveWithoutSubmit(HttpServletRequest request, HttpServletResponse response,
//				@ModelAttribute("employeePFBean") PFEmployeeVO pfemployeevo) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "redirect:/home/pfEnrollment");
//
//			String formDate = pfemployeevo.getFormDOB();
//			String formPrevDoj = pfemployeevo.getFormPrevDOJ();
//			String formPrevDol = pfemployeevo.getFormPrevDOL();
//			String formOptedSlab = request.getParameter("selectPlan");
//			System.out.println("---------***********************---------" + formOptedSlab);
//			Date auditDate = new Date();
//
//			PFEmployee pfemployee = pfEmployeeService.getByEmpId(appContext.getEmpId());
//			PFEmployeeResponse empResponse = employeeAvailableSlabs(appContext.getEmpId());
//
//			if (pfemployee == null) {
//				pfemployee = new PFEmployee();
//				pfemployee.setEmployee(appContext.getCurrentEmployee());
//
//				System.out.println("----------Opted Slab---------" + pfemployeevo.getOptedSlab());
//				if (pfemployeevo.getOptedSlab().equals(BenefitsConstants.PF_EMPLOYEE_SLAB_FIXED)) {
//					pfemployee.setOptedSlab(BenefitsConstants.PF_EMPLOYEE_SLAB_FIXED);
//				}
//				if (pfemployeevo.getOptedSlab().equals(BenefitsConstants.PF_EMPLOYEE_SLAB_VARIABLE)) {
//					pfemployee.setOptedSlab(BenefitsConstants.PF_EMPLOYEE_SLAB_VARIABLE);
//				}
//
//				if (pfemployeevo.getOptedSlab().equals(BenefitsConstants.PF_EMPLOYEE_SLAB_MANDATORY)) {
//					pfemployee.setOptedSlab(BenefitsConstants.PF_EMPLOYEE_SLAB_MANDATORY);
//				}
//
//				pfemployee.setFormEmpName(pfemployeevo.getFormEmpName());
//				pfemployee.setFormDOB(DataTypeUtil.toDateFromStringddMMMMMyyyy(formDate));
//				pfemployee.setFormGender(pfemployeevo.getFormGender());
//				pfemployee.setFormMaritalStatus(pfemployeevo.getFormMaritalStatus());
//				pfemployee.setFormGuardianName(pfemployeevo.getFormGuardianName());
//				pfemployee.setFormMobile(pfemployeevo.getFormMobile());
//				pfemployee.setFormEmail(pfemployeevo.getFormEmail());
//				pfemployee.setFormPan(pfemployeevo.getFormPan());
//				pfemployee.setFormAadharNo(pfemployeevo.getFormAadharNo());
//				pfemployee.setFormCurrentAddress(pfemployeevo.getFormCurrentAddress());
//				pfemployee.setFormPermanentAddress(pfemployeevo.getFormPermanentAddress());
//				pfemployee.setFormPrevEstablishment(pfemployeevo.getFormPrevEstablishment());
//				pfemployee.setFormPrevPfAccNo(pfemployeevo.getFormPrevPfAccNo());
//				pfemployee.setFormPrevDOJ(DataTypeUtil.toDateFromStringddMMMMMyyyy(formPrevDoj));
//				pfemployee.setFormPrevDOL(DataTypeUtil.toDateFromStringddMMMMMyyyy(formPrevDol));
//
//				pfemployee.setActive(true);
//				pfemployee.setAgreeTermsConditions(true);
//				pfemployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_SAVED);
//				// pfemployee.setEffFrom(auditDate);
//				pfemployee.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//				pfemployee.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//				pfemployee.setOptedDate(auditDate);
//				pfemployee.setCurrentDOJ(appContext.getCurrentEmployee().getDateOfJoin());
//				pfemployee.setActive(true);
//				pfemployee.setAgreeTermsConditions(true);
//				pfemployee.setFormVoluntaryPF(pfemployeevo.getFormVoluntaryPF());
//				pfemployee.setDocmanUUId(pfemployeevo.getDocmanUUId());
//
//				if (pfemployeevo.getUan() == null || pfemployeevo.getUan() == "" || pfemployeevo.getUan().isEmpty()) {
//					pfemployee.setUan(empResponse.getUan());
//				} else {
//					pfemployee.setUan(pfemployeevo.getUan());
//				}
//				if (pfEmployeeService.save(pfemployee)) {
//					mav.addObject("successMessage", "your details are saved.");
//
//					mav.addObject("status", true);
//
//				} else {
//					mav.addObject("errorMessage", "Failed to Save PF Form. Please Try again!");
//					mav.addObject("status", false);
//
//				}
//
//			} else {
//				pfemployee.setEmployee(appContext.getCurrentEmployee());
//
//				System.out.println("--------SLLLLLLLLLAAAAABBBBBBBBBBBB----- : " + pfemployeevo.getOptedSlab());
//				pfemployee.setOptedSlab(pfemployeevo.getOptedSlab());
//				pfemployee.setFormEmpName(pfemployeevo.getFormEmpName());
//				pfemployee.setFormDOB(DataTypeUtil.toDateFromStringddMMMMMyyyy(formDate));
//				pfemployee.setFormGender(pfemployeevo.getFormGender());
//				pfemployee.setFormMaritalStatus(pfemployeevo.getFormMaritalStatus());
//				pfemployee.setFormGuardianName(pfemployeevo.getFormGuardianName());
//				pfemployee.setFormMobile(pfemployeevo.getFormMobile());
//				pfemployee.setFormEmail(pfemployeevo.getFormEmail());
//				pfemployee.setFormPan(pfemployeevo.getFormPan());
//				pfemployee.setFormAadharNo(pfemployeevo.getFormAadharNo());
//				pfemployee.setFormCurrentAddress(pfemployeevo.getFormCurrentAddress());
//				pfemployee.setFormPermanentAddress(pfemployeevo.getFormPermanentAddress());
//				pfemployee.setFormPrevEstablishment(pfemployeevo.getFormPrevEstablishment());
//				pfemployee.setFormPrevPfAccNo(pfemployeevo.getFormPrevPfAccNo());
//				pfemployee.setFormPrevDOJ(DataTypeUtil.toDateFromStringddMMMMMyyyy(formPrevDoj));
//				pfemployee.setFormPrevDOL(DataTypeUtil.toDateFromStringddMMMMMyyyy(formPrevDol));
//				if (pfemployeevo.getUan() == null || pfemployeevo.getUan() == "" || pfemployeevo.getUan().isEmpty()) {
//					pfemployee.setUan(empResponse.getUan());
//				} else {
//					pfemployee.setUan(pfemployeevo.getUan());
//				}
//				pfemployee.setActive(pfemployeevo.getActive());
//				pfemployee.setAgreeTermsConditions(pfemployeevo.getAgreeTermsConditions());
//				pfemployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_SAVED);
//				// pfemployee.setEffFrom(auditDate);
//				pfemployee.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//				pfemployee.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//				pfemployee.setFormVoluntaryPF(pfemployeevo.getFormVoluntaryPF());
//				pfemployee.setDocmanUUId(pfemployeevo.getDocmanUUId());
//
//				if (pfEmployeeService.update(pfemployee)) {
//					mav.addObject("successMessage", "Successfully Updated PF Form");
//
//				} else {
//					mav.addObject("errorMessage", "Failed to Update PF Form. Please Try Again!");
//				}
//
//			}
//
//			mav.addObject("planSlab", empResponse.getAvailableSlab());
//
//			return mav;
//		}
//
//		/*
//		 * Employee PF - Submission
//		 */
//
//		@RequestMapping(value = "/home/npsEnrollment/submit", method = RequestMethod.POST)
//		public ModelAndView employeePFEnrollmentSubmit(HttpServletRequest request, HttpServletResponse response,
//				@ModelAttribute("employeePFBean") PFEmployeeVO pfemployeevo) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "redirect:/home/pfEnrollment");
//
//			String formDate = pfemployeevo.getFormDOB();
//			String formPrevDoj = pfemployeevo.getFormPrevDOJ();
//			String formPrevDol = pfemployeevo.getFormPrevDOL();
//			Date auditDate = new Date();
//
//			PFEmployee pfemployee = pfEmployeeService.getByEmpId(appContext.getEmpId());
//			PFEmployeeResponse empResponse = employeeAvailableSlabs(appContext.getEmpId());
//
//			if (pfemployee == null) {
//
//				pfemployee = new PFEmployee();
//
//				pfemployee.setEmployee(appContext.getCurrentEmployee());
//
//				if (pfemployeevo.getOptedSlab().equals(BenefitsConstants.PF_EMPLOYEE_SLAB_FIXED)) {
//					pfemployee.setOptedSlab(BenefitsConstants.PF_EMPLOYEE_SLAB_FIXED);
//				}
//				if (pfemployeevo.getOptedSlab().equals(BenefitsConstants.PF_EMPLOYEE_SLAB_VARIABLE)) {
//					pfemployee.setOptedSlab(BenefitsConstants.PF_EMPLOYEE_SLAB_VARIABLE);
//				}
//
//				if (pfemployeevo.getOptedSlab().equals(BenefitsConstants.PF_EMPLOYEE_SLAB_MANDATORY)) {
//					pfemployee.setOptedSlab(BenefitsConstants.PF_EMPLOYEE_SLAB_MANDATORY);
//				}
//
//				pfemployee.setFormEmpName(pfemployeevo.getFormEmpName());
//				pfemployee.setFormDOB(DataTypeUtil.toDateFromStringddMMMMMyyyy(formDate));
//				pfemployee.setFormGender(pfemployeevo.getFormGender());
//				pfemployee.setFormMaritalStatus(pfemployeevo.getFormMaritalStatus());
//				pfemployee.setFormGuardianName(pfemployeevo.getFormGuardianName());
//				pfemployee.setFormMobile(pfemployeevo.getFormMobile());
//				pfemployee.setFormEmail(pfemployeevo.getFormEmail());
//				pfemployee.setFormPan(pfemployeevo.getFormPan());
//				pfemployee.setFormAadharNo(pfemployeevo.getFormAadharNo());
//				pfemployee.setFormCurrentAddress(pfemployeevo.getFormCurrentAddress());
//				pfemployee.setFormPermanentAddress(pfemployeevo.getFormPermanentAddress());
//				pfemployee.setFormPrevEstablishment(pfemployeevo.getFormPrevEstablishment());
//				pfemployee.setFormPrevPfAccNo(pfemployeevo.getFormPrevPfAccNo());
//				pfemployee.setFormPrevDOJ(DataTypeUtil.toDateFromStringddMMMMMyyyy(formPrevDoj));
//				pfemployee.setFormPrevDOL(DataTypeUtil.toDateFromStringddMMMMMyyyy(formPrevDol));
//				if (pfemployeevo.getUan() == null || pfemployeevo.getUan() == "" || pfemployeevo.getUan().isEmpty()) {
//					pfemployee.setUan(empResponse.getUan());
//				} else {
//					pfemployee.setUan(pfemployeevo.getUan());
//				}
//				pfemployee.setActive(true);
//				pfemployee.setAgreeTermsConditions(true);
//				pfemployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_SUBMITTED);
//
//				pfemployee.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//				pfemployee.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//
//				pfemployee.setFormVoluntaryPF(pfemployeevo.getFormVoluntaryPF());
//
//				pfemployee.setOptedDate(auditDate);
//				pfemployee.setCurrentDOJ(appContext.getCurrentEmployee().getDateOfJoin());
//
//				// here check whether the date opted is before or after 21st of the
//				// opted month
//
//				BenefitsProperty property = settingsService.getPropertyByCode(BenefitsConstants.PF_ENROLL_EFF_DAY);
//				Integer cutoffDate = Integer.parseInt(property.getPropertyValue());
//
//				Date currDate = new Date();
//				if (currDate.getDate() > cutoffDate) {
//					currDate.setDate(1);
//					currDate.setMonth(currDate.getMonth() + 1);
//					System.out.println("**********Customized Date***********" + currDate);
//					pfemployee.setEffFrom(currDate);
//
//				} else {
//					pfemployee.setEffFrom(auditDate);
//				}
//				pfemployee.setDocmanUUId(pfemployeevo.getDocmanUUId());
//
//				PFEmployeeSlabHistory pfHistory = new PFEmployeeSlabHistory();
//
//				pfHistory.setPfEmployee(pfemployee);
//				pfHistory.setChangedEntity(BenefitsConstants.PF_CHANGED_ENTITY_INITIAL_SUBMISSION);
//				pfHistory.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_SUBMITTED);
//				pfHistory.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//				pfHistory.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//				pfHistory.setFiscalYear(appContext.getCurrentFiscalYear());
//
//				if (pfEmployeeService.save(pfemployee)) {
//
//					// Sending Email
//
//					pfEmployeeService.saveHistory(pfHistory);
//					// Sending Email
//
//					EmailService emailService = null;
//					String messageSubject = "PF request enrolled";
//					String messageBody = EmailFormatter.pfEnrolled(pfemployee);
//					String ccPFEmployee = EmailProperties.getProperty(BenefitsConstants.PROP_PF_ENROLLED_CC_EMAIL);
//
//					emailService = new EmailService(pfemployee.getEmployee().getEmail(), ccPFEmployee, messageBody,
//							messageSubject);
//					Thread emailThread = new Thread(emailService);
//					emailThread.start();
//
//					mav.addObject("successMessage", "Successfully Submitted PF Form");
//
//					mav.addObject("status", true);
//
//				} else {
//					mav.addObject("errorMessage", "Failed to Submit PF Form. Please try again!");
//					mav.addObject("status", false);
//
//				}
//				System.out.println("----------- FORM VOL AMOUNT ------" + pfemployeevo.getFormVoluntaryPF());
//				// double entry when vol amount submission - at the time of
//				// enrollment
//				if (pfemployeevo.getFormVoluntaryPF().trim() != null && !pfemployeevo.getFormVoluntaryPF().isEmpty()
//						&& pfemployeevo.getFormVoluntaryPF() != "") {
//					pfHistory.setPfEmployee(pfemployee);
//					pfHistory.setChangedEntity(BenefitsConstants.PF_CHANGED_ENTITY_VOLAMOUNT);
//					pfHistory.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_SUBMITTED);
//					pfHistory.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//					pfHistory.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//					pfHistory.setFiscalYear(appContext.getCurrentFiscalYear());
//
//					pfEmployeeService.saveHistory(pfHistory);
//				}
//
//			} else {
//
//				System.out.println("--------SLLLLLLLLLAAAAABBBBBBBBBBBB----- : " + pfemployeevo.getOptedSlab());
//				pfemployee.setOptedSlab(pfemployeevo.getOptedSlab());
//				pfemployee.setEmployee(appContext.getCurrentEmployee());
//				pfemployee.setFormEmpName(pfemployeevo.getFormEmpName());
//				pfemployee.setFormDOB(DataTypeUtil.toDateFromStringddMMMMMyyyy(formDate));
//				pfemployee.setFormGender(pfemployeevo.getFormGender());
//				pfemployee.setFormMaritalStatus(pfemployeevo.getFormMaritalStatus());
//				pfemployee.setFormGuardianName(pfemployeevo.getFormGuardianName());
//				pfemployee.setFormMobile(pfemployeevo.getFormMobile());
//				pfemployee.setFormEmail(pfemployeevo.getFormEmail());
//				pfemployee.setFormPan(pfemployeevo.getFormPan());
//				pfemployee.setFormAadharNo(pfemployeevo.getFormAadharNo());
//				pfemployee.setFormCurrentAddress(pfemployeevo.getFormCurrentAddress());
//				pfemployee.setFormPermanentAddress(pfemployeevo.getFormPermanentAddress());
//				pfemployee.setFormPrevEstablishment(pfemployeevo.getFormPrevEstablishment());
//				pfemployee.setFormPrevPfAccNo(pfemployeevo.getFormPrevPfAccNo());
//				pfemployee.setFormPrevDOJ(DataTypeUtil.toDateFromStringddMMMMMyyyy(formPrevDoj));
//				pfemployee.setFormPrevDOL(DataTypeUtil.toDateFromStringddMMMMMyyyy(formPrevDol));
//				// pfemployee.setUan(pfemployeevo.getUan());
//				if (pfemployeevo.getUan() == null || pfemployeevo.getUan() == "" || pfemployeevo.getUan().isEmpty()) {
//					pfemployee.setUan(empResponse.getUan());
//				} else {
//					pfemployee.setUan(pfemployeevo.getUan());
//				}
//				pfemployee.setAgreeTermsConditions(pfemployeevo.getAgreeTermsConditions());
//				pfemployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_SUBMITTED);
//
//				// here check whether the date opted is before or after 21st of the
//				// opted month
//
//				BenefitsProperty property = settingsService.getPropertyByCode(BenefitsConstants.PF_ENROLL_EFF_DAY);
//				Integer cutoffDate = Integer.parseInt(property.getPropertyValue());
//
//				Date currDate = new Date();
//
//				if (currDate.getDate() > cutoffDate) {
//					currDate.setDate(1);
//					currDate.setMonth(currDate.getMonth() + 1);
//					System.out.println("**********Customized Date***********" + currDate);
//					pfemployee.setEffFrom(currDate);
//
//				} else {
//					pfemployee.setEffFrom(auditDate);
//				}
//				pfemployee.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//				pfemployee.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//				pfemployee.setFormVoluntaryPF(pfemployeevo.getFormVoluntaryPF());
//				pfemployee.setDocmanUUId(pfemployeevo.getDocmanUUId());
//
//				PFEmployeeSlabHistory pfHistory = new PFEmployeeSlabHistory();
//
//				pfHistory.setPfEmployee(pfemployee);
//				pfHistory.setChangedEntity(BenefitsConstants.PF_CHANGED_ENTITY_INITIAL_SUBMISSION);
//				pfHistory.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_SUBMITTED);
//				pfHistory.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//				pfHistory.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//				pfHistory.setFiscalYear(appContext.getCurrentFiscalYear());
//
//				if (pfEmployeeService.update(pfemployee)) {
//
//					pfEmployeeService.saveHistory(pfHistory);
//					// Sending Email
//					EmailService emailService = null;
//					String messageSubject = "PF request enrolled";
//					String messageBody = EmailFormatter.pfEnrolled(pfemployee);
//					String ccPFEmployee = EmailProperties.getProperty(BenefitsConstants.PROP_PF_ENROLLED_CC_EMAIL);
//
//					emailService = new EmailService(pfemployee.getEmployee().getEmail(), ccPFEmployee, messageBody,
//							messageSubject);
//					Thread emailThread = new Thread(emailService);
//					emailThread.start();
//
//					mav.addObject("successMessage", "Successfully Updated PF Form");
//
//				} else {
//					mav.addObject("errorMessage", "Failed to Update PF Form. Please try again!");
//				}
//
//				// double entry when vol amount submission - at the time of
//				// enrollment
//
//				System.out.println("----------- FORM VOL AMOUNT ------" + pfemployeevo.getFormVoluntaryPF());
//
//				if (!empResponse.getAvailableSlab().equals("mandatory")) {
//					if (pfemployeevo.getFormVoluntaryPF().trim() != null && !pfemployeevo.getFormVoluntaryPF().isEmpty()
//							&& pfemployeevo.getFormVoluntaryPF() != "") {
//						pfHistory.setPfEmployee(pfemployee);
//						pfHistory.setChangedEntity(BenefitsConstants.PF_CHANGED_ENTITY_VOLAMOUNT);
//						pfHistory.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_SUBMITTED);
//						pfHistory.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//						pfHistory.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//						pfHistory.setFiscalYear(appContext.getCurrentFiscalYear());
//
//						pfEmployeeService.saveHistory(pfHistory);
//					}
//				}
//
//			}
//			mav.addObject("planSlab", empResponse.getAvailableSlab());
//			return mav;
//
//		}
//
//		@RequestMapping(value = "/home/controlPanel/nps/search", method = RequestMethod.GET)
//		public ModelAndView viewPFEnrolledEmployees(HttpServletRequest request, HttpServletResponse response) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewPFEnrolledEmployees");
//
//			List<PFEmployee> pfEmployees = pfEmployeeService.listAll();
//			System.out.println("******PF EMPLOYEE*********" + pfEmployees);
//			mav.addObject("pfEmployees", pfEmployees);
//
//			return mav;
//		}
//
//		@RequestMapping(value = "/home/controlPanel/nps/search/approvalView/{pfEmployeeId}", method = RequestMethod.GET)
//		public ModelAndView approveEmployeePF(HttpServletRequest request, HttpServletResponse response,
//				@PathVariable(value = "pfEmployeeId") Integer pfEmployeeId) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "approvePF");
//
//			PFEmployee pfEmployee = pfEmployeeService.getById(pfEmployeeId);
//
//			PFEmployeeVO pfEmployeeVO = new PFEmployeeVO();
//			pfEmployeeVO
//					.setUploadUrl(docmanRestClient.getUploadUrl(pfEmployee.getDocmanUUId(), appContext.getUserLoginKey()));
//			pfEmployeeVO.setDownloadUrl(
//					docmanRestClient.getDownloadUrl(pfEmployee.getDocmanUUId(), appContext.getUserLoginKey()));
//			mav.addObject("pfEmployee", pfEmployee);
//			mav.addObject("document", pfEmployeeVO);
//			return mav;
//		}
//
//		@RequestMapping(value = "/home/controlPanel/nps/search/viewDetails/{pfEmployeeId}", method = RequestMethod.GET)
//		public ModelAndView viewPFEmployeeDetails(HttpServletRequest request, HttpServletResponse response,
//				@PathVariable(value = "pfEmployeeId") Integer pfEmployeeId) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewPFEmployeeDetails");
//
//			PFEmployee pfEmployee = pfEmployeeService.getById(pfEmployeeId);
//			PFEmployeeVO pfEmployeeVO = new PFEmployeeVO();
//			pfEmployeeVO
//					.setUploadUrl(docmanRestClient.getUploadUrl(pfEmployee.getDocmanUUId(), appContext.getUserLoginKey()));
//			pfEmployeeVO.setDownloadUrl(
//					docmanRestClient.getDownloadUrl(pfEmployee.getDocmanUUId(), appContext.getUserLoginKey()));
//			mav.addObject("pfEmployee", pfEmployee);
//			mav.addObject("document", pfEmployeeVO);
//			return mav;
//		}
//
//		/*
//		 * @RequestMapping(value =
//		 * "/home/controlPanel/pf/search/approve/{pfEmployeeId}", method =
//		 * RequestMethod.GET) public ModelAndView
//		 * pfEmployeeApproval(HttpServletRequest request, HttpServletResponse
//		 * response,
//		 * 
//		 * @PathVariable(value = "pfEmployeeId") Integer pfEmployeeId) { appContext
//		 * = (AppContext) request.getSession().getAttribute( "appContext");
//		 * ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
//		 * "redirect:/home/controlPanel/pf/search");
//		 * 
//		 * PFEmployee pfEmployee = pfEmployeeService.getById(pfEmployeeId);
//		 * pfEmployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED);
//		 * pfEmployeeService.update(pfEmployee);
//		 * 
//		 * Date auditDate = new Date();
//		 * 
//		 * PFEmployeeSlabHistory pfemployeeHistory = new PFEmployeeSlabHistory();
//		 * 
//		 * //------------------ Changes required
//		 * 
//		 * pfemployeeHistory.setPfEmployee(pfEmployee);
//		 * pfemployeeHistory.setChangedEntity(BenefitsConstants.
//		 * PF_CHANGED_ENTITY_INITIAL_SUBMISSION);
//		 * pfemployeeHistory.setCreatedBy(appContext.getCurrentEmployee().
//		 * getUserName()); pfemployeeHistory.setStatus(BenefitsConstants.
//		 * PF_EMPLOYEE_STATUS_HR_APPROVED);
//		 * pfemployeeHistory.setUpdatedBy(appContext.getCurrentEmployee().
//		 * getUserName());
//		 * 
//		 * pfEmployeeService.saveHistory(pfemployeeHistory);
//		 * 
//		 * 
//		 * // Sending Email
//		 * 
//		 * EmailService emailService = null; String messageSubject =
//		 * "PF Request Approved by HR"; String messageBody =
//		 * EmailFormatter.pfEnrollmentApproved(pfEmployee); String ccPFEmployee =
//		 * EmailProperties
//		 * .getProperty(BenefitsConstants.PROP_PF_ENROLLED_CC_EMAIL);
//		 * 
//		 * emailService = new EmailService(pfEmployee.getEmployee().getEmail(),
//		 * ccPFEmployee, messageBody, messageSubject); System.out
//		 * .println("+++++++++++++++++Email Sending Started+++++++++++++++");
//		 * 
//		 * Thread emailThread = new Thread(emailService); emailThread.start();
//		 * 
//		 * return mav; }
//		 */
//
//		/*
//		 * @RequestMapping(value =
//		 * "/home/controlPanel/pf/search/reject/{pfEmployeeId}", method =
//		 * RequestMethod.GET) public ModelAndView
//		 * pfEmployeeReject(HttpServletRequest request, HttpServletResponse
//		 * response,
//		 * 
//		 * @PathVariable(value = "pfEmployeeId") Integer pfEmployeeId) { appContext
//		 * = (AppContext) request.getSession().getAttribute( "appContext");
//		 * ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
//		 * "redirect:/home/controlPanel/pf/search");
//		 * 
//		 * PFEmployee pfEmployee = pfEmployeeService.getById(pfEmployeeId);
//		 * pfEmployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_REJECTED);
//		 * if(pfEmployeeService.update(pfEmployee)) { EmailService emailService =
//		 * null; String messageSubject = "PF Request Rejected by HR"; String
//		 * messageBody = EmailFormatter.pfEnrollmentReject(pfEmployee); String
//		 * ccPFEmployee = EmailProperties
//		 * .getProperty(BenefitsConstants.PROP_PF_ENROLLED_CC_EMAIL);
//		 * 
//		 * emailService = new EmailService(pfEmployee.getEmployee().getEmail(),
//		 * ccPFEmployee, messageBody, messageSubject); System.out
//		 * .println("+++++++++++++++++Email Sending Started+++++++++++++++");
//		 * 
//		 * Thread emailThread = new Thread(emailService); emailThread.start(); }
//		 * 
//		 * return mav; }
//		 */
//
//		@RequestMapping(value = "/home/controlPanel/nps/search/approveSelected")
//		public ModelAndView approveSelectedPF(HttpServletRequest request, HttpServletResponse response) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "redirect:/home/controlPanel/pf/search");
//
//			PFEmployeeResponse empResponse = employeeAvailableSlabs(appContext.getEmpId());
//
//			String selectedIDs = request.getParameter("approveSelected");
//			System.out.println("++++++++++++++++++++++++++++++" + selectedIDs + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//			String[] empIDs = selectedIDs.split(",");
//
//			Integer pfEmpID = Integer.parseInt(empIDs[0]);
//
//			for (String ids : empIDs) {
//				Integer pfEmpIDs = Integer.parseInt(ids);
//
//				PFEmployee pfEmployee = pfEmployeeService.getById(pfEmpIDs);
//
//				pfEmployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED);
//				System.out.println("----------------------update begins ----------------" + pfEmployee.getStatus());
//				pfEmployeeService.update(pfEmployee);
//				System.out.println("----------------------update end ----------------");
//
//				Date auditDate = new Date();
//
//				PFEmployeeSlabHistory pfemployeeHistory = new PFEmployeeSlabHistory();
//
//				// ------------------ Changes required
//
//				pfemployeeHistory.setPfEmployee(pfEmployee);
//				pfemployeeHistory.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//				pfemployeeHistory.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED);
//				pfemployeeHistory.setChangedEntity(BenefitsConstants.PF_CHANGED_ENTITY_INITIAL_SUBMISSION);
//				pfemployeeHistory.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//				pfemployeeHistory.setFiscalYear(appContext.getCurrentFiscalYear());
//
//				// pfEmployeeService.saveHistory(pfemployeeHistory);
//
//				// Sending Email
//
//				// pfEmployee.setFormVoluntaryPF(pfemployeevo.getFormVoluntaryPF());
//
//				/*
//				 * PFEmployeeSlabHistory pfHistory = new PFEmployeeSlabHistory();
//				 * 
//				 * pfHistory.setPfEmployee(pfEmployee);
//				 * pfHistory.setChangedEntity(BenefitsConstants.
//				 * PF_CHANGED_ENTITY_INITIAL_SUBMISSION);
//				 * pfHistory.setStatus(BenefitsConstants.
//				 * PF_EMPLOYEE_STATUS_SUBMITTED);
//				 * pfHistory.setCreatedBy(appContext.getCurrentEmployee()
//				 * .getUserName());
//				 * pfHistory.setUpdatedBy(appContext.getCurrentEmployee()
//				 * .getUserName());
//				 */
//
//				// Sending Email
//
//				if (pfEmployeeService.update(pfEmployee)) {
//
//					pfEmployeeService.saveHistory(pfemployeeHistory);
//					// Sending Email
//
//					EmailService emailService = null;
//					String messageSubject = "PF request enrolled";
//					String messageBody = EmailFormatter.pfEnrolled(pfEmployee);
//					String ccPFEmployee = EmailProperties.getProperty(BenefitsConstants.PROP_HR_EMAIL);
//
//					emailService = new EmailService(pfEmployee.getEmployee().getEmail(), ccPFEmployee, messageBody,
//							messageSubject);
//					Thread emailThread = new Thread(emailService);
//					emailThread.start();
//
//					mav.addObject("successMessage", "Successfully Updated PF Form");
//
//				} else {
//					mav.addObject("errorMessage", "Failed to Update PF Form. Please try again!");
//				}
//			}
//			mav.addObject("planSlab", empResponse.getAvailableSlab());
//			return mav;
//
//		}
//
//		/*
//		 * @RequestMapping(value = "/home/controlPanel/pf/search", method =
//		 * RequestMethod.GET) public ModelAndView
//		 * viewPFEnrolledEmployees(HttpServletRequest request, HttpServletResponse
//		 * response) { appContext = (AppContext) request.getSession().getAttribute(
//		 * "appContext"); ModelAndView mav =
//		 * AuthorizationUtil.authorizeAdmin(appContext, "viewPFEnrolledEmployees");
//		 * 
//		 * List<PFEmployee> pfEmployees = pfEmployeeService.listAll();
//		 * System.out.println("******PF EMPLOYEE*********" + pfEmployees);
//		 * mav.addObject("pfEmployees", pfEmployees);
//		 * 
//		 * return mav; }
//		 */
//		/*
//		 * @RequestMapping(value =
//		 * "/home/controlPanel/pf/search/approvalView/{pfEmployeeId}", method =
//		 * RequestMethod.GET) public ModelAndView
//		 * approveEmployeePF(HttpServletRequest request, HttpServletResponse
//		 * response,
//		 * 
//		 * @PathVariable(value = "pfEmployeeId") Integer pfEmployeeId) { appContext
//		 * = (AppContext) request.getSession().getAttribute( "appContext");
//		 * ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
//		 * "approvePF");
//		 * 
//		 * PFEmployee pfEmployee = pfEmployeeService.getById(pfEmployeeId);
//		 * mav.addObject("pfEmployee", pfEmployee); return mav; }
//		 */
//
//		/*
//		 * @RequestMapping(value =
//		 * "/home/controlPanel/pf/search/viewDetails/{pfEmployeeId}", method =
//		 * RequestMethod.GET) public ModelAndView
//		 * viewPFEmployeeDetails(HttpServletRequest request, HttpServletResponse
//		 * response,
//		 * 
//		 * @PathVariable(value = "pfEmployeeId") Integer pfEmployeeId) { appContext
//		 * = (AppContext) request.getSession().getAttribute( "appContext");
//		 * ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
//		 * "viewPFEmployeeDetails");
//		 * 
//		 * PFEmployee pfEmployee = pfEmployeeService.getById(pfEmployeeId);
//		 * mav.addObject("pfEmployee", pfEmployee); return mav; }
//		 */
//
//		@RequestMapping(value = "/home/controlPanel/nps/search/approve/{pfEmployeeId}", method = RequestMethod.GET)
//		public ModelAndView pfEmployeeApproval(HttpServletRequest request, HttpServletResponse response,
//				@PathVariable(value = "pfEmployeeId") Integer pfEmployeeId) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "redirect:/home/controlPanel/pf/search");
//			try{
//			PFEmployee pfEmployee = pfEmployeeService.getById(pfEmployeeId);
//			pfEmployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED);
//			pfEmployeeService.update(pfEmployee);
//
//			Date auditDate = new Date();
//
//			PFEmployeeSlabHistory pfemployeeHistory = new PFEmployeeSlabHistory();
//
//			// ------------------ Changes required
//			
//			pfemployeeHistory.setPfEmployee(pfEmployee);
//
//			pfemployeeHistory.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//			// pfemployeeHistory.setEffFrom(auditDate);
//			// pfemployeeHistory.setSlabTo(pfEmployee.getOptedSlab());
//			pfemployeeHistory.setStatus(pfEmployee.getStatus());
//
//			pfemployeeHistory.setChangedEntity(BenefitsConstants.PF_CHANGED_ENTITY_INITIAL_SUBMISSION);
//			pfemployeeHistory.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//			pfemployeeHistory.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED);
//
//			pfemployeeHistory.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//			pfemployeeHistory.setFiscalYear(appContext.getCurrentFiscalYear());
//			pfEmployeeService.saveHistory(pfemployeeHistory);
//			
//			// Sending Email
//
//			EmailService emailService = null;
//			String messageSubject = "PF Request Approved by HR";
//			String messageBody = EmailFormatter.pfEnrollmentApproved(pfEmployee);
//			String ccPFEmployee = EmailProperties.getProperty(BenefitsConstants.PROP_HR_EMAIL);
//
//			emailService = new EmailService(pfEmployee.getEmployee().getEmail(), ccPFEmployee, messageBody, messageSubject);
//			System.out.println("+++++++++++++++++Email Sending Started+++++++++++++++");
//
//			Thread emailThread = new Thread(emailService);
//			emailThread.start();
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//			return mav;
//		}
//
//		@RequestMapping(value = "/home/controlPanel/nps/search/reject/{pfEmployeeId}", method = RequestMethod.GET)
//		public ModelAndView pfEmployeeReject(HttpServletRequest request, HttpServletResponse response,
//				@PathVariable(value = "pfEmployeeId") Integer pfEmployeeId) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "redirect:/home/controlPanel/pf/search");
//
//			PFEmployee pfEmployee = pfEmployeeService.getById(pfEmployeeId);
//			pfEmployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_REJECTED);
//			if (pfEmployeeService.update(pfEmployee)) {
//				EmailService emailService = null;
//				String messageSubject = "PF Request Rejected by HR";
//				String messageBody = EmailFormatter.pfEnrollmentReject(pfEmployee);
//				String ccPFEmployee = EmailProperties.getProperty(BenefitsConstants.PROP_PF_ENROLLED_CC_EMAIL);
//
//				emailService = new EmailService(pfEmployee.getEmployee().getEmail(), ccPFEmployee, messageBody,
//						messageSubject);
//				System.out.println("+++++++++++++++++Email Sending Started+++++++++++++++");
//
//				Thread emailThread = new Thread(emailService);
//				emailThread.start();
//			}
//
//			return mav;
//		}
//
//		/*
//		 * @RequestMapping(value = "/home/controlPanel/pf/search/approveSelected")
//		 * public ModelAndView approveSelectedPF(HttpServletRequest request,
//		 * HttpServletResponse response) { appContext = (AppContext)
//		 * request.getSession().getAttribute( "appContext"); ModelAndView mav =
//		 * AuthorizationUtil.authorizeAdmin(appContext,
//		 * "redirect:/home/controlPanel/pf/search");
//		 * 
//		 * String selectedIDs = request.getParameter("approveSelected");
//		 * System.out.println("++++++++++++++++++++++++++++++" + selectedIDs +
//		 * "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"); String[] empIDs =
//		 * selectedIDs.split(",");
//		 * 
//		 * Integer pfEmpID = Integer.parseInt(empIDs[0]);
//		 * 
//		 * for (String ids : empIDs) { Integer pfEmpIDs = Integer.parseInt(ids);
//		 * 
//		 * PFEmployee pfEmployee = pfEmployeeService.getById(pfEmpIDs);
//		 * 
//		 * pfEmployee .setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED);
//		 * System.out
//		 * .println("----------------------update begins ----------------" +
//		 * pfEmployee.getStatus()); pfEmployeeService.update(pfEmployee); System.out
//		 * .println("----------------------update end ----------------");
//		 * 
//		 * Date auditDate = new Date();
//		 * 
//		 * PFEmployeeSlabHistory pfemployeeHistory = new PFEmployeeSlabHistory();
//		 * 
//		 * //------------------ Changes required
//		 * 
//		 * pfemployeeHistory.setPfEmployee(pfEmployee);
//		 * 
//		 * pfemployeeHistory.setCreatedBy(appContext.getCurrentEmployee()
//		 * .getUserName()); // pfemployeeHistory.setEffFrom(auditDate); //
//		 * pfemployeeHistory.setSlabTo(pfEmployee.getOptedSlab());
//		 * pfemployeeHistory.setStatus(pfEmployee.getStatus());
//		 * 
//		 * pfemployeeHistory.setCreatedBy(appContext.getCurrentEmployee().
//		 * getUserName()); pfemployeeHistory.setStatus(BenefitsConstants.
//		 * PF_EMPLOYEE_STATUS_HR_APPROVED);
//		 * pfemployeeHistory.setChangedEntity(BenefitsConstants.
//		 * PF_CHANGED_ENTITY_INITIAL_SUBMISSION);
//		 * 
//		 * pfemployeeHistory.setUpdatedBy(appContext.getCurrentEmployee()
//		 * .getUserName());
//		 * 
//		 * pfEmployeeService.saveHistory(pfemployeeHistory);
//		 * 
//		 * // Sending Email
//		 * 
//		 * EmailService emailService = null; String messageSubject =
//		 * "PF Request Approved by HR"; String messageBody = EmailFormatter
//		 * .pfEnrollmentApproved(pfEmployee); String ccPFEmployee = EmailProperties
//		 * .getProperty(BenefitsConstants.PROP_PF_ENROLLED_CC_EMAIL); emailService =
//		 * new EmailService( pfEmployee.getEmployee().getEmail(), ccPFEmployee,
//		 * messageBody, messageSubject); System.out
//		 * .println("+++++++++++++++++Email Sending Started+++++++++++++++");
//		 * 
//		 * Thread emailThread = new Thread(emailService); emailThread.start();
//		 * 
//		 * } return mav; }
//		 */
//
//		/*
//		 * 
//		 * Slab Search - Admin View
//		 */
//
//		@RequestMapping(value = "/home/controlPanel/nps/changeSlabSearch", method = RequestMethod.GET)
//		public ModelAndView viewPFEnrolledEmployeesChangeSlab(HttpServletRequest request, HttpServletResponse response) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewPFSlabChangeEmployees");
//
//			List<PFEmployee> pfEmployees = pfEmployeeService.listAllPfEmployeesSlabChangeRequested();
//			System.out.println("******PF EMPLOYEE*********" + pfEmployees);
//			mav.addObject("pfEmployees", pfEmployees);
//
//			return mav;
//		}
//
//		@RequestMapping(value = "/home/controlPanel/nps/changeSlabSearch/approvalView/{pfEmployeeId}", method = RequestMethod.GET)
//		public ModelAndView approvalViewEmployeePFSlabChange(HttpServletRequest request, HttpServletResponse response,
//				@PathVariable(value = "pfEmployeeId") Integer pfEmployeeId) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "approveSlabChange");
//
//			PFEmployee pfEmployee = pfEmployeeService.getById(pfEmployeeId);
//			List<PFEmployeeSlabHistory> pfemePfEmployeeSlabHistories = pfEmployeeService
//					.listSlabChangeByPFEmpId(pfEmployeeId);
//
//			mav.addObject("slabHistory", pfemePfEmployeeSlabHistories);
//			mav.addObject("pfEmployee", pfEmployee);
//			return mav;
//		}
//
//		@RequestMapping(value = "/home/controlPanel/nps/changeSlabSearch/approve/{pfEmployeeId}", method = RequestMethod.GET)
//		public ModelAndView pfEmployeeSlabChangeApproval(HttpServletRequest request, HttpServletResponse response,
//				@PathVariable(value = "pfEmployeeId") Integer pfEmployeeId) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
//					"redirect:/home/controlPanel/pf/changeSlabSearch");
//
//			PFEmployee pfEmployee = pfEmployeeService.getById(pfEmployeeId);
//			pfEmployee.setOptedSlab(BenefitsConstants.PF_EMPLOYEE_SLAB_VARIABLE);
//			pfEmployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED);
//			pfEmployeeService.update(pfEmployee);
//
//			Date auditDate = new Date();
//
//			PFEmployeeSlabHistory pfemployeeHistory = new PFEmployeeSlabHistory();
//
//			// ------------------ Changes required
//
//			pfemployeeHistory.setPfEmployee(pfEmployee);
//
//			pfemployeeHistory.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//			// pfemployeeHistory.setEffFrom(auditDate);
//			// pfemployeeHistory.setSlabTo(pfEmployee.getOptedSlab());
//			// pfemployeeHistory.setSlabFrom(BenefitsConstants.PF_EMPLOYEE_SLAB_FIXED);
//			pfemployeeHistory.setStatus(pfEmployee.getStatus());
//
//			pfemployeeHistory.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//			pfemployeeHistory.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED);
//
//			pfemployeeHistory.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//
//			pfemployeeHistory.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//			pfemployeeHistory.setChangedEntity(BenefitsConstants.PF_CHANGED_ENTITY_SLAB);
//			pfemployeeHistory.setEntityFrom(BenefitsConstants.PF_CHANGED_ENTITY_SLAB_FROM);
//			pfemployeeHistory.setEntityTo(BenefitsConstants.PF_CHANGED_ENTITY_SLAB_TO);
//			pfemployeeHistory.setFiscalYear(appContext.getCurrentFiscalYear());
//
//			pfEmployeeService.saveHistory(pfemployeeHistory);
//
//			// Sending Email
//
//			EmailService emailService = null;
//			String messageSubject = "PF Slab Change Request Approved by HR";
//			String messageBody = EmailFormatter.pfEnrolledUpdate(pfEmployee, pfemployeeHistory.getEntityFrom());
//			String ccPFEmployee = EmailProperties.getProperty(BenefitsConstants.PROP_HR_EMAIL);
//			emailService = new EmailService(pfEmployee.getEmployee().getEmail(), ccPFEmployee, messageBody, messageSubject);
//			System.out.println("+++++++++++++++++Email Sending Started+++++++++++++++");
//
//			Thread emailThread = new Thread(emailService);
//			emailThread.start();
//
//			return mav;
//		}
//
//		@RequestMapping(value = "/home/controlPanel/nps/changeSlabSearch/reject/{pfEmployeeId}", method = RequestMethod.GET)
//		public ModelAndView pfEmployeeSlabChangeReject(HttpServletRequest request, HttpServletResponse response,
//				@PathVariable(value = "pfEmployeeId") Integer pfEmployeeId) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
//					"redirect:/home/controlPanel/pf/changeSlabSearch");
//
//			PFEmployee pfEmployee = pfEmployeeService.getById(pfEmployeeId);
//			pfEmployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_REJECTED);
//
//			if (pfEmployeeService.update(pfEmployee)) {
//				EmailService emailService = null;
//				String messageSubject = "PF Slab Change Request Rejected by HR";
//				String messageBody = EmailFormatter.pfEnrollmentReject(pfEmployee);
//				String ccPFEmployee = EmailProperties.getProperty(BenefitsConstants.PROP_PF_ENROLLED_CC_EMAIL);
//
//				emailService = new EmailService(pfEmployee.getEmployee().getEmail(), ccPFEmployee, messageBody,
//						messageSubject);
//				System.out.println("+++++++++++++++++Email Sending Started+++++++++++++++");
//
//				Thread emailThread = new Thread(emailService);
//				emailThread.start();
//			}
//			return mav;
//		}
//
//		@RequestMapping(value = "/home/controlPanel/nps/changeSlabSearch/approveSelected")
//		public ModelAndView approveSelectedPFSlabChangeRequested(HttpServletRequest request, HttpServletResponse response) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext,
//					"redirect:/home/controlPanel/pf/changeSlabSearch");
//
//			String selectedIDs = request.getParameter("approveSelected");
//			System.out.println("++++++++++++++++++++++++++++++" + selectedIDs + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//			String[] empIDs = selectedIDs.split(",");
//
//			Integer pfEmpID = Integer.parseInt(empIDs[0]);
//
//			for (String ids : empIDs) {
//				Integer pfEmpIDs = Integer.parseInt(ids);
//
//				PFEmployee pfEmployee = pfEmployeeService.getById(pfEmpIDs);
//				pfEmployee.setOptedSlab(BenefitsConstants.PF_EMPLOYEE_SLAB_VARIABLE);
//				pfEmployee.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED);
//				System.out.println("----------------------update begins ----------------" + pfEmployee.getStatus());
//				pfEmployeeService.update(pfEmployee);
//				System.out.println("----------------------update end ----------------");
//
//				Date auditDate = new Date();
//
//				PFEmployeeSlabHistory pfemployeeHistory = new PFEmployeeSlabHistory();
//
//				// ------------------ Changes required
//
//				pfemployeeHistory.setPfEmployee(pfEmployee);
//
//				pfemployeeHistory.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//				// pfemployeeHistory.setEffFrom(auditDate);
//				// pfemployeeHistory.setSlabTo(pfEmployee.getOptedSlab());
//				// pfemployeeHistory.setSlabFrom(BenefitsConstants.PF_EMPLOYEE_SLAB_FIXED);
//				pfemployeeHistory.setStatus(pfEmployee.getStatus());
//
//				pfemployeeHistory.setCreatedBy(appContext.getCurrentEmployee().getUserName());
//				pfemployeeHistory.setStatus(BenefitsConstants.PF_EMPLOYEE_STATUS_HR_APPROVED);
//
//				pfemployeeHistory.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//				pfemployeeHistory.setFiscalYear(appContext.getCurrentFiscalYear());
//
//				pfemployeeHistory.setUpdatedBy(appContext.getCurrentEmployee().getUserName());
//				pfemployeeHistory.setChangedEntity(BenefitsConstants.PF_CHANGED_ENTITY_SLAB);
//				pfemployeeHistory.setEntityFrom(BenefitsConstants.PF_CHANGED_ENTITY_SLAB_FROM);
//				pfemployeeHistory.setEntityTo(BenefitsConstants.PF_CHANGED_ENTITY_SLAB_TO);
//
//				pfEmployeeService.saveHistory(pfemployeeHistory);
//
//				// Sending Email
//
//				EmailService emailService = null;
//				String messageSubject = "PF Slab Request Approved by HR";
//				String messageBody = EmailFormatter.pfEnrollmentApproved(pfEmployee);
//				String ccPFEmployee = EmailProperties.getProperty(BenefitsConstants.PROP_HR_EMAIL);
//				emailService = new EmailService(pfEmployee.getEmployee().getEmail(), ccPFEmployee, messageBody,
//						messageSubject);
//				System.out.println("+++++++++++++++++Email Sending Started+++++++++++++++");
//
//				Thread emailThread = new Thread(emailService);
//				emailThread.start();
//
//			}
//			return mav;
//		}
//
//		@RequestMapping(value = "/home/controlPanel/nps/changeSlabSearch/viewSlabDetails/{pfEmployeeId}", method = RequestMethod.GET)
//		public ModelAndView viewPFEmployeeSlabChangeDetails(HttpServletRequest request, HttpServletResponse response,
//				@PathVariable(value = "pfEmployeeId") Integer pfEmployeeId) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewSlabChangeDetails");
//
//			PFEmployee pfEmployee = pfEmployeeService.getById(pfEmployeeId);
//			List<PFEmployeeSlabHistory> pfemePfEmployeeSlabHistories = pfEmployeeService
//					.listSlabChangeByPFEmpId(pfEmployeeId);
//
//			mav.addObject("pfEmployee", pfEmployee);
//			mav.addObject("slabHistory", pfemePfEmployeeSlabHistories);
//			return mav;
//		}
//
//		@RequestMapping(value = "/home/controlPanel/nps/searchMonth", method = RequestMethod.GET)
//		public ModelAndView viewMonthlyReport(HttpServletRequest request, HttpServletResponse response) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "pfMonthlyReport");
//			return mav;
//		}
//
//		@RequestMapping(value = "home/controlPanel/nps/pfMonthlyEmployeeList", method = RequestMethod.POST)
//		public ModelAndView viewMonthlyReportSearch(HttpServletRequest request, HttpServletResponse response) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "pfMonthlyReport");
//
//			String monthValue = request.getParameter("pfEmployeeMonth");
//			String yearValue = request.getParameter("pfEmployeeYear");
//			// Integer month = Integer.parseInt(monthValue);
//
//			List<PFEmployee> pfMonthlyList = pfEmployeeService.listAllMonthlyEmp(monthValue, yearValue);
//
//			mav.addObject("pfMonthlyList", pfMonthlyList);
//
//			return mav;
//		}
//
//		@RequestMapping(value = "/home/controlPanel/nps/pfReport")
//		public ModelAndView viewPFEmployeeReport(HttpServletRequest request, HttpServletResponse response) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "pfEmployeeReport");
//
//			List<PFEmployee> pfEmployees = pfEmployeeService.listAll();
//
//			mav.addObject("pfEmployees", pfEmployees);
//
//			return mav;
//		}
//
//		private PFEmployeeResponse employeeAvailableSlabs(Integer empId) {
//
//			String uanNumber = "";
//			String planSlab = "";
//
//			try {
//
//				/**
//				 * WS request body/parameters
//				 */
//				GetX0020PFX0020DetailsX0020OfX0020TheX0020Employee pfRequest = new GetX0020PFX0020DetailsX0020OfX0020TheX0020Employee();
//				pfRequest.setEmployeeId(appContext.getCurrentEmployee().getEmployeeId());
//
//				/**
//				 * WS request header
//				 */
//				ServiceAuthenticationHeader authenticationHeader = new ObjectFactory().createServiceAuthenticationHeader();
//
//				/**
//				 * calling WS
//				 */
//				GetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResponse pfResponse = mirrorDataService
//						.getMirrorDataServiceSoap().getEmployeePFDetails( pfRequest, authenticationHeader);
//				pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult().setIsVariablePF(true);;
//				pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult().setIsMandatoryPF(false);
//				uanNumber = pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult().getUANNumber();
//
//				if (pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult().isIsMandatoryPF() == true
//						&& pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult()
//								.isIsVariablePF() == false
//						&& pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult().isIsFixedPF() == false
//
//				) {
//
//					// mav.addObject("planSlab", "mandatory");
//
//					planSlab = "mandatory";
//				}
//
//				else if (pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult().isIsMandatoryPF() == false
//						&& pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult()
//								.isIsVariablePF() == false
//						&& pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult().isIsFixedPF() == true
//
//				) {
//					planSlab = "fixedOnly";
//
//				}
//
//				else if (pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult().isIsMandatoryPF() == false
//						&& pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult().isIsVariablePF() == true
//						&& pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult().isIsFixedPF() == true
//
//				) {
//
//					planSlab = "fixedOrVariable";
//			} 
//				//else if (pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult().isIsPFNA() == true) {
////					planSlab = "notApplicable";
//	//
////				}
//
//				else if (pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult().isIsMandatoryPF() == false
//						&& pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult().isIsVariablePF() == true
//						&& pfResponse.getGetX0020PFX0020DetailsX0020OfX0020TheX0020EmployeeResult().isIsFixedPF() == false
//
//				) {
//
//					planSlab = "fixedOrVariable";
//			} 
//
//				else {
//					planSlab = "invalid";
//
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			PFEmployeeResponse pfEmployeeResponse = new PFEmployeeResponse();
//			pfEmployeeResponse.setAvailableSlab(planSlab);
//			pfEmployeeResponse.setEmpId(empId);
//			pfEmployeeResponse.setUan(uanNumber);
//
//			return pfEmployeeResponse;
//		}
//
//		/*
//		 * PF - Opted Out Employees List
//		 * 
//		 * 
//		 */
//		@RequestMapping(value = "/home/controlPanel/nps/optOutSearch", method = RequestMethod.GET)
//		public ModelAndView viewPFOptedOutEmployees(HttpServletRequest request, HttpServletResponse response) {
//			appContext = (AppContext) request.getSession().getAttribute("appContext");
//			ModelAndView mav = AuthorizationUtil.authorizeAdmin(appContext, "viewPFOptedOutEmployees");
//
//			List<PFDeniedEmployees> pfDeniedEmployees = pfEmployeeService.listAllDeniedEmployees();
//			System.out.println("******PF EMPLOYEE*********" + pfDeniedEmployees);
//			mav.addObject("pfDeniedEmployees", pfDeniedEmployees);
//
//			return mav;
//		}
//		
//
//
//		
//		
//		
//
//	}
//
//
