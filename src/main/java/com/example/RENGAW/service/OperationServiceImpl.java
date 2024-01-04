package com.example.RENGAW.service;

import com.example.RENGAW.entity.Operation;
import com.example.RENGAW.entity.Team;
import com.example.RENGAW.repository.OperationRepository;
import com.example.RENGAW.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl implements OperationService{

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Operation saveOperation(Operation operation) {
        return operationRepository.save(operation);
    }

    @Override
    public Operation assignTeamToOperationByTeamId(Long operationId, Long teamId) {
        Operation operation = operationRepository.findByOperationId(operationId)
                .orElseThrow(() -> new EntityNotFoundException("No Operation Found by Id : " + operationId));
        Team team = teamRepository.findByTeamId(teamId)
                .orElseThrow(() -> new EntityNotFoundException("No Team Found by Id : " + teamId));
        operation.getOperationTeams().add(team);

        return operationRepository.save(operation);
    }
}
