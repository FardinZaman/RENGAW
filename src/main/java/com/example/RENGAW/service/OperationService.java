package com.example.RENGAW.service;

import com.example.RENGAW.dto.OperationDTO;
import com.example.RENGAW.entity.Operation;

import java.util.List;
import java.util.Optional;

public interface OperationService {
    public Operation saveOperation(Operation operation);

    public Operation assignTeamToOperationByTeamId(Long operationId, Long teamId);

    public OperationDTO showOperationByOperationId(Long operationId);

    public List<OperationDTO> showOperationByOperationType(String operationType);

    public List<OperationDTO> showOperationByTeamCodeName(String teamCodeName);
}
