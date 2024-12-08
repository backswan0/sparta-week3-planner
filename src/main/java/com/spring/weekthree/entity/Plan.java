package com.spring.weekthree.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Plan {
    // 속성
    private Long id;
    private String name;
    private String password;
    private LocalDate plannedDate;
    private String title;
    private String task;
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