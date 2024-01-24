package com.example.RENGAW.service;

import com.example.RENGAW.entity.ArmouredCarrier;
import com.example.RENGAW.entity.Weapon;
import com.example.RENGAW.repository.ArmouredCarrierRepository;
import com.example.RENGAW.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void assignCarrierToTeam(Long carrierSerialNumber, Long teamId) {
        ArmouredCarrier armouredCarrier =
                armouredCarrierRepository.findByCarrierSerialNumber(carrierSerialNumber)
                        .orElseThrow(() -> new EntityNotFoundException("No Carrier found with Serial : " + carrierSerialNumber));
        assignCarrierToTeamAndSave(teamId, armouredCarrier);
    }

    public List<ArmouredCarrier> findCarrierByTeamId(Long teamId) {
        return armouredCarrierRepository.findAllByTeamId(teamId);
    }

    public Page<ArmouredCarrier> findAllCarrierPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return armouredCarrierRepository.findAll(pageable);
    }

    public void saveArmouredCarrier(ArmouredCarrier carrier) {
        armouredCarrierRepository.save(carrier);
    }

    public ArmouredCarrier findCarrierBySerialNumber(Long carrierSerialNumber) {
        return armouredCarrierRepository.findByCarrierSerialNumber(carrierSerialNumber)
                .orElseThrow(() -> new EntityNotFoundException("No Carrier Found with Serial : " + carrierSerialNumber));
    }

    public void deleteCarrierBySerialNumber(Long carrierSerialNumber) {
        armouredCarrierRepository.deleteByCarrierSerialNumber(carrierSerialNumber);
    }

    public List<ArmouredCarrier> findCarrierOfTeam(Long teamId) {
        return armouredCarrierRepository.findAllByTeamId(teamId);
    }

    @Transactional
    public void removeTeam(Long carrierSerialNumber) {
        ArmouredCarrier armouredCarrier = armouredCarrierRepository.findByCarrierSerialNumber(carrierSerialNumber)
                .orElseThrow(() -> new EntityNotFoundException("No weapon found with Serial : " + carrierSerialNumber));
        armouredCarrier.setTeam(null);
        armouredCarrierRepository.save(armouredCarrier);
    }
}
