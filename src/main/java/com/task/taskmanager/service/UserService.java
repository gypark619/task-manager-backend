package com.task.taskmanager.service;

import com.task.taskmanager.entity.User;
import com.task.taskmanager.exception.CustomException;
import com.task.taskmanager.exception.ErrorCode;
import com.task.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getEmployeeNo()));
        return userRepository.save(user);
    }

    public Page<User> searchUsers(
            String name,
            Long teamId,
            Long positionId,
            User.Status status,
            Pageable pageable
    ) {
        return userRepository.searchUsers(name, teamId, positionId, status, pageable);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(
                        ErrorCode.DATA_NOT_FOUND,
                        "사용자를 찾을 수 없습니다."
                ));
    }

    public User getUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(
                        ErrorCode.DATA_NOT_FOUND,
                        "사용자를 찾을 수 없습니다."
                ));
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new CustomException(
                        ErrorCode.DATA_NOT_FOUND,
                        "수정할 사용자를 찾을 수 없습니다."
                ));

        existingUser.setEmployeeNo(user.getEmployeeNo());
        existingUser.setLoginId(user.getLoginId());
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setOfficePhone(user.getOfficePhone());
        existingUser.setTeamId(user.getTeamId());
        existingUser.setPositionId(user.getPositionId());
        existingUser.setStatus(user.getStatus());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(
                        ErrorCode.DATA_NOT_FOUND,
                        "삭제할 사용자를 찾을 수 없습니다."
                ));

        userRepository.delete(user);
    }
}