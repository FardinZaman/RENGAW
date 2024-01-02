package com.example.RENGAW.service;

import com.example.RENGAW.entity.Equipment;
import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Team;
import com.example.RENGAW.entity.Weapon;
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
        return teamRepository.save(team);
    }

    @Override
    public Team assignPersonnelToTeam(Long teamId, Long personnelId) {
        Personnel personnel = personnelRepository.findByPersonnelId(personnelId).orElseThrow(EntityNotFoundException::new);
        Team team = teamRepository.findByTeamId(teamId).orElseThrow(EntityNotFoundException::new);

        personnel.setTeam(team);
        personnelRepository.save(personnel);

        if(personnel.getExpertise() != null){
            modifyTeamExpertiseStatus(personnel, team);
        }

        return teamRepository.save(team);
    }

    @Override
    public String isTeamReady(Long teamId) {
        Team team = teamRepository.findByTeamId(teamId).orElseThrow(() -> new EntityNotFoundException("Team Not Found With Id" + teamId));
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
        return personnelRepository.findAllByTeamTeamId(teamId);
    }

    @Override
    public List<Weapon> showWeaponsByTeamId(Long teamId) {
        return teamRepository.findWeaponUsedByTeamPersonnelByTeamId(teamId);
    }

    @Override
    public List<Equipment> showEquipmentByTeamId(Long teamId) {
        return teamRepository.findEquipmentUsedByTeamPersonnelByTeamId(teamId);
    }

    @Override
    public List<Weapon> showWeaponOfTeamByOneEquipmentType(String equipmentType) {
        log.info("equipmentType : " + equipmentType);
        Equipment equipment = equipmentRepository.findByEquipmentTypeContainingIgnoreCase(equipmentType);
//        log.info(equipment.toString());
        Personnel personnel = personnelRepository.findByPersonnelId(equipment.getPersonnel().getPersonnelId())
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Id : " + equipment.getPersonnel().getPersonnelId()));

        return teamRepository.findWeaponUsedByTeamPersonnelByTeamId(personnel.getTeam().getTeamId());
    }
}
