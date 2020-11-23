package pageObjects;

import org.openqa.selenium.By;
import org.testng.Assert;

import base.BaseClass;
import utilities.Log;
import utilities.Utilities;

public class Register_Page extends BaseClass {

	private static By firstName_IP = By.xpath("//input[@ng-model='FirstName']");
	private static By lastName_IP = By.xpath("//input[@ng-model='LastName']");
	private static By address_TextArea = By.xpath("//textarea[@ng-model='Adress']");
	private static By email_IP = By.xpath("//input[@ng-model='EmailAdress']");
	private static By phone_IP = By.xpath("//input[@ng-model='Phone']");
	private static By gender_Male_RBtn = By.xpath("//input[@name='radiooptions' and @value='Male']");
	private static By gender_FeMale_RBtn = By.xpath("//input[@name='radiooptions' and @value='FeMale']");
	private static By hobbies_Cricket_ChkBx = By.xpath("//input[@id='checkbox1']");
	private static By hobbies_Movies_ChkBx = By.xpath("//input[@id='checkbox2']");
	private static By hobbies_Hockey_ChkBx = By.xpath("//input[@id='checkbox3']");
	private static By languages_DropDown = By.xpath("//div[starts-with(@class,'ui-autocomplete-multiselect')]");
	private static By languages_Options = By.xpath("//a[@class='ui-corner-all']");
	private static By skill_Select = By.xpath("//select[@id='Skills']");
	private static By country_Select = By.xpath("//select[@id='countries']");
	private static By selectCountry_DropDown = By.xpath("//span[@class='select2-selection select2-selection--single']");
	private static By selectCountry_Options = By.xpath("//li[@class='select2-results__option']");
	private static By dateOfBirth_Year_Select = By.id("yearbox");
	private static By dateOfBirth_Month_Select = By.xpath("//select[@ng-model='monthbox']");
	private static By dateOfBirth_Day_Select = By.xpath("//select[@ng-model='daybox']");
	private static By password_IP = By.id("firstpassword");
	private static By confirmPassword_IP = By.id("secondpassword");
	private static By submit_Btn = By.id("submitbtn");
	private static By refresh_Btn = By.id("Button1");

	// Verify the Page Title
	public static void verifyPageTitle() {
		if (!Utilities.titleContains("Register", 30)) {
			Log.error("Register Page is not displayed");
			Assert.assertTrue(false, "Verify Register Page is displayed");
		}
		Log.info("Register Page is displayed");
	}

	public static void register(String firstName, String lastName, String address, String email, String phone,
			String gender, String hobbies, String languages, String skill, String country, String dob_Year,
			String dob_Month, String dob_Day, String password, String confirmPassword) {

		// Enter First Name, Last Name, Address, Email Address and Phone Number
		Utilities.enterText(firstName_IP, firstName, "First Name");
		Utilities.enterText(lastName_IP, lastName, "Last Name");
		Utilities.enterText(address_TextArea, address, "Address");
		Utilities.enterText(email_IP, email, "Email address");
		Utilities.enterText(phone_IP, phone, "Phone");

		// Select Gender
		if (gender.equalsIgnoreCase("Male")) {
			Utilities.clickElement(gender_Male_RBtn, "Gender - Male Radio button");
		} else {
			Utilities.clickElement(gender_FeMale_RBtn, "Gender - FeMale Radio button");
		}

		// Select Hobbies
		String[] hobbiesArray;
		if (hobbies.contains("||")) {
			hobbiesArray = hobbies.split("\\|\\|");
		} else {
			hobbiesArray = new String[] { hobbies };
		}
		for (int i = 0; i < hobbiesArray.length; i++) {
			if (hobbiesArray[i].equalsIgnoreCase("Cricket")) {
				Utilities.clickElement(hobbies_Cricket_ChkBx, "Hobbies - Cricket");
			} else if (hobbiesArray[i].equalsIgnoreCase("Movies")) {
				Utilities.clickElement(hobbies_Movies_ChkBx, "Hobbies - Movies");
			} else {
				Utilities.clickElement(hobbies_Hockey_ChkBx, "Hobbies - Hockey");
			}
		}

		// Select Languages
		Utilities.chooseFromDropDown(languages_DropDown, languages_Options, languages, "Languages");

		// Select Skills
		Utilities.selectDropDown(skill_Select, skill, "Skills");

		// Select Country
		Utilities.selectDropDown(country_Select, country, "Country");

		// Select "Select Country"
		Utilities.chooseFromDropDown(selectCountry_DropDown, selectCountry_Options, country, "Select Country");

		// Select Date of Birth - Year
		Utilities.selectDropDown(dateOfBirth_Year_Select, dob_Year, "Date of Birth - Year");

		// Select Date of Birth - Month
		Utilities.selectDropDown(dateOfBirth_Month_Select, dob_Month, "Date of Birth - Month");

		// Select Date of Birth - Day
		Utilities.selectDropDown(dateOfBirth_Day_Select, dob_Day, "Date of Birth - Day");

		// Enter Password, Confirm Password
		Utilities.enterText(password_IP, password, "Password");
		Utilities.enterText(confirmPassword_IP, confirmPassword, "Confirm Password");

		// CLick on Submit button
		Utilities.clickElement(submit_Btn, "Submit button");
	}

	public static void ValidateRefreshBtn(String firstName, String lastName, String address, String email,
			String phone) {
		// Enter First Name, Last Name, Address, Email Address and Phone Number
		Utilities.enterText(firstName_IP, firstName, "First Name");
		Utilities.enterText(lastName_IP, lastName, "Last Name");
		Utilities.enterText(address_TextArea, address, "Address");
		Utilities.enterText(email_IP, email, "Email address");
		Utilities.enterText(phone_IP, phone, "Phone");

		// CLick on Refresh button
		Utilities.clickElement(refresh_Btn, "Refresh button");

		// Verify the values entered in the all the fields are cleared
		verifyFieldsAreEmpty(firstName_IP, "value", "First Name");
		verifyFieldsAreEmpty(lastName_IP, "value", "Last Name");
		verifyFieldsAreEmpty(address_TextArea, "value", "Address");
		verifyFieldsAreEmpty(email_IP, "value", "Email address");
		verifyFieldsAreEmpty(phone_IP, "value", "Phone");
	}

	public static void verifyFieldsAreEmpty(By locator, String attribute, String fieldName) {
		if (driver.findElement(locator).getAttribute(attribute).isEmpty()) {
			Log.info(fieldName + " field is empty");
		} else {
			Log.error(fieldName + " field is empty");
			Assert.assertTrue(false, "Verify " + fieldName + " field is empty");
		}
	}

	public static void verify_PasswordMissMatch_FormSubmitted() {
		String errMsg = driver.findElement(confirmPassword_IP).getAttribute("validationMessage");
		if(errMsg.contentEquals("Passwords dont match")) {
			Log.info("Password Missmatch error message \"Passwords dont match\" is displayed");
		}else {
			Log.error("Password Missmatch error message is not displayed");
			Assert.assertTrue(false, "Verify Password Missmatch error message \"Passwords dont match\" is displayed");
		}
	}
}
