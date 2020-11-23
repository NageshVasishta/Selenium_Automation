package utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.BaseClass;

public class Utilities extends BaseClass {

	// This method enters text in the specified field
	public static void enterText(By locator, String value, String fieldName) {
		driver.findElement(locator).clear();
		driver.findElement(locator).sendKeys(value);
		Log.info("Entered " + fieldName + ": " + value);
	}

	// Click on the Element
	public static void clickElement(By locator, String fieldName) {
		try {
			elementToBeClickable(locator, 15);
			driver.findElement(locator).click();
		} catch (ElementClickInterceptedException e) {
			scrollUntilElementVisible(locator);
			scrollUp();
			elementToBeClickable(locator, 15);
			driver.findElement(locator).click();
		}
		Log.info("Clicked on " + fieldName);
	}

	// Click on the Element
	public static void clickElement(WebElement element, String fieldName) {
		element.click();
		Log.info("Clicked on " + fieldName);
	}

	// select value from DropDown
	public static void chooseFromDropDown(By dropDownLoator, By dropDownOptionsLocator, String value,
			String fieldName) {
		// Click on DropDown
		clickElement(dropDownLoator, fieldName + "Drop Down");

		Boolean flag = false;
		List<WebElement> dropDownOptions = driver.findElements(dropDownOptionsLocator);
		for (WebElement element : dropDownOptions) {
			if (element.getAttribute("textContent").trim().equalsIgnoreCase(value)) {
				element.click();
				Log.info("Clicked on " + fieldName + " - " + value);
				flag = true;
				break;
			}
		}

		if (!flag) {
			Log.error(fieldName + " - " + value + " not found");
			Assert.assertTrue(false, "Select " + fieldName + " - " + value);
		}
	}

	// Select value from Select Locator Drop Down field
	public static void selectDropDown(By selectLocator, String value, String fieldName) {
		Select select = new Select(driver.findElement(selectLocator));
		select.selectByVisibleText(value);
		Log.info("Selected '" + value + "' from " + fieldName + " drop down");
	}

	// Switch to Frame
	public static void switchToFrame(By locator, String frameName) {
		driver.switchTo().frame(driver.findElement(locator));
		Log.info("Switch to Frame: " + frameName);
	}

	// Scroll until Element Visible
	public static void scrollUntilElementVisible(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void elementToBeClickable(By locator, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static void scrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)", "");
	}

	public static void scrollUp() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-250)", "");
	}

	public static boolean isDisplayed(By locator) {
		try {
			driver.findElement(locator).isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static boolean titleContains(String title, int seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			wait.until(ExpectedConditions.titleContains(title));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}
}