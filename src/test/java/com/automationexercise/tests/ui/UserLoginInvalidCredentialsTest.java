package com.automationexercise.tests.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserLoginInvalidCredentialsTest extends BaseTest {

    @Test(description = "Test Case 3: Login User with incorrect email and password")
    public void testCase3_loginUserWithIncorrectEmailAndPassword() {
        driver.get("http://automationexercise.com");

        Assert.assertTrue(driver.getCurrentUrl().contains("automationexercise.com"),
                "Home page is not visible / URL is not correct.");

        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginToYourAccountVisible(),
                "'Login to your account' is not visible.");

        loginPage.enterEmail("invalid_" + System.currentTimeMillis() + "@example.com");
        loginPage.enterPassword("wrong-password-123");

        loginPage.clickLoginButtonExpectingIncorrectCredentialsError();

        Assert.assertTrue(loginPage.isIncorrectCredentialsErrorVisible(),
                "Error message 'Your email or password is incorrect!' is not visible.");
    }
}