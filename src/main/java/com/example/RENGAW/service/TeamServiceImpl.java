package com.example.RENGAW.service;

import com.example.RENGAW.entity.Personnel;
import com.example.RENGAW.entity.Team;
import com.example.RENGAW.repository.PersonnelRepository;
import com.example.RENGAW.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TeamServiceImpl implements TeamService{

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

//    public boolean isReady(Team team){
//        return team.isHasBallisticExpert() && team.isHasExplosiveExpert() && team.isHasTechnologyExpert() &&
//                (Objects.equals(team.getTeamMemberCount(), personnelRepository.countByTeamTeamId(team.getTeamId()))) &&
//                personnelRepository.checkAllStatusAvailableByTeamId(team.getTeamId());
//    }

    @Override
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team assignPersonnelToTeam(Long teamId, Long personnelId) {
        Personnel personnel = personnelRepository.findByPersonnelId(personnelId).orElseThrow(EntityNotFoundException::new);
        Team team = teamRepository.findByTeamId(teamId).orElseThrow(EntityNotFoundException::new);

        for(String s: personnel.getExpertise()){
            if(s.equalsIgnoreCase("explosive")){
                team.setHasExplosiveExpert(true);
            } else if (s.equalsIgnoreCase("ballistic")) {
                team.setHasBallisticExpert(true);
            } else if (s.equalsIgnoreCase("technology")){
                team.setHasTechnologyExpert(true);
            } else {
                team.getAdditionalExpertise().add(s);
            }
        }

        personnel.setTeam(team);
        personnelRepository.save(personnel);

        return teamRepository.save(team);
    }
}
