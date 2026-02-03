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
        wait.until(d -> isLoggedInAsVisible() || isLoginToYourAccountVisible() || isIncorrectCredentialsErrorVisible());
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
            System.out.println("After delete, URL is: " + driver.getCurrentUrl());
            return false;
        }
    }
}