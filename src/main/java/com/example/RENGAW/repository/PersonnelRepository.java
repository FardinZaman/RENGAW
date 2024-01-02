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
    public Optional<Personnel> findByPersonnelId(Long personnelId);

    public List<Personnel> findByFirstNameIgnoreCase(String firstName);

    public List<Personnel> findByLastNameIgnoreCase(String lastName);

    public List<Personnel> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);

    @Query(
            value = "SELECT p.status FROM personnel p where p.email_address = :email_address",
            nativeQuery = true
    )
    public Optional<String> findStatusByEmailId(@Param("email_address") String emailId);

    public Optional<Personnel> findByEmailId(String emailId);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE personnel p\n" +
                    "SET p.status = 'Unavailable until cleared by Dr. Bekhterev'\n" +
                    "WHERE p.personnel_id IN (\n" +
                    "    SELECT pmh.personnel_id\n" +
                    "    FROM personnel_medical_history pmh\n" +
                    "    WHERE pmh.chronic_illness = 'Depression'\n" +
                    ")",
            nativeQuery = true
    )
    public void updatePersonnelStatusIfDepressed();

    public Long countByTeamTeamId(Long teamId);

    @Query(
            "SELECT COUNT(p) = SUM(CASE WHEN p.status = 'Available' THEN 1 ELSE 0 END) " +
                    "FROM Personnel p WHERE p.team.teamId = :teamId"
    )
    public boolean checkAllStatusAvailableByTeamId(@Param("teamId") Long teamId);

    public List<Personnel> findAllByTeamTeamId(Long teamId);
}
