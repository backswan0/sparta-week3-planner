package com.spring.weekthree.dto.plan.response;

import com.spring.weekthree.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

// 일정 조회 및 수정 응답에 해당하는 response dto
@AllArgsConstructor
@Getter
public class PlanResponseDto {
    // 속성
    private long planId;
    private long memberId;
    private LocalDate plannedDate;
    private String title;
    private String task;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    // 생성자
    public PlanResponseDto(Plan plan) {
        this.planId = plan.getPlanId();
        this.memberId = plan.getMemberId();
        this.plannedDate = plan.getPlannedDate();
        this.title = plan.getTitle();
        this.task = plan.getTask();

        this.createdDateTime = plan.getCreatedDateTime();
        this.updatedDateTime = plan.getUpdatedDateTime();
    }
    // 기능
}