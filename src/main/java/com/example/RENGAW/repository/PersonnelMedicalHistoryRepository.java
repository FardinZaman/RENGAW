package com.example.RENGAW.repository;

import com.example.RENGAW.entity.PersonnelMedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonnelMedicalHistoryRepository extends JpaRepository<PersonnelMedicalHistory, Long> {
    public Optional<PersonnelMedicalHistory> findByPersonnelPersonnelId(Long personnelId);
}
