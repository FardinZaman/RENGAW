package com.example.RENGAW.service;

import com.example.RENGAW.entity.Operation;

public interface OperationService {
    public Operation saveOperation(Operation operation);

    public Operation assignTeamToOperationByTeamId(Long operationId, Long teamId);
}
