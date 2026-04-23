package com.task.taskmanager.repository;

import com.task.taskmanager.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("""
        SELECT t
        FROM Task t
        WHERE (:title IS NULL OR :title = '' OR t.title LIKE %:title%)
          AND (:statusId IS NULL OR t.status.statusId = :statusId)
          AND (:teamId IS NULL OR t.team.teamId = :teamId)
          AND (:assigneeId IS NULL OR t.assignee.userId = :assigneeId)
          AND (:startDate IS NULL OR :dueDate IS NULL OR t.startDate BETWEEN :startDate AND :dueDate)
    """)
    Page<Task> searchTasks(
            @Param("title") String title,
            @Param("statusId") Long statusId,
            @Param("teamId") Long teamId,
            @Param("assigneeId") Long assigneeId,
            @Param("startDate") LocalDate startDate,
            @Param("dueDate") LocalDate dueDate,
            Pageable pageable
    );
}
