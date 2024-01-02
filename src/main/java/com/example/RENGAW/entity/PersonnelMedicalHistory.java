package com.example.RENGAW.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonnelMedicalHistory {

    @Id
    @SequenceGenerator(
            name = "personnel_medical_history_sequence",
            sequenceName = "personnel_medical_history_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "personnel_medical_history_sequence"
    )
    private Long personnelMedicalHistoryId;

    private String surgeries;
    private String chronicIllness;
    private String medications;
    private String allergies;
    private String hazardousExposure;
    private String immunizations;
    private String stressLevels;
    private String mentalHealth;
    private String lifestyleHabits;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "personnel_id",
            referencedColumnName = "personnelId"
    )
    @JsonIgnore
    private Personnel personnel;
}
