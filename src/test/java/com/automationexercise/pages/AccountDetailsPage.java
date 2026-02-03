package com.automationexercise.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
public class AccountDetailsPage {
    private WebDriver driver;

    public AccountDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    // Elements - CORRECTED based on actual website HTML
    private By enterAccountInfoText = By.xpath("//h2[@class='title text-center']");
    private By titleMr = By.id("id_gender1");
    private By titleMrs = By.id("id_gender2");
    private By passwordField = By.id("password");
    private By dayDropdown = By.id("days");
    private By monthDropdown = By.id("months");
    private By yearDropdown = By.id("years");
    private By newsletterCheckbox = By.id("newsletter");
    private By offersCheckbox = By.id("optin");
    private By firstNameField = By.id("first_name");
    private By lastNameField = By.id("last_name");
    private By companyField = By.id("company");
    private By addressField = By.id("address1");
    private By address2Field = By.id("address2");
    private By countryDropdown = By.id("country");
    private By stateField = By.id("state");
    private By cityField = By.id("city");
    private By zipcodeField = By.id("zipcode");
    private By mobileField = By.id("mobile_number");
    private By createAccountButton = By.xpath("//button[@data-qa='create-account']");

    // Methods
    public boolean isEnterAccountInfoVisible() {
        try {
            return driver.findElement(enterAccountInfoText).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void selectTitle(String title) {
        if (title.equalsIgnoreCase("Mr")) {
            driver.findElement(titleMr).click();
        } else if (title.equalsIgnoreCase("Mrs")) {
            driver.findElement(titleMrs).click();
        }
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void selectDateOfBirth(String day, String month, String year) {
        new Select(driver.findElement(dayDropdown)).selectByValue(day);
        new Select(driver.findElement(monthDropdown)).selectByValue(month);
        new Select(driver.findElement(yearDropdown)).selectByValue(year);
    }

    public void selectNewsletterCheckbox() {
        WebElement checkbox = driver.findElement(newsletterCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void selectOffersCheckbox() {
        WebElement checkbox = driver.findElement(offersCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void enterFirstName(String firstName) {
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void enterCompany(String company) {
        driver.findElement(companyField).sendKeys(company);
    }

    public void enterAddress(String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    public void enterAddress2(String address2) {
        driver.findElement(address2Field).sendKeys(address2);
    }

    public void selectCountry(String country) {
        new Select(driver.findElement(countryDropdown)).selectByVisibleText(country);
    }

    public void enterState(String state) {
        driver.findElement(stateField).sendKeys(state);
    }

    public void enterCity(String city) {
        driver.findElement(cityField).sendKeys(city);
    }

    public void enterZipcode(String zipcode) {
        driver.findElement(zipcodeField).sendKeys(zipcode);
    }

    public void enterMobileNumber(String mobile) {
        driver.findElement(mobileField).sendKeys(mobile);
    }

    public void clickCreateAccountButton() {
        driver.findElement(createAccountButton).click();
    }
}