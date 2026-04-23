package com.task.taskmanager.service;

import com.task.taskmanager.dto.common.PageResponse;
import com.task.taskmanager.dto.task.TaskResponse;
import com.task.taskmanager.entity.Task;
import com.task.taskmanager.exception.CustomException;
import com.task.taskmanager.exception.ErrorCode;
import com.task.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public PageResponse<TaskResponse> getTasks(
            String title,
            Long statusId,
            Long teamId,
            Long assigneeId,
            LocalDate startDate,
            LocalDate dueDate,
            Pageable pageable
    ) {
        Page<Task> result = taskRepository.searchTasks(title, statusId, teamId, assigneeId, startDate, dueDate, pageable);

        return new PageResponse<>(
                result.getContent().stream()
                        .map(TaskResponse::from)
                        .toList(),
                result.getTotalPages(),
                result.getTotalElements(),
                result.getNumber()
        );
    }

    public TaskResponse getTaskById(Long taskId) {
        Task task = findTask(taskId);
        return TaskResponse.from(task);
    }

    public TaskResponse save(Task task) {
        Task savedTask = taskRepository.save(task);
        return TaskResponse.from(savedTask);
    }

    public void delete(Long taskId) {
        Task task = findTask(taskId);
        taskRepository.delete(task);
    }

    public Task findTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(
                        ErrorCode.DATA_NOT_FOUND,
                        "업무를 찾을 수 없습니다."
                ));
    }
}
