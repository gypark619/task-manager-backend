package com.task.taskmanager.repository;

import com.task.taskmanager.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUserUserId(Long userId);

    void deleteByUserUserId(Long userId);
}
