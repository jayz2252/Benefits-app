package com.speridian.benefits2.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.speridian.benefits2.ws.client.docman.rest.DocmanRestClient;

@RestController
public class DocmanRestController {

	@Autowired
	DocmanRestClient docmanRestClient;

	@RequestMapping(value = "/api/docman/document/{docmanUuid}/availability/session/{userSessionKey}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkAvailabilty(@PathVariable String docmanUuid,
			@PathVariable String userSessionKey) {
		ResponseEntity<Boolean> responseEntity;
		if (userSessionKey != null && !("".equals(userSessionKey))) {
			JSONObject response = docmanRestClient.checkAvailability(docmanUuid, userSessionKey);
			if (response != null) {
				JSONObject result = response.getJSONObject("result");
				if (result != null) {
					responseEntity = new ResponseEntity<Boolean>(result.getBoolean("available"), HttpStatus.OK);
					return responseEntity;
				}
			}
		}
		responseEntity = new ResponseEntity<Boolean>(HttpStatus.FORBIDDEN);
		return responseEntity;
	}

}
