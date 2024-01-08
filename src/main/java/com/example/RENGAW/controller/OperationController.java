package com.example.RENGAW.controller;

import com.example.RENGAW.dto.OperationDTO;
import com.example.RENGAW.entity.Operation;
import com.example.RENGAW.service.OperationService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rengaw")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @PostMapping("/saveOperation")
    public Operation saveOperation(@Valid @RequestBody Operation operation,
                                   BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorMsg = bindingResult.getAllErrors().getFirst().getDefaultMessage();
            throw new ValidationException(errorMsg);
        }
        return operationService.saveOperation(operation);
    }

    @PutMapping("/assignToOp/{oid}/team/{tid}")
    public Operation assignTeamToOperationByTeamId(@PathVariable("oid") Long operationId,
                                                   @PathVariable("tid") Long teamId){
        return operationService.assignTeamToOperationByTeamId(operationId, teamId);
    }

    @GetMapping("/showOP/{oid}")
    public OperationDTO showOperationByOperationId(@PathVariable("oid") Long operationId){
        return operationService.showOperationByOperationId(operationId);
    }

    @GetMapping("/showOP/type/{opType}")
    public List<OperationDTO> showOperationByOperationType(@PathVariable("opType") String operationType){
        return operationService.showOperationByOperationType(operationType);
    }

    @GetMapping("/showOP/team/{tcn}")
    public List<OperationDTO> showOperationByTeamCodeName(@PathVariable("tcn") String teamCodeName){
        return operationService.showOperationByTeamCodeName(teamCodeName);
    }
}
