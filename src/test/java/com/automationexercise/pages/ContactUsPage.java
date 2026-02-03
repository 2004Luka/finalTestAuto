package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.time.Duration;

public class ContactUsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private final By getInTouchText = By.xpath("//*[self::h2 or self::h3][contains(normalize-space(), 'GET IN TOUCH')]");
    private final By nameField = By.xpath("//input[@data-qa='name' or @name='name']");
    private final By emailField = By.xpath("//input[@data-qa='email' or @name='email']");
    private final By subjectField = By.xpath("//input[@data-qa='subject' or @name='subject']");
    private final By messageField = By.xpath("//textarea[@data-qa='message' or @name='message']");
    private final By uploadFileInput = By.xpath("//input[@type='file' and (@name='upload_file' or @data-qa='upload_file')]");
    private final By submitButton = By.xpath("//input[@data-qa='submit-button' or @name='submit'] | //button[contains(normalize-space(),'Submit')]");
    private final By successMessage = By.xpath("//*[contains(normalize-space(), 'Success! Your details have been submitted successfully.')]");
    private final By homeButton = By.xpath("//a[contains(normalize-space(),'Home') and (@href='/' or contains(@href,'/'))]");

    private final By getInTouchTextAlt1 =
            By.xpath("//*[contains(translate(normalize-space(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'GET IN TOUCH')]");
    private final By getInTouchTextAlt2 =
            By.xpath("//div[contains(@class,'contact-form')]//*[contains(translate(normalize-space(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'GET IN TOUCH')]");

    public void waitUntilContactUsPageLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(d -> d.getCurrentUrl().contains("contact_us") || d.getTitle().toLowerCase().contains("contact"));
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(getInTouchText),
                    ExpectedConditions.visibilityOfElementLocated(getInTouchTextAlt1),
                    ExpectedConditions.visibilityOfElementLocated(getInTouchTextAlt2)
            ));
        } catch (Exception ignored) {
            // Keep it silent; test will assert using isGetInTouchVisible()
        }
    }

    public boolean isGetInTouchVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(getInTouchText));
            return true;
        } catch (Exception e) {
            try {
                new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOfElementLocated(getInTouchTextAlt1),
                        ExpectedConditions.visibilityOfElementLocated(getInTouchTextAlt2)
                ));
                return true;
            } catch (Exception ignored) {
                System.out.println("Contact Us URL: " + driver.getCurrentUrl());
                System.out.println("Contact Us Title: " + driver.getTitle());
                return false;
            }
        }
    }

    public void enterName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).clear();
        driver.findElement(nameField).sendKeys(name);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterSubject(String subject) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(subjectField)).clear();
        driver.findElement(subjectField).sendKeys(subject);
    }

    public void enterMessage(String message) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(messageField)).clear();
        driver.findElement(messageField).sendKeys(message);
    }

    public void uploadFile(Path filePath) {
        wait.until(ExpectedConditions.presenceOfElementLocated(uploadFileInput))
                .sendKeys(filePath.toAbsolutePath().toString());
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public void acceptAlertIfPresent() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (Exception ignored) {
            // If no alert appears, do nothing
        }
    }

    public boolean isSuccessMessageVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickHomeButton() {
        wait.until(ExpectedConditions.elementToBeClickable(homeButton)).click();
    }
}