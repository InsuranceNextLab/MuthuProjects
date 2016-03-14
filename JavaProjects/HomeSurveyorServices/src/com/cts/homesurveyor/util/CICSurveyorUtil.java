package com.cts.homesurveyor.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class CICSurveyorUtil {

	public static Element getDocumentRoot(String xmlData) {
		Element root = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			System.out.println("getDocumentRoot - The XMLData is " + xmlData);
			Document doc = builder.parse(new ByteArrayInputStream(xmlData
					.trim().getBytes()));
			root = doc.getDocumentElement();
		} catch (ParserConfigurationException pConfigurationException) {
			pConfigurationException.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (SAXException saxException) {
			saxException.printStackTrace();
		}
		return root;
	}

	/**
	 * get the XML content as String from the XML file.
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getXMLString(String filePath) {
		Element root = null;
		String xml = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(filePath));

			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			xml = writer.toString();

			/*
			 * root = doc.getDocumentElement(); xml = root.toString();
			 */
			System.out.println("XMl Read from File is -- " + xml);
		} catch (ParserConfigurationException pConfigurationException) {
			pConfigurationException.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (SAXException saxException) {
			saxException.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return xml;
	}

	/**
	 * get the XML content as String from the XML file.
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getXMLString(String filePath, InputStream inputStream, ServletContext context) {
		String xml = null;
		try {
			if( inputStream == null ) {
				inputStream = context.getResourceAsStream(filePath);
			}

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inputStream);

			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			xml = writer.toString();
		} catch (ParserConfigurationException pConfigurationException) {
			pConfigurationException.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (SAXException saxException) {
			saxException.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return xml;
	}

	public static void sendPostRequest(String requestXML, String requestURL) {
		try {
			// Construct data
			String data = URLEncoder.encode("RequestXML", "UTF-8") + "="
					+ URLEncoder.encode(requestXML, "UTF-8");

			// Send data
			URL url = new URL(requestURL);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the String date based on the difference provided
	 * 
	 * @param diff
	 * @return
	 */
	public static String getDate(int diff) {
		String dateValue = "";

		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -diff);

		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		dateValue = "" + month + "/" + day + "/" + year;

		return dateValue;
	}
}
