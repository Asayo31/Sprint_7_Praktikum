import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CourierOrderListTest {
    private OrderListClient orderListClient;

    @Test
    @DisplayName("Checking the ability to get an orders list")
    @Description("Checking the body and status code of a successful response")
    public void createOrderTest() {
        orderListClient = new OrderListClient();
        ValidatableResponse orderListResponse = orderListClient.getList();
        int statusCode = orderListResponse.extract().statusCode();
        assertEquals("Status code is incorrect", SC_OK, statusCode);
        List<Integer> listOrder = orderListResponse.extract().path("orders");
        assertNotNull("List order is null", listOrder);

    }
}