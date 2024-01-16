package com.example.RENGAW.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
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
    private Long id;

    private String surgeries;
    private String chronicIllness;
    private String medications;
    private String allergies;
    private String hazardousExposure;
    private String immunizations;

    @Enumerated(EnumType.STRING)
    private StressLevel stressLevels;

    private String mentalHealth;
    private String lifestyleHabits;

    @OneToOne(
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "personnel_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private Personnel personnel;

    public PersonnelMedicalHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(String surgeries) {
        this.surgeries = surgeries;
    }

    public String getChronicIllness() {
        return chronicIllness;
    }

    public void setChronicIllness(String chronicIllness) {
        this.chronicIllness = chronicIllness;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getHazardousExposure() {
        return hazardousExposure;
    }

    public void setHazardousExposure(String hazardousExposure) {
        this.hazardousExposure = hazardousExposure;
    }

    public String getImmunizations() {
        return immunizations;
    }

    public void setImmunizations(String immunizations) {
        this.immunizations = immunizations;
    }

    public StressLevel getStressLevels() {
        return stressLevels;
    }

    public void setStressLevels(StressLevel stressLevels) {
        this.stressLevels = stressLevels;
    }

    public String getMentalHealth() {
        return mentalHealth;
    }

    public void setMentalHealth(String mentalHealth) {
        this.mentalHealth = mentalHealth;
    }

    public String getLifestyleHabits() {
        return lifestyleHabits;
    }

    public void setLifestyleHabits(String lifestyleHabits) {
        this.lifestyleHabits = lifestyleHabits;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    @Override
    public String toString() {
        return "PersonnelMedicalHistory{" +
                "personnelMedicalHistoryId=" + id +
                ", surgeries='" + surgeries + '\'' +
                ", chronicIllness='" + chronicIllness + '\'' +
                ", medications='" + medications + '\'' +
                ", allergies='" + allergies + '\'' +
                ", hazardousExposure='" + hazardousExposure + '\'' +
                ", immunizations='" + immunizations + '\'' +
                ", stressLevels=" + stressLevels +
                ", mentalHealth='" + mentalHealth + '\'' +
                ", lifestyleHabits='" + lifestyleHabits + '\'' +
                '}';
    }
}
