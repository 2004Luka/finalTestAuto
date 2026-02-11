package com.automationexercise.tests.api;

import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductsApiTest extends BaseApiTest {

    @Test(description = "API 1: Get All Products List")
    @Description("Verify that the API returns a list of all products with a 200 OK status.")
    public void api1_getAllProductsList() {
        given()
            .filter(new AllureRestAssured())
        .when()
            .get("/api/productsList")
        .then()
            .statusCode(200)
            .contentType(ContentType.HTML) 
            .body("responseCode", equalTo(200))
            .body("products", not(empty()));
    }

    @Test(description = "API 2: POST To All Products List (Negative)")
    @Description("Verify that a POST request to products list returns 405 Method Not Allowed.")
    public void api2_postToAllProductsList() {
        given()
            .filter(new AllureRestAssured())
        .when()
            .post("/api/productsList")
        .then()
            .statusCode(200)
            .body("responseCode", equalTo(405))
            .body("message", equalTo("This request method is not supported."));
    }

    @Test(description = "API 5: POST To Search Product")
    @Description("Verify searching for a product returns the correct results.")
    public void api5_postToSearchProduct() {
        given()
            .filter(new AllureRestAssured())
            .contentType(ContentType.MULTIPART)
            .formParam("search_product", "tshirt")
        .when()
            .post("/api/searchProduct")
        .then()
            .statusCode(200)
            .body("responseCode", equalTo(200))
            .body("products", not(empty()));
    }

    @Test(description = "API 6: POST To Search Product without parameter (Negative)")
    @Description("Verify that searching without a parameter returns a 400 Bad Request.")
    public void api6_postToSearchProductWithoutParameter() {
        given()
            .filter(new AllureRestAssured())
            .contentType(ContentType.MULTIPART)
        .when()
            .post("/api/searchProduct")
        .then()
            .statusCode(200)
            .body("responseCode", equalTo(400))
            .body("message", equalTo("Bad request, search_product parameter is missing in POST request."));
    }
}