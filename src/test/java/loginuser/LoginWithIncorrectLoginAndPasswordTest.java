package loginuser;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellarburgers.api.UserClient;
import stellarburgers.api.model.User;
import stellarburgers.api.model.UserCredentials;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class LoginWithIncorrectLoginAndPasswordTest {
    //Создать userClient
    private UserClient userClient = new UserClient();
    //Создать объект - пользователь
    User user = new User("incorrect", "incorrect");
    @Test
    @DisplayName("Login with incorrect user data")
    @Description("Login user with incorrect user data and check answer that code 401 and success is false")
    public void userWithIncorrectDataCantBeLoggedIn() {
        //создать объект - UserCredentials (email и пароль)
        UserCredentials userCredentials = new UserCredentials(user.getEmail(), user.getPassword());
        //логин пользователя
        Response responseLoginUser = userClient.loginUser(userCredentials);
        boolean isUserLoggedIn = responseLoginUser
                .then()
                .assertThat().statusCode(401)
                .extract()
                .path("success");
        assertFalse(isUserLoggedIn, "User is created");
    }
}
