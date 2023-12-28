package com.example.RENGAW.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    private String gunType;
    private String gunModel;
    private String bulletCaliber;
    private String productionCompany;
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
            referencedColumnName = "personnelId"
    )
    private Personnel personnel;
}
