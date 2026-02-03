package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private final By loginToYourAccountText = By.xpath("//h2[contains(normalize-space(), 'Login to your account')]");
    private final By emailField = By.xpath("//input[@data-qa='login-email']");
    private final By passwordField = By.xpath("//input[@data-qa='login-password']");
    private final By loginButton = By.xpath("//button[@data-qa='login-button']");
    private final By loggedInAsText = By.xpath("//a[contains(normalize-space(), 'Logged in as')]");
    private final By deleteAccountButton =
            By.xpath("//a[contains(@href, 'delete_account') or contains(normalize-space(), 'Delete Account')]");

    private final By accountDeletedMessage =
            By.xpath("//*[self::h1 or self::h2 or self::h3 or self::b or self::p or self::div]" +
                    "[contains(normalize-space(), 'ACCOUNT DELETED')]");
    private final By logoutButton = By.xpath("//a[contains(normalize-space(), 'Logout') and (@href='/logout' or contains(@href,'logout'))]");

    private final By incorrectCredentialsError =
            By.xpath("//p[contains(normalize-space(), 'Your email or password is incorrect!')]");
    private final By accountDeletedMessageAlt =
            By.xpath("//*[self::h1 or self::h2 or self::h3 or self::b or self::p or self::div]" +
                    "[contains(translate(normalize-space(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'ACCOUNT DELETED')]");
    private By testCasesButtonAlt = By.cssSelector("a[href*='test_cases']");
    private By productsButtonAlt = By.cssSelector("a[href*='products']");
    private By testCasesButton =
            By.xpath("//a[@href='/test_cases' or contains(normalize-space(),'Test Cases')]");
    private By productsButton =
            By.xpath("//a[@href='/products' or contains(normalize-space(),'Products')]");
    private boolean isLoggedInAsVisibleNow() {
        return !driver.findElements(loggedInAsText).isEmpty();
    }

    private boolean isLoginToYourAccountVisibleNow() {
        return !driver.findElements(loginToYourAccountText).isEmpty();
    }

    private boolean isIncorrectCredentialsErrorVisibleNow() {
        return !driver.findElements(incorrectCredentialsError).isEmpty();
    }
    public void clickLoginButtonExpectingIncorrectCredentialsError() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(incorrectCredentialsError));
    }


    public boolean isLoginToYourAccountVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginToYourAccountText));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).clear();
        driver.findElement(passwordField).sendKeys(password);
    }


    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

        wait.until(d ->
                isLoggedInAsVisibleNow()
                        || isIncorrectCredentialsErrorVisibleNow()
                        || d.getCurrentUrl().contains("/login")
                        || isLoginToYourAccountVisibleNow()
        );
    }

    public boolean isLoggedInAsVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loggedInAsText));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isIncorrectCredentialsErrorVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(incorrectCredentialsError));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogoutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginToYourAccountText));
    }

    public void clickDeleteAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteAccountButton)).click();

        wait.until(d ->
                d.getCurrentUrl().contains("delete_account") ||
                        !d.findElements(accountDeletedMessage).isEmpty()
        );
    }

    public boolean isAccountDeletedMessageVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(accountDeletedMessage));
            return true;
        } catch (Exception e) {
            try {
                new WebDriverWait(driver, Duration.ofSeconds(25))
                        .until(ExpectedConditions.visibilityOfElementLocated(accountDeletedMessageAlt));
                return true;
            } catch (Exception ignored) {
                System.out.println("After delete, URL is: " + driver.getCurrentUrl());
                return false;
            }
        }
    }
    private void jsClick(By locator) {
        var element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void clickTestCasesButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(testCasesButton)).click();
        } catch (Exception e) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(testCasesButtonAlt)).click();
            } catch (Exception e2) {
                jsClick(testCasesButton);
            }
        }
    }

    public void clickProductsButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(productsButton)).click();
        } catch (Exception e) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(productsButtonAlt)).click();
            } catch (Exception e2) {
                jsClick(productsButton);
            }
        }
    }
}