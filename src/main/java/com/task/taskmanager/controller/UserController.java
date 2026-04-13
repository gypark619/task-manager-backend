package com.task.taskmanager.controller;

import com.task.taskmanager.dto.PageResponse;
import com.task.taskmanager.dto.UserResponse;
import com.task.taskmanager.entity.User;
import com.task.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import org.springframework.data.domain.Page;
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
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping
    public PageResponse<UserResponse> getUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long teamId,
            @RequestParam(required = false) Long positionId,
            @RequestParam(required = false) User.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userId") String sortField,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> result = userService.searchUsers(name, teamId, positionId, status, pageable);

        List<UserResponse> content = result.getContent().stream()
                .map(UserResponse::from)
                .toList();

        return new PageResponse<>(
                content,
                result.getTotalPages(),
                result.getTotalElements(),
                result.getNumber()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        existingUser.setEmployeeNo(user.getEmployeeNo());
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setLoginId(user.getLoginId());
        existingUser.setPhone(user.getPhone());
        existingUser.setOfficePhone(user.getOfficePhone());
        existingUser.setTeamId(user.getTeamId());
        existingUser.setPositionId(user.getPositionId());
        existingUser.setStatus(user.getStatus());

        return ResponseEntity.ok(userService.updateUser(existingUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}