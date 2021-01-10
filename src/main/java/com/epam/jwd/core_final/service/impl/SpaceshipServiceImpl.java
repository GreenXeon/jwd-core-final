package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.EntityAssignException;
import com.epam.jwd.core_final.exception.ShipCreationException;
import com.epam.jwd.core_final.service.SpaceshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpaceshipServiceImpl implements SpaceshipService {
    private static SpaceshipServiceImpl instance;
    private SpaceshipServiceImpl(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }
    public static SpaceshipServiceImpl getInstance(){
        if (instance == null){
            instance = new SpaceshipServiceImpl(NassaContext.getInstance());
        }
        return instance;
    }

    private final Logger logger = LoggerFactory.getLogger(SpaceshipServiceImpl.class);

    private final ApplicationContext applicationContext;

    @Override
    public List<Spaceship> findAllSpaceships() {
        return (List<Spaceship>)applicationContext.retrieveBaseEntityList(Spaceship.class);
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(SpaceshipCriteria criteria) {
        return findAllSpaceships()
                .stream()
                .filter(member -> member.getCrew().equals(criteria.getCrew()) || criteria.getCrew() == null)
                .filter(member -> member.getFlightDistance().equals(criteria.getFlightDistance()) || criteria.getFlightDistance() == null)
                .filter(member -> member.isReadyForNextMissions() == (criteria.isReadyForNextMissions()) || !member.isReadyForNextMissions())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(SpaceshipCriteria criteria) {
        return findAllSpaceships()
                .stream()
                .filter(member -> member.getCrew().equals(criteria.getCrew()) || criteria.getCrew() == null)
                .filter(member -> member.getFlightDistance().equals(criteria.getFlightDistance()) || criteria.getFlightDistance() == null)
                .filter(member -> member.isReadyForNextMissions() == (criteria.isReadyForNextMissions()) || !member.isReadyForNextMissions())
                .findAny();
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship changing, Spaceship changer) {
        changing.setCrew(changer.getCrew());
        changing.setFlightDistance(changer.getFlightDistance());
        changing.setReadyForNextMissions(changer.isReadyForNextMissions());
        return changing;
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship) throws RuntimeException {
        List<FlightMission> flightMissions = (List<FlightMission>) applicationContext.retrieveBaseEntityList(FlightMission.class);
        try {
            for (FlightMission mission : flightMissions) {
                if (mission.getAssignedSpaceShift().equals(spaceship)) {
                    throw new EntityAssignException("Spaceship is already assigned");
                }
            }
            spaceship.setReadyForNextMissions(false);
        } catch (EntityAssignException e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws RuntimeException {
        List<Spaceship> crewMembers = findAllSpaceships();
        try {
            if (crewMembers.contains(spaceship)) {
                throw new ShipCreationException("Spaceship already exists");
            }
            else {
                crewMembers.add(spaceship);
            }
        }
        catch (ShipCreationException e){
            logger.error(e.getMessage());
        }
        return spaceship;
    }
}
