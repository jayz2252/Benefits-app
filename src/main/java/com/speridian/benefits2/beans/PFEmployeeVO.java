package com.speridian.benefits2.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.speridian.benefits2.model.pojo.Employee;

public class PFEmployeeVO {

	private Integer pfEmployeeId;

	private Employee employee;


	private String optedSlab;


	private String optedDate;


	private String effFrom;

	private String effTill;


	private String uan;

	private String pfAcNo;

	private Boolean epfNomPresent;

	private Boolean epfNoFamilyFlag;


	private Boolean epfFathMothDep;


	private Boolean epsNomPresent;


	private Boolean epsNoFamilyFlag;


	private Boolean agreeTermsConditions;


	private byte[] employeeSign;


	private Boolean active;


	private String status;


	private byte[] employerSign;

	private String formEmpName;

	private String formGuardianName;

	private String formDOB;

	private String formMaritalStatus;

	private String formMobile;

	private String formEmail;
	
	private String formOffEmail;

	private String formAadharNo;

	private String formPrevPfAccNo;

	private String formPrevEstablishment;

	private String formPrevDOJ;

	private String formPrevDOL;

	private String currentDOJ;

	private String currentDOL;

	private String formGender;

	private String formCurrentAddress;

	private String formPermanentAddress;

	private String formPan;

	private String formVoluntaryPF;
	
	/*
	 * Audit fields
	 */



	private String updatedBy;

	private String updatedDate;

	private String createdBy;

	private String createdDate;
	
	private String docmanUUId;
	
	private String uploadUrl;
	
	private String downloadUrl;
	
	
	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getDocmanUUId() {
		return docmanUUId;
	}

	public void setDocmanUUId(String docmanUUId) {
		this.docmanUUId = docmanUUId;
	}

	public Integer getPfEmployeeId() {
		return pfEmployeeId;
	}

	public void setPfEmployeeId(Integer pfEmployeeId) {
		this.pfEmployeeId = pfEmployeeId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getOptedSlab() {
		return optedSlab;
	}

	public void setOptedSlab(String optedSlab) {
		this.optedSlab = optedSlab;
	}

	public String getOptedDate() {
		return optedDate;
	}

	public void setOptedDate(String optedDate) {
		this.optedDate = optedDate;
	}

	public String getEffFrom() {
		return effFrom;
	}

	public void setEffFrom(String effFrom) {
		this.effFrom = effFrom;
	}

	public String getEffTill() {
		return effTill;
	}

	public void setEffTill(String effTill) {
		this.effTill = effTill;
	}

	public String getUan() {
		return uan;
	}

	public void setUan(String uan) {
		this.uan = uan;
	}

	public String getPfAcNo() {
		return pfAcNo;
	}

	public void setPfAcNo(String pfAcNo) {
		this.pfAcNo = pfAcNo;
	}

	public Boolean getEpfNomPresent() {
		return epfNomPresent;
	}

	public void setEpfNomPresent(Boolean epfNomPresent) {
		this.epfNomPresent = epfNomPresent;
	}

	public Boolean getEpfNoFamilyFlag() {
		return epfNoFamilyFlag;
	}

	public void setEpfNoFamilyFlag(Boolean epfNoFamilyFlag) {
		this.epfNoFamilyFlag = epfNoFamilyFlag;
	}

	public Boolean getEpfFathMothDep() {
		return epfFathMothDep;
	}

	public void setEpfFathMothDep(Boolean epfFathMothDep) {
		this.epfFathMothDep = epfFathMothDep;
	}

	public Boolean getEpsNomPresent() {
		return epsNomPresent;
	}

	public void setEpsNomPresent(Boolean epsNomPresent) {
		this.epsNomPresent = epsNomPresent;
	}

	public Boolean getEpsNoFamilyFlag() {
		return epsNoFamilyFlag;
	}

	public void setEpsNoFamilyFlag(Boolean epsNoFamilyFlag) {
		this.epsNoFamilyFlag = epsNoFamilyFlag;
	}

	public Boolean getAgreeTermsConditions() {
		return agreeTermsConditions;
	}

	public void setAgreeTermsConditions(Boolean agreeTermsConditions) {
		this.agreeTermsConditions = agreeTermsConditions;
	}

	public byte[] getEmployeeSign() {
		return employeeSign;
	}

	public void setEmployeeSign(byte[] employeeSign) {
		this.employeeSign = employeeSign;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public byte[] getEmployerSign() {
		return employerSign;
	}

	public void setEmployerSign(byte[] employerSign) {
		this.employerSign = employerSign;
	}

	public String getFormEmpName() {
		return formEmpName;
	}

	public void setFormEmpName(String formEmpName) {
		this.formEmpName = formEmpName;
	}

	public String getFormGuardianName() {
		return formGuardianName;
	}

	public void setFormGuardianName(String formGuardianName) {
		this.formGuardianName = formGuardianName;
	}

	public String getFormDOB() {
		return formDOB;
	}

	public void setFormDOB(String formDOB) {
		this.formDOB = formDOB;
	}

	public String getFormMaritalStatus() {
		return formMaritalStatus;
	}

	public void setFormMaritalStatus(String formMaritalStatus) {
		this.formMaritalStatus = formMaritalStatus;
	}

	public String getFormMobile() {
		return formMobile;
	}

	public void setFormMobile(String formMobile) {
		this.formMobile = formMobile;
	}

	public String getFormEmail() {
		return formEmail;
	}

	public void setFormEmail(String formEmail) {
		this.formEmail = formEmail;
	}

	public String getFormAadharNo() {
		return formAadharNo;
	}

	public void setFormAadharNo(String formAadharNo) {
		this.formAadharNo = formAadharNo;
	}

	public String getFormPrevPfAccNo() {
		return formPrevPfAccNo;
	}

	public void setFormPrevPfAccNo(String formPrevPfAccNo) {
		this.formPrevPfAccNo = formPrevPfAccNo;
	}

	public String getFormPrevEstablishment() {
		return formPrevEstablishment;
	}

	public void setFormPrevEstablishment(String formPrevEstablishment) {
		this.formPrevEstablishment = formPrevEstablishment;
	}

	public String getFormPrevDOJ() {
		return formPrevDOJ;
	}

	public void setFormPrevDOJ(String formPrevDOJ) {
		this.formPrevDOJ = formPrevDOJ;
	}

	public String getFormPrevDOL() {
		return formPrevDOL;
	}

	public void setFormPrevDOL(String formPrevDOL) {
		this.formPrevDOL = formPrevDOL;
	}

	public String getCurrentDOJ() {
		return currentDOJ;
	}

	public void setCurrentDOJ(String currentDOJ) {
		this.currentDOJ = currentDOJ;
	}

	public String getCurrentDOL() {
		return currentDOL;
	}

	public void setCurrentDOL(String currentDOL) {
		this.currentDOL = currentDOL;
	}

	public String getFormGender() {
		return formGender;
	}

	public void setFormGender(String formGender) {
		this.formGender = formGender;
	}

	public String getFormCurrentAddress() {
		return formCurrentAddress;
	}

	public void setFormCurrentAddress(String formCurrentAddress) {
		this.formCurrentAddress = formCurrentAddress;
	}

	public String getFormPermanentAddress() {
		return formPermanentAddress;
	}

	public void setFormPermanentAddress(String formPermanentAddress) {
		this.formPermanentAddress = formPermanentAddress;
	}

	public String getFormPan() {
		return formPan;
	}

	public void setFormPan(String formPan) {
		this.formPan = formPan;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getFormOffEmail() {
		return formOffEmail;
	}

	public void setFormOffEmail(String formOffEmail) {
		this.formOffEmail = formOffEmail;
	}
	
	public String getFormVoluntaryPF() {
		return formVoluntaryPF;
	}

	public void setFormVoluntaryPF(String formVoluntaryPF) {
		this.formVoluntaryPF = formVoluntaryPF;
	}
	
}
