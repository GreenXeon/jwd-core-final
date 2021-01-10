package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.CrewService;

import java.util.List;
import java.util.Optional;

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

    private final ApplicationContext applicationContext;


    @Override
    public List<CrewMember> findAllCrewMembers() {
        return (List<CrewMember>)applicationContext.retrieveBaseEntityList(CrewMember.class);
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(Criteria<? extends CrewMember> criteria) {
        return null;
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(Criteria<? extends CrewMember> criteria) {
        return Optional.empty();
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

    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws RuntimeException {
        List<CrewMember> crewMembers = findAllCrewMembers();
        try {
            if (crewMembers.contains(crewMember)) {
                throw new Exception("Crewmember already exists");
            }
            else {
                crewMembers.add(crewMember);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return crewMember;
    }
}
