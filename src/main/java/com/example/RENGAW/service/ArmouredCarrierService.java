package com.example.RENGAW.service;

import com.example.RENGAW.entity.ArmouredCarrier;
import com.example.RENGAW.repository.ArmouredCarrierRepository;
import com.example.RENGAW.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmouredCarrierService{

    @Autowired
    private ArmouredCarrierRepository armouredCarrierRepository;

    @Autowired
    private TeamRepository teamRepository;

    public ArmouredCarrier assignCarrierToTeamAndSave(Long teamId, ArmouredCarrier armouredCarrier) {
        armouredCarrier.setTeam(teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team Not Found with Id " + teamId)));

        return armouredCarrierRepository.save(armouredCarrier);
    }

    public List<ArmouredCarrier> findCarrierByTeamId(Long teamId) {
        return armouredCarrierRepository.findByTeamId(teamId);
    }
}
