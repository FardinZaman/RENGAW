package com.example.RENGAW.service;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.repository.PersonnelRepository;
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

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private TeamService teamService;

    public Personnel savePersonnel(Personnel personnel) {
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

    public void deletePersonnelById(Long personnelId) {
        personnelRepository.deleteById(personnelId);
    }
}
