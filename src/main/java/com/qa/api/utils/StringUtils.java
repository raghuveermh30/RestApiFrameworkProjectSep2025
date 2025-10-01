package com.qa.api.utils;

public class StringUtils {

    public static String getRandomEmailId(){
        return "apiAutomation"+ System.currentTimeMillis()+"@open.com";
    }

    public static String getRandomName(){
        return "apiAutomation"+ System.currentTimeMillis();
    }
}
