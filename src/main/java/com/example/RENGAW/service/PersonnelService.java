package com.example.RENGAW.service;

import com.example.RENGAW.entity.Equipment;
import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Weapon;
import com.example.RENGAW.repository.EquipmentRepository;
import com.example.RENGAW.repository.PersonnelRepository;
import com.example.RENGAW.repository.WeaponRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonnelService{

    private final PersonnelRepository personnelRepository;
    private final TeamService teamService;
    private final WeaponRepository weaponRepository;
    private final EquipmentRepository equipmentRepository;

    public PersonnelService(PersonnelRepository personnelRepository, TeamService teamService, WeaponRepository weaponRepository, EquipmentRepository equipmentRepository) {
        this.personnelRepository = personnelRepository;
        this.teamService = teamService;
        this.weaponRepository = weaponRepository;
        this.equipmentRepository = equipmentRepository;
    }

    public Personnel savePersonnel(Personnel personnel) {
        if(personnel.getTeam() != null){
            teamService.modifyTeamExpertiseStatus(personnel, personnel.getTeam());
        }

        return personnelRepository.save(personnel);
    }

    public Personnel findPersonnelById(Long personnelId) {
        return personnelRepository.findById(personnelId)
                .orElseThrow(() -> new EntityNotFoundException("No personnel found with id " + personnelId));
    }

    public List<Personnel> findPersonnelByFirstName(String firstName) {
        return personnelRepository.findByFirstNameIgnoreCase(firstName);
    }

    public List<Personnel> findPersonnelByLastName(String lastName) {
        return personnelRepository.findByLastNameIgnoreCase(lastName);
    }

    public List<Personnel> findPersonnelByFirstNameAndLastName(String firstName, String lastName) {
        return personnelRepository.findByFirstNameAndLastNameIgnoreCase(firstName, lastName);
    }

    public String findPersonnelStatusByEmail(String email) {
        String personnelStatus = personnelRepository.findStatusByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Status Found By Email : " + email));
        return "Status : " + personnelStatus;
    }

    public void updatePersonnelStatusIfDepressed() {
        personnelRepository.updatePersonnelStatusIfDepressed();
    }

    public Personnel addExpertiseToPersonnel(Map<String, Object> expertiseListMap, Long personnelId) {
        Personnel personnel = personnelRepository.findById(personnelId)
                .orElseThrow(() -> new EntityNotFoundException("No Personnel Found By Id : " + personnelId));
        List<String> expertiseList = (List<String>) expertiseListMap.get("expertiseList");
        personnel.setExpertise(expertiseList);

        if(personnel.getTeam() != null){
            teamService.modifyTeamExpertiseStatus(personnel, personnel.getTeam());
        }

        return personnelRepository.save(personnel);
    }

    public Page<Personnel> findAllPersonnelPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return personnelRepository.findAll(pageable);
    }

    public void removeEquipmentFromPersonnel(Long personnelId) {
        List<Equipment> equipmentList = equipmentRepository.findByPersonnelId(personnelId);
        for (Equipment equipment : equipmentList){
            equipment.setPersonnel(null);
        }
        equipmentRepository.saveAll(equipmentList);
    }

    public void removeWeaponFromPersonnel(Long personnelId) {
        List<Weapon> weaponList = weaponRepository.findByPersonnelId(personnelId);
        for (Weapon weapon : weaponList){
            weapon.setPersonnel(null);
        }
        weaponRepository.saveAll(weaponList);
    }
    public void deletePersonnelById(Long personnelId) {
        removeWeaponFromPersonnel(personnelId);
        removeEquipmentFromPersonnel(personnelId);

        if (findPersonnelById(personnelId).getTeam() != null){
            teamService.removeSinglePersonnel(personnelId);
        }

        personnelRepository.deleteById(personnelId);
    }

    public List<Personnel> findAllPersonnel() {
        return personnelRepository.findAll();
    }

    public List<Personnel> findPersonnelOfTeam(Long teamId) {
        return personnelRepository.findAllByTeamId(teamId);
    }

    public List<Personnel> findPersonnelWithoutTeam() {
        return personnelRepository.findByTeamIsNull();
    }
}
