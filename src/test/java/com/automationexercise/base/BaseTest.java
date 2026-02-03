package com.automationexercise.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected WebDriver driver;
    protected Properties properties;

    @BeforeMethod
    public void setUp() {
        loadProperties();
        initializeDriver();
    }

    private void loadProperties() {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeDriver() {
        String browser = properties.getProperty("browser", "chrome").toLowerCase();
        String headless = properties.getProperty("headless", "false");

        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();

            if (headless.equals("true")) {
                options.addArguments("--headless=new");
            }

            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2); // block
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", prefs);

            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-save-password-popup");
            options.addArguments("--disable-translate");
            options.addArguments("--no-default-browser-check");
            options.addArguments("--disable-sync");

            driver = new ChromeDriver(options);

        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            if (headless.equals("true")) {
                options.addArguments("--headless");
            }
            driver = new FirefoxDriver(options);
        }

        // IMPORTANT: avoid mixing implicit wait with explicit waits (it causes long hangs)
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(Long.parseLong(properties.getProperty("page.load.timeout", "30")))
        );

        driver.manage().window().maximize();
        driver.get(properties.getProperty("base.url"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void closePopups() {
        // Using findElements() so it returns immediately (no implicit-wait delays)
        clickIfPresent(By.xpath("//button[contains(., 'No thanks')]"));
        clickIfPresent(By.xpath("//button[contains(., 'Close')]"));
        clickIfPresent(By.xpath("//button[@aria-label='Close']"));
        clickIfPresent(By.xpath("//div[contains(@role, 'dialog')]//button[1]"));
    }

    private void clickIfPresent(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty() && elements.get(0).isDisplayed() && elements.get(0).isEnabled()) {
                elements.get(0).click();
            }
        } catch (Exception ignored) {
            // ignore anything flaky here; it's best-effort
        }
    }
}