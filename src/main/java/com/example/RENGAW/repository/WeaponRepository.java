package com.example.RENGAW.repository;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.PersonnelMedicalHistory;
import com.example.RENGAW.entity.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeaponRepository extends JpaRepository<Weapon, Long> {
    public Optional<Weapon> findByWeaponSerialNumber(Long weaponSerialNumber);

    @Query(
            "SELECT w.personnel FROM Weapon w WHERE w.weaponSerialNumber = :weaponSerialNumber"
    )
    public Optional<Personnel> findWeaponUserByWeaponSerialNumber(Long weaponSerialNumber);

    public List<Weapon> findByPersonnelId(Long personnelId);

    @Query(
            "SELECT w.personnel FROM Weapon w WHERE w.productionCompany LIKE %:productionCompany%"
    )
    public List<Personnel> findWeaponUserByWeaponProductionCompany(String productionCompany);

    @Query(
            "SELECT w.personnel FROM Weapon w WHERE " +
                    "w.bulletCaliber LIKE CONCAT(:diameter, '%') AND w.bulletCaliber LIKE CONCAT('%', :length, 'mm')"
    )
    public List<Personnel> findWeaponUserByWeaponBulletCaliber(@Param("diameter") String diameter, @Param("length") String length);

    @Query(
            "SELECT w.personnel.personnelMedicalHistory FROM Weapon w " +
                    "WHERE w.gunType LIKE %:gunType%"
    )
    public List<PersonnelMedicalHistory> findPersonnelMedicalHistoryByGunType(String gunType);

    @Transactional
    public void deleteByWeaponSerialNumber(Long weaponSerialNumber);

    public boolean existsByWeaponSerialNumber(Long weaponSerialNumber);
}
