package stellarburgers.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import stellarburgers.api.model.Ingredients;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {

    public final String ORDERS_PATH = BASE_URL + "orders";

    //Создание заказа
    @Step("Create order")
    public Response createOrder(String token, Ingredients ingredients) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .and()
                .body(ingredients)
                .when()
                .post(ORDERS_PATH);
    }

    //Получение заказов определенного пользователя
    @Step("Get user orders")
    public Response getUserOrders(String token) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .get(ORDERS_PATH);
    }
}