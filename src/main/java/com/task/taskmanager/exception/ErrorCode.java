package com.task.taskmanager.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // 공통 서버 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-500", "서버 오류가 발생했습니다."),

    // 잘못된 요청
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "COMMON-400", "잘못된 요청입니다."),

    // 데이터 없음 (공통)
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON-404", "데이터를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}