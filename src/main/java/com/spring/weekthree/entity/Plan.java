package com.spring.weekthree.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Create 완료
 * Read 리팩토링 1차 완료 (if문 대신 스트림 사용, 목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
 * Delete 완료
 */

@Getter
public class Plan {
    // [1-a] 속성 중 사용자의 입력값을 저장하는 필드
    @Setter
    private Long id;
    private String name;
    private String password;
    private LocalDateTime plannedDate;
    private String title;
    private String task;

    // [1-b] 속성 중 사용자의 입력값을 저장하지 않는 필드
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    /**
     * [2] 생성자
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
            LocalDateTime plannedDate,
            String title,
            String task
    ) {
        this.name = name;
        this.password = password;
        this.plannedDate = plannedDate;
        this.title = title;
        this.task = task;

        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    // [3] 기능
    public void editPlanEntity(
            String name,
            LocalDateTime plannedDate,
            String title,
            String task
    ) {
        this.name = name;
        this.plannedDate = plannedDate;
        this.title = title;
        this.task = task;

        this.updatedDate = LocalDateTime.now();
    }
}