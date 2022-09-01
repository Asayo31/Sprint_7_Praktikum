import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {

    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String COURIER_LOGIN = "/api/v1/courier/login";
    private static final String COURIER_DELETE = "/api/v1/courier/{id}";

    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }
    public ValidatableResponse createLogin(CourierLogin courier) {
        return given()
                .spec(getBaseSpec())
                .and()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    public ValidatableResponse login(CourierCred courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }
    public ValidatableResponse loginNotWithPassword(CourierLogin courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }

    public void delete(int id) {
        given()
                .spec(getBaseSpec())
                .delete(COURIER_DELETE, id)
                .then();
    }
}