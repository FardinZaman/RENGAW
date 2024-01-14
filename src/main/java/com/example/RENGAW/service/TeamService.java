package com.example.RENGAW.service;

import com.example.RENGAW.entity.Equipment;
import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Team;
import com.example.RENGAW.entity.Weapon;
import com.example.RENGAW.exception.PersonnelAlreadyAssignedException;
import com.example.RENGAW.exception.TeamFullException;
import com.example.RENGAW.repository.EquipmentRepository;
import com.example.RENGAW.repository.PersonnelRepository;
import com.example.RENGAW.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class TeamService{

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    public boolean isReady(Team team){
        return team.isHasBallisticExpert() && team.isHasExplosiveExpert() && team.isHasTechnologyExpert() &&
                (Objects.equals(team.getTeamMemberCount(), personnelRepository.countByTeamId(team.getId()))) &&
                personnelRepository.checkAllStatusAvailableByTeamId(team.getId());
    }

    public void modifyTeamExpertiseStatus(Personnel personnel, Team team) {
        for(String s: personnel.getExpertise()){
            if(s.equalsIgnoreCase("explosive")){
                team.setHasExplosiveExpert(true);
            } else if (s.equalsIgnoreCase("ballistic")) {
                team.setHasBallisticExpert(true);
            } else if (s.equalsIgnoreCase("technology")){
                team.setHasTechnologyExpert(true);
            } else {
                boolean notInList = team.getAdditionalExpertise().stream()
                        .noneMatch(str -> str.equalsIgnoreCase(s));
                if(notInList)
                    team.getAdditionalExpertise().add(s);
            }
        }
    }

    public Team createTeam(Team team) {
        return teamRepository.save(assignPersonnelToTeam(team, team.getCurrentLeadId()));
    }

    public Team assignPersonnelToTeam(Team team, Long personnelId){
        Personnel personnel = personnelRepository.findById(personnelId)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Id : " + personnelId));

        if(team.getId() != null &&
                (Objects.equals(team.getTeamMemberCount(), personnelRepository.countByTeamId(team.getId())))){
            throw new TeamFullException("Team " + team.getTeamCodeName() + " is already full");
        }

        if(personnel.getTeam() != null){
            throw new PersonnelAlreadyAssignedException(personnel.getFirstName() + " " +
                    personnel.getLastName() + " already assigned to team " +
                    personnel.getTeam().getTeamCodeName());
        }

        if(personnel.getExpertise() != null){
            modifyTeamExpertiseStatus(personnel, team);
        }

        personnel.setTeam(team);
        personnelRepository.save(personnel);

        return team;
    }

    public Team assignPersonnelToTeamByTeamId(Long teamId, Long personnelId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("No Team Found By Id : " + teamId));

        return assignPersonnelToTeam(team, personnelId);
    }

    public Team assignPersonnelToTeamByTeamCodeName(String teamCodeName, Long personnelId) {
        Team team = teamRepository.findByTeamCodeName(teamCodeName)
                .orElseThrow(() -> new EntityNotFoundException("No Team Found By Code : " + teamCodeName));

        return assignPersonnelToTeam(team, personnelId);
    }

    public String isTeamReady(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team Not Found With Id" + teamId));
        if(isReady(team)){
            return "Team " + team.getTeamCodeName() + " is ready";
        }
        else
        {
            return "Team " + team.getTeamCodeName() + " is not ready";
        }
    }

    public List<Personnel> showTeamPersonnelByTeamId(Long teamId) {
        return personnelRepository.findAllByTeamId(teamId);
    }

    public List<Weapon> showWeaponsByTeamId(Long teamId) {
        return teamRepository.findWeaponUsedByTeamPersonnelByTeamId(teamId);
    }

    public List<Equipment> showEquipmentByTeamId(Long teamId) {
        return teamRepository.findEquipmentUsedByTeamPersonnelByTeamId(teamId);
    }

    public List<Weapon> showWeaponOfTeamByOneEquipmentType(String equipmentType) {
        Equipment equipment = equipmentRepository.findByEquipmentTypeContainingIgnoreCase(equipmentType)
                .orElseThrow(() -> new EntityNotFoundException("No Equipment Found By Type : " + equipmentType));
        Personnel personnel = personnelRepository.findById(equipment.getPersonnel().getId())
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Id : " + equipment.getPersonnel().getId()));

        return teamRepository.findWeaponUsedByTeamPersonnelByTeamId(personnel.getTeam().getId());
    }

}
