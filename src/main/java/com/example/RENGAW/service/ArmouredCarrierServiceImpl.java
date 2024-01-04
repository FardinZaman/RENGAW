package com.example.RENGAW.service;

import com.example.RENGAW.entity.ArmouredCarrier;
import com.example.RENGAW.repository.ArmouredCarrierRepository;
import com.example.RENGAW.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmouredCarrierServiceImpl implements ArmouredCarrierService{

    @Autowired
    private ArmouredCarrierRepository armouredCarrierRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public ArmouredCarrier assignCarrierToTeamAndSave(Long teamId, ArmouredCarrier armouredCarrier) {
        armouredCarrier.setTeam(teamRepository.findByTeamId(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team Not Found with Id " + teamId)));

        return armouredCarrierRepository.save(armouredCarrier);
    }

    @Override
    public List<ArmouredCarrier> findCarrierByTeamId(Long teamId) {
        List<ArmouredCarrier> armouredCarrierList = armouredCarrierRepository.findByTeamTeamId(teamId);

        if(armouredCarrierList.isEmpty()){
            throw new EntityNotFoundException("No Carrier Found with Team Id : " + teamId);
        }

        return armouredCarrierList;
    }
}
