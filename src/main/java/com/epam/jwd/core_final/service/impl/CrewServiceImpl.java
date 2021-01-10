package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.exception.CrewmemberCreationException;
import com.epam.jwd.core_final.exception.EntityAssignException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.CrewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CrewServiceImpl implements CrewService {
    private static CrewServiceImpl instance;
    private CrewServiceImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    public static CrewServiceImpl getInstance(){
        if (instance == null){
            instance = new CrewServiceImpl(NassaContext.getInstance());
        }
        return instance;
    }

    private final Logger logger = LoggerFactory.getLogger(CrewServiceImpl.class);

    private final ApplicationContext applicationContext;

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return (List<CrewMember>)applicationContext.retrieveBaseEntityList(CrewMember.class);
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(CrewMemberCriteria criteria) {
        return findAllCrewMembers()
                .stream()
                .filter(member -> member.getRole().equals(criteria.getRole()) || criteria.getRole() == null)
                .filter(member -> member.getRank().equals(criteria.getRank()) || criteria.getRank() == null)
                .filter(member -> member.isReadyForNextMissions() == (criteria.isReadyForNextMissions()) || !member.isReadyForNextMissions())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(CrewMemberCriteria criteria) {
        return findAllCrewMembers()
                .stream()
                .filter(member -> member.getRole().equals(criteria.getRole()) || criteria.getRole() == null)
                .filter(member -> member.getRank().equals(criteria.getRank()) || criteria.getRank() == null)
                .filter(member -> member.isReadyForNextMissions() == (criteria.isReadyForNextMissions()) || !criteria.isReadyForNextMissions())
                .findAny();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        List<CrewMember> crewMembers = findAllCrewMembers();
        Optional<CrewMember> found = crewMembers
                .stream()
                .filter(s -> s.equals(crewMember))
                .findFirst();
        return found.orElseThrow();
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws RuntimeException {
        List<FlightMission> flightMissions = (List<FlightMission>) applicationContext.retrieveBaseEntityList(FlightMission.class);
        try {
            for (FlightMission mission : flightMissions) {
                if (mission.getAssignedCrew().contains(crewMember)) {
                    throw new EntityAssignException("Crewmember is already assigned");
                }
            }
            crewMember.setReadyForNextMissions(false);
        } catch (EntityAssignException e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws RuntimeException {
        List<CrewMember> crewMembers = findAllCrewMembers();
        try {
            if (crewMembers.contains(crewMember)) {
                throw new CrewmemberCreationException("Crewmember already exists");
            }
            else {
                applicationContext.retrieveBaseEntityList(CrewMember.class).add(crewMember);
            }
        }
        catch (CrewmemberCreationException e){
            logger.error(e.getMessage());
        }
        return crewMember;
    }
}
