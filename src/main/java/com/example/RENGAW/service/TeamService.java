package com.example.RENGAW.service;

import com.example.RENGAW.entity.Equipment;
import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Team;
import com.example.RENGAW.entity.Weapon;

import java.util.List;

public interface TeamService {
    
    public boolean isReady(Team team);

    public void modifyTeamExpertiseStatus(Personnel personnel, Team team);

    public Team createTeam(Team team);

    public Team assignPersonnelToTeam(Long teamId, Long personnelId);

    public String isTeamReady(Long teamId);

    public List<Personnel> showTeamPersonnelByTeamId(Long teamId);

    public List<Weapon> showWeaponsByTeamId(Long teamId);

    public List<Equipment> showEquipmentByTeamId(Long teamId);

    public List<Weapon> showWeaponOfTeamByOneEquipmentType(String equipmentType);
}
