package com.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.entity.mime.content.ContentBody;
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


    @Test
    public void testSix() {
        baseURI = "http://localhost:3000/";

        given().
                param("firstName", "Hamim").
                get("/users").
                then().log().all();
    }

    @Test
    public void testSeven() {
        baseURI = "http://localhost:3000/";
        JSONObject body = new JSONObject();
        body.put("FirstName", "Dummy");
        body.put("LastName", "Meme");

        given().
                contentType(ContentType.JSON).accept(ContentType.JSON).
                header("Content-Type", "Application/json").
                body(body.toJSONString()).
                when().
                delete("/users/4").
                then().
                statusCode(404);
    }

    @Test
    public void testEight() {
        baseURI = "http://localhost:3000/";

        given().
                param("id", 1).
                get("/subjects").
                then().log().all();

    }

}
