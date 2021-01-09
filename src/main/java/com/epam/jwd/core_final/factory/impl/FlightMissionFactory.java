package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.time.LocalDate;
import java.util.List;

public class FlightMissionFactory implements EntityFactory {
    private static FlightMissionFactory instance;
    private FlightMissionFactory(){
    }
    public static FlightMissionFactory getInstance(){
        if(instance == null){
            instance = new FlightMissionFactory();
        }
        return instance;
    }

    @Override
    public BaseEntity create(Object... args) {
        return new FlightMission((String)args[0], (LocalDate)args[1], (LocalDate)args[2], (Long)args[3],
                (Spaceship)args[4], (List<CrewMember>)args[5], (MissionResult)args[6]);
    }
}
