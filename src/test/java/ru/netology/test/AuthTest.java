package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.Registration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.AddUser.*;



public class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void positiveAllInputActive() {
        Registration testUser = addUser("active");
        $("[data-test-id=login] input").setValue(testUser.getLogin());
        $("[data-test-id=password] input").setValue(testUser.getPassword());
        $(".button__content").click();
        $(".heading").shouldBe(Condition.text("Личный кабинет"));
    }

    @Test
    void negativeAllInputBlocked() {
        Registration testUser = addUser("blocked");
        $("[data-test-id=login] input").setValue(testUser.getLogin());
        $("[data-test-id=password] input").setValue(testUser.getPassword());
        $(".button__content").click();
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void negativeLoginInvalid() {
        Registration testUser = addUserLoginInvalid();
        $("[data-test-id=login] input").setValue(testUser.getLogin());
        $("[data-test-id=password] input").setValue(testUser.getPassword());
        $(".button__content").click();
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void negativePasswordInvalid() {
        Registration testUser = addUserPasswordInvalid();
        $("[data-test-id=login] input").setValue(testUser.getLogin());
        $("[data-test-id=password] input").setValue(testUser.getPassword());
        $(".button__content").click();
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void negativeUserNotRegistered() {
        Registration testUser = addUserNotRegistered();
        $("[data-test-id=login] input").setValue(testUser.getLogin());
        $("[data-test-id=password] input").setValue(testUser.getPassword());
        $(".button__content").click();
        $("[data-test-id=error-notification]").shouldBe(visible).shouldHave(text("Неверно указан логин или пароль"));
    }
}
