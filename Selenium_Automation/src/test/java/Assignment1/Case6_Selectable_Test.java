package Assignment1;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseClass;
import pageObjects.Selectable_Page;

public class Case6_Selectable_Test extends BaseClass {
	@BeforeClass(groups = { "positive", "negative", "selectable" })
	public void initializeBrowser() {
		// Launch Browser and Navigate to URL
		initialization(prop.getProperty("selectable_URL"));

		// Verify Alerts page is displayed
		Selectable_Page.verifyPageTitle();
	}

	@Test(groups = { "positive", "selectable" })
	public void selectable_Serialize() {
		Selectable_Page.selectable_Serialize();
	}

	@Test(groups = { "positive", "selectable" })
	public void selectable_defaultFunctionality() {
		Selectable_Page.selectable_DefaultFunctionality();
	}

	// Verify Multiple tabs can be selected in Default Functionality
	@Test(groups = { "negative", "selectable" })
	public void selectable_defaultFunctionality_MultiSelect() {
		Selectable_Page.verify_DefaultFunctionality_multipleSelect();
	}

	@AfterClass(groups = { "positive", "negative", "selectable" })
	public void tearDown() {
		driver.quit();
	}
}
