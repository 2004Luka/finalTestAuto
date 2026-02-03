package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By signupLoginButton1 = By.xpath("//a[@href='/login']");
    private By signupLoginButton2 = By.linkText("Signup / Login");
    private By signupLoginButton3 = By.xpath("//a[contains(text(), 'Signup')]");
    private By contactUsButton = By.xpath("//a[contains(normalize-space(), 'Contact us') or contains(normalize-space(), 'Contact Us') or @href='/contact_us']");

    public void clickSignupLoginButton() {
        try {
            System.out.println("Trying selector 1: //a[@href='/login']");
            wait.until(ExpectedConditions.elementToBeClickable(signupLoginButton1)).click();
            System.out.println("✅ Clicked using selector 1");
        } catch (Exception e) {
            System.out.println("Selector 1 failed, trying selector 2");
            try {
                System.out.println("Trying selector 2: linkText 'Signup / Login'");
                wait.until(ExpectedConditions.elementToBeClickable(signupLoginButton2)).click();
                System.out.println("✅ Clicked using selector 2");
            } catch (Exception e2) {
                System.out.println("Selector 2 failed, trying selector 3");
                try {
                    System.out.println("Trying selector 3: //a[contains(text(), 'Signup')]");
                    wait.until(ExpectedConditions.elementToBeClickable(signupLoginButton3)).click();
                    System.out.println("✅ Clicked using selector 3");
                } catch (Exception e3) {
                    System.out.println("❌ All selectors failed!");
                    throw new RuntimeException("Could not find Signup/Login button with any selector", e3);
                }
            }
        }
    }

    public void clickContactUsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(contactUsButton)).click();
    }
}