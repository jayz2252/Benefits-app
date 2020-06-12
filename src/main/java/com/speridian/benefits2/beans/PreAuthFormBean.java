package com.speridian.benefits2.beans;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * <pre>
 * Model attribute for Pre Authorization Form
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 21-May-2017
 *
 */
public class PreAuthFormBean {
	
	private int claimId;

	private String fiscalYear;
	
	private int memberId;
	
	private String memberName;
	
	private String memberDob;
	
	private String memberRelationship;

	
	private int treatmentId;
	
	private String tratmentName;
	
	private String illnessType;
	
	private BigDecimal estimatedMedicalExpense;
	
	private BigDecimal amountRequired;
	private BigDecimal amountApproved;
	
	private String state;
	
	private String city;
	
	private int hospitalId;
	
	private String otherHospital;
	
	private String hospitalName;

	
	private String prescriberName;
	
	private String prescriberContactNo;
	
	private String prescriberEmail;
	
	private Boolean speclistServiceRequired;
	
	private String specialistName;
	
	private String specialistContactNo;
	
	private String specialistEmail;
	
	private List<INSBillDetailBean> bean;
	
	private String proName;
	private String proContactNo;
	private String proEmail;
	
	private String comments;
	private String hrComments;
	private String insComments;
	private String gmComments;
	
	private Boolean isStart;
	
	private Integer planEmployeeId;
	
	private String otherTreatment;
	public PreAuthFormBean() {
		super();
	}
	
	public PreAuthFormBean(Integer claimId) {
		super();
		this.claimId = claimId;
	}
	
	public int getClaimId() {
		return claimId;
	}

	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}

	public String getFiscalYear() {
		return fiscalYear;
	}


	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}


	public int getMemberId() {
		return memberId;
	}


	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}


	public String getMemberName() {
		return memberName;
	}


	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public String getMemberDob() {
		return memberDob;
	}


	public void setMemberDob(String memberDob) {
		this.memberDob = memberDob;
	}


	public String getMemberRelationship() {
		return memberRelationship;
	}


	public void setMemberRelationship(String memberRelationship) {
		this.memberRelationship = memberRelationship;
	}


	public int getTreatmentId() {
		return treatmentId;
	}


	public void setTreatmentId(int treatmentId) {
		this.treatmentId = treatmentId;
	}


	public String getTratmentName() {
		return tratmentName;
	}


	public void setTratmentName(String tratmentName) {
		this.tratmentName = tratmentName;
	}


	public String getIllnessType() {
		return illnessType;
	}


	public void setIllnessType(String illnessType) {
		this.illnessType = illnessType;
	}


	public BigDecimal getEstimatedMedicalExpense() {
		return estimatedMedicalExpense;
	}


	public void setEstimatedMedicalExpense(BigDecimal estimatedMedicalExpense) {
		this.estimatedMedicalExpense = estimatedMedicalExpense;
	}


	public BigDecimal getAmountRequired() {
		return amountRequired;
	}


	public void setAmountRequired(BigDecimal amountRequired) {
		this.amountRequired = amountRequired;
	}


	public String getState() {
		return state;
	}


	public String getOtherTreatment() {
		return otherTreatment;
	}

	public void setOtherTreatment(String otherTreatment) {
		this.otherTreatment = otherTreatment;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public int getHospitalId() {
		return hospitalId;
	}


	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}


	public String getHospitalName() {
		return hospitalName;
	}


	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}


	public String getPrescriberName() {
		return prescriberName;
	}


	public void setPrescriberName(String prescriberName) {
		this.prescriberName = prescriberName;
	}


	public String getPrescriberContactNo() {
		return prescriberContactNo;
	}


	public void setPrescriberContactNo(String prescriberContactNo) {
		this.prescriberContactNo = prescriberContactNo;
	}


	public String getPrescriberEmail() {
		return prescriberEmail;
	}


	public void setPrescriberEmail(String prescriberEmail) {
		this.prescriberEmail = prescriberEmail;
	}


	public Boolean getSpeclistServiceRequired() {
		return speclistServiceRequired;
	}


	public void setSpeclistServiceRequired(Boolean speclistServiceRequired) {
		this.speclistServiceRequired = speclistServiceRequired;
	}


	public String getSpecialistName() {
		return specialistName;
	}


	public void setSpecialistName(String specialistName) {
		this.specialistName = specialistName;
	}


	public String getSpecialistContactNo() {
		return specialistContactNo;
	}


	public void setSpecialistContactNo(String specialistContactNo) {
		this.specialistContactNo = specialistContactNo;
	}


	public String getSpecialistEmail() {
		return specialistEmail;
	}


	public void setSpecialistEmail(String specialistEmail) {
		this.specialistEmail = specialistEmail;
	}


	public String getProName() {
		return proName;
	}


	public void setProName(String proName) {
		this.proName = proName;
	}


	public String getProContactNo() {
		return proContactNo;
	}


	public void setProContactNo(String proContactNo) {
		this.proContactNo = proContactNo;
	}


	public String getProEmail() {
		return proEmail;
	}


	public void setProEmail(String proEmail) {
		this.proEmail = proEmail;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<INSBillDetailBean> getBean() {
		return bean;
	}

	public void setBean(List<INSBillDetailBean> bean) {
		this.bean = bean;
	}

	public Boolean getIsStart() {
		return isStart;
	}

	public void setIsStart(Boolean isStart) {
		this.isStart = isStart;
	}

	public Integer getPlanEmployeeId() {
		return planEmployeeId;
	}

	public void setPlanEmployeeId(Integer planEmployeeId) {
		this.planEmployeeId = planEmployeeId;
	}

	public String getHrComments() {
		return hrComments;
	}

	public void setHrComments(String hrComments) {
		this.hrComments = hrComments;
	}

	public String getInsComments() {
		return insComments;
	}

	public void setInsComments(String insComments) {
		this.insComments = insComments;
	}

	public String getGmComments() {
		return gmComments;
	}

	public void setGmComments(String gmComments) {
		this.gmComments = gmComments;
	}

	public BigDecimal getAmountApproved() {
		return amountApproved;
	}

	public void setAmountApproved(BigDecimal amountApproved) {
		this.amountApproved = amountApproved;
	}

	public String getOtherHospital() {
		return otherHospital;
	}

	public void setOtherHospital(String otherHospital) {
		this.otherHospital = otherHospital;
	}

	

	
	
}
