
package com.speridian.benefits2.security.filter;

import java.io.IOException;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jithin.kuriakose
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = { "/home/*" })
public class AuthenticateFilter implements Filter {

	public AuthenticateFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			// check whether session variable is set
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			HttpSession ses = req.getSession(false);
			// allow user to proccede if url is login.xhtml or user logged in or user is
			// accessing any page in //public folder
			String reqURI = req.getRequestURI();
			
			if (reqURI.indexOf("/login") >= 0 || (ses != null && ses.getAttribute("appContext") != null)
					|| reqURI.indexOf("/resources") >= 0 || reqURI.contains("favicon")) {
				chain.doFilter(request, response);
			} else {// user didn't log in but asking for a page that is not allowed so take user to
					// login page
				/*
				 * manually appending parameters in URL
				 */
				Map parametersMap = request.getParameterMap();
				StringBuffer redirectUrl = new StringBuffer(reqURI);

				int i = 0;
				for (Object key : parametersMap.keySet()) {
					redirectUrl.append(key + "=" + request.getParameter(key + ""));
					if (i != parametersMap.size() - 1) {
						redirectUrl.append("&");
					}
					i++;
				}

				/*
				 * replacing '&' with Http code '%26' to avoid ignorance of secondary parameter
				 * at login.xhtml
				 */
				String redirectUrlString = redirectUrl.toString().replace("&", "%26");

				if (reqURI != null && !("".equals(reqURI)) && !("/".equals(reqURI))) {
					res.sendRedirect(
							req.getContextPath() + "/login?redirectUrl=" + redirectUrlString); // Anonymous page
				} else {
					res.sendRedirect(req.getContextPath() + "/login"); // Anonymous user. Redirect to
																							// login page
				}
			}
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		}
	} // doFilter

	@Override
	public void destroy() {

	}

}
