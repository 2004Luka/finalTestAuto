package com.automationexercise.tests.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.AccountCreatedPage;
import com.automationexercise.pages.AccountDetailsPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.LoginPage;
import com.automationexercise.pages.SignupPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserLoginTest extends BaseTest {

    @Test
    public void testUserLoginWithCorrectCredentials() {

        // Step 1 & 2: Launch browser + navigate (BaseTest does this in setUp)

        // Step 3: Verify that home page is visible successfully
        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Automation Exercise"), "Home page not loaded (title mismatch)");

        // Step 4: Click on 'Signup / Login' button
        HomePage homePage = new HomePage(driver);
        closePopups();
        homePage.clickSignupLoginButton();

        // ===== Option A begins: create a fresh account first =====
        // Create a unique user
        String email = "loginuser" + System.currentTimeMillis() + "@example.com";
        String password = "Password123";

        // Signup (New User Signup)
        SignupPage signupPage = new SignupPage(driver);
        Assert.assertTrue(signupPage.isNewUserSignupVisible(), "Signup page not visible (New User Signup missing)");

        signupPage.enterName("Login User");
        signupPage.enterEmail(email);
        closePopups();
        signupPage.clickSignupButton();

        // Fill account details and create account
        AccountDetailsPage accountDetailsPage = new AccountDetailsPage(driver);
        Assert.assertTrue(accountDetailsPage.isEnterAccountInfoVisible(), "ENTER ACCOUNT INFORMATION not visible");

        accountDetailsPage.selectTitle("Mr");
        accountDetailsPage.enterPassword(password);
        accountDetailsPage.selectDateOfBirth("1", "1", "1990");
        accountDetailsPage.selectNewsletterCheckbox();
        accountDetailsPage.selectOffersCheckbox();

        accountDetailsPage.enterFirstName("Login");
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

        // Verify logged in right after account creation
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoggedInAsVisible(), "Not logged in after creating account");

        // Logout so we can test login properly
        closePopups();
        loginPage.clickLogoutButton();

        // ===== Now do the actual login test steps =====

        // Step 5: Verify 'Login to your account' is visible
        Assert.assertTrue(loginPage.isLoginToYourAccountVisible(), "Login to your account not visible");

        // Step 6: Enter correct email address and password (the ones we just created)
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);

        // Step 7: Click 'login' button
        closePopups();
        loginPage.clickLoginButton();

        // Step 8: Verify that 'Logged in as username' is visible
        Assert.assertTrue(loginPage.isLoggedInAsVisible(), "Login failed - 'Logged in as' not visible");

        // Step 9: Click 'Delete Account' button
        closePopups();
        loginPage.clickDeleteAccountButton();

        // Step 10: Verify that 'ACCOUNT DELETED!' is visible
        Assert.assertTrue(loginPage.isAccountDeletedMessageVisible(), "Account deleted message not visible");
    }
}