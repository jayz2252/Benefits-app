package com.speridian.benefits2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.util.AuthorizationUtil;

/**
 * 
 * <pre>
 * Controller for admin control panel
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 05-Feb-2017
 *
 */
@Controller
public class AdminHomeController {

	@RequestMapping(value="/home/controlPanel", method=RequestMethod.GET)
	public ModelAndView showAdminHome(HttpServletRequest request, HttpServletResponse response){
		AppContext appContext = (AppContext)request.getSession().getAttribute("appContext");
		
		ModelAndView mav = AuthorizationUtil.authorizeUser(appContext, "adminDashboard");
		
		return mav;
	}
}
