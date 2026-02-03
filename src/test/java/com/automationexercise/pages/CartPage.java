package com.automationexercise.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private final By cartTableRows = By.cssSelector("table#cart_info_table tbody tr");
    private final By cartPageMarker = By.cssSelector("#cart_info_table");

    public void waitForCartPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartPageMarker));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartTableRows));
    }

    public int getCartRowCount() {
        return driver.findElements(cartTableRows).size();
    }

    private WebElement getRow(int oneBasedRowIndex) {
        List<WebElement> rows = driver.findElements(cartTableRows);
        return rows.get(oneBasedRowIndex - 1);
    }

    private int parseMoneyToInt(String text) {
        String digits = text.replaceAll("[^0-9]", "");
        if (digits.isBlank()) return 0;
        return Integer.parseInt(digits);
    }

    public int getRowPrice(int oneBasedRowIndex) {
        WebElement row = getRow(oneBasedRowIndex);
        String priceText = row.findElement(By.cssSelector("td.cart_price")).getText();
        return parseMoneyToInt(priceText);
    }

    public int getRowQuantity(int oneBasedRowIndex) {
        WebElement row = getRow(oneBasedRowIndex);

        WebElement qtyEl = row.findElement(By.cssSelector("td.cart_quantity button, td.cart_quantity"));
        String qtyText = qtyEl.getText().trim();
        String digits = qtyText.replaceAll("[^0-9]", "");
        return digits.isBlank() ? 0 : Integer.parseInt(digits);
    }

    public int getRowTotal(int oneBasedRowIndex) {
        WebElement row = getRow(oneBasedRowIndex);
        String totalText = row.findElement(By.cssSelector("td.cart_total")).getText();
        return parseMoneyToInt(totalText);
    }

    public boolean isRowVisible(int oneBasedRowIndex) {
        try {
            return getRow(oneBasedRowIndex).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}