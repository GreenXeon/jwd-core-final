package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class PropertyReaderUtil {

    private static final Properties properties = new Properties();

    private PropertyReaderUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(PropertyReaderUtil.class);

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    public static void loadProperties() {
        final String propertiesFileName = "src/main/resources/application.properties";
        try (FileInputStream inputStream = new FileInputStream(new File(propertiesFileName))) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.info("Error while reading properties file");
        }
    }

    public static Properties getProperties() {
        return properties;
    }
}
