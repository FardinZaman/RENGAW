package com.example.RENGAW.repository;

import com.example.RENGAW.entity.ArmouredCarrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArmouredCarrierRepository extends JpaRepository<ArmouredCarrier, Long> {
    public List<ArmouredCarrier> findAllByTeamId(Long teamId);

    Optional<ArmouredCarrier> findByCarrierSerialNumber(Long carrierSerialNumber);

    @Transactional
    void deleteByCarrierSerialNumber(Long carrierSerialNumber);
}
