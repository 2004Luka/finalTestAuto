package com.automationexercise.tests.api;

import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class ProductsApiTest extends BaseApiTest {

    @Test(description = "API 1: Get All Products List")
    public void api1_getAllProductsList() {
        Response response = given().filter(new AllureRestAssured()).when().get("/api/productsList");
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Assert.assertEquals(jsonPath.getInt("responseCode"), 200);
    }

    @Test(description = "API 2: POST To All Products List (Negative)")
    public void api2_postToAllProductsList() {
        Response response = given().filter(new AllureRestAssured()).when().post("/api/productsList");
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Assert.assertEquals(jsonPath.getInt("responseCode"), 405);
        Assert.assertEquals(jsonPath.getString("message"), "This request method is not supported.");
    }

    @Test(description = "API 5: POST To Search Product")
    public void api5_postToSearchProduct() {
        Response response = given()
                .filter(new AllureRestAssured())
                .multiPart("search_product", "tshirt")
                .when()
                .post("/api/searchProduct");

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Assert.assertEquals(jsonPath.getInt("responseCode"), 200);
    }

    @Test(description = "API 6: POST To Search Product without parameter (Negative)")
    public void api6_postToSearchProductWithoutParameter() {
        Response response = given()
                .filter(new AllureRestAssured())
                .when()
                .post("/api/searchProduct");

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Assert.assertEquals(jsonPath.getInt("responseCode"), 400);
        Assert.assertEquals(jsonPath.getString("message"), "Bad request, search_product parameter is missing in POST request.");
    }
}