package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Spaceship;

import java.time.LocalDate;
import java.util.List;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {
    private String missionName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long distance;
    private Spaceship assignedSpaceShift;
    private List<CrewMember> assignedCrew;
    private MissionResult missionResult;

    public String getMissionName() {
        return missionName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getDistance() {
        return distance;
    }

    public Spaceship getAssignedSpaceShift() {
        return assignedSpaceShift;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    public static class CriteriaBuilder{
        private final FlightMissionCriteria flightMissionCriteria;

        public CriteriaBuilder(){
            this.flightMissionCriteria = new FlightMissionCriteria();
        }

        public CriteriaBuilder hasMissionName(String missionName){
            flightMissionCriteria.missionName = missionName;
            return this;
        }

        public CriteriaBuilder hasStartDate(LocalDate startDate){
            flightMissionCriteria.startDate = startDate;
            return this;
        }

        public CriteriaBuilder hasEndDate(LocalDate endDate){
            flightMissionCriteria.endDate = endDate;
            return this;
        }

        public CriteriaBuilder hasDistance(Long distance){
            flightMissionCriteria.distance = distance;
            return this;
        }

        public CriteriaBuilder hasSpaceship(Spaceship assignedSpaceShift){
            flightMissionCriteria.assignedSpaceShift = assignedSpaceShift;
            return this;
        }

        public CriteriaBuilder hasCrew(List<CrewMember> assignedCrew){
            flightMissionCriteria.assignedCrew = assignedCrew;
            return this;
        }

        public CriteriaBuilder hasResult(MissionResult missionResult){
            flightMissionCriteria.missionResult = missionResult;
            return this;
        }

        public FlightMissionCriteria build(){
            return flightMissionCriteria;
        }
    }
}
