package ru.meldren.weblab2.database;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.meldren.weblab2.Constant;

import java.time.Instant;
import java.time.ZoneId;

/**
 * Created by Meldren on 02/10/2022
 */
@Measurement(name = Constant.MEASUREMENT)
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Result {

    int number;
    @Column
    double x, y, r;
    boolean successful;
    @Column
    long executionTimeInMicros;
    @Column(timestamp = true)
    Instant time;

    @Override
    public String toString() {
        return String.format("""
                <tr>
                    <td>%d</td>
                    <td>%.2f</td>
                    <td>%.2f</td>
                    <td>%.2f</td>
                    <td>%s</td>
                    <td>%s</td>
                    <td>%d</td>
                <tr>
                """,
                number, x, y, r, successful ? "Да" : "Нет",
                time.atZone(ZoneId.systemDefault())
                        .format(Constant.DATE_FORMATTER),
                executionTimeInMicros
        );
    }
}
