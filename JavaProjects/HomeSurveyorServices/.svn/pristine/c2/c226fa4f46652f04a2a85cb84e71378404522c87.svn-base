package com.cts.homesurveyor.servlet;


import com.cts.homesurveyor.util.CICSurveyorUtil;

public class GetClaimsListServletTest {
	
	public void testDoPostHttpServletRequestHttpServletResponse() {
		
		System.setProperty("http.proxyHost", "10.237.125.83");
		System.setProperty("http.proxyPort", "6050");

		String requestXML = CICSurveyorUtil.getXMLString("test/GetClaimsListRequest.xml");
		//String requestURL = "http://174.129.3.17/CICSurveyorServices/services/GetClaimsListService";
		String requestURL = "http://localhost:8080/CICSurveyorServices/services/GetClaimsListService";

		CICSurveyorUtil.sendPostRequest(requestXML, requestURL);
	}
}