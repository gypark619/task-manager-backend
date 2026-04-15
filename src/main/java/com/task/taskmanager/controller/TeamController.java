package com.task.taskmanager.controller;

import com.task.taskmanager.dto.common.PageResponse;
import com.task.taskmanager.dto.team.TeamCreateRequest;
import com.task.taskmanager.dto.team.TeamResponse;
import com.task.taskmanager.dto.team.TeamUpdateRequest;
import com.task.taskmanager.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponse> register(@RequestBody TeamCreateRequest request) {
        return ResponseEntity.ok(teamService.register(request));
    }

    @GetMapping
    public ResponseEntity<PageResponse<TeamResponse>> getTeams (
            @RequestParam(required = false) String teamName,
            @RequestParam(required = false) String useYn,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "teamId") String sortField,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                teamService.getTeams(teamName, useYn, pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamResponse> updateTeam(
            @PathVariable Long id,
            @RequestBody TeamUpdateRequest request
    ) {
        return ResponseEntity.ok(teamService.updateTeam(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }
}
