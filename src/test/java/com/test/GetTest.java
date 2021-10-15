package com.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.json.simple.JSONObject;

public class GetTest {

    @Test
    public void testOne() {
       Response response = RestAssured.get("https://reqres.in/api/users/2");
       System.out.println(response.getBody());
       System.out.println(response.asPrettyString());
       System.out.println(response.getTime());
       System.out.println(response.getBody());
       System.out.println(response.getStatusLine());
       int statuscode =  response.getStatusCode();
       Assert.assertEquals(statuscode, 201);


    }

    @Test
    public void testTwo() {
        given().
                get("https://reqres.in/api/users?page=2").
                then().
                statusCode(200).
                body("data.first_name[4]", equalTo("Georg"));
    }

    @Test
    public void testThree() {
        JSONObject request = new JSONObject();
        request.put("name", "Hamim");
        request.put("job", "Dummy");
        System.out.println(request);

        given().body(request.toJSONString()).
                when().post("https://reqres.in/api/users").
                then().
                statusCode(201);




    }

    @Test
    public void testFour() {
        JSONObject request = new JSONObject();
        request.put("name", "Hamim");
        request.put("job", "Dummy");
        System.out.println(request);

        given().body(request.toJSONString()).
                when().put("https://reqres.in/api/users").
                then().
                statusCode(200);




    }

    @Test
    public void testFive() {
        JSONObject request = new JSONObject();
        request.put("name", "Hamim");
        request.put("job", "Dummy");
        System.out.println(request);

        given().body(request.toJSONString()).
                when().delete("https://reqres.in/api/users").
                then().
                statusCode(204);




    }

}
