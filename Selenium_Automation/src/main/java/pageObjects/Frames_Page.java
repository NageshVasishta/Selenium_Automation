package pageObjects;

import org.openqa.selenium.By;
import org.testng.Assert;

import base.BaseClass;
import utilities.Log;
import utilities.Utilities;

public class Frames_Page extends BaseClass {
	private static By singleFrame_Link = By.partialLinkText("Single Iframe");
	private static By iframeWithInAnIframe_Link = By.partialLinkText("Iframe with in an Iframe");
	private static By singleFrame = By.xpath("//iframe[@id='singleframe']");
	private static By mutipleFrame_OuterFrame = By.xpath("//iframe[contains(@src,'MultipleFrames')]");
	private static By mutipleFrame_InnerFrame = By.xpath("//iframe[contains(@src,'SingleFrame')]");
	private static By inputbox = By.xpath("//input[@type='text']");

	// Verify the Page Title
	public static void verifyPageTitle() {
		String pageTitle = driver.getTitle();
		Assert.assertEquals(pageTitle, "Frames", "Verify Page Title");
		Log.info("Page with title \""+pageTitle+"\" is displayed");
	}

	public static void singleFrame(String value) {
		// Click on Single Iframe
		Utilities.clickElement(singleFrame_Link, "Single Iframe tab");

		// Switch To Frame
		Utilities.switchToFrame(singleFrame, "SingleFrame");

		// Enter value in text box present inside Single Frame
		Utilities.enterText(inputbox, value, "Text");
		
		driver.switchTo().defaultContent();
	}

	public static void multipleFrame(String value) {
		// Click on Iframe with in an Iframe
		Utilities.clickElement(iframeWithInAnIframe_Link, "Iframe with in an Iframe tab");

		// Switch To Frame
		Utilities.switchToFrame(mutipleFrame_OuterFrame, "Outer Frame");

		// Switch To Frame
		Utilities.switchToFrame(mutipleFrame_InnerFrame, "Inner Frame");

		// Enter value in text box present inside mutiple frames
		Utilities.enterText(inputbox, value, "Text");
		
		driver.switchTo().defaultContent();
	}

}
