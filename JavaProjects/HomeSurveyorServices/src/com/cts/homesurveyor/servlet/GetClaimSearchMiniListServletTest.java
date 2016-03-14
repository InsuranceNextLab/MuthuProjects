package com.cts.homesurveyor.servlet;


import com.cts.homesurveyor.util.CICSurveyorUtil;

public class GetClaimSearchMiniListServletTest {


	public void testDoPostHttpServletRequestHttpServletResponse() {
		System.setProperty("http.proxyHost", "10.237.125.87");
		System.setProperty("http.proxyPort", "6050");

		String requestXML = CICSurveyorUtil
				.getXMLString("test/GetClaimsListRequest.xml");
		String requestURL = "http://174.129.3.17/CICSurveyorServices/services/GetClaimSearchMiniListService";
		// String requestURL = "http://localhost:8080/CICSurveyorServices/services/GetClaimSearchMiniListService";

		CICSurveyorUtil.sendPostRequest(requestXML, requestURL);
	}

}
