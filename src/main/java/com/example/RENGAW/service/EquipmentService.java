package com.example.RENGAW.service;

import com.example.RENGAW.entity.Equipment;

import java.util.List;

public interface EquipmentService {
    public Equipment saveEquipment(Equipment equipment);

    public Equipment assignEquipmentToPersonnelByEmailId(Long equipmentSerialNumber, String emailId);

    public Equipment assignEquipmentToPersonnelByPersonnelIdAndSaveEquipment(Long personnelId, Equipment equipment);

    public List<Equipment> findEquipmentUsedByPersonnelFromGunModel(String gunModel);

    public Equipment updateWeightInGramsByEquipmentId(Long equipmentSerialNumber, double weightInGrams);
}
