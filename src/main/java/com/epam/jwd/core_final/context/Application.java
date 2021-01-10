package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.context.impl.NassaMenu;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public interface Application {
    static ApplicationMenu start() throws InvalidStateException {
        ApplicationMenu applicationMenu = NassaMenu.getInstance();
        final Supplier<ApplicationContext> applicationContextSupplier = applicationMenu::getApplicationContext; // todo
        NassaContext nassaContext = NassaContext.getInstance();
        nassaContext.init();
        CrewServiceImpl crewService = CrewServiceImpl.getInstance();
        SpaceshipServiceImpl spaceshipService = SpaceshipServiceImpl.getInstance();
        //System.out.println(crewService.findAllCrewMembers());
        //System.out.println(spaceshipService.findAllSpaceships());
        //applicationMenu.printAvailableOptions();
        //applicationMenu.handleUserInput(null);
        return applicationContextSupplier::get;
    }
}
