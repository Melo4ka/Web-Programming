package ru.meldren.weblab4.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthenticationStatus {

    SUCCESS("Успешная аутентификация"),
    USER_ALREADY_EXISTS("Пользователь с данным именем уже зарегистрирован"),
    ALREADY_AUTHENTICATED("Вы уже прошли процедуру аутентификации"),
    INVALID_CREDENTIALS("Логин или пароль не соответствуют требованиям"),
    USER_DOES_NOT_EXIST("Указанный пользователь не зарегистрирован"),
    WRONG_PASSWORD("Неправильный пароль"),
    INVALID_TOKEN("Некорректный токен"),
    UNKNOWN_ERROR("Неизвестная ошибка");

    private final String message;
}
