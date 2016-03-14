package com.cts.homesurveyor.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TestUtil {

	public static void DateFormatTest() throws Exception {
		/*
		 * String dateString = "2001/03/09";
		 * 
		 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
		 * Date convertedDate = dateFormat.parse(dateString);
		 * 
		 * System.out.println("Converted string to date : " + convertedDate);
		 * 
		 * long ageInMillis = new Date().getTime() - convertedDate.getTime();
		 * Date age = new Date(ageInMillis);
		 */
		// Date dateOB = new Date("1983/03/09");
		String dt = "19680724";
		Date dateOB = new SimpleDateFormat("yyyyddmm", Locale.ENGLISH)
				.parse(dt);
		// Date dateOB = new Date();
		System.out.println("dateOB 11 -- " + dateOB);

		System.out.println("Year 11 -- " + dateOB.getYear());
		/*
		 * System.out.println("Yr--" + dt.substring(0, 4));
		 * System.out.println("Month--" + dt.substring(4, 6));
		 * System.out.println("Day--" + dt.substring(6, 8));
		 * 
		 * dateOB.setYear(dt.indexOf(0, 4)); dateOB.setMonth(dt.indexOf(4, 6));
		 * 
		 * System.out.println("dateOB -- " + dateOB.getYear());
		 */

		Calendar dob = Calendar.getInstance();
		dob.setTime(dateOB);
		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR))
			age--;

		System.out.println("sdfsdf -- " + age);
	}

	public static void main(String[] argv) throws Exception {
		// DateFormatTest();
		System.out.println(getDate(1));
		System.out.println(getDate(2));
		System.out.println(getDate(3));
		System.out.println(getDate(4));
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

	private static String getDiff() {
		String dateValue = "";
		Date date = new Date();
		Date date2 = new Date("07-23-2012");
		System.out.println("DAte diff -- " + dateValue.compareTo("2012-07-23"));

		return dateValue;
	}

	public static int getAge() throws Exception {

		DateFormat formatter = new SimpleDateFormat("yy-mm-dd");
		Date dateOB = (Date) formatter.parse("1983-03-12");

		System.out.println("dateOB--" + dateOB);
		long ageInMillis = new Date().getTime() - dateOB.getTime();
		Date age = new Date(ageInMillis);
		return age.getYear();
	}

}
