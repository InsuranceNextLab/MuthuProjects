package com.cts.homesurveyor.adapter;

import com.cts.homesurveyor.adapter.gw.GWAuthenticationAdapter;
import com.cts.homesurveyor.adapter.gw.GWClaimAdapter;

public class CICSurveyorAdapterFactory {

	/**
	 * Creates the instance of the authentication adapter
	 * @return
	 */
	public static AuthenticationAdapter getAuthenticationAdapter() {
		return new GWAuthenticationAdapter();
	}
	
	/**
	 * Creates the instance of the claims adapter
	 * @return
	 */
	public static ClaimsAdapter getClaimsAdapter() {
		return new GWClaimAdapter();
	}
}