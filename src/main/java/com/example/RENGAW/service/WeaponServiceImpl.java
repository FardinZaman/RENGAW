package com.example.RENGAW.service;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.PersonnelMedicalHistory;
import com.example.RENGAW.entity.Weapon;
import com.example.RENGAW.repository.PersonnelRepository;
import com.example.RENGAW.repository.WeaponRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponServiceImpl implements WeaponService{

    @Autowired
    WeaponRepository weaponRepository;

    @Autowired
    PersonnelRepository personnelRepository;

    @Override
    public Weapon saveWeapon(Weapon weapon) {
        return weaponRepository.save(weapon);
    }

    @Override
    public Weapon assignWeaponToPersonnelByPersonnelId(Long weaponSerialNumber, Long personnelId) {
        Weapon weapon = weaponRepository.findByWeaponSerialNumber(weaponSerialNumber)
                .orElseThrow(() -> new EntityNotFoundException("No Weapon Found By Serial Number : " + weaponSerialNumber));
        Personnel personnel = personnelRepository.findByPersonnelId(personnelId)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Id : " + personnelId));
        weapon.setPersonnel(personnel);

        return weaponRepository.save(weapon);
    }

    @Override
    public Weapon assignWeaponToPersonnelByEmailIdAndSaveWeapon(String emailId, Weapon weapon) {
        Personnel personnel = personnelRepository.findByEmailId(emailId)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Email : " + emailId));
        weapon.setPersonnel(personnel);

        return weaponRepository.save(weapon);
    }

    @Override
    public Personnel findWeaponUserByWeaponSerialNumber(Long weaponSerialNumber) {
        return weaponRepository.findWeaponUserByWeaponSerialNumber(weaponSerialNumber)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Weapon Serial : " + weaponSerialNumber));
    }

    @Override
    public List<Weapon> findWeaponUsedByPersonnelByEmailId(String emailId) {
        Personnel personnel = personnelRepository.findByEmailId(emailId)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Email : " + emailId));
        List<Weapon> weaponList = weaponRepository.findByPersonnelPersonnelId(personnel.getPersonnelId());

        if(weaponList.isEmpty()){
            throw new EntityNotFoundException("No weapon Found with Personnel Id : " + personnel.getPersonnelId());
        }

        return weaponList;
    }

    @Override
    public List<Weapon> findWeaponUsedByPersonnelByPersonnelId(Long personnelId) {
        List<Weapon> weaponList = weaponRepository.findByPersonnelPersonnelId(personnelId);

        if(weaponList.isEmpty()){
            throw new EntityNotFoundException("No weapon Found with Personnel Id : " + personnelId);
        }

        return weaponList;
    }

    @Override
    public List<Personnel> findWeaponUserByWeaponProductionCompany(String productionCompany) {
        List<Personnel> personnelList = weaponRepository.findWeaponUserByWeaponProductionCompany(productionCompany);

        if(personnelList.isEmpty()){
            throw new EntityNotFoundException("No Personnel Found with Weapon Company : " + productionCompany);
        }

        return personnelList;
    }

    @Override
    public List<Personnel> findWeaponUserByWeaponBulletCaliber(String diameter, String length) {
        List<Personnel> personnelList = weaponRepository.findWeaponUserByWeaponBulletCaliber(diameter, length);

        if(personnelList.isEmpty()){
            throw new EntityNotFoundException("No Personnel Found With Bullet Caliber : " + diameter + "x" + length);
        }

        return personnelList;
    }

    @Override
    public List<PersonnelMedicalHistory> findPersonnelMedicalHistoryByGunType(String gunType) {
        List<PersonnelMedicalHistory> personnelMedicalHistoryList =
                weaponRepository.findPersonnelMedicalHistoryByGunType(gunType);

        if(personnelMedicalHistoryList.isEmpty()){
            throw new EntityNotFoundException("No Medical History Found Of Personnel with Gun Type : " + gunType);
        }

        return personnelMedicalHistoryList;
    }
}
