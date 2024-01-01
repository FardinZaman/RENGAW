package com.example.RENGAW.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Long teamId;

    private String teamCodeName;

    @NotNull(message = "Provide Team Lead")
    @Pattern(regexp = "\\p{Alpha}+(\\s\\p{Alpha}+)*", message = "Name should have only Alphabetical characters")
    private String currentLead;

    @NotNull(message = "Provide Team Lead Name")
    @Digits(integer = 1000, fraction = 0, message = "Id should have only numeric characters")
    private Long currentLeadId;

    @NotNull(message = "Provide Team personnel number")
    @Range(min = 3, max = 5, message = "Team should have personnels between 3 and 5")
    private Long teamMemberCount;

    private boolean hasBallisticExpert;
    private boolean hasExplosiveExpert;
    private boolean hasTechnologyExpert;
    private List<String> additionalExpertise;
    private Long totalMissionExecuted;
    private Long totalMissionSuccess;
    private Long totalCasualties;
    private String friendlyFireIncidents;
}
