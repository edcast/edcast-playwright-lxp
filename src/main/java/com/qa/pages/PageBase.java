package com.qa.pages;

import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

public class PageBase {
	public JSONObject testData;

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
}
