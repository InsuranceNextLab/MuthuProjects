/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cts.homesurveyor.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author 367025
 */
public class ItemregistrationServlet extends HttpServlet {

    Java2MySql dbfunction;

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {


            String data = request.getParameter("data");
            System.out.print("data values = " + data);
            JSONObject jsonDataObject = (JSONObject) JSONValue.parse(data);
            dbfunction = new Java2MySql();
            int last_insert_id = dbfunction.addItemDetails(jsonDataObject);
            PrintWriter out = response.getWriter();
            out.println("<h1>item details updated successfully</h1> == " + last_insert_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
