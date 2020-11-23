package Assignment1;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.Frames_Page;
import utilities.Constant;
import utilities.DataReaders;

public class Case4_Frames_Test extends BaseClass {
	@BeforeClass(groups = { "positive", "frames" })
	public void initializeBrowser() {
		// Launch Browser and Navigate to URL
		initialization(prop.getProperty("frames_URL"));

		// Verify Alerts page is displayed
		Frames_Page.verifyPageTitle();
	}

	@DataProvider
	public Object[][] getTestData_SingleFrame() throws InvalidFormatException, IOException {
		Object[][] data = DataReaders.getTestData(Constant.dataSheetPath, Constant.case4_SingleFrame_Sheet);
		return data;
	}

	@Test(dataProvider = "getTestData_SingleFrame", groups = { "positive", "frames" })
	public void sigleIframe_validation(String value) {
		Frames_Page.singleFrame(value);
	}

	@DataProvider
	public Object[][] getTestData_MultipleFrames() throws InvalidFormatException, IOException {
		Object[][] data = DataReaders.getTestData(Constant.dataSheetPath, Constant.case4_SingleFrame_Sheet);
		return data;
	}

	@Test(dataProvider = "getTestData_MultipleFrames", enabled = true, groups = { "positive", "frames" })
	public void multipleFrames_validation(String value) {
		Frames_Page.multipleFrame(value);
	}

	@AfterClass(groups = { "positive", "frames" })
	public void tearDown() {
		driver.quit();
	}
}
