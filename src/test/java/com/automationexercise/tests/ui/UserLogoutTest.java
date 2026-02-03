package com.automationexercise.tests.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.AccountCreatedPage;
import com.automationexercise.pages.AccountDetailsPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.LoginPage;
import com.automationexercise.pages.SignupPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserLogoutTest extends BaseTest {

    @Test(description = "Test Case 4: Logout User (with simple user creation first)")
    public void testCase4_logoutUser() {

        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Automation Exercise"), "Home page not loaded (title mismatch)");

        HomePage homePage = new HomePage(driver);
        closePopups();
        homePage.clickSignupLoginButton();

        String email = "logoutuser" + System.currentTimeMillis() + "@example.com";
        String password = "Password123";

        SignupPage signupPage = new SignupPage(driver);
        Assert.assertTrue(signupPage.isNewUserSignupVisible(), "Signup page not visible (New User Signup missing)");

        signupPage.enterName("Logout User");
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

        accountDetailsPage.enterFirstName("Logout");
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

        Assert.assertTrue(loginPage.isLoginToYourAccountVisible(), "User not navigated to login page after logout");
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "URL does not contain /login after logout");
    }
}