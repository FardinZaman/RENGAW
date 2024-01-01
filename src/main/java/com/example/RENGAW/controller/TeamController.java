package com.example.RENGAW.controller;

import com.example.RENGAW.entity.Team;
import com.example.RENGAW.service.TeamService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
}
