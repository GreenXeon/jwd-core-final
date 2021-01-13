package com.epam.jwd.core_final.domain;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class CrewMember extends AbstractBaseEntity {
    // todo
    private Role role;
    private Rank rank;
    private boolean isReadyForNextMissions = true;

    public CrewMember(Role role, String name, Rank rank, Long id){
        this.role = role;
        this.rank = rank;
        this.name = name;
        this.id = id;
    }

    public CrewMember(String name, Role role, Rank rank) {
        this.role = role;
        this.rank = rank;
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    public void setReadyForNextMissions(boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

    public String toString(){
        return "Crewmember " + this.name
                + " has id = " + this.id + ", " + this.role + " role and "
                + this.rank + " rank. Ready for mission: " + this.isReadyForNextMissions;
    }
}
