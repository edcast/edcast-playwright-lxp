package com.qa.pages;

import static com.qa.utils.Constants.Test_DATA.HOME_PAGE_TEST_DATA;

import com.microsoft.playwright.Page;
import com.qa.web.service.HomePageService;

public class HomePage extends PageBase {
	Page page;

	private String search = "input#search_query_top";
	private String createButton = "id=createBtn";
	private String smartCardText = "text=Create your own content - articles, polls, quizzes and many more.";
	private String searchContent = "input[placeholder='Search']";
	private String searchIn = "xpath=//div[@class='search']//button";
	private String apply = "button:has-text('Apply')";
	private String moreButton = "//span[@class='ed-dropdown-label' and text()='More']";
	private String adminButton = "text=Admin";
	private String clickOnNextButton = "button:has-text('Next')";
	private String selectLevel = "id=select-level";
	private String clickOnAdd = "button:has-text('Add')";
	private String clickOnSkip = "button:has-text('Skip')";
	private String defaultOnboardingCheckbox = "xpath=//input[@type='checkbox' and @id='tandCheckbox']";
	private String letsGetStarted = "xpath=//*[text()=\"Let's Get Started\"]";
	private String selectGoal = "id=select-goal";
	private String getUserNameAfterFirstLogin = "//*[@class='user-profile']//h1";
	private String clickOn3Dots = "xpath=//*[@class='std-padding']//div[@class='insight-dropdown']/button";
	private String clickOnAssignToMe = "//div[@role='dialog']//*[@role='menu']//*[text()='Assign to Me']";
	private String startDateCalendar = "//*[text()='Start Date: ']//span[text()='Select Date']";
	private String nextMonthOnCalendar = "//*[@aria-label='Next Month']";
	private String assignButton = "button:has-text('Assign')";
	private String dueDateCalendar = "//*[text()='Due Date: ']//span[text()='Select Date']";
	private String meTab = "//*[@class='menu-items-wrapper']//a[text()='ME']";
	private String skillsPassport = "//button[@role='tab' and text()='Skills Passport']";
	private String pathwayText = "text=A learning experience defined by a sequence of SmartCards.";
	private String getTranscriptButton = "button:has-text('Get Transcript')";

	private String calendarDate(String date) {
		return getLocatorWithParam("//div[@class='react-datepicker__week']//div[text()='%s']", date);
	}

	private String calendarMonthYear1(String... monthYear) {
		return getLocatorWithParam(
				"//div[contains(@class, 'react-datepicker__current-month') and text()='%s" + " " + "%s']", monthYear);
	}

	public HomePage(Page page) {
		this.page = page;
		testData = getTestData(HOME_PAGE_TEST_DATA);
	}

	public String getLocatorWithParam(String loc, String... val) {
		String value = String.format(loc, val);
		return value;

	}

	public HomePage clickGetTranscript() {
		page.locator(getTranscriptButton).click();
		return this;
	}

	public HomePage search(String searchTxt) {
		page.locator(search).fill(searchTxt);
		return this;
	}

	public SmartCardPage clickSmartCard() {
		page.waitForTimeout(2000);
		page.locator(smartCardText).click();
		return new SmartCardPage(page);
	}

	public SmartCardPage clickPathway() {
		page.waitForTimeout(2000);
		page.locator(pathwayText).click();
		return new SmartCardPage(page);
	}

	public HomePage clickOnCreateButton() {
		page.waitForTimeout(2000);
		page.locator(createButton).click();
		return this;
	}

	public HomePage clickOnMoreButton() {
		page.locator(moreButton).click();
		return this;
	}

	public AdminPage clickOnAdminButton() {
		page.locator(adminButton).click();
		return new AdminPage(page);
	}

	public HomePageService cardSearchOnHomePage(String cardName) {
		page.fill(searchContent, cardName);
		page.click(searchIn);
		page.click(apply);
		page.click("text='" + cardName + "'");
		return new HomePageService(page);

	}

	public HomePageService fillDefaultOnboardingInfo(Page page) {
		try {
			page.locator(defaultOnboardingCheckbox).click();
			page.click(clickOnNextButton);
			page.locator(selectGoal).fill(testData.getString("select-goal-fill"));
			page.locator(String.format("xpath=//div[text()='%s']", testData.getString("select-goal"))).click();
			page.selectOption(selectLevel, testData.getString("select-level"));
			page.click(clickOnAdd);
			page.click(clickOnNextButton);
			page.click(clickOnSkip);
			page.locator(letsGetStarted).click();
		} catch (Exception e) {

		}
		return new HomePageService(page);

	}

	public String getUserNameAfterFirstLogin(Page page) {
		String userNameOnFirstLogin = page.textContent(getUserNameAfterFirstLogin);
		return userNameOnFirstLogin;

	}

	public void clickSkillsPassport() {
		page.locator(meTab).click();
		page.locator(skillsPassport).click();
		try {
			page.locator(getTranscriptButton).click();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public HomePageService clickOnCardAssign(Page page, String startDate, String endDate) {
		try {
			page.locator(clickOn3Dots).click();
			page.locator(clickOnAssignToMe).click();
			page.locator(startDateCalendar).click();
			String[] startDateParts = startDate.split("-");
			while (!page.locator(calendarMonthYear1(startDateParts[1], startDateParts[2])).isVisible()) {
				page.click(nextMonthOnCalendar);
			}

			page.click(calendarDate(startDateParts[0]));
			String[] dueDateParts = startDate.split("-");

			page.locator(dueDateCalendar).click();

			while (!page.locator(calendarMonthYear1(dueDateParts[1], dueDateParts[2])).isVisible()) {
				page.click(nextMonthOnCalendar); // Adjust the selector for the next month button
			}
			page.click(calendarDate(dueDateParts[0]));
			page.click(assignButton);
		} catch (Exception e) {

			System.out.println("");
		}
		return new HomePageService(page);

	}

	public Boolean IsAssignToMePresent() {
		page.locator(clickOn3Dots).click();
		return page.locator(clickOnAssignToMe).isVisible();

	}
}
