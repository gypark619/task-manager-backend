package com.task.taskmanager.controller;

import com.task.taskmanager.dto.common.PageResponse;
import com.task.taskmanager.entity.Position;
import com.task.taskmanager.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/positions")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping
    public ResponseEntity<PageResponse<Position>> getPositions(
            @RequestParam(required = false) String positionName,
            @RequestParam(required = false) String useYn,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "positionId") String sortField,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                positionService.getPositions(positionName, useYn, pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getPosition(@PathVariable Long id) {
        return ResponseEntity.ok(positionService.getPosition(id));
    }

    @PostMapping
    public ResponseEntity<Position> save(@RequestBody Position position) {
        return ResponseEntity.ok(
                positionService.save(position)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Position> update(
            @PathVariable Long id,
            @RequestBody Position position
    ) {
        position.setPositionId(id);
        return ResponseEntity.ok(
                positionService.save(position)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        positionService.delete(id);
        return ResponseEntity.ok().build();
    }

}