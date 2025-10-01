package com.qa.api.amedusapi.test;

import com.qa.api.base.BaseTest;
import com.qa.api.configmanager.ConfigManager;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidator;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.groovy.util.Maps;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

public class AmadeusApiTest extends BaseTest {

    String accessToken;

    @BeforeMethod
    public void getOauth2Token() {
        Response response = restClient.postApiCall(BASE_URL_OAUTH2_AMADEUS, AMADEUS_OAUTH2_TOKEN_ENDPOINT, ConfigManager.getProperty("clientid")
                , ConfigManager.getProperty("clientsecret"), ConfigManager.getProperty("grant_type"), ContentType.JSON);

        accessToken = response.jsonPath().getString("access_token");
        System.out.println("Access Token is + " + accessToken);
        ConfigManager.setProperty("bearerToken", accessToken);
    }

    //https://test.api.amadeus.com/v1/shopping/flight-destinations?origin=PAR&maxPrice=200

    @Test(enabled = false)
    public void getFlightDetails() {
        Map<String, String> queryParams = Maps.of("origin", "PAR", "maxPrice", "200");
        Response response = restClient.getApiCall(BASE_URL_OAUTH2_AMADEUS, AMADEUS_GET_ENDPOINT, queryParams, null, AuthType.BEARER_TOKEN, ContentType.ANY);

    }

}
