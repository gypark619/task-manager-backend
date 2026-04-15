package com.task.taskmanager.dto.team;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamCreateRequest {

    private String teamName;
    private Long teamLeaderId;
    private String description;
    private String useYn;
}
