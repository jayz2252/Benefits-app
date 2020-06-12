package com.speridian.benefits2.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.speridian.benefits2.email.EmailFormatter;
import com.speridian.benefits2.email.EmailProperties;
import com.speridian.benefits2.email.EmailService;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.TaxRegime;
import com.speridian.benefits2.service.EmployeeService;
import com.speridian.benefits2.service.TaxRegimeService;
import com.speridian.benefits2.service.TaxRegimeServiceImpl;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.util.AuthorizationUtil;

@Controller

public class EmployeeTaxRegimeController {
	AppContext appContext;
	
	
	TaxRegimeServiceImpl taxRegimeServiceImpl=new TaxRegimeServiceImpl();
	
	@Autowired
	EmployeeService employeeService;
	
	
	@RequestMapping(value = "/home/myFlexiPlans/taxRegime", method = RequestMethod.GET)
	public ModelAndView taxRegimeSelection(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "taxRegimeEmployeePlan");
		List<TaxRegime> taxRegime=taxRegimeServiceImpl.getTaxRegime(appContext.getEmpId(), appContext.getCurrentFiscalYear());
        mav.addObject("taxRegimeOfEmployee", taxRegime);
		return mav;
	}
	
	@RequestMapping(value = "/home/myFlexiPlans/saveTaxRegime", method = RequestMethod.POST)
	public ModelAndView taxRegimeSelectionSave(HttpServletRequest request, HttpServletResponse response) {
		appContext = (AppContext) request.getSession().getAttribute("appContext");
		//ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "employeeBenefitPlanMenu");
		Employee employee=employeeService.get(appContext.getEmpId());
		
		
		TaxRegime taxRegime=new TaxRegime();
		taxRegime.setCreatedBy(appContext.getUserName());
		taxRegime.setEmployeeId(appContext.getEmpId());
		taxRegime.setEmployeeCode(employee.getEmployeeCode());
		taxRegime.setFiscalYear(appContext.getCurrentFiscalYear());
		taxRegime.setCreatedDate(new Date());
		taxRegime.setUpdatedBy(appContext.getUserName());
		taxRegime.setTaxRegime(Integer.valueOf(request.getParameter("item")));
		if((request.getParameter("item")).equals("1")){
			taxRegime.setTaxRegimeName("Old Tax Regime");
		}else{
			taxRegime.setTaxRegimeName("New Tax Regime");
		}
		taxRegime.setUpdatedDate(new Date());
		
		boolean result=taxRegimeServiceImpl.saveTaxRegime(taxRegime);	
		if(result){
			System.out.println("Yealry Tax Plan Slected.....");
			System.out.println("Now sending email");
			String subject = "Tax Regime Enrolled for FY "+appContext.getCurrentFiscalYear();
			String body="";
			if((request.getParameter("item")).equals("1")){
				body=EmailFormatter.taxRegimeSelection(employee, "Old Slab",appContext.getCurrentFiscalYear());
			}else{
				body=EmailFormatter.taxRegimeSelectionNew(employee, "New Slab", appContext.getCurrentFiscalYear());
			}

			try{
				EmailService emailService = new EmailService(employee.getEmail(),
						"subramanyam.m@speridian.com",
						body, subject);
				Thread emailThread = new Thread(emailService);
				emailThread.start();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		ModelAndView mav=AuthorizationUtil.authorizeUser(appContext, "redirect:/home/myFlexiPlans");
		return mav;
	}
		

	
}
