package pageObjects;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import base.BaseClass;
import utilities.Log;
import utilities.Utilities;

public class CreateProfile_Page extends BaseClass {
	private static By fullName_IP = By.id("customerName");
	private static By firstName_IP = By.xpath("//input[contains(@name,'firstname')]");
	private static By lastName_IP = By.xpath("//input[contains(@name,'lastname')]");
	private static By email_IP = By.xpath("//input[contains(translate(@id,'E','e'),'email')]");
	private static By confirm_Email_IP = By.id("confirmCustomerEmail");
	private static By password_IP = By.xpath("//input[contains(translate(@id,'P','p'),'password')]");
	private static By confirmPassword_IP = By.xpath(
			"//input[contains(translate(@id,'C','c'),'confirm') and contains(translate(@id,'P','p'),'password')]");
	private static By birthMonth_Select = By.xpath("//select[contains(@name,'month')]");
	private static By birthYear_Select = By.xpath("//select[contains(@name,'year')]");
	private static By zipCode_Select = By.xpath("//input[contains(@name,'postalarea')]");
	private static By receiveEmailOffers_Yes_RadioBtn = By.id("OptInReceiveNewsLetterRadio1");
	private static By receiveEmailOffers_No_RadioBtn = By.id("OptInReceiveNewsLetterRadio2");
	private static By createProfile_Register_Btn = By.xpath("//*[@id='continue' or contains(@name,'submit')]");
	private static By emailErrorMsg_in = By.xpath("//span[@class='error-message' and contains(@id,'emails')]");
	private static By emailErrorMsg_de = By.xpath("//span[@id='customerEmail.errors']");
	private static By passwordErrorMsg_in = By.xpath("//span[@class='error-message' and contains(@id,'password')]");
	private static By fullNameError_de = By.xpath("//span[@id='customerName.errors']");
	private static By passwordError_de = By.xpath("//span[@id='customerPassword.errors']");
	private static By optInReceiveNewsLetterRadio_de = By.xpath("//span[@id='OptInReceiveNewsLetterRadio.errors']");

	public static void register(String firstName, String lastName, String email, String password,
			String birthMonth, String birthYear, String zipCode, String receiveEmailsForOffers) {
		String region;
		if (driver.getCurrentUrl().contains(".in")) {
			region = "in";
		} else {
			region = "de";
		}

		if (region.equals("in")) {
			// Enter First name, Last name
			Utilities.enterText(firstName_IP, firstName, "FIRSTNAME");
			Utilities.enterText(lastName_IP, lastName, "LAST NAME");

			// Select Birthday month and year
			Utilities.selectDropDown(birthMonth_Select, birthMonth, "BIRTHDATE Month");
			Utilities.selectDropDown(birthYear_Select, birthYear, "BIRTHDATE Year");

			// Enter Zipcode
			Utilities.enterText(zipCode_Select, zipCode, "ZIP CODE");
		} else {
			String fullName = firstName +" "+ lastName;
			Utilities.enterText(fullName_IP, fullName, "Full Name");
			Utilities.enterText(confirm_Email_IP, email, "Confirm email address");

			// Select Yes/No Radio button to "Receive Email for Offers"
			if (receiveEmailsForOffers.equalsIgnoreCase("Yes")) {
				Utilities.clickElement(receiveEmailOffers_Yes_RadioBtn, "Receive Email for Offers Yes radio button");
			} else {
				Utilities.clickElement(receiveEmailOffers_No_RadioBtn, "Receive Email for Offers No radio butto");
			}
		}

		// Enter Email address, Password and Confirm Password
		Utilities.enterText(email_IP, email, "EMAIL");
		Utilities.enterText(password_IP, password, "Password");
		Utilities.enterText(confirmPassword_IP, password, "Confirm Password");

		// Click on Create Profile/Register
		Utilities.clickElement(createProfile_Register_Btn, "CreateProfile/Register button");
	}

	public static void verifyEmailIDError(String emailID) {
		String errMsg;
		By errMsgLocator;

		if (driver.getCurrentUrl().contains(".in")) {
			errMsgLocator = emailErrorMsg_in;
		} else {
			errMsgLocator = emailErrorMsg_de;
		}

		// Verify Error Message is displayed
		if (!Utilities.isDisplayed(errMsgLocator)) {
			Log.error("Email Validation message is not displayed when entered invalid email ID: " + emailID);
			Assert.assertTrue(false,
					"Verify Email Validation message is displayed when entered invalid email ID: " + emailID);
		}
		errMsg = driver.findElement(errMsgLocator).getAttribute("textContent");
		Log.info(
				"Email Validation message \"" + errMsg + "\" is displayed when entered invalid email ID: " + emailID);
	}

	public static void passwordValidation_in() {

		Random rnd = new Random();
		String number_password = Integer.toString(rnd.nextInt(99999999));
		String alphabets_password = RandomStringUtils.randomAlphabetic(9);
		String lessThanEightChar_password = RandomStringUtils.randomAlphanumeric(6);

		String[] passwords = { number_password, alphabets_password, lessThanEightChar_password };

		String errMsg;
		for (int i = 0; i < passwords.length; i++) {

			// Enter Password
			Utilities.enterText(password_IP, passwords[i], "Password");
			driver.findElement(password_IP).sendKeys(Keys.TAB);

			// Verify error message field is present
			if (!Utilities.isDisplayed(passwordErrorMsg_in)) {
				Assert.assertTrue(false, "Password Validation Message is not displayed");
			}

			// verify error message displayed for invalid password
			errMsg = driver.findElement(passwordErrorMsg_in).getAttribute("textContent");
			if (!errMsg
					.contains("The password must be minimum 8 characters, including at least 1 letter and 1 number")) {
				Log.error("\"Password Validation Message is not displayed\"");
				Assert.assertTrue(false, "Password Validation Message is not displayed");
			}
			Log.info("Password validation \"" + errMsg + "\" is displayed");
		}
	}

	public static void blankFormSubmittionValidation_austria() {
		String errMsg;

		// Click on Create Profile/Register
		Utilities.clickElement(createProfile_Register_Btn, "CreateProfile/Register button");

		// Verify error is displayed for first name field
		if (!Utilities.isDisplayed(fullNameError_de)) {
			Assert.assertTrue(false, "Full Name validation error message is not displayed");
		}
		errMsg = driver.findElement(fullNameError_de).getAttribute("textContent");
		Log.info("Full Name validation error message \"" + errMsg + "\" is displayed");

		// Verify error is displayed for Email ID field
		if (!Utilities.isDisplayed(emailErrorMsg_de)) {
			Assert.assertTrue(false, "Email ID validation error message is not displayed");
		}
		errMsg = driver.findElement(emailErrorMsg_de).getAttribute("textContent");
		Log.info("Email ID validation error message \"" + errMsg + "\" is displayed");

		// Verify error is displayed for password field
		if (!Utilities.isDisplayed(passwordError_de)) {
			Assert.assertTrue(false, "Password validation error message is not displayed");
		}
		errMsg = driver.findElement(passwordError_de).getAttribute("textContent");
		Log.info("Password validation error message \"" + errMsg + "\" is displayed");

		// Verify error is displayed for Opt to Receive News Letter
		if (!Utilities.isDisplayed(optInReceiveNewsLetterRadio_de)) {
			Assert.assertTrue(false,
					"'Opt to Receive News Letter' radio button validation error message is not displayed");
		}
		errMsg = driver.findElement(optInReceiveNewsLetterRadio_de).getAttribute("textContent");
		Log.info("'Opt to Receive News Letter' radio button validation error message \"" + errMsg + " \""
				+ errMsg + "\" is displayed");

	}
}
