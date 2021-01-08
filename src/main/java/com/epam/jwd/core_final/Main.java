package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    public static void main(String[] args) throws InvalidStateException {
        CrewMemberFactory crewMemberFactory = new CrewMemberFactory();
        CrewMember sample = crewMemberFactory.create(Role.PILOT, Rank.CAPTAIN, true);
        System.out.println(Rank.resolveRankById(4));
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("lol");
        //Application.start();
    }
}