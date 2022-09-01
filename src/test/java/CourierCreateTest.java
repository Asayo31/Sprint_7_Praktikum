import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CourierCreateTest {

    private Courier courier;
    private Courier courierDublicate;
    private CourierClient courierClient;
    private int courierId;


    @Before
    public void setUp() {
        courier = CourierGen.getDefault();
        courierDublicate = CourierGen.getDuplicateLogin();
        courierClient = new CourierClient();

    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Checking the ability to create a courier")
    @Description("Checking the ID and status code of a successful response")
    public void courierCanBeCreatedTest() {
        ValidatableResponse response;
        response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode);
        boolean isCreated = response.extract().path("ok");
        assertTrue("Courier is not created", isCreated);
        ValidatableResponse loginResponce = courierClient.login(CourierCred.from(courier));
        courierId = loginResponce.extract().path("id");
        assertNotNull("Id is null", courierId);
        assert (courierId > 0);
    }

    @Test
    @DisplayName("Checking the inability to create two identical couriers")
    @Description("Checking the body and status code of an unsuccessful response")
    public void errorWhenCreatingDublicateCourierTest() {
        courierClient.create(courier);
        ValidatableResponse response2 = courierClient.create(courier);
        int statusCode2 = response2.extract().statusCode();
        assertEquals("You can create two identical couriers", SC_CONFLICT, statusCode2);
        ValidatableResponse loginResponce = courierClient.login(CourierCred.from(courier));
        courierId = loginResponce.extract().path("id");
    }

    @Test
    @DisplayName("Checking the inability to create courier with UNfilled required fields")
    @Description("Checking the message and status code of an unsuccessful response")
    public void creatingCourierNotWithAllRequiredFields() {
        courierClient.create(courier);
        ValidatableResponse response = courierClient.createLogin(CourierLogin.from(courier));
        int statusCode = response.extract().statusCode();
        assertEquals("You can create two identical couriers", SC_BAD_REQUEST, statusCode);
        String message = response.extract().path("message");
        assertEquals("Incorrect message when creating a client with incomplete data",
                "Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Checking the inability to create two couriers with the same login")
    @Description("Checking the message, ID and status code of an unsuccessful response")
    public void creatingCourierWithDuplicateLogin() {
        courierClient.create(courier);
        ValidatableResponse response2 = courierClient.create(courierDublicate);
        int statusCode2 = response2.extract().statusCode();
        String message = response2.extract().path("message");
        assertEquals("Incorrect code for client with duplicate login", SC_CONFLICT, statusCode2);
        assertEquals("Incorrect message for client with duplicate login",
                "Этот логин уже используется. Попробуйте другой.", message);
        ValidatableResponse loginResponce = courierClient.login(CourierCred.from(courier));
        courierId = loginResponce.extract().path("id");

    }

}