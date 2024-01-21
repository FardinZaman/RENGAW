package com.example.RENGAW.repository;

import com.example.RENGAW.entity.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long> {

    public List<Personnel> findByFirstNameIgnoreCase(String firstName);

    public List<Personnel> findByLastNameIgnoreCase(String lastName);

    public List<Personnel> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);

    @Query(
            "SELECT p.status FROM Personnel p where p.email = :email"
    )
    public Optional<String> findStatusByEmail(String email);

    public Optional<Personnel> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(
            "UPDATE Personnel p\n" +
                    "SET p.status = 'Unavailable until cleared by Dr. Bekhterev'\n" +
                    "WHERE p.id IN (\n" +
                    "    SELECT pmh.personnel.id\n" +
                    "    FROM PersonnelMedicalHistory pmh\n" +
                    "    WHERE pmh.chronicIllness = 'Depression'\n" +
                    ")"
    )
    public void updatePersonnelStatusIfDepressed();

    public Long countByTeamId(Long teamId);

    public Long countByTeamTeamCodeNameIgnoreCase(String teamCodeName);

    @Query(
            "SELECT COUNT(p) = SUM(CASE WHEN p.status = 'Available' THEN 1 ELSE 0 END) " +
                    "FROM Personnel p WHERE p.team.id = :teamId"
    )
    public boolean checkAllStatusAvailableByTeamId(Long teamId);

    public List<Personnel> findAllByTeamId(Long teamId);
}
