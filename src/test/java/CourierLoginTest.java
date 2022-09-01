import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class CourierLoginTest{

    private Courier courier;

    private Courier courierWrongPassword;
    private CourierClient courierClient;
    private Courier CourierNonAuthorization;
    private int courierId;

    @Before
    public void setUp() {
        courier = CourierGen.getCourierCreate();
        courierWrongPassword = CourierGen.getDuplicateLogin();
        CourierNonAuthorization = CourierGen.CourierNonAuthorization();
        courierClient = new CourierClient();

    }
    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    public void courierAuthorizationTest() {
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrecr", SC_CREATED, statusCode);
        ValidatableResponse loginResponce = courierClient.login(CourierCred.from(courier));
        courierId = loginResponce.extract().path("id");
        assert(courierId>0);
    }

    @Test
    @DisplayName("Checking the inability to log in with incorrect password")
    @Description("Checking ID and status code of an unsuccessful response")
    public void authorizationOfCourierWithAnIncorrectPasswordTest() {
        courierClient.create(courier);
        ValidatableResponse loginResponce = courierClient.login(CourierCred.from(courier));
        courierId = loginResponce.extract().path("id");
        ValidatableResponse loginResponce2 = courierClient.login(CourierCred.from(courierWrongPassword));
        int statusCode = loginResponce2.extract().statusCode();
        assertEquals("Status code is incorrect", SC_NOT_FOUND, statusCode);
    }
    @Test
    @DisplayName("Checking the inability to log in without a login or password")
    @Description("Checking the body and status code of an unsuccessful response")
    public void loginCourierNotWithAllRequiredFieldsTest() {
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCred.from(courier));
        courierId = loginResponse.extract().path("id");
        ValidatableResponse response = courierClient.loginNotWithPassword(CourierLogin.from(courier));
        int statusCode = response.extract().statusCode();
        assertEquals("Incorrect code when registering a user without required fields", SC_BAD_REQUEST, statusCode);
        String message = response.extract().path("message");
        assertEquals("Incorrect message when registering a user without required fields",
                "Недостаточно данных для входа", message);

    }

    @Test
    @DisplayName("Checking the inability to log in with non-existent credentials")
    @Description("Checking the body and status code of an unsuccessful response")
    public void AuthorizationErrorUnderNonexistentUserTest() {
        ValidatableResponse response = courierClient.login(CourierCred.from(CourierNonAuthorization));
        int statusCode = response.extract().statusCode();
        assertEquals("Invalid response code when logging in with a non-existent user", SC_NOT_FOUND, statusCode);
        String message = response.extract().path("message");
        assertEquals("\n" +
                        "Invalid message when logging in under a non-existent user",
                "Учетная запись не найдена", message);
    }
}