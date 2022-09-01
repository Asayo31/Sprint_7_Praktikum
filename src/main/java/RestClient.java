import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RestClient {
    private static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/";

    public RequestSpecification getBaseSpec() {

        return new RequestSpecBuilder().setBaseUri(BASE_URL).
                setContentType("application/json").
                build();
    }
}