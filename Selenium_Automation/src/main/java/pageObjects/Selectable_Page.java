package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import base.BaseClass;
import utilities.Log;
import utilities.Utilities;

public class Selectable_Page extends BaseClass {
	private static By defaultFunctionality_link = By.partialLinkText("Default Functionality");
	private static By serialize_link = By.partialLinkText("Serialize");
	private static By defaultFuncTabs = By.xpath("//ul[@class='deaultFunc']/li");
	private static By serializeFuncTabs = By.xpath("//ul[@class='SerializeFunc']/li");
//	private static By defaultFuncTabs = By.xpath("//ul[@class='deaultFunc']/li/b");
//	private static By serializeFuncTabs = By.xpath("//ul[@class='SerializeFunc']/li/b");
	private static By feedBack1 = By.xpath("//p[@id='feedback']/span");
	private static By feedBack2 = By.xpath("//p[@id='feedback']/span[2]");

	// Verify the Page Title
	public static void verifyPageTitle() {
		if (!Utilities.titleContains("Selectable", 30)) {
			Log.error("Selectable Page is not displayed");
			Assert.assertTrue(false, "Verify Selectable Page is displayed");
		}
		Log.info("Selectable Page is displayed");
	}

	public static void selectable_DefaultFunctionality() {
		String tabName = "";

		// Click on Default Functionality tab
		Utilities.clickElement(defaultFunctionality_link, "Default Functionality tab");

		// Select Individual tabs and verify text displayed
		List<WebElement> tabsList = driver.findElements(defaultFuncTabs);
		for (WebElement element : tabsList) {
			tabName = element.getAttribute("textContent");

			// Click on the tab
			Utilities.clickElement(element, tabName);

			// Verify tab is selected
			if (element.getAttribute("class").contains("selected")) {
				Log.info(tabName + " is selected");
			} else {
				Log.error(tabName + " is not selected");
				Assert.assertTrue(false, "Verify " + tabName + " is selected");
			}
		}
	}

	public static void verify_DefaultFunctionality_multipleSelect() {
		String tabName = "";

		// Click on Default Functionality tab
		Utilities.clickElement(defaultFunctionality_link, "Default Functionality tab");

		// Select Individual tabs and verify text displayed
		Actions action = new Actions(driver);
		List<WebElement> tabsList = driver.findElements(defaultFuncTabs);
		for (WebElement element : tabsList) {
			tabName = element.getAttribute("textContent");

			// Click on the tab
			// Hold control key and click on the tab
			action.keyDown(Keys.CONTROL).click(element).build().perform();
			Log.info("Clicked on " + tabName);
			Utilities.clickElement(element, tabName);

			// Verify tab is selected
			if (element.getAttribute("class").contains("selected")) {
				Log.info(tabName + " is selected");
			} else {
				Log.error(tabName + " is not selected");
				Assert.assertTrue(false, "Verify " + tabName + " is selected");
			}
		}
		
		//Verify all the Tabs are Selected
		int tabSelectedCount = 0;
		for (WebElement element : tabsList) {
			if (element.getAttribute("class").contains("selected")) {
				tabSelectedCount+=1;
			}
		}
		if(tabSelectedCount>1) {
			Log.error("All the tabs are selected");
			Assert.assertTrue(false, "Verify all the tabs are not selected");
		}else {
			Log.info("Multiple tabs are not selected in Default Functionality");
		}
	}

	public static void selectable_Serialize() {
		String tabName = "", actualfeedback = "", expectedFeedback = "", tabNames = "";

		// Click on Serialize tab
		Utilities.clickElement(serialize_link, "Serialize tab");

		// Select Individual tabs and verify text displayed
		List<WebElement> tabsList = driver.findElements(serializeFuncTabs);
		for (WebElement element : tabsList) {
			tabName = element.getAttribute("textContent");

			// Click on the tab
			Utilities.clickElement(element, tabName);

			// Verify tab is selected
			if (element.getAttribute("class").contains("selected")) {
				Log.info(tabName + " is selected");
			} else {
				Log.error(tabName + " is not selected");
				Assert.assertTrue(false, "Verify " + tabName + " is selected");
			}

			actualfeedback = driver.findElement(feedBack1).getAttribute("textContent");
			actualfeedback = (actualfeedback + " " + driver.findElement(feedBack2).getAttribute("textContent"))
					.replaceAll("\\s+", " ");

			tabName = tabName.split(" - ")[1];
			tabNames = tabNames + " , " + tabName;
			expectedFeedback = "You've selected: " + tabName;
			if (actualfeedback.equals(expectedFeedback)) {
				Log.info("The message '" + actualfeedback + " is displayed");
			} else {
				Log.error("The message '" + expectedFeedback + "' is not displayed");
				Assert.assertTrue(false, "Verify the message '" + actualfeedback + " is displayed");
			}
		}

		// Select multiple tabs verify text displayed
		driver.navigate().refresh();
		Utilities.clickElement(serialize_link, "Serialize tab");
		List<WebElement> tabsList2 = driver.findElements(serializeFuncTabs);
		Actions action = new Actions(driver);
		for (int i = 0; i < tabsList2.size(); i++) {
			tabName = tabsList2.get(i).getAttribute("textContent");

			if (i == 0) {
				Utilities.clickElement(tabsList2.get(i), tabName);
			} else {
				// Hold control key and click on the tab
				action.keyDown(Keys.CONTROL).click(tabsList2.get(i)).build().perform();
				Log.info("Clicked on " + tabName);
			}
		}

		// Verify All the tabs are selected
		for (WebElement element : tabsList2) {
			// Verify tab is selected
			if (element.getAttribute("class").contains("selected")) {
				Log.info(tabName + " is selected");
			} else {
				Log.error(tabName + " is not selected");
				Assert.assertTrue(false, "Verify " + tabName + " is selected");
			}
		}
		Log.info("Selected all the tabs");

		// Verify the text displayed
		tabNames = tabNames.substring(3, tabNames.length());
		expectedFeedback = "You've selected: " + tabNames;
		actualfeedback = driver.findElement(feedBack1).getAttribute("textContent");
		actualfeedback = actualfeedback + " " + driver.findElement(feedBack2).getAttribute("textContent");
		if (actualfeedback.equals(expectedFeedback)) {
			Log.info(actualfeedback + " is displayed");
		} else {
			Log.error("The message '" + expectedFeedback + "' is not displayed");
			Assert.assertTrue(false, "Verify the message '" + actualfeedback + " is displayed");
		}
	}
}
