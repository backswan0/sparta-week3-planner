package com.spring.weekthree.dto.responsedto;

import com.spring.weekthree.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * [리팩토링 완료]
 * 수정이 바로 안 되는 점 해결
 */

@AllArgsConstructor
@Getter
public class PlanResponseDto {
    // 속성
    private long id;
    private String name;
    private LocalDate plannedDate;
    private String title;
    private String task;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    // 생성자
    public PlanResponseDto(Plan plan) {
        this.id = plan.getId();
        this.name = plan.getName();
        this.plannedDate = plan.getPlannedDate();
        this.title = plan.getTitle();
        this.task = plan.getTask();

        this.createdDateTime = plan.getCreatedDateTime();
        this.updatedDateTime = plan.getUpdatedDateTime();
    }
    // 기능
}