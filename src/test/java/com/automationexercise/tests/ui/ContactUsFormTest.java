package com.automationexercise.tests.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.ContactUsPage;
import com.automationexercise.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;

public class ContactUsFormTest extends BaseTest {

    @Test(description = "Test Case 6: Contact Us Form")
    public void testCase6_contactUsForm() throws Exception {

        Assert.assertTrue(driver.getTitle().contains("Automation Exercise"), "Home page not visible (title mismatch)");

        HomePage homePage = new HomePage(driver);
        closePopups();
        homePage.clickContactUsButton();

        ContactUsPage contactUsPage = new ContactUsPage(driver);
        contactUsPage.waitUntilContactUsPageLoaded();

        Assert.assertTrue(contactUsPage.isGetInTouchVisible(), "'GET IN TOUCH' is not visible");

        contactUsPage.enterName("Test User");
        contactUsPage.enterEmail("testuser" + System.currentTimeMillis() + "@example.com");
        contactUsPage.enterSubject("Automation Contact");
        contactUsPage.enterMessage("Hello! This is an automated message from Selenium TestNG.");

        Path upload = Path.of("src/test/resources/upload.txt");

        if (!Files.exists(upload)) {
            Files.createDirectories(upload.getParent());
            Files.writeString(upload, "This is a test upload file for AutomationExercise Contact Us form.");
        }

        contactUsPage.uploadFile(upload);

        closePopups();
        contactUsPage.clickSubmit();

        contactUsPage.acceptAlertIfPresent();

        Assert.assertTrue(
                contactUsPage.isSuccessMessageVisible(),
                "Success message not visible after submitting Contact Us form"
        );

        contactUsPage.clickHomeButton();
        Assert.assertTrue(driver.getTitle().contains("Automation Exercise"), "Did not land on home page after clicking Home");
    }
}