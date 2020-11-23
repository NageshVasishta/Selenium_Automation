package pageObjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;

import base.BaseClass;
import utilities.Log;
import utilities.Utilities;

public class Alerts_Page extends BaseClass {
	private static By alertWithOK = By.partialLinkText("Alert with OK");
	private static By alertWithOkCancel = By.partialLinkText("Alert with OK & Cancel");
	private static By alertWithTextbox = By.partialLinkText("Alert with Textbox");
	private static By BtnToDisplayAlertBx_Btn = By
			.xpath("//button[contains(text(),'click the button to display an  alert box')]");
	private static By BtnToDisplayConfirmBx_Btn = By
			.xpath("//button[contains(text(),'click the button to display a confirm box')]");
	private static By BtnToDemostrateThePromptBx_Btn = By
			.xpath("//button[contains(text(),'click the button to demonstrate the prompt box')]");
	private static By confirmMsgLoactor1 = By.id("demo");
	private static By confirmMsgLoactor2 = By.id("demo1");

	// Verify the Page Title
	public static void verifyPageTitle() {
		String pageTitle = driver.getTitle();
		Assert.assertEquals(pageTitle, "Alerts", "Verify Page Title");
		Log.info("Alerts Page is displayed");
	}

	public static void verify_alertWithOKBtn() {
		// Click on the "Alert with OK button" tab
		Utilities.clickElement(alertWithOK, "Alert with OK button");

		// Click on "click the button to display an alert box" button
		Utilities.clickElement(BtnToDisplayAlertBx_Btn, "click the button to display an  alert box button");

		// Verify Alert box displayed and verify expected text message
		verify_AlertBox_Displayed_TextDisplayed("Alert with OK button", "I am an alert box!");

		// Click OK button on the Alert button
		driver.switchTo().alert().accept();
		Log.info("Clicked on Ok button");
	}

	public static void verify_AlertWithOKCancelBtn() {
		// Click on the "Alert with OK button" tab
		Utilities.clickElement(alertWithOkCancel, "Alert with OK & Cancel");

		// Click on "click the button to display a confirm box" button
		Utilities.clickElement(BtnToDisplayConfirmBx_Btn, "click the button to display a confirm box button");

		// Verify Alert box displayed and verify expected text message
		verify_AlertBox_Displayed_TextDisplayed("Alert with OK button", "Press a Button !");

		// Click OK button on the Alert button
		driver.switchTo().alert().accept();
		Log.info("Clicked on Ok button");

		// Verify the message displayed below the button
		verify_ConfirmMsg(confirmMsgLoactor1, "You pressed Ok");

		// Click on "click the button to display a confirm box" button
		Utilities.clickElement(BtnToDisplayConfirmBx_Btn, "click the button to display a confirm box button");

		// Click Cancel button on the Alert button
		driver.switchTo().alert().dismiss();
		Log.info("Clicked on Cancel button");

		// Verify the message displayed below the button
		verify_ConfirmMsg(confirmMsgLoactor1, "You Pressed Cancel");
	}

	public static void verify_AlertWithTextBox(String name) {
		// Click on the "Alert with Textbox" tab
		Utilities.clickElement(alertWithTextbox, "Alert with Textbox");

		// Click on "click the button to demonstrate the prompt box" button
		Utilities.clickElement(BtnToDemostrateThePromptBx_Btn, "click the button to demonstrate the prompt box button");

		// Verify Alert box displayed and verify expected text message
		verify_AlertBox_Displayed_TextDisplayed("Alert with Textbox", "Please enter your name");
		Log.info("Entered '" + name + "' in the prompt box");

		// Enter Name in the Prompt box
		driver.switchTo().alert().sendKeys(name);

		// Click OK button on the Alert button
		driver.switchTo().alert().accept();
		Log.info("Clicked on Ok button");

		// Verify the message displayed below the button
		verify_ConfirmMsg(confirmMsgLoactor2, "Hello " + name + " How are you today");

		// Click on "click the button to demonstrate the prompt box" button
		Utilities.clickElement(BtnToDemostrateThePromptBx_Btn, "click the button to demonstrate the prompt box button");

		// Enter Name in the Prompt box
		driver.switchTo().alert().sendKeys(name);
		Log.info("Entered '" + name + "' in the prompt box");

		// Click Cancel button on the Alert button
		driver.switchTo().alert().dismiss();
		Log.info("Clicked on Cancel button");

		// Verify the message displayed below the button
		verify_ConfirmMsg(confirmMsgLoactor2, "");
	}

	public static void verify_AlertBox_Displayed_TextDisplayed(String alertBoxType, String alertBoxText) {
		// Verify Alert box is displayed
		Alert alert = null;
		try {
			alert = driver.switchTo().alert();
		} catch (Exception e) {
			Log.error(alertBoxType + " is not displayed");
			Assert.assertTrue(false, "Verify " + alertBoxType + " is displayed");
		}

		// Verify message displayed in Alert Box
		String alertMsg = alert.getText();
		if (alertMsg.equals(alertBoxText)) {
			Log.info("Alert Message \"" + alertBoxText + "\" is displayed");
		} else {
			Log.error("Alert Message \"" + alertBoxText + "\" is not displayed");
			Assert.assertTrue(false, "Verify Alert Message \"" + alertBoxText + "\" is displayed");
		}
	}

	// Verify the message displayed below the button
	public static void verify_ConfirmMsg(By locator, String value) {
		String confirmMsg = driver.findElement(locator).getAttribute("textContent");
		if (confirmMsg.contentEquals(value)) {
			if (confirmMsg.isEmpty()) {
				Log.error("No message is displayed");
			} else {
				Log.info("Message \"" + value + "\" is displayed");
			}
		} else {
			Assert.assertEquals(confirmMsg, value, "Verfy Confirmation Message is displayed");
		}
	}

}
