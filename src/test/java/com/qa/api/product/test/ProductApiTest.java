package com.qa.api.product.test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Products;
import com.qa.api.utils.JsonUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.qa.api.utils.JsonUtils.deserialize;

public class ProductApiTest extends BaseTest {

    @Test
    public void getAllProductsTest() {
        Response getProductResponse = restClient.getApiCall(BASE_URL_PRODUCT, PRODUCTS_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);

        Products[] productJsonResponse = JsonUtils.deserialize(getProductResponse, Products[].class);

        System.out.println(Arrays.toString(productJsonResponse));

        for (Products products : productJsonResponse) {
            System.out.println("id : " + products.getId());
            System.out.println("title : " + products.getTitle());
            System.out.println("price : " + products.getPrice());
            System.out.println("description : " + products.getDescription());
            System.out.println("category : " + products.getCategory());
            System.out.println("rate : " + products.getRating().getRate());
            System.out.println("count : " + products.getRating().getCount());
        }
    }
}
