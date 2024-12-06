package com.spring.weekthree.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Create 완료
 * Read 진행 중 (목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
 *
 */

@Getter
public class PlanRequestDto {
    // [1] 속성
    private String name;
    private String password;
    private LocalDateTime plannedDate;
    private String title;
    private String task;

    // [2] 생성자

    // [3] 기능
}
