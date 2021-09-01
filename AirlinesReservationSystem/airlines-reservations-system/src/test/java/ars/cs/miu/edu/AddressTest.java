package ars.cs.miu.edu;

import ars.cs.miu.edu.models.Address;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class AddressTest {

    @BeforeClass
    public static void setup() {
        RestAssured.port = Integer.valueOf(8090);
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "";
    }

    @Test
    public void testAddress() {
        Address address = new Address(15,"100N","Fairfield","IOWA","52555");


        given()
               .header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(address)
                .when().post("/addresses").then()
                .statusCode(201);
        given()
                .when()
                .get("/addresses/zip/52555")
                .then()
                .contentType(ContentType.JSON)
                .and()
                .body("street",equalTo("100N"))
                .body("city",equalTo("Fairfield"))
                .body("state",equalTo("IOWA"))
                .body("zip",equalTo("52555"));
        //cleanup
        given()
                .when()
                .delete("/addresses/zip/52555");
    }

}
