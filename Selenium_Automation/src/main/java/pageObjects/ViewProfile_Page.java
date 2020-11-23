package pageObjects;

import org.testng.Assert;

import base.BaseClass;
import utilities.Log;
import utilities.Utilities;

public class ViewProfile_Page extends BaseClass {
	public static void verifyVewProfilePageDisplayed() {
		if (Utilities.titleContains("View Profile", 30)) {
			Log.info("'View Profile' Page is displayed");
		} else {
			Log.error("'View Profile' Page is not displayed");
			Assert.assertTrue(false, "Verify 'View Profile' Page is displayed");
		}
	}
}
