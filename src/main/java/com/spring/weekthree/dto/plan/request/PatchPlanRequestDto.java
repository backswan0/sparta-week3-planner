package com.spring.weekthree.dto.plan.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// 일정 수정 요청에 해당하는 request dto
@NoArgsConstructor
@Getter
public class PatchPlanRequestDto {
    // 속성
    private String password;
    private LocalDate plannedDate;
    private String title;
    private String task;

    // 생성자

    // 기능
}