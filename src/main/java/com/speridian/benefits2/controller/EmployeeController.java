package com.speridian.benefits2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.service.DependentService;
import com.speridian.benefits2.service.EmployeeService;

/**
 * 
 * <pre>
 * Controller for Employee related processes
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 05-Feb-2017
 *
 */
@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	DependentService dependentService;
	
	@RequestMapping(value="/employees", method=RequestMethod.GET)
	public ModelAndView listAllEmployees (){
		ModelAndView mav = new ModelAndView("employees");
		List<Employee> employees = employeeService.listAll();
		mav.addObject("employees", employees);
		return mav;
	}

}
