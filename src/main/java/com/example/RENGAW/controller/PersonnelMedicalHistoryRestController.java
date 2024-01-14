package com.example.RENGAW.controller;

import com.example.RENGAW.entity.PersonnelMedicalHistory;
import com.example.RENGAW.service.PersonnelMedicalHistoryService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rengaw")
@Slf4j
public class PersonnelMedicalHistoryRestController {

    @Autowired
    private PersonnelMedicalHistoryService personnelMedicalHistoryService;

    @PostMapping("/savePersonnelMH")
    public PersonnelMedicalHistory savePersonnelMedicalHistory(@RequestParam("pid") String personnelId,
                                                               @Valid @RequestBody PersonnelMedicalHistory personnelMedicalHistory,
                                                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorMsg = bindingResult.getAllErrors().getFirst().getDefaultMessage();
            throw new ValidationException(errorMsg);
        }
        return personnelMedicalHistoryService.savePersonnelMedicalHistory(personnelMedicalHistory, Long.valueOf(personnelId));
    }

    @PutMapping("/updatePersonnelMH/{pid}")
    public PersonnelMedicalHistory updatePersonnelMedicalHistoryByPersonnelId(@PathVariable("pid") Long personnelId,
                                                                              @Valid @RequestBody PersonnelMedicalHistory personnelMedicalHistory,
                                                                              BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorMsg = bindingResult.getAllErrors().getFirst().getDefaultMessage();
            throw new ValidationException(errorMsg);
        }
        return personnelMedicalHistoryService.updatePersonnelMedicalHistoryByPersonnelId(personnelMedicalHistory, personnelId);
    }

    @GetMapping("/findPersonnelMH/name/{name}")
    public List<PersonnelMedicalHistory> findPersonnelMedicalHistoryByName(@PathVariable("name") String personnelName){
        return personnelMedicalHistoryService.findPersonnelMedicalHistoryByName(personnelName);
    }


    @PutMapping("/updatePersonnelMH/mail")
    public PersonnelMedicalHistory updatePersonnelMedicalHistoryByEmailId(@RequestParam("mail") String emailId,
                                                                          @Valid @RequestBody PersonnelMedicalHistory personnelMedicalHistory,
                                                                          BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorMsg = bindingResult.getAllErrors().getFirst().getDefaultMessage();
            throw new ValidationException(errorMsg);
        }
        return personnelMedicalHistoryService.updatePersonnelMedicalHistoryByEmailId(personnelMedicalHistory, emailId);
    }

    @GetMapping("/showMH/mat/{mat}/cal/{cal}/name/{name}")
    public List<PersonnelMedicalHistory> showMedicalHistoryByEquipmentMaterialAndCaliberAndName(@PathVariable("mat") String materialsUsed,
                                                                                          @PathVariable("cal") String caliber,
                                                                                          @PathVariable("name") String personnelName){
        return personnelMedicalHistoryService.showMedicalHistoryByEquipmentMaterialAndCaliberAndName(materialsUsed, caliber, personnelName);
    }
}
