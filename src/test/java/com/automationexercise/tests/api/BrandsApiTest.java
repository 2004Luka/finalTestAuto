package com.automationexercise.tests.api;

import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class BrandsApiTest extends BaseApiTest {

    @Test(description = "API 3: Get All Brands List")
    public void api3_getAllBrandsList() {
        Response response = given().filter(new AllureRestAssured()).when().get("/api/brandsList");
        JsonPath jsonPath = new JsonPath(response.getBody().asString());

        Assert.assertEquals(jsonPath.getInt("responseCode"), 200);
        Assert.assertNotNull(jsonPath.get("brands"));
    }

    @Test(description = "API 4: PUT To All Brands List (Negative)")
    public void api4_putToAllBrandsList() {
        Response response = given().filter(new AllureRestAssured()).when().put("/api/brandsList");
        JsonPath jsonPath = new JsonPath(response.getBody().asString());

        Assert.assertEquals(jsonPath.getInt("responseCode"), 405);
        Assert.assertEquals(jsonPath.getString("message"), "This request method is not supported.");
    }
}