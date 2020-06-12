package com.speridian.benefits2.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.speridian.benefits2.model.pojo.HomePageNotification;
import com.speridian.benefits2.model.pojo.IncomeTaxSlab;
import com.speridian.benefits2.service.IncomeTaxSlabService;
import com.speridian.benefits2.service.HomePageNotificationService;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.util.AuthorizationUtil;

/**
 * 
 * <pre>
 * Controller for Employee Home page
 * </pre>
 *
 * @author jithin.kuriakose, minnu.john
 * @since 05-Feb-2017
 *
 */
@Controller
public class EmployeeHomeController {

	@Autowired
	IncomeTaxSlabService incomeTaxSlabService;
	@Autowired
	HomePageNotificationService homePageNotificationService;
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public ModelAndView showHomePage(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = null;
		AppContext appContext = (AppContext) request.getSession().getAttribute("appContext");
		mav = AuthorizationUtil.authorizeUser(appContext, "employeeHome");
		/*
		 *Fetching IT slab for current FY to display in the home page  
		 */
		List<IncomeTaxSlab> taxSlabs = incomeTaxSlabService.listAll(appContext.getCurrentFiscalYear());
		List<String> homePageNotifications = homePageNotificationService.listAll();
		
		mav.addObject("taxSlabs", taxSlabs);
		mav.addObject("notifications",homePageNotifications);
		return mav;
	}
}
