package com.example.RENGAW.service;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.PersonnelMedicalHistory;
import com.example.RENGAW.entity.Weapon;

import java.util.List;

public interface WeaponService {

    public Weapon saveWeapon(Weapon weapon);

    public Weapon assignWeaponToPersonnelByPersonnelId(Long weaponSerialNumber, Long personnelId);

    public Weapon assignWeaponToPersonnelByEmailIdAndSaveWeapon(String emailId, Weapon weapon);

    public Personnel findWeaponUserByWeaponSerialNumber(Long weaponSerialNumber);

    public List<Weapon> findWeaponUsedByPersonnelByEmailId(String emailId);

    public List<Weapon> findWeaponUsedByPersonnelByPersonnelId(Long personnelId);

    public List<Personnel> findWeaponUserByWeaponProductionCompany(String productionCompany);

    public List<Personnel> findWeaponUserByWeaponBulletCaliber(String diameter, String length);

    public List<PersonnelMedicalHistory> findPersonnelMedicalHistoryByGunType(String gunType);
}
