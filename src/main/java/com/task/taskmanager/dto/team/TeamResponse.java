package com.task.taskmanager.dto.team;

import com.task.taskmanager.entity.Team;
import lombok.Getter;

@Getter
public class TeamResponse {

    private final Long teamId;
    private final String teamName;
    private final Long teamLeaderId;
    private final String description;
    private final String useYn;

    public TeamResponse(Long teamId, String teamName, Long teamLeaderId, String description, String useYn) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamLeaderId = teamLeaderId;
        this.description = description;
        this.useYn = useYn;
    }

    public static TeamResponse from(Team team) {
        return new TeamResponse(
                team.getTeamId(),
                team.getTeamName(),
                team.getTeamLeaderId(),
                team.getDescription(),
                team.getUseYn()
        );
    }
}
