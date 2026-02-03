package com.automationexercise.tests.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.ProductSearchComponent;
import com.automationexercise.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchProductTest extends BaseTest {

    @Test(description = "Test Case 9: Search Product")
    public void testCase9_searchProduct() {
        Assert.assertTrue(driver.getTitle().contains("Automation Exercise"),
                "Home page not visible (title mismatch)");

        HomePage homePage = new HomePage(driver);
        closePopups();
        homePage.clickProductsButton();

        System.out.println("After clicking Products -> URL: " + driver.getCurrentUrl());
        System.out.println("After clicking Products -> Title: " + driver.getTitle());

        if (driver.getCurrentUrl().contains("google_vignette")) {
            String baseUrl = properties.getProperty("base.url", "https://automationexercise.com");
            driver.get(baseUrl + "/products");
        }

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isOnAllProductsPage(),
                "User is not navigated to ALL PRODUCTS page");

        String term = "Dress";
        ProductSearchComponent search = new ProductSearchComponent(driver);
        closePopups();
        search.searchFor(term);

        Assert.assertTrue(search.isSearchedProductsHeaderVisible(),
                "'SEARCHED PRODUCTS' is not visible");

        Assert.assertTrue(search.areAnySearchResultsVisible(),
                "No searched products are visible");

        Assert.assertTrue(search.doesAnyVisibleResultNameContain(term),
                "No visible searched product name contains the term: " + term);
    }
}