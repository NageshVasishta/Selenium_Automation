package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.Constant;
import utilities.Log;

public class BaseClass {
	public static WebDriver driver;
	public static Properties prop;

	// Setting Log File Path
	static {
		System.setProperty("log4j.configurationFile",
				Constant.rootFolder + "\\src\\main\\java\\resources\\log4j2.xml");
	}

	public BaseClass() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					Constant.rootFolder + "\\src\\main\\java\\config\\config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialization(String url) {

		// setup the chromedriver using WebDriverManager
		WebDriverManager.chromedriver().setup();

		// Create driver object for Chrome
		driver = new ChromeDriver();

		// Maximize Browser Window
		driver.manage().window().maximize();

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		driver.get(url);
		Log.debug("Navigate to url "+url);
	}
}
