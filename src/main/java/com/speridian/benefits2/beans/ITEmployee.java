package com.speridian.benefits2.beans;

import java.util.List;

import com.speridian.benefits2.model.pojo.ITEmployeeInvestment;
import com.speridian.benefits2.model.pojo.EmpRentDetail;
import com.speridian.benefits2.model.pojo.Employee;
import com.speridian.benefits2.model.pojo.ITInvestmentField;


public class ITEmployee {

private	Employee employeeDetails;
private List<ITInvestmentField> sectionA;
private List<ITInvestmentField> sectionB;
private List<ITEmployeeInvestment> empInvestmentDetails;
private List<Feild> sectionD;
private List<Feild> sectionE;
private List<Feild> sectionF;
private List<Feild> sectionG;
private List<EmpRentDetail> empRentDetails;
private ITEmployee itYearlyEmployeeDeclaration;

private List<ITEmployeeInvestment> empInvestmentDetailsA;
private List<ITEmployeeInvestment> empInvestmentDetailsB;

private String fromDate;
private String toDate;


public String getFromDate() {
	return fromDate;
}
public void setFromDate(String fromDate) {
	this.fromDate = fromDate;
}
public String getToDate() {
	return toDate;
}
public void setToDate(String toDate) {
	this.toDate = toDate;
}
public Employee getEmployeeDetails() {
	return employeeDetails;
}
public void setEmployeeDetails(Employee employeeDetails) {
	this.employeeDetails = employeeDetails;
}
public ITEmployee getItYearlyEmployeeDeclaration() {
	return itYearlyEmployeeDeclaration;
}
public void setItYearlyEmployeeDeclaration(
		ITEmployee itYearlyEmployeeDeclaration) {
	this.itYearlyEmployeeDeclaration = itYearlyEmployeeDeclaration;
}

public List<ITInvestmentField> getSectionA() {
	return sectionA;
}
public void setSectionA(List<ITInvestmentField> sectionA) {
	this.sectionA = sectionA;
}
public List<ITInvestmentField> getSectionB() {
	return sectionB;
}
public void setSectionB(List<ITInvestmentField> sectionB) {
	this.sectionB = sectionB;
}
public List<ITEmployeeInvestment> getEmpInvestmentDetails() {
	return empInvestmentDetails;
}
public void setEmpInvestmentDetails(
		List<ITEmployeeInvestment> empInvestmentDetails) {
	this.empInvestmentDetails = empInvestmentDetails;
}
public List<Feild> getSectionD() {
	return sectionD;
}
public void setSectionD(List<Feild> sectionD) {
	this.sectionD = sectionD;
}
public List<Feild> getSectionE() {
	return sectionE;
}
public void setSectionE(List<Feild> sectionE) {
	this.sectionE = sectionE;
}
public List<Feild> getSectionF() {
	return sectionF;
}
public void setSectionF(List<Feild> sectionF) {
	this.sectionF = sectionF;
}
public List<Feild> getSectionG() {
	return sectionG;
}
public void setSectionG(List<Feild> sectionG) {
	this.sectionG = sectionG;
}
public List<EmpRentDetail> getEmpRentDetails() {
	return empRentDetails;
}
public void setEmpRentDetails(List<EmpRentDetail> empRentDetails) {
	this.empRentDetails = empRentDetails;
}
public List<ITEmployeeInvestment> getEmpInvestmentDetailsA() {
	return empInvestmentDetailsA;
}
public void setEmpInvestmentDetailsA(
		List<ITEmployeeInvestment> empInvestmentDetailsA) {
	this.empInvestmentDetailsA = empInvestmentDetailsA;
}
public List<ITEmployeeInvestment> getEmpInvestmentDetailsB() {
	return empInvestmentDetailsB;
}
public void setEmpInvestmentDetailsB(
		List<ITEmployeeInvestment> empInvestmentDetailsB) {
	this.empInvestmentDetailsB = empInvestmentDetailsB;
}



	
}
