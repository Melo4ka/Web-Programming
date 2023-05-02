package ru.meldren.lab3.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.meldren.weblab3.util.LocalizationUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Meldren on 01/04/2023
 */
class LocalizationUtilTest {

    @ParameterizedTest
    @CsvSource({"yes,Да", "no,Нет"})
    void testMessageLocalization(String input, String expected) {
        String value = LocalizationUtil.getMessage(input);
        assertEquals(expected, value);
    }
}