package ru.meldren.weblab4.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class NumberUtil {

    private final int DEFAULT_PLACES = 3;

    public double roundDouble(double value, int places) {
        return new BigDecimal(value)
                .setScale(places, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public double roundDouble(String value, int places) {
        return roundDouble(Double.parseDouble(value), places);
    }

    public double roundDouble(double value) {
        return roundDouble(value, DEFAULT_PLACES);
    }

    public double roundDouble(String value) {
        return roundDouble(value, DEFAULT_PLACES);
    }

}
