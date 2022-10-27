package ru.meldren.weblab2.util;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileUtilTest {

    @Test
    void getProperty() {
        Properties properties = FileUtil.getProperties("config");
        assertEquals("http://127.0.0.1:8086", properties.getProperty("database.hostname"));
    }
}