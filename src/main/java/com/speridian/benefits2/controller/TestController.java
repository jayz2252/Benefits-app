package com.speridian.benefits2.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.speridian.benefits2.email.EmailFormatter;
import com.speridian.benefits2.email.EmailService;
import com.speridian.benefits2.model.pojo.BenefitPlan;
import com.speridian.benefits2.model.pojo.BenefitPlanEmployee;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.service.BenefitPlanService;

@Controller
public class TestController {

	@Autowired
	BenefitPlanService benefitPlanService;
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public String sendMail (HttpServletRequest request, HttpServletResponse response){
		
		/*List<BenefitPlanEmployee> planEmployees = new ArrayList<BenefitPlanEmployee>();
		BenefitPlanEmployee planEmployee = new BenefitPlanEmployee();
		Employee emp = new Employee();
		emp.setFirstName("jithin");
		emp.setLastName("kuriakose");
		planEmployee.setEmployee(emp);
		
		BenefitPlan plan = new BenefitPlan("Test Plan", "", null, null);
		
		planEmployee.setBenefitPlan(plan);
		
		planEmployees.add(planEmployee);
		
		String messageBody = EmailFormatter.formatFlexyPlansSubmitMail(planEmployees);
		String subject = "New Flexi Plan(s) Submitted";
		

		EmailService emailService = new EmailService("ardra.madhu@speridian.com, ardra.java@gmail.com","jithin.kuriakose@speridian.com"  , messageBody, subject);
		
		Thread thread = new Thread(emailService);
		thread.start();
		*/
		
		return "";
		
	}
}
