package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.context.impl.NassaMenu;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
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
        /*CrewServiceImpl crewService = CrewServiceImpl.getInstance();
        MissionServiceImpl missionService = MissionServiceImpl.getInstance();
        SpaceshipServiceImpl spaceshipService = SpaceshipServiceImpl.getInstance();
        System.out.println(crewService.findAllCrewMembers());
        System.out.println(NassaContext.getInstance().retrieveBaseEntityList(CrewMember.class));
        System.out.println(crewService.findAllCrewMembersByCriteria(new CrewMemberCriteria
            .CriteriaBuilder()
            .hasRole(Role.MISSION_SPECIALIST)
            .build()
            ));
        System.out.println(crewService.findCrewMemberByCriteria(new CrewMemberCriteria
                .CriteriaBuilder()
                .hasRole(Role.PILOT)
                .build()
        ).toString());
        System.out.println(missionService.findAllMissions());
        System.out.println(missionService.findAllMissionsByCriteria(new FlightMissionCriteria
                .CriteriaBuilder()
                .hasMissionName("Moon colonising")
                .build()
        ));

         */
        applicationMenu.printAvailableOptions();
        return applicationContextSupplier::get;
    }
}
