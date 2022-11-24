package ru.meldren.weblab4.util;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class StringUtil {

    public String randomString(int length) {
        return new SecureRandom()
                .ints('0', 'z')
                .filter(num -> num >= '0' && num <= '9' || num >= 'A' && num <= 'Z' || num >= 'a' && num <= 'z')
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
