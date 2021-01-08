package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.util.Map;

public class SpaceshipFactory implements EntityFactory {

    @Override
    public BaseEntity create(Object... args) {
        return new Spaceship((Map<Role, Short>)args[0], (Long)args[1], (boolean)args[2]);
    }
}
