package com.selenium.common;

import java.util.List;
import java.util.concurrent.TimeUnit;
import com.selenium.utility.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;
	public BasePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Constants.WEBDRIVER_TIMEOUT);
		waitForLoad();
		verifyPage();

	}

	public abstract void verifyPage();
	public abstract void waitForLoad();
	public void selectBox(WebElement item)
	{
		waitForClickable(item);
		if (!item.isSelected())
		{
			item.click();
		}
	}

	public void unSelectBox(WebElement item)
	{
		waitForClickable(item);
		if (item.isSelected())
		{
			item.click();
		}
	}
	public boolean iselementExists (WebElement element)
	{
		boolean result=false;;
		try
		{
		
		if(element.isDisplayed())
			result=true;
		}
		catch(Exception e)
		{
			result=false;
		}
		return result;
	}
	
	public boolean iselementEnabled (WebElement element)
	{
		boolean result=false;;
		try
		{
		if(element.isEnabled())
			result=true;
		}
		catch(Exception e)
		{
			result=false;
		}
		return result;
	}
	public void handleBox(WebElement item, Boolean selected)
	{

		waitForClickable(item);
		if (selected)
		{
			selectBox(item);
		}
		else
		{
			unSelectBox(item);
		}
	}

	public void selectByText(WebElement selElement, String text)
	{

		waitForClickable(selElement);
		Select item = new Select(selElement);
		item.selectByVisibleText(text);
	}

	public void selectByValue(WebElement selElement, String value)
	{

		waitForClickable(selElement);
		Select item = new Select(selElement);
		item.selectByValue(value);
	}

	public void enterText(WebElement input, String text)
	{
		if (text == null || text.isEmpty())
			return;

		waitForClickable(input);
		input.clear();

		if (text.equals("NULL"))
			return;

		input.sendKeys(text);
	}

	public void click(WebElement elem)
	{

		waitForClickable(elem);
		elem.click();
		
	}

	public void clear(WebElement elem)
	{

		waitForClickable(elem);
		elem.clear();
	}

	public void executeJavaScript(String scriptToExecute)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(scriptToExecute);
	}

	public void specialClick(WebElement element)
	{
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click().build().perform();
	}

	public void hover(WebElement element)
	{
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.build().perform();
	}

	public void changeWait(int waitTime)
	{
		driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
	}

	public void resetWait()
	{
		driver.manage().timeouts().implicitlyWait(Constants.WEBDRIVER_TIMEOUT, TimeUnit.SECONDS);
	}

	public void waitForVisible(WebElement element)
	{
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForClickable(WebElement element)
	{
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void acceptAlert()
	{

		try
		{
		if (wait.until(ExpectedConditions.alertIsPresent()) != null)
		{
			Alert alert = driver.switchTo().alert();
			alert.accept();
		}
		}
		catch(Exception e)
		{
			
		}

	}
	public void dismissAlert()
	{

		if (wait.until(ExpectedConditions.alertIsPresent()) != null)
		{
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		}

	}
	public void WaitForElementPresent(WebElement element)
	{
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void selectFromDropDown(WebElement element, String sValue)
	{

			if (element != null && element.isEnabled())
			{
				Select dropdown = new Select(element);
				String content = element.getText();
				if (!content.contentEquals(sValue))
					dropdown.selectByVisibleText(sValue);
			}

	}
	public String getValueFromTextBox(WebElement element)
	{
		String ActualText=null;
		if(element!=null)
		{
			String content = element.getAttribute("value");
			ActualText = content;
		}
		return ActualText;
	}
	public void sendKeyPress(WebElement elm, Keys k)
	{
		if (elm != null)
			elm.sendKeys(k);
	
	}
	@SuppressWarnings("null")
	public String GetText(WebElement element)
	{
		String content = null;
		if (element == null)
			content = element.getText();
		return content;
	}
	public void selectElementfromDropDownContains(WebElement object, String sValue)
	{		
			List<WebElement> sOptions;
			String content = object.getText();
			Select dropdown = new Select(object);
			sOptions = dropdown.getOptions();
			for (WebElement s : sOptions)
			{
				if (s.getText().contains(sValue) && !sValue.contains(content))
				{
					dropdown.selectByVisibleText(s.getText());
					object.sendKeys("[Enter]");					
				}
			}		
	} 
	
	public String GetObjectProperty(WebElement element,String Property)
	{
		String content = null;
		content = element.getAttribute(Property);
		return content;
	}


}
