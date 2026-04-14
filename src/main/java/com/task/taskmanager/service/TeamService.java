package com.task.taskmanager.service;

import com.task.taskmanager.entity.Team;
import com.task.taskmanager.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Page<Team> getTeams(
            String teamName,
            String useYn,
            Pageable pageable
    ) {
        return teamRepository.findByTeamNameContainingAndUseYnContaining(teamName, useYn, pageable);
    }

    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}
