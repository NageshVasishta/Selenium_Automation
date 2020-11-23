package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import base.BaseClass;
import utilities.Log;
import utilities.Utilities;

public class GilleteHome_Page extends BaseClass {
	private static By account_de_Link = By.className("responsiveAccountHeader_openAccountButton");
	private static By register_de_link = By.className("responsiveAccountHeader_accountRegister");
	private static By register_link = By.className("event_profile_register");
	private static By accountHeader_AccountName = By.xpath("//div[@class='responsiveAccountHeader_accountName']");
	private static By signIn_LoginIn_link = By.xpath("//a[contains(translate(@class,'L','l'),'login')]");

	// Verify the Page Title
	public static void verifyPageTitle() {

		if (driver.getCurrentUrl().contains(".in")) {
			if (!Utilities.titleContains("Gillette India", 30)) {
				Log.error("Gillete Home Page is not displayed");
				Assert.assertTrue(false, "Verify Gillete Home Page is displayed");
			}
		} else {
			if (!Utilities.titleContains("Gillette DE", 30)) {
				Log.error("Gillete Home Page is not displayed");
				Assert.assertTrue(false, "Verify Gillete Home Page is displayed");
			}
		}
		Log.info("Gillete Home Page is displayed");
	}

	public static void navigateToRegister() {
		if (driver.getCurrentUrl().contains(".in")) {
			Utilities.clickElement(register_link, "Register");
		} else {
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(account_de_Link)).build().perform();
			Utilities.clickElement(register_de_link, "Registrieren");
		}
	}

	public static void navigateToLogin() {
		if (driver.getCurrentUrl().contains(".in")) {
			Utilities.clickElement(signIn_LoginIn_link, "SIGN IN");
		} else {
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(account_de_Link)).build().perform();
			Utilities.clickElement(signIn_LoginIn_link, "LOG IN");
		}
	}

	public static void verifyUserLoggedIn_Austria(String username) {
		// Verify the user name
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(account_de_Link)).build().perform();
		if (driver.findElement(accountHeader_AccountName).getAttribute("textContent").contains(username)) {
			Log.info("Account holder name '" + username + "' is displayed");
		} else {
			Log.error("Account holder name '" + username + "' is not displayed");
			Assert.assertTrue(false, "Verify account holder name '" + username + "' is displayed");
		}
	}

}
