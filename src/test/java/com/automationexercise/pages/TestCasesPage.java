package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestCasesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By testCasesHeaderAlt =
            By.xpath("//*[contains(translate(normalize-space(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'TEST CASES')]");

    public TestCasesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private final By testCasesHeader =
            By.xpath("//*[self::h2 or self::h1][contains(normalize-space(), 'Test Cases')]");

    public boolean isOnTestCasesPage() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("test_cases"),
                    ExpectedConditions.visibilityOfElementLocated(testCasesHeader),
                    ExpectedConditions.visibilityOfElementLocated(testCasesHeaderAlt)
            ));
            return driver.getCurrentUrl().contains("test_cases")
                    || !driver.findElements(testCasesHeader).isEmpty()
                    || !driver.findElements(testCasesHeaderAlt).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}