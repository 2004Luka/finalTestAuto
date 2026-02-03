package com.automationexercise.tests.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.ProductDetailsPage;
import com.automationexercise.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VerifyAllProductsAndProductDetailTest extends BaseTest {

    @Test(description = "Test Case 8: Verify All Products and product detail page")
    public void testCase8_verifyAllProductsAndProductDetails() {

        Assert.assertTrue(driver.getTitle().contains("Automation Exercise"),
                "Home page not visible (title mismatch)");

        HomePage homePage = new HomePage(driver);
        closePopups();
        homePage.clickProductsButton();

        System.out.println("After clicking Products -> URL: " + driver.getCurrentUrl());
        System.out.println("After clicking Products -> Title: " + driver.getTitle());

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isOnAllProductsPage(),
                "User is not navigated to ALL PRODUCTS page");

        Assert.assertTrue(productsPage.isProductsListVisible(),
                "Products list is not visible on ALL PRODUCTS page");

        closePopups();
        productsPage.openFirstProductDetailsByHref();

        ProductDetailsPage detailsPage = new ProductDetailsPage(driver);
        Assert.assertTrue(detailsPage.isOnProductDetailsPage(),
                "User is not landed on product detail page");

    }
}