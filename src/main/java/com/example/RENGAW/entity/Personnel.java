package com.example.RENGAW.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "emailId_unique",
                columnNames = "email_address"
        )
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    private Long personnelId;

    @OneToOne(
            mappedBy = "personnel",
            optional = false
    )
    @JsonManagedReference
    private PersonnelMedicalHistory personnelMedicalHistory;

    private String firstName;
    private String lastName;

    @Column(
            name = "email_address",
            nullable = false
    )
    @Email(message = "Provide Organization's Email Id")
    private String emailId;

    @NotNull(message = "Provide Current Status")
    private String status;

    private String currentRank;

    @NotNull(message = "Provide Background")
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
            referencedColumnName = "teamId"
    )
    private Team team;
}
