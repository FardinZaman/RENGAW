package com.example.RENGAW.controller;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Weapon;
import com.example.RENGAW.service.WeaponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rengaw")
public class WeaponController {

    @Autowired
    private WeaponService weaponService;

    @PostMapping("/saveWeapon")
    public Weapon saveWeapon(@Valid @RequestBody Weapon weapon){
        return weaponService.saveWeapon(weapon);
    }

    @PutMapping("/assignWeapon")
    public Weapon assignWeaponToPersonnelByPersonnelId(@RequestParam("wsn") Long weaponSerialNumber,
                                                       @RequestParam("pid") Long personnelId){
        return weaponService.assignWeaponToPersonnelByPersonnelId(weaponSerialNumber, personnelId);
    }

    @PostMapping("/assignAndSaveWeapon")
    public Weapon assignWeaponToPersonnelByEmailIdAndSaveWeapon(@RequestParam("mail") String emailId,
                                                                @Valid @RequestBody Weapon weapon)
    {
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
}
