package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.service.SpaceshipService;

import java.util.List;
import java.util.Optional;

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

    private final ApplicationContext applicationContext;

    @Override
    public List<Spaceship> findAllSpaceships() {
        return (List<Spaceship>)applicationContext.retrieveBaseEntityList(Spaceship.class);
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(Criteria<? extends Spaceship> criteria) {
        return null;
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(Criteria<? extends Spaceship> criteria) {
        return Optional.empty();
    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        return null;
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship) throws RuntimeException {

    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws RuntimeException {
        return null;
    }
}
