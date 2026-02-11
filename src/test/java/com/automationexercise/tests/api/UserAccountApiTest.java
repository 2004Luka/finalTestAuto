package com.automationexercise.tests.api;

import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserAccountApiTest extends BaseApiTest {

    private String email;
    private String password = "Password123";

    @Test(description = "API 11: POST To Create/Register User Account")
    @Description("Verify that a user can be registered via API.")
    public void api11_createRegisterUserAccount() {
        email = "apitest" + System.currentTimeMillis() + "@example.com";

        given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.MULTIPART)
                .formParam("name", "APITestUser")
                .formParam("email", email)
                .formParam("password", password)
                .formParam("title", "Mr")
                .formParam("birth_date", "10")
                .formParam("birth_month", "05")
                .formParam("birth_year", "1990")
                .formParam("firstname", "API")
                .formParam("lastname", "User")
                .formParam("company", "TestCorp")
                .formParam("address1", "123 API St")
                .formParam("country", "United States")
                .formParam("zipcode", "90210")
                .formParam("state", "California")
                .formParam("city", "Los Angeles")
                .formParam("mobile_number", "1231231234")
                .when()
                .post("/api/createAccount")
                .then()
                .statusCode(200)
                .body("responseCode", equalTo(201))
                .body("message", equalTo("User created!"));
    }

    @Test(description = "API 7: POST To Verify Login with valid details", dependsOnMethods = "api11_createRegisterUserAccount")
    @Description("Verify that the user can log in with the created credentials.")
    public void api7_verifyLoginWithValidDetails() {
        given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.MULTIPART)
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .post("/api/verifyLogin")
                .then()
                .statusCode(200)
                .body("responseCode", equalTo(200))
                .body("message", equalTo("User exists!"));
    }

    @Test(description = "API 8: POST To Verify Login without email (Negative)")
    @Description("Verify that login fails when the email parameter is missing.")
    public void api8_verifyLoginWithoutEmail() {
        given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.MULTIPART)
                .formParam("password", password)
                .when()
                .post("/api/verifyLogin")
                .then()
                .statusCode(200)
                .body("responseCode", equalTo(400))
                .body("message", equalTo("Bad request, email or password parameter is missing in POST request."));
    }

    @Test(description = "API 12: DELETE METHOD To Delete User Account", dependsOnMethods = "api7_verifyLoginWithValidDetails")
    @Description("Verify that the user account can be deleted.")
    public void api12_deleteUserAccount() {
        given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.MULTIPART)
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .delete("/api/deleteAccount")
                .then()
                .statusCode(200)
                .body("responseCode", equalTo(200))
                .body("message", equalTo("Account deleted!"));
    }
}