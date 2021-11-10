package com.testFinalTest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetTest {

    @Test
    public void getCheck() {
        RestAssured.baseURI = "http://localhost:3000";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, "/data");

        /*check if the response body has any
        * given name*/

        JsonPath path = response.jsonPath();
        String actualName = path.get("[0].first_name");
        Assert.assertEquals(actualName, "Michael");

        /*Also checking the response code which should be 200*/
        int responseCode = response.getStatusCode();
        Assert.assertEquals(responseCode, 200);
    }




}
