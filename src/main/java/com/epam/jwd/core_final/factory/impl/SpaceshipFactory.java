package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SpaceshipFactory implements EntityFactory {
    private static SpaceshipFactory instance;
    private SpaceshipFactory(){
    }
    public static SpaceshipFactory getInstance(){
        if(instance == null){
            instance = new SpaceshipFactory();
        }
        return instance;
    }

    @Override
    public Spaceship create(Object... args) {
        return new Spaceship((Map<Role, Short>) args[0], (String) args[1], (Long) args[2], (Long) args[3]);
    }
}
