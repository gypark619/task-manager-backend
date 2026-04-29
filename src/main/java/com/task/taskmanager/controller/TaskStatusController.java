package com.task.taskmanager.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.task.taskmanager.entity.TaskStatus;
import com.task.taskmanager.service.TaskStatusService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/task-statuses")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class TaskStatusController {

    private final TaskStatusService taskStatusService;

    @GetMapping
    public ResponseEntity<List<TaskStatus>> getTaskStatuses() {
        return ResponseEntity.ok(taskStatusService.getTaskStatuses());
    }
}