package com.task.taskmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.taskmanager.entity.TaskStatus;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {

    List<TaskStatus> findByUseYnOrderBySortOrderAsc(String useYn);

}