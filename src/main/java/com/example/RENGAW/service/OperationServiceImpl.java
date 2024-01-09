package com.example.RENGAW.service;

import com.example.RENGAW.dto.OperationDTO;
import com.example.RENGAW.dto.mapper.OperationDTOMapper;
import com.example.RENGAW.email.EmailSenderService;
import com.example.RENGAW.entity.Operation;
import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Team;
import com.example.RENGAW.entity.enumaration.OperationType;
import com.example.RENGAW.exception.TeamNotReadyException;
import com.example.RENGAW.repository.OperationRepository;
import com.example.RENGAW.repository.PersonnelRepository;
import com.example.RENGAW.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OperationServiceImpl implements OperationService{

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private EmailSenderService emailSenderService;

    private OperationDTOMapper operationDTOMapper;

    public void sendEmailToTeamPersonnel(Team team, Operation operation) {
        List<Personnel> personnelList = personnelRepository.findAllByTeamTeamId(team.getTeamId());
        String operationMessage = "New Operation Assigned.\n" +
                operation.toString() +
                "\n\nContact your command.";

        for (Personnel personnel:personnelList){
            String mailBody = personnel.getCurrentRank() + " " + personnel.getFirstName() + " " + personnel.getLastName() + "\n\n" + operationMessage;
            emailSenderService.sendEmail(personnel.getEmailId(), "Operation Details", mailBody);
        }

        System.out.println("Mail Sent To Personnels");
    }

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

        if(teamService.isReady(team)){
            operation.getOperationTeams().add(team);
        }
        else {
            throw new TeamNotReadyException("Team " + team.getTeamCodeName() + " is not ready");
        }
        sendEmailToTeamPersonnel(team, operation);

        return operationRepository.save(operation);
    }

    @Override
    public OperationDTO showOperationByOperationId(Long operationId) {
        Operation operation = operationRepository.findByOperationId(operationId)
                .orElseThrow(() -> new EntityNotFoundException("No Operation Found by Id : " + operationId));
        operationDTOMapper = new OperationDTOMapper();

        return operationDTOMapper.apply(operation);
    }

    @Override
    public List<OperationDTO> showOperationByOperationType(String operationType) {
        List<Operation> operationList = operationRepository.findByOperationType(OperationType.valueOf(operationType.toUpperCase()));

        if(operationList.isEmpty()){
            throw new EntityNotFoundException("No Operation Found with Type : " + operationType);
        }

        return operationList.stream()
                .map(new OperationDTOMapper())
                .collect(Collectors.toList());
    }

    @Override
    public List<OperationDTO> showOperationByTeamCodeName(String teamCodeName) {
        List<Operation> operationList = operationRepository.findByTeamCodeName(teamCodeName);

        if(operationList.isEmpty()){
            throw new EntityNotFoundException("No Operation Found with team : " + teamCodeName);
        }

        return operationList.stream()
                .map(new OperationDTOMapper())
                .collect(Collectors.toList());
    }

    @Override
    public List<Team> showTeamsAssignedToOperation(String operationCodeName) {
        List<Team> teamList = operationRepository.findTeamByOperationCodeName(operationCodeName);

        if(teamList.isEmpty()){
            throw new EntityNotFoundException("No Team Found with Operation Code : " + operationCodeName);
        }

        return teamList;
    }
}
