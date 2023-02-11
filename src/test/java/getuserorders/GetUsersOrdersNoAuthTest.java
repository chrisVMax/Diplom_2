package getuserorders;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellarburgers.api.OrderClient;
import stellarburgers.api.UserClient;

import static org.junit.Assert.assertFalse;

public class GetUsersOrdersNoAuthTest {

    //Создать userClient
    private UserClient userClient = new UserClient();

    //Создать orderClient
    private OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Get orders for NO auth user")
    @Description("Get orders for No auth user and check answer that code is 401")

    public void orderCantBeGetForNoAuthUser() {

        //Получить список заказов
        Response responseGetUserOrders = orderClient.getUserOrders("");
        boolean isOrders = responseGetUserOrders
                .then()
                .assertThat().statusCode(401)
                .extract()
                .path("success");
        assertFalse(isOrders);
    }
}
