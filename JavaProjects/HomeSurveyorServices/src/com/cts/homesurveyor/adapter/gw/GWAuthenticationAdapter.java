package com.cts.homesurveyor.adapter.gw;

import com.cts.homesurveyor.adapter.AuthenticationAdapter;
import com.cts.homesurveyor.helper.CICSurveyorJAXBHelper;
import com.cts.homesurveyor.jaxb.AuthenticationRequest;
import com.cts.homesurveyor.jaxb.AuthenticationResponse;
import com.cts.homesurveyor.jaxb.Authority;

/**
 * Authentication Adapter
 * 
 * @author Ayyanar Inbamohan (122685)
 * 
 */
public class GWAuthenticationAdapter implements AuthenticationAdapter {

	/***
	 * Authenticate the method 
	 */
	public String authenticate(String requestXML) {
		CICSurveyorJAXBHelper cicSurveyorJAXBHelper = new CICSurveyorJAXBHelper();
		AuthenticationRequest authenticationRequest = (AuthenticationRequest) cicSurveyorJAXBHelper
				.getJAXBObject(requestXML);

		AuthenticationResponse authenticationResponse = new AuthenticationResponse();

		System.out.println(authenticationRequest.getUserId());
		
		String userId = authenticationRequest.getUserId();
		if ( userId.equals("cts") ) {
			authenticationResponse.setIsValidUser(true);
			authenticationResponse.setUserRole("Adjuster");
			Authority authority = new Authority();
			authority.setClaimEditAuthority(true);
			authority.setPolicyViewAuthority(true);
			authenticationResponse.setAuthority(authority);
		} else {
			authenticationResponse.setIsValidUser(false);
		}

		String responseXML = cicSurveyorJAXBHelper
				.getObjectAsXML(authenticationResponse);
		return responseXML;
	}

}
