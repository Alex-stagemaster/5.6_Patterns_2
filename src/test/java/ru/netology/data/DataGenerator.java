package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.netology.data.Registration;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static Faker faker = new Faker(new Locale("en"));
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private DataGenerator() {
    }

    static void registerUser(Registration user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    static String getLogin() {
        return faker.name().firstName().toLowerCase();
    }

    static String getPassword() {
        return faker.internet().password();
    }

    public static class AddUser {

        public static Registration addUserValid() {
            Registration user = new Registration(getLogin(), getPassword(), "active");
            registerUser(user);
            return user;
        }

        public static Registration addUserPasswordInvalid() {
            String login = getLogin();
            Registration user = new Registration(login, getPassword(), "active");
            registerUser(user);
            return new Registration(login, getPassword(), "active");
        }

        public static Registration addUserLoginInvalid() {
            String password = getPassword();
            Registration user = new Registration(getLogin(),password, "active");
            registerUser(user);
            return new Registration(getLogin(), password, "active");
        }

        public static Registration addUserBlocked() {
            Registration user = new Registration(getLogin(), getPassword(), "blocked");
            registerUser(user);
            return user;
        }

        public static Registration addUserNotRegistered() {
            Registration user = new Registration(getLogin(), getPassword(), "active");
            return user;
        }
    }
}