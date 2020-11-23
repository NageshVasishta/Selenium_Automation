package Assignment1;

import java.io.IOException;
import java.text.ParseException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.DatePicker_Page;
import utilities.Constant;
import utilities.DataReaders;

public class Case5_DatePicker_test extends BaseClass {
	@BeforeClass(groups = { "positive", "negative", "datepicker" })
	public void launchBrowser_NavigateToUrl() {
		// Launch Browser and Navigate to URL
		initialization(prop.getProperty("datePicker_URL"));

		// Verify Date Picker page is displayed
		DatePicker_Page.verifyPageTitle();
	}

	@DataProvider
	public Object[][] getTestData_DatePickerDisabled() throws InvalidFormatException, IOException {
		Object[][] data = DataReaders.getTestData(Constant.dataSheetPath, Constant.case5_DatePickerDisabled_Sheet);
		return data;
	}

	@DataProvider
	public Object[][] getTestData_DatePickerEnabled() throws InvalidFormatException, IOException {
		Object[][] data = DataReaders.getTestData(Constant.dataSheetPath, Constant.case5_DatePickerEnabled_Sheet);
		return data;
	}

	@DataProvider
	public Object[][] getTestData_InvalidDate() throws InvalidFormatException, IOException {
		Object[][] data = DataReaders.getTestData(Constant.dataSheetPath, Constant.case5_DatePickerInvalidDate_Sheet);
		return data;
	}

	@Test(dataProvider = "getTestData_DatePickerDisabled", enabled = true, groups = { "positive", "datepicker" })
	public void datePickerDisabled_SelectDate(String date) throws ParseException, InterruptedException {
		DatePicker_Page.selectDate_DisabledDatePicker(date);
	}

	@Test(dataProvider = "getTestData_DatePickerEnabled", enabled = true, groups = { "positive",
			"datepicker" })
	public void datePickerEnabled_SelectDate(String date) throws ParseException, InterruptedException {
		DatePicker_Page.selectDate_EnabledDatePicker(date);
	}

	@Test(dataProvider = "getTestData_DatePickerEnabled", enabled = true, groups = { "positive",
			"datepicker" })
	public void datePickerEnabled_SelectDate_SelectMonthYearFromDropDown(String date)
			throws ParseException, InterruptedException {
		DatePicker_Page.selectDate_EnabledDatePicker_SelectMonthYearFromDropDown(date);
	}

	@Test(dataProvider = "getTestData_InvalidDate", groups = { "negative", "datepicker" })
	public void dataPickerEnabled_InvalidDate(String invalidDate) {
		DatePicker_Page.invalidDateValidation(invalidDate);
	}

	@AfterClass(groups = { "positive", "negative", "datepicker" })
	public void tearDown() {
		driver.quit();
	}

}
