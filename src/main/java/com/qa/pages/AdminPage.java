package com.qa.pages;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;
import org.json.JSONArray;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.qa.utils.CommonUtils;
import com.google.gson.Gson;
import static com.qa.utils.Constants.FILE_PATH.*;
public class AdminPage extends PageBase {
	Page page;

	private String accountButton = "text=Accounts";
	private String sampleDownload = "text='(Download Sample file)'";
	private String addUserButton = "button:has-text('Add Users')";
	private String usersButton = "//span[@class='name' and text()='Users']";
	private String clickOnPreviewButton = "button:has-text('Preview')";
	private String clickOnImportButton = "button:has-text('Import')";
	private String searchBox = "xpath=//*[@class='rubix-panel-container-with-controls']//input[@placeholder='Search']";
	private String deleteUser ="button:has-text('Delete User')";
	private String deleteButtonPromptYes ="button:has-text('YES')";
	private String uploadCSVFile ="text='Upload CSV file'";


	public AdminPage(Page page) {
		this.page = page;
		//testData = getTestData(ADMIN_PAGE_TEST_DATA);
	}

	public AdminPage clickOnAccountTab() {
		page.locator(accountButton).click();
		return new AdminPage(page);
	}

	public AdminPage clickOnUsersTab()  {
		page.locator(usersButton).click();
		return new AdminPage(page);
	}

	public AdminPage clickOnAddUsersButton() {
		page.locator(addUserButton).click();
		return new AdminPage(page);
	}

	public AdminPage downloadUsersSampleFile() {
		Download download = page.waitForDownload(() -> {
			page.locator(sampleDownload).click();
		});
		Path downloadPath = download.path();
		System.out.println("File downloaded to: " + downloadPath);
		Path targetPath = Paths.get(DOWNLOADED_USER_FILE_PATH);
		download.saveAs(targetPath);
		System.out.println("File saved to: " + targetPath);
		return new AdminPage(page);
	}

	public AdminPage uploadSampleFileWithUsersDetails(JSONArray val) {
		new CommonUtils().cleanAndWriteCSV(DOWNLOADED_USER_FILE_PATH,
				USER_SAMPLE_FILE_UPDATED, val);
		page.locator(uploadCSVFile)
				.setInputFiles(Paths.get(USER_SAMPLE_FILE_UPDATED));
		page.locator(clickOnPreviewButton).click();
		page.locator(clickOnImportButton).click();
		return new AdminPage(page);
	}

	public AdminPage deleteExistingUser(JSONArray newRow) {
		IntStream.range(0, newRow.length()).forEach(i -> {
			JSONArray newUserData = newRow.getJSONArray(i);
			String[] stringArray = new Gson().fromJson(newUserData.toString(), String[].class);
			page.locator(searchBox)
					.fill(stringArray[2]);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				page.locator("xpath=//td[text()='" + stringArray[2] + "']/..//input").click();
				page.locator(deleteUser).click();
				page.locator(deleteButtonPromptYes).click();
			} catch (Exception e) {
				System.out.println("User Email " + stringArray[2] + " not found under user list");
			}
			
		});
		return new AdminPage(page);
	}

	

	
}
