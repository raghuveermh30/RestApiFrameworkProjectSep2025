package com.qa.api.circuits;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.XmlPathValidator;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CircuitAPIWithXMLTest extends BaseTest {

    @Test
    public void getCircuitInfoTest() {
        Response response =
                restClient.getApiCall(BASE_URL_ERGAST_CIRCUIT, ERGAST_CIRCUIT_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.ANY);

        List<String> circuitNames = XmlPathValidator.readList(response, "MRData.CircuitTable.Circuit.CircuitName");
        System.out.println(circuitNames);

        for (String e : circuitNames) {
            Assert.assertNotNull(e);
        }


        String americaLoc = XmlPathValidator.read(response, "**.find{ it.@circuitId == 'americas' }.Location.Locality");
        System.out.println("americas location--->" + americaLoc);
        Assert.assertEquals(americaLoc, "Austin");

    }

}
