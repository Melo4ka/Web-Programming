package ru.meldren.weblab2.database;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResultTest {

    @Test
    void toStringTest() {
        Result result = new Result();
        result.number = 2;
        result.x = 1.2;
        result.y = -2.4444;
        result.r = 1.0;
        result.successful = true;
        result.executionTimeInMicros = 12345;
        result.time = Instant.ofEpochMilli(1234567890);
        assertEquals("""
                <tr>
                    <td>2</td>
                    <td>1,20</td>
                    <td>-2,44</td>
                    <td>1,00</td>
                    <td>Да</td>
                    <td>15.01.70 09:56:07</td>
                    <td>12345</td>
                <tr>
                """, result.toString());
    }
}