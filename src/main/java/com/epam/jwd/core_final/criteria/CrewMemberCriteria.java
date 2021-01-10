package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {
    private Role role;
    private Rank rank;
    private boolean isReadyForNextMissions = true;

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public static class CriteriaBuilder{
        private final CrewMemberCriteria crewMemberCriteria;

        public CriteriaBuilder(){
            this.crewMemberCriteria = new CrewMemberCriteria();
        }

        public CriteriaBuilder hasRank(Rank rank){
            crewMemberCriteria.rank = rank;
            return this;
        }

        public CriteriaBuilder hasRole(Role role){
            crewMemberCriteria.role = role;
            return this;
        }

        public CriteriaBuilder hasReadyForNextMissions(boolean isReadyForNextMissions){
            crewMemberCriteria.isReadyForNextMissions = isReadyForNextMissions;
            return this;
        }

        public CrewMemberCriteria build(){
            return crewMemberCriteria;
        }
    }
}
