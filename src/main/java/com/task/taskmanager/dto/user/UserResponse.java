package com.task.taskmanager.dto.user;

import com.task.taskmanager.entity.User;
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

                // position
                user.getPosition() != null ? user.getPosition().getPositionId() : null,
                user.getPosition() != null ? user.getPosition().getPositionName() : "",

                user.getStatus().name(),
                getStatusName(user.getStatus())
        );
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