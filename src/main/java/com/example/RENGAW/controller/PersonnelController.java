package com.example.RENGAW.controller;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.service.PersonnelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonnelController {

    @Autowired
    private PersonnelService personnelService;

    @PostMapping("/rengaw/savePersonnel")
    public Personnel savePersonnel(@Valid @RequestBody Personnel personnel){
        return personnelService.savePersonnel(personnel);
    }

    @GetMapping("/rengaw/findPersonnel/firstName/{firstName}")
    public List<Personnel> findPersonnelByFirstName(@PathVariable("firstName") String firstName){
        return personnelService.findPersonnelByFirstName(firstName);
    }

    @GetMapping("/rengaw/findPersonnel/lastName/{lastName}")
    public List<Personnel> findPersonnelByLastName(@PathVariable("lastName") String lastName){
        return personnelService.findPersonnelByLastName(lastName);
    }

    @GetMapping("rengaw/findPersonnel/name")
    public List<Personnel> findPersonnelByFirstNameAndLastName(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName
    ){
        return personnelService.findPersonnelByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/rengaw/findStatus")
    public String findPersonnelStatusByEmailId(@RequestParam("mail") String emailId){
        return personnelService.findPersonnelStatusByEmailId(emailId);
    }
}
