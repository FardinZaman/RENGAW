package com.example.RENGAW.repository;

import com.example.RENGAW.entity.ArmouredCarrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArmouredCarrierRepository extends JpaRepository<ArmouredCarrier, Long> {
    public List<ArmouredCarrier> findByTeamTeamId(Long teamId);
}
