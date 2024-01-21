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

//    @Query(
//            "SELECT pmh " +
//                    "FROM PersonnelMedicalHistory pmh " +
//                    "WHERE pmh.personnel.id IN (" +
//                    "    SELECT p.id " +
//                    "    FROM Personnel p " +
//                    "    WHERE p.firstName = :personnelName OR p.lastName = :personnelName" +
//                    ")"
//    )
//    public List<PersonnelMedicalHistory> findPersonnelMedicalHistoryByName(@Param("personnelName") String personnelName);

    public List<PersonnelMedicalHistory> findByPersonnelFirstNameOrPersonnelLastName(String firstName, String lastName);


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
