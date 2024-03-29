package com.example.RENGAW.service;

import com.example.RENGAW.entity.*;
import com.example.RENGAW.exception.PersonnelAlreadyAssignedException;
import com.example.RENGAW.exception.TeamFullException;
import com.example.RENGAW.repository.ArmouredCarrierRepository;
import com.example.RENGAW.repository.EquipmentRepository;
import com.example.RENGAW.repository.PersonnelRepository;
import com.example.RENGAW.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TeamService{

    private final TeamRepository teamRepository;
    private final PersonnelRepository personnelRepository;
    private final EquipmentRepository equipmentRepository;
    private final ArmouredCarrierRepository armouredCarrierRepository;

    public TeamService(TeamRepository teamRepository, PersonnelRepository personnelRepository, EquipmentRepository equipmentRepository, ArmouredCarrierRepository armouredCarrierRepository) {
        this.teamRepository = teamRepository;
        this.personnelRepository = personnelRepository;
        this.equipmentRepository = equipmentRepository;
        this.armouredCarrierRepository = armouredCarrierRepository;
    }

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

    public void removeTeamExpertise(List<String> expertiseToRemove, Team team){
        for(String s: expertiseToRemove){
            if(s.equalsIgnoreCase("explosive")){
                team.setHasExplosiveExpert(false);
            } else if (s.equalsIgnoreCase("ballistic")) {
                team.setHasBallisticExpert(false);
            } else if (s.equalsIgnoreCase("technology")){
                team.setHasTechnologyExpert(false);
            } else {
                team.getAdditionalExpertise().remove(s);
            }
        }
    }

    public Team saveTeam(Team team) {
        Long teamLeadId = team.getCurrentLeadId();
        Personnel teamLead = personnelRepository.findById(teamLeadId)
                .orElseThrow(() -> new EntityNotFoundException("No personnel with Id : " + teamLeadId));
        if(teamLead.getTeam() == null){
            System.out.println("New Team");
            team = assignPersonnelToTeam(team, team.getCurrentLeadId());
            team.setCurrentLead(teamLead.getCurrentRank() + " " + teamLead.getFirstName() + " " + teamLead.getLastName());
        }

        return teamRepository.save(team);
//        return teamRepository.save(assignPersonnelToTeam(team, team.getCurrentLeadId()));
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

    public void removePersonnelFromTeam(Long teamId){
        List<Personnel> personnelList = personnelRepository.findAllByTeamId(teamId);
        for(Personnel personnel : personnelList){
            personnel.setTeam(null);
        }
        personnelRepository.saveAll(personnelList);
    }

    private void removeCarrierFromTeam(Long teamId) {
        List<ArmouredCarrier> armouredCarrierList = armouredCarrierRepository.findAllByTeamId(teamId);
        for (ArmouredCarrier armouredCarrier : armouredCarrierList){
            armouredCarrier.setTeam(null);
        }
        armouredCarrierRepository.saveAll(armouredCarrierList);
    }

    public Page<Team> findAllTeamPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return teamRepository.findAll(pageable);
    }

    public Team findTeamById(Long teamId) {
        return teamRepository.findById(teamId).
                orElseThrow(() -> new EntityNotFoundException("No Team with Id : " + teamId));
    }

    @Transactional
    public void deleteTeamById(Long teamId) {
        removePersonnelFromTeam(teamId);
        removeCarrierFromTeam(teamId);

        teamRepository.deleteById(teamId);
    }

    private List<String> findExceptionalExpertise(Personnel personnelToRemove, List<Personnel> otherPersonnel) {
        if (otherPersonnel.isEmpty()) {
            return personnelToRemove.getExpertise();
        }

        return personnelToRemove.getExpertise().stream()
                .filter(expertiseToRemove -> otherPersonnel.stream()
                        .noneMatch(personnel -> personnel.getExpertise().contains(expertiseToRemove)))
                .collect(Collectors.toList());
    }

    public void removeSinglePersonnel(Long personnelId) {
        Personnel personnelToRemove = personnelRepository.findById(personnelId)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel with Id : " + personnelId));
        Team team = personnelToRemove.getTeam();
        personnelToRemove.setTeam(null);
        personnelRepository.save(personnelToRemove);
        List<Personnel> otherPersonnel = personnelRepository.findAllByTeamId(team.getId());

        List<String> expertiseToRemove = findExceptionalExpertise(personnelToRemove, otherPersonnel);
        removeTeamExpertise(expertiseToRemove, team);
        teamRepository.save(team);
    }

    public boolean isTeamFull(Long teamId) {
        return Objects.equals(findTeamById(teamId).getTeamMemberCount(), personnelRepository.countByTeamId(teamId));
    }

    public List<Team> findAllTeam() {
        return teamRepository.findAll();
    }
}
