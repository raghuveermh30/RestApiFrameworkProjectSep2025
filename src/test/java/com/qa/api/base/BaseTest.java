package com.qa.api.base;


import com.qa.api.client.RestClient;
import com.qa.api.configmanager.ConfigManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;


//@Listeners(ChainTestListener.class)
public class BaseTest {

    protected RestClient restClient;
    protected static String BASE_URL_GOREST;
    protected static String BASE_URL_REQRES;

    //*** API Base URL's ***
    protected final static String BASE_URL_GO_REST = "https://gorest.co.in";
    protected final static String BASE_URL_CONTACTS = "https://thinking-tester-contact-list.herokuapp.com";
    protected final static String BASE_URL_REQ_RES = "https://reqres.in";
    protected final static String BASE_URL_HERO_BASIC = "https://the-internet.herokuapp.com";
    protected final static String BASE_URL_PRODUCT = "https://fakestoreapi.com";
    protected final static String BASE_URL_OAUTH2_AMADEUS = "https://test.api.amadeus.com";
    protected final static String BASE_URL_ERGAST_CIRCUIT = "http://ergast.com";



    //*** API End Points ***
    protected final static String GOREST_USERS_ENDPOINT = "/public/v2/users";
    protected static final String CONTACTS_ALL_ENDPOINT = "/contacts";
    protected static final String CONTACTS_USER_LOGIN_ENDPOINT = "/users/login";
    protected static final String AMADEUS_OAUTH2_TOKEN_ENDPOINT = "/v1/security/oauth2/token";
    protected static final String AMADEUS_GET_ENDPOINT ="/v1/shopping/flight-destinations";
    protected final static String ERGAST_CIRCUIT_ENDPOINT = "/api/f1/2017/circuits.xml";
    protected final static String PRODUCTS_ENDPOINT = "/products";

    @BeforeSuite
    public void setupAllureReport(){
        RestAssured.filters(new AllureRestAssured());
        BASE_URL_GOREST = ConfigManager.getProperty("baseurl.gorest").trim();
        BASE_URL_REQRES = ConfigManager.getProperty("baseurl.reqres").trim();
    }

    @BeforeTest
    public void setup() {
        restClient = new RestClient();
    }

}
