package com.spring.weekthree.dto;

import com.spring.weekthree.entity.Plan;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Create 완료
 * Read 리팩토링 1차 완료 (if문 대신 스트림 사용, 목록 조회)
 * Read 완료 (단건 조회)
 * Update 완료 (PATCH)
 * Delete 완료
 */

@Getter
public class PlanResponseDto {
    // [1] 속성
    private long id;
    private String name;
    private LocalDateTime plannedDate;
    private String title;
    private String task;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // [2] 생성자
    public PlanResponseDto(Plan plan) {
        this.id = plan.getId();
        this.name = plan.getName();
        this.plannedDate = plan.getPlannedDate();
        this.title = plan.getTitle();
        this.task = plan.getTask();

        this.createdDate = plan.getCreatedDate();
        this.updatedDate = plan.getUpdatedDate();
    }
    // [3] 기능
}