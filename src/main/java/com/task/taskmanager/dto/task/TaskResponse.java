package com.task.taskmanager.dto.task;

import com.task.taskmanager.entity.Task;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TaskResponse {

    private final Long taskId;
    private final String title;
    private final Long statusId;
    private final String statusName;
    private final Integer priority;
    private final Integer progress;
    private final LocalDate startDate;
    private final LocalDate dueDate;
    private final Long teamId;
    private final String teamName;
    private final Long assigneeId;
    private final String assigneeName;

    public TaskResponse(Long taskId, String title, Long statusId,
                        String statusName, Integer priority, Integer progress,
                        LocalDate startDate, LocalDate dueDate,
                        Long teamId, String teamName, Long assigneeId, String assigneeName) {
        this.taskId = taskId;
        this.title = title;
        this.statusId = statusId;
        this.statusName = statusName;
        this.priority = priority;
        this.progress = progress;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.teamId = teamId;
        this.teamName = teamName;
        this.assigneeId = assigneeId;
        this.assigneeName = assigneeName;
    }

    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getTaskId(),
                task.getTitle(),

                task.getStatus() != null ? task.getStatus().getStatusId() : null,
                task.getStatus() != null ? task.getStatus().getStatusName() : "",

                task.getPriority(),
                task.getProgress(),

                task.getStartDate(),
                task.getDueDate(),

                task.getTeam() != null ? task.getTeam().getTeamId() : null,
                task.getTeam() != null ? task.getTeam().getTeamName() : "",

                task.getAssignee() != null ? task.getAssignee().getUserId() : null,
                task.getAssignee() != null ? task.getAssignee().getName() : ""
        );
    }
}
