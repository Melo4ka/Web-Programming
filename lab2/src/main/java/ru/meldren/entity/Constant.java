package ru.meldren.entity;

import java.time.format.DateTimeFormatter;

/**
 * Created by Meldren on 03/10/2022
 */
public interface Constant {
    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");
    String ATTRIBUTE_NAME = "results";
}
