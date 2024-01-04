package com.example.RENGAW.controller;

import com.example.RENGAW.entity.Operation;
import com.example.RENGAW.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rengaw")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @PostMapping("/saveOperation")
    public Operation saveOperation(@RequestBody Operation operation){
        return operationService.saveOperation(operation);
    }

    @PutMapping("/assignToOp/{oid}/team/{tid}")
    public Operation assignTeamToOperationByTeamId(@PathVariable("oid") Long operationId,
                                                   @PathVariable("tid") Long teamId){
        return operationService.assignTeamToOperationByTeamId(operationId, teamId);
    }
}
