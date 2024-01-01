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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Equipment {

    @Id
    @SequenceGenerator(
            name = "equipment_serial_number_sequence",
            sequenceName = "equipment_serial_number_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "equipment_serial_number_sequence"
    )
    private Long equipmentSerialNumber;

    @NotNull(message = "Provide Equipment Type")
    private String equipmentType;

    @NotNull(message = "Provide Equipment Model")
    private String equipmentModel;

    @NotNull(message = "Provide lethality Status")
    private boolean lethal;

    private String productionCompany;

    @NotNull(message = "Provide Production Year of the Equipment")
    @Pattern(regexp = "^\\d{4}$", message = "Production Year must be a valid year in 'YYYY' format")
    private String productionYear;

    @NotNull(message = "Provide Materials Used in Equipment")
    private String materialsUsed;

    @NotNull(message = "Provide Radio-Activity Status")
    private boolean radioActive;

    @NotNull(message = "Provide Weight of Equipment")
    private double weightInGrams;

    @Size(max = 10000)
    private String description;

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
