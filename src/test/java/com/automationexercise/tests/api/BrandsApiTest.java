package com.automationexercise.tests.api;

import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BrandsApiTest extends BaseApiTest {

    @Test(description = "API 3: Get All Brands List")
    @Description("Verify that the API returns a list of all brands.")
    public void api3_getAllBrandsList() {
        given()
            .filter(new AllureRestAssured())
        .when()
            .get("/api/brandsList")
        .then()
            .statusCode(200)
            .body("responseCode", equalTo(200))
            .body("brands", not(empty()));
    }

    @Test(description = "API 4: PUT To All Brands List (Negative)")
    @Description("Verify that PUT method is not supported for brands list.")
    public void api4_putToAllBrandsList() {
        given()
            .filter(new AllureRestAssured())
        .when()
            .put("/api/brandsList")
        .then()
            .statusCode(200)
            .body("responseCode", equalTo(405))
            .body("message", equalTo("This request method is not supported."));
    }
}