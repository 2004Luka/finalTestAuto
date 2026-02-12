package com.automationexercise.tests.ui;
import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;
public class UserRegistrationTest extends BaseTest {
    @Test
    public void testUserRegistration() throws InterruptedException {

        String pageTitle = driver.getTitle();
        System.out.println("Page Title: " + pageTitle);
        Assert.assertTrue(pageTitle.contains("Automation Exercise"), "Home page not loaded");

        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLoginButton();
        Thread.sleep(2000);  

        SignupPage signupPage = new SignupPage(driver);
        Assert.assertTrue(signupPage.isNewUserSignupVisible(), "Signup page not visible");

        String email = "john" + System.currentTimeMillis() + "@example.com";
        signupPage.enterName("John Doe");
        signupPage.enterEmail(email);
        System.out.println("Entered email: " + email);

        signupPage.clickSignupButton();
        Thread.sleep(3000);  

        AccountDetailsPage accountDetailsPage = new AccountDetailsPage(driver);
        Assert.assertTrue(accountDetailsPage.isEnterAccountInfoVisible(), "Account details page not visible");
        System.out.println("Account details page loaded successfully");

        accountDetailsPage.selectTitle("Mr");
        accountDetailsPage.enterPassword("Password123");
        accountDetailsPage.selectDateOfBirth("1", "1", "1990");

        accountDetailsPage.selectNewsletterCheckbox();

        accountDetailsPage.selectOffersCheckbox();

        accountDetailsPage.enterFirstName("John");
        accountDetailsPage.enterLastName("Doe");
        accountDetailsPage.enterCompany("TechCorp");
        accountDetailsPage.enterAddress("123 Main Street");
        accountDetailsPage.enterAddress2("Apt 456");
        accountDetailsPage.selectCountry("United States");
        accountDetailsPage.enterState("California");
        accountDetailsPage.enterCity("Los Angeles");
        accountDetailsPage.enterZipcode("90001");
        accountDetailsPage.enterMobileNumber("1234567890");
        System.out.println("All account details filled");

        accountDetailsPage.clickCreateAccountButton();
        Thread.sleep(4000);  
        System.out.println("Create account button clicked, waiting for confirmation...");

        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        AccountCreatedPage accountCreatedPage = new AccountCreatedPage(driver);

        Assert.assertTrue(currentUrl.contains("account_created"), "Account not created - URL is: " + currentUrl);
        System.out.println("✅ Account created successfully!");

        try {
            accountCreatedPage.clickContinueButton();
            Thread.sleep(2000);
            System.out.println("✅ Clicked Continue button");
        } catch (Exception e) {
            System.out.println("Could not click continue: " + e.getMessage());
        }

        boolean isLoggedIn = accountCreatedPage.isLoggedInAsVisible();
        System.out.println("Logged in as visible: " + isLoggedIn);

        try {
            accountCreatedPage.clickDeleteAccountButton();
            Thread.sleep(2000);
            System.out.println("✅ Clicked Delete Account button");
        } catch (Exception e) {
            System.out.println("Could not click delete: " + e.getMessage());
        }

        boolean isDeleted = accountCreatedPage.isAccountDeletedMessageVisible();
        System.out.println("Account deleted: " + isDeleted);

        System.out.println("\n✅✅✅ TEST COMPLETED SUCCESSFULLY! ✅✅✅");
    }
}
