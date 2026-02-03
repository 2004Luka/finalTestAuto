package com.automationexercise.tests.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.AccountCreatedPage;
import com.automationexercise.pages.AccountDetailsPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.LoginPage;
import com.automationexercise.pages.SignupPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterWithExistingEmailTest extends BaseTest {

    @Test(description = "Test Case 5: Register User with existing email")
    public void testCase5_registerUserWithExistingEmail() {
        // Step 1 & 2: Launch browser + Navigate (BaseTest opens base.url)
        // Step 3: Verify that home page is visible successfully
        Assert.assertTrue(driver.getTitle().contains("Automation Exercise"), "Home page not loaded (title mismatch)");

        // Step 4: Click on 'Signup / Login' button
        HomePage homePage = new HomePage(driver);
        closePopups();
        homePage.clickSignupLoginButton();

        // Step 5: Verify 'New User Signup!' is visible
        SignupPage signupPage = new SignupPage(driver);
        Assert.assertTrue(signupPage.isNewUserSignupVisible(), "'New User Signup!' is not visible");

        // --- Create a user first so we have an already-registered email ---
        String existingEmail = "existing" + System.currentTimeMillis() + "@example.com";
        String password = "Password123";

        signupPage.enterName("Existing User");
        signupPage.enterEmail(existingEmail);
        closePopups();
        signupPage.clickSignupButton();

        AccountDetailsPage accountDetailsPage = new AccountDetailsPage(driver);
        Assert.assertTrue(accountDetailsPage.isEnterAccountInfoVisible(), "ENTER ACCOUNT INFORMATION not visible");

        accountDetailsPage.selectTitle("Mr");
        accountDetailsPage.enterPassword(password);
        accountDetailsPage.selectDateOfBirth("1", "1", "1990");
        accountDetailsPage.selectNewsletterCheckbox();
        accountDetailsPage.selectOffersCheckbox();

        accountDetailsPage.enterFirstName("Existing");
        accountDetailsPage.enterLastName("User");
        accountDetailsPage.enterCompany("TestCompany");
        accountDetailsPage.enterAddress("123 Main Street");
        accountDetailsPage.enterAddress2("Apt 1");
        accountDetailsPage.selectCountry("United States");
        accountDetailsPage.enterState("CA");
        accountDetailsPage.enterCity("Los Angeles");
        accountDetailsPage.enterZipcode("90001");
        accountDetailsPage.enterMobileNumber("1234567890");

        closePopups();
        accountDetailsPage.clickCreateAccountButton();

        AccountCreatedPage accountCreatedPage = new AccountCreatedPage(driver);
        closePopups();
        accountCreatedPage.clickContinueButton();

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoggedInAsVisible(), "Not logged in after creating account");

        // Logout to return to login page and attempt signup again with same email
        closePopups();
        loginPage.clickLogoutButton();

        // We are back on /login, where New User Signup exists too
        Assert.assertTrue(signupPage.isNewUserSignupVisible(), "'New User Signup!' is not visible after logout");

        // Step 6: Enter name and already registered email address
        signupPage.enterName("Another Name");
        signupPage.enterEmail(existingEmail);

        // Step 7: Click 'Signup' button
        closePopups();
        signupPage.clickSignupButton();

        // Step 8: Verify error 'Email Address already exist!' is visible
        Assert.assertTrue(
                signupPage.isEmailAlreadyExistsErrorVisible(),
                "Error 'Email Address already exist!' is not visible"
        );
    }
}