package com.task.taskmanager.service;

import com.task.taskmanager.dto.common.PageResponse;
import com.task.taskmanager.entity.Position;
import com.task.taskmanager.exception.CustomException;
import com.task.taskmanager.exception.ErrorCode;
import com.task.taskmanager.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    public PageResponse<Position> getPositions(
            String positionName,
            String useYn,
            Pageable pageable
    ) {
        Page<Position> result = positionRepository.searchPositions(positionName, useYn, pageable);

        return new PageResponse<>(
                result.getContent(),
                result.getTotalPages(),
                result.getTotalElements(),
                result.getNumber()
        );
    }

    public Position getPosition(Long positionId) {
        return positionRepository.findById(positionId)
                .orElseThrow(() -> new CustomException(
                        ErrorCode.DATA_NOT_FOUND,
                        "직급을 찾을 수 없습니다."
                ));
    }

    public Position save(Position position) {
        return positionRepository.save(position);
    }

    public void delete(Long positionId) {
        Position position = findPosition(positionId);
        positionRepository.delete(position);
    }

    public Position findPosition(Long positionId) {
        return positionRepository.findById(positionId)
                .orElseThrow(() -> new CustomException(
                        ErrorCode.DATA_NOT_FOUND,
                        "직급을 찾을 수 없습니다."
                ));
    }
}
