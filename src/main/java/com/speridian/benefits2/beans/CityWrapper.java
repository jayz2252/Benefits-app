package com.speridian.benefits2.beans;

/**
 * 
 * <pre>
 * Wrapper class of INS Hospital cities
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 22-May-2017
 *
 */
public class CityWrapper {

	private String city;
	
	private String state;
	
	

	public CityWrapper() {
		super();
	}

	public CityWrapper(String city, String state) {
		super();
		this.city = city;
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
