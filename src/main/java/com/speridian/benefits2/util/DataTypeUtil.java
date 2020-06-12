package com.speridian.benefits2.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTypeUtil {
	
	public static Date toDateFromStringyyyyMMdd(String dateString){
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	    Date date = null;
	    try {
	    	date = df.parse(dateString);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return date;
	}
	public static Date toDateFromStringddMMMMMyyyy(String dateString){
		DateFormat df = new SimpleDateFormat("dd MMMMM, yyyy"); 
	    Date date = null;
	    try {
	    	date = df.parse(dateString);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return date;
	}
	
	
	public static String toDateddMMMMMyyyy(Date date){
		DateFormat df = new SimpleDateFormat("dd MMMMM, yyyy");
		
		return df.format(date);
	}
	public static Date toDateFromStringddMMyyyy(String dateString){
	    DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
	    Date date = null;
	    try {
	    	date = df.parse(dateString);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return date;
	}
	

}
