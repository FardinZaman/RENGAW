package com.example.RENGAW.service;

import com.example.RENGAW.entity.PersonnelMedicalHistory;

public interface PersonnelMedicalHistoryService {
    public PersonnelMedicalHistory savePersonnelMedicalHistory(PersonnelMedicalHistory personnelMedicalHistory, Long personnelId);

    public PersonnelMedicalHistory updatePersonnelMedicalHistoryByPersonnelId(PersonnelMedicalHistory personnelMedicalHistory, Long personnelId);
}
