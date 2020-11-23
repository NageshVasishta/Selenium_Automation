package Assignment1;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.FramesAndWindows_Page;

public class Case3_Windows_Test extends BaseClass {
	@BeforeMethod(groups = { "positive", "windows" })
	public void initializeBrowser() {
		// Launch Browser and Navigate to URL
		initialization(prop.getProperty("windows_URL"));

		// Verify Alerts page is displayed
		FramesAndWindows_Page.verifyPageTitle();
	}

	@Test(groups = { "positive", "windows" })
	public void openNewTabbedWindow() {
		FramesAndWindows_Page.openNewTabbedWindow();
	}

	@Test(groups = { "positive", "windows" })
	public void openNewSeperateWindow() {
		FramesAndWindows_Page.openNewSeperateWindow();
	}

	@Test(groups = { "positive", "windows" })
	public void openSeperateMultipleWindows() {
		FramesAndWindows_Page.openSeperateMultipleWindows();
	}

	@AfterMethod(groups = { "positive", "windows" })
	public void tearDown() {
		driver.quit();
	}
}
