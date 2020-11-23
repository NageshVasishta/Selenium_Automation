package pageObjects;

import java.util.Set;

import org.openqa.selenium.By;
import org.testng.Assert;

import base.BaseClass;
import utilities.Log;
import utilities.Utilities;

public class FramesAndWindows_Page extends BaseClass {
	private static By opneNewTabbedWindow_Link = By.partialLinkText("Open New Tabbed Windows");
	private static By openNewSeperateWindow_Link = By.partialLinkText("New Seperate Windows");
	private static By openSeperateMultipleWindows_Link = By.partialLinkText("Open Seperate Multiple Windows");
	private static By tabbedWindowClick_Btn = By.xpath("//div[@id='Tabbed']//button");
	private static By seperateWindowClick_Btn = By.xpath("//div[@id='Seperate']//button");
	private static By multipleWindowClick_Btn = By.xpath("//div[@id='Multiple']//button");

	// Verify the Page Title
	public static void verifyPageTitle() {
		String pageTitle = driver.getTitle();
		Assert.assertEquals(pageTitle, "Frames & windows", "Verify Page Title");
		Log.info("Page with title \""+pageTitle+"\" is displayed");
	}

	public static void openNewTabbedWindow() {
		// Click on "Open New Tabbed Windows" tab
		Utilities.clickElement(opneNewTabbedWindow_Link, "Open New Tabbed Windows");

		// Click on "Click" button
		Utilities.clickElement(tabbedWindowClick_Btn, "Click button");

		verify_NumOfWinOpened_SwitchWindow(2);
	}

	public static void openNewSeperateWindow() {
		// Click on "New Seperate Windows" tab
		Utilities.clickElement(openNewSeperateWindow_Link, "New Seperate Windows");

		// Click on "Click" button
		Utilities.clickElement(seperateWindowClick_Btn, "Click button");

		verify_NumOfWinOpened_SwitchWindow(2);
	}
	
	public static void openSeperateMultipleWindows() {
		// Click on "New Seperate Windows" tab
		Utilities.clickElement(openSeperateMultipleWindows_Link, "New Seperate Windows");

		// Click on "Click" button
		Utilities.clickElement(multipleWindowClick_Btn, "Click button");

		verify_NumOfWinOpened_SwitchWindow(3);
		Log.info("Multiple Windows are displayed");
	}

	public static void verify_NumOfWinOpened_SwitchWindow(int expectedWindowCount) {
		// Get window handles of all the windows
		Set<String> windowHandles = driver.getWindowHandles();
		String parent_Window = driver.getWindowHandle();

		// Verify Number of Windows displayed
		int windowCount = windowHandles.size();
		if (windowCount == 1) {
			Log.error("New Window is not opened");
			Assert.assertTrue(false, "Verify New Window is opened");
		} else if (windowCount > expectedWindowCount) {
			Log.error(windowCount + " windows are displayed but expected to display "+expectedWindowCount+" windows");
			Assert.assertTrue(false, windowCount + " windows are displayed but expected to display "+expectedWindowCount+" windows");
		}
		Log.info("New Window is opened");

		// Switched to all new windows
		Set<String> windows = driver.getWindowHandles();
		for (String winID : windows) {
			if (!winID.equals(parent_Window)) {
				driver.switchTo().window(winID);
				driver.manage().window().maximize();
			}
		}
		
		// Switch back to Parent Window
		driver.switchTo().window(parent_Window);
	}
}
