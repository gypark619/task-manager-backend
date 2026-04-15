package com.task.taskmanager.repository;

import com.task.taskmanager.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("""
        SELECT t
        FROM Team t
        WHERE (:teamName IS NULL OR :teamName = '' OR t.teamName LIKE %:teamName%)
          AND (:useYn IS NULL OR :useYn = '' OR t.useYn = :useYn)
    """)
    Page<Team> searchTeams(
            @Param("teamName") String teamName,
            @Param("useYn") String useYn,
            Pageable pageable
    );
}
