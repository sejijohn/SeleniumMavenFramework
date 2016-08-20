package com.selenium.common;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Utilities {
	public void OpenLogFile(String spath) throws IOException
	{
		File htmlFile = new File(spath);
		Desktop.getDesktop().browse(htmlFile.toURI());
	}
	@SuppressWarnings("deprecation")
	public void Scheduler(String sTime) throws InterruptedException
	{
		String Str[] = sTime.split(":");
		int Hour = Integer.parseInt(Str[0].trim());
		int Min = Integer.parseInt(Str[1].trim());
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		date = calendar.getTime();
		System.out.println(date);
		Date date2 = new Date(calendar.getTimeInMillis());
		date2.setHours(Hour);
		date2.setMinutes(Min);
		System.out.println(date2);
		while (date2.after(date))
		{
			Robot robot;
			try
			{
				robot = new Robot();
				robot.mouseMove(100, 100);
				Thread.sleep(30);
				robot.mouseMove(200, 100);
				Thread.sleep(30);
				calendar = Calendar.getInstance();
				date = calendar.getTime();
			}
			catch (AWTException e)
			{
				e.printStackTrace();

			}
		}
	}
	public void createUserDir(String sFilename) throws IOException
	{
		File file = new File(sFilename);
		try
		{
			if (!file.exists())
			{
				file.mkdirs();
			}
			else
			{
				System.out.println("Folder already exists");
			}
		}
		catch (Exception e)
		{}
	}
	public void deletetemp(String path)
	{
		try
		{
			Runtime.getRuntime().exec(path);
		}
		catch (Exception delex)
		{}

	}
	public DesiredCapabilities bypassProxy(String Browser)
	{
		DesiredCapabilities capabilities = null;
		Proxy newProxy = new Proxy();
		newProxy.setProxyType(Proxy.ProxyType.DIRECT);
		if (Browser == "IE")
		{
			capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability("ie.setProxyByServer", true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(CapabilityType.PROXY, newProxy);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		}
		else if (Browser == "Chrome")
		{
			capabilities = DesiredCapabilities.chrome();
		}
		return capabilities;
	}
	public String FormatString(String sValue)
	{
		if (sValue.contains("\n"))
		{
			sValue = sValue.replace("\n", "").trim();
		}
		sValue.replaceAll(" ", "");
		return sValue;
	}
	public String StringTrimmer(String sText)
	{
		return sText.trim().replaceAll("\n", "").replaceAll(" ", "").replaceAll(":", "").replaceAll("_", "");
	}
	public String GetSystemDateinFormatSpecified(String Format)
	{
		String ActualDate = null;
		try
		{

			Calendar currentCal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			System.out.println("Current date of Calendar : " + dateFormat.format(currentCal.getTime()));
			currentCal.add(Calendar.MONTH, Integer.parseInt(Format.split(":")[0].trim()));
			currentCal.add(Calendar.DATE, Integer.parseInt(Format.split(":")[1].trim()));
			currentCal.add(Calendar.YEAR, Integer.parseInt(Format.split(":")[2].trim()));
			ActualDate = dateFormat.format(currentCal.getTime());
		}
		catch (Exception e)
		{
			System.out.println("Exception in Function GetSystemDateinFormatSpecified");
		}
		return ActualDate;
	}
	public String GenerateRandomAlphaNumericValue(String sChar)
	{
		String ActualValue = null;
		try
		{
			int iNumber = 0;
			iNumber = (int) ((Math.random() * 900000) + 100000);
			String value = String.valueOf(iNumber);
			ActualValue = sChar + value;

		}
		catch (Exception e)
		{
			System.out.println("Exception in Function GenerateRandomAlphaNumericValue");
		}
		return ActualValue;
	}

}
