package createorder;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellarburgers.api.IngredientsClient;
import stellarburgers.api.OrderClient;
import stellarburgers.api.UserClient;
import stellarburgers.api.model.Ingredients;
import stellarburgers.api.model.User;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CreateOrderWithAuthAndIngredientsTest {

    //Создать userClient
    private UserClient userClient = new UserClient();
    //Создать orderClient
    private OrderClient orderClient = new OrderClient();
    //Создать ingrediensClient
    private IngredientsClient ingredientsClient = new IngredientsClient();

    @Test
    @DisplayName("Create order with auth")
    @Description("Create order with auth and check answer that code is 200 success is true")

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

        //Получить список ингридиентов

        Response responseGetIngredients = ingredientsClient.getIngredients(clearToken);
        ArrayList <String> ingredients = responseGetIngredients
                .then()
                .extract()
                .path("data._id");

        //Создать объект ingredientsInOrder
        Ingredients ingredientsInOrder = new Ingredients(ingredients);
        //Создать заказ из ингридиеднтов
        Response responseCreateOrder = orderClient.createOrder(clearToken, ingredientsInOrder);
        //Извлечь из ответа значение по ключу success
        boolean isOrderCreated = responseCreateOrder
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("success");

        //Извлечь из ответа номер заказа
        int orderNumber = responseCreateOrder
                .then()
                .extract()
                .path("order.number");

        //Проверка "success": true
        assertTrue(isOrderCreated);
        //Проверка что заказу присвоен номер
        assertNotNull(orderNumber);
        //удаление пользователя
        UserClient.deleteUser(accessToken);

    }
 }
