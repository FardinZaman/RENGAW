package com.example.RENGAW.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Weapon {

    @Id
    @SequenceGenerator(
            name = "weapon_serial_number_sequence",
            sequenceName = "weapon_serial_number_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "weapon_serial_number_sequence"
    )
    private Long weaponSerialNumber;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 50)
    private String gunType;

    @NotNull
    @NotBlank
//    @Pattern(regexp = "^[A-Za-z]+[-\\s]?\\d+$", message = "Gun Model must follow the format 'word-number'")
    private String gunModel;

    @NotNull
    @Pattern(regexp = "^\\d+(\\.\\d+)\\s?[×xX]\\s?\\d+mm$")
    private String bulletCaliber;

    private String productionCompany;

    @NotNull
    @Pattern(regexp = "^\\d{4}$")
    private String productionYear;

    private String sandTestStatus;
    private String mudTestStatus;
    private String iceTestStatus;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "personnel_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private Personnel personnel;

    public Weapon() {
    }

    public Long getWeaponSerialNumber() {
        return weaponSerialNumber;
    }

    public void setWeaponSerialNumber(Long weaponSerialNumber) {
        this.weaponSerialNumber = weaponSerialNumber;
    }

    public String getGunType() {
        return gunType;
    }

    public void setGunType(String gunType) {
        this.gunType = gunType;
    }

    public String getGunModel() {
        return gunModel;
    }

    public void setGunModel(String gunModel) {
        this.gunModel = gunModel;
    }

    public String getBulletCaliber() {
        return bulletCaliber;
    }

    public void setBulletCaliber(String bulletCaliber) {
        this.bulletCaliber = bulletCaliber;
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

    public String getSandTestStatus() {
        return sandTestStatus;
    }

    public void setSandTestStatus(String sandTestStatus) {
        this.sandTestStatus = sandTestStatus;
    }

    public String getMudTestStatus() {
        return mudTestStatus;
    }

    public void setMudTestStatus(String mudTestStatus) {
        this.mudTestStatus = mudTestStatus;
    }

    public String getIceTestStatus() {
        return iceTestStatus;
    }

    public void setIceTestStatus(String iceTestStatus) {
        this.iceTestStatus = iceTestStatus;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "weaponSerialNumber=" + weaponSerialNumber +
                ", gunType='" + gunType + '\'' +
                ", gunModel='" + gunModel + '\'' +
                ", bulletCaliber='" + bulletCaliber + '\'' +
                ", productionCompany='" + productionCompany + '\'' +
                ", productionYear='" + productionYear + '\'' +
                ", sandTestStatus='" + sandTestStatus + '\'' +
                ", mudTestStatus='" + mudTestStatus + '\'' +
                ", iceTestStatus='" + iceTestStatus + '\'' +
                '}';
    }
}
