package com.example.RENGAW.controller;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.PersonnelMedicalHistory;
import com.example.RENGAW.entity.Weapon;
import com.example.RENGAW.service.WeaponService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rengaw")
public class WeaponController {

    @Autowired
    private WeaponService weaponService;

    @PostMapping("/saveWeapon")
    public Weapon saveWeapon(@Valid @RequestBody Weapon weapon, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorMsg = bindingResult.getAllErrors().getFirst().getDefaultMessage();
            throw new ValidationException(errorMsg);
        }
        return weaponService.saveWeapon(weapon);
    }

    @PutMapping("/assignWeapon")
    public Weapon assignWeaponToPersonnelByPersonnelId(@RequestParam("wsn") Long weaponSerialNumber,
                                                       @RequestParam("pid") Long personnelId){
        return weaponService.assignWeaponToPersonnelByPersonnelId(weaponSerialNumber, personnelId);
    }

    @PostMapping("/assignAndSaveWeapon")
    public Weapon assignWeaponToPersonnelByEmailIdAndSaveWeapon(@RequestParam("mail") String emailId,
                                                                @Valid @RequestBody Weapon weapon,
                                                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            String errorMsg = bindingResult.getAllErrors().getFirst().getDefaultMessage();
            throw new ValidationException(errorMsg);
        }
        return weaponService.assignWeaponToPersonnelByEmailIdAndSaveWeapon(emailId, weapon);
    }

    @GetMapping("/showWeaponUser/{wsn}")
    public Personnel findWeaponUserByWeaponSerialNumber(@PathVariable("wsn") Long weaponSerialNumber){
        return weaponService.findWeaponUserByWeaponSerialNumber(weaponSerialNumber);
    }

    @GetMapping("/showUsersWeapon")
    public List<Weapon> findWeaponUsedByPersonnelByEmailId(@RequestParam("mail") String emailId){
        return weaponService.findWeaponUsedByPersonnelByEmailId(emailId);
    }

    @GetMapping("/showUsersWeaponById")
    public List<Weapon> findWeaponUsedByPersonnelByPersonnelId(@RequestParam("pid") Long personnelId){
        return weaponService.findWeaponUsedByPersonnelByPersonnelId(personnelId);
    }

    @GetMapping("/showUsersOfCompany")
    public List<Personnel> findWeaponUserByWeaponProductionCompany(@RequestParam("comp") String productionCompany){
        return weaponService.findWeaponUserByWeaponProductionCompany(productionCompany);
    }

    @GetMapping("/showUsersOfCaliber")
    public List<Personnel> findWeaponUserByWeaponBulletCaliber(@RequestParam("d") String diameter,
                                                               @RequestParam("l") String length){
        return weaponService.findWeaponUserByWeaponBulletCaliber(diameter, length);
    }

    @GetMapping("/showPMHByGunType")
    public List<PersonnelMedicalHistory> findPersonnelMedicalHistoryByGunType(@RequestParam("gunType") String gunType){
        return weaponService.findPersonnelMedicalHistoryByGunType(gunType);
    }
}
