package com.automationexercise.tests.ui;

import com.automationexercise.base.BaseTest;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.ProductsCartActions;
import com.automationexercise.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddProductsToCartTest extends BaseTest {

    @Test(description = "Test Case 12: Add Products in Cart")
    public void testCase12_addProductsInCart() {
        Assert.assertTrue(driver.getTitle().contains("Automation Exercise"),
                "Home page not visible (title mismatch)");

        HomePage homePage = new HomePage(driver);
        closePopups();
        homePage.clickProductsButton();

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isOnAllProductsPage(),
                "Not navigated to ALL PRODUCTS page");

        ProductsCartActions productsActions = new ProductsCartActions(driver);
        closePopups();
        productsActions.hoverAndAddToCart(1);

        closePopups();
        productsActions.clickContinueShoppingSafely();

        closePopups();
        productsActions.hoverAndAddToCart(2);

        closePopups();
        productsActions.clickViewCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.waitForCartPage();

        Assert.assertTrue(cartPage.getCartRowCount() >= 2,
                "Cart does not contain at least 2 products. Rows: " + cartPage.getCartRowCount());

        Assert.assertTrue(cartPage.isRowVisible(1), "First cart row is not visible");
        Assert.assertTrue(cartPage.isRowVisible(2), "Second cart row is not visible");

        for (int row = 1; row <= 2; row++) {
            int price = cartPage.getRowPrice(row);
            int qty = cartPage.getRowQuantity(row);
            int total = cartPage.getRowTotal(row);

            Assert.assertTrue(price > 0, "Row " + row + " price is not valid: " + price);
            Assert.assertTrue(qty > 0, "Row " + row + " quantity is not valid: " + qty);
            Assert.assertTrue(total > 0, "Row " + row + " total is not valid: " + total);

            Assert.assertEquals(total, price * qty,
                    "Row " + row + " total mismatch. Expected: " + (price * qty) + " but got: " + total);
        }
    }
}