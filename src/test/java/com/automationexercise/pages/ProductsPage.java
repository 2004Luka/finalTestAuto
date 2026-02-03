package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.time.Duration;

public class ProductsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private final By allProductsHeader =
            By.xpath("//*[self::h2 or self::h1][contains(normalize-space(), 'ALL PRODUCTS')]");

    private final By allProductsHeaderAlt =
            By.xpath("//*[contains(translate(normalize-space(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'ALL PRODUCTS')]");

    private final By productsList =
            By.xpath("//div[contains(@class,'features_items') or contains(@class,'product-image-wrapper')]");

    private final By productsGridAlt1 = By.cssSelector(".features_items");
    private final By productsGridAlt2 = By.cssSelector(".product-image-wrapper");
    private final By productsGridAlt3 = By.cssSelector(".single-products");

    private final By firstViewProductLink =
            By.xpath("(//a[contains(normalize-space(),'View Product')])[1]");

    public void openFirstProductDetailsByHref() {
        WebElement link = wait.until(ExpectedConditions.presenceOfElementLocated(firstViewProductLink));
        String href = link.getAttribute("href");

        if (href == null || href.isBlank()) {
            href = link.getDomAttribute("href");
        }

        if (href == null || href.isBlank()) {
            throw new IllegalStateException("View Product link has no href attribute");
        }

        if (href.startsWith("/")) {
            String base = URI.create(driver.getCurrentUrl()).resolve("/").toString();
            href = URI.create(base).resolve(href).toString();
        }

        driver.get(href);
        wait.until(d -> d.getCurrentUrl().contains("/product_details/"));
    }

    private boolean isProductsGridPresentNow() {
        return !driver.findElements(productsList).isEmpty()
                || !driver.findElements(productsGridAlt1).isEmpty()
                || !driver.findElements(productsGridAlt2).isEmpty()
                || !driver.findElements(productsGridAlt3).isEmpty();
    }

    public boolean isOnAllProductsPage() {
        try {
            wait.until(d ->
                    d.getCurrentUrl().contains("products")
                            || isProductsGridPresentNow()
                            || !d.findElements(allProductsHeader).isEmpty()
                            || !d.findElements(allProductsHeaderAlt).isEmpty()
            );

            return driver.getCurrentUrl().contains("products")
                    || !driver.findElements(allProductsHeader).isEmpty()
                    || !driver.findElements(allProductsHeaderAlt).isEmpty()
                    || isProductsGridPresentNow();
        } catch (Exception e) {
            System.out.println("ProductsPage check failed. URL: " + driver.getCurrentUrl());
            System.out.println("ProductsPage check failed. Title: " + driver.getTitle());
            return false;
        }
    }

    public boolean isProductsListVisible() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(productsList),
                    ExpectedConditions.visibilityOfElementLocated(productsGridAlt1),
                    ExpectedConditions.visibilityOfElementLocated(productsGridAlt2),
                    ExpectedConditions.visibilityOfElementLocated(productsGridAlt3)
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickFirstViewProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(firstViewProductLink)).click();
    }
}