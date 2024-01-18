package com.example.RENGAW.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Equipment {

//    @SequenceGenerator(
//            name = "equipment_serial_number_sequence",
//            sequenceName = "equipment_serial_number_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "equipment_serial_number_sequence"
//    )
    @Id
    @Column(unique = true)
    private Long equipmentSerialNumber;

    @NotNull
    @NotBlank
    private String equipmentType;

    @NotNull
    @NotBlank
    private String equipmentModel;

    @NotNull
    private boolean lethal;

    private String productionCompany;

    @NotNull
    @Pattern(regexp = "^\\d{4}$")
    private String productionYear;

    @NotNull
    private String materialsUsed;

    @NotNull
    private boolean radioActive;

    @NotNull
    private double weightInGrams;

    @Size(max = 10000)
    private String description;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private Personnel personnel;

    public Equipment() {
    }

    public Long getEquipmentSerialNumber() {
        return equipmentSerialNumber;
    }

    public void setEquipmentSerialNumber(Long equipmentSerialNumber) {
        this.equipmentSerialNumber = equipmentSerialNumber;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(String equipmentModel) {
        this.equipmentModel = equipmentModel;
    }

    public boolean isLethal() {
        return lethal;
    }

    public void setLethal(boolean lethal) {
        this.lethal = lethal;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public String getMaterialsUsed() {
        return materialsUsed;
    }

    public void setMaterialsUsed(String materialsUsed) {
        this.materialsUsed = materialsUsed;
    }

    public boolean isRadioActive() {
        return radioActive;
    }

    public void setRadioActive(boolean radioActive) {
        this.radioActive = radioActive;
    }

    public double getWeightInGrams() {
        return weightInGrams;
    }

    public void setWeightInGrams(double weightInGrams) {
        this.weightInGrams = weightInGrams;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "equipmentSerialNumber=" + equipmentSerialNumber +
                ", equipmentType='" + equipmentType + '\'' +
                ", equipmentModel='" + equipmentModel + '\'' +
                ", lethal=" + lethal +
                ", productionCompany='" + productionCompany + '\'' +
                ", productionYear='" + productionYear + '\'' +
                ", materialsUsed='" + materialsUsed + '\'' +
                ", radioActive=" + radioActive +
                ", weightInGrams=" + weightInGrams +
                ", description='" + description + '\'' +
                '}';
    }
}
