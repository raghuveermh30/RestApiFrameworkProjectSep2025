package com.qa.api.utils;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class XmlPathValidator {

    private static XmlPath getXmlPath(Response response){
        String responseBody = response.body().asString();
        return new XmlPath(responseBody);
    }

    public static <T> T read(Response response, String xmlPathExpression) {
        XmlPath xmlPath = getXmlPath(response);
        return xmlPath.get(xmlPathExpression);
    }

    public static <T> List<T> readList(Response response, String jsonPath) {
        XmlPath xmlPath = getXmlPath(response);
        return xmlPath.getList(jsonPath);
    }

    public static <T> List<Map<String, T>> readListOfMaps(Response response, String jsonPath) {
        XmlPath xmlPath = getXmlPath(response);
        return xmlPath.getList(jsonPath);
    }

}
