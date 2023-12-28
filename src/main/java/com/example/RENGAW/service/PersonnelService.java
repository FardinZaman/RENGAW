package com.example.RENGAW.service;

import com.example.RENGAW.entity.Personnel;

import java.util.List;

public interface PersonnelService {
    public Personnel savePersonnel(Personnel personnel);

    public List<Personnel> findPersonnelByFirstName(String firstName);

    public List<Personnel> findPersonnelByLastName(String lastName);

    public List<Personnel> findPersonnelByFirstNameAndLastName(String firstName, String lastName);

    public String findPersonnelStatusByEmailId(String emailId);

    public void updatePersonnelStatusIfDepressed();
}
