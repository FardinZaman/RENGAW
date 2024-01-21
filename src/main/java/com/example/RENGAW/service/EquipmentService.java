package com.example.RENGAW.service;

import com.example.RENGAW.entity.Equipment;
import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Weapon;
import com.example.RENGAW.repository.EquipmentRepository;
import com.example.RENGAW.repository.PersonnelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipmentService{

    private final EquipmentRepository equipmentRepository;
    private final PersonnelRepository personnelRepository;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository, PersonnelRepository personnelRepository) {
        this.equipmentRepository = equipmentRepository;
        this.personnelRepository = personnelRepository;
    }

    public Equipment saveEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public Equipment assignEquipmentToPersonnelByEmailId(Long equipmentSerialNumber, String emailId) {
        Personnel personnel = personnelRepository.findByEmail(emailId)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Email : " + emailId));
        Equipment equipment = equipmentRepository.findByEquipmentSerialNumber(equipmentSerialNumber)
                .orElseThrow(() -> new EntityNotFoundException("No Equipment Found By Serial Number : " + equipmentSerialNumber));
        equipment.setPersonnel(personnel);

        return equipmentRepository.save(equipment);
    }

    public Equipment assignEquipmentToPersonnelByPersonnelIdAndSaveEquipment(Long personnelId, Equipment equipment) {
        Personnel personnel = personnelRepository.findById(personnelId)
                .orElseThrow(() -> new EntityNotFoundException("No personnel Found By Id : " + personnelId));
        equipment.setPersonnel(personnel);

        return equipmentRepository.save(equipment);
    }

    public List<Equipment> findEquipmentUsedByPersonnelFromGunModel(String gunModel) {
        return equipmentRepository.findEquipmentUsedByPersonnelFromGunModel(gunModel);
    }

    public Equipment updateWeightInGramsByEquipmentId(Long equipmentSerialNumber, double weightInGrams) {
        Equipment equipment = equipmentRepository.findByEquipmentSerialNumber(equipmentSerialNumber)
                .orElseThrow(() -> new EntityNotFoundException("No Equipment Found By Serial Number : " + equipmentSerialNumber));
        equipment.setWeightInGrams(weightInGrams);

        return equipmentRepository.save(equipment);
    }

    public Page<Equipment> findAllEquipmentPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return equipmentRepository.findAll(pageable);
    }

    public Equipment findEquipmentBySerialNumber(Long equipmentSerialNumber) {
        return equipmentRepository.findByEquipmentSerialNumber(equipmentSerialNumber)
                .orElseThrow(() -> new EntityNotFoundException("No Equipment found with Serial " + equipmentRepository));
    }

    public void deleteEquipmentBySerialNumber(Long equipmentSerialNumber) {
        equipmentRepository.deleteByEquipmentSerialNumber(equipmentSerialNumber);
    }

    public List<Equipment> findEquipmentOfPersonnel(Long personnelId) {
        return equipmentRepository.findByPersonnelId(personnelId);
    }

    public Equipment assignEquipmentToPersonnel(Long equipmentSerialNumber, Long personnelId) {
        Equipment equipment = equipmentRepository.findByEquipmentSerialNumber(equipmentSerialNumber)
                .orElseThrow(() -> new EntityNotFoundException("No Weapon Found By Serial Number : " + equipmentSerialNumber));
        Personnel personnel = personnelRepository.findById(personnelId)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Id : " + personnelId));
        equipment.setPersonnel(personnel);

        return equipmentRepository.save(equipment);
    }

    @Transactional
    public void removePersonnel(Long equipmentSerialNumber) {
        Equipment equipment = equipmentRepository.findByEquipmentSerialNumber(equipmentSerialNumber)
                .orElseThrow(() -> new EntityNotFoundException("No weapon found with Serial : " + equipmentSerialNumber));
        equipment.setPersonnel(null);
        equipmentRepository.save(equipment);
    }
}