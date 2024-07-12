package com.qa.utils;

public class Constants {


	
	public interface Test_DATA{
		String BASE_PATH = "src/test/resources/test_data/";
		String SMART_CARD_TEST_DATA = BASE_PATH + "smart_card_test_data.json";
		String HOME_PAGE_TEST_DATA = BASE_PATH + "home_page_test_data.json";
		String ADMIN_PAGE_TEST_DATA = BASE_PATH + "admin_page_test_data.json";
		String SKILL_PASSPORT_TEST_DATA = BASE_PATH + "skill_passport_test_data.json";


	}
	
	public interface FILE_PATH{
		String DOWNLOADED_USER_FILE_PATH = "src/test/resources/download/csv_files/userSampleFile.csv";
		String USER_SAMPLE_FILE_UPDATED ="src/test/resources/csv_files/loginUpdatedFile.csv";
		String FILE_DOWNLOADED ="src/test/resources/download/";


	}
}
