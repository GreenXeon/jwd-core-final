package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import com.sun.jdi.LocalVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

// todo
public class NassaContext implements ApplicationContext {
    private static NassaContext instance = new NassaContext();
    private NassaContext(){
    }
    public static NassaContext getInstance(){
        return instance;
    }

    private final Logger logger = LoggerFactory.getLogger(NassaContext.class);
    private ApplicationProperties applicationProperties;

    // no getters/setters for them
    private Collection<CrewMember> crewMembers = new ArrayList<>();
    private Collection<Spaceship> spaceships = new ArrayList<>();
    private Collection<FlightMission> missions = new ArrayList<>();

    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        Collection<T> retrieveCollection = null;
        if (tClass.equals(CrewMember.class)){
            retrieveCollection = (Collection<T>)crewMembers;
        }
        if (tClass.equals(Spaceship.class)){
            retrieveCollection = (Collection<T>)spaceships;
        }
        if (tClass.equals(FlightMission.class)){
            retrieveCollection = (Collection<T>)missions;
        }
        return retrieveCollection;
    }

    /**
     * You have to read input files, populate collections
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {
        PropertyReaderUtil.loadProperties();
        applicationProperties = new ApplicationProperties();
        crewMembers = readCrewFromFile();
        spaceships = readSpaceshipsFromFile();
        missions = generateMissions();
        if (crewMembers.isEmpty() || spaceships.isEmpty() || missions.isEmpty()){
            throw new InvalidStateException("Collection is empty");
        }
    }

    private Collection<CrewMember> readCrewFromFile(){
        ArrayList<CrewMember> inputInfo= new ArrayList<>();
        Role role;
        Rank rank;
        String[] info;
        Long id = 0L;
        try {
            Path path = Paths.get("src", "main", "resources", applicationProperties.getInputRootDir(),
                    applicationProperties.getCrewFileName());
            Scanner scan = new Scanner(path.toFile().getAbsoluteFile());
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (!line.contains("#")){
                    String[] crewInfoString = line.split(";");
                    for (String crew : crewInfoString){
                        info = crew.split(",");
                        role = Role.resolveRoleById(Integer.parseInt(info[0]));
                        rank = Rank.resolveRankById(Integer.parseInt(info[2]));
                        inputInfo.add(CrewMemberFactory.getInstance().create(role, info[1],
                                rank, id));
                        id++;
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return inputInfo;
    }

    private Collection<Spaceship> readSpaceshipsFromFile(){
        ArrayList<Spaceship> inputInfo= new ArrayList<>();
        Map<Role, Short> crewMap;
        String[] crew, mapInfo, spaceshipInfoString;
        String spaceshipName, line;
        Long distance;
        Long id = 0L;
        try{
            Path path = Paths.get("src", "main", "resources", applicationProperties.getInputRootDir(),
                    applicationProperties.getSpaceshipsFileName());
            Scanner scan = new Scanner(path.toFile().getAbsoluteFile());
            while(scan.hasNextLine()){
                mapInfo = null;
                line = scan.nextLine();
                if (!line.contains("#")){
                    crewMap = new HashMap<>();
                    spaceshipInfoString = line.split(";");
                    spaceshipName = spaceshipInfoString[0];
                    distance = Long.parseLong(spaceshipInfoString[1]);
                    crew = spaceshipInfoString[2].split("[{]")[1]
                        .split("}")[0]
                        .split(",");
                    for (String s : crew){
                        mapInfo = s.split(":");
                        Role role = Role.resolveRoleById(Integer.parseInt(mapInfo[0]));
                        Short quantity = Short.parseShort(mapInfo[1]);
                        crewMap.put(role, quantity);
                    }
                    Spaceship spaceship = SpaceshipFactory.getInstance().create(crewMap, spaceshipName, distance, id);
                    inputInfo.add(spaceship);
                    id++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputInfo;
    }

    private Collection<FlightMission> generateMissions(){
       ArrayList<FlightMission> missions = new ArrayList<>();
       ArrayList<CrewMember> crew;
       ArrayList<Spaceship> ships;
       ArrayList<String> missionNames = new ArrayList<>();
       Long id = 0L;
       int numOfMissions = 8;
        try{
            Path path = Paths.get("src", "main", "resources", applicationProperties.getInputRootDir(),
                    applicationProperties.getMissionsFileName());
            Scanner scan = new Scanner(path.toFile().getAbsoluteFile());
            String line;
            while(scan.hasNextLine()){
                line = scan.nextLine();
                missionNames.add(line);
            }
            if (missionNames.isEmpty()){
                throw new InvalidStateException("List of missions.json is empty");
            }
        }
        catch (IOException | NullPointerException | InvalidStateException e){
            e.printStackTrace();
        }
        FlightMissionFactory flightMissionFactory = FlightMissionFactory.getInstance();
        crew = new ArrayList<>(crewMembers);
        ships = new ArrayList<>(spaceships);
        List<String> existingNames = new ArrayList<>();
        String missionName;
        for (int i = 0; i < numOfMissions; i++){
            LocalDate[] dates = randomDates();
            LocalDate beginDate = dates[0];
            LocalDate endDate = dates[1];
            long distance = ThreadLocalRandom.current().nextLong(256042, 1395960);
            Spaceship spaceship;
            do {
                spaceship = ships.get(ThreadLocalRandom.current().nextInt(spaceships.size() - 1));
            } while (!spaceship.isReadyForNextMissions());
            do {
                missionName = missionNames.get(ThreadLocalRandom.current().nextInt(missionNames.size() - 1));
            } while (existingNames.contains(missionName));
            existingNames.add(missionName);
            List<CrewMember> crewMemberList = new ArrayList<>();
            Map<Role, Short> shipCrewInfo = spaceship.getCrew();
            for (Role role : shipCrewInfo.keySet()){
                List<CrewMember> suitableMembers = crew.stream()
                        .filter(CrewMember::isReadyForNextMissions)
                        .filter(member -> member.getRole() == role)
                        .collect(Collectors.toList());
                //logger.info(String.valueOf(suitableMembers.size()));
                for (int j = 0; j < shipCrewInfo.get(role); j++){
                    CrewMember assignedMember = suitableMembers.get(ThreadLocalRandom.current().nextInt(suitableMembers.size()));
                    crewMemberList.add(assignedMember);
                }
            }
            MissionResult missionResult;
            do {
                missionResult = randomResult();
            } while (!checkDateAndResult(beginDate, endDate, missionResult));
            if (missionResult == MissionResult.FAILED){
                for (CrewMember crewMember : crewMemberList){
                    crewMember.setReadyForNextMissions(false);
                }
                spaceship.setReadyForNextMissions(false);
            }
            FlightMission flightMission = (FlightMission)flightMissionFactory.create(id, missionName, beginDate, endDate, distance, spaceship, crewMemberList, missionResult);
            missions.add(flightMission);
            id++;
            //logger.info(flightMission.toString());
        }
        return missions;
    }

    private LocalDate[] randomDates(){
        LocalDate[] localDates = new LocalDate[2];
        long minDay = LocalDate.of(2018, 5, 13).toEpochDay();
        long maxDay = LocalDate.of(2022, 10, 27).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate beginDate = LocalDate.ofEpochDay(randomDay);
        LocalDate endDate = LocalDate.of(2018, 5, 13);
        while (beginDate.isAfter(endDate)) {
            randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            endDate = LocalDate.ofEpochDay(randomDay);
        }
        localDates[0] = beginDate;
        localDates[1] = endDate;
        return localDates;
    }

    private MissionResult randomResult(){
        List<MissionResult> missionResults = Arrays.asList(MissionResult.values());
        int size = missionResults.size();
        return missionResults.get(ThreadLocalRandom.current().nextInt(size));
    }

    private boolean checkDateAndResult(LocalDate beginDate, LocalDate endDate, MissionResult missionResult){
        LocalDate now = LocalDate.now();
        if (now.isAfter(beginDate) && now.isBefore(endDate) && (missionResult == MissionResult.PLANNED
                || missionResult == MissionResult.COMPLETED)){
            return false;
        }
        if (now.isAfter(endDate) && (missionResult == MissionResult.PLANNED
                || missionResult == MissionResult.IN_PROGRESS)){
            return false;
        }
        if (now.isBefore(beginDate) && (missionResult == MissionResult.FAILED
                || missionResult == MissionResult.IN_PROGRESS || missionResult == MissionResult.COMPLETED)){
            return false;
        }
        return true;
    }
}
