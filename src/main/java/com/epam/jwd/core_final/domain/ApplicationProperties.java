package com.epam.jwd.core_final.domain;

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
public class ApplicationProperties {
    //todo
    private String inputRootDir;
    private String outputRootDir;
    private String crewFileName;
    private String missionsFileName;
    private String spaceshipsFileName;
    private Integer fileRefreshDate;
    private String dateTimeFormat;

    public String getInputRootDir() {
        return inputRootDir;
    }

    public void setInputRootDir(String inputRootDir) {
        this.inputRootDir = inputRootDir;
    }

    public String getOutputRootDir() {
        return outputRootDir;
    }

    public void setOutputRootDir(String outputRootDir) {
        this.outputRootDir = outputRootDir;
    }

    public String getCrewFileName() {
        return crewFileName;
    }

    public void setCrewFileName(String crewFileName) {
        this.crewFileName = crewFileName;
    }

    public String getMissionsFileName() {
        return missionsFileName;
    }

    public void setMissionsFileName(String missionsFileName) {
        this.missionsFileName = missionsFileName;
    }

    public String getSpaceshipsFileName() {
        return spaceshipsFileName;
    }

    public void setSpaceshipsFileName(String spaceshipsFileName) {
        this.spaceshipsFileName = spaceshipsFileName;
    }

    public Integer getFileRefreshDate() {
        return fileRefreshDate;
    }

    public void setFileRefreshDate(Integer fileRefreshDate) {
        this.fileRefreshDate = fileRefreshDate;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }
}
