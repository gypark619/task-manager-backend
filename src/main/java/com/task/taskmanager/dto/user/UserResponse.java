package com.task.taskmanager.dto.user;

import com.task.taskmanager.entity.Team;
import com.task.taskmanager.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long userId;
    private final String employeeNo;
    private final String loginId;
    private final String name;
    private final String email;
    private final String phone;
    private final String officePhone;
    private final Long teamId;
    private final String teamName;
    private final Long positionId;
    private final String positionName;
    private final String status;
    private final String statusName;

    public UserResponse(Long userId, String employeeNo, String loginId, String name,
                        String email, String phone, String officePhone,
                        Long teamId, String teamName,
                        Long positionId, String positionName,
                        String status, String statusName) {
        this.userId = userId;
        this.employeeNo = employeeNo;
        this.loginId = loginId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.officePhone = officePhone;
        this.teamId = teamId;
        this.teamName = teamName;
        this.positionId = positionId;
        this.positionName = positionName;
        this.status = status;
        this.statusName = statusName;
    }

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getEmployeeNo(),
                user.getLoginId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getOfficePhone(),

                // team
                user.getTeam() != null ? user.getTeam().getTeamId() : null,
                user.getTeam() != null ? user.getTeam().getTeamName() : "",

                user.getPositionId(),
                getPositionName(user.getPositionId()),
                user.getStatus().name(),
                getStatusName(user.getStatus())
        );
    }

    private static String getPositionName(Long positionId) {
        if (positionId == null) return "";

        return switch (positionId.intValue()) {
            case 1 -> "사원";
            case 2 -> "대리";
            case 3 -> "과장";
            case 4 -> "부장";
            default -> "";
        };
    }

    private static String getStatusName(User.Status status) {
        if (status == null) return "";

        return switch (status) {
            case ACTIVE -> "재직";
            case SUSPENDED -> "휴직";
            case INACTIVE -> "퇴직";
        };
    }
}