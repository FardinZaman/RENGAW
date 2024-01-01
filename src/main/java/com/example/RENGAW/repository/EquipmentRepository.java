package com.example.RENGAW.repository;

import com.example.RENGAW.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    public Equipment findByEquipmentSerialNumber(Long equipmentSerialNumber);

    @Query(
            "SELECT e FROM Equipment e " +
                    "JOIN Personnel p ON e.personnel = p " +
                    "JOIN Weapon w ON p = w.personnel " +
                    "WHERE w.gunModel LIKE %:gunModel%"
    )
    public List<Equipment> findEquipmentUsedByPersonnelFromGunModel(@Param("gunModel") String gunModel);
}
