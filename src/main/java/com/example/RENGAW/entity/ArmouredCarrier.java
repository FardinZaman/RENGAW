package com.example.RENGAW.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "team")
public class ArmouredCarrier {

    @Id
    @SequenceGenerator(
            name = "armoured_carrier_sequence",
            sequenceName = "armoured_carrier_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "armoured_carrier_sequence"
    )
    private Long carrierID;

    @NotNull(message = "Provide Carrier Type")
    @NotBlank(message = "Provide Carrier Type")
    private String carrierType;

    @NotNull(message = "Provide Carrier Model")
    @NotBlank(message = "Provide Carrier Model")
    private String carrierModel;

    private String wheelStructure;
    private String terrainType;
    private String productionCompany;

    @NotBlank(message = "Provide Production Year of the Vehicle")
    @Pattern(regexp = "^\\d{4}$", message = "Production Year must be a valid year in 'YYYY' format")
    private String productionYear;

    @NotNull(message = "Provide Capacity of Vehicle")
    @Digits(integer = 100, fraction = 0, message = "Capacity should be a number")
    private Long personnelCapacity;

    private double maxSpeedKmH;
    private String engineModel;
    private String armor;
    private String armament;
    private double weightInTonne;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "team_id",
            referencedColumnName = "teamId"
    )
    @JsonIgnore
    private Team team;
}
