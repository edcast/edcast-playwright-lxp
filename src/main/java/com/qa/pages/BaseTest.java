package com.qa.pages;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Properties;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.google.gson.Gson;
import com.microsoft.playwright.*;
import com.qa.base.PlaywrightFactory;
import com.qa.base.SetUpUtils;
import com.qa.utils.PlayWrightUtils;
import com.qa.web.service.AdminService;
import com.qa.web.service.CardService;
import com.qa.web.service.HomePageService;
import com.qa.web.service.PathwayService;
import static com.qa.utils.Constants.FRAMEWORK_DATA.DATA_TO_DELETE;
import static com.qa.utils.Constants.Test_DATA.ADMIN_PAGE_TEST_DATA;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import java.util.Optional;

@Listeners({ AllureTestNg.class })
public class BaseTest extends SetUpUtils {
	public HomePage homePage;
	protected SmartCardPage smartCard;
	protected CardService cardService;
	protected AdminService adminService;
	protected AdminPage adminPage;
	protected HomePageService homePageService;

}
