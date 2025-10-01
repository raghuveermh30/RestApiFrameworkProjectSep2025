package com.qa.api.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T deserialize(Response response, Class<T> targetPojoClass){
        try {
            return objectMapper.readValue(response.getBody().asString(), targetPojoClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Deserialization is failed... "+targetPojoClass.getName());
        }
    }
}
