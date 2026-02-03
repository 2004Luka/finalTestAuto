package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import java.util.List;

public class ProductSearchComponent {

    private final WebDriver driver;
    private final org.openqa.selenium.support.ui.WebDriverWait wait;

    public ProductSearchComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15));
    }

    private final By searchInput = By.id("search_product");
    private final By searchButton = By.id("submit_search");

    private final By searchedProductsHeader =
            By.xpath("//*[self::h2 or self::h1][contains(translate(normalize-space(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'SEARCHED PRODUCTS')]");

    private final By resultCards =
            By.cssSelector(".features_items .product-image-wrapper, .product-image-wrapper, .single-products");

    private final By resultNames =
            By.cssSelector(".features_items .productinfo p, .productinfo p");

    private void scrollIntoView(WebElement el) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        } catch (Exception ignored) {
            // best-effort
        }
    }

    public void searchFor(String productName) {
        WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(searchInput));
        scrollIntoView(input);
        wait.until(ExpectedConditions.visibilityOf(input));
        input.clear();
        input.sendKeys(productName);

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        scrollIntoView(btn);
        btn.click();
    }

    public boolean isSearchedProductsHeaderVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchedProductsHeader));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areAnySearchResultsVisible() {
        try {
            wait.until(d -> {
                List<WebElement> cards = d.findElements(resultCards);
                return !cards.isEmpty() && cards.stream().anyMatch(WebElement::isDisplayed);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean doesAnyVisibleResultNameContain(String expectedTerm) {
        String term = expectedTerm == null ? "" : expectedTerm.trim().toLowerCase();

        try {
            wait.until(d -> !d.findElements(resultNames).isEmpty());
            List<WebElement> names = driver.findElements(resultNames);

            for (WebElement el : names) {
                if (!el.isDisplayed()) continue;
                String text = el.getText() == null ? "" : el.getText().trim().toLowerCase();
                if (text.contains(term)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}