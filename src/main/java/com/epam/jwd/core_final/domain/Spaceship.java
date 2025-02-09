package com.epam.jwd.core_final.domain;

import java.util.Map;

/**
 * crew {@link java.util.Map<Role, Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class Spaceship extends AbstractBaseEntity {
    private Map<Role, Short> crew;
    private Long flightDistance;
    private boolean isReadyForNextMissions = true;

    public Spaceship(Map<Role, Short> crew, String name, Long flightDistance, Long id) {
        this.crew = crew;
        this.flightDistance = flightDistance;
        this.name = name;
        this.id = id;
    }

    public Spaceship(String name, Map<Role, Short> crew, Long flightDistance) {
        this.crew = crew;
        this.flightDistance = flightDistance;
        this.name = name;
    }

    public Spaceship(String name, Long flightDistance) {
        this.name = name;
        this.flightDistance = flightDistance;
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public void setCrew(Map<Role, Short> crew) {
        this.crew = crew;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public void setFlightDistance(Long flightDistance) {
        this.flightDistance = flightDistance;
    }

    public boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void setReadyForNextMissions(boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

    public String toString(){
        return "Spaceship " + this.name + " has id = " + this.id
                + ", can fly " + this.flightDistance
                + " and has a crew: " + this.crew;
    }
}
