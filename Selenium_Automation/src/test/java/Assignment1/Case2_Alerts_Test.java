package Assignment1;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.Alerts_Page;
import utilities.Constant;
import utilities.DataReaders;

public class Case2_Alerts_Test extends BaseClass {

	@BeforeClass(groups = { "positive", "alerts" })
	public void initializeBrowser() {
		// Launch Browser and Navigate to URL
		initialization(prop.getProperty("alert_URL"));

		// Verify Alerts page is displayed
		Alerts_Page.verifyPageTitle();
	}

	@Test(groups = { "positive", "alerts" })
	public void verifyAlertWithOKBtn() {
		Alerts_Page.verify_alertWithOKBtn();
	}

	@Test(groups = { "positive", "alerts" })
	public void verifyAlertWithOKCancelBtn() {
		Alerts_Page.verify_AlertWithOKCancelBtn();
	}

	@DataProvider
	public Object[][] getTestData() throws InvalidFormatException, IOException {
		Object[][] data = DataReaders.getTestData(Constant.dataSheetPath, Constant.case2_Alerts_Sheet);
		return data;
	}

	@Test(dataProvider = "getTestData", groups = { "positive", "alerts" })
	public void verifyAlertWithTextBox(String name) {
		Alerts_Page.verify_AlertWithTextBox(name);
	}

	@AfterClass(groups = { "positive", "alerts" })
	public void tearDown() {
		driver.quit();
	}
}