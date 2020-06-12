package com.speridian.benefits2.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.speridian.benefits2.beans.NPSEmployeeVO;
import com.speridian.benefits2.beans.PFEmployeeResponse;
import com.speridian.benefits2.beans.PFEmployeeVO;
import com.speridian.benefits2.email.EmailFormatter;
import com.speridian.benefits2.email.EmailProperties;
import com.speridian.benefits2.email.EmailService;
import com.speridian.benefits2.model.pojo.BenefitsProperty;
import com.speridian.benefits2.model.pojo.NPSDeniedEmployees;
import com.speridian.benefits2.model.pojo.NPSDenyReasonsMaster;
import com.speridian.benefits2.model.pojo.NPSEmployee;
import com.speridian.benefits2.model.pojo.PFDeniedEmployees;
import com.speridian.benefits2.model.pojo.PFDenyReasonsMaster;
import com.speridian.benefits2.model.pojo.PFEmployee;
import com.speridian.benefits2.model.pojo.PFEmployeeSlabHistory;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.service.NPSEmployeeService;
import com.speridian.benefits2.service.SettingsService;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.util.AuthorizationUtil;
import com.speridian.benefits2.util.DataTypeUtil;
import com.speridian.benefits2.ws.client.docman.rest.DocmanRestClient;
import com.speridian.benefits2.ws.client.mirror.soap.MirrorDataService;
import com.speridian.benefits2.ws.constants.BenefitsWSConstants;

@Controller
public class EmployeeNPSController {

	AppContext appContext;

	@Autowired
	NPSEmployeeService npsEmployeeService;

	@Autowired
	MirrorDataService mirrorDataService;

	@Autowired
	SettingsService settingsService;

	@Autowired
	DocmanRestClient docmanRestClient;

	/*
	 * Employee PF DashBoard
	 */
	@RequestMapping(value = "/home/myEnpsHome", method = RequestMethod.GET)
	public ModelAndView viewNpsDashBoard(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "employeeNPSHome");
		System.out.println("-----Inside My EPF----");
		NPSEmployee npSEmployee = npsEmployeeService.getByEmpId(appContext.getEmpId());
		// PFEmployee pFEmployee = null;
		//NPSEmployeeResponse empResponse = employeeAvailableSlabs(appContext.getEmpId());

		List<NPSDenyReasonsMaster> npsDenyReasonsMaster = npsEmployeeService.listAllDenyReasons();

		mav.addObject("optOutEnabled", false);
		if (npSEmployee == null) {
			/*
			 * 
			 * Here we will be checking if employee prefered opt out not
			 */
			NPSDeniedEmployees npsDeniedEmployees = npsEmployeeService
					.getDeniedEmployeesByFiscalYear(appContext.getCurrentFiscalYear(), appContext.getEmpId());

			if (npsDeniedEmployees == null) {
				mav.addObject("optOutEnabled", true);
			}

		
		
		 }
		return mav; 
		
	}

	/*
	 * 
	 * Save Opt out Reason and redirect to myEPF
	 */
	@RequestMapping(value = "/home/myEnpsHome/optOutnps", method = RequestMethod.POST)
	public ModelAndView optOutPF(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "redirect:/home/myEnpsHome");
		
			System.out.println("--- Save PF Deny Reason --- ");

			String reasonId = request.getParameter("reasonId");

			/*
			 * 
			 * new change
			 */

			NPSEmployee npsEmployee = npsEmployeeService.getByEmpId(appContext.getEmpId());
			/*
			 * List<PFEmployeeSlabHistory> pfEmployeeSlabHistory =
			 * pfEmployeeService
			 * .listSlabChangeByPFEmpId(pfEmployee.getPfEmployeeId());
			 */
            
            	if (npsEmployee.getStatus().equalsIgnoreCase(BenefitsConstants.NPS_EMPLOYEE_STATUS_SAVED)) {
    				// pfEmployeeService.delete(pfEmployeeSlabHistory);
    				npsEmployeeService.delete(npsEmployee);
    			}
           
			

			Date auditDate = new Date();

			NPSDeniedEmployees npsDeniedEmployees = new NPSDeniedEmployees();
			npsDeniedEmployees.setCreatedBy(appContext.getUserName());
			npsDeniedEmployees.setCreatedDate(auditDate);
			npsDeniedEmployees.setUpdatedBy(appContext.getUserName());
			npsDeniedEmployees.setUpdatedDate(auditDate);
			npsDeniedEmployees.setFiscalYear(appContext.getCurrentFiscalYear());

			if (reasonId.equals("0")) {
				String otherReasonDesc = request.getParameter("otherReason");
				npsDeniedEmployees.setOtherReasons(otherReasonDesc);
			} else {
				NPSDenyReasonsMaster denyReasonsMaster = new NPSDenyReasonsMaster();
				denyReasonsMaster.setReasonId(Integer.parseInt(reasonId));
				npsDeniedEmployees.setReasonId(denyReasonsMaster);
			}

		
		return mav;
	}

	

	


	

	

	
	/*
	 * 
	 * PF Enrolment - New Requirment - From dashboard - Form View
	 */
	@RequestMapping(value = "/home/npsEnrollment", method = RequestMethod.GET)
	public ModelAndView employeeNPSEnrollment(HttpServletRequest request, HttpServletResponse response) {

		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "employeeProvidentFundForm");

		NPSEmployee npsemployee = npsEmployeeService.getByEmpId(appContext.getEmpId());

		
		NPSEmployeeVO npsemployeevo = new NPSEmployeeVO();

		if (npsemployee == null) {
			npsemployeevo.setEmployee(appContext.getCurrentEmployee());
			npsemployeevo.setFormEmpName(appContext.getCurrentEmployee().getFirstName() + " "
					+ appContext.getCurrentEmployee().getLastName());
			npsemployeevo.setFormDOB(DataTypeUtil.toDateddMMMMMyyyy(appContext.getCurrentEmployee().getDateOfBirth()));
			npsemployeevo.setFormGender(appContext.getCurrentEmployee().getGender());
			npsemployeevo.setFormMobile(appContext.getCurrentEmployee().getMobileNo());
			npsemployeevo.setFormPan(appContext.getCurrentEmployee().getPan());
			npsemployeevo.setFormOffEmail(appContext.getCurrentEmployee().getEmail());
			
		} else {
			System.out.println("-------SLAB IN DB---- : " + npsemployee.getOptedSlab());
			npsemployeevo.setOptedSlab(npsemployee.getOptedSlab());
			npsemployeevo.setFormEmpName(npsemployee.getFormEmpName());

			if (npsemployee.getFormDOB() != null) {
				npsemployeevo.setFormDOB(DataTypeUtil.toDateddMMMMMyyyy(npsemployee.getFormDOB()));
			}
			npsemployeevo.setFormGender(npsemployee.getFormGender());
			npsemployeevo.setFormMaritalStatus(npsemployee.getFormMaritalStatus());
			npsemployeevo.setFormGuardianName(npsemployee.getFormGuardianName());
			npsemployeevo.setFormMobile(npsemployee.getFormMobile());
			npsemployeevo.setFormEmail(npsemployee.getFormEmail());
			npsemployeevo.setFormPan(npsemployee.getFormPan());
			npsemployeevo.setFormAadharNo(npsemployee.getFormAadharNo());
			npsemployeevo.setFormCurrentAddress(npsemployee.getFormCurrentAddress());
			npsemployeevo.setFormPermanentAddress(npsemployee.getFormPermanentAddress());
			npsemployeevo.setFormPrevEstablishment(npsemployee.getFormPrevEstablishment());
			npsemployeevo.setFormPrevnpsAccNo(npsemployee.getFormPrevnpsAccNo());

			if (npsemployee.getFormPrevDOJ() != null) {
				npsemployeevo.setFormPrevDOJ(DataTypeUtil.toDateddMMMMMyyyy(npsemployee.getFormPrevDOJ()));
			}
			if (npsemployee.getFormPrevDOL() != null) {
				npsemployeevo.setFormPrevDOL(DataTypeUtil.toDateddMMMMMyyyy(npsemployee.getFormPrevDOL()));
			}

			npsemployeevo.setUan(npsemployee.getUan());
			npsemployeevo.setActive(npsemployee.getActive());
			npsemployeevo.setStatus(npsemployee.getStatus());
			npsemployeevo.setAgreeTermsConditions(npsemployee.getAgreeTermsConditions());
			// pfemployeevo.setEffFrom(DataTypeUtil.toDateddMMMMMyyyy(pfemployee.getEffFrom()));
			npsemployeevo.setUpdatedBy(npsemployee.getUpdatedBy());
			npsemployeevo.setCreatedBy(npsemployee.getCreatedBy());

			npsemployeevo.setFormOffEmail(appContext.getCurrentEmployee().getEmail());
			npsemployeevo.setFormVoluntaryNPS(npsemployee.getFormVoluntaryNPS());
			
		}

		System.out.println("-------PF EMPLOYEE STATUS----------" + npsemployeevo.getStatus());

		if (npsemployeevo.getStatus() == null
				|| npsemployeevo.getStatus().equals(BenefitsConstants.NPS_EMPLOYEE_STATUS_SAVED)) {
			mav.addObject("enrollmentPossible", true);
		}
		npsemployeevo.setUploadUrl(
				docmanRestClient.getUploadUrl(npsemployeevo.getDocmanUUId(), appContext.getUserLoginKey()));
		npsemployeevo.setDownloadUrl(
				docmanRestClient.getDownloadUrl(npsemployeevo.getDocmanUUId(), appContext.getUserLoginKey()));

		mav.addObject("employeeNPSBean", npsemployeevo);
		

		return mav;
	}
	
	
	@RequestMapping(value = "/home/myEnpsHome/optOutNPS", method = RequestMethod.POST)
	public ModelAndView optOutNPS(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");

		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "redirect:/home/myEnpsHome");
		try {
			System.out.println("--- Save NPS Deny Reason --- ");

			String reasonId = request.getParameter("reasonId");

			/*
			 * 
			 * new change
			 */

			NPSEmployee npsEmployee = npsEmployeeService.getByEmpId(appContext.getEmpId());
			/*
			 * List<PFEmployeeSlabHistory> pfEmployeeSlabHistory =
			 * pfEmployeeService
			 * .listSlabChangeByPFEmpId(pfEmployee.getPfEmployeeId());
			 */
            try{
            	if (npsEmployee.getStatus().equalsIgnoreCase(BenefitsConstants.NPS_EMPLOYEE_STATUS_SAVED)) {
    				// pfEmployeeService.delete(pfEmployeeSlabHistory);
    				npsEmployeeService.delete(npsEmployee);
    			}
            }catch(Exception e){
            	e.printStackTrace();
            }
			

			Date auditDate = new Date();

			NPSDeniedEmployees npsDeniedEmployees = new NPSDeniedEmployees();
			npsDeniedEmployees.setCreatedBy(appContext.getUserName());
			npsDeniedEmployees.setCreatedDate(auditDate);
			npsDeniedEmployees.setUpdatedBy(appContext.getUserName());
			npsDeniedEmployees.setUpdatedDate(auditDate);
			npsDeniedEmployees.setFiscalYear(appContext.getCurrentFiscalYear());

			if (reasonId.equals("0")) {
				String otherReasonDesc = request.getParameter("otherReason");
				npsDeniedEmployees.setOtherReasons(otherReasonDesc);
			} else {
				NPSDenyReasonsMaster denyReasonsMaster = new NPSDenyReasonsMaster();
				denyReasonsMaster.setReasonId(Integer.parseInt(reasonId));
				npsDeniedEmployees.setReasonId(denyReasonsMaster);
			}
			npsDeniedEmployees.setEmployee(appContext.getCurrentEmployee());
			
			npsEmployeeService.saveDeniedEmployee(npsDeniedEmployees);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	
	/*
	 * 
	 * PF Enrolment - New Requirment - From dashboard - Form View
	 */
	@RequestMapping(value = "/home/npsEnrollmentt", method = RequestMethod.GET)
	public ModelAndView employeeNPSEnrollment1(HttpServletRequest request, HttpServletResponse response) {

		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "employeeNPSEnrollmentForn");

		NPSEmployee npsemployee = npsEmployeeService.getByEmpId(appContext.getEmpId());

		NPSEmployeeVO npsemployeevo = new NPSEmployeeVO();

		if (npsemployee == null) {
			npsemployeevo.setEmployee(appContext.getCurrentEmployee());
			npsemployeevo.setFormEmpName(appContext.getCurrentEmployee().getFirstName() + " "
					+ appContext.getCurrentEmployee().getLastName());
			npsemployeevo.setFormDOB(DataTypeUtil.toDateddMMMMMyyyy(appContext.getCurrentEmployee().getDateOfBirth()));
			npsemployeevo.setFormGender(appContext.getCurrentEmployee().getGender());
			npsemployeevo.setFormMobile(appContext.getCurrentEmployee().getMobileNo());
			npsemployeevo.setFormPan(appContext.getCurrentEmployee().getPan());
			npsemployeevo.setFormOffEmail(appContext.getCurrentEmployee().getEmail());
		

		} else {
			System.out.println("-------SLAB IN DB---- : " + npsemployee.getOptedSlab());
		
			npsemployeevo.setFormEmpName(npsemployee.getFormEmpName());

			if (npsemployee.getFormDOB() != null) {
				npsemployeevo.setFormDOB(DataTypeUtil.toDateddMMMMMyyyy(npsemployee.getFormDOB()));
			}
			npsemployeevo.setFormGender(npsemployee.getFormGender());
			npsemployeevo.setFormMaritalStatus(npsemployee.getFormMaritalStatus());
			npsemployeevo.setFormGuardianName(npsemployee.getFormGuardianName());
			npsemployeevo.setFormMobile(npsemployee.getFormMobile());
			npsemployeevo.setFormEmail(npsemployee.getFormEmail());
			npsemployeevo.setFormPan(npsemployee.getFormPan());
			npsemployeevo.setFormAadharNo(npsemployee.getFormAadharNo());
			npsemployeevo.setFormCurrentAddress(npsemployee.getFormCurrentAddress());
			npsemployeevo.setFormPermanentAddress(npsemployee.getFormPermanentAddress());
			npsemployeevo.setFormPrevEstablishment(npsemployee.getFormPrevEstablishment());
			npsemployeevo.setFormPrevnpsAccNo(npsemployee.getFormPrevnpsAccNo());

			if (npsemployee.getFormPrevDOJ() != null) {
				npsemployeevo.setFormPrevDOJ(DataTypeUtil.toDateddMMMMMyyyy(npsemployee.getFormPrevDOJ()));
			}
			if (npsemployee.getFormPrevDOL() != null) {
				npsemployeevo.setFormPrevDOL(DataTypeUtil.toDateddMMMMMyyyy(npsemployee.getFormPrevDOL()));
			}

			npsemployeevo.setUan(npsemployee.getUan());
			npsemployeevo.setActive(npsemployee.getActive());
			npsemployeevo.setStatus(npsemployee.getStatus());
			npsemployeevo.setAgreeTermsConditions(npsemployee.getAgreeTermsConditions());
			// pfemployeevo.setEffFrom(DataTypeUtil.toDateddMMMMMyyyy(pfemployee.getEffFrom()));
			npsemployeevo.setUpdatedBy(npsemployee.getUpdatedBy());
			npsemployeevo.setCreatedBy(npsemployee.getCreatedBy());

			npsemployeevo.setFormOffEmail(appContext.getCurrentEmployee().getEmail());
			
		

		}

		

		return mav;
	}

}
