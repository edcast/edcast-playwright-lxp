package com.qa.base;

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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.microsoft.playwright.Page;
import com.qa.utils.PlayWrightUtils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class SetUpUtils extends PlaywrightFactory {

//    protected PathwayService pathwayService;
//    protected PathwayPage pathwayPage;
    protected static Properties config;

    @BeforeMethod
    public void setUp() {
        config = loadProperties("config.properties");
        PlaywrightFactory.createPage(config.getProperty(config.getProperty("env") + ".baseUrl"),
                config.getProperty("browserType"), config);
    }

    public <T> T getPage(Class<T> pageClass, Page page) {
        try {
            Constructor<T> constructor = pageClass.getDeclaredConstructor(Page.class);
            return constructor.newInstance(page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @AfterMethod
    public void tearDown() {
        PlaywrightSession playwrightSession = playwrightSessionThreadLocal.get();
        if (playwrightSession != null) {
            playwrightSession.getPage().close();
            playwrightSession.getBrowserContext().close();
            playwrightSession.getBrowser().close();
            playwrightSession.getPlaywright().close();
            playwrightSessionThreadLocal.remove();
        }
    }

    @AfterMethod
    public void testCaseFailureVideo(ITestResult result) {
        if (Boolean.parseBoolean(config.getProperty("video-recording")))
            if (!result.isSuccess()) {
                List<String> ls = getFilesUnderFolder(PlaywrightFactory.path.toString());
                String pathval = PlaywrightFactory.path + "/" + ls.get(0);
                File file = new File(pathval);
                try {
                    Allure.addAttachment("Test Case video file", new FileInputStream(file));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Allure.addAttachment("Test Case video file link", file.getAbsolutePath().toString());
                System.out.println(file.getAbsolutePath());
                logStep(file.getAbsolutePath());

            }
    }

    @AfterMethod
    public void failureScreenshot(ITestResult result) {
        if (Boolean.parseBoolean(config.getProperty("failure-screenshot")))
            if (!result.isSuccess()) {
                String screenshotPath = config.getProperty("failure-screenshot-path")
                        + result.getMethod().getMethodName() + ".png";
                PlayWrightUtils.captureScreenshot(playwrightSessionThreadLocal.get().getPage(), screenshotPath);

            }
    }

    public List<String> getFilesUnderFolder(String folderPath) {
        List<String> ls = new ArrayList<>();
        try (Stream<Path> paths = Files.list(Paths.get(folderPath))) {
            paths.filter(Files::isRegularFile)
                    .forEach(path -> System.out.println(ls.add(path.getFileName().toString())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ls;

    }

    public JSONObject getTestData(String path) {
        JSONObject testData;
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
                logStep("Sorry, unable to find " + fileName);
                return null;
            }
            properties.load(input);
        } catch (IOException ex) {
            logStep("Unable to load property file");
            ex.printStackTrace();
        }
        return properties;
    }

    @Step("Log: {message}")
    public void logStep(String message) {
        Allure.addAttachment("Log", new ByteArrayInputStream(message.getBytes()));
    }

    public void deleteGarbageData() {
        Properties properties = loadProperties("values.properties");
        for (String key : properties.stringPropertyNames()) {
            System.out.println(properties.getProperty(key));
            Path path = Paths.get(properties.getProperty(key));
            if (Files.exists(path)) {
                try {
                    Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
