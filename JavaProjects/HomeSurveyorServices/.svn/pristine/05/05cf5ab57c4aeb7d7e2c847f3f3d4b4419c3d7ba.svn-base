package com.cts.homesurveyor.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cts.homesurveyor.delegate.ClaimsBusinessDelegate;

/**
 * Ayyanar Inbamohan (122685)
 */
public class GetClaimSearchMiniListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;      
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String requestXML = request.getParameter("RequestXML");
		
		ClaimsBusinessDelegate claimsBusinessDelegate = new ClaimsBusinessDelegate();
		String responseXML = claimsBusinessDelegate.getClaimSearchMini(requestXML);

		System.out.println(" responseXML -- " + responseXML);
		
		out.print(responseXML);
		out.flush();
		out.close();
	}
}
