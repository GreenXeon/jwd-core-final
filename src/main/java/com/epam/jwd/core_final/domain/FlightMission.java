package com.epam.jwd.core_final.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

/**
 * Expected fields:
 * <p>
 * missions.json name {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - mission distance
 * assignedSpaceShift {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionResult}
 */
public class FlightMission extends AbstractBaseEntity {
    // todo
    @JsonProperty("Name")
    private String missionName;
    @JsonProperty("StartDate")
    private LocalDate startDate;
    @JsonProperty("EndDate")
    private LocalDate endDate;
    @JsonProperty("Distance")
    private Long distance;
    @JsonProperty("Spaceship")
    private Spaceship assignedSpaceShift;
    @JsonProperty("Crew")
    private List<CrewMember> assignedCrew;
    @JsonProperty("Result")
    private MissionResult missionResult;

    public FlightMission(long id, String missionName, LocalDate startDate, LocalDate endDate, Long distance, Spaceship assignedSpaceShift, List<CrewMember> assignedCrew, MissionResult missionResult) {
        this.id = id;
        this.missionName = missionName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.assignedSpaceShift = assignedSpaceShift;
        this.assignedCrew = assignedCrew;
        this.missionResult = missionResult;
    }

    public FlightMission(String missionName, LocalDate startDate, LocalDate endDate, Long distance, Spaceship assignedSpaceShift, MissionResult missionResult) {
        this.missionName = missionName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.assignedSpaceShift = assignedSpaceShift;
        this.missionResult = missionResult;
    }

    public FlightMission(){

    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Spaceship getAssignedSpaceShift() {
        return assignedSpaceShift;
    }

    public void setAssignedSpaceShift(Spaceship assignedSpaceShift) {
        this.assignedSpaceShift = assignedSpaceShift;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public void setAssignedCrew(List<CrewMember> assignedCrew) {
        this.assignedCrew = assignedCrew;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    public void setMissionResult(MissionResult missionResult) {
        this.missionResult = missionResult;
    }

    public String toString(){
        return id + " - " + missionName + " :\nStart date - " + startDate + "\nEnd date - " + endDate +
                "\nSpaceship - " + assignedSpaceShift.name + "\nDistance - " + distance +
                "\nCrew - " + assignedCrew +
                "\nMission result - " + missionResult + "\n";
    }
}
