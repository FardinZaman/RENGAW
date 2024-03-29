package com.example.RENGAW.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class ArmouredCarrier {

//    @SequenceGenerator(
//            name = "armoured_carrier_sequence",
//            sequenceName = "armoured_carrier_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "armoured_carrier_sequence"
//    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @NotNull
    @Positive
    @Column(unique = true)
    private Long carrierSerialNumber;

    @NotNull
    @NotBlank
    private String carrierType;

    @NotNull
    @NotBlank
    private String carrierModel;

    private String wheelStructure;
    private String terrainType;
    private String productionCompany;

    @NotNull
    @Pattern(regexp = "^\\d{4}$")
    private String productionYear;

    @NotNull
    @Digits(integer = 100, fraction = 0)
    private Long personnelCapacity;

    private Double maxSpeedKmH;
    private String engineModel;
    private String armor;
    private String armament;
    private Double weightInTonne;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private Team team;

    public ArmouredCarrier() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarrierSerialNumber() {
        return carrierSerialNumber;
    }

    public void setCarrierSerialNumber(Long carrierSerialNumber) {
        this.carrierSerialNumber = carrierSerialNumber;
    }

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    public String getCarrierModel() {
        return carrierModel;
    }

    public void setCarrierModel(String carrierModel) {
        this.carrierModel = carrierModel;
    }

    public String getWheelStructure() {
        return wheelStructure;
    }

    public void setWheelStructure(String wheelStructure) {
        this.wheelStructure = wheelStructure;
    }

    public String getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(String terrainType) {
        this.terrainType = terrainType;
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

    public Long getPersonnelCapacity() {
        return personnelCapacity;
    }

    public void setPersonnelCapacity(Long personnelCapacity) {
        this.personnelCapacity = personnelCapacity;
    }

    public String getEngineModel() {
        return engineModel;
    }

    public void setEngineModel(String engineModel) {
        this.engineModel = engineModel;
    }

    public Double getMaxSpeedKmH() {
        return maxSpeedKmH;
    }

    public void setMaxSpeedKmH(Double maxSpeedKmH) {
        this.maxSpeedKmH = maxSpeedKmH;
    }

    public Double getWeightInTonne() {
        return weightInTonne;
    }

    public void setWeightInTonne(Double weightInTonne) {
        this.weightInTonne = weightInTonne;
    }

    public String getArmor() {
        return armor;
    }

    public void setArmor(String armor) {
        this.armor = armor;
    }

    public String getArmament() {
        return armament;
    }

    public void setArmament(String armament) {
        this.armament = armament;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "ArmouredCarrier{" +
                "id=" + id +
                ", carrierSerialNumber=" + carrierSerialNumber +
                ", carrierType='" + carrierType + '\'' +
                ", carrierModel='" + carrierModel + '\'' +
                ", wheelStructure='" + wheelStructure + '\'' +
                ", terrainType='" + terrainType + '\'' +
                ", productionCompany='" + productionCompany + '\'' +
                ", productionYear='" + productionYear + '\'' +
                ", personnelCapacity=" + personnelCapacity +
                ", maxSpeedKmH=" + maxSpeedKmH +
                ", engineModel='" + engineModel + '\'' +
                ", armor='" + armor + '\'' +
                ", armament='" + armament + '\'' +
                ", weightInTonne=" + weightInTonne +
                '}';
    }
}
