package com.example.RENGAW.service;

import com.example.RENGAW.entity.ArmouredCarrier;

import java.util.List;

public interface ArmouredCarrierService {
    public ArmouredCarrier assignCarrierToTeamAndSave(Long teamId, ArmouredCarrier armouredCarrier);

    public List<ArmouredCarrier> findCarrierByTeamId(Long teamId);
}
