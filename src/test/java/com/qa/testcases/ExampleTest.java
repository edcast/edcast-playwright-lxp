package com.qa.testcases;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class ExampleTest {

    private Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeMethod
    public void setup() {
        playwright = Playwright.create(); // Correct usage of static method
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        page = browser.newPage();
    }

    @Test
    public void testExampleOne() {
       System.out.println("1");
    }
    @Test
    public void testExample2() {
       System.out.println("1");
    }
    @Test
    public void testExample3() {
       System.out.println("3");
    }
    @Test
    public void testExample4() {
       System.out.println("4");
    }
    @Test
    public void testExample5() {
       System.out.println("5");
    }
    @Test
    public void testExample6() {
       System.out.println("6");
    }
    @Test
    public void testExample7() {
       System.out.println("7");
    }
    @Test
    public void testExample8() {
       System.out.println("8");
    }
    @Test
    public void testExample9() {
       System.out.println("9");
    } @Test
    public void testExample10() {
        System.out.println("10");
     }
    

    @AfterMethod
    public void teardown() {
        page.close();
        browser.close();
        playwright.close();
    }
}