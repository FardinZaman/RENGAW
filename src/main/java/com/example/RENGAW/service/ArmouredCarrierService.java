package com.example.RENGAW.service;

import com.example.RENGAW.entity.ArmouredCarrier;
import com.example.RENGAW.repository.ArmouredCarrierRepository;
import com.example.RENGAW.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmouredCarrierService{

    private final ArmouredCarrierRepository armouredCarrierRepository;
    private final TeamRepository teamRepository;

    public ArmouredCarrierService(ArmouredCarrierRepository armouredCarrierRepository, TeamRepository teamRepository) {
        this.armouredCarrierRepository = armouredCarrierRepository;
        this.teamRepository = teamRepository;
    }

    public ArmouredCarrier assignCarrierToTeamAndSave(Long teamId, ArmouredCarrier armouredCarrier) {
        armouredCarrier.setTeam(teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team Not Found with Id " + teamId)));

        return armouredCarrierRepository.save(armouredCarrier);
    }

    public List<ArmouredCarrier> findCarrierByTeamId(Long teamId) {
        return armouredCarrierRepository.findByTeamId(teamId);
    }

    public Page<ArmouredCarrier> findAllCarrierPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return armouredCarrierRepository.findAll(pageable);
    }

    public void saveArmouredCarrier(ArmouredCarrier carrier) {
        armouredCarrierRepository.save(carrier);
    }
}
