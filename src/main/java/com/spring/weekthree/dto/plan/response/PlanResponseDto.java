package com.spring.weekthree.dto.plan.response;

import com.spring.weekthree.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 도전 과제 C 완료
 * 도전 과제 R 전체 조회 완료
 * 도전 과제 R 단건 조회 리팩토링 완료
 * 도전 과제 U 초안 완료
 * 도전 과제 D 완료
 */

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