package com.automationexercise.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.time.Duration;

public class ProductsCartActions {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public ProductsCartActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    private final By productCards = By.cssSelector(".features_items .product-image-wrapper, .product-image-wrapper");
    private final By continueShoppingButton = By.xpath("//button[contains(normalize-space(),'Continue Shopping')]");
    private final By viewCartLink = By.xpath("//u[normalize-space()='View Cart']/parent::a | //a[contains(@href,'/view_cart')][contains(.,'Cart') or .//u]");

    public void openCartByUrl() {
        String base = URI.create(driver.getCurrentUrl()).resolve("/").toString();
        driver.get(base + "view_cart");
        wait.until(d -> d.getCurrentUrl().contains("view_cart"));
    }

    public void clickViewCart() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(viewCartLink)).click();
            wait.until(d -> d.getCurrentUrl().contains("view_cart"));
        } catch (ElementClickInterceptedException intercepted) {
            openCartByUrl();
        } catch (TimeoutException te) {
            openCartByUrl();
        }
    }

    private WebElement getProductCard(int oneBasedIndex) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCards));
        return driver.findElements(productCards).get(oneBasedIndex - 1);
    }

    private By addToCartInsideCard = By.cssSelector("a.add-to-cart");

    public void hoverAndAddToCart(int productIndexOneBased) {
        WebElement card = getProductCard(productIndexOneBased);

        actions.moveToElement(card).pause(Duration.ofMillis(200)).perform();

        WebElement add = card.findElement(addToCartInsideCard);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(add)).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", add);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", add);
        }

        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(continueShoppingButton),
                ExpectedConditions.visibilityOfElementLocated(viewCartLink)
        ));
    }

    public void clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton)).click();
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(continueShoppingButton));
        } catch (Exception ignored) {
        }
    }

    private void clickSafely(By locator) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        } catch (Exception ignored) {
        }

        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
            return;
        } catch (ElementClickInterceptedException intercepted) {
            // fall through
        } catch (Exception ignored) {
            // fall through
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    public void clickContinueShoppingSafely() {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception ignored) {
        }

        clickSafely(continueShoppingButton);

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(continueShoppingButton));
        } catch (Exception ignored) {
        }
    }
}