package com.speridian.benefits2.util;

import org.springframework.web.servlet.ModelAndView;

import com.speridian.benefits2.model.util.BenefitsConstants;

/**
 * 
 * <pre>
 * class used for all authorization functionalities
 * </pre>
 * 
 * @author jithin.kuriakose
 * @since 05-Feb-2017
 * 
 */
public class AuthorizationUtil {

	public static ModelAndView authorizeUser(AppContext appContext,
			String successView) {
		ModelAndView mav = null;

		if (appContext == null) {
			mav = new ModelAndView("redirect:/login");
		} else {

			if (appContext.isAdmin()
					|| (appContext.getRole().equals(
							BenefitsConstants.USER_ROLE_EMPLOYEE))) {
				mav = new ModelAndView(successView);
			} else {
				mav = new ModelAndView("employeeHome");
			}
		}
		return mav;
	}

	public static ModelAndView authorizeAdmin(AppContext appContext,
			String successView) {
		ModelAndView mav = null;

		if (appContext == null) {
			mav = new ModelAndView("redirect:/login");
		} else {
			if (appContext.isAdmin()) {
				mav = new ModelAndView(successView);
			} else {
				mav = new ModelAndView("employeeHome");
			}
		}
		return mav;
	}
}
