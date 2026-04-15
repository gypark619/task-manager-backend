package com.task.taskmanager.repository;

import com.task.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);

    @Query("""
        SELECT u
        FROM User u
        WHERE (:name IS NULL OR :name = '' OR u.name LIKE %:name%)
          AND (:teamId IS NULL OR u.team.teamId = :teamId)
          AND (:positionId IS NULL OR u.positionId = :positionId)
          AND (:status IS NULL OR u.status = :status)
    """)
    Page<User> searchUsers(
            @Param("name") String name,
            @Param("teamId") Long teamId,
            @Param("positionId") Long positionId,
            @Param("status") User.Status status,
            Pageable pageable
    );
}