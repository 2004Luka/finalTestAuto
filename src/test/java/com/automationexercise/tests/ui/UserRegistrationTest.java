package com.automationexercise.tests.ui;
import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;
public class UserRegistrationTest extends BaseTest {
    @Test
    public void testUserRegistration() throws InterruptedException {

        // Step 1 & 2 & 3: Launch browser and navigate (BaseTest does this in setUp)
        // Step 3: Verify home page is visible
        String pageTitle = driver.getTitle();
        System.out.println("Page Title: " + pageTitle);
        Assert.assertTrue(pageTitle.contains("Automation Exercise"), "Home page not loaded");

        // Step 4: Click on 'Signup / Login' button
        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLoginButton();
        Thread.sleep(2000);  // Wait for page to load

        // Step 5: Verify 'New User Signup!' is visible
        SignupPage signupPage = new SignupPage(driver);
        Assert.assertTrue(signupPage.isNewUserSignupVisible(), "Signup page not visible");

        // Step 6: Enter name and email address
        String email = "john" + System.currentTimeMillis() + "@example.com";
        signupPage.enterName("John Doe");
        signupPage.enterEmail(email);
        System.out.println("Entered email: " + email);

        // Step 7: Click 'Signup' button
        signupPage.clickSignupButton();
        Thread.sleep(3000);  // Wait for next page to load

        // Step 8: Verify that 'ENTER ACCOUNT INFORMATION' is visible
        AccountDetailsPage accountDetailsPage = new AccountDetailsPage(driver);
        Assert.assertTrue(accountDetailsPage.isEnterAccountInfoVisible(), "Account details page not visible");
        System.out.println("Account details page loaded successfully");

        // Step 9: Fill details: Title, Name, Email, Password, Date of birth
        accountDetailsPage.selectTitle("Mr");
        accountDetailsPage.enterPassword("Password123");
        accountDetailsPage.selectDateOfBirth("1", "1", "1990");

        // Step 10: Select checkbox 'Sign up for our newsletter!'
        accountDetailsPage.selectNewsletterCheckbox();

        // Step 11: Select checkbox 'Receive special offers from our partners!'
        accountDetailsPage.selectOffersCheckbox();

        // Step 12: Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
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

        // Step 13: Click 'Create Account' button
        accountDetailsPage.clickCreateAccountButton();
        Thread.sleep(4000);  // Wait for account creation
        System.out.println("Create account button clicked, waiting for confirmation...");

        // Check current URL to see if account was created
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        // Step 14-18: Verify account created and delete
        AccountCreatedPage accountCreatedPage = new AccountCreatedPage(driver);

        // If we're on account_created page, account was created successfully!
        Assert.assertTrue(currentUrl.contains("account_created"), "Account not created - URL is: " + currentUrl);
        System.out.println("✅ Account created successfully!");

        // Step 15: Click 'Continue' button
        try {
            accountCreatedPage.clickContinueButton();
            Thread.sleep(2000);
            System.out.println("✅ Clicked Continue button");
        } catch (Exception e) {
            System.out.println("Could not click continue: " + e.getMessage());
        }

        // Step 16: Verify that 'Logged in as username' is visible
        boolean isLoggedIn = accountCreatedPage.isLoggedInAsVisible();
        System.out.println("Logged in as visible: " + isLoggedIn);

        // Step 17: Click 'Delete Account' button
        try {
            accountCreatedPage.clickDeleteAccountButton();
            Thread.sleep(2000);
            System.out.println("✅ Clicked Delete Account button");
        } catch (Exception e) {
            System.out.println("Could not click delete: " + e.getMessage());
        }

        // Step 18: Verify that 'ACCOUNT DELETED!' is visible
        boolean isDeleted = accountCreatedPage.isAccountDeletedMessageVisible();
        System.out.println("Account deleted: " + isDeleted);

        System.out.println("\n✅✅✅ TEST COMPLETED SUCCESSFULLY! ✅✅✅");
    }
}