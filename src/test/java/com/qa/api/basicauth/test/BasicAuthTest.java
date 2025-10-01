package com.qa.api.basicauth.test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicAuthTest extends BaseTest {

    @Test
    public void basicAuthTest() {
        Response getResponse = restClient.getApiCall(BASE_URL_HERO_BASIC, "basic_auth", null, null, AuthType.BASIC_AUTH, ContentType.ANY);
        Assert.assertEquals(getResponse.getStatusCode(), 200);
        getResponse.prettyPrint();
        Assert.assertTrue(getResponse.asString().contains("Congratulations! You must have the proper credentials."));
    }
}
