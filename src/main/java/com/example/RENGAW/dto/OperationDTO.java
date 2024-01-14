package com.example.RENGAW.dto;

import com.example.RENGAW.entity.OperationStatus;
import com.example.RENGAW.entity.OperationType;

import java.util.Date;

public class OperationDTO {

    private Long operationId;
    private OperationType operationType;
    private String operationCodeName;
    private OperationStatus operationStatus;
    private String operationZone;
    private Date operationDate;
    private String operationBrief;
    private Long totalCasualties;

    public OperationDTO(Long operationId, OperationType operationType, String operationCodeName, OperationStatus operationStatus, String operationZone, Date operationDate, String operationBrief, Long totalCasualties) {
        this.operationId = operationId;
        this.operationType = operationType;
        this.operationCodeName = operationCodeName;
        this.operationStatus = operationStatus;
        this.operationZone = operationZone;
        this.operationDate = operationDate;
        this.operationBrief = operationBrief;
        this.totalCasualties = totalCasualties;
    }

    public OperationDTO() {
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getOperationCodeName() {
        return operationCodeName;
    }

    public void setOperationCodeName(String operationCodeName) {
        this.operationCodeName = operationCodeName;
    }

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatus operationStatus) {
        this.operationStatus = operationStatus;
    }

    public String getOperationZone() {
        return operationZone;
    }

    public void setOperationZone(String operationZone) {
        this.operationZone = operationZone;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationBrief() {
        return operationBrief;
    }

    public void setOperationBrief(String operationBrief) {
        this.operationBrief = operationBrief;
    }

    public Long getTotalCasualties() {
        return totalCasualties;
    }

    public void setTotalCasualties(Long totalCasualties) {
        this.totalCasualties = totalCasualties;
    }
}
