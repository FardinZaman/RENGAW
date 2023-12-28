package com.example.RENGAW.repository;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeaponRepository extends JpaRepository<Weapon, Long> {
    Optional<Weapon> findByWeaponSerialNumber(Long weaponSerialNumber);

    @Query(
          "SELECT w.personnel FROM Weapon w WHERE w.weaponSerialNumber = :weaponSerialNumber"
    )
    public Optional<Personnel> findWeaponUserByWeaponSerialNumber(@Param("weaponSerialNumber") Long weaponSerialNumber);

    public List<Weapon> findByPersonnelPersonnelId(Long personnelId);
}
