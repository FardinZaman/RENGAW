package com.example.RENGAW.repository;

import com.example.RENGAW.entity.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long> {
    public Optional<Personnel> findByPersonnelId(Long personnelId);

    public Optional<List<Personnel>> findByFirstNameIgnoreCase(String firstName);

    public Optional<List<Personnel>> findByLastNameIgnoreCase(String lastName);

    public Optional<List<Personnel>> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);

    @Query(
            value = "SELECT p.status FROM personnel p where p.email_address = :email_address",
            nativeQuery = true
    )
    public Optional<String> findStatusByEmailId(@Param("email_address") String emailId);
}
