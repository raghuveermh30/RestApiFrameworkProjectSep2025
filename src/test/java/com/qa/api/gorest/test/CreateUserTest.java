package com.qa.api.gorest.test;

import com.qa.api.base.BaseTest;
import com.qa.api.configmanager.ConfigManager;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

public class CreateUserTest extends BaseTest {

    private String token;

    @BeforeClass
    public void setupToken() {
        token = "5e4744d09692112453fbded05b74e62e76d246fc0d3fffb1642d2e0f8fb31107";
        ConfigManager.setProperty("bearerToken", token);
    }

    @Test
    public void createUserTest() {

        User user = new User(null,"Naveen", StringUtils.getRandomEmailId(), "male", "active");
        Response response = restClient.postApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT, null, null, user, AuthType.BEARER_TOKEN,
                ContentType.JSON);
        Assert.assertNotNull(response.jsonPath().getString("name"));
        Assert.assertNotNull(response.jsonPath().getString("id"));
    }

    @Test(enabled = false)
    public void createUserTestWithJsonString() {

        String userJson = "{\n" +
                "\n" +
                "    \"name\": \"API Automation133\",\n" +
                "    \"email\": \"api12211@automa133tion21.com\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"status\": \"active\"\n" +
                "}";
        Response response = restClient.postApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT, null, null, userJson, AuthType.BEARER_TOKEN,
                ContentType.JSON);
        Assert.assertEquals(response.jsonPath().getString("name"), "API Automation133");
        Assert.assertNotNull(response.jsonPath().getString("id"));
    }

    @Test(enabled = false)
    public void createUserTestWithJsonFile() {

        File file = new File("src/test/resources/jsons/user.json");
        Response response = restClient.postApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT, null, null, file, AuthType.BEARER_TOKEN,
                ContentType.JSON);
        Assert.assertNotNull(response.jsonPath().getString("name"));
        Assert.assertNotNull(response.jsonPath().getString("id"));
    }

}
