package com.qa.api.contacts.test;

import com.qa.api.base.BaseTest;
import com.qa.api.configmanager.ConfigManager;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.ContactsCredentials;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContactsApiTest extends BaseTest {

    private String token;

    @BeforeMethod
    public void getToken() {
        ContactsCredentials contactsCredentials = ContactsCredentials.builder().email("naveenanimation20@gmail.com")
                .password("test@123").build();
        Response response = restClient.postApiCall(BASE_URL_CONTACTS, CONTACTS_USER_LOGIN_ENDPOINT, null, null, contactsCredentials, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 200);
        token = response.jsonPath().getString("token");
        System.out.println("Contacts API Token : " + token);
        ConfigManager.setProperty("bearerToken", token);
    }

    @Test
    public void getAllContacts() {
        Response response = restClient.getApiCall(BASE_URL_CONTACTS, CONTACTS_ALL_ENDPOINT, null, null,
                AuthType.BEARER_TOKEN, ContentType.JSON);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
