package com.task.taskmanager.service;

import com.task.taskmanager.dto.common.PageResponse;
import com.task.taskmanager.dto.team.TeamCreateRequest;
import com.task.taskmanager.dto.team.TeamResponse;
import com.task.taskmanager.dto.team.TeamUpdateRequest;
import com.task.taskmanager.entity.Team;
import com.task.taskmanager.exception.CustomException;
import com.task.taskmanager.exception.ErrorCode;
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

    public TeamResponse register(TeamCreateRequest request) {
        Team team = new Team();
        team.setTeamName(request.getTeamName());
        team.setTeamLeaderId(request.getTeamLeaderId());
        team.setDescription(request.getDescription());
        team.setUseYn(request.getUseYn());

        Team savedTeam = teamRepository.save(team);
        return TeamResponse.from(savedTeam);
    }

    public PageResponse<TeamResponse> getTeams(
            String teamName,
            String useYn,
            Pageable pageable
    ) {

        Page<Team> result = teamRepository.searchTeams(teamName, useYn, pageable);

        return new PageResponse<>(
                result.getContent().stream()
                        .map(TeamResponse::from)
                        .toList(),
                result.getTotalPages(),
                result.getTotalElements(),
                result.getNumber()
        );
    }

    public TeamResponse getTeamById(Long teamId) {
        Team team = findTeam(teamId);
        return TeamResponse.from(team);
    }

    public TeamResponse updateTeam(Long teamId, TeamUpdateRequest request) {
        System.out.println("=== updateTeam start ===");
        System.out.println("teamId = " + teamId);
        System.out.println("teamName = " + request.getTeamName());
        System.out.println("teamLeaderId = " + request.getTeamLeaderId());
        System.out.println("description = " + request.getDescription());
        System.out.println("useYn = " + request.getUseYn());
        Team team = findTeam(teamId);
        System.out.println("findTeam success");

        team.setTeamName(request.getTeamName());
        team.setTeamLeaderId(request.getTeamLeaderId());
        team.setDescription(request.getDescription());
        team.setUseYn(request.getUseYn());
        System.out.println("before save");
        Team updateTeam = teamRepository.save(team);
        System.out.println("after save");
        return TeamResponse.from(updateTeam);
    }

    public void deleteTeam(Long teamId) {
        Team team = findTeam(teamId);
        teamRepository.delete(team);
    }

    private Team findTeam(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomException(
                        ErrorCode.DATA_NOT_FOUND,
                        "부서를 찾을 수 없습니다."
                ));
    }
}
