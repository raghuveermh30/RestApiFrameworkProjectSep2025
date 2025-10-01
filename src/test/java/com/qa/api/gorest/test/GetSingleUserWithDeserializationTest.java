package com.qa.api.gorest.test;

import com.qa.api.base.BaseTest;
import com.qa.api.configmanager.ConfigManager;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.JsonUtils;
import com.qa.api.utils.StringUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

public class GetSingleUserWithDeserializationTest extends BaseTest {

    private String token;

    @BeforeClass
    public void setupToken() {
        token = "68734ee53f25a08b6fc8baabff02bc5c364db7763d871a76874e36845fb586e9";
        ConfigManager.setProperty("bearerToken", token);
    }

    @Test
    public void createUserTestWithDeserialize() {

        User user = new User(null, "Naveen", StringUtils.getRandomEmailId(), "male", "active");
        Response response = restClient.postApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT, null, null, user, AuthType.BEARER_TOKEN,
                ContentType.JSON);
        Assert.assertNotNull(response.jsonPath().getString("name"));
        Assert.assertNotNull(response.jsonPath().getString("id"));

        String userId = response.jsonPath().getString("id");
        System.out.println("User Id is : " + userId);

        //2. Get the User -- GET
        Response getResponse = restClient.getApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT + "/" + userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getResponse.jsonPath().getString("name"), "Naveen");
        Assert.assertTrue(getResponse.statusLine().contains("OK"));
        Assert.assertNotNull(getResponse.jsonPath().getString("id"));

        System.out.println("*********");

        User getUserResponse = JsonUtils.deserialize(getResponse, User.class);
        System.out.println("Name : " + getUserResponse.getName());
        System.out.println("Email : " + getUserResponse.getEmail());
        System.out.println("Status : " + getUserResponse.getStatus());
        System.out.println("Gender : " + getUserResponse.getGender());

        Assert.assertEquals(getUserResponse.getName(), "Naveen");
        Assert.assertEquals(getUserResponse.getGender(), "male");
        Assert.assertEquals(getUserResponse.getStatus(), "active");

    }

    @Test
    public void getAllUsersTest() {
        User user = new User(null, "Naveen", StringUtils.getRandomEmailId(), "male", "active");

        // 1. Create User Test
        Response createUserResponse = restClient.postApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT, null, null, user, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(createUserResponse.jsonPath().getString("name"), "Naveen");

        //2. Get All Users Test
        Response getUserResponse = restClient.getApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        User[] users = JsonUtils.deserialize(getUserResponse, User[].class);
        System.out.println(Arrays.toString(users));

        for (User user1 : users) {
            System.out.println(user1.getName());
            System.out.println(user1.getGender());
            System.out.println(user1.getStatus());
            System.out.println(user1.getId());
        }
    }
}
