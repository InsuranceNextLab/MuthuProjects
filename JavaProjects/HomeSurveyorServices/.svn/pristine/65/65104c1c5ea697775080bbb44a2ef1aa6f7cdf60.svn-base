package com.cts.homesurveyor.helper;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class CICSurveyorJAXBHelper {

	/**
	 * Gets the XML data and returns the
	 * 
	 * @param xmlData
	 * @return
	 */
	public Object getJAXBObject(String xmlData) {
		Object jaxbObject = null;
		try {
			if (xmlData != null && !xmlData.trim().equals("")) {
				JAXBContext jaxbContext = JAXBContext
						.newInstance("com.cts.homesurveyor.jaxb");
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				ByteArrayInputStream inputStream = new ByteArrayInputStream(
						xmlData.getBytes());
				jaxbObject = unmarshaller.unmarshal(inputStream);
			}
		} catch (Exception e) {
			System.out.println("ERROE MESSAGE -- " + e.getMessage());
			e.printStackTrace();
		}
		return jaxbObject;
	}

	/**
	 * Gets the XML data and returns the
	 * 
	 * @param xmlData
	 * @return
	 */

	public String getObjectAsXML(Object jaxbElement) {
		String xml = "";
		try {
			if (jaxbElement != null) {
				JAXBContext jaxbContext = JAXBContext
						.newInstance("com.cts.homesurveyor.jaxb");
				Marshaller marshaller = jaxbContext.createMarshaller();

				StringWriter sw = new StringWriter();
				marshaller.marshal(jaxbElement, sw);
				xml = sw.toString();
			}
		} catch (Exception e) {
			System.out.println("ERROE MESSAGE -- " + e.getMessage());
			e.printStackTrace();
		}
		return xml;
	}

}
