package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AccountCreatedPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public AccountCreatedPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Elements - Using multiple strategies for better robustness
    private By accountCreatedMessage = By.xpath("//*[contains(text(), 'ACCOUNT CREATED')]");
    private By continueButton = By.xpath("//a[contains(@href, '/')][@class='btn btn-primary']");
    private By loggedInAsText = By.xpath("//a[contains(text(), 'Logged in as')]");
    private By deleteAccountButton = By.xpath("//a[contains(@href, 'delete_account')]");
    private By accountDeletedMessage = By.xpath("//*[contains(text(), 'ACCOUNT DELETED')]");

    // Helper method to close ANY popup
    private void closeAnyPopup() {
        try {
            By noThanksButton = By.xpath("//button[contains(text(), 'No thanks')]");
            driver.findElement(noThanksButton).click();
            System.out.println("Closed popup");
            Thread.sleep(500);
        } catch (Exception e) {
            // Popup might not exist, that's ok
        }
    }

    // Methods with wait and error handling
    public boolean isAccountCreatedMessageVisible() {
        try {
            // Close any popups that might be blocking
            closeAnyPopup();
            closeAnyPopup();
            closeAnyPopup();

            Thread.sleep(2000);

            wait.until(ExpectedConditions.visibilityOfElementLocated(accountCreatedMessage));
            return true;
        } catch (Exception e) {
            System.out.println("Account created message not visible: " + e.getMessage());
            return false;
        }
    }

    public void clickContinueButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        } catch (Exception e) {
            System.out.println("Continue button not found, trying alternative...");
            // Alternative: Click by any button with text "Continue"
            driver.findElement(By.xpath("//a[@class='btn btn-primary']")).click();
        }
    }

    public boolean isLoggedInAsVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loggedInAsText));
            return true;
        } catch (Exception e) {
            System.out.println("Logged in message not visible: " + e.getMessage());
            return false;
        }
    }

    public void clickDeleteAccountButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(deleteAccountButton)).click();
        } catch (Exception e) {
            System.out.println("Delete button not found: " + e.getMessage());
            // Try alternative selector
            driver.findElement(By.xpath("//a[contains(text(), 'Delete Account')]")).click();
        }
    }

    public boolean isAccountDeletedMessageVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(accountDeletedMessage));
            return true;
        } catch (Exception e) {
            System.out.println("Account deleted message not visible: " + e.getMessage());
            return false;
        }
    }
}