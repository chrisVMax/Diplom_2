package createuser;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellarburgers.api.UserClient;
import stellarburgers.api.model.User;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class CreateUserWithoutNameTest {
    //Создать userClient
    private UserClient userClient = new UserClient();

    @Test
    @DisplayName("Create a new user without name")
    @Description("Create new user without name and check answer that code 403 and success is false")
    //создать пользователя без имени
    public void userCantBeRegisterWithoutName(){
        //Создать объект user - экземпляр класса со случайными данными, но без имени
        User user = User.getUserWithoutName();
        //Вызвать метод создания пользователя, сохранить ответ
        // проверка код ответа
        Response response = userClient.create(user);
        boolean isUserNotRegistered = response
                .then()
                .assertThat().statusCode(403)
                .extract()
                .path("success");
        //Проверка что в ответе success: false, если нет - вывести сообщение
        assertFalse(isUserNotRegistered, "User is created");
    }
}
