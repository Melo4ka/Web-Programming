package ru.meldren.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * Created by Meldren on 30/09/2022
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Data
public class Point implements Serializable {
    double x, y, r;
    boolean clicked;
}
