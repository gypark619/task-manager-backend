package com.task.taskmanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "task_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "status_name", nullable = false)
    private String statusName;

    @Column(name = "sort_order")
    private String sortOrder;

    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn;
}