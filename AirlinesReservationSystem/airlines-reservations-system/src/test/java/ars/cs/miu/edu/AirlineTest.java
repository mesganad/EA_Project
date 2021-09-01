package ars.cs.miu.edu;

import ars.cs.miu.edu.models.Airline;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class AirlineTest {

    @BeforeClass
    public static void setup() {
        RestAssured.port = Integer.valueOf(8090);
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "";
    }

    @Test
    public void testAirline() {
        Airline airline = new Airline(12,"UT", "United", "The biggest airline in eritrea");

        given()
                .header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(airline)
                .when().post("/airlines").then()
                .statusCode(201);

        given()
                .when()
                .get("/airlines/code/UT")
                .then()
                .contentType(ContentType.JSON)
                .and()
                .body("code",equalTo("UT"))
                .body("name", equalTo("United"));
       // cleanup
        given()
                .when()
                .delete("/airlines/code/UT");
    }

}
