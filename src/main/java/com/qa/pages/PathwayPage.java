package com.qa.pages;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.imageio.ImageIO;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.*;
import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import dev.brachtendorf.jimagehash.hashAlgorithms.PerceptiveHash;
import net.coobird.thumbnailator.Thumbnails;
import static com.qa.utils.Constants.Test_DATA.SMART_CARD_TEST_DATA;;

public class PathwayPage extends PageBase {
	Page page;
	private String pathwayTitle = "input[id='Pathway-Title']";
	private String ContinueButton = "button:has-text('Continue')";
	private String selectorTextVal = "input[placeholder='Search for existing SmartCards...']";
	private String cardCheckBox = "//*[text()='%s']/../../../../../input";
	private String AddContentButton = "button:has-text('Add Content Selection')";
	private String getCardTitle = "//*[@class='pathway-card-index' and text()=%s]/..//*[contains(@id,'card-title-')]";
	private String cardToMove = "//*[text()='%s']/../../../../../../../div/div/div/div/i/..";
	private String cardMoveToPosition = "//span[@class='pathway-card-index' and text()='%s']/..";
	private String pathwayCardIndex = "//span[@class='pathway-card-index']";

	public PathwayPage(Page page) {
		this.page = page;
	}

	public PathwayPage enterPathwayTitle(String searchTxt) {
		page.locator(pathwayTitle).fill(searchTxt);
		return new PathwayPage(page);
	}

	public PathwayPage clickOnContinueButton() {
		page.locator(ContinueButton).click();
		return new PathwayPage(page);
	}

	public PathwayPage clickOnAddContentButton() {
		page.locator(AddContentButton).click();
		return new PathwayPage(page);
	}

	public PathwayPage enterSmartCardSearchAndCheck(JSONArray... val) {
		try {
			for (JSONArray val1 : val) {
				page.fill(selectorTextVal, val1.toString());
				;
				// System.out.println(cardCheckBox.replace("%s", val1));
				page.locator(cardCheckBox.replace("%s", val1.toString())).click();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return this;
	}

	public PathwayPage enterSmartCardSearchAndCheck(JSONArray cardsArray) {
		try {
			for (int i = 0; i < cardsArray.length(); i++) {
				page.fill(selectorTextVal, cardsArray.getString(i));
				page.locator(cardCheckBox.replace("%s", cardsArray.getString(i))).click();
			}

		} catch (Exception e) {
			return new PathwayPage(page);
		}
		return null;

	}

	public String getCardTitle(String val) {
		return page.locator(getCardTitle.replace("%s", val)).innerText();

	}

	public PathwayPage moveCardUnderPathway(String cardName, String positionNumber) {
		try {int count =25;
			List<ElementHandle> elements = page.querySelectorAll(pathwayCardIndex);
			do {
				ElementHandle sourceElement = page.querySelector(cardToMove.replace("%s", cardName));
				ElementHandle targetElement = page.querySelector(cardMoveToPosition.replace("%s", positionNumber));
				if (sourceElement != null && targetElement != null) {
					BoundingBox sourceBox = sourceElement.boundingBox();
					BoundingBox targetBox = targetElement.boundingBox();
					sourceElement.hover();
					page.mouse().move(sourceBox.x + sourceBox.width / 2, sourceBox.y + sourceBox.height / 2);
					page.mouse().down();
					targetElement.hover();
					for (int i = 0; i <= elements.size(); i++) {
						targetElement.hover();
					}
					page.mouse().move(targetBox.x + targetBox.width / 2, targetBox.y + targetBox.height / 2);
					page.mouse().up();			
				}
				count--;
			} while ((!getCardTitle(positionNumber).toString().contains(cardName)) && count!=0);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}
}