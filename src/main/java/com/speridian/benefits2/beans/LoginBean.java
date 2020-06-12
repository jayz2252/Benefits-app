package com.speridian.benefits2.beans;

/**
 * 
 * <pre>
 * Backing Bean for login 
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 05-Feb-2017
 *
 */
public class LoginBean {
	
	private String userName;
	private String password;
	
	
	
	public LoginBean() {
		super();
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
