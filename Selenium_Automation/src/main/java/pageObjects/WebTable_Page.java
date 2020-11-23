package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.BaseClass;
import utilities.Log;
import utilities.Utilities;

public class WebTable_Page extends BaseClass {
	private static By itemsPerPage_Select = By.xpath("//select[contains(@aria-labelledby,'items-per-page-label')]");
	private static By numberOfpages_Loator = By.xpath("//span[@class='ui-grid-pager-max-pages-number ng-binding']");
	private static By nextPageArrow = By.xpath("//div[@class='last-triangle next-triangle']");
	private static By table_Rows = By.xpath("//div[@class='ui-grid-canvas']//div[@class='ui-grid-row ng-scope']");
	private static By emailCell = By.xpath("//div[@role='gridcell'][1]");

	public static void verify_RegisteredUser(String email) {
		// Wait for 20 seconds for the WebTable Page to be displayed
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.urlContains("WebTable"));
		
		// Verify Register Page is displayed
		if (!driver.getTitle().equals("Web Table")) {
			Log.error("'Web Table' page is not displayed");
			Assert.assertTrue(false, "Verify 'Web Table' page is displayed");
		}
		Log.info("'Web Table' page is displayed");

		/*
		 * // Select Number of Items per page
		 * Utilities.selectDropDown(itemsPerPage_Select, "30", "Items per page");
		 * 
		 * // Verify User is added in the User Table Grid Boolean flag = false; int
		 * numOfpages = Integer.parseInt(
		 * driver.findElement(numberOfpages_Loator).getAttribute("textContent").
		 * replaceAll("\\s+|\\/", "")); for (int i = 0; i < numOfpages; i++) {
		 * List<WebElement> tableRow_ElementList = driver.findElements(table_Rows); for
		 * (WebElement element : tableRow_ElementList) { if
		 * (element.findElement(emailCell).getAttribute("textContent").equals(email)) {
		 * Log.info("User is successfully added in User table grid"); flag =
		 * true; } } if (i < numOfpages - 1) { Utilities.clickElement(nextPageArrow,
		 * "Next Page Arrow"); } }
		 * 
		 * if (!flag) { Log.error("User is not added in the User table grid");
		 * Assert.assertTrue(false, "Verify User is added in the User table grid"); }
		 */
	}
}
