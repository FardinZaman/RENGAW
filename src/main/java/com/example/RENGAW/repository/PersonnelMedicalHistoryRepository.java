package com.example.RENGAW.repository;

import com.example.RENGAW.entity.PersonnelMedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonnelMedicalHistoryRepository extends JpaRepository<PersonnelMedicalHistory, Long> {
    public Optional<PersonnelMedicalHistory> findByPersonnelId(Long personnelId);

    @Query(
            value = "SELECT *\n" +
                    "FROM personnel_medical_history pmh\n" +
                    "WHERE pmh.personnel_id IN (\n" +
                    "    SELECT p.id\n" +
                    "    FROM personnel p\n" +
                    "    WHERE p.first_name = :personnel_name OR p.last_name = :personnel_name\n" +
                    ")",
            nativeQuery = true
    )
    public List<PersonnelMedicalHistory> findPersonnelMedicalHistoryByName(@Param("personnel_name") String personnelName);

    @Query(
            "SELECT pmh FROM PersonnelMedicalHistory pmh " +
                    "JOIN pmh.personnel p " +
                    "JOIN Weapon w ON p = w.personnel " +
                    "JOIN Equipment e ON p = e.personnel " +
                    "WHERE (p.firstName LIKE %:personnelName% OR p.lastName LIKE %:personnelName%) " +
                    "AND w.bulletCaliber LIKE CONCAT(:caliber, '%') " +
                    "AND e.materialsUsed LIKE %:materialsUsed%"
    )
    public List<PersonnelMedicalHistory> findMedicalHistoryByEquipmentMaterialAndCaliberAndName(@Param("materialsUsed") String materialsUsed,
                                                                                         @Param("caliber") String caliber,
                                                                                         @Param("personnelName") String personnelName);
}
