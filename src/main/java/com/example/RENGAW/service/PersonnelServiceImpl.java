package com.example.RENGAW.service;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.repository.PersonnelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonnelServiceImpl implements PersonnelService{

    @Autowired
    private PersonnelRepository personnelRepository;

    @Override
    public Personnel savePersonnel(Personnel personnel) {
        return personnelRepository.save(personnel);
    }

    @Override
    public List<Personnel> findPersonnelByFirstName(String firstName) {
        Optional<List<Personnel>> personnelList = personnelRepository.findByFirstNameIgnoreCase(firstName);

        if (personnelList.isEmpty()){
            throw new EntityNotFoundException("No personnel with first name " + firstName);
        }

        return personnelList.get();
    }

    @Override
    public List<Personnel> findPersonnelByLastName(String lastName) {
        Optional<List<Personnel>> personnelList = personnelRepository.findByLastNameIgnoreCase(lastName);

        if (personnelList.isEmpty()){
            throw new EntityNotFoundException("No personnel with first name " + lastName);
        }

        return personnelList.get();
    }

    @Override
    public List<Personnel> findPersonnelByFirstNameAndLastName(String firstName, String lastName) {
        Optional<List<Personnel>> personnelList = personnelRepository.findByFirstNameAndLastNameIgnoreCase(firstName, lastName);

        if (personnelList.isEmpty()){
            throw new EntityNotFoundException("No personnel with name " + firstName + lastName);
        }

        return personnelList.get();
    }

    @Override
    public String findPersonnelStatusByEmailId(String emailId) {
        String personnelStatus = personnelRepository.findStatusByEmailId(emailId).orElseThrow(EntityNotFoundException::new);
        return "Status : " + personnelStatus;
    }
}
