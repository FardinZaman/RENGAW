package com.example.RENGAW.repository;

import com.example.RENGAW.entity.Equipment;
import com.example.RENGAW.entity.Team;
import com.example.RENGAW.entity.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
//    public Optional<Team> findByTeamId(Long teamId);

    @Query(
            "SELECT w FROM Weapon w " +
                    "JOIN w.personnel p " +
                    "JOIN p.team t " +
                    "WHERE t.id = :teamId"
    )
    public List<Weapon> findWeaponUsedByTeamPersonnelByTeamId(@Param("teamId") Long teamId);

    @Query(
            "SELECT e FROM Equipment e " +
                    "JOIN e.personnel p " +
                    "JOIN p.team t " +
                    "WHERE t.id = :teamId"
    )
    public List<Equipment> findEquipmentUsedByTeamPersonnelByTeamId(@Param("teamId") Long teamId);

    Optional<Team> findByTeamCodeName(String teamCodeName);
}
