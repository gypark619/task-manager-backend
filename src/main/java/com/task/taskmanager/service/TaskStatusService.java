package com.task.taskmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.task.taskmanager.entity.TaskStatus;
import com.task.taskmanager.repository.TaskRepository;
import com.task.taskmanager.repository.TaskStatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskStatusService {
	
	private final TaskStatusRepository taskStatusRepository;
	
	public List<TaskStatus> getTaskStatuses() {
	    return taskStatusRepository.findByUseYnOrderBySortOrderAsc("Y");
	}
}
