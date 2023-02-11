package getuserorders;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellarburgers.api.OrderClient;
import stellarburgers.api.UserClient;
import stellarburgers.api.model.Ingredients;
import stellarburgers.api.model.User;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class GetUsersOrdersWithAuthTest {


    //Создать userClient
    private UserClient userClient = new UserClient();

    //Создать orderClient
    private OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Get orders for auth user")
    @Description("Get orders for auth user and check answer that code is 200 success is true")

    public void orderCanBeGetForAuthUser() {
        //Создать данные и зарегистрировать пользователя
        User user = User.getRandomUser();
        Response responseRegisterUser = userClient.create(user);
        //Извлечь из ответа токен
        String token = responseRegisterUser
                .then()
                .extract()
                .path("accessToken");
        String clearToken = token.replace("Bearer ", "");

        //Получить список заказов
        Response responseGetUserOrders = orderClient.getUserOrders(clearToken);
        boolean isOrders = responseGetUserOrders
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("success");
        assertTrue(isOrders);
    }
}
