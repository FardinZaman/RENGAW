package com.example.RENGAW.controller;

import com.example.RENGAW.entity.ArmouredCarrier;
import com.example.RENGAW.service.ArmouredCarrierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rengaw")
public class ArmouredCarrierRestController {

    @Autowired
    private ArmouredCarrierService armouredCarrierService;

    @PostMapping("/assignAndSaveCarrier/{tid}")
    public ArmouredCarrier assignCarrierToTeamAndSave(@PathVariable("tid") Long teamId,
                                                      @Valid @RequestBody ArmouredCarrier armouredCarrier){
        return armouredCarrierService.assignCarrierToTeamAndSave(teamId, armouredCarrier);
    }

    @GetMapping("/showCarrier/team/{tid}")
    public List<ArmouredCarrier> findCarrierByTeamId(@PathVariable("tid") Long teamId){
        return armouredCarrierService.findCarrierByTeamId(teamId);
    }
}
