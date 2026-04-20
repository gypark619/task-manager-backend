package com.task.taskmanager.repository;

import com.task.taskmanager.entity.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PositionRepository extends JpaRepository<Position, Long> {
    @Query("""
        SELECT p
        FROM Position p
        WHERE (:positionName IS NULL OR :positionName = '' OR p.positionName LIKE %:positionName%)
          AND (:useYn IS NULL OR :useYn = '' OR p.useYn = :useYn)
    """)
    Page<Position> searchPositions(
            @Param("positionName") String positionName,
            @Param("useYn") String useYn,
            Pageable pageable
    );
}
