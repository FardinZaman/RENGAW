package com.example.RENGAW.service;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.PersonnelMedicalHistory;
import com.example.RENGAW.repository.PersonnelMedicalHistoryRepository;
import com.example.RENGAW.repository.PersonnelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PersonnelMedicalHistoryServiceImpl implements PersonnelMedicalHistoryService{

    @Autowired
    private PersonnelMedicalHistoryRepository personnelMedicalHistoryRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Override
    public PersonnelMedicalHistory savePersonnelMedicalHistory(PersonnelMedicalHistory personnelMedicalHistory, Long personnelId) {
        Personnel personnel = personnelRepository.findByPersonnelId(personnelId).orElseThrow(EntityNotFoundException::new);
        personnelMedicalHistory.setPersonnel(personnel);

        return personnelMedicalHistoryRepository.save(personnelMedicalHistory);
    }

    @Override
    public PersonnelMedicalHistory updatePersonnelMedicalHistoryByPersonnelId(PersonnelMedicalHistory personnelMedicalHistory, Long personnelId) {
        PersonnelMedicalHistory personnelMedicalHistoryFromDB = personnelMedicalHistoryRepository.findByPersonnelPersonnelId(personnelId).orElseThrow(EntityNotFoundException::new);

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
}
