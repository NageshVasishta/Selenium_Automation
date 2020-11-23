package pageObjects;

import org.openqa.selenium.By;
import org.testng.Assert;

import base.BaseClass;
import utilities.Log;
import utilities.Utilities;

public class SignIn_Page extends BaseClass {
	private static By header = By.id("phdesktopbody_0_Header");
	private static By userName_IP = By.xpath("//input[contains(@name,'name')]");
	private static By password_IP = By.xpath("//input[contains(@type,'password')]");
	private static By signIn_Btn = By.xpath("//input[contains(@id,'Sign In')]");
	private static By logIn_Btn = By.xpath("//button[@id='login-submit']");

	// Verify "Your Registration Is Complete" message is displayed
	public static void verifyRegisteredSuccessfully() {
		if (!Utilities.isDisplayed(header)) {
			Log.error("'Your Registration Is Complete' message is not displayed");
			Assert.assertTrue(false, "Verfiy 'Your Registration Is Complete' message is displayed");
		} else if (driver.findElement(header).getAttribute("textContent").equals("Your Registration Is Complete")) {
			Log.info("'Your Registration Is Complete' message is displayed");
		} else {
			Log.error("'Your Registration Is Complete' message is not displayed");
			Assert.assertTrue(false, "Verfiy 'Your Registration Is Complete' message is displayed");
		}
	}
	
	public static void login(String email, String password) {
		// Enter User name, Password
		Utilities.enterText(userName_IP, email, "Email address");
		Utilities.enterText(password_IP, password, "Password");
		
		// Click on SignIn/Login
		if(driver.getCurrentUrl().contains(".in")) {
			Utilities.clickElement(signIn_Btn, "SIGN IN");
		}else {
			Utilities.clickElement(logIn_Btn, "LOG IN");
		}
	}
}
