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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CreateOrderNoAuthAndWithIngredientsTest {

    //Создать userClient
    private UserClient userClient = new UserClient();
    //Создать orderClient
    private OrderClient orderClient = new OrderClient();
    //Создать ingrediensClient
    private IngredientsClient ingredientsClient = new IngredientsClient();

    @Test
    @DisplayName("Create order without auth")
    @Description("Create order without auth and check answer that code is 401 success is false")

    public void orderCanBeCreatedWithoutAuth() {

        //Создать данные и зарегать пользователя
        User user = User.getRandomUser();
        Response responseRegisterUser = userClient.create(user);
        //Извлечь из ответа токен
        String token = responseRegisterUser
                .then()
                .extract()
                .path("accessToken");
        String clearToken = token.replace("Bearer ", "");

        //Получение списка ингридиентов
        Response responseGetIngredients = ingredientsClient.getIngredients(clearToken);
        ArrayList <String> ingredients = responseGetIngredients
                .then()
                .extract()
                .path("data._id");

        //Создать объект ingredientsInOrder
        Ingredients ingredientsInOrder = new Ingredients(ingredients);
        //Создать заказ из ингридиедиентов без токена (без авторизации)
        Response responseCreateOrder = orderClient.createOrder("", ingredientsInOrder);
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
