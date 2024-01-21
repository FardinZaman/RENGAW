package com.example.RENGAW.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Entity
public class Team {

    @Id
    @SequenceGenerator(
            name = "team_sequence",
            sequenceName = "team_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "team_sequence"
    )
    private Long id;

    @Column(unique = true)
    private String teamCodeName;

    @NotNull
    @Pattern(regexp = "\\p{Alpha}+(\\s\\p{Alpha}+)*")
    private String currentLead;

    @NotNull
    @Digits(integer = 4, fraction = 0)
    private Long currentLeadId;

    @NotNull
    @Range(min = 3, max = 5)
    private Long teamMemberCount;

    private boolean hasBallisticExpert;
    private boolean hasExplosiveExpert;
    private boolean hasTechnologyExpert;

    @ElementCollection
    private List<String> additionalExpertise;

    private Long totalMissionExecuted;
    private Long totalMissionSuccess;
    private Long totalCasualties;
    private String friendlyFireIncidents;

    public Team() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamCodeName() {
        return teamCodeName;
    }

    public void setTeamCodeName(String teamCodeName) {
        this.teamCodeName = teamCodeName;
    }

    public String getCurrentLead() {
        return currentLead;
    }

    public void setCurrentLead(String currentLead) {
        this.currentLead = currentLead;
    }

    public Long getCurrentLeadId() {
        return currentLeadId;
    }

    public void setCurrentLeadId(Long currentLeadId) {
        this.currentLeadId = currentLeadId;
    }

    public Long getTeamMemberCount() {
        return teamMemberCount;
    }

    public void setTeamMemberCount(Long teamMemberCount) {
        this.teamMemberCount = teamMemberCount;
    }

    public boolean isHasBallisticExpert() {
        return hasBallisticExpert;
    }

    public void setHasBallisticExpert(boolean hasBallisticExpert) {
        this.hasBallisticExpert = hasBallisticExpert;
    }

    public boolean isHasExplosiveExpert() {
        return hasExplosiveExpert;
    }

    public void setHasExplosiveExpert(boolean hasExplosiveExpert) {
        this.hasExplosiveExpert = hasExplosiveExpert;
    }

    public boolean isHasTechnologyExpert() {
        return hasTechnologyExpert;
    }

    public void setHasTechnologyExpert(boolean hasTechnologyExpert) {
        this.hasTechnologyExpert = hasTechnologyExpert;
    }

    public List<String> getAdditionalExpertise() {
        return additionalExpertise;
    }

    public void setAdditionalExpertise(List<String> additionalExpertise) {
        this.additionalExpertise = additionalExpertise;
    }

    public Long getTotalMissionExecuted() {
        return totalMissionExecuted;
    }

    public void setTotalMissionExecuted(Long totalMissionExecuted) {
        this.totalMissionExecuted = totalMissionExecuted;
    }

    public Long getTotalMissionSuccess() {
        return totalMissionSuccess;
    }

    public void setTotalMissionSuccess(Long totalMissionSuccess) {
        this.totalMissionSuccess = totalMissionSuccess;
    }

    public Long getTotalCasualties() {
        return totalCasualties;
    }

    public void setTotalCasualties(Long totalCasualties) {
        this.totalCasualties = totalCasualties;
    }

    public String getFriendlyFireIncidents() {
        return friendlyFireIncidents;
    }

    public void setFriendlyFireIncidents(String friendlyFireIncidents) {
        this.friendlyFireIncidents = friendlyFireIncidents;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", teamCodeName='" + teamCodeName + '\'' +
                ", currentLead='" + currentLead + '\'' +
                ", currentLeadId=" + currentLeadId +
                ", teamMemberCount=" + teamMemberCount +
                ", hasBallisticExpert=" + hasBallisticExpert +
                ", hasExplosiveExpert=" + hasExplosiveExpert +
                ", hasTechnologyExpert=" + hasTechnologyExpert +
                ", additionalExpertise=" + additionalExpertise +
                ", totalMissionExecuted=" + totalMissionExecuted +
                ", totalMissionSuccess=" + totalMissionSuccess +
                ", totalCasualties=" + totalCasualties +
                ", friendlyFireIncidents='" + friendlyFireIncidents + '\'' +
                '}';
    }
}
