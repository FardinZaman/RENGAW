package com.example.RENGAW.service;

import com.example.RENGAW.dto.OperationDTO;
import com.example.RENGAW.dto.mapper.OperationDTOMapper;
import com.example.RENGAW.email.EmailSenderService;
import com.example.RENGAW.entity.Operation;
import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Team;
import com.example.RENGAW.entity.OperationStatus;
import com.example.RENGAW.entity.OperationType;
import com.example.RENGAW.exception.DateNotValidException;
import com.example.RENGAW.exception.TeamNotReadyException;
import com.example.RENGAW.repository.OperationRepository;
import com.example.RENGAW.repository.PersonnelRepository;
import com.example.RENGAW.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationService{

    private final OperationRepository operationRepository;
    private final PersonnelRepository personnelRepository;
    private final TeamRepository teamRepository;
    private final TeamService teamService;
    private final EmailSenderService emailSenderService;
    private OperationDTOMapper operationDTOMapper;

    public OperationService(OperationRepository operationRepository, PersonnelRepository personnelRepository, TeamRepository teamRepository, TeamService teamService, EmailSenderService emailSenderService) {
        this.operationRepository = operationRepository;
        this.personnelRepository = personnelRepository;
        this.teamRepository = teamRepository;
        this.teamService = teamService;
        this.emailSenderService = emailSenderService;
    }

    public void sendEmailToTeamPersonnel(Team team, Operation operation) {
        List<Personnel> personnelList = personnelRepository.findAllByTeamId(team.getId());
        String operationMessage = "New Operation Assigned.\n" +
                operation.toString() +
                "\n\nContact your command.";

        for (Personnel personnel:personnelList){
            String mailBody = personnel.getCurrentRank() + " " + personnel.getFirstName() + " " + personnel.getLastName() + "\n\n" + operationMessage;
            emailSenderService.sendEmail(personnel.getEmail(), "Operation Details", mailBody);
        }

        System.out.println("Mail Sent To Personnels");
    }

    public boolean checkIfDateConflict(Operation operation1, Operation operation2){
        return operation2.getOperationStatus() == OperationStatus.valueOf("ACTIVE") &&
                operation1.getOperationStatus() == OperationStatus.valueOf("ACTIVE") &&
                operation1.getOperationDate().equals(operation2.getOperationDate());
    }

    private void checkConflictWithActiveOperations(Operation operation, Team team) {
        List<Operation> operationListFromDB = operationRepository.findByTeamCodeName(team.getTeamCodeName());
        for(Operation operationFromDB:operationListFromDB){
            if(checkIfDateConflict(operationFromDB, operation)){
                throw new DateNotValidException("Team " + team.getTeamCodeName() + " has an assigned" +
                        " ACTIVE operation on Date : " + operation.getOperationDate());
            }
        }
    }

    public Operation saveOperation(Operation operation) {
        return operationRepository.save(operation);
    }

    public Operation assignTeamToOperationByTeamId(Long operationId, Long teamId) {
        Operation operation = operationRepository.findById(operationId)
                .orElseThrow(() -> new EntityNotFoundException("No Operation Found by Id : " + operationId));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("No Team Found by Id : " + teamId));

        checkConflictWithActiveOperations(operation, team);

        if(teamService.isReady(team)){
            operation.getTeam().add(team);
        }
        else {
            throw new TeamNotReadyException("Team " + team.getTeamCodeName() + " is not ready");
        }
        sendEmailToTeamPersonnel(team, operation);

        return operationRepository.save(operation);
    }

    public OperationDTO showOperationByOperationId(Long operationId) {
        Operation operation = operationRepository.findById(operationId)
                .orElseThrow(() -> new EntityNotFoundException("No Operation Found by Id : " + operationId));
        operationDTOMapper = new OperationDTOMapper();

        return operationDTOMapper.apply(operation);
    }

    public List<OperationDTO> showOperationByOperationType(String operationType) {
        List<Operation> operationList = operationRepository.findByOperationType(OperationType.valueOf(operationType.toUpperCase()));

        return operationList.stream()
                .map(new OperationDTOMapper())
                .collect(Collectors.toList());
    }

    public List<OperationDTO> showOperationByTeamCodeName(String teamCodeName) {
        List<Operation> operationList = operationRepository.findByTeamCodeName(teamCodeName);

        return operationList.stream()
                .map(new OperationDTOMapper())
                .collect(Collectors.toList());
    }

    public List<Team> showTeamsAssignedToOperation(String operationCodeName) {
        return operationRepository.findTeamByOperationCodeName(operationCodeName);
    }
}
