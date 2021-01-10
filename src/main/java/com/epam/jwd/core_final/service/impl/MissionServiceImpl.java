package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.MissionCreationException;
import com.epam.jwd.core_final.service.MissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MissionServiceImpl implements MissionService {
    private static MissionServiceImpl instance;
    private MissionServiceImpl(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }
    public static MissionServiceImpl getInstance(){
        if (instance == null){
            instance = new MissionServiceImpl(NassaContext.getInstance());
        }
        return instance;
    }

    private final Logger logger = LoggerFactory.getLogger(MissionServiceImpl.class);

    private final ApplicationContext applicationContext;

    @Override
    public List<FlightMission> findAllMissions() {
        return (List<FlightMission>)applicationContext.retrieveBaseEntityList(FlightMission.class);
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(FlightMissionCriteria criteria) {
        return findAllMissions()
                .stream()
                .filter(member -> member.getMissionName().equals(criteria.getMissionName()) || criteria.getMissionName() == null)
                .filter(member -> member.getStartDate().equals(criteria.getStartDate()) || criteria.getStartDate() == null)
                .filter(member -> member.getEndDate().equals(criteria.getEndDate()) || criteria.getEndDate() == null)
                .filter(member -> member.getDistance().equals(criteria.getDistance()) || criteria.getDistance() == null)
                .filter(member -> member.getAssignedSpaceShift().equals(criteria.getAssignedSpaceShift()) || criteria.getAssignedSpaceShift() == null)
                .filter(member -> member.getAssignedCrew().equals(criteria.getAssignedCrew()) || criteria.getAssignedCrew() == null)
                .filter(member -> member.getMissionResult() == (criteria.getMissionResult()) || criteria.getMissionResult() == null)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(FlightMissionCriteria criteria) {
        return findAllMissions()
                .stream()
                .filter(member -> member.getMissionName().equals(criteria.getMissionName()) || criteria.getMissionName() == null)
                .filter(member -> member.getStartDate().equals(criteria.getStartDate()) || criteria.getStartDate() == null)
                .filter(member -> member.getEndDate().equals(criteria.getEndDate()) || criteria.getEndDate() == null)
                .filter(member -> member.getDistance().equals(criteria.getDistance()) || criteria.getDistance() == null)
                .filter(member -> member.getAssignedSpaceShift().equals(criteria.getAssignedSpaceShift()) || criteria.getAssignedSpaceShift() == null)
                .filter(member -> member.getAssignedCrew().equals(criteria.getAssignedCrew()) || criteria.getAssignedCrew() == null)
                .filter(member -> member.getMissionResult() == (criteria.getMissionResult()) || criteria.getMissionResult() == null)
                .findAny();
    }

    @Override
    public FlightMission updateSpaceshipDetails(FlightMission flightMission) {
        return null;
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) {
        List<FlightMission> flightMissions = findAllMissions();
        try {
            if (flightMissions.contains(flightMission)) {
                throw new MissionCreationException("Crewmember already exists");
            }
            else {
                applicationContext.retrieveBaseEntityList(FlightMission.class).add(flightMission);
            }
        }
        catch (MissionCreationException e){
            logger.error(e.getMessage());
        }
        return flightMission;
    }
}
