package com.task.taskmanager.controller;

import com.task.taskmanager.dto.common.PageResponse;
import com.task.taskmanager.dto.task.TaskResponse;
import com.task.taskmanager.entity.Task;
import com.task.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> register(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.save(task));
    }

    @GetMapping
    public ResponseEntity<PageResponse<TaskResponse>> getTasks(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "statusId", required = false) Long statusId,
            @RequestParam(name = "teamId", required = false) Long teamId,
            @RequestParam(name = "assigneeId", required = false) Long assigneeId,
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @RequestParam(name = "dueDate", required = false) LocalDate dueDate,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortField", defaultValue = "taskId") String sortField,
            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection
    ) {
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                taskService.getTasks(title, statusId, teamId, assigneeId, startDate, dueDate, pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable(name = "id") Long id,
            @RequestBody Task task
    ) {
        task.setTaskId(id);
        return ResponseEntity.ok(taskService.save(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable(name = "id") Long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

}