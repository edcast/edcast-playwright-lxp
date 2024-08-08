package com.qa.pages;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;
import javax.imageio.ImageIO;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import dev.brachtendorf.jimagehash.hashAlgorithms.PerceptiveHash;
import net.coobird.thumbnailator.Thumbnails;

import static com.qa.utils.Constants.Test_DATA.SMART_CARD_TEST_DATA;;

public class SmartCardPage extends PageBase {
    private Page page;
	private JSONObject testData;
    private String createButton = "id=smartcart-tab-key-1";
    private String label = "Select Files to Upload";
    private String fileUploadPath = "input[id='fsp-fileUpload']";
    private String clickUpload = "span[title='Upload']";
    private String cardSkills = "input[id='skills']";
    private String selectFirstCardSkill = "div[id='react-select-2-option-0']";
    private String labelCardTitle = "Enter title";
    private String cardLevel = "//*[text()='Level']/following-sibling::select";
    private String createCardButton = "xpath=//div[@id='create_smartCard_panel-undefined']/../following-sibling::div//button[@id='create-card-btn']";
    private String cardImage = "xpath=//img[@alt='User provided image']";
    private String toastMessagePath = "xpath=//*[@id='success-alertBox']//following-sibling::span";
    private String iconTrash = "xpath=//*[@class='icon-trash']";
    private String imageSavingProgressBar = "xpath=//*[@class='fsp-progress-bar_bar']";

    public SmartCardPage(Page page) {
        this.page = page;
        testData = getTestData(SMART_CARD_TEST_DATA);
    }



    public SmartCardPage clickOnUploadContent() {
        page.locator(createButton).click();
        return this;
    }

    public SmartCardPage clickToUploadFile(String searchTxt) {
        page.getByLabel(label).click();
        page.waitForSelector(iconTrash);
        return this;
    }

    public HomePage setCardDetails(String cardName) {
        page.locator(fileUploadPath).setInputFiles(Paths.get(testData.getString("test_file_name")).toAbsolutePath());
        pause(3);
        page.locator(clickUpload).click();
        page.getByLabel(labelCardTitle).clear();
        page.getByLabel(labelCardTitle).fill(cardName);
        page.waitForSelector(imageSavingProgressBar,
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN));
        page.locator(cardSkills).click();
        page.locator(cardSkills).fill(testData.getString("cardSkills"));
        page.locator(selectFirstCardSkill).click();
        page.selectOption(cardLevel, testData.getString("cardLevel"));
        page.locator(createCardButton).click();
        return new HomePage(page);
    }

    public String getToastMessage() {
        page.waitForSelector(toastMessagePath);
        return page.innerText(toastMessagePath);

    }

    public String getToastMessage(Page page) {
        page.waitForSelector(toastMessagePath);
        return page.innerText(toastMessagePath);

    }

    public int captureCardImageAndGetHammingDistance(String cardName, Page page, JSONObject val) {
        page.click("text='" + cardName + "'");
        Locator imageLocator = page.locator(cardImage);
        Path screenshotPath = Paths.get("test-output/captured_image.jpg");
        imageLocator.screenshot(new Locator.ScreenshotOptions().setPath(screenshotPath));
        File capturedImage = screenshotPath.toFile();
        File referenceImage = new File(val.getString("test_file_name").toString());
        int hammingDistance = compareImagesWithTolerance(capturedImage, referenceImage);
        return hammingDistance;
    }

    public static boolean compareExactImages(File fileA, File fileB) {
        try {
            BufferedImage imgA = ImageIO.read(fileA);
            BufferedImage imgB = ImageIO.read(fileB);
            if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
                return false;
            }

            for (int y = 0; y < imgA.getHeight(); y++) {
                for (int x = 0; x < imgA.getWidth(); x++) {
                    if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                        return false;
                    }
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int compareImagesWithTolerance(File fileA, File fileB) {
        int hammingDistance = -1;
        try {
            BufferedImage imgA = ImageIO.read(fileA);
            BufferedImage imgB = ImageIO.read(fileB);

            if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
                imgA = Thumbnails.of(imgA).size(100, 100).asBufferedImage();
                imgB = Thumbnails.of(imgB).size(100, 100).asBufferedImage();
            }

            HashingAlgorithm hasher = new PerceptiveHash(64);
            Hash hashA = hasher.hash(imgA);
            Hash hashB = hasher.hash(imgB);

            hammingDistance = hashA.hammingDistance(hashB);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return hammingDistance;
    }

}
