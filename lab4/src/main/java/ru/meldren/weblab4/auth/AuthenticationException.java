package ru.meldren.weblab4.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationException extends RuntimeException {

    private final AuthenticationStatus status;

    @Override
    public String getMessage() {
        return status.getMessage();
    }
}
