package com.task.taskmanager.dto.team;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamUpdateRequest {

    private String teamName;
    private Long teamLeaderId;
    private String description;
    private String useYn;
}
