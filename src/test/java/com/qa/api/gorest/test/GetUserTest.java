package com.qa.api.gorest.test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Epic Get users Api Tests")
@Story("SUP-12345")
public class GetUserTest extends BaseTest {

    @Description( "Getting all the Users")
    @Owner("Raghuveer")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void getAllUsersTest() {
        Response response = restClient.getApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertTrue(response.statusLine().contains("OK"));

    }

    @Test
    public void getAllUsersWithQueryParam() {

        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("name", "API Automation");
        queryParam.put("status", "active");

        Response response = restClient.getApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT, queryParam, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertTrue(response.statusLine().contains("OK"));
    }

    @Test
    public void getSingleUserTest() {

        String userId = "8150169";
        Response response = restClient.getApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT + "/" + userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertTrue(response.statusLine().contains("OK"));
        Assert.assertEquals(response.jsonPath().getString("id"), userId);
    }


}
