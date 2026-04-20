package com.task.taskmanager.service;

import com.task.taskmanager.entity.Position;
import com.task.taskmanager.entity.Team;
import com.task.taskmanager.entity.User;
import com.task.taskmanager.exception.CustomException;
import com.task.taskmanager.exception.ErrorCode;
import com.task.taskmanager.repository.PositionRepository;
import com.task.taskmanager.repository.TeamRepository;
import com.task.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.task.taskmanager.dto.user.UserCreateRequest;
import com.task.taskmanager.dto.user.UserUpdateRequest;
import com.task.taskmanager.dto.user.UserResponse;
import com.task.taskmanager.dto.common.PageResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeamRepository teamRepository;
    private final PositionRepository positionRepository;
    public UserResponse register(UserCreateRequest request) {
        User user = new User();
        user.setEmployeeNo(request.getEmployeeNo());
        user.setLoginId(request.getLoginId());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setOfficePhone(request.getOfficePhone());

        Team team = teamRepository.findById(request.getTeamId())
                .orElse(null);
        user.setTeam(team);

        Position position = positionRepository.findById(request.getPositionId())
                .orElse(null);
        user.setPosition(position);

        user.setStatus(request.getStatus() != null ? request.getStatus() : User.Status.ACTIVE);

        user.setPassword(passwordEncoder.encode(request.getEmployeeNo()));

        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

    public PageResponse<UserResponse> getUsers(
            String name,
            Long teamId,
            Long positionId,
            User.Status status,
            Pageable pageable
    ) {
        Page<User> result = userRepository.searchUsers(name, teamId, positionId, status, pageable);

        return new PageResponse<>(
                result.getContent().stream()
                        .map(UserResponse::from)
                        .toList(),
                result.getTotalPages(),
                result.getTotalElements(),
                result.getNumber()
        );
    }

    public UserResponse getUserById(Long userId) {
        User user = findUser(userId);
        return UserResponse.from(user);
    }

    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = findUser(userId);

        user.setEmployeeNo(request.getEmployeeNo());
        user.setLoginId(request.getLoginId());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setOfficePhone(request.getOfficePhone());

        Team team = teamRepository.findById(request.getTeamId())
                .orElse(null);
        user.setTeam(team);

        Position position = positionRepository.findById(request.getPositionId())
                .orElse(null);
        user.setPosition(position);

        user.setStatus(request.getStatus());

        User updatedUser = userRepository.save(user);
        return UserResponse.from(updatedUser);
    }

    public void deleteUser(Long userId) {
        User user = findUser(userId);
        userRepository.delete(user);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(
                        ErrorCode.DATA_NOT_FOUND,
                        "사용자를 찾을 수 없습니다."
                ));
    }
}