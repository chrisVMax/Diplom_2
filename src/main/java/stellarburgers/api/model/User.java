package stellarburgers.api.model;

import com.github.javafaker.Faker;

public class User {
    private String email;
    private String password;
    private String name;

    //Конструкторы
    public User() {
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //Сгенерируем случайные данные юзера
    public static User getRandomUser(){
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String password = faker.lorem().characters(8, true);
        String name = faker.name().firstName();
    return new User (email, password, name);
    }

    public static User getUserWithoutName(){
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String password = faker.lorem().characters(8, true);
        return new User (email, password);
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
}
