package Assignment1;

import java.io.IOException;
import java.util.Random;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.Register_Page;
import pageObjects.WebTable_Page;
import utilities.Constant;
import utilities.DataReaders;

public class Case1_Register_Test extends BaseClass {

	@BeforeMethod(groups = { "positive", "negative", "register" })
	public void launchBrowser_NavigateToUrl() {
		// Launch Browser and Navigate to URL
		initialization(prop.getProperty("register_URL"));

		// Verify Register Page is displayed
		Register_Page.verifyPageTitle();
	}

	@DataProvider
	public Object[][] getTestData() throws InvalidFormatException, IOException {
		Object[][] data = DataReaders.getTestData(Constant.dataSheetPath, Constant.case1_Register_Sheet);
		return data;
	}

	@Test(dataProvider = "getTestData", groups = { "positive", "register" })
	public void registerNewUser(String firstName, String lastName, String address, String email, String phone,
			String gender, String hobbies, String languages, String skill, String country, String dob_Year,
			String dob_Month, String dob_Day, String password, String confirmPassword) {

		// Register New User
		phone = Integer.toString(new Random().nextInt(999999999)) + "1";
		Register_Page.register(firstName, lastName, address, email, phone, gender, hobbies, languages, skill, country,
				dob_Year, dob_Month, dob_Day, password, confirmPassword);
		WebTable_Page.verify_RegisteredUser(email);
	}

	@Test(dataProvider = "getTestData", groups = { "positive", "register" })
	public void refreshRegisterPage(String firstName, String lastName, String address, String email, String phone,
			String gender, String hobbies, String languages, String skill, String country, String dob_Year,
			String dob_Month, String dob_Day, String password, String confirmPassword) {

		// Verify Refresh button Functionality
		Register_Page.ValidateRefreshBtn(firstName, lastName, address, email, phone);
	}

	@DataProvider
	public Object[][] getTestData_PasswordMismatch() throws InvalidFormatException, IOException {
		Object[][] data = DataReaders.getTestData(Constant.dataSheetPath, Constant.case1_PasswordMismatch_Sheet);
		return data;
	}

	@Test(dataProvider = "getTestData_PasswordMismatch", groups = { "negative", "register" })
	public void passwordMismatch(String firstName, String lastName, String address, String email, String phone,
			String gender, String hobbies, String languages, String skill, String country, String dob_Year,
			String dob_Month, String dob_Day, String password, String confirmPassword) {

		// Enter all the registration details
		Register_Page.register(firstName, lastName, address, email, phone, gender, hobbies, languages, skill, country,
				dob_Year, dob_Month, dob_Day, password, confirmPassword);

		// Verify form is submitted
		Register_Page.verify_PasswordMissMatch_FormSubmitted();
	}

	@AfterMethod(groups = { "positive", "negative", "register" })
	public void tearDown() {
		driver.quit();
	}
}
