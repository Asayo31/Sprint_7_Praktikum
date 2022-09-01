import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderListClient extends RestClient {
    private static final String ORDERS_PATH = "/api/v1/orders";
    @Step
    public ValidatableResponse getList(){
        return given()
                .spec(getBaseSpec())// настройки base URL и метод. тип данных. в нашем случае это джейсон.
                .when()
                .get(ORDERS_PATH)
                .then();
    }
}