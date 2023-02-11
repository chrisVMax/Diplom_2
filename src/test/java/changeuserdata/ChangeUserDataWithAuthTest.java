package changeuserdata;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellarburgers.api.UserClient;
import stellarburgers.api.model.User;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ChangeUserDataWithAuthTest {
    //Создать userClient
    private UserClient userClient = new UserClient();

    @Test
    @DisplayName("Change user data with auth")
    @Description("Change user data with auth and check answer that code is 200 success is true")
    public void userDataCanBeChangedWithAuth() {
        //создаем объект - пользователь
        User user = User.getRandomUser();

        //Регистрируем пользователя со случайными данными
        Response responseRegisterUser = userClient.create(user);
        //Извлечение токена
        String token = responseRegisterUser
                .then()
                .extract()
                .path("accessToken");
        String clearToken = token.replace("Bearer ", "");
        //Генерация новых данных пользователя
        User userForEditInfo = User.getRandomUser();
        //Меняем данные пользователя с токеном из responseRegisterUser и новые данные пользователя
        Response responseEditUserInfo = userClient.editUserInfo(clearToken,userForEditInfo);

        boolean isDataChanged =
                responseEditUserInfo
                        .then()
                        .extract()
                        .path("success");

        String name = responseEditUserInfo
                .then()
                .extract()
                .path("user.name");

        String email = responseEditUserInfo
                .then()
                .extract()
                .path("user.email");

        assertTrue(isDataChanged);
        assertNotEquals(user.getName(), name);
        assertNotEquals(user.getEmail(), email);

        }
    }

