package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NassaMenu implements ApplicationMenu {
    private static NassaMenu instance;
    private NassaMenu(){
    }
    public static NassaMenu getInstance(){
        if (instance == null){
            instance = new NassaMenu();
        }
        return instance;
    }

    private final Logger logger = LoggerFactory.getLogger(NassaMenu.class);

    @Override
    public ApplicationContext getApplicationContext() {
        return NassaContext.getInstance();
    }

    @Override
    public void printAvailableOptions() {
        System.out.println("Choose an action:");
        System.out.println("1. Get info about crewmembers\n2. Get info about spaceships\n" +
                "3. Get info about missions\n4. Update info about crewmembers\n5. Update info about spaceships\n" +
                "6. Update info about missions\n7. Choose missions for output(print id's with ',' delimiter)\n8. Quit app");
        handleUserInput(null);
    }

    @Override
    public void handleUserInput(Object o) {
        Scanner scanner = new Scanner(System.in);
        CrewServiceImpl crewService = CrewServiceImpl.getInstance();
        SpaceshipServiceImpl spaceshipService = SpaceshipServiceImpl.getInstance();
        MissionServiceImpl missionService = MissionServiceImpl.getInstance();
        ApplicationProperties applicationProperties = new ApplicationProperties();
        int operation = 0;
        try {
            operation = scanner.nextInt();
        } catch (NumberFormatException e){
            logger.error(e.getMessage());
        }
        switch (operation){
            case (1):
                List<CrewMember> crewMembers = crewService.findAllCrewMembers();
                for (CrewMember crewMember : crewMembers){
                    System.out.println(crewMember);
                }
                break;
            case (2):
                List<Spaceship> spaceships = spaceshipService.findAllSpaceships();
                for (Spaceship spaceship : spaceships){
                    System.out.println(spaceship);
                }
                break;
            case (3):
                List<FlightMission> flightMissions = missionService.findAllMissions();
                for (FlightMission mission : flightMissions){
                    System.out.println(mission);
                }
                break;
            case (7):
                String toOutput = scanner.nextLine();
                toOutput = scanner.nextLine();
                toOutput = toOutput.trim();
                ArrayList<FlightMission> missionsToOutput = new ArrayList<>();
                ArrayList<FlightMission> missions = (ArrayList<FlightMission>)getApplicationContext().retrieveBaseEntityList(FlightMission.class);
                String[] ids = toOutput.split(",");
                try {
                    for (String id : ids){
                        FlightMission flightMission = missions.get(Integer.parseInt(id));
                        missionsToOutput.add(flightMission);
                    }
                } catch (Exception e){
                    logger.error(e.getMessage());
                }
                Path path = Paths.get("src", "main", "resources", applicationProperties.getOutputRootDir(),
                        applicationProperties.getMissionsOutput());
                ObjectMapper mapper = new ObjectMapper();
                try {
                    mapper.writeValue(new File(String.valueOf(path)), missionsToOutput);
                    System.out.println("Missions are serialized successfully!");
                } catch (IOException e){
                    logger.error(e.getMessage());
                }
                break;
            case (8):
                System.exit(0);
                break;
        }
        printAvailableOptions();
    }
}
