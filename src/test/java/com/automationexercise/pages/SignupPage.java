package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignupPage {

    private WebDriver driver;

    public SignupPage(WebDriver driver) {
        this.driver = driver;
    }

    // Elements on signup page
    private By nameField = By.xpath("//input[@data-qa='signup-name']");
    private By emailField = By.xpath("//input[@data-qa='signup-email']");
    private By signupButton = By.xpath("//button[@data-qa='signup-button']");

    // Verify 'New User Signup!' is visible
    private By newUserSignupText = By.xpath("//h2[contains(text(), 'New User Signup')]");

    // Methods
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
}