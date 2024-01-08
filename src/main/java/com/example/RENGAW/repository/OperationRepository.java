package com.example.RENGAW.repository;

import com.example.RENGAW.dto.OperationDTO;
import com.example.RENGAW.entity.Operation;
import com.example.RENGAW.entity.enumaration.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    public Optional<Operation> findByOperationId(Long operationId);

    public List<Operation> findByOperationType(OperationType operationType);

    @Query(
            "SELECT o FROM Operation o " +
                    "JOIN o.operationTeams t " +
                    "WHERE t.teamCodeName LIKE :teamCodeName"
    )
    public List<Operation> findByTeamCodeName(@Param("teamCodeName") String teamCodeName);
}
