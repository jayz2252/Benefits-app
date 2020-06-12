package com.speridian.benefits2.beans;

import com.speridian.benefits2.model.pojo.Employee;

public class PFEmployeeResponse {

	private Integer empId;

	private String uan;

	private String availableSlab;

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getUan() {
		return uan;
	}

	public void setUan(String uan) {
		this.uan = uan;
	}

	public String getAvailableSlab() {
		return availableSlab;
	}

	public void setAvailableSlab(String availableSlab) {
		this.availableSlab = availableSlab;
	}
	
	
}
