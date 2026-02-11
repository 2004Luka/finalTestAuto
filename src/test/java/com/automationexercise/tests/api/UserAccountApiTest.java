package com.automationexercise.tests.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class UserAccountApiTest extends BaseApiTest {

    private String email;
    private String password = "Password123";

    @Test(description = "API 11: POST To Create/Register User Account")
    public void api11_createRegisterUserAccount() {
        email = "apitest" + System.currentTimeMillis() + "@example.com";
        Response response = given()
                .filter(new AllureRestAssured())
                .multiPart("name", "APITestUser")
                .multiPart("email", email)
                .multiPart("password", password)
                .multiPart("title", "Mr")
                .multiPart("birth_date", "10")
                .multiPart("birth_month", "05")
                .multiPart("birth_year", "1990")
                .multiPart("firstname", "API")
                .multiPart("lastname", "User")
                .multiPart("company", "TestCorp")
                .multiPart("address1", "123 API St")
                .multiPart("country", "United States")
                .multiPart("zipcode", "90210")
                .multiPart("state", "California")
                .multiPart("city", "Los Angeles")
                .multiPart("mobile_number", "1231231234")
                .when()
                .post("/api/createAccount");

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Assert.assertEquals(jsonPath.getInt("responseCode"), 201);
    }

    @Test(description = "API 7: POST To Verify Login", dependsOnMethods = "api11_createRegisterUserAccount")
    public void api7_verifyLoginWithValidDetails() {
        Response response = given()
                .filter(new AllureRestAssured())
                .multiPart("email", email)
                .multiPart("password", password)
                .when()
                .post("/api/verifyLogin");

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Assert.assertEquals(jsonPath.getInt("responseCode"), 200);
        Assert.assertEquals(jsonPath.getString("message"), "User exists!");
    }

    @Test(description = "API 8: POST To Verify Login without email (Negative)")
    public void api8_verifyLoginWithoutEmail() {
        Response response = given()
                .filter(new AllureRestAssured())
                .multiPart("password", password)
                .when()
                .post("/api/verifyLogin");

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Assert.assertEquals(jsonPath.getInt("responseCode"), 400);
    }

    @Test(description = "API 12: DELETE METHOD To Delete User Account", dependsOnMethods = "api7_verifyLoginWithValidDetails")
    public void api12_deleteUserAccount() {
        Response response = given()
                .filter(new AllureRestAssured())
                .multiPart("email", email)
                .multiPart("password", password)
                .when()
                .delete("/api/deleteAccount");

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Assert.assertEquals(jsonPath.getInt("responseCode"), 200);
    }
}