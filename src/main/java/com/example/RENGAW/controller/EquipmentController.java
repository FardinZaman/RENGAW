package com.example.RENGAW.controller;

import com.example.RENGAW.entity.Equipment;
import com.example.RENGAW.service.EquipmentService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rengaw")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @PostMapping("/saveEquipment")
    public Equipment saveEquipment(@Valid @RequestBody Equipment equipment, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorMsg = bindingResult.getAllErrors().getFirst().getDefaultMessage();
            throw new ValidationException(errorMsg);
        }
        return equipmentService.saveEquipment(equipment);
    }

    @PutMapping("/assignEquipment")
    public Equipment assignEquipmentToPersonnelByPersonnelEmailId(@RequestParam("esn") Long equipmentSerialNumber,
                                                                  @RequestParam("mail") String emailId){
        return equipmentService.assignEquipmentToPersonnelByEmailId(equipmentSerialNumber, emailId);
    }

    @PostMapping("/assignAndSaveEquipment")
    public Equipment assignEquipmentToPersonnelByPersonnelIdAndSaveEquipment(@RequestParam("pid") Long personnelId,
                                                                             @Valid @RequestBody Equipment equipment,
                                                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorMsg = bindingResult.getAllErrors().getFirst().getDefaultMessage();
            throw new ValidationException(errorMsg);
        }
        return equipmentService.assignEquipmentToPersonnelByPersonnelIdAndSaveEquipment(personnelId, equipment);
    }

    @GetMapping("/showEquipment/gunModel/{gunModel}")
    public List<Equipment> findEquipmentUsedByPersonnelFromGunModel(@PathVariable("gunModel") String gunModel){
        return equipmentService.findEquipmentUsedByPersonnelFromGunModel(gunModel);
    }

    @PutMapping("/updateWeight")
    public Equipment updateWeightInGramsByEquipmentSerialNumber(@RequestParam("esn") Long equipmentSerialNumber,
                                                      @RequestParam("weight") double weightInGrams){
        return equipmentService.updateWeightInGramsByEquipmentId(equipmentSerialNumber, weightInGrams);
    }
}
