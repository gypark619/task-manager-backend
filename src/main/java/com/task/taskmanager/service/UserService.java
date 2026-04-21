package com.task.taskmanager.service;

import com.task.taskmanager.entity.*;
import com.task.taskmanager.exception.CustomException;
import com.task.taskmanager.exception.ErrorCode;
import com.task.taskmanager.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.task.taskmanager.dto.user.UserCreateRequest;
import com.task.taskmanager.dto.user.UserUpdateRequest;
import com.task.taskmanager.dto.user.UserResponse;
import com.task.taskmanager.dto.common.PageResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeamRepository teamRepository;
    private final PositionRepository positionRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public UserResponse register(UserCreateRequest request) {
        User user = new User();
        user.setEmployeeNo(request.getEmployeeNo());
        user.setLoginId(request.getLoginId());
        user.setName(request.getName());

        String email = request.getEmail();
        validateEmail(email);
        user.setEmail(email != null ? email.trim() : null);

        user.setPhone(request.getPhone());
        user.setOfficePhone(request.getOfficePhone());

        Team team = null;
        if (request.getTeamId() != null) {
            team = teamRepository.findById(request.getTeamId())
                    .orElse(null);
        }
        user.setTeam(team);

        Position position = null;
        if (request.getPositionId() != null) {
            position = positionRepository.findById(request.getPositionId())
                    .orElse(null);
        }
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

        String email = request.getEmail();
        validateEmail(email);
        user.setEmail(email != null ? email.trim() : null);

        user.setPhone(request.getPhone());
        user.setOfficePhone(request.getOfficePhone());

        Team team = null;
        if (request.getTeamId() != null) {
            team = teamRepository.findById(request.getTeamId())
                    .orElse(null);
        }
        user.setTeam(team);

        Position position = null;
        if (request.getPositionId() != null) {
            position = positionRepository.findById(request.getPositionId())
                    .orElse(null);
        }
        user.setPosition(position);

        user.setStatus(request.getStatus());

        User updatedUser = userRepository.save(user);
        return UserResponse.from(updatedUser);
    }

    public void deleteUser(Long userId) {
        User user = findUser(userId);
        userRepository.delete(user);
    }

    @Transactional
    public void saveUserRoles(Long userId, List<Long> roleIds) {
        User user = findUser(userId);

        userRoleRepository.deleteByUserUserId(userId);

        for (Long roleId : roleIds) {
            Role role = findRole(roleId);

            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);

            userRoleRepository.save(userRole);
        }
    }

    public List<Long> getUserRoleIds(Long userId) {
        return userRoleRepository.findByUserUserId(userId).stream()
                .map(userRole -> userRole.getRole().getRoleId())
                .toList();
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(
                        ErrorCode.DATA_NOT_FOUND,
                        "사용자를 찾을 수 없습니다."
                ));
    }

    private Role findRole(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new CustomException(
                        ErrorCode.DATA_NOT_FOUND,
                        "권한을 찾을 수 없습니다."
                ));
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            return;
        }

        String trimmedEmail = email.trim();
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (!trimmedEmail.matches(emailRegex)) {
            throw new CustomException(
                    ErrorCode.INVALID_INPUT,
                    "이메일 형식이 올바르지 않습니다."
            );
        }
    }
}