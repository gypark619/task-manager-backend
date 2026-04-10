package com.task.taskmanager.controller;

import com.task.taskmanager.entity.User;
import com.task.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userService.getUserById(id);
        if (existingUser == null) return null;

        existingUser.setEmployeeNo(user.getEmployeeNo());
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setLoginId(user.getLoginId());
        existingUser.setPhone(user.getPhone());
        existingUser.setOfficePhone(user.getOfficePhone());
        existingUser.setTeamId(user.getTeamId());
        existingUser.setPositionId(user.getPositionId());
        existingUser.setStatus(user.getStatus());

        return userService.updateUser(existingUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}