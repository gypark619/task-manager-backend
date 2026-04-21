# Task Manager Backend

## 📌 Overview

Spring Boot 기반의 업무 관리 시스템 REST API 서버입니다.

사용자, 팀, 직급, 권한 관리 기능을 중심으로  
검색, 페이징, 정렬, 공통 예외 처리 구조를 적용하였습니다.

데이터 무결성을 위해 입력 검증과 예외 처리 구조를 명확히 분리하여 설계하였습니다.

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
- Service: 비즈니스 로직 및 검증 처리
- Controller: REST API 엔드포인트
- DTO: 요청 / 응답 데이터 구조 분리
- Exception: 공통 예외 처리 구조

---

## 🚀 주요 기능

### 사용자 관리
- 사용자 등록 / 수정 / 삭제
- 사용자 목록 조회
- 사용자 권한 조회 / 저장

### 팀 관리
- 팀 등록 / 수정 / 삭제
- 팀 목록 조회
- 팀장(부서장) 사용자 정보 조회 지원

### 직급 / 권한 관리
- 직급 등록 / 수정 / 삭제
- 권한 등록 / 수정 / 삭제

### 검색 및 필터링
- 이름, 부서, 직급, 상태 조건 검색
- 서버 사이드 검색 처리

### 페이징
- Spring Data JPA Pageable 기반 서버 페이징

### 정렬
- 최신순, 이름순, 사번순 정렬

---

## 🔍 입력 값 검증

- 이메일 형식 검증 로직을 Service 계층에서 수행
- 잘못된 입력은 CustomException으로 처리
- 프론트 검증과 별도로 서버에서 최종 검증 수행

👉 클라이언트 우회 요청에도 데이터 무결성 보장

---

## ⚠️ 공통 예외 처리

- GlobalExceptionHandler 적용
- CustomException + ErrorCode 기반 처리
- 일관된 API 응답 구조 유지

---

## 📡 API 설계

### 사용자 조회
GET /users

### 사용자 등록
POST /users

### 사용자 수정
PUT /users/{userId}

### 사용자 삭제
DELETE /users/{userId}

### 사용자 권한 조회
GET /users/{userId}/roles

### 사용자 권한 저장
PUT /users/{userId}/roles

---

## 🧩 트러블슈팅

### 1. 프론트 필터링 → 서버 검색 구조 변경
- 문제: 데이터 증가 시 성능 저하
- 해결: API에서 검색 조건 처리

### 2. Page<Entity> 직접 반환 문제
- 문제: 불필요한 데이터 노출
- 해결: PageResponse DTO로 변환

### 3. 예외 처리 분산 문제
- 문제: Controller마다 try-catch 중복
- 해결: GlobalExceptionHandler로 통합

### 4. 사용자 권한 관리 기능 분리
- 문제: 사용자 정보와 권한 관리 책임 혼재
- 해결: API 분리로 책임 명확화

### 5. 팀장 정보 표시 문제
- 문제: ID만으로 화면 표시 어려움
- 해결: 응답에 이름/사번 포함

### 6. 이메일 입력 구조 개선 ⭐
- 문제: 이메일을 단일 문자열로 처리 시 프론트 로직 복잡
- 해결: emailId / emailDomain 구조로 분리
- 결과: 입력 처리 단순화 및 유지보수성 향상

---

## 🔐 보안

- DB 설정은 application-local.properties로 분리
- 해당 파일은 .gitignore로 관리
- 환경별 설정 분리 구조 적용

---

## ⚙️ Run

```bash
./gradlew bootRun