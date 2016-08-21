package com.selenium.utility;

import org.openqa.selenium.WebDriver;

public class ThreadedItems {

	private WebDriver driver;
	private String name;
	
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getTestName() {
		return name;
	}
	
	public void setTestName(String name) {
		this.name = name;
	}

}
