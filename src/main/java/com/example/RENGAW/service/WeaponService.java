package com.example.RENGAW.service;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.PersonnelMedicalHistory;
import com.example.RENGAW.entity.Weapon;
import com.example.RENGAW.repository.PersonnelRepository;
import com.example.RENGAW.repository.WeaponRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponService{

    @Autowired
    WeaponRepository weaponRepository;

    @Autowired
    PersonnelRepository personnelRepository;

    public Weapon saveWeapon(Weapon weapon) {
        return weaponRepository.save(weapon);
    }

    public Weapon assignWeaponToPersonnelByPersonnelId(Long weaponSerialNumber, Long personnelId) {
        Weapon weapon = weaponRepository.findByWeaponSerialNumber(weaponSerialNumber)
                .orElseThrow(() -> new EntityNotFoundException("No Weapon Found By Serial Number : " + weaponSerialNumber));
        Personnel personnel = personnelRepository.findById(personnelId)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Id : " + personnelId));
        weapon.setPersonnel(personnel);

        return weaponRepository.save(weapon);
    }

    public Weapon assignWeaponToPersonnelByEmailIdAndSaveWeapon(String email, Weapon weapon) {
        Personnel personnel = personnelRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Email : " + email));
        weapon.setPersonnel(personnel);

        return weaponRepository.save(weapon);
    }

    public Personnel findWeaponUserByWeaponSerialNumber(Long weaponSerialNumber) {
        return weaponRepository.findWeaponUserByWeaponSerialNumber(weaponSerialNumber)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Weapon Serial : " + weaponSerialNumber));
    }

    public List<Weapon> findWeaponUsedByPersonnelByEmail(String email) {
        Personnel personnel = personnelRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Email : " + email));

        return weaponRepository.findByPersonnelId(personnel.getId());
    }

    public List<Weapon> findWeaponUsedByPersonnelByPersonnelId(Long personnelId) {
        return weaponRepository.findByPersonnelId(personnelId);
    }

    public List<Personnel> findWeaponUserByWeaponProductionCompany(String productionCompany) {
        return weaponRepository.findWeaponUserByWeaponProductionCompany(productionCompany);
    }

    public List<Personnel> findWeaponUserByWeaponBulletCaliber(String diameter, String length) {
        return weaponRepository.findWeaponUserByWeaponBulletCaliber(diameter, length);
    }

    public List<PersonnelMedicalHistory> findPersonnelMedicalHistoryByGunType(String gunType) {
        return weaponRepository.findPersonnelMedicalHistoryByGunType(gunType);
    }

    public Page<Weapon> findAllWeaponPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return weaponRepository.findAll(pageable);
    }

    public Weapon findWeaponBySerialNumber(Long weaponSerialNumber) {
        return weaponRepository.findByWeaponSerialNumber(weaponSerialNumber)
                .orElseThrow(() -> new EntityNotFoundException("No Weapon found with Serial " + weaponSerialNumber));
    }

    public void deleteWeaponBySerialNumber(Long weaponSerialNumber) {
        weaponRepository.deleteByWeaponSerialNumber(weaponSerialNumber);
    }
}
