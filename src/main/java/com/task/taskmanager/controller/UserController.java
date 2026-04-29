package com.task.taskmanager.controller;

import com.task.taskmanager.dto.common.PageResponse;
import com.task.taskmanager.dto.user.UserCreateRequest;
import com.task.taskmanager.dto.user.UserResponse;
import com.task.taskmanager.dto.user.UserUpdateRequest;
import com.task.taskmanager.entity.User;
import com.task.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> register(@RequestBody UserCreateRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping
    public ResponseEntity<PageResponse<UserResponse>> getUsers(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "teamId", required = false) Long teamId,
            @RequestParam(name = "positionId", required = false) Long positionId,
            @RequestParam(name = "status", required = false) User.Status status,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortField", defaultValue = "userId") String sortField,
            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection
    ) {
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                userService.getUsers(name, teamId, positionId, status, pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UserUpdateRequest request
    ) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<Void> saveUserRoles(
            @PathVariable(name = "id") Long id,
            @RequestBody List<Long> roleIds
    ) {
        userService.saveUserRoles(id, roleIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/roles")
    public ResponseEntity<List<Long>> getUserRoles(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(userService.getUserRoleIds(id));
    }
}