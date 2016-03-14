package com.cts.homesurveyor.adapter.gw;

import java.io.InputStream;
import java.util.HashMap;

import com.cts.homesurveyor.util.VelocityUtil;
import com.cts.homesurveyor.adapter.ClaimsAdapter;
import com.cts.homesurveyor.helper.CICSurveyorJAXBHelper;
import com.cts.homesurveyor.jaxb.AuthenticationRequest;
import com.cts.homesurveyor.jaxb.GetClaimsListRequest;
import com.cts.homesurveyor.jaxb.GetClaimsListResponse;
import com.cts.homesurveyor.util.CICSurveyorUtil;

/**
 * Authentication Adapter
 * 
 * @author Ayyanar Inbamohan (122685)
 * 
 */
public class GWClaimAdapter implements ClaimsAdapter {

	/***
	 * get the claims
	 */
	public String getClaims(String requestXML) {
		CICSurveyorJAXBHelper cicSurveyorJAXBHelper = new CICSurveyorJAXBHelper();
		GetClaimsListRequest getClaimRequest = (GetClaimsListRequest) cicSurveyorJAXBHelper
				.getJAXBObject(requestXML);

		// System.out.println("requestXML -- " + requestXML);

		GetClaimsListResponse claimResponse = new GetClaimsListResponse();

		AuthenticationRequest authenticationRequest = getClaimRequest
				.getAuthenticationRequest();

		String userId = authenticationRequest.getUserId();

		HashMap<String, String> values = new HashMap<String, String>();
		values.put("Date1", CICSurveyorUtil.getDate(1));
		values.put("Date2", CICSurveyorUtil.getDate(2));
		values.put("Date3", CICSurveyorUtil.getDate(3));
		values.put("Date4", CICSurveyorUtil.getDate(4));
		values.put("Date5", CICSurveyorUtil.getDate(5));
		values.put("Date6", CICSurveyorUtil.getDate(6));
		values.put("Date7", CICSurveyorUtil.getDate(7));
		values.put("Date8", CICSurveyorUtil.getDate(8));
		values.put("Date9", CICSurveyorUtil.getDate(9));
		values.put("Date10", CICSurveyorUtil.getDate(10));
		
		String responseText = "";
		if ( userId.equals("cts")) {
			InputStream inputStream = null;

			if (getClaimRequest.getClaimSearchList() == null
					|| getClaimRequest.getClaimSearchList().getClaimNumber() == null) {
				// inputStream =
				// GWClaimAdapter.class.getResourceAsStream("testfiles/GetClaimsListResponse_"
				// + userId + ".xml");
				// Date setting part here...
				responseText = VelocityUtil.getCalEngineRequestXML(
						"GetClaimsListResponse_cts.xml", values);
				System.out.println("The Reponse Text from Velocity $$$ " + responseText);
			} else if (getClaimRequest.getClaimSearchList().getClaimNumber()
					.equals("CL-000000124")) {
				inputStream = GWClaimAdapter.class
						.getResourceAsStream("testfiles/GetClaimResponse.xml");
			}

			System.out.println("inputStream sdfjsgdf " + inputStream);

			if (responseText != null && inputStream != null) {
				// The below code should actually do the mapping from the
				// external
				// system object to the JAXB object...
				responseText = CICSurveyorUtil.getXMLString("", inputStream,
						null);
			}
		}

		System.out.println(" responseText in adapter -- " + responseText);

		// Apply the Velocity Template here to process the velocity code...

		String responseXML = responseText;
		return responseXML;
	}

	/**
	 * to get the minimum details in the claims mini
	 */
	public String getClaimSearchMini(String requestXML) {
		CICSurveyorJAXBHelper cicSurveyorJAXBHelper = new CICSurveyorJAXBHelper();
		GetClaimsListRequest getClaimRequest = (GetClaimsListRequest) cicSurveyorJAXBHelper
				.getJAXBObject(requestXML);

		GetClaimsListResponse claimResponse = new GetClaimsListResponse();

		AuthenticationRequest authenticationRequest = getClaimRequest
				.getAuthenticationRequest();

		String userId = authenticationRequest.getUserId();

		String responseText = "";
		if ( userId.equals("cts")) {
			InputStream inputStream = null;

			inputStream = GWClaimAdapter.class
					.getResourceAsStream("testfiles/GetClaimSearchMiniResponse.xml");
			System.out.println("GetClaimSearchResponse.xml read not");
			// System.out.println("Input stream is " + inputStream);
			responseText = CICSurveyorUtil.getXMLString("", inputStream, null);
		}

		String responseXML = responseText; // cicSurveyorJAXBHelper.getObjectAsXML(claimResponse);
		return responseXML;
	}

	/**
	 * Update the claim details...
	 */
	public String updateClaim(String requestXML) {
		// just store the file content into the File System, once we get access
		// to GW we need to pass the data on to the GW.
		InputStream inputStream = GWClaimAdapter.class
				.getResourceAsStream("testfiles/UpdateClaimResponse.xml");
		// The below code should actually do the mapping from the external
		// system object to the JAXB object...
		String responseText = CICSurveyorUtil.getXMLString("", inputStream,
				null);

		return responseText;
	}
	
}