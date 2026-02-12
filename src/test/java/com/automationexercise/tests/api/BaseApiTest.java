package com.automationexercise.tests.api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseApiTest {

    protected Properties properties;

    @BeforeClass
    public void setup() {
        loadProperties();
        RestAssured.baseURI = properties.getProperty("api.base.url", "https://automationexercise.com");
    }

    private void loadProperties() {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not load config.properties");
        }
    }
}
