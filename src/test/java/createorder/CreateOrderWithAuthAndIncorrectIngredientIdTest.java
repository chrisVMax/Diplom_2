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

public class CreateOrderWithAuthAndIncorrectIngredientIdTest {

    //Создать userClient
    private UserClient userClient = new UserClient();
    //Создать orderClient
    private OrderClient orderClient = new OrderClient();
    //Создать ingrediensClient
    private IngredientsClient ingredientsClient = new IngredientsClient();

    @Test
    @DisplayName("Create order with auth and incorrect ingredients id")
    @Description("Create order with auth and check answer that code is 500")

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

        //меняем первый элемент списка на некорректный
        String incorrectID = ingredients.set(0,"123");

        //Создать объект ingredientsInOrder
        Ingredients ingredientsInOrder = new Ingredients(ingredients);
        //Создать заказ из ингридиеднтов
        Response responseCreateOrder = orderClient.createOrder(clearToken, ingredientsInOrder);
        //Проверка код ответа 500
       responseCreateOrder
                .then()
                .assertThat().statusCode(500);
        //удаление пользователя
        UserClient.deleteUser(accessToken);
    }
 }
