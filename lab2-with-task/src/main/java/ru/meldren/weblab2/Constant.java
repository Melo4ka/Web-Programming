package ru.meldren.weblab2;

import java.time.format.DateTimeFormatter;

/**
 * Created by Meldren on 03/10/2022
 */
public interface Constant {

    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");
    String DATABASE_ATTRIBUTE = "database",
            MEASUREMENT = "plot_point",
            CONFIG_FILE_NAME = "config",
            CONFIG_HOST_PROPERTY = "database.hostname",
            CONFIG_TOKEN_PROPERTY = "database.token",
            CONFIG_ORGANIZATION_PROPERTY = "database.organization",
            CONFIG_BUCKET_PROPERTY = "database.bucket";
    int ROWS_PER_PAGE = 10;

    static boolean isOnPlot(double x, double y, double r) {
        return (x >= 0 && x <= r / 2 && y >= 0 && y <= r && 2 * x + y <= r) || //triangle
                (x >= 0 && x <= r / 2 && y <= 0 && y >= -r) || //rectangle
                (x <= 0 && y <= 0 && Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r / 2, 2)); //circle
    }
}
