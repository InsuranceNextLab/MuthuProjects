package com.cts.homesurveyor.adapter;

/**
 * Authetication Adapter Interface
 * @author Ayyanar Inbamohan (122685)
 *
 */
public interface AuthenticationAdapter {

	/**
	 * Authentication method...
	 * @param requestXML
	 * @return
	 */
	public String authenticate(String requestXML);

}
