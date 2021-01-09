package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

// todo
public class NassaContext implements ApplicationContext {

    private final Logger logger = LoggerFactory.getLogger(NassaContext.class);
    private ApplicationProperties applicationProperties;

    // no getters/setters for them
    private Collection<CrewMember> crewMembers = new ArrayList<>();
    private Collection<Spaceship> spaceships = new ArrayList<>();
    private Collection<FlightMission> missions = new ArrayList<>();

    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {
        return null;
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
        for (CrewMember c : crewMembers){
            System.out.println(c.toString());
        }
        for (Spaceship s : spaceships){
            System.out.println(s.toString());
        }
                //throw new InvalidStateException();
    }

    private Collection<CrewMember> readCrewFromFile(){
        ArrayList<CrewMember> inputInfo= new ArrayList<>();
        Role role;
        Rank rank;
        String[] info;
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
                                rank));
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
                    Spaceship spaceship = SpaceshipFactory.getInstance().create(crewMap, spaceshipName, distance);
                    inputInfo.add(spaceship);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputInfo;
    }
}
