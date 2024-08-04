package com.qa.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Listeners;

import static com.qa.utils.Constants.FILE_PATH.FILE_DOWNLOADED;

import com.google.gson.Gson;

import io.qameta.allure.testng.AllureTestNg;

public class CommonUtils {
	public void cleanAndWriteCSV(String inputFilePath, String outputFilePath, JSONArray newRow) {

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
				BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

			String header = reader.readLine();
			if (header == null) {
				System.err.println("The input file is empty or the header is missing.");
				return;
			}
			if (header != null) {
				writer.write(header);
				writer.newLine();
			}
			System.out.println(newRow.length());
			IntStream.range(0, newRow.length()).forEach(i -> {
				JSONArray newRow11 = newRow.getJSONArray(i);
				String[] stringArray = new Gson().fromJson(newRow11.toString(), String[].class);
				System.out.println(stringArray);
				try {
					writeLine(writer, stringArray);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readContentFromPDF(String fileName) {

		String pdfFileInfo = null;
		try {

			File file = new File(FILE_DOWNLOADED + fileName);
			PDDocument document = PDDocument.load(file);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			pdfFileInfo = pdfStripper.getText(document);
			System.out.println("Text in the PDF: " + pdfFileInfo);
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pdfFileInfo;
	}

	public  static void writeLine(BufferedWriter writer, String[] values) throws IOException {
		String line = String.join(",", values);
		writer.write(line);
		writer.newLine();
	}

}
