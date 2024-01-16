package com.example.RENGAW.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "emailId_unique",
                columnNames = "email_address"
        )
)
public class Personnel {

    @Id
    @SequenceGenerator(
            name = "personnel_sequence",
            sequenceName = "personnel_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "personnel_sequence"
    )
    private Long id;

    @OneToOne(
            mappedBy = "personnel",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private PersonnelMedicalHistory personnelMedicalHistory;

    private String firstName;
    private String lastName;

    @Column(
            name = "email_address",
            nullable = false
    )
    @Email
    private String email;

    @NotNull
    private String status;

    private String currentRank;

    @NotNull
    private String background;

    private Long totalOperation;
    private Long reportedKillCount;

    @ElementCollection
    private List<String> expertise;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "team_id",
            referencedColumnName = "id"
    )
    @JsonIgnore
    private Team team;

    public Personnel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonnelMedicalHistory getPersonnelMedicalHistory() {
        return personnelMedicalHistory;
    }

    public void setPersonnelMedicalHistory(PersonnelMedicalHistory personnelMedicalHistory) {
        this.personnelMedicalHistory = personnelMedicalHistory;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentRank() {
        return currentRank;
    }

    public void setCurrentRank(String currentRank) {
        this.currentRank = currentRank;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Long getTotalOperation() {
        return totalOperation;
    }

    public void setTotalOperation(Long totalOperation) {
        this.totalOperation = totalOperation;
    }

    public Long getReportedKillCount() {
        return reportedKillCount;
    }

    public void setReportedKillCount(Long reportedKillCount) {
        this.reportedKillCount = reportedKillCount;
    }

    public List<String> getExpertise() {
        return expertise;
    }

    public void setExpertise(List<String> expertise) {
        this.expertise = expertise;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Personnel{" +
                "personnelId=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + email + '\'' +
                ", status='" + status + '\'' +
                ", currentRank='" + currentRank + '\'' +
                ", background='" + background + '\'' +
                ", totalOperation=" + totalOperation +
                ", reportedKillCount=" + reportedKillCount +
                ", expertise=" + expertise +
                '}';
    }
}
