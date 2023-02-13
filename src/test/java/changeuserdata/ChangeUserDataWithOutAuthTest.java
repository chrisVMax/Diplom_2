package changeuserdata;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellarburgers.api.UserClient;
import stellarburgers.api.model.User;

import static org.junit.Assert.*;

public class ChangeUserDataWithOutAuthTest {
    //Создать userClient
    private UserClient userClient = new UserClient();

    @Test
    @DisplayName("Change user data with auth")
    @Description("Change user data with auth and check answer that code is 200 success is true")
    public void userDataCanBeChangedWithAuth() {
        //создать объект - пользователь
        User user = User.getRandomUser();

        //Зарегистрировать пользователя со случайными данными
        Response responseRegisterUser = userClient.create(user);

        //генерация новых данных пользователя
        User userForEditInfo = User.getRandomUser();

        //Меняем данные пользователя, передаем пустой токен
        Response responseEditUserInfo = userClient.editUserInfo("",userForEditInfo);
        boolean isDataChanged = responseEditUserInfo
                .then()
                .extract()
                .path("success");

        String message = responseEditUserInfo
                .then()
                .extract()
                .path("message");

        assertFalse(isDataChanged);
        assertEquals(message, "You should be authorised");
        //удаление пользователя
        UserClient.deleteUser(accessToken);

        }
    }

