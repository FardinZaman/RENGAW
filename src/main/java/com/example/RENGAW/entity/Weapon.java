package com.example.RENGAW.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Provide Type of the Gun")
    @Size(min = 5, max = 50)
    private String gunType;

    @NotNull(message = "Provide Model of the Gun")
    @Pattern(regexp = "^[A-Za-z]+[-\\s]?\\d+$", message = "Gun Model must follow the format 'word-number'")
    private String gunModel;

    @NotNull(message = "Provide Caliber of bullet used in the Gun")
    @Pattern(regexp = "^\\d+(\\.\\d+)\\s?[×xX]\\s?\\d+mm$", message = "Bullet Caliber must follow the format 'number×numbermm'")
    private String bulletCaliber;

    private String productionCompany;

    @NotNull(message = "Provide Production Year of the Gun")
    @Pattern(regexp = "^\\d{4}$", message = "Production Year must be a valid year in 'YYYY' format")
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
