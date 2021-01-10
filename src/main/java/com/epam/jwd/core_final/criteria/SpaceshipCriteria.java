package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.Map;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {
    private Map<Role, Short> crew;
    private Long flightDistance;
    private boolean isReadyForNextMissions = true;

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public Long getFlightDistance() {
        return flightDistance;
    }

    public boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public static class CriteriaBuilder{
        private final SpaceshipCriteria spaceshipCriteria;

        public CriteriaBuilder(){
            this.spaceshipCriteria = new SpaceshipCriteria();
        }

        public CriteriaBuilder hasCrew(Map<Role, Short> crew){
            spaceshipCriteria.crew = crew;
            return this;
        }

        public CriteriaBuilder hasRole(Long flightDistance){
            spaceshipCriteria.flightDistance = flightDistance;
            return this;
        }

        public CriteriaBuilder hasReadyForNextMissions(boolean isReadyForNextMissions){
            spaceshipCriteria.isReadyForNextMissions = isReadyForNextMissions;
            return this;
        }

        public SpaceshipCriteria build(){
            return spaceshipCriteria;
        }
    }
}
