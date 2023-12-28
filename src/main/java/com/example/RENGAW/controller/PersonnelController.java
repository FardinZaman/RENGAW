package com.example.RENGAW.controller;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.service.PersonnelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rengaw")
public class PersonnelController {

    @Autowired
    private PersonnelService personnelService;

    @PostMapping("/savePersonnel")
    public Personnel savePersonnel(@Valid @RequestBody Personnel personnel){
        return personnelService.savePersonnel(personnel);
    }

    @GetMapping("/findPersonnel/firstName/{firstName}")
    public List<Personnel> findPersonnelByFirstName(@PathVariable("firstName") String firstName){
        return personnelService.findPersonnelByFirstName(firstName);
    }

    @GetMapping("/findPersonnel/lastName/{lastName}")
    public List<Personnel> findPersonnelByLastName(@PathVariable("lastName") String lastName){
        return personnelService.findPersonnelByLastName(lastName);
    }

    @GetMapping("/findPersonnel/name")
    public List<Personnel> findPersonnelByFirstNameAndLastName(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName
    ){
        return personnelService.findPersonnelByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/findStatus")
    public String findPersonnelStatusByEmailId(@RequestParam("mail") String emailId){
        return personnelService.findPersonnelStatusByEmailId(emailId);
    }

    @PutMapping("/changeStatusIfDepressed")
    public void updatePersonnelStatusIfDepressed(){
        personnelService.updatePersonnelStatusIfDepressed();
    }
}
