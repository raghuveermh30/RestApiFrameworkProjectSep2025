package com.qa.api.gorest.test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteUserTest extends BaseTest {


    @Test
    public void deleteUserTest() {

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

        //3. Delete the User - DELETE
        Response deleteResponse = restClient.deleteApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT + "/" + userId, null, null, AuthType.BEARER_TOKEN,
                ContentType.JSON);
        Assert.assertEquals(deleteResponse.getStatusCode(), 204);

        //4. Get the User -- GET
        getResponse = restClient.getApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT + "/" + userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(getResponse.getStatusCode(), 404);
    }

}
