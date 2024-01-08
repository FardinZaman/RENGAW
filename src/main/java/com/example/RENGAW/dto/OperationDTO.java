package com.example.RENGAW.dto;

import com.example.RENGAW.entity.enumaration.OperationStatus;
import com.example.RENGAW.entity.enumaration.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationDTO {

    private Long operationId;
    private OperationType operationType;
    private String operationCodeName;
    private OperationStatus operationStatus;
    private String operationZone;
    private Date operationDate;
    private String operationBrief;
    private Long totalCasualties;
}
