import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    private static final String ORDER_PATH = "/api/v1/orders";
    private static final String ORDER_CANCEL = "/api/v1/orders/cancel";

    @Step
    public ValidatableResponse create(Order order) {
        return given().spec(getBaseSpec()).and().body(order).when().post(ORDER_PATH).then();
    }

    public void cancel(int id) {
        given().spec(getBaseSpec()).and().body(id).put(ORDER_CANCEL).then();
    }

}