package in.regres;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class RegresApiTests {

    @Test
    void listUsers() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("total_pages", is(2))
                .body("data.email[1]", is("lindsay.ferguson@reqres.in"));
    }

    @Test
    void createUser() {
        String req_data = "{ \"name\": \"Alexander\", \"job\": \"Tester\" }";
        given()
                .contentType(JSON)
                .body(req_data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", is("Alexander"))
                .body("job", is("Tester"));
    }

    @Test
    void deleteUser() {
        given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    void successfulEmailRegister() {
        String req_data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .contentType(JSON)
                .body(req_data)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body("id", is(4))
                .body("token", notNullValue());
    }

    @Test
    void unsuccessfulRegister() {
        String data = "{ \"email\": \"sydney@fife\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void successfulLogin() {

        String req_data = "{ \"email\": \"eve.holt@reqres.in\", " +
                        "\"password\": \"cityslicka\" }";

        given()
                .contentType(JSON)
                .body(req_data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    void negativeLogin() {

        String req_data = "{ \"email\": \"eve.holt@reqres.in\"}";

        given()
                .contentType(JSON)
                .body(req_data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }



}
