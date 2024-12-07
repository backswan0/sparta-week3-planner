package com.spring.weekthree.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Create 완료
 * Read 리팩토링 1차 완료 (if문 대신 스트림 사용, 목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
 * Delete 완료
 * JDBC - Create 리팩토링 완료
 * JDBC - Read 리팩토링 중 (목록 조회)
 * JDBC - Read 리팩토링 완료 (단건 조회)
 * JDBC - Update 리팩토링 2차 완료 (수정 날짜 바뀌도록 수정, 일부가 null일 때 예외 처리 전)
 * JDBC - Delete 리팩토링 완료
 */

@NoArgsConstructor
@Getter
public class PlanRequestDto {
    // 속성
    private String name;
    private String password;
    private LocalDate plannedDate;
    private String title;
    private String task;

    // 생성자

    // 기능
}