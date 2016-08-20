package com.selenium.common;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Verifications extends Utilities {

	protected WebDriverWait wait;
	public void verifyTextPresent(WebElement elm, String sExpected)
	{
		waitForVisible(elm);
		sExpected = sExpected.trim().toUpperCase();
		String sActual = getText(elm);
		
		Assert.assertTrue(sActual.toUpperCase().contains(sExpected.trim()), "Expected: " + sExpected + " Actual: " + sActual);
	}

	public void verifyTextBoxAsBlank(WebElement elm)
	{
		
		String sActual = elm.getText();
		
		Assert.assertTrue(sActual.toUpperCase().contentEquals(""), "Expected: is NULL and  Actual: " + sActual);
	}
	
	public void verifyObjectNotBlank(WebElement elm)
	{
		String sActual = elm.getText();	
		Assert.assertTrue(!(sActual.toUpperCase().contentEquals("")));
	}
	protected String getText(WebElement elm)
	{
		String sActual = elm.getText().toUpperCase();
		if (sActual.equalsIgnoreCase(""))
		{
			try
			{
				sActual = elm.getAttribute("value");
			}
			catch (Exception e)
			{}
			try
			{
				if (sActual.equalsIgnoreCase(""))
				{
					sActual = elm.getAttribute("innerHTML");
				}
			}
			catch (Exception e)
			{}
			try
			{
				if (sActual.equalsIgnoreCase(""))
				{
					sActual = elm.getAttribute("text");
				}
			}
			catch (Exception e)
			{}
		}
		return sActual.toUpperCase();
	}
	public void verifyTextNotPresent(WebElement object, String sExpected)
	{
		waitForVisible(object);
		sExpected = sExpected.trim().toUpperCase();
		String sActual = getText(object);

		Assert.assertFalse(sActual.contains(sExpected.trim()), "Expected: " + sExpected + " Actual: " + sActual);

	}
	public void verifyTitle(WebDriver driver, String sExpected)
	{
		String sActual = driver.getTitle().toUpperCase();
		sExpected = sExpected.trim().toUpperCase();
		Assert.assertTrue(sActual.contains(sExpected), "Expected: " + sExpected + " Actual: " + sActual);

	}
	public void verifyTitleNotPresent(WebDriver driver, String sExpected)
	{
		String sActual = driver.getTitle().toUpperCase();
		sExpected = sExpected.trim().toUpperCase();
		Assert.assertFalse(sActual.contains(sExpected), "Expected: " + sExpected + " Actual: " + sActual);
	}
	public void verifyAlertText(WebDriver driver, String sExpected)
	{
		Alert alert = driver.switchTo().alert();
		String sActual = alert.getText().toUpperCase();
		sExpected = sExpected.trim().toUpperCase();
		Assert.assertTrue(sActual.contains(sExpected), "Expected: " + sExpected + " Actual: " + sActual);
	}
	public void verifyObjectNotNull(WebElement elm )
	{
		Assert.assertNotNull(elm);
	}
	public void verifyTableText(WebElement object, String sText)
	{
		waitForVisible(object);
		String sElementText = null;
		sText = sText.trim().toUpperCase();
		boolean bFound = false;
		List<WebElement> child = findChildren(object);
		if (child != null)
		{
			for (WebElement webElement : child)
			{
				sElementText = webElement.getText().trim().toUpperCase();
				if (sElementText.contains(sText.trim()))
				{
					bFound = true;
					break;
				}
			}
		}
		Assert.assertTrue(bFound, "Expected text: " + sText + " Table contents: " + object.getText());
	}
	public List<WebElement> findChildren(WebElement object)
	{
		waitForVisible(object);
		List<WebElement> children = object.findElements(By.cssSelector("*"));

		return children;
	}
	public void verifyRadioSelected(WebElement object)
	{
		waitForVisible(object);
		Assert.assertTrue(object.isSelected());
	}
	public void verifyRadioNotSelected(WebElement object)
	{
		waitForVisible(object);
		Assert.assertFalse(object.isSelected());
	}
	public void verifyTableRowCount(WebElement object, int RowCount)
	{
		waitForVisible(object);
		List<WebElement> sRow = object.findElements(By.xpath(".//tr"));
		Assert.assertEquals(sRow.size() - 1, RowCount, "Actual: " + (sRow.size() - 1) + " Expected: " + RowCount);
	}

	public void verifyValue(WebElement elm, String sExpected)
	{
		waitForVisible(elm);
		sExpected = sExpected.trim().toUpperCase();
		String sActual = elm.getAttribute("value");
		sActual = sActual.toUpperCase();
		Assert.assertTrue(sActual.contains(sExpected.trim()), "Expected: " + sExpected + " Actual: " + sActual);
	}

	public void verifyElementExistence (WebElement element)
	{
		waitForVisible(element);
		Assert.assertEquals(element.isDisplayed(), true);		
	}
	
	public void verifyIsEnabled (WebElement element)
	{
		waitForVisible(element);
		Assert.assertEquals(element.isEnabled(), true);
	}

	public void verifyIsDisabled(WebElement element)
	{
		waitForVisible(element);
		Assert.assertEquals(element.isEnabled(), false);
	}
	
	public void verifyTableHeaders(WebElement element, String sText)
	{
		waitForVisible(element);
		boolean bfound = false;
		ArrayList<String> headers = new ArrayList<String>();
		for (String s1 : sText.split(";"))
		{
			headers.add(s1);
		}
		String ss = "";
		List<WebElement> colHeaders = element.findElements(By.tagName("th"));
		System.out.println(colHeaders.size());
		for (WebElement col : colHeaders)
		{
			System.out.println(col.getText());
			ss = col.getText();
			if (ss.length() > 1)
			{
				if (headers.contains(ss))
					bfound = true;				
				else
				{
					bfound = false;
					break;
				}
			}
		}
		Assert.assertEquals(bfound,true);
	}
	
	public void waitForVisible(WebElement element)
	{
//		wait.until(ExpectedConditions.visibilityOf(element));
	}

}
