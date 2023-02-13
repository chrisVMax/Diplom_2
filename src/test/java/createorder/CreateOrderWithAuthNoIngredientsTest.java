package createorder;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellarburgers.api.OrderClient;
import stellarburgers.api.UserClient;
import stellarburgers.api.model.Ingredients;
import stellarburgers.api.model.User;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CreateOrderWithAuthNoIngredientsTest {

    //Создать userClient
    private UserClient userClient = new UserClient();

    //Создать orderClient
    private OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Create order with auth and without ingredients")
    @Description("Create order without ingredients and check answer that code is 400")

    public void orderCanBeCreatedWithAuth() {
        //Создать данные и зарегистрировать пользователя
        User user = User.getRandomUser();
        Response responseRegisterUser = userClient.create(user);
        //Извлечь из ответа токен
        String token = responseRegisterUser
                .then()
                .extract()
                .path("accessToken");
        String clearToken = token.replace("Bearer ", "");

        //Создать пустой список ингредиентов
        ArrayList<String> ingredientsInOrderIsEmpty= new ArrayList<>();

        //Создать объект ingredientsInOrder (нет ингридиентов)
        Ingredients ingredientsInOrder = new Ingredients(ingredientsInOrderIsEmpty);

        //Создать заказ без ингридиеднтов
        Response responseCreateOrder = orderClient.createOrder(clearToken, ingredientsInOrder);
//Проверка код ответа 400 и "success": false
        boolean isOrderNotCreated = responseCreateOrder
                .then()
                .assertThat().statusCode(400)
                .extract()
                .path("success");
        assertFalse(isOrderNotCreated);
        //удаление пользователя
        UserClient.deleteUser(accessToken);
     }
 }
