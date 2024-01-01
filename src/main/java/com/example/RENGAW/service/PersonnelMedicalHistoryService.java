package com.example.RENGAW.service;

import com.example.RENGAW.entity.PersonnelMedicalHistory;

import java.util.List;

public interface PersonnelMedicalHistoryService {
    public PersonnelMedicalHistory savePersonnelMedicalHistory(PersonnelMedicalHistory personnelMedicalHistory, Long personnelId);

    public PersonnelMedicalHistory updatePersonnelMedicalHistoryByPersonnelId(PersonnelMedicalHistory personnelMedicalHistory, Long personnelId);

    public List<PersonnelMedicalHistory> findPersonnelMedicalHistoryByName(String personnelName);

    public PersonnelMedicalHistory updatePersonnelMedicalHistoryByEmailId(PersonnelMedicalHistory personnelMedicalHistory, String emailId);

    public List<PersonnelMedicalHistory> showMedicalHistoryByEquipmentMaterialAndCaliberAndName(String materialsUsed, String caliber, String personnelName);
}
