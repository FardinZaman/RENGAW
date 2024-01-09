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
public class PersonnelMedicalHistoryServiceImpl implements PersonnelMedicalHistoryService{

    @Autowired
    private PersonnelMedicalHistoryRepository personnelMedicalHistoryRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Override
    public PersonnelMedicalHistory savePersonnelMedicalHistory(PersonnelMedicalHistory personnelMedicalHistory, Long personnelId) {
        Personnel personnel = personnelRepository.findByPersonnelId(personnelId)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Id : " + personnelId));
        personnelMedicalHistory.setPersonnel(personnel);

        return personnelMedicalHistoryRepository.save(personnelMedicalHistory);
    }

    @Override
    public PersonnelMedicalHistory updatePersonnelMedicalHistoryByPersonnelId(PersonnelMedicalHistory personnelMedicalHistory, Long personnelId) {
        PersonnelMedicalHistory personnelMedicalHistoryFromDB = personnelMedicalHistoryRepository.findByPersonnelPersonnelId(personnelId)
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

    @Override
    public List<PersonnelMedicalHistory> findPersonnelMedicalHistoryByName(String personnelName) {
        List<PersonnelMedicalHistory> personnelMedicalHistoryList = personnelMedicalHistoryRepository.findPersonnelMedicalHistoryByName(personnelName);

        if(personnelMedicalHistoryList.isEmpty()){
            throw new EntityNotFoundException("No Medical History with Name : " + personnelName);
        }

        return personnelMedicalHistoryList;
    }

    @Override
    public PersonnelMedicalHistory updatePersonnelMedicalHistoryByEmailId(PersonnelMedicalHistory personnelMedicalHistory, String emailId) {
        Personnel personnel = personnelRepository.findByEmailId(emailId)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Email : " + emailId));

        return updatePersonnelMedicalHistoryByPersonnelId(personnelMedicalHistory, personnel.getPersonnelId());
    }

    @Override
    public List<PersonnelMedicalHistory> showMedicalHistoryByEquipmentMaterialAndCaliberAndName(String materialsUsed, String caliber, String personnelName) {
        List<PersonnelMedicalHistory> personnelMedicalHistoryList =
                personnelMedicalHistoryRepository.findMedicalHistoryByEquipmentMaterialAndCaliberAndName(materialsUsed, caliber, personnelName);

        if(personnelMedicalHistoryList.isEmpty()){
            throw new EntityNotFoundException("No Personnel Medical History matches following requirements : " +
                    "\nMaterials : " + materialsUsed +
                    "\nCaliber : " + caliber +
                    "\nPersonnel Name : " + personnelName);
        }

        return personnelMedicalHistoryList;
    }
}
