package com.speridian.benefits2.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.ls.LSInput;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.speridian.benefits2.model.pojo.Hospital;
import com.speridian.benefits2.service.INSPlanEmployeeService;

/**
 * 
 * @author akhil.b
 *
 */

@RestController
public class INSRestController {
	@Autowired
	INSPlanEmployeeService insPlanEmployeeService;

	@RequestMapping(value = "/api/ins/state/city", method = RequestMethod.GET)
	public ResponseEntity<List<String>> selectCity(@RequestParam(required=true) String state) {
		ResponseEntity<List<String>> responseEntity;
		try {
			state=state.replace(" and ", " & ");
			List<String> cities = insPlanEmployeeService.getCities(state);
			if(cities.isEmpty() || cities==null){
				state=state.replace(" & ", " and ");
				cities = insPlanEmployeeService.getCities(state);
			}
			JSONParser parser = new JSONParser();
			JSONArray array = new JSONArray();
			for(String city:cities){
				array.add(city);	
			}
					responseEntity = new ResponseEntity<List<String>>(array, HttpStatus.OK);
					return responseEntity;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/api/ins/state/city/hospital", method = RequestMethod.GET)
	public ResponseEntity<List<Hospital>> selectHospital(@RequestParam(required=true) String city) {
		ResponseEntity<List<Hospital>> responseEntity;
		try{
			city=city.replace(" and ", " & ");
			List<Hospital> hospitals=insPlanEmployeeService.listAllHospitalsBYCity(city);
			if(hospitals==null || hospitals.isEmpty()){
				city=city.replace(" & ", " and ");
				hospitals=insPlanEmployeeService.listAllHospitalsBYCity(city);
			}
			JSONParser parser = new JSONParser();
			JSONArray array = new JSONArray();
			for(Hospital hospital:hospitals){
				array.add(hospital);
			}
				responseEntity = new ResponseEntity<List<Hospital>>(array, HttpStatus.OK);
				return responseEntity;
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
		
	}
	
}
