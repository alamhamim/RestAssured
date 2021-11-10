package com.testTwo;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class APIClass {

    @Test
    public void testGet() {
        RestAssured.baseURI = "https://reqres.in/api/users";
        RequestSpecification httpRequ = RestAssured.given();
        Response response = httpRequ.request(Method.GET, "/2");
        String showBody = response.getBody().asPrettyString();
        System.out.println("Resposnse Body is " + showBody);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        JsonPath path = response.jsonPath();
        String name = path.get("data.first_name");
        Assert.assertEquals(name, "Janet");

    }

    @Test
    public void getAllUsersAndVerifyTheBody() {
        RestAssured.baseURI = "https://reqres.in/api";
        RequestSpecification http = RestAssured.given();
        Response response = http.get("/users");

        String allUsers = response.getBody().asPrettyString();
        System.out.println("All the users: " + allUsers);

        //Verifying the response code which should be 200
        int responseCode = response.statusCode();
        Assert.assertEquals(responseCode, 200);
    }


    @Test
    public void postSomething() {
        RestAssured.baseURI = "https://reqres.in";
        RequestSpecification http = RestAssured.given();

        JSONObject object = new JSONObject();
        object.put("first_name", "Hamim");
        object.put("last_name", "Alam");
        http.header("Content-Type", "application/json");
        http.body(object.toJSONString());

        Response response = http.request(Method.POST, "/api/users");
        String responseBody = response.getBody().asPrettyString();
        System.out.println("Response Body is: "+responseBody);
        JsonPath path = response.jsonPath();
        System.out.println(path.get("first_name").toString());

        Headers headers = response.headers();

        for (Header header : headers) {
            System.out.println(header);
        }

        int responseCode = response.getStatusCode();
        Assert.assertEquals(responseCode, 201);



    }

    @Test
    public void updatePut() {
        RestAssured.baseURI = "https://reqres.in";
        RequestSpecification httpRequest = RestAssured.given();

        JSONObject object = new JSONObject();
        object.put("first_name", "Hamim");
        object.put("last_name", "Alam");

        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(object.toJSONString());

        Response response = httpRequest.put("/api/users/2");
        System.out.println(response.getBody().asPrettyString());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        JsonPath path = response.jsonPath();
        String verifyName = path.get("first_name");
        Assert.assertEquals(verifyName, "Hamim");

        Headers headers = response.getHeaders();

        for (Header header : headers) {
            System.out.println(header);

        }


    }



    @Test
    public void deleteMethod() {

        RequestSpecification request = RestAssured.given();

        Response response = request.request(Method.DELETE, "https://reqres.in/api/users/2");
        int responseCode = response.getStatusCode();
        Assert.assertEquals(responseCode, 204);

    }

    @Test(dataProvider = "dataProvider", dataProviderClass = APIClass.class)
    public void postWithManyData(String firstName, String lastName) {
        RequestSpecification request = RestAssured.given();
        JSONObject object = new JSONObject();
        object.put("first_name", firstName);
        object.put("last_name", lastName);
        request.header("Content_Type", "application/json");
        request.body(object.toJSONString());

        Response response = request.request(Method.POST, "https://reqres.in/api/users");

        int responseCode = response.getStatusCode();
        Assert.assertEquals(responseCode, 201);

    }

    @DataProvider(name = "dataProvider")
    public static Object[][] dataProdiver() {
        FileInputStream file = null;
        XSSFWorkbook book = null;
        XSSFSheet sheet = null;
        Object[][] data = null;

        try {
            file = new FileInputStream(new File("path"));
            book = new XSSFWorkbook(file);
            sheet = book.getSheetAt(0);

            int getRow = sheet.getLastRowNum();
            int getCell = sheet.getRow(0).getLastCellNum();

            data = new Object[getRow][getCell];

            for (int r = 0; r <= getRow; r++) {
                for (int c = 1; c < getCell; c++) {
                    XSSFCell cell = sheet.getRow(r).getCell(c);

                    switch (cell.getCellType()) {
                        case XSSFCell.CELL_TYPE_STRING:
                            data[r][c] = cell.getStringCellValue();
                        case XSSFCell.CELL_TYPE_NUMERIC:
                            data[r][c] = cell.getNumericCellValue();
                        default:
                            break;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                book.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return data;

    }

    @Test
    public void getTest() {
        RestAssured.baseURI = "http://localhost:3000";

        RequestSpecification httpRequ = RestAssured.given();
        Response response = httpRequ.request(Method.GET, "/subjects/1");
        httpRequ.header("Content-Type", "application/json");
        String getBody =  response.getBody().asPrettyString();

        System.out.println("The body is: " + getBody);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 404);

        Headers headers = new Headers();

        for (Header header : headers) {
            System.out.println(header);
        }

    }


}
