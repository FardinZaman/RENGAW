package com.example.RENGAW.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
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
    private Long id;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String operationCodeName;

    @Enumerated(EnumType.STRING)
    private OperationStatus operationStatus;

    @NotNull
    @NotBlank
    private String operationZone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date operationDate;

    @Size(max = 10000)
    @NotNull
    @NotBlank
    private String operationBrief;

    private Long totalCasualties;
    private Long vehicleDestroyed;

    @Size(max = 100000)
    private String infoGathered;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnore
    private List<Team> team;

    public Operation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getOperationCodeName() {
        return operationCodeName;
    }

    public void setOperationCodeName(String operationCodeName) {
        this.operationCodeName = operationCodeName;
    }

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatus operationStatus) {
        this.operationStatus = operationStatus;
    }

    public String getOperationZone() {
        return operationZone;
    }

    public void setOperationZone(String operationZone) {
        this.operationZone = operationZone;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationBrief() {
        return operationBrief;
    }

    public void setOperationBrief(String operationBrief) {
        this.operationBrief = operationBrief;
    }

    public Long getTotalCasualties() {
        return totalCasualties;
    }

    public void setTotalCasualties(Long totalCasualties) {
        this.totalCasualties = totalCasualties;
    }

    public Long getVehicleDestroyed() {
        return vehicleDestroyed;
    }

    public void setVehicleDestroyed(Long vehicleDestroyed) {
        this.vehicleDestroyed = vehicleDestroyed;
    }

    public String getInfoGathered() {
        return infoGathered;
    }

    public void setInfoGathered(String infoGathered) {
        this.infoGathered = infoGathered;
    }

    public List<Team> getTeam() {
        return team;
    }

    public void setTeam(List<Team> team) {
        this.team = team;
    }

    @Override
    public String toString(){
        return "Type : " + getOperationType() + "\n" +
                "Code name : " + getOperationCodeName() + "\n" +
                "Zone : " + getOperationZone() + "\n" +
                "Date : " + getOperationDate() + "\n" +
                "Brief : " + getOperationBrief();
    }
}
