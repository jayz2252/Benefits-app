package com.speridian.benefits2.service;

import com.speridian.benefits2.beans.LoginBean;
import com.speridian.benefits2.model.pojo.BenefitsProperty;
import com.speridian.benefits2.model.pojo.UserRole;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.ws.client.mirror.types.AuthenticateX0020TheX0020UserX0020WithX0020AD;
import com.speridian.benefits2.ws.client.mirror.types.AuthenticateX0020TheX0020UserX0020WithX0020ADResponse;
import com.speridian.benefits2.ws.client.mirror.types.ToX0020InactivateX0020LoginX0020Key;
import com.speridian.benefits2.ws.client.mirror.types.ToX0020InactivateX0020LoginX0020KeyResponse;


/**
 * 
 * <pre>
 *Service interface for all Authentication & Authorization related processes
 * </pre>
 *
 * @author jithin.kuriakose, swathy.raghu
 * @since 05-Feb-2017
 *
 */
public interface SecurityService {

	public AuthenticateX0020TheX0020UserX0020WithX0020ADResponse authenticateUser(AuthenticateX0020TheX0020UserX0020WithX0020AD authenticateRequest);
	public ToX0020InactivateX0020LoginX0020KeyResponse inActiveLoginKey(ToX0020InactivateX0020LoginX0020Key logoutRequest);
	
	
	public void addUser(LoginBean loginBean,AppContext appcontext);
	public void addUserLog(LoginBean loginBean,AppContext appcontext);
	public UserRole getUserRole(String userName);
	public void updateUsersLog(String userLoginKey);
	public BenefitsProperty getPortalProperty(String propertyCode);
	
}
