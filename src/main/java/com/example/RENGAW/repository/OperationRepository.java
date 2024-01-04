package com.example.RENGAW.repository;

import com.example.RENGAW.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    public Optional<Operation> findByOperationId(Long operationId);
}
