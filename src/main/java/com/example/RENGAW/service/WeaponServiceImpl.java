package com.example.RENGAW.service;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Weapon;
import com.example.RENGAW.repository.PersonnelRepository;
import com.example.RENGAW.repository.WeaponRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Weapon weapon = weaponRepository.findByWeaponSerialNumber(weaponSerialNumber).orElseThrow(EntityNotFoundException::new);
        Personnel personnel = personnelRepository.findByPersonnelId(personnelId).orElseThrow(EntityNotFoundException::new);
        weapon.setPersonnel(personnel);

        return weaponRepository.save(weapon);
    }

    @Override
    public Weapon assignWeaponToPersonnelByEmailIdAndSaveWeapon(String emailId, Weapon weapon) {
        Personnel personnel = personnelRepository.findByEmailId(emailId).orElseThrow(EntityNotFoundException::new);
        weapon.setPersonnel(personnel);

        return weaponRepository.save(weapon);
    }

    @Override
    public Personnel findWeaponUserByWeaponSerialNumber(Long weaponSerialNumber) {
        return weaponRepository.findWeaponUserByWeaponSerialNumber(weaponSerialNumber).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Weapon> findWeaponUsedByPersonnelByEmailId(String emailId) {
        Personnel personnel = personnelRepository.findByEmailId(emailId).orElseThrow(EntityNotFoundException::new);
        return weaponRepository.findByPersonnelPersonnelId(personnel.getPersonnelId());
    }

    @Override
    public List<Weapon> findWeaponUsedByPersonnelByPersonnelId(Long personnelId) {
        return weaponRepository.findByPersonnelPersonnelId(personnelId);
    }
}
