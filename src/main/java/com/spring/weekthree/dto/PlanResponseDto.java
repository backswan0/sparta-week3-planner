package com.spring.weekthree.dto;

import com.spring.weekthree.entity.Plan;
import lombok.Getter;

@Getter
public class PlanResponseDto {
    // 속성
    private long id;
    private String name;
    private String password;
    private String plannedDate;
    private String title;
    private String task;

    // 생성자
    public PlanResponseDto (Plan plan)
    {
        this.id = plan.getId();
        this.name = plan.getName();
        this.password = plan.getPassword();
        this.plannedDate = plan.getPlannedDate();
        this.title = plan.getTitle();
        this.task = plan.getTask();
    }

    // 기능
}
