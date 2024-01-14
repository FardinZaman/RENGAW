package com.example.RENGAW.service;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.PersonnelMedicalHistory;
import com.example.RENGAW.repository.PersonnelMedicalHistoryRepository;
import com.example.RENGAW.repository.PersonnelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PersonnelMedicalHistoryService{

    @Autowired
    private PersonnelMedicalHistoryRepository personnelMedicalHistoryRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    public PersonnelMedicalHistory savePersonnelMedicalHistory(PersonnelMedicalHistory personnelMedicalHistory, Long personnelId) {
        Personnel personnel = personnelRepository.findById(personnelId)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Id : " + personnelId));
        personnelMedicalHistory.setPersonnel(personnel);

        return personnelMedicalHistoryRepository.save(personnelMedicalHistory);
    }

    public PersonnelMedicalHistory updatePersonnelMedicalHistoryByPersonnelId(PersonnelMedicalHistory personnelMedicalHistory, Long personnelId) {
        PersonnelMedicalHistory personnelMedicalHistoryFromDB = personnelMedicalHistoryRepository.findByPersonnelId(personnelId)
                .orElseThrow(() -> new EntityNotFoundException("No Medical History Found By Personnel Id : " + personnelId));

        personnelMedicalHistoryFromDB.setSurgeries(personnelMedicalHistory.getSurgeries());
        personnelMedicalHistoryFromDB.setChronicIllness(personnelMedicalHistory.getChronicIllness());
        personnelMedicalHistoryFromDB.setMedications(personnelMedicalHistory.getMedications());
        personnelMedicalHistoryFromDB.setAllergies(personnelMedicalHistory.getAllergies());
        personnelMedicalHistoryFromDB.setHazardousExposure(personnelMedicalHistory.getHazardousExposure());
        personnelMedicalHistoryFromDB.setImmunizations(personnelMedicalHistory.getImmunizations());
        personnelMedicalHistoryFromDB.setStressLevels(personnelMedicalHistory.getStressLevels());
        personnelMedicalHistoryFromDB.setMentalHealth(personnelMedicalHistory.getMentalHealth());
        personnelMedicalHistoryFromDB.setLifestyleHabits(personnelMedicalHistory.getLifestyleHabits());

        return personnelMedicalHistoryRepository.save(personnelMedicalHistoryFromDB);
    }

    public List<PersonnelMedicalHistory> findPersonnelMedicalHistoryByName(String personnelName) {
        return personnelMedicalHistoryRepository.findPersonnelMedicalHistoryByName(personnelName);
    }

    public PersonnelMedicalHistory updatePersonnelMedicalHistoryByEmailId(PersonnelMedicalHistory personnelMedicalHistory, String emailId) {
        Personnel personnel = personnelRepository.findByEmail(emailId)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Email : " + emailId));

        return updatePersonnelMedicalHistoryByPersonnelId(personnelMedicalHistory, personnel.getId());
    }

    public List<PersonnelMedicalHistory> showMedicalHistoryByEquipmentMaterialAndCaliberAndName(String materialsUsed, String caliber, String personnelName) {
        return personnelMedicalHistoryRepository.findMedicalHistoryByEquipmentMaterialAndCaliberAndName(materialsUsed, caliber, personnelName);
    }
}
