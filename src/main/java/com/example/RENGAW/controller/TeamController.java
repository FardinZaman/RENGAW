package com.example.RENGAW.controller;

import com.example.RENGAW.entity.Equipment;
import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Team;
import com.example.RENGAW.entity.Weapon;
import com.example.RENGAW.service.TeamService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rengaw")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/createTeam")
    public Team createTeam(@Valid @RequestBody Team team, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorMsg = bindingResult.getAllErrors().getFirst().getDefaultMessage();
            throw new ValidationException(errorMsg);
        }
        return teamService.createTeam(team);
    }

    @PutMapping("/assignToTeam/{tid}/{pid}")
    public Team assignPersonnelToTeam(@PathVariable("tid") Long teamId, @PathVariable("pid") Long personnelId){
        return teamService.assignPersonnelToTeam(teamId, personnelId);
    }

    @GetMapping("/isReady/{tid}")
    public String isTeamReady(@PathVariable("tid") Long teamId){
        return teamService.isTeamReady(teamId);
    }

    @GetMapping("/showTeamMembers/{tid}")
    public List<Personnel> showTeamPersonnelByTeamId(@PathVariable("tid") Long teamId){
        return teamService.showTeamPersonnelByTeamId(teamId);
    }

    @GetMapping("/showWeaponOfTeam/{tid}")
    public List<Weapon> showWeaponsByTeamId(@PathVariable("tid") Long teamId){
        return teamService.showWeaponsByTeamId(teamId);
    }

    @GetMapping("/showEquipmentOfTeam/{tid}")
    public List<Equipment> showEquipmentByTeamId(@PathVariable("tid") Long teamId){
        return teamService.showEquipmentByTeamId(teamId);
    }

    @GetMapping("/showWeapon/equipment/{et}")
    public List<Weapon> showWeaponOfTeamByOneEquipmentType(@PathVariable("et") String equipmentType){
        return teamService.showWeaponOfTeamByOneEquipmentType(equipmentType);
    }
}
