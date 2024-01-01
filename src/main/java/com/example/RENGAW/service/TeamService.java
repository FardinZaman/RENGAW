package com.example.RENGAW.service;

import com.example.RENGAW.entity.Team;

public interface TeamService {
    public Team createTeam(Team team);

    public Team assignPersonnelToTeam(Long teamId, Long personnelId);
}
