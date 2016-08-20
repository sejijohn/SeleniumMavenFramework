package com.selenium.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

    public static final int WEBDRIVER_TIMEOUT = 30;
	
    public static final String TASKLIST = "tasklist";
    
    public static final String KILL = "taskkill /F /IM ";
    
    public static Date date = new Date() ;
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH-mm-ss");
	public static SimpleDateFormat   todaysDate = new SimpleDateFormat("MM-dd-yyyy");
	public static String sFilePath = System.getProperty("user.dir");
  	public static String sReportPath=sFilePath+"\\Result_Log\\EA_Result_Log_"+todaysDate.format(date)+"";
    public static String sReportFileName = sReportPath+"\\"+"EATestReport_"+dateFormat.format(date)+".html";
    public static final String URL = "https://wps-portal.uat2.va.anthem.com/wps/portal/eeaemployer";
	public static String sDriverPath=sFilePath+"\\"+"Drivers";
	public static String sBrowser ="firefox";
	public String sTestName = "";


}
