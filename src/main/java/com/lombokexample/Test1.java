package com.lombokexample;

import com.Model.Employee;
import com.Model.Employees;
import groovy.json.JsonException;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
public class Test1 {
    private String apiPath = "http://dummy.restapiexample.com/api/v1/";

    @Test
    public void getEmployee() throws JsonException {

        Employees dto = given()
                .accept(ContentType.JSON)
                .when()
                .request(Method.GET, apiPath + "employees")
                .then()
                .extract()
                .response()
                .as(Employees.class);

        assertThat(dto.getData(), notNullValue());
        assertThat(dto.getStatus(), is("success"));
        assertThat(dto.getMessage(), is("Successfully! All records has been fetched."));

    }

    @Test
    public void getEmployeeById() throws JsonException {

        Employee response = given()
                .accept(ContentType.JSON)
                .when()
                .request(Method.GET, apiPath + "employee/1")
                .then()
                .extract()
                .response().as(Employee.class);

        assertThat(response.getStatus(), is("success"));
        assertThat(response.getData().employee_name, is("Tiger Nixon"));
        assertThat(response.getData().employee_age, is(61));
        assertThat(response.getData().id, is(1));
        assertThat(response.getData().employee_salary, is("320800"));
    }


    @Test
    public void postEmployee() throws JsonException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("employee_name", "Tiger Nixon");
        jsonObject.put("employee_salary", "320800");
        jsonObject.put("employee_age", 61);
        Response response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .body(jsonObject)
                .request(Method.POST, apiPath + "create")
                .then()
                .extract()
                .response();
        assertThat(response.statusCode(), is(HttpStatus.SC_OK));
    }

    @Test
    public void deleteEmployee() {
        int empId = 2;
        Response response = given()
                .accept(ContentType.JSON)
                .when()
                .request(Method.DELETE, apiPath + "delete/" + empId)
                .then()
                .extract()
                .response();

        assertThat(response.statusCode(), is(HttpStatus.SC_OK));
    }

    int empId = 21;
    @Test
    public void putEmployee() {

        JSONObject updateBody = new JSONObject();
        updateBody.put("employee_name", "Tiger Nixon");
        updateBody.put("employee_salary", "320800");
        updateBody.put("employee_age", 61);
        Response putResponse = putEmployee(updateBody, empId);
        assertThat(putResponse.statusCode(), is(HttpStatus.SC_OK));
    }

    public Response putEmployee(JSONObject body, Integer id) {

        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .body(body.toJSONString())
                .request(Method.PUT, apiPath + "update/" + id)
                .then()
                .extract()
                .response();
        return response;
    }

}
