package com.example.RENGAW.entity;

import com.example.RENGAW.entity.enumaration.OperationStatus;
import com.example.RENGAW.entity.enumaration.OperationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Operation {

    @Id
    @SequenceGenerator(
            name = "operation_sequence",
            sequenceName = "operation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "operation_sequence"
    )
    private Long operationId;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @NotNull(message = "Provide Operation's Code Name")
    @NotBlank(message = "Provide Operation's Code Name")
    private String operationCodeName;

    @Enumerated(EnumType.STRING)
    private OperationStatus operationStatus;

    @NotNull(message = "Provide Operation Zone")
    @NotBlank(message = "Provide Operation Zone")
    private String operationZone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Provide Operation Date")
    private Date operationDate;

    @Size(max = 10000, message = "Brief shouldn't exceed 10000 characters.")
    @NotNull(message = "Provide Operation Brief")
    @NotBlank(message = "Provide Operation Brief")
    private String operationBrief;

    private Long totalCasualties;
    private Long vehicleDestroyed;

    @Size(max = 100000, message = "Gathered information shouldn't exceed 100000 characters.")
    private String infoGathered;

    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "team_operation_mapping",
            joinColumns = @JoinColumn(
                    name = "operation_id",
                    referencedColumnName = "operationId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "team_id",
                    referencedColumnName = "teamId"
            )
    )
    @JsonIgnore
    private List<Team> operationTeams;

    public String toString(){
        return "Type : " + getOperationType() + "\n" +
                "Code name : " + getOperationCodeName() + "\n" +
                "Zone : " + getOperationZone() + "\n" +
                "Date : " + getOperationDate() + "\n" +
                "Brief : " + getOperationBrief();
    }
}
