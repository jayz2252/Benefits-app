package com.speridian.benefits2.util;

import java.util.Date;
import java.util.List;

import com.speridian.benefits2.model.pojo.Employee;

/**
 * 
 * <pre>
 * AppContext used for Security (Authentication and Authorization), this will store to session once user logins
 * </pre>
 * 
 * @author jithin.kuriakose
 * @since 05-Feb-2017
 * 
 */
public class AppContext {

	String userName;
	String role;
	String designation;
	Integer empId;
	String empCode;
	String userLoginKey;
	Boolean admin;
	String currentFiscalYear;
	String currentInsuranceFiscalYear;
	String currentLtaBlock;
	Employee currentEmployee;
	List<String> fiscalYears;

	Date loginTime;
	String loginIp;
	
	Boolean optingPeriodOver;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer i) {
		this.empId = i;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getUserLoginKey() {
		return userLoginKey;
	}

	public void setUserLoginKey(String userLoginKey) {
		this.userLoginKey = userLoginKey;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Boolean isAdmin() {
		return admin;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public String getCurrentFiscalYear() {
		return currentFiscalYear;
	}

	public void setCurrentFiscalYear(String currentFiscalYear) {
		this.currentFiscalYear = currentFiscalYear;
	}

	public String getCurrentLtaBlock() {
		return currentLtaBlock;
	}

	public void setCurrentLtaBlock(String currentLtaBlock) {
		this.currentLtaBlock = currentLtaBlock;
	}

	public Employee getCurrentEmployee() {
		return currentEmployee;
	}

	public void setCurrentEmployee(Employee currentEmployee) {
		this.currentEmployee = currentEmployee;
	}

	public Boolean getOptingPeriodOver() {
		return optingPeriodOver;
	}

	public void setOptingPeriodOver(Boolean optingPeriodOver) {
		this.optingPeriodOver = optingPeriodOver;
	}

	public List<String> getFiscalYears() {
		return fiscalYears;
	}

	public void setFiscalYears(List<String> fiscalYears) {
		this.fiscalYears = fiscalYears;
	}

	public String getCurrentInsuranceFiscalYear() {
		return currentInsuranceFiscalYear;
	}

	public void setCurrentInsuranceFiscalYear(String currentInsuranceFiscalYear) {
		this.currentInsuranceFiscalYear = currentInsuranceFiscalYear;
	}
	
	
}