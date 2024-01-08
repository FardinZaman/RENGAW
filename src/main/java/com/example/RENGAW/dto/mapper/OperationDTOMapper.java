package com.example.RENGAW.dto.mapper;

import com.example.RENGAW.dto.OperationDTO;
import com.example.RENGAW.entity.Operation;

import java.util.function.Function;

public class OperationDTOMapper implements Function<Operation, OperationDTO> {
    @Override
    public OperationDTO apply(Operation operation) {
        return new OperationDTO(
                operation.getOperationId(),
                operation.getOperationType(),
                operation.getOperationCodeName(),
                operation.getOperationStatus(),
                operation.getOperationZone(),
                operation.getOperationDate(),
                operation.getOperationBrief(),
                operation.getTotalCasualties()
        );
    }
}
