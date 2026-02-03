package com.automationexercise.tests.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.TestCasesPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VerifyTestCasesPageTest extends BaseTest {

    @Test(description = "Test Case 7: Verify Test Cases Page")
    public void testCase7_verifyTestCasesPage() {

        Assert.assertTrue(driver.getTitle().contains("Automation Exercise"),
                "Home page not visible (title mismatch)");

        HomePage homePage = new HomePage(driver);
        closePopups();
        homePage.clickTestCasesButton();

        TestCasesPage testCasesPage = new TestCasesPage(driver);
        Assert.assertTrue(testCasesPage.isOnTestCasesPage(),
                "User is not navigated to Test Cases page successfully");
    }
}