package com.qa.api.client;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.configmanager.ConfigManager;
import com.qa.api.constants.AuthType;
import com.qa.api.exception.APIException;
import io.qameta.allure.Step;
import io.restassured.RestAssured;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.expect;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.File;
import java.util.Base64;
import java.util.Map;

public class RestClient {

    //Define All Response Specs
    private final ResponseSpecification responseSpec200 = expect().statusCode(200);
    private final ResponseSpecification responseSpec201 = expect().statusCode(201);
    private final ResponseSpecification responseSpec400 = expect().statusCode(400);
    private final ResponseSpecification responseSpec200Or201 = expect().statusCode(anyOf(equalTo(200), equalTo(201)));
    private final ResponseSpecification responseSpec200Or204 = expect().statusCode(anyOf(equalTo(200), equalTo(404)));
    private final ResponseSpecification responseSpec404 = expect().statusCode(404);
    private final ResponseSpecification responseSpec500 = expect().statusCode(500);
    private final ResponseSpecification responseSpec401 = expect().statusCode(401);
    private final ResponseSpecification responseSpec422 = expect().statusCode(422);
    private final ResponseSpecification responseSpec204 = expect().statusCode(204);

    private RequestSpecification setupRequest(String baseUrl, AuthType authType, ContentType contentType) {

        RequestSpecification requestSpecification = RestAssured.given().baseUri(baseUrl).log().all()
                .contentType(contentType)
                .accept(contentType);

        switch (authType) {
            case BEARER_TOKEN:
                requestSpecification.header("Authorization", "Bearer " + ConfigManager.getProperty("bearerToken"));
                break;
            case OAUTH2:
                requestSpecification.header("Authorization", "Bearer " + "Some Oauth2 Token");
                break;
            case BASIC_AUTH:
                requestSpecification.header("Authorization", "Basic " + generateBasicAuthToken());
                break;
            case API_KEY:
                requestSpecification.header("x-api-key", "API Key");
                break;
            case NO_AUTH:
                System.out.println("Auth is not required...");
                break;
            default:
                System.out.println("This auth is not supported...Please pass the right AuthType..");
                throw new APIException("===Invalid Auth===");
        }
        return requestSpecification;
    }

    /*
     * This method is used to generate the Base 64 Encoded
     */
    private String generateBasicAuthToken() {
        String credentials = ConfigManager.getProperty("basicUserName") + ":" + ConfigManager.getProperty("basicPassword");
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }


    private void applyParams(RequestSpecification requestSpecification, Map<String, String> queryParams, Map<String, String> pathParams) {
        if (queryParams != null) {
            requestSpecification.queryParams(queryParams);
            ChainTestListener.log("Query Params : "+queryParams);
        }
        if (pathParams != null) {
            requestSpecification.pathParams(pathParams);
            ChainTestListener.log("Path Params : "+pathParams);
        }

    }

    //CRUD Operations

    /*
     * This method is used to call the GET APIs
     * @param baseUrl
     * @param endPoint
     * @param queryMap
     * @param pathMap
     * @param authType
     * @param contentType
     * @return it returns the get api response
     */
    @Step("Calling Get Api with base url : {0} , endpoint {1}, QueryParam {2}, PathParam {3}, AuthType {4} and Content Type {5}")
    public Response getApiCall(String baseUrl, String endPoint, Map<String, String> queryParams, Map<String, String> pathParams,
                               AuthType authType, ContentType contentType) {
        ChainTestListener.log("API Base URL  : "+baseUrl);
        ChainTestListener.log("Auth Type is   : "+authType.toString());
        RequestSpecification request = setupRequest(baseUrl, authType, contentType);
        applyParams(request, queryParams, pathParams);
        ChainTestListener.log("Request  : "+request.toString());
        Response response = request.log().all().get(endPoint).then().log().all().spec(responseSpec200Or204).extract().response();
        response.prettyPrint();
        ChainTestListener.log("Response  : "+response.prettyPrint());

        return response;
    }

    /*
     * This method is used to call the POST APIs
     * @param baseUrl
     * @param endPoint
     * @param queryMap
     * @param pathMap
     * @param authType
     * @param contentType
     * @param body
     * @return it returns the post api response
     */
    public <T> Response postApiCall(String baseUrl, String endPoint, Map<String, String> queryParams, Map<String, String> pathParams,
                                    T body, AuthType authType, ContentType contentType) {

        RequestSpecification request = setupRequest(baseUrl, authType, contentType);
        applyParams(request, queryParams, pathParams);

        Response response = request.body(body).post(endPoint).then().log().all().spec(responseSpec200Or201).extract().response();
        response.prettyPrint();
        return response;
    }

    /*
     * <T> body will not work for File object
     * This method is used to call the POST APIs
     * @param baseUrl
     * @param endPoint
     * @param queryMap
     * @param pathMap
     * @param authType
     * @param contentType
     * @param body
     * @return it returns the post api response
     */
    public Response postApiCall(String baseUrl, String endPoint, Map<String, String> queryParams, Map<String, String> pathParams,
                                File file, AuthType authType, ContentType contentType) {

        RequestSpecification request = setupRequest(baseUrl, authType, contentType);
        applyParams(request, queryParams, pathParams);

        Response response = request.body(file).post(endPoint).then().log().all().spec(responseSpec200Or201).extract().response();
        response.prettyPrint();
        return response;
    }

    /*
     * This method is used to call the Put APIs
     * @param baseUrl
     * @param endPoint
     * @param queryMap
     * @param pathMap
     * @param authType
     * @param contentType
     * @param body
     * @return it returns the Put api response
     */
    public <T> Response putApiCall(String baseUrl, String endPoint, Map<String, String> queryParams, Map<String, String> pathParams,
                                   T body, AuthType authType, ContentType contentType) {

        RequestSpecification request = setupRequest(baseUrl, authType, contentType);
        applyParams(request, queryParams, pathParams);

        Response response = request.body(body).put(endPoint).then().log().all().spec(responseSpec200).extract().response();
        response.prettyPrint();
        return response;
    }

    /*
     * This method is used to call the PATCH APIs
     * @param baseUrl
     * @param endPoint
     * @param queryMap
     * @param pathMap
     * @param authType
     * @param contentType
     * @param body
     * @return it returns the PATCH api response
     */
    public <T> Response patchApiCall(String baseUrl, String endPoint, Map<String, String> queryParams, Map<String, String> pathParams,
                                     T body, AuthType authType, ContentType contentType) {

        RequestSpecification request = setupRequest(baseUrl, authType, contentType);
        applyParams(request, queryParams, pathParams);

        Response response = request.body(body).patch(endPoint).then().log().all().spec(responseSpec200).extract().response();
        response.prettyPrint();
        return response;
    }

    /*
     * This method is used to call the DELETE APIs
     * @param baseUrl
     * @param endPoint
     * @param queryMap
     * @param pathMap
     * @param authType
     * @param contentType
     * @return it returns the get DELETE response
     */
    public Response deleteApiCall(String baseUrl, String endPoint, Map<String, String> queryParams, Map<String, String> pathParams,
                                  AuthType authType, ContentType contentType) {

        RequestSpecification request = setupRequest(baseUrl, authType, contentType);
        applyParams(request, queryParams, pathParams);

        Response response = request.log().all().delete(endPoint).then().log().all().spec(responseSpec204).extract().response();
        response.prettyPrint();
        return response;
    }

    /*
     * This method is used to call the POST APIs for OAuth 2.0
     * @param baseUrl
     * @param endPoint
     * @param queryMap
     * @param pathMap
     * @param authType
     * @param contentType
     * @return it returns the POST APIs for OAuth 2.0
     */
    public Response postApiCall(String baseUrl, String endPoint, String clientId, String clientSecret, String grantType,
                                ContentType contentType) {
        Response response = RestAssured.given().log().all()
                .contentType(contentType)
                .formParam("grant_type", grantType)
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .post(baseUrl + endPoint).then().log().all().extract().response();
        response.prettyPrint();
        return response;
    }


}
