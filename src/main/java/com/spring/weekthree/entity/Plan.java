package com.spring.weekthree.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Create 완료
 * Read 리팩토링 1차 완료 (if문 대신 스트림 사용, 목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
 * Delete 완료
 * JDBC - Create 리팩토링 완료
 * JDBC - Read 리팩토링 중 (목록 조회)
 * JDBC - Read 리팩토링 완료 (예외처리 추가 수정, 단건 조회)
 * JDBC - Update 리팩토링 3차 완료 (클린 업 완료, 수정 날짜 바뀌도록 수정, 일부가 null일 때 예외 처리 전)
 * JDBC - Delete 리팩토링 완료
 */

@AllArgsConstructor
@Getter
public class Plan {
    // 속성 - 사용자의 입력값을 저장하는 필드
    private Long id;
    private String name;
    private String password;
    private LocalDate plannedDate;
    // 일정 날짜에 시간은 필요하지 않으므로 LocalDateTime에서 변경

    private String title;
    private String task;

    // 속성 - 사용자의 입력값을 저장하지 않는 필드
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    /**
     * 생성자
     *
     * @param name        : 사용자 이름
     * @param password    : 사용자 비밀번호
     * @param plannedDate : 사용자가 입력한 일정 날짜
     * @param title       : 사용자가 입력한 일정의 제목
     * @param task        : 사용자가 입력한 일정의 상세 정보
     */
    public Plan(
            String name,
            String password,
            LocalDate plannedDate,
            String title,
            String task
    ) {
        this.name = name;
        this.password = password;
        this.plannedDate = plannedDate;
        this.title = title;
        this.task = task;

        this.createdDateTime = LocalDateTime.now();
        this.updatedDateTime = LocalDateTime.now();
    }
    // 기능
}