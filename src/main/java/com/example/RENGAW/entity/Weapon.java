package com.example.RENGAW.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Weapon {

//    @SequenceGenerator(
//            name = "weapon_serial_number_sequence",
//            sequenceName = "weapon_serial_number_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "weapon_serial_number_sequence"
//    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(unique = true)
    @NotNull
    @Positive
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

    @Enumerated(EnumType.STRING)
    private GunTestStatus sandTestStatus;

    @Enumerated(EnumType.STRING)
    private GunTestStatus mudTestStatus;

    @Enumerated(EnumType.STRING)
    private GunTestStatus iceTestStatus;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private Personnel personnel;

    public Weapon() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public GunTestStatus getSandTestStatus() {
        return sandTestStatus;
    }

    public void setSandTestStatus(GunTestStatus sandTestStatus) {
        this.sandTestStatus = sandTestStatus;
    }

    public GunTestStatus getMudTestStatus() {
        return mudTestStatus;
    }

    public void setMudTestStatus(GunTestStatus mudTestStatus) {
        this.mudTestStatus = mudTestStatus;
    }

    public GunTestStatus getIceTestStatus() {
        return iceTestStatus;
    }

    public void setIceTestStatus(GunTestStatus iceTestStatus) {
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
                "id=" + id +
                ", weaponSerialNumber=" + weaponSerialNumber +
                ", gunType='" + gunType + '\'' +
                ", gunModel='" + gunModel + '\'' +
                ", bulletCaliber='" + bulletCaliber + '\'' +
                ", productionCompany='" + productionCompany + '\'' +
                ", productionYear='" + productionYear + '\'' +
                ", sandTestStatus=" + sandTestStatus +
                ", mudTestStatus=" + mudTestStatus +
                ", iceTestStatus=" + iceTestStatus +
                '}';
    }
}
