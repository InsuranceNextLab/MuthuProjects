package com.cts.homesurveyor.delegate;

import com.cts.homesurveyor.adapter.CICSurveyorAdapterFactory;

/**
 * AuthenticationDelegate class to delegate the work to the actual Business processing layer...
 * @author Ayyanar Inbamohan (122685)
 *
 */
public class AuthenticationBusinessDelegate {
	
	public String authenticate(String requestXML) {
		return CICSurveyorAdapterFactory.getAuthenticationAdapter().authenticate(requestXML);
	}
}