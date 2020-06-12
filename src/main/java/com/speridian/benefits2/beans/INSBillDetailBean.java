package com.speridian.benefits2.beans;

import java.util.List;

import com.speridian.benefits2.model.pojo.InsPlanEmployeeClaimBill;
import com.speridian.benefits2.model.pojo.InsPlanEmployeeClaimBillDetail;

public class INSBillDetailBean {
	private InsPlanEmployeeClaimBill claimBill;
	private List<InsPlanEmployeeClaimBillDetail> billDetails;
	
	
	public InsPlanEmployeeClaimBill getClaimBill() {
		return claimBill;
	}
	public void setClaimBill(InsPlanEmployeeClaimBill claimBill) {
		this.claimBill = claimBill;
	}
	public List<InsPlanEmployeeClaimBillDetail> getBillDetails() {
		return billDetails;
	}
	public void setBillDetails(List<InsPlanEmployeeClaimBillDetail> billDetails) {
		this.billDetails = billDetails;
	}
	@Override
	public String toString() {
		return "INSBillDetailBean [claimBill=" + claimBill + ", billDetails="
				+ billDetails + "]";
	}
	
	

}
