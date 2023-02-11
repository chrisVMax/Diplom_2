package stellarburgers.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IngredientsClient extends RestClient {
    public final String INGREDIENTS_PATH = BASE_URL + "ingredients";
    //Получение данных об ингридиентах
    @Step("Get ingredients")
    public Response getIngredients(String token) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .get(INGREDIENTS_PATH);
    }

}
