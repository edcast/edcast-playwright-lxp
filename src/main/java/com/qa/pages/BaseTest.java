package com.qa.pages;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Properties;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import com.microsoft.playwright.*;
import com.qa.base.PlaywrightFactory;
import com.qa.web.service.AdminService;
import com.qa.web.service.CardService;
import com.qa.web.service.HomePageService;

import io.qameta.allure.testng.AllureTestNg;
@Listeners({AllureTestNg.class})
public class BaseTest extends PlaywrightFactory  {

	protected Page page;
	public HomePage homePage;
	protected SmartCardPage smartCard;
	protected LoginPage loginPage;
	protected PlaywrightFactory play;
	protected static  Properties config;
	protected CardService cardService;
	protected AdminService adminService;
	protected AdminPage adminPage;
	protected HomePageService homePageService;
	public static JSONObject testData;

	@BeforeMethod
	// @Parameters({ "appURL", "browserType" })
	public void setUp() {
		cleanTestOutputFolder("test-output");
		cleanTestOutputFolder("surefire-reports");

		config = loadProperties("config.properties");
		play = new PlaywrightFactory();
		page = play.getPage(config.getProperty("baseUrl"), config.getProperty("browserType"));
		loginPage = new LoginPage(page,config);

	}
	
	public <T> T getPage(Class<T> pageClass,Page page) {
        try {
            Constructor<T> constructor = pageClass.getDeclaredConstructor(Page.class);;
            return constructor.newInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
	
	
	private void cleanTestOutputFolder(String val) {
//        Path path = Paths.get("test-output");
        Path path = Paths.get(val);

        
        if (Files.exists(path)) {
            try {
                Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	@AfterMethod
	public void tearDown() {
		//page.context().browser().close();
		
        page.close();
        browser.close();
        playwright.close();

	}
	public JSONObject getTestData(String path) {

		try {
			FileReader reader = new FileReader(path);
			testData = new JSONObject(new JSONTokener(reader));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load test data");
		}
		return testData;
	}
	private Properties loadProperties(String fileName) {
		Properties properties = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
			if (input == null) {
				System.out.println("Sorry, unable to find " + fileName);
				return null;
			}
			properties.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return properties;
	}

}
