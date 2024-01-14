package com.example.RENGAW.service;

import com.example.RENGAW.entity.Equipment;
import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.repository.EquipmentRepository;
import com.example.RENGAW.repository.PersonnelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService{

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

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
}