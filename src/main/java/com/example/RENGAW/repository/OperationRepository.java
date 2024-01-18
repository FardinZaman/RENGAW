package com.example.RENGAW.repository;

import com.example.RENGAW.entity.Operation;
import com.example.RENGAW.entity.OperationType;
import com.example.RENGAW.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    public List<Operation> findByOperationType(OperationType operationType);

    @Query(
            "SELECT o FROM Operation o " +
                    "JOIN o.team t " +
                    "WHERE t.teamCodeName LIKE :teamCodeName"
    )
    public List<Operation> findByTeamCodeName(@Param("teamCodeName") String teamCodeName);

    @Query(
            "SELECT t FROM Operation o" +
                    " JOIN o.team t" +
                    " WHERE o.operationCodeName LIKE :operationCodeName"
    )
    public List<Team> findTeamByOperationCodeName(@Param("operationCodeName") String operationCodeName);
}
