package com.selenium.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import com.selenium.utility.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest extends Verifications implements ITest {
	private static ThreadLocal<ThreadedItems> threadDriver;
	public static WebDriver getDriver()
	{

		String browser = System.getProperty("browser.type");
		String local = System.getProperty("run.local");

		initializeThreadLocal();

		if (threadDriver.get().getDriver() == null)
		{

			DesiredCapabilities dc = null;

			if ("chrome".equals(browser))
			{
				dc = DesiredCapabilities.chrome();
				dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ignore");
				dc.setBrowserName("chrome");
				dc.setPlatform(Platform.fromString("Win7"));
				dc.setVersion("48"); // 8,9,10 or 11
				if (!"false".equals(local))
				{
					threadDriver.get().setDriver(new ChromeDriver(dc));
				}
			}
			else if (browser != null && browser.startsWith("ie"))
			{
				dc = DesiredCapabilities.internetExplorer();
				dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ignore");
				dc.setBrowserName("internet explorer");
				dc.setPlatform(Platform.fromString("Win7"));
				System.setProperty("webdriver.ie.driver",Settings.sDriverPath+"\\IEDriverServer.exe"); 
				dc.setVersion(browser.replaceAll("[^0-9]+", "")); // 8,9,10 or
																	// 11
				if (!"false".equals(local))
				{
					threadDriver.get().setDriver(new InternetExplorerDriver(dc));
				}
			}
			/*else if ("headless".equals(browser))
			{
				dc = DesiredCapabilities.htmlUnitWithJs();
				dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ignore");
				threadDriver.get().setDriver(new HtmlUnitDriver(dc));
			}*/
			else
			{
				dc = DesiredCapabilities.firefox();
				dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "ignore");
				dc.setBrowserName("firefox");
				dc.setPlatform(Platform.fromString("Win7"));
				dc.setVersion("44");
				FirefoxBinary ffbinary=new FirefoxBinary(new File("C:\\Users\\ac98587\\AppData\\Local\\Mozilla Firefox\\firefox.exe"));
				FirefoxProfile profile = new FirefoxProfile();
				profile.setPreference("toolkit.startup.max_resumed_crashes", "-1");
				dc.setCapability(FirefoxDriver.PROFILE, profile);
				dc.setCapability(FirefoxDriver.BINARY, ffbinary);
				if (!"false".equals(local))
				{
					threadDriver.get().setDriver(new FirefoxDriver(dc));
				}
			}

			if ("false".equals(local) && !"headless".equals(browser))
			{
				try
				{
					threadDriver.get().setDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc));
				}
				catch (MalformedURLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return threadDriver.get().getDriver();
	}

	public void wait(int seconds)
	{

		try
		{
			Thread.sleep(seconds);
		}
		catch (Exception e)
		{

		}
	}

	@BeforeMethod(alwaysRun = true)
	protected void initializeWebDriver(Method method, Object[] testData)
	{
		String params = "";
		if (testData != null)
		{

			for (int i = 0; i < testData.length && params.length() < 225; i++)
			{
				if (testData[i] != null)
					params += testData[i].toString() + ", ";
				else
					params += "NULL, ";
			}

		}

		if (params.length() >= 2)
			params = params.substring(0, params.length() - 2);

		initializeThreadLocal();
		threadDriver.get().setTestName(String.format("%s(%s)", method.getName(), params));

		killBogusProcesses();

		getDriver().manage().timeouts().implicitlyWait(Constants.WEBDRIVER_TIMEOUT, TimeUnit.SECONDS);
		getDriver().manage().window().maximize();
	}

	@AfterMethod(alwaysRun = true)
	
	protected void killWebDriver()
	{
		threadDriver.get().getDriver().quit();
		threadDriver.get().setDriver(null);

		killBogusProcesses();
	}

	private boolean processIsRunning(String name) throws IOException
	{

		Process p = Runtime.getRuntime().exec(Constants.TASKLIST);
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

		String line;
		while ((line = reader.readLine()) != null)
		{
			if (line.contains(name))
			{
				return true;
			}
		}

		return false;

	}

	private void killProcess(String name)
	{

		try
		{
			if (processIsRunning(name))
				Runtime.getRuntime().exec(Constants.KILL + name);
		}
		catch (IOException ioe)
		{}
	}

	private void killBogusProcesses()
	{
		String processName = "";
		String browser = System.getProperty("browser.type");

		if ("chrome".equals(browser))
		{
			processName = "chrome,chromedriver";
		}
		else if ("ie".equals(browser))
		{
			processName = "IEDriverServer,iexplore";
		}
		else
		{
			processName = "firefox";
		}

		String[] processes = processName.split(",");

		for (String process : processes)
		{
			killProcess(process);
		}

	}

	//@Override
	public String getTestName()
	{
		initializeThreadLocal();
		String name = threadDriver.get().getTestName();
		if (name == null)
		{
			name = "";
		}
		return name;
	}

	private static void initializeThreadLocal()
	{

		if (threadDriver == null)
		{
			threadDriver = new ThreadLocal<ThreadedItems>();
		}

		if (threadDriver.get() == null)
		{
			threadDriver.set(new ThreadedItems());
		}
	}

}
