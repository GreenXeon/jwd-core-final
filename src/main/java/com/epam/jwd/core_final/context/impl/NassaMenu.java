package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
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

    private CrewServiceImpl crewService = CrewServiceImpl.getInstance();
    private SpaceshipServiceImpl spaceshipService = SpaceshipServiceImpl.getInstance();
    private MissionServiceImpl missionService = MissionServiceImpl.getInstance();

    private Scanner scanner = new Scanner(System.in);

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
        ArrayList<CrewMember> crewMembers = (ArrayList<CrewMember>) crewService.findAllCrewMembers();
        ArrayList<Spaceship> spaceships = (ArrayList<Spaceship>) spaceshipService.findAllSpaceships();
        ArrayList<FlightMission> flightMissions = (ArrayList<FlightMission>) missionService.findAllMissions();
        ApplicationProperties applicationProperties = new ApplicationProperties();
        CrewMember crewForChange, crewChangeOn = null;
        FlightMission missionForChange, missionChangeOn;
        Spaceship shipForChange, shipChangeOn;
        int operation = 0, index = 0, changingId = 0, changerId = 0, chooseSrc = 0;
        int[] indexes = new int[2];
        try {
            System.out.print("Your choice: ");
            operation = scanner.nextInt();
        } catch (NumberFormatException | InputMismatchException e){
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
                System.out.println("Create new member(0) or take info from existing?(1)");
                try {
                    chooseSrc = scanner.nextInt();
                    if (chooseSrc > 1 || chooseSrc < 0){
                        throw new Exception("Wrong source number");
                    }
                    indexes = retrievingIndexes(scanner, chooseSrc);
                    changingId = indexes[0];
                    changerId = indexes[1];
                    crewForChange = crewService.findAllCrewMembers().get(changingId);
                    if (chooseSrc == 0){
                        crewChangeOn = createMember();
                    } else {if (chooseSrc == 1) {
                        crewChangeOn = crewService.findAllCrewMembers().get(changerId);
                    } else {
                        throw new Exception("Wrong source number");
                    }
                    }
                    CrewMember changedMember = crewService.updateCrewMemberDetails(crewForChange, crewChangeOn);
                    crewMembers.set(changingId, changedMember);
                    System.out.println("Crewmember was successfully changed!");
                } catch (Exception e){
                    logger.error(e.getMessage());
                }
                break;
            case (5):
                System.out.println("Create new spaceship(0) or take info from existing?(1)");
                try{
                    chooseSrc = scanner.nextInt();
                    if (chooseSrc > 1 || chooseSrc < 0){
                        throw new Exception("Wrong source number");
                    }
                    indexes = retrievingIndexes(scanner, chooseSrc);
                    changingId =indexes[0];
                    changerId = indexes[1];
                    shipForChange = spaceshipService.findAllSpaceships().get(changingId);
                    if (chooseSrc == 0){
                        shipChangeOn = createSpaceship();
                        shipChangeOn.setCrew(shipForChange.getCrew());
                    } else {
                        if (chooseSrc == 1) {
                            shipChangeOn = spaceshipService.findAllSpaceships().get(changerId);
                        } else {
                            throw new Exception("Wrong source number");
                        }
                    }
                    Spaceship changedShip = spaceshipService.updateSpaceshipDetails(shipForChange, shipChangeOn);
                    spaceships.set(changingId, changedShip);
                    System.out.println("Spaceship was successfully changed!");
                } catch (Exception e){
                    logger.error(e.getMessage());
                }
                break;
            case (6):
                System.out.println("Create new mission(0) or take info from existing?(1)");
                try{
                    chooseSrc = scanner.nextInt();
                    if (chooseSrc > 1 || chooseSrc < 0){
                        throw new Exception("Wrong source number");
                    }
                    indexes = retrievingIndexes(scanner, chooseSrc);
                    changingId =indexes[0];
                    changerId = indexes[1];
                    missionForChange = missionService.findAllMissions().get(changingId);
                    if (chooseSrc == 0){
                        missionChangeOn = createMission();
                        missionChangeOn.setAssignedCrew(missionForChange.getAssignedCrew());
                    } else {
                        if (chooseSrc == 1) {
                            missionChangeOn = missionService.findAllMissions().get(changerId);
                        } else {
                            throw new Exception("Wrong source number");
                        }
                    }
                    FlightMission changedMission = missionService.updateMissionDetails(missionForChange, missionChangeOn);
                    flightMissions.set(changingId, changedMission);
                    System.out.println("Mission was successfully changed!");
                } catch (Exception e){
                    logger.error(e.getMessage());
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
                Path path = Paths.get("src", "main", "resources", applicationProperties.getOutputRootDir(), applicationProperties.getMissionsOutput());
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

    private int[] retrievingIndexes(Scanner scanner, int src){
        int[] index = new int[2];
        System.out.println("Enter index of entity you wanna change: ");
        try {
            index[0] = scanner.nextInt();
        } catch (NumberFormatException e){
            logger.error(e.getMessage());
        }
        if (src == 1) {
            System.out.println("Enter index of entity to change on: ");
            try {
                index[1] = scanner.nextInt();
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
            }
        }
        return index;
    }

    private CrewMember createMember(){
        CrewMember newMember = null;
        try {
            System.out.println("Enter member's name: ");
            scanner.nextLine();
            String name = scanner.nextLine();
            if (name.equals("")){
                throw new Exception("Name can't be empty");
            }
            System.out.println("Enter member's role id: ");
            int roleId = scanner.nextInt();
            if (roleId > 4 || roleId < 0){
                throw new Exception("Wrong role id!");
            }
            Role role = Role.resolveRoleById(roleId);
            System.out.println("Enter member's rank id: ");
            int rankId = scanner.nextInt();
            if (rankId > 4 || rankId < 0){
                throw new Exception("Wrong rank id!");
            }
            Rank rank = Rank.resolveRankById(rankId);
            newMember = new CrewMember(name, role, rank);
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return newMember;
    }

    private Spaceship createSpaceship(){
        Spaceship newShip = null;
        try {
            System.out.println("Enter ship's name: ");
            scanner.nextLine();
            String name = scanner.nextLine();
            if (name.equals("")){
                throw new Exception("Name can't be empty");
            }
            System.out.println("Enter ships's flying distance: ");
            long distance = scanner.nextLong();
            if (distance <= 0) {
                throw new Exception("Distance can't be equal zero");
            }
            newShip = new Spaceship(name, distance);
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return newShip;
    }

    private FlightMission createMission(){
        FlightMission newMission = null;
        String date;
        int id;
        try {
            System.out.println("Enter mission's name: ");
            scanner.nextLine();
            String name = scanner.nextLine();
            if (name.equals("")){
                throw new Exception("Name can't be empty");
            }
            System.out.println("Enter ships's flying distance: ");
            long distance = scanner.nextLong();
            if (distance <= 0) {
                throw new Exception("Distance can't be equal zero");
            }
            System.out.println("Enter start date in M/d/yyyy format:");
            scanner.nextLine();
            date = scanner.nextLine();
            if (date.equals("")){
                throw new Exception("Date can't be empty");
            }
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
            LocalDate startDate = LocalDate.parse(date, dateFormat);
            System.out.println("Enter end date in M/d/yyyy format:");
            date = scanner.nextLine();
            if (date.equals("")){
                throw new Exception("Date can't be empty");
            }
            LocalDate endDate = LocalDate.parse(date, dateFormat);
            Spaceship newShip;
            do {
                System.out.println("Enter an id of a spaceship to assign:");
                id = scanner.nextInt();
                newShip = spaceshipService.findAllSpaceships().get(id);
            } while (!newShip.isReadyForNextMissions());
            spaceshipService.assignSpaceshipOnMission(newShip);
            System.out.println("Enter mission result's id(0-4):");
            id = scanner.nextInt();
            if (id > 4 || id < 0){
                throw new Exception("Wrong mission result id");
            }
            MissionResult result = MissionResult.values()[id];
            newMission = new FlightMission(name, startDate, endDate, distance, newShip, result);
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return newMission;
    }
}
