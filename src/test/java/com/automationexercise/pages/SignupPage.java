package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignupPage {

    private WebDriver driver;

    public SignupPage(WebDriver driver) {
        this.driver = driver;
    }

    private By nameField = By.xpath("//input[@data-qa='signup-name']");
    private By emailField = By.xpath("//input[@data-qa='signup-email']");
    private By signupButton = By.xpath("//button[@data-qa='signup-button']");

    private By newUserSignupText = By.xpath("//h2[contains(text(), 'New User Signup')]");

    private By emailAlreadyExistsError =
            By.xpath("//*[self::p or self::div][contains(normalize-space(), 'Email Address already exist!')]");

    public void enterName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void clickSignupButton() {
        driver.findElement(signupButton).click();
    }

    public boolean isNewUserSignupVisible() {
        return driver.findElement(newUserSignupText).isDisplayed();
    }

    public boolean isEmailAlreadyExistsErrorVisible() {
        try {
            return driver.findElement(emailAlreadyExistsError).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}