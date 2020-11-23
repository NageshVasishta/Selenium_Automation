package Assignment1;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.CreateProfile_Page;
import pageObjects.GilleteHome_Page;
import pageObjects.SignIn_Page;
import pageObjects.ViewProfile_Page;
import utilities.Constant;
import utilities.DataReaders;

public class Case7_Gillette_Test extends BaseClass {

	@DataProvider
	public Object[][] getTestData() throws IOException, ParseException {
		JSONArray jsonArray = DataReaders.getTestData_json(Constant.jsonFile_Path, Constant.register_Variable);

		Object[][] data = new Object[jsonArray.size()][9];
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject register = (JSONObject) jsonArray.get(i);
			data[i][0] = (String) register.get("url");
			data[i][1] = (String) register.get("firstname");
			data[i][2] = (String) register.get("lastname");
			data[i][3] = (String) register.get("emailid");
			data[i][4] = (String) register.get("password");
			data[i][5] = (String) register.get("birthdayMonth");
			data[i][6] = (String) register.get("birthdayYear");
			data[i][7] = (String) register.get("zipCode");
			data[i][8] = (String) register.get("receiveEmailsForOffers");
		}
		return data;
	}

	@DataProvider
	public Object[][] getTestData_SignIn() throws IOException, ParseException {
		JSONArray jsonArray = DataReaders.getTestData_json(Constant.jsonFile_Path, Constant.signIn_Variable);

		Object[][] data = new Object[jsonArray.size()][4];
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject signIn = (JSONObject) jsonArray.get(i);
			data[i][0] = (String) signIn.get("url");
			data[i][1] = (String) signIn.get("emailid");
			data[i][2] = (String) signIn.get("password");
			data[i][3] = (String) signIn.get("firstname");
		}
		return data;
	}

	@DataProvider
	public Object[][] getTestData_InvalidEmailIDValidation() throws IOException, ParseException {
		JSONArray jsonArray = DataReaders.getTestData_json(Constant.jsonFile_Path, Constant.invalidEmailID_Variable);

		Object[][] data = new Object[jsonArray.size()][9];
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject register = (JSONObject) jsonArray.get(i);
			data[i][0] = (String) register.get("url");
			data[i][1] = (String) register.get("firstname");
			data[i][2] = (String) register.get("lastname");
			data[i][3] = (String) register.get("emailid");
			data[i][4] = (String) register.get("password");
			data[i][5] = (String) register.get("birthdayMonth");
			data[i][6] = (String) register.get("birthdayYear");
			data[i][7] = (String) register.get("zipCode");
			data[i][8] = (String) register.get("receiveEmailsForOffers");
		}
		return data;
	}

	@Test(dataProvider = "getTestData", enabled = true, groups = { "positive", "gillete" })
	public void register(String url, String firstName, String lastName, String email, String password,
			String birthMonth, String birthYear, String zipCode, String receiveEmailsForOffers) {

		// Launch Browser and Navigate to URL
		initialization(url);

		// Verify GIllete Home page is displayed
		GilleteHome_Page.verifyPageTitle();

		// Navigate to Register Page
		GilleteHome_Page.navigateToRegister();

		// Register New User
		String randomizedEmail = RandomStringUtils.randomAlphanumeric(3);
		email = email.substring(0, email.indexOf("@")) + randomizedEmail + email.substring(email.indexOf("@"));
		CreateProfile_Page.register(firstName, lastName, email, password, birthMonth, birthYear, zipCode,
				receiveEmailsForOffers);

		// Verify
		if (url.contains(".in")) {
			SignIn_Page.verifyRegisteredSuccessfully();
		} else {
			GilleteHome_Page.verifyUserLoggedIn_Austria(firstName);
		}
	}

	@Test(dataProvider = "getTestData_SignIn", enabled = true, groups = { "positive", "gillete" })
	public void loginIn(String url, String email, String password, String firstname) {
		// Launch Browser and Navigate to URL
		initialization(url);

		// Verify GIllete Home page is displayed
		GilleteHome_Page.verifyPageTitle();

		// Navigate to Login Page
		GilleteHome_Page.navigateToLogin();

		// Sign In
		SignIn_Page.login(email, password);

		// Verify User Log In
		if (url.contains(".in")) {
			ViewProfile_Page.verifyVewProfilePageDisplayed();
		} else {
			GilleteHome_Page.verifyUserLoggedIn_Austria(firstname);
		}
	}

	@Test(dataProvider = "getTestData_InvalidEmailIDValidation", enabled = true, groups = { "negative", "gillete" })
	public void invalidEmailIDValidation(String url, String firstName, String lastName, String email, String password,
			String birthMonth, String birthYear, String zipCode, String receiveEmailsForOffers) {
		// Launch Browser and Navigate to URL
		initialization(url);

		// Verify GIllete Home page is displayed
		GilleteHome_Page.verifyPageTitle();

		// Navigate to Register Page
		GilleteHome_Page.navigateToRegister();

		// Register New User
		CreateProfile_Page.register(firstName, lastName, email, password, birthMonth, birthYear, zipCode,
				receiveEmailsForOffers);

		// Verify Email Validation message is displayed
		CreateProfile_Page.verifyEmailIDError(email);
	}

	@Test(enabled = true, groups = { "negative", "gillete" })
	public void passwordFldValidation_in() {
		// Launch Browser and Navigate to URL
		initialization(prop.getProperty("gillette_IN_URL"));

		// Verify GIllete Home page is displayed
		GilleteHome_Page.verifyPageTitle();

		// Navigate to Register Page
		GilleteHome_Page.navigateToRegister();

		// Verify error message is displayed for invalid password
		CreateProfile_Page.passwordValidation_in();
	}

	@Test(enabled = true, groups = { "negative", "gillete" })
	public void blankRegisterFormSubmittion_austria() {
		// Launch Browser and Navigate to URL
		initialization(prop.getProperty("gillette_Austria_URL"));

		// Verify GIllete Home page is displayed
		GilleteHome_Page.verifyPageTitle();

		// Navigate to Register Page
		GilleteHome_Page.navigateToRegister();

		// Verify error message when blank registration form is submitted
		CreateProfile_Page.blankFormSubmittionValidation_austria();
	}

	@AfterMethod(groups = { "positive", "negative", "gillete" })
	public void tearDown() {
		driver.quit();
	}
}
