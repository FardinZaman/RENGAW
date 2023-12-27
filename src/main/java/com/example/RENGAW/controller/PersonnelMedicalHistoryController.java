package com.example.RENGAW.controller;

import com.example.RENGAW.entity.PersonnelMedicalHistory;
import com.example.RENGAW.service.PersonnelMedicalHistoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PersonnelMedicalHistoryController {

    @Autowired
    private PersonnelMedicalHistoryService personnelMedicalHistoryService;

    @PostMapping("/rengaw/savePersonnelMH")
    public PersonnelMedicalHistory savePersonnelMedicalHistory(@RequestParam("pid") String personnelId,
                                                               @Valid @RequestBody PersonnelMedicalHistory personnelMedicalHistory){
        return personnelMedicalHistoryService.savePersonnelMedicalHistory(personnelMedicalHistory, Long.valueOf(personnelId));
    }

    @PutMapping("/rengaw/updatePersonnelMH/{pid}")
    public PersonnelMedicalHistory updatePersonnelMedicalHistoryByPersonnelId(@PathVariable("pid") Long personnelId,
                                                                              @RequestBody PersonnelMedicalHistory personnelMedicalHistory){
        return personnelMedicalHistoryService.updatePersonnelMedicalHistoryByPersonnelId(personnelMedicalHistory, personnelId);
    }
}
