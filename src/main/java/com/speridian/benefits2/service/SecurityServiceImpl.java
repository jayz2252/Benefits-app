package com.speridian.benefits2.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.speridian.benefits2.beans.LoginBean;
import com.speridian.benefits2.model.dao.BenefitsPropertyDao;
import com.speridian.benefits2.model.dao.EmployeeDao;
import com.speridian.benefits2.model.dao.UserDao;
import com.speridian.benefits2.model.dao.UsersLogDao;
import com.speridian.benefits2.model.dao.UsersRoleDao;
import com.speridian.benefits2.model.pojo.BenefitsProperty;
import com.speridian.benefits2.model.pojo.User;
import com.speridian.benefits2.model.pojo.UserRole;
import com.speridian.benefits2.model.pojo.UsersLog;
import com.speridian.benefits2.model.util.BenefitsConstants;
import com.speridian.benefits2.util.AppContext;
import com.speridian.benefits2.ws.client.mirror.soap.MirrorDataService;
import com.speridian.benefits2.ws.client.mirror.soap.ObjectFactory;
import com.speridian.benefits2.ws.client.mirror.types.AuthenticateX0020TheX0020UserX0020WithX0020AD;
import com.speridian.benefits2.ws.client.mirror.types.AuthenticateX0020TheX0020UserX0020WithX0020ADResponse;
import com.speridian.benefits2.ws.client.mirror.types.ServiceAuthenticationHeader;
import com.speridian.benefits2.ws.client.mirror.types.ToX0020InactivateX0020LoginX0020Key;
import com.speridian.benefits2.ws.client.mirror.types.ToX0020InactivateX0020LoginX0020KeyResponse;

/**
 * 
 * <pre>
 * Service class for all Authentication & Authorization related processes
 * </pre>
 *
 * @author jithin.kuriakose, swathy.raghu
 * @since 05-Feb-2017
 *
 */
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	MirrorDataService mirrorDataService;

	@Autowired
	UserDao userDao;

	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	UsersLogDao usersLogDao;
	
	@Autowired
	UsersRoleDao usersRoleDao;
	
	@Autowired
	BenefitsPropertyDao benefitsPropertyDao;

	ServiceAuthenticationHeader serviceAuthenticationHeader = new ObjectFactory()
			.createServiceAuthenticationHeader();

	@Override
	public AuthenticateX0020TheX0020UserX0020WithX0020ADResponse authenticateUser(
			AuthenticateX0020TheX0020UserX0020WithX0020AD authenticateRequest) {
		
		return mirrorDataService.getMirrorDataServiceSoap()
				.authenticateADLogin(
						authenticateRequest, serviceAuthenticationHeader);
	}
	
	@Override
	public ToX0020InactivateX0020LoginX0020KeyResponse inActiveLoginKey(
			ToX0020InactivateX0020LoginX0020Key logoutRequest) {
		return mirrorDataService.getMirrorDataServiceSoap().inActivateLoginKey( logoutRequest, serviceAuthenticationHeader);
	}

	@Override
	public void addUser(LoginBean loginBean, AppContext appContext) {
		Date auditDate = new Date();
		User user = userDao.getByUsername(loginBean.getUserName());
		System.out.println("INSIDE SERVICE,CHECKING RETURNED USER VARIABLE" + user);
		if (user != null) {
			// updating entry in USERS table
			user.setLastLoginDate(new Date());
			user.setLastSessionKey(appContext.getUserLoginKey());
			
			
			user.setUpdatedBy(loginBean.getUserName());
			userDao.update(user);
		} else {
			// Adding entry in USERS table
			user = new User();
			user.setUserName(loginBean.getUserName());
			user.setEmployee(employeeDao.get(appContext.getEmpId()));
			user.setLastSessionKey(appContext.getUserLoginKey());
			user.setStatus(BenefitsConstants.USER_STATUS_ACTIVE);
			user.setLastLoginDate(auditDate);
			
			user.setCreatedBy(appContext.getUserName());
			user.setUpdatedBy(appContext.getUserName());
			
			userDao.insert(user);

		}

	}

	@Override
	public void addUserLog(LoginBean loginBean, AppContext appcontext) {
		Date auditDate = new Date();
		User user = userDao.getByUsername(loginBean.getUserName());
		UsersLog usersLog = new UsersLog();
		usersLog.setUser(user);
		usersLog.setLoginTime(auditDate);
		usersLog.setSessionKey(appcontext.getUserLoginKey());
		usersLog.setStatus(BenefitsConstants.USER_STATUS_ACTIVE);
		usersLog.setCreatedBy(appcontext.getUserName());
		usersLog.setUpdatedBy(appcontext.getUserName());
		
		usersLogDao.insert(usersLog);
		
	}
	
	@Override
	public UserRole getUserRole(String userName){
		return usersRoleDao.getUserDetails(userName);
		
	}

	@Override
	public void updateUsersLog(String userLoginKey) {
		Date auditDate = new Date();
		UsersLog userslog = usersLogDao.get(userLoginKey);
		userslog.setLogoutTime(auditDate);
		userslog.setStatus(BenefitsConstants.USER_STATUS_INACTIVE);
		
		usersLogDao.update(userslog);
	}
	
	@Override
	public BenefitsProperty getPortalProperty(String propertyCode){
		return benefitsPropertyDao.get(propertyCode);
	}
}
