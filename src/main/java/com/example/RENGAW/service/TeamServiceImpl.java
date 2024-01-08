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
public class TeamServiceImpl implements TeamService{

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public boolean isReady(Team team){
        return team.isHasBallisticExpert() && team.isHasExplosiveExpert() && team.isHasTechnologyExpert() &&
                (Objects.equals(team.getTeamMemberCount(), personnelRepository.countByTeamTeamId(team.getTeamId()))) &&
                personnelRepository.checkAllStatusAvailableByTeamId(team.getTeamId());
    }

    @Override
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

    @Override
    public Team createTeam(Team team) {
        return teamRepository.save(assignPersonnelToTeam(team, team.getCurrentLeadId()));
    }

    public Team assignPersonnelToTeam(Team team, Long personnelId){
        Personnel personnel = personnelRepository.findByPersonnelId(personnelId)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Id : " + personnelId));

        if(team.getTeamId() != null &&
                (Objects.equals(team.getTeamMemberCount(), personnelRepository.countByTeamTeamId(team.getTeamId())))){
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

    @Override
    public Team assignPersonnelToTeamByTeamId(Long teamId, Long personnelId) {
        Team team = teamRepository.findByTeamId(teamId)
                .orElseThrow(() -> new EntityNotFoundException("No Team Found By Id : " + teamId));

        return assignPersonnelToTeam(team, personnelId);
    }

    @Override
    public Team assignPersonnelToTeamByTeamCodeName(String teamCodeName, Long personnelId) {
        Team team = teamRepository.findByTeamCodeName(teamCodeName)
                .orElseThrow(() -> new EntityNotFoundException("No Team Found By Code : " + teamCodeName));

        return assignPersonnelToTeam(team, personnelId);
    }

    @Override
    public String isTeamReady(Long teamId) {
        Team team = teamRepository.findByTeamId(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team Not Found With Id" + teamId));
        if(isReady(team)){
            return "Team " + team.getTeamCodeName() + " is ready";
        }
        else
        {
            return "Team " + team.getTeamCodeName() + " is not ready";
        }
    }

    @Override
    public List<Personnel> showTeamPersonnelByTeamId(Long teamId) {
        List<Personnel> personnelList = personnelRepository.findAllByTeamTeamId(teamId);

        if(personnelList.isEmpty()){
            throw new EntityNotFoundException("No Personnel Found of Team Id : " + teamId);
        }

        return personnelList;
    }

    @Override
    public List<Weapon> showWeaponsByTeamId(Long teamId) {
        List<Weapon> weaponList = teamRepository.findWeaponUsedByTeamPersonnelByTeamId(teamId);

        if(weaponList.isEmpty()){
            throw new EntityNotFoundException("No Weapon Found of Team Id : " + teamId);
        }

        return weaponList;
    }

    @Override
    public List<Equipment> showEquipmentByTeamId(Long teamId) {
        List<Equipment> equipmentList = teamRepository.findEquipmentUsedByTeamPersonnelByTeamId(teamId);

        if(equipmentList.isEmpty()){
            throw new EntityNotFoundException("No Equipment Found of Team Id : " + teamId);
        }

        return equipmentList;
    }

    @Override
    public List<Weapon> showWeaponOfTeamByOneEquipmentType(String equipmentType) {
        Equipment equipment = equipmentRepository.findByEquipmentTypeContainingIgnoreCase(equipmentType)
                .orElseThrow(() -> new EntityNotFoundException("No Equipment Found By Type : " + equipmentType));
        Personnel personnel = personnelRepository.findByPersonnelId(equipment.getPersonnel().getPersonnelId())
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Id : " + equipment.getPersonnel().getPersonnelId()));

        return teamRepository.findWeaponUsedByTeamPersonnelByTeamId(personnel.getTeam().getTeamId());
    }

}
