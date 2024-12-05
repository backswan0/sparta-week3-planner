package com.spring.weekthree.dto;

import lombok.Getter;

/**
 * Create 완료
 * Read 완료 (목록 조회)
 *
 *
 *
 */

@Getter
public class PlanRequestDto {
    // [1] 속성
    private String name;
    private String password;
    private String plannedDate;
    private String title;
    private String task;

    // [2] 생성자

    // [3] 기능
}
