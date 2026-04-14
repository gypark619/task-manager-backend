# Task Manager Backend

## 📌 Overview

Spring Boot 기반의 사용자 및 업무 관리 시스템 REST API 서버입니다.

실무 환경을 고려하여 검색, 페이징, 정렬, 공통 예외 처리 구조를 적용하였습니다.

---

## 🛠 Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- MySQL

---

## 📂 Project Structure

- Entity: DB 테이블 매핑
- Repository: JPA 기반 데이터 접근 계층
- Service: 비즈니스 로직 처리
- Controller: REST API 엔드포인트

---

## 🚀 주요 기능

### 사용자 관리
- 사용자 등록 / 수정 / 삭제
- 사용자 목록 조회

### 검색 및 필터링
- 이름, 부서, 직급, 상태 조건 검색
- 서버 사이드 검색 처리

### 페이징
- Spring Data JPA Pageable 기반 서버 페이징 적용

### 정렬
- 최신순, 이름순, 사번순 정렬 기능

### 응답 구조 개선
- Page<User> → PageResponse DTO 변환
- 필요한 데이터만 전달하도록 구조 개선

### 공통 예외 처리
- GlobalExceptionHandler 적용
- CustomException + ErrorCode 기반 에러 처리
- 일관된 API 응답 구조 유지

---

## 📡 API 설계

### 사용자 조회
GET /users

Query Params:
- name
- teamId
- positionId
- status
- page
- size
- sortField
- sortDirection

### 사용자 등록
POST /users

### 사용자 수정
PUT /users/{userId}

### 사용자 삭제
DELETE /users/{userId}

---

## 🧩 트러블슈팅

### 1. 프론트 필터링 → 서버 검색 구조 변경
- 문제: 데이터 증가 시 성능 저하
- 해결: API에서 검색 조건 처리하도록 개선

### 2. Page<Entity> 직접 반환 문제
- 문제: 불필요한 데이터 노출
- 해결: PageResponse DTO로 변환하여 응답 최적화

### 3. 예외 처리 분산 문제
- 문제: Controller마다 try-catch 중복 발생
- 해결: GlobalExceptionHandler로 공통 처리 구조 적용

---

## ⚙️ Run

```bash
./gradlew bootRun