package ru.meldren.weblab4.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PlotUtil {

    public boolean validate(double x, double y, double r, boolean clicked) {
        return (clicked || x >= -5 && x <= 3 && y >= -5 && y <= 3) &&
                r % 1 == 0 && r >= 1 && r <= 3;
    }

    public boolean isOnPlot(double x, double y, double r) {
        return (x >= -r && x <= 0 && y >= 0 && y <= r / 2) || //rectangle
                (x >= 0 && x <= r / 2 && y >= 0 && y <= r && 2 * x + y <= r) || //triangle
                (x >= 0 && y <= 0 && Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r, 2)); //circle
    }

}