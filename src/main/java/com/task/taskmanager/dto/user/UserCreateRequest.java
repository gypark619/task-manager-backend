package com.task.taskmanager.dto.user;

import com.task.taskmanager.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {

    private String employeeNo;
    private String loginId;
    private String name;
    private String email;
    private String phone;
    private String officePhone;
    private Long teamId;
    private Long positionId;
    private User.Status status;
}