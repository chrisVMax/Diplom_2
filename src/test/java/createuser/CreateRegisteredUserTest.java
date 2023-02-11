package createuser;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellarburgers.api.UserClient;
import stellarburgers.api.model.User;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class CreateRegisteredUserTest {
    //Создать userClient
    private UserClient userClient = new UserClient();
    @Test
    @DisplayName("Create registered user")
    @Description("Create user which already exist and check answer that code 403 and success is false")
    public void userCantBeRegisterWithSameCredentials() {

        //создать объект - пользователь
        User user = User.getRandomUser();
        //Зарегистрировать пользователя со случайными данными
        Response responseRegisterUser = userClient.create(user);
        //Зарегистрировать пользователя с этими же данными повторно
        Response responseRegisterTheSameUser = userClient.create(user);

        boolean isUserNotRegistered = responseRegisterTheSameUser
                .then()
                .assertThat().statusCode(403)
                .extract()
                .path("success");
        //проверка что  код ответа 403 Forbidden и "success": false,
        // если нет - вывести сообщение
        assertFalse(isUserNotRegistered, "User is created");
    }
}
