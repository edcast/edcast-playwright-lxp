package com.qa.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.microsoft.playwright.Playwright;
import com.qa.base.PlaywrightFactory;
import com.qa.base.SimpleConfig;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock.Catch;
import org.apache.commons.configuration2.builder.BasicConfigurationBuilder;

public class Constants {

	public interface FRAMEWORK_DATA {
		String BASE_PATH = "src/test/resources/test_data/";
		String DATA_TO_DELETE = BASE_PATH + "garbageCollection.json";

	}

	public interface Test_DATA {
		String BASE_PATH = "src/test/resources/test_data/";
		String SMART_CARD_TEST_DATA = BASE_PATH + "smart_card_test_data.json";
		String HOME_PAGE_TEST_DATA = BASE_PATH + "home_page_test_data.json";
		String ADMIN_PAGE_TEST_DATA = BASE_PATH + "admin_page_test_data.json";
		String SKILL_PASSPORT_TEST_DATA = BASE_PATH + "skill_passport_test_data.json";
		String PAATHWAY_TEST_DATA = BASE_PATH + "pathway_test_data.json";

	}

	public interface FILE_PATH {
		String DOWNLOADED_USER_FILE_PATH = "test-output/csv_files/userSampleFile.csv";
		String USER_SAMPLE_FILE_UPDATED = "test-output/csv_files/loginUpdatedFile.csv";
		String FILE_DOWNLOADED = "test-output/";
		String FILE_UPLOAD = "test-output/";

	}



	public interface USER_DATA {

		String PICASSO_ADMIN_USER_EMAIL = SimpleConfig.getProperty("picasso.adminEmail");
		String PICASSO_ADMIN_USER_PASSWORD = SimpleConfig.getProperty("picasso.adminPassword");

		String TRAUTMATIONV_ADMIN_USER_EMAIL = SimpleConfig.getProperty("trautomationv.adminEmail");
		String TRAUTMATIONV_ADMIN_USER_PASSWORD = SimpleConfig.getProperty("trautomationv.adminPassword");

	}

}
