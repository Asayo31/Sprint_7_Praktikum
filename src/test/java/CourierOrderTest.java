import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static java.sql.JDBCType.NULL;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class CourierOrderTest {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<Color> color;
    private int track;

    public CourierOrderTest(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<Color> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;

    }

    private Order order;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        orderClient = new OrderClient();

    }
    @Parameterized.Parameters
    public static Object[][] getOrder() {
        Object[][] objects = {
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of("BLACK")},
                {"Narto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of("GREY")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of("BLACK", "GREY")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of(NULL)}
                ,
        };
        return objects;
    }

    @Test
    public void createOrderTest() {

        ValidatableResponse orderResponce = orderClient.create(order);
        int statusCode = orderResponce.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode);
        track = orderResponce.extract().path("track");
        assert(track > 0);

    }

    @After
    public void tearDown() {
        orderClient.cancel(track);
    }
}