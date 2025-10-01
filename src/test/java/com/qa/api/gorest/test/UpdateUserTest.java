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

public class UpdateUserTest extends BaseTest {

    private String token;

    @BeforeClass
    public void setupToken() {
        token = "5e4744d09692112453fbded05b74e62e76d246fc0d3fffb1642d2e0f8fb31107";
        ConfigManager.setProperty("bearerToken", token);
    }

    @Test
    public void updateUserTest() {

        //1. Create the User -- POST
        // User user = new User("Naveen", StringUtils.getRandomEmailId(), "male", "active");
        User user = User.builder().name("Raghu").email(StringUtils.getRandomEmailId()).gender("male").status("active").build();
        Response response = restClient.postApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT, null, null, user, AuthType.BEARER_TOKEN,
                ContentType.JSON);
        Assert.assertEquals(response.jsonPath().getString("name"), "Raghu");
        Assert.assertNotNull(response.jsonPath().getString("id"));
        String userId = response.jsonPath().getString("id");
        System.out.println("User Id : " + userId);

        //2. Get the User -- GET
        Response getResponse = restClient.getApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT + "/" + userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getResponse.jsonPath().getString("name"), "Raghu");
        Assert.assertTrue(getResponse.statusLine().contains("OK"));
        Assert.assertNotNull(getResponse.jsonPath().getString("id"));

        //3. Update the User - PUT
        user.setName("Shruthi");
        user.setGender("female");
        Response putResponse = restClient.putApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT +"/" + userId, null, null, user, AuthType.BEARER_TOKEN,
                ContentType.JSON);
        Assert.assertTrue(putResponse.statusLine().contains("OK"));
        Assert.assertNotNull(putResponse.jsonPath().getString("id"));
        Assert.assertEquals(putResponse.jsonPath().getString("name"), "Shruthi");
        Assert.assertEquals(putResponse.jsonPath().getString("gender"), "female");

        //4. Get the User -- GET
        getResponse = restClient.getApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT + "/" + userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getResponse.jsonPath().getString("name"), "Shruthi");
        Assert.assertNotNull(getResponse.jsonPath().getString("id"));
        Assert.assertTrue(getResponse.statusLine().contains("OK"));
        Assert.assertEquals(getResponse.jsonPath().getString("gender"), "female");

    }

}
