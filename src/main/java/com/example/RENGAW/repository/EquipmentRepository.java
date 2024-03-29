package com.example.RENGAW.repository;

import com.example.RENGAW.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    public Optional<Equipment> findByEquipmentSerialNumber(Long equipmentSerialNumber);

    @Query(
            "SELECT e FROM Equipment e " +
                    "JOIN Personnel p ON e.personnel = p " +
                    "JOIN Weapon w ON p = w.personnel " +
                    "WHERE w.gunModel LIKE %:gunModel%"
    )
    public List<Equipment> findEquipmentUsedByPersonnelFromGunModel(String gunModel);

    public Optional<Equipment> findByEquipmentTypeContainingIgnoreCase(String equipmentType);

    @Transactional
    public void deleteByEquipmentSerialNumber(Long equipmentSerialNumber);

    public List<Equipment> findByPersonnelId(Long personnelId);
}
