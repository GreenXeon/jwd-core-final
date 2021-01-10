package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.util.PropertyReaderUtil;

import java.util.Properties;

/**
 * This class should be IMMUTABLE!
 * <p>
 * Expected fields:
 * <p>
 * inputRootDir {@link String} - base dir for all input files
 * outputRootDir {@link String} - base dir for all output files
 * crewFileName {@link String}
 * missionsFileName {@link String}
 * spaceshipsFileName {@link String}
 * <p>
 * fileRefreshRate {@link Integer}
 * dateTimeFormat {@link String} - date/time format for {@link java.time.format.DateTimeFormatter} pattern
 */
public final class ApplicationProperties {
    //todo
    private final String inputRootDir;
    private final String outputRootDir;
    private final String crewFileName;
    private final String missionsFileName;
    private final String missionsOutput;
    private final String spaceshipsFileName;
    private final Integer fileRefreshDate;
    private final String dateTimeFormat;

    private final Properties properties = PropertyReaderUtil.getProperties();

    public ApplicationProperties() {
        this.inputRootDir = properties.getProperty("inputRootDir");
        this.outputRootDir = properties.getProperty("outputRootDir");
        this.crewFileName = properties.getProperty("crewFileName");
        this.missionsFileName = properties.getProperty("missionsFileName");
        this.missionsOutput = properties.getProperty("missionsOutput");
        this.spaceshipsFileName = properties.getProperty("spaceshipsFileName");
        this.fileRefreshDate = Integer.getInteger(properties.getProperty("fileRefreshDate"));
        this.dateTimeFormat = properties.getProperty("dateTimeFormat");
    }

    public String getInputRootDir() {
        return inputRootDir;
    }

    public String getOutputRootDir() {
        return outputRootDir;
    }

    public String getCrewFileName() {
        return crewFileName;
    }

    public String getMissionsFileName() {
        return missionsFileName;
    }

    public String getMissionsOutput() {
        return missionsOutput;
    }

    public String getSpaceshipsFileName() {
        return spaceshipsFileName;
    }

    public Integer getFileRefreshDate() {
        return fileRefreshDate;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }
}
