package ru.meldren.weblab2.database;

import org.junit.jupiter.api.Test;
import ru.meldren.weblab2.util.FileUtil;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.meldren.weblab2.Constant.*;

class InfluxDatabaseTest {

    Properties properties = FileUtil.getProperties(CONFIG_FILE_NAME);
    InfluxDatabase database = new InfluxDatabase(
            properties.getProperty(CONFIG_HOST_PROPERTY),
            properties.getProperty(CONFIG_TOKEN_PROPERTY),
            properties.getProperty(CONFIG_ORGANIZATION_PROPERTY),
            "test_plot_points"
    );

    @Test
    void addElement() {
        long count = database.getElementsCount();
        database.addElement(1.0, 1.0, 1.0, 0);
        assertEquals(count + 1, database.getElementsCount());
    }

    @Test
    void getElementsByPage() {
        long count = database.getElementsCount();
        if (count < 10) {
            assertEquals(count, database.getElementsByPage(1).size());
        } else {
            assertEquals(10, database.getElementsByPage(1).size());
        }
    }
}