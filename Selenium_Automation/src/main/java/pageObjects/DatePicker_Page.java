package pageObjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import base.BaseClass;
import utilities.Log;
import utilities.Utilities;

public class DatePicker_Page extends BaseClass {
	private static By disabledDatePicker = By.id("datepicker1");
	private static By disabledDP_Month = By.className("ui-datepicker-month");
	private static By disabledDP_Year = By.className("ui-datepicker-year");
	private static By disabledDP_NextMonth = By.xpath("//a[contains(@class,'ui-datepicker-next')]/span");
	private static By disabledDP_PrevMonth = By.xpath("//a[contains(@class,'ui-datepicker-prev')]/span");
	private static By diabledDP_Day = By.xpath("//a[contains(@class,'ui-state-default')]");
	private static By enabledDatePicker = By.id("datepicker2");
	private static By enabledDP_Month = By
			.xpath("//select[@class='datepick-month-year' and @title='Change the month']/option[@selected='selected']");
	private static By enabledDP_Year = By
			.xpath("//select[@class='datepick-month-year' and @title='Change the year']/option[@selected='selected']");
	private static By enabledDP_Month_Select = By
			.xpath("//select[@class='datepick-month-year' and @title='Change the month']");
	private static By enabledDP_Year_Select = By
			.xpath("//select[@class='datepick-month-year' and @title='Change the year']");
	private static By enabledDP_NextMonth = By.xpath("//a[contains(@class,'datepick-cmd datepick-cmd-next')]");
	private static By enabledDP_PrevMonth = By.xpath("//a[contains(@class,'datepick-cmd datepick-cmd-prev')]");
	private static By enabledDP_Day = By.xpath("//a[starts-with(@class,'dp')]");

	// Verify the Page Title
	public static void verifyPageTitle() {
		String pageTitle = driver.getTitle();
		Assert.assertEquals(pageTitle, "Datepicker", "Verify Page Title");
	}

	public static void selectDate_DisabledDatePicker(String SelectDate) throws ParseException, InterruptedException {
		selectDate_DatePicker(disabledDatePicker, "DatePicker Disabled", disabledDP_Year, disabledDP_Month,
				disabledDP_PrevMonth, disabledDP_NextMonth, diabledDP_Day, SelectDate);
	}

	public static void selectDate_EnabledDatePicker(String SelectDate) throws ParseException, InterruptedException {
		driver.navigate().refresh();
		selectDate_DatePicker(enabledDatePicker, "DatePicker Enabled", enabledDP_Year, enabledDP_Month,
				enabledDP_PrevMonth, enabledDP_NextMonth, enabledDP_Day, SelectDate);
	}

	public static void selectDate_EnabledDatePicker_SelectMonthYearFromDropDown(String SelectDate)
			throws ParseException, InterruptedException {
		driver.navigate().refresh();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = sdf.parse(SelectDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);

		// Click on Date Picker
		Utilities.clickElement(enabledDatePicker, "DatePicker Enabled");
		if(!Utilities.isDisplayed(enabledDP_Year_Select)) {
			driver.navigate().refresh();
			Utilities.clickElement(enabledDatePicker, "DatePicker Enabled");
		}

		// Select Year from Date Picker
		Select selectYear = new Select(driver.findElement(enabledDP_Year_Select));
		int selectedYear = Integer.parseInt(selectYear.getFirstSelectedOption().getAttribute("textContent"));
		while (selectedYear != year) {
			List<WebElement> yearElementList;
			try {
				yearElementList = selectYear.getOptions();
			} catch (StaleElementReferenceException e) {
				Thread.sleep(3000);
				yearElementList = selectYear.getOptions();
			}
			ArrayList<String> yearOptionsList = new ArrayList<String>();
			for (int i = 1; i < yearElementList.size() - 1; i++) {
				yearOptionsList.add(yearElementList.get(i).getAttribute("textContent"));
			}

			if (yearOptionsList.contains(year + "")) {
				selectYear.selectByVisibleText(year + "");
				break;
			}

			Collections.sort(yearOptionsList);
			if (Integer.parseInt(yearOptionsList.get(1)) > year) {
				selectYear.selectByIndex(1);
			} else {
				selectYear.selectByIndex(yearOptionsList.size() - 1);
			}
			
			selectYear = new Select(driver.findElement(enabledDP_Year_Select));
			selectedYear = Integer.parseInt(selectYear.getFirstSelectedOption().getAttribute("textContent"));
		}

		// Select Month from Date Picker
		Select selectMonth = new Select(driver.findElement(enabledDP_Month_Select));
		selectMonth.selectByVisibleText(monthName);

		// Select Day
		selectDay(enabledDP_Day, day);

		// Verify Date is selected
		compareTwoDates(enabledDatePicker, SelectDate, "DatePicker Enabled");
	}

	public static void selectDate_DatePicker(By dpLocator, String dpName, By yearLocator, By monthLocator,
			By prevMonthLocator, By nextMonthLocator, By dayLocator, String SelectDate)
			throws ParseException, InterruptedException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = sdf.parse(SelectDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
		int monthNo = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);

		// Click on Date Picker
		Utilities.clickElement(dpLocator, dpName);

		// Select Year from Date Picker
		int selectedYear = Integer.parseInt(driver.findElement(yearLocator).getAttribute("textContent"));
		while (selectedYear != year) {
			if (selectedYear > year) {
				Utilities.clickElement(prevMonthLocator, "Previous Month Arrow");
			} else {
				Utilities.clickElement(nextMonthLocator, "Next Month Arrow");
			}
			selectedYear = Integer.parseInt(driver.findElement(yearLocator).getAttribute("textContent"));
		}

		// Select Month from Date Picker
		Calendar calendar2 = Calendar.getInstance();
		String selectedMonth = driver.findElement(monthLocator).getAttribute("textContent");
		while (!selectedMonth.contentEquals(monthName)) {

			Date date2 = new SimpleDateFormat("MMMM").parse(selectedMonth);
			calendar2.setTime(date2);
			int selectedMonthNo = calendar2.get(Calendar.MONTH);

			if (selectedMonthNo > monthNo) {
				Utilities.clickElement(prevMonthLocator, "Previous Month Arrow");
			} else {
				Utilities.clickElement(nextMonthLocator, "Next Month Arrow");
			}
			selectedMonth = driver.findElement(monthLocator).getAttribute("textContent");
		}

		// Select Day
		selectDay(dayLocator, day);

		// Verify Date is selected
		compareTwoDates(dpLocator, SelectDate, dpName);
	}

	public static void selectDay(By dayLocator, int day) {
		// Select Day
		try {
			List<WebElement> dayList = driver.findElements(dayLocator);
			for (WebElement element : dayList) {
				if (Integer.parseInt(element.getAttribute("textContent")) == day) {
					element.click();
				}
			}
		} catch (StaleElementReferenceException e) {
			List<WebElement> dayList = driver.findElements(dayLocator);
			for (WebElement element : dayList) {
				if (Integer.parseInt(element.getAttribute("textContent")) == day) {
					element.click();
					break;
				}
			}
		}
	}

	public static void compareTwoDates(By datePickerLocator, String expectedDate, String fieldName)
			throws ParseException {

		String actualDate = driver.findElement(datePickerLocator).getAttribute("value");
		if (actualDate.isEmpty()) {
			Log.error("Date \"" + expectedDate + "\" is not selected in the " + fieldName);
			Assert.assertTrue(false, "Verify Date \"" + expectedDate + "\" is selected in the " + fieldName);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date1 = sdf.parse(actualDate);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);

		Date date2 = sdf.parse(expectedDate);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);

		if (calendar1.compareTo(calendar2) == 0) {
			Log.info("Date \"" + expectedDate + "\" is selected in the " + fieldName);
		} else {
			Log.error("Date \"" + expectedDate + "\" is not selected in the " + fieldName);
			Assert.assertTrue(false, "Verify Date \"" + expectedDate + "\" is selected in the " + fieldName);
		}
	}
	
	public static void invalidDateValidation(String invalidDate) {
		// Enter Invalid Date
		Utilities.enterText(enabledDatePicker, invalidDate, "DatePicker Enabled");
		driver.findElement(enabledDatePicker).sendKeys(Keys.TAB);
		
		// Verify Enabled Date picker accepts the invalid date
		if(driver.findElement(enabledDatePicker).getAttribute("value").equals(invalidDate)) {
			Log.error("Enabled Date Picker accepts invalid date: "+invalidDate);
			Assert.assertTrue(false, "Verify Enabled Date Picker accepts invalid date: "+invalidDate);
		}else {
			Log.info("Enabled Date Picker does not accepts invalid date: "+invalidDate);
		}
	}
}
