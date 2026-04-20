package com.task.taskmanager.service;

import com.task.taskmanager.dto.common.PageResponse;
import com.task.taskmanager.entity.Role;
import com.task.taskmanager.exception.CustomException;
import com.task.taskmanager.exception.ErrorCode;
import com.task.taskmanager.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public PageResponse<Role> getRoles(
            String roleName,
            Pageable pageable
    ) {
        Page<Role> result = roleRepository.searchRoles(roleName, pageable);

        return new PageResponse<>(
                result.getContent(),
                result.getTotalPages(),
                result.getTotalElements(),
                result.getNumber()
        );
    }

    public Role getRole(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new CustomException(
                        ErrorCode.DATA_NOT_FOUND,
                        "권한을 찾을 수 없습니다."
                ));
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public void delete(Long roleId) {
        Role role = findRole(roleId);
        roleRepository.delete(role);
    }

    public Role findRole(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new CustomException(
                        ErrorCode.DATA_NOT_FOUND,
                        "권한을 찾을 수 없습니다."
                ));
    }
}
