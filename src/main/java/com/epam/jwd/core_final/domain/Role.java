package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.UnknownEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Role implements BaseEntity {
    MISSION_SPECIALIST(1L),
    FLIGHT_ENGINEER(2L),
    PILOT(3L),
    COMMANDER(4L);

    private final Long id;

    Role(Long id) {
        this.id = id;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Role.class);

    @Override
    public Long getId() {
        return id;
    }

    /**
     * todo via java.lang.enum methods!
     */
    @Override
    public String getName() {
        return this.name();
    }

    /**
     * todo via java.lang.enum methods!
     * @throws UnknownEntityException if such id does not exist
     */
    public static Role resolveRoleById(int id) {
        Role result = null;
        try {
            for (Role r : Role.values()) {
                if (r.id.equals((long) id))
                    result = r;
            }
            if (result == null)
                throw new UnknownEntityException("Wrong role id");
        }
        catch (Exception e) {
            LOGGER.debug("test");
        }
        return result;
    }
}
