package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class NassaMenu<T> implements ApplicationMenu {
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
        ArrayList<CrewMember> crewMembers = (ArrayList<CrewMember>) crewService.findAllCrewMembers();
        ArrayList<Spaceship> spaceships = (ArrayList<Spaceship>) spaceshipService.findAllSpaceships();
        ArrayList<FlightMission> flightMissions = (ArrayList<FlightMission>) missionService.findAllMissions();
        CrewMember crewForChange, crewChangeOn;
        FlightMission missionForChange, missionChangeOn;
        Spaceship shipForChange, shipChangeOn;
        int operation = 0, index = 0, changingId = 0, changerId = 0;
        int[] indexes = new int[2];
        try {
            operation = scanner.nextInt();
        } catch (NumberFormatException e){
            logger.error(e.getMessage());
        }
        switch (operation){
            case (1):
                for (CrewMember crewMember : crewMembers){
                    System.out.println(crewMember);
                }
                break;
            case (2):
                for (Spaceship spaceship : spaceships){
                    System.out.println(spaceship);
                }
                break;
            case (3):
                for (FlightMission mission : flightMissions){
                    System.out.println(mission);
                }
                break;
            case (4):
                indexes = retrievingIndexes(scanner);
                changingId =indexes[0];
                changerId = indexes[1];
                try{
                    if ((changingId >= crewService.findAllCrewMembers().size() && changingId < 0)
                    || (changerId >= crewService.findAllCrewMembers().size() && changerId < 0)){
                        throw new Exception("Wrong index of entity list");
                    }
                } catch (Exception e){
                    logger.error(e.getMessage());
                }
                crewForChange = crewService.findAllCrewMembers().get(changingId);
                crewChangeOn = crewService.findAllCrewMembers().get(changerId);
                CrewMember changedMember = crewService.updateCrewMemberDetails(crewForChange, crewChangeOn);
                crewMembers.set(changingId, changedMember);
                break;
            case (5):
                indexes = retrievingIndexes(scanner);
                changingId =indexes[0];
                changerId = indexes[1];
                try{
                    if ((changingId >= spaceshipService.findAllSpaceships().size() && changingId < 0)
                            || (changerId >= spaceshipService.findAllSpaceships().size() && changerId < 0)){
                        throw new Exception("Wrong index of entity list");
                    }
                } catch (Exception e){
                    logger.error(e.getMessage());
                }
                shipForChange = spaceshipService.findAllSpaceships().get(changingId);
                shipChangeOn = spaceshipService.findAllSpaceships().get(changerId);
                Spaceship changedShip = spaceshipService.updateSpaceshipDetails(shipForChange, shipChangeOn);
                spaceships.set(changingId, changedShip);
                break;
            case (6):
                indexes = retrievingIndexes(scanner);
                changingId =indexes[0];
                changerId = indexes[1];
                try{
                    if ((changingId >= missionService.findAllMissions().size() && changingId < 0)
                            || (changerId >= missionService.findAllMissions().size() && changerId < 0)){
                        throw new Exception("Wrong index of entity list");
                    }
                } catch (Exception e){
                    logger.error(e.getMessage());
                }
                missionForChange = missionService.findAllMissions().get(changingId);
                missionChangeOn = missionService.findAllMissions().get(changerId);
                FlightMission changedMission = missionService.updateMissionDetails(missionForChange, missionChangeOn);
                flightMissions.set(changingId, changedMission);
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
            default:
                break;
        }
        printAvailableOptions();
    }

    private int[] retrievingIndexes(Scanner scanner){
        int[] index = new int[2];
        System.out.println("Enter index of entity you wanna change: ");
        try {
            index[0] = scanner.nextInt();
        } catch (NumberFormatException e){
            logger.error(e.getMessage());
        }
        System.out.println("Enter index of entity to change on: ");
        try {
            index[1] = scanner.nextInt();
        } catch (NumberFormatException e){
            logger.error(e.getMessage());
        }
        return index;
    }
}
