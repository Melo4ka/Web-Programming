package ru.meldren.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Meldren on 02/10/2022
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Data
public class Result implements Serializable {

    int number;
    Point point;
    boolean successful;
    LocalDateTime time;
    int executionTimeInMicros;

    @Override
    public String toString() {
        return String.format("""
                <tr>
                    <td>%s</td>
                    <td>%.2f</td>
                    <td>%.2f</td>
                    <td>%.2f</td>
                    <td>%s</td>
                    <td>%s</td>
                    <td>%s</td>
                    <td>%s</td>
                <tr>
                """,
                number, point.getX(), point.getY(),
                point.getR(), successful ? "Да" : "Нет",
                point.isClicked() ? "Нажатие" : "Форма",
                time.format(Constant.DATE_FORMATTER),
                executionTimeInMicros
        );
    }
}
