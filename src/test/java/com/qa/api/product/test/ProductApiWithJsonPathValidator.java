package com.qa.api.product.test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidator;
import io.qameta.allure.Epic;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

@Epic("Epic Products Api Tests")
public class ProductApiWithJsonPathValidator extends BaseTest {

    @Test
    public void getAllProductsTest() {
        Response getProductApiResponse = restClient.getApiCall(BASE_URL_PRODUCT, "/products", null, null, AuthType.NO_AUTH, ContentType.JSON);
        Assert.assertEquals(getProductApiResponse.getStatusCode(), 200);

        List<Number> prices = JsonPathValidator.readList(getProductApiResponse, "$[?(@.price > 50)].price");
        prices.forEach(System.out::println);
        System.out.println("****************");

        List<Number> ids = JsonPathValidator.readList(getProductApiResponse, "$[?(@.price > 50)].id");
        ids.forEach(System.out::println);
        System.out.println("****************");

        List<Double> rateList = JsonPathValidator.readList(getProductApiResponse, "$[?(@.price > 50)].rating.rate");
        rateList.forEach(System.out::println);
        System.out.println("****************");

        List<Integer> countList = JsonPathValidator.readList(getProductApiResponse, "$[?(@.price > 50)].rating.count");
        countList.forEach(System.out::println);
        System.out.println("****************");

        //Get List of MAP
        List<Map<String, Object>> idTitleList = JsonPathValidator.readListOfMaps(getProductApiResponse, "$.[*].['id','title']");
        idTitleList.forEach(System.out::println);
        System.out.println("****************");

        List<Map<String, Object>> idTitleCatList = JsonPathValidator.readListOfMaps(getProductApiResponse, "$.[*].['id','title', 'category']");
        idTitleCatList.forEach(System.out::println);
        System.out.println("****************");

        List<Map<String, Object>> jewelleryList = JsonPathValidator.readList(getProductApiResponse, "$[?(@.category == 'jewelery')]..['title','price']");
        System.out.println(jewelleryList.size());

        for (Map<String, Object> product : jewelleryList) {
            String title = product.get("title").toString();
            Number price = (Number) product.get("price");
            System.out.println("title:" + title);
            System.out.println("price:" + price);
            System.out.println("***************");
        }

        //Get Minimum Price
        Double minPrice = JsonPathValidator.read(getProductApiResponse, "min($[*].price)");
        System.out.println("Minimum Price is " + minPrice);

        //Get Maximum Price
        Double maxPrice = JsonPathValidator.read(getProductApiResponse, "max($[*].price)");
        System.out.println("Maximum Price is " + maxPrice);

        //Get Average Price
        Double averagePrice = JsonPathValidator.read(getProductApiResponse, "avg($[*].price)");
        System.out.println("Average Price is " + averagePrice);

        //Get stddev() - Provides the standard deviation value of an array of numbers
        Double stddev = JsonPathValidator.read(getProductApiResponse, "stddev($[*].price)");
        System.out.println("Average Price is " + stddev);

        // length() - Provides the length of an array
        Integer arrayLength = JsonPathValidator.read(getProductApiResponse, "length($)");
        System.out.println("Array length: " + arrayLength);
        System.out.println("-----------");

    }
}
