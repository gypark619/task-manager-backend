package com.task.taskmanager.repository;

import com.task.taskmanager.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("""
        SELECT r
        FROM Role r
        WHERE (:roleName IS NULL OR :roleName = '' OR r.roleName LIKE %:roleName%)
    """)
    Page<Role> searchRoles(
            @Param("roleName") String roleName,
            Pageable pageable
    );
}
