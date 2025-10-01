package com.qa.api.reqres.test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ReqResTest extends BaseTest {

    @Test
    public void getUserTest() {
        Response getResponse = restClient.getApiCall(BASE_URL_REQ_RES, "/api/users?page=2", null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(getResponse.getStatusCode(), 200);
    }
}
