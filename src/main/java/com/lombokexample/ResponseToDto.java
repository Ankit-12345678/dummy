package com.lombokexample;
import com.Model.Employee;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
public class ResponseToDto {
   private String api = "http://dummy.restapiexample.com/api/v1/employees";
    @Test
    public void responseToDto() {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .request(Method.GET, api)
                .then()
                .extract()
                .response();
        Employee employee1 = response.as(Employee.class);
        System.out.println(employee1);
        System.out.println(employee1.getMessage());
        assertThat(employee1.getStatus(), is("success"));
        assertThat(employee1.getMessage(), is("Successfully! All records has been fetched."));
    }
}