package com.qa.api.schema;

import com.qa.api.base.BaseTest;
import com.qa.api.configmanager.ConfigManager;
import com.qa.api.utils.SchemaValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GoRestUserApiSchemaTest extends BaseTest {

    private String token;

    @BeforeClass
    public void setupToken() {
        token = "5e4744d09692112453fbded05b74e62e76d246fc0d3fffb1642d2e0f8fb31107";
        ConfigManager.setProperty("bearerToken", token);
    }

    @Test
    public void getUsersAPISchemaTest() {

        Response response = restClient.getApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/get-user-schema.json"));
    }


    @Test
    public void createUserAPISchemaTest() {
        User user = User.builder()
                .name("api")
                .status("active")
                .email(StringUtils.getRandomEmailId())
                .gender("female")
                .build();

        Response response = restClient.postApiCall(BASE_URL_GO_REST, GOREST_USERS_ENDPOINT, null, null, user, AuthType.BEARER_TOKEN, ContentType.JSON);

        Assert.assertTrue(SchemaValidator.validateSchema(response, "schema/create-user-schema.json"));

    }


}
