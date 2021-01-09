package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.util.PropertyReaderUtil;

public class Main {

    public static void main(String[] args) throws InvalidStateException {
        CrewMemberFactory crewMemberFactory = CrewMemberFactory.getInstance();
        CrewMember sample = crewMemberFactory.create(Role.PILOT, "Zakhar", Rank.CAPTAIN);
        //PropertyReaderUtil.loadProperties();
        //System.out.println(PropertyReaderUtil.getProperties().getProperty("missionsFileName"));
        Application.start();
    }
}