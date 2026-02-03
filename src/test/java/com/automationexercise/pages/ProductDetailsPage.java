package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductDetailsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private final By productName = By.xpath("//div[contains(@class,'product-information')]//h2");
    private final By category = By.xpath("//div[contains(@class,'product-information')]//p[contains(normalize-space(),'Category')]");
    private final By price = By.xpath("//div[contains(@class,'product-information')]//span[contains(text(),'Rs') or contains(text(),'$')]");
    private final By availability = By.xpath("//div[contains(@class,'product-information')]//p[contains(normalize-space(),'Availability')]");
    private final By condition = By.xpath("//div[contains(@class,'product-information')]//p[contains(normalize-space(),'Condition')]");
    private final By brand = By.xpath("//div[contains(@class,'product-information')]//p[contains(normalize-space(),'Brand')]");

    public boolean isOnProductDetailsPage() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/product_details/"),
                    ExpectedConditions.visibilityOfElementLocated(productName)
            ));
            return driver.getCurrentUrl().contains("/product_details/");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isProductNameVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
            return !driver.findElement(productName).getText().trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCategoryVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(category));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPriceVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(price));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAvailabilityVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(availability));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isConditionVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(condition));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBrandVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(brand));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}