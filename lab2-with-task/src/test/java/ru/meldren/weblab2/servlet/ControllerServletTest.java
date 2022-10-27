package ru.meldren.weblab2.servlet;

import com.influxdb.client.InfluxDBClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.meldren.weblab2.database.InfluxDatabase;
import ru.meldren.weblab2.util.ReflectionUtil;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerServletTest {

    @Test
    @SneakyThrows
    void checkDatabaseConnection() {
        InfluxDatabase database = (InfluxDatabase) ReflectionUtil.invokeMethod(
                new ControllerServlet(), "createDatabase");
        InfluxDBClient client = (InfluxDBClient) ReflectionUtil.getField(database, "client");
        assertTrue(client.ping());
    }
}