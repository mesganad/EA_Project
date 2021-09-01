package ars.cs.miu.edu;

import ars.cs.miu.edu.models.Address;
import ars.cs.miu.edu.models.Airport;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class AirportTest {

    @BeforeClass
    public static void setup() {
        RestAssured.port = Integer.valueOf(8090);
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "";
    }

    @Test
    public void testGetOneProduct() {
        Address address = new Address(2,"100N","Fairfield","IOWA","13213");
        Airport airport=new Airport(1,"AMD","American Airline", address);
        airport.setAddress(address);

        given()
                .header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(airport)
                .when().post("/airports").then()
                .statusCode(201);
        given()
                .when()
                .get("airports/code/AMD")
                .then()
                .contentType(ContentType.JSON)
                .and()
                .body("code",equalTo("AMD"))
                .body("name", equalTo("American Airline"));
        //cleanup
        given()
                .when()
                .delete("airports/code/AMD");
    }

}

