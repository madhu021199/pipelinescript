package com.registration.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.logging.Logger;

public class TestHooks {

    private static final Logger logger = Logger.getLogger(TestHooks.class.getName());

    public static WebDriver driver;

    @Before
    public void setUp() {
        logger.info("Setting up WebDriver...");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        logger.info("WebDriver setup complete. Browser launched.");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing the browser...");
            driver.quit();
            logger.info("Browser closed.");
        }
    }
}