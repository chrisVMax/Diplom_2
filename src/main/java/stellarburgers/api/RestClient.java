package stellarburgers.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestClient {
    //Общая часть URL
    public final String BASE_URL ="https://stellarburgers.nomoreparties.site/api/";
    //Базовая спецификация для всех запросов
    //Content-type всегда application/json
    //URL всегда BASE_URL
    protected RequestSpecification getBaseSpec(){
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();
    }
}
