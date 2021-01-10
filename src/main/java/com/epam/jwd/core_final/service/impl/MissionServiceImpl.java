package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.service.MissionService;

import java.util.List;
import java.util.Optional;

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

    private final ApplicationContext applicationContext;

    @Override
    public List<FlightMission> findAllMissions() {
        return (List<FlightMission>)applicationContext.retrieveBaseEntityList(FlightMission.class);
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(Criteria<? extends FlightMission> criteria) {
        return null;
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(Criteria<? extends FlightMission> criteria) {
        return Optional.empty();
    }

    @Override
    public FlightMission updateSpaceshipDetails(FlightMission flightMission) {
        return null;
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) {
        return null;
    }
}
