package ru.meldren.weblab2.util;

import lombok.experimental.UtilityClass;

import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Meldren on 24/10/2022
 */
@UtilityClass
public class FileUtil {

    public Properties getProperties(String fileName) {
        Properties properties = new Properties();
        ResourceBundle bundle = ResourceBundle.getBundle(fileName);
        bundle.keySet().forEach(key -> properties.put(key, bundle.getString(key)));
        return properties;
    }
}
