package loginuser;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellarburgers.api.UserClient;
import stellarburgers.api.model.User;
import stellarburgers.api.model.UserCredentials;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginRegisteredUserTest {
    //Создать userClient
    private UserClient userClient = new UserClient();
    @Test
    @DisplayName("Login user")
    @Description("Login user and check answer that code 200 and success is true")
    public void userCanBeLogin() {
        //создать объект - пользователь
        User user = User.getRandomUser();
        //сохранить данные в переменные
        String email = user.getEmail();
        String password = user.getPassword();
        //Зарегистрировать пользователя со случайными данными
        Response responseRegisterUser = userClient.create(user);

        //создать объект - UserCredentials (email и пароль)
        UserCredentials userCredentials = new UserCredentials(email, password);
        //логин пользователя
        Response responseLoginUser = userClient.loginUser(userCredentials);
        boolean isUserLoggedIn = responseLoginUser
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("success");
        //Проверка что в ответе success: true, если нет - вывести сообщение
        assertTrue(isUserLoggedIn, "User not created");
        //Проверка наличие токена в ответе
        String token = responseLoginUser
                .then()
                .extract()
                .path("accessToken");
        String clearToken = token.replace("Bearer ", "");
        assertFalse(clearToken.isEmpty());
        //удаление пользователя
        UserClient.deleteUser(accessToken);
    }
}
