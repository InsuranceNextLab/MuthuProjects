package com.cts.homesurveyor.delegate;

import com.cts.homesurveyor.adapter.CICSurveyorAdapterFactory;

/**
 * AuthenticationDelegate class to delegate the work to the actual Business processing layer...
 * @author Ayyanar Inbamohan (122685)
 *
 */
public class ClaimsBusinessDelegate {
	
	public String getClaims(String requestXML) {
		return CICSurveyorAdapterFactory.getClaimsAdapter().getClaims(requestXML);
	}
	
	public String getClaimSearchMini(String requestXML) {
		return CICSurveyorAdapterFactory.getClaimsAdapter().getClaimSearchMini(requestXML);
	}
	
	public String updateClaim(String requestXML) {
		return CICSurveyorAdapterFactory.getClaimsAdapter().updateClaim(requestXML);
	}
}