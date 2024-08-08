package com.qa.pages;

import static com.qa.utils.Constants.FILE_PATH.DOWNLOADED_USER_FILE_PATH;
import static com.qa.utils.Constants.FILE_PATH.USER_SAMPLE_FILE_UPDATED;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.qa.utils.CommonUtils;

public class AdminPage extends PageBase {
	Page page;

	private String accountButton = "text=Accounts";
	private String sampleDownload = "//*[text()='(Download Sample file)']";
	private String addUserButton = "//*[text()='Add Users']";
	private String usersButton = "//span[@class='name' and text()='Users']";
	private String clickOnPreviewButton = "button:has-text('Preview')";
	private String clickOnImportButton = "button:has-text('Import')";
	private String searchBox = "xpath=//*[@class='rubix-panel-container-with-controls']//input[@placeholder='Search']";
	private String deleteUser = "button:has-text('Delete User')";
	private String deleteButtonPromptYes = "button:has-text('YES')";
	private String uploadCSVFile = "text='Upload CSV file'";

	public AdminPage(Page page) {
		this.page = page;
	}

	private Page getPage() {
		return page;
	}

	public AdminPage clickOnAccountTab() {
		getPage().locator(accountButton).click();
		return this;
	}

	public AdminPage clickOnUsersTab() {
		getPage().locator(usersButton).click();
		return this;
	}

	public AdminPage clickOnAddUsersButton() {
		customWaitToClickForSelector(addUserButton);
		return this;
	}

	public AdminPage downloadUsersSampleFile() {
		Download download = getPage().waitForDownload(() -> {
			customWaitToClickForSelector(sampleDownload);
		});
		Path downloadPath = download.path();
		System.out.println("File downloaded to: " + downloadPath);
		Path targetPath = Paths.get(DOWNLOADED_USER_FILE_PATH);
		download.saveAs(targetPath);
		System.out.println("File saved to: " + targetPath);
		return this;
	}

	public AdminPage uploadSampleFileWithUsersDetails(JSONArray val) {
		new CommonUtils().cleanAndWriteCSV(DOWNLOADED_USER_FILE_PATH, USER_SAMPLE_FILE_UPDATED, val);
		getPage().locator(uploadCSVFile).setInputFiles(Paths.get(USER_SAMPLE_FILE_UPDATED));
		getPage().locator(clickOnPreviewButton).click();
		customWaitToClickForSelector(clickOnImportButton);
		return this;
	}

	public void customWaitToClickForSelector(String locator) {
		int count = 10;
		do {
			count--;

			try {
				getPage().locator(locator).click();
				return;
			} catch (Exception e) {
				System.out.println(e);
			}

		} while (count >= 0);
		;

	}

	public AdminPage deleteExistingUser(JSONArray newRow) {
		IntStream.range(0, newRow.length()).forEach(i -> {
			JSONArray newUserData = newRow.getJSONArray(i);
			String[] stringArray = new Gson().fromJson(newUserData.toString(), String[].class);
			getPage().locator(searchBox).fill(stringArray[2]);

			try {
				getPage().locator("xpath=//td[text()='" + stringArray[2] + "']/..//input").click();
				getPage().locator(deleteUser).click();
				getPage().locator(deleteButtonPromptYes).click();
			} catch (Exception e) {
				logStep("User Email " + stringArray[2] + " not found under existing user list");
			}

		});
		return this;
	}

}
