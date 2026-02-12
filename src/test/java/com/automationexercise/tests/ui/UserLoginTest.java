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

        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Automation Exercise"), "Home page not loaded (title mismatch)");

        HomePage homePage = new HomePage(driver);
        closePopups();
        homePage.clickSignupLoginButton();

        String email = "loginuser" + System.currentTimeMillis() + "@example.com";
        String password = "Password123";

        SignupPage signupPage = new SignupPage(driver);
        Assert.assertTrue(signupPage.isNewUserSignupVisible(), "Signup page not visible (New User Signup missing)");

        signupPage.enterName("Login User");
        signupPage.enterEmail(email);
        closePopups();
        signupPage.clickSignupButton();

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

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoggedInAsVisible(), "Not logged in after creating account");

        closePopups();
        loginPage.clickLogoutButton();

        Assert.assertTrue(loginPage.isLoginToYourAccountVisible(), "Login to your account not visible");

        loginPage.enterEmail(email);
        loginPage.enterPassword(password);

        closePopups();
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.isLoggedInAsVisible(), "Login failed - 'Logged in as' not visible");

        closePopups();
        loginPage.clickDeleteAccountButton();

        Assert.assertTrue(loginPage.isAccountDeletedMessageVisible(), "Account deleted message not visible");
    }
}
