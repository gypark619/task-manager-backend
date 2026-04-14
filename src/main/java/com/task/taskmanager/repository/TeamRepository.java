package com.task.taskmanager.repository;

import com.task.taskmanager.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Page<Team> findByTeamNameContainingAndUseYnContaining(
            String teamName,
            String useYn,
            Pageable pageable
    );
}
